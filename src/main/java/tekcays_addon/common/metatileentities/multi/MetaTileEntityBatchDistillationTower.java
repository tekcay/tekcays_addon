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
import gregtech.api.recipes.Recipe;
import gregtech.api.sound.GTSounds;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiFluidHatch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.ItemStackHandler;
import tekcays_addon.api.capability.impl.DistillationMethods;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.api.utils.TKCYALog;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockLargeMultiblockCasing;

import java.util.List;
import com.google.common.collect.Lists;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Function;

import static gregtech.api.util.RelativeDirection.*;
import static tekcays_addon.api.capability.impl.MultiblocksMethods.*;
import static tekcays_addon.api.utils.TKCYAValues.NEW_DISTILLATION_RECIPES;

public class MetaTileEntityBatchDistillationTower extends RecipeMapMultiblockController {

    private List<FluidStack> inputs = new ArrayList<>();
    private List<FluidStack> distillate = new ArrayList<>();
    private List<FluidStack> toDistill = new ArrayList<>();
    private Map<Integer, FluidStack> toDistillBP = new TreeMap<>();
    private int temp, targetTemp, increaseTemp;
    private int bp, toFill;
    private int outputRate, energyCost, parallel;

    private boolean hasEnoughEnergy;

    public MetaTileEntityBatchDistillationTower(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYARecipeMaps.DISTILLATION);
        temp = 300;
        outputRate = 0;
        targetTemp = 0;
        energyCost = 0;
        bp = 0;
        toFill = 0;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityBatchDistillationTower(metaTileEntityId);
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


    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();
        setOutputRate();
        setIncreaseTemp();
    }

    @Override
    public void updateFormedValid() {
        super.updateFormedValid();

        if (toDistillBP.isEmpty()) {

            FluidStack inputFluidStack = inputFluidInventory.getTankAt(0).getFluid();

            if (inputFluidStack == null) return;

            for (FluidStack fs : NEW_DISTILLATION_RECIPES.keySet()) {

                if (!inputFluidStack.getFluid().equals(fs.getFluid())) continue;

                int test = inputFluidStack.amount % fs.amount;

                parallel = (inputFluidStack.amount - test) / fs.amount;
                FluidStack toDrain = new FluidStack(fs.getFluid(), fs.amount * parallel);
                inputFluidInventory.drain(toDrain, true);

                //this.recipeMapWorkable.isActive();
            }

            DistillationMethods.setToDistillBP(distillate, toDistillBP);

        }

        bp = new ArrayList<>(toDistillBP.keySet()).get(0);
        toFill = toDistillBP.get(bp).amount;

        TKCYALog.logger.info("bp = " + bp);
        //Not hot enough to boil first product
        if (temp < bp) {
            TKCYALog.logger.info("does it go here3");

            if (getOffsetTimer() % 20 == 0) {
                TKCYALog.logger.info("does it go here2");

                energyCost = temperatureEnergyCostBatchDistillationTower(temp + increaseTemp);
                hasEnoughEnergy = enoughEnergyToDrain(energyContainer, energyCost);

                if (hasEnoughEnergy) {

                    TKCYALog.logger.info("does it go here");
                    drainEnergy(energyContainer, energyCost);
                    setTemp(Math.min(temp + increaseTemp, bp));
                    TKCYALog.logger.info("temp = " + temp);

                } else setTemp(Math.max(temp - increaseTemp, 300));
            }
                //if (!hasEnoughEnergy && temp == 300) break;
        }

        //Placed here in case of interrupted process

        TKCYALog.logger.info("toFill = " + toFill);

        //Hot enough to boil product
        if (temp == bp) {

            hasEnoughEnergy = enoughEnergyToDrain(energyContainer, energyCost);

            if (toFill > 0 && hasEnoughEnergy) {

                if (getOffsetTimer() % 20 == 0) {

                    Fluid fluid = toDistillBP.get(bp).getFluid();

                    if (toFill - outputRate >= 0) {
                        outputFluidInventory.fill(new FluidStack(fluid, outputRate), true);
                        toFill -= outputRate;
                    } else {
                        outputFluidInventory.fill(new FluidStack(fluid, toFill), true);
                        toFill = 0;
                        toDistillBP.remove(bp);
                    }
                }
            }
        }



    }





    @Override
    protected Function<BlockPos, Integer> multiblockPartSorter() {
        return BlockPos::getY; // todo this needs to be "relative up" with Freedom Wrench
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (isStructureFormed()) {
            FluidStack stackInTank = importFluids.drain(Integer.MAX_VALUE, false);
            if (stackInTank != null && stackInTank.amount > 0) {
                TextComponentTranslation fluidName = new TextComponentTranslation(stackInTank.getFluid().getUnlocalizedName(stackInTank));
                textList.add(new TextComponentTranslation("gregtech.multiblock.distillation_tower.distilling_fluid", fluidName));
                textList.add(new TextComponentTranslation("tkcya.multiblock.distillation_tower.parallel", parallel));

            }
            textList.add(new TextComponentTranslation("tkcya.multiblock.distillation_tower.temperature", temp));
            textList.add(new TextComponentTranslation("tkcya.multiblock.distillation_tower.energy_cost", energyCost));
        }
        super.addDisplayText(textList);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle("YSY", "YYY", "YYY")
                .aisle("XXX", "X#X", "XXX").setRepeatable(1, 11)
                .aisle("XXX", "XXX", "XXX")
                .where('S', selfPredicate())
                .where('Y', states(getCasingState())
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(3))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setExactLimit(1)))
                .where('X', states(getCasingState())
                        .or(metaTileEntities(MultiblockAbility.REGISTRY.get(MultiblockAbility.EXPORT_FLUIDS).stream()
                                .filter(mte->!(mte instanceof MetaTileEntityMultiFluidHatch))
                                .toArray(MetaTileEntity[]::new))
                                .setMinLayerLimited(1).setMaxLayerLimited(1))
                        .or(autoAbilities(true, false)))
                .where('#', air())
                .build();
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        inputs = recipe.getFluidInputs();
        distillate = recipe.getAllFluidOutputs(-1);
        return false;
    }

    @Override
    protected boolean allowSameFluidFillForOutputs() {
        return false;
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return TKCYATextures.WHITE_GT;
    }

    protected IBlockState getCasingState() {
        return TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.GALVANIZED_STEEL_WALL);
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.DISTILLATION_TOWER_OVERLAY;
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
        data.setInteger("targetTemp", this.targetTemp);
        data.setBoolean("hasEnoughEnergy", this.hasEnoughEnergy);
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
        this.targetTemp = data.getInteger("targetTemp");
        this.hasEnoughEnergy = data.getBoolean("hasEnoughEnergy");
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.temp);
        buf.writeInt(this.targetTemp);
        buf.writeBoolean(this.hasEnoughEnergy);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.temp = buf.readInt();
        this.targetTemp = buf.readInt();
        this.hasEnoughEnergy = buf.readBoolean();
    }
}