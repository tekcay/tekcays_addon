package tekcays_addon.common.metatileentities.multi;

import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.sound.GTSounds;
import gregtech.client.renderer.ICubeRenderer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.ItemStackHandler;
import tekcays_addon.api.recipes.DistillationRecipes;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockLargeMultiblockCasing;

import java.util.List;
import com.google.common.collect.Lists;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

import static gregtech.api.util.RelativeDirection.*;
import static tekcays_addon.api.capability.impl.DistillationMethods.*;
import static tekcays_addon.api.capability.impl.MultiblocksMethods.*;
import static tekcays_addon.api.recipes.DistillationRecipes.TKCYA_DISTILLATION_RECIPES;
import static tekcays_addon.api.utils.TKCYAValues.getGasCostMap;

public class MetaTileEntityBatchDistillationTower extends RecipeMapMultiblockController {

    private Map<Integer, FluidStack> toDistillBP = new TreeMap<>();
    private FluidStack fluidToDistill;
    /**
     * represents the current {@code Fluid} fraction that is handled.
     */
    private Fluid fraction;
    /**
     * represents the next {@code Fluid} fraction to come.
     */
    private Fluid nextFraction;
    /**
     * represents the index of the current fraction in {@code toDistillBP} map.
     */
    private int fractionIndex;
    private int temp, increaseTemp;
    private int outputRate, energyCost, parallel;
    private int bp, nextbp, toFill;
    private int height;
    private final int duration = 20;
    private boolean recipeAcquired, isTheLastFraction;
    /**
     * Useful to remember the current recipe
     */
    private int distillationRecipesIndex;


    private boolean hasEnoughEnergy;

    public MetaTileEntityBatchDistillationTower(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYARecipeMaps.DISTILLATION);
        temp = 300;
        outputRate = 0;
        energyCost = 0;
        bp = 0;
        toFill = 0;
        fractionIndex = 0;
        recipeAcquired = false;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityBatchDistillationTower(metaTileEntityId);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();
        this.height = context.getOrDefault("DTHeight", 1);
        setOutputRate();
        setIncreaseTemp();
    }

    @Override
    public void updateFormedValid() {
        super.updateFormedValid();

        if (toDistillBP.isEmpty() && recipeAcquired) {
            setToDistillBP(TKCYA_DISTILLATION_RECIPES.get(distillationRecipesIndex).getFluidStackOutput(), toDistillBP);
            setBp();
            setFraction();
        }

        if (toDistillBP.isEmpty() && this.isBlockRedstonePowered() && !recipeAcquired) {

            for (IFluidTank fluidTank : inputFluidInventory.getFluidTanks()) {
                fluidToDistill = fluidTank.getFluid();
                if (fluidToDistill == null) continue;

                distillationRecipesIndex = -1;

                for (DistillationRecipes distillationRecipes : TKCYA_DISTILLATION_RECIPES) {

                    distillationRecipesIndex++;
                    FluidStack inputFluidStackRecipe = distillationRecipes.getFluidStackInput();
                    if (!fluidToDistill.isFluidEqual(inputFluidStackRecipe)) continue;

                    int test = fluidToDistill.amount % inputFluidStackRecipe.amount;

                    //Evaluates how many recipes can be run at once
                    parallel = (fluidToDistill.amount - test) / inputFluidStackRecipe.amount;

                    //Parallel = 0 means not enough fluid to run any recipe, so it skips to the next recipe
                    if (parallel == 0) continue;

                    FluidStack toDrain = new FluidStack(inputFluidStackRecipe.getFluid(), inputFluidStackRecipe.amount * parallel);
                    inputFluidInventory.drain(toDrain, true);
                    setToDistillBP(distillationRecipes.getFluidStackOutput(), toDistillBP);
                    break;
                }
                if (parallel != 0) break;
            }

            fractionIndex = 0;
            setBp();
            setToFill();
            setFraction();
            isItTheLastFraction();
            recipeAcquired = true;
            this.markDirty();
        }

        isItTheLastFraction();

        //Not hot enough to boil first product
        if (temp < bp) {

            if (getOffsetTimer() % (int) (duration * parallel - 0.2f * Math.pow(height, 1.7)) == 0) {

                energyCost = (int) (temperatureEnergyCostBatchDistillationTower(temp + increaseTemp) * 1.2f * parallel);
                hasEnoughEnergy = enoughEnergyToDrain(energyContainer, energyCost);

                if (hasEnoughEnergy) {
                    drainEnergy(energyContainer, energyCost);
                    setTemp(Math.min(temp + increaseTemp, bp));

                } else setTemp(Math.max(temp - increaseTemp, 300));
            }
        }

        //Hot enough to boil product
        if (temp > 300 && temp == bp) {

            energyCost = (int) (temperatureEnergyCostBatchDistillationTower(temp + increaseTemp) * 1.2f * parallel);
            hasEnoughEnergy = enoughEnergyToDrain(energyContainer, energyCost);

            if (toFill > 0 && hasEnoughEnergy) {

                setNextFraction();

                if (getOffsetTimer() % (int) (duration * parallel - 0.2f * Math.pow(height, 1.7)) == 0) {

                    if (toFill - outputRate > 0) {
                        outputFluidInventory.fill(new FluidStack(fraction, outputRate), true);
                        toFill -= outputRate;
                    } else {
                        outputFluidInventory.fill(new FluidStack(fraction, toFill), true);
                        toFill = 0;
                        fractionIndex ++;
                    }
                }
            }
        }

        if (!toDistillBP.isEmpty() && !isTheLastFraction && toFill == 0) {
            setBp();
            setToFill();
            setFraction();
            this.markDirty();
        }


        if (!toDistillBP.isEmpty() && isTheLastFraction) reset();
    }

    public void setOutputRate() {
        outputRate = 1;
    }

    public void setTemp(int temperature) {
        temp = temperature;
    }

    public void setIncreaseTemp() {
        increaseTemp = 1;
    }

    public void setBp() {
        bp = new ArrayList<>(toDistillBP.keySet()).get(fractionIndex);
    }

    public void setToFill() {
        toFill = toDistillBP.get(bp).amount;
    }

    private void isItTheLastFraction() {
        isTheLastFraction =  fractionIndex == toDistillBP.size();
    }

    private void setFraction() {
        fraction = toDistillBP.get(bp).getFluid();
    }

    private void setNextFraction() {
        nextbp = new ArrayList<>(toDistillBP.keySet()).get(fractionIndex + 1);
        nextFraction = toDistillBP.get(nextbp).getFluid();
    }

    private boolean hasCircuit1() {

        for (int i = 0;  i < inputInventory.getSlots(); i++) {
            ItemStack input = inputInventory.getStackInSlot(i);
            if (input.isItemEqual((IntCircuitIngredient.getIntegratedCircuit(1)))) return true;
        }
        return false;
    }

    public void reset() {

        toDistillBP.clear();
        outputRate = 0;
        energyCost = 0;
        bp = 0;
        toFill = 0;
        fractionIndex = 0;
        recipeAcquired = false;

    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);

        tooltip.add(I18n.format("tkcya.machine.batch_distillation_tower.tooltip.1"));
        tooltip.add(I18n.format("tkcya.machine.batch_distillation_tower.tooltip.2"));
        tooltip.add(I18n.format("tkcya.machine.batch_distillation_tower.tooltip.3"));
        tooltip.add(I18n.format("tkcya.machine.batch_distillation_tower.tooltip.4"));
        tooltip.add(I18n.format("tkcya.machine.batch_distillation_tower.tooltip.5"));

   }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (isStructureFormed()) {

            if (inputInventory.getSlots() != 0 && hasCircuit1()) {

                if (fluidToDistill != null) {
                    textList.add(new TextComponentTranslation("gregtech.multiblock.distillation_tower.distilling_fluid", fluidToDistill.getLocalizedName()));
                }

                if (fraction != null && temp == bp) {
                    textList.add(new TextComponentTranslation("tkcya.multiblock.distillation_tower.boiling", new FluidStack(fraction, 1).getLocalizedName(), toFill));
                    if (nextFraction != null) {
                        textList.add(new TextComponentTranslation("tkcya.multiblock.distillation_tower.next_fraction", new FluidStack(nextFraction, 1).getLocalizedName(), nextbp));
                    }
                }

                if (fraction != null && temp < bp) {
                    textList.add(new TextComponentTranslation("tkcya.multiblock.distillation_tower.next_fraction", new FluidStack(fraction, 1).getLocalizedName(), bp));
                }
            }
            textList.add(new TextComponentTranslation("tkcya.multiblock.distillation_tower.parallel", parallel));
            textList.add(new TextComponentTranslation("tkcya.multiblock.distillation_tower.temperature", temp));
            textList.add(new TextComponentTranslation("tkcya.multiblock.distillation_tower.energy_cost", energyCost));
        }
        super.addDisplayText(textList);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle("HHH", "HHH", "HHH")
                .aisle("YSY", "YYY", "YYY")
                .aisle("XXX", "X#X", "XXX").setRepeatable(1, 11)
                .aisle("AAA", "AAA", "AAA")
                .where('H', states(getHeatAcceptorState()))
                .where('X', states(getCasingState()))
                .where('S', selfPredicate())
                .where('Y', states(getCasingState())
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(3))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setExactLimit(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMaxGlobalLimited(1).setPreviewCount(1))
                        .or(autoAbilities(true, false)))
                .where('A', states(getCasingState())
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMinGlobalLimited(1)))
                .where('#', heightIndicatorPredicate())
                .build();
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        return false;
    }

    public static TraceabilityPredicate heightIndicatorPredicate() {
        return new TraceabilityPredicate((blockWorldState) -> {
            if (air().test(blockWorldState)) {
                blockWorldState.getMatchContext().increment("DTHeight", 1);
                return true;
            } else
                return false;
        });
    }


    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return TKCYATextures.WHITE_GT_STRIPE;
    }

    protected IBlockState getCasingState() {
        return TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.BATCH_DISTILLATION_TOWER_CASING);
    }

    protected IBlockState getHeatAcceptorState() {
        return TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.HEAT_ACCEPTOR);
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return TKCYATextures.BATCH_DISTILLATION_TOWER_OVERLAY;
    }

    @Override
    public int getFluidOutputLimit() {
        return getOutputFluidInventory().getTanks();
    }

    @Override
    public SoundEvent getSound() {
        return GTSounds.FURNACE;
    }

    @Override
    public void initializeAbilities() {
        this.inputFluidInventory = new FluidTankList(true, getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        this.inputInventory = new ItemHandlerList(getAbilities(MultiblockAbility.IMPORT_ITEMS));
        this.outputFluidInventory = new FluidTankList(true, getAbilities(MultiblockAbility.EXPORT_FLUIDS));
        this.energyContainer = new EnergyContainerList(getAbilities(MultiblockAbility.INPUT_ENERGY));
    }

    private void resetTileAbilities() {
        this.inputFluidInventory = new FluidTankList(true);
        this.inputInventory = new ItemStackHandler(0);
        this.outputFluidInventory = new FluidTankList(true);
        this.energyContainer = new EnergyContainerList(Lists.newArrayList());
    }

    @Override
    public void invalidateStructure() {
        setTemp(300);
        super.invalidateStructure();
        resetTileAbilities();
    }





    ////Data

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("temp", this.temp);
        data.setInteger("fractionIndex", this.fractionIndex);
        data.setInteger("toFill", this.toFill);
        data.setInteger("parallel", this.parallel);
        data.setInteger("distillationRecipesIndex", this.distillationRecipesIndex);
        data.setBoolean("recipeAcquired", this.recipeAcquired);
        return data;
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == 600) {
            this.temp = buf.readInt();
            scheduleRenderUpdate();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.temp = data.getInteger("temp");
        this.fractionIndex = data.getInteger("fractionIndex");
        this.toFill = data.getInteger("toFill");
        this.parallel = data.getInteger("parallel");
        this.distillationRecipesIndex = data.getInteger("distillationRecipesIndex");
        this.recipeAcquired = data.getBoolean("recipeAcquired");

    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.temp);
        buf.writeInt(this.fractionIndex);
        buf.writeInt(this.toFill);
        buf.writeInt(this.parallel);
        buf.writeInt(this.distillationRecipesIndex);
        buf.writeBoolean(this.recipeAcquired);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.temp = buf.readInt();
        this.fractionIndex = buf.readInt();
        this.toFill = buf.readInt();
        this.parallel = buf.readInt();
        this.distillationRecipesIndex = buf.readInt();
        this.recipeAcquired = buf.readBoolean();

    }
}
