package tekcays_addon.common.metatileentities.multi;

import gregtech.api.GTValues;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.Recipe;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import tekcays_addon.api.capability.impl.MultiblockNoEnergyRecipeLogic;
import tekcays_addon.api.metatileentity.mutiblock.RecipeMapMultiblockNoEnergyController;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.api.recipes.builders.TemperatureRecipeBuilder;
import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.api.unification.TKCYAMaterials;
import tekcays_addon.api.utils.MiscMethods;
import tekcays_addon.api.utils.TKCYALog;
import tekcays_addon.common.items.TKCYAMetaItems;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static gregtech.api.unification.ore.OrePrefix.gem;
import static gregtech.api.util.RelativeDirection.*;


public class MetaTileEntityTKCYABlastFurnace extends RecipeMapMultiblockNoEnergyController {


    private boolean canAchieveTargetTemp, hasEnoughGas, hasEnoughItem;
    private int temp, targetTemp, increaseTemp;
    private int height, hasGasOutputHatchInt;
    private FluidTankList airOrFlueGasImport;
    private IItemHandlerModifiable coalOrCokeImport;
    private int inputItemSlot, itemHeatingValue;


    private final Fluid[] ACCEPTED_INPUT_FLUIDS = {Materials.Air.getFluid(), TKCYAMaterials.HotFlueGas.getFluid()};
    private final ItemStack[] ACCEPTED_INPUT_ITEMS = {OreDictUnifier.get(gem, Materials.Charcoal), OreDictUnifier.get(gem, Materials.Coal), OreDictUnifier.get(gem, Materials.Coke)};
    private final int[] ACCEPTED_INPUT_FLUIDS_MULTIPLIER = {10, 1};
    private final int[] ACCEPTED_INPUT_ITEMS_MULTIPLIER = {2, 2, 1};
    private int inputFluidMultiplier, inputItemMultiplier;
    private FluidStack inputGasFluidStack;
    private ItemStack inputItemStack;

    public void setIncreaseTemp() {
        increaseTemp = 5;
    }

    public void setTargetTemp() {
        targetTemp = 2000;
    }

    public MetaTileEntityTKCYABlastFurnace(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYARecipeMaps.BLASTING_RECIPES);
        this.recipeMapWorkable = new TKCYABlastFurnaceLogic(this);

        temp = 300;
        inputFluidMultiplier = 0;
        inputItemMultiplier = 0;
        inputItemSlot = 0;
        itemHeatingValue = 0;
        hasEnoughGas = false;
        hasEnoughItem = false;
    }

    @Override
    protected void updateFormedValid() {
        super.updateFormedValid();
        setIncreaseTemp();
        setTargetTemp();
        setMultiplier();

        if (temp >= targetTemp && hasEnoughInputGas(temp) && hasEnoughInputItem(temp)) {
            canAchieveTargetTemp = true;
            if (getOffsetTimer() % 20 == 0) {
                drainGas(temp);
                drainItem(temp);
            }
        }
        
        if (temp - increaseTemp >= 300 &&
            (!hasEnoughInputGas(temp) || !hasEnoughInputItem(temp)))
        {
            canAchieveTargetTemp = false;
            if (getOffsetTimer() % 20 == 0) {
                setTemp(temp - increaseTemp);
            }
        }

        if (canHeatUp()) {
             canAchieveTargetTemp = true;
            if (getOffsetTimer() % 20 == 0) {
                drainGas(temp + increaseTemp);
                drainItem(temp + increaseTemp);
                setTemp(temp + increaseTemp);
            }
        }

        hasEnoughGas = hasEnoughInputGas(temp);
        hasEnoughItem = hasEnoughInputItem(temp);
    }

    public void setMultiplier() {

        if (!hasAcceptedFluid() || !hasAcceptedItem()) return;

        getGasCostMap().entrySet().stream()
                .filter(e -> MiscMethods.isSameFluid(inputGasFluidStack, e.getKey()))
                .forEach(e -> inputFluidMultiplier = e.getValue());

        getItemCostMap().entrySet().stream()
                .filter(e -> inputItemStack.isItemEqual(e.getKey()))
                .forEach(e -> inputItemMultiplier = e.getValue());
    }

    public Map<Fluid, Integer> getGasCostMap() {

        Map<Fluid, Integer> gasCostMap = new HashMap<>();

        if (ACCEPTED_INPUT_FLUIDS.length != ACCEPTED_INPUT_FLUIDS_MULTIPLIER.length) return gasCostMap;

        for (int i = 0; i < ACCEPTED_INPUT_FLUIDS.length; i++) {
            gasCostMap.put(ACCEPTED_INPUT_FLUIDS[i], ACCEPTED_INPUT_FLUIDS_MULTIPLIER[i]);
        }
        return gasCostMap;
    }

    public Map<ItemStack, Integer> getItemCostMap() {

        Map<ItemStack, Integer> itemCostMap = new HashMap<>();

        if (ACCEPTED_INPUT_ITEMS.length != ACCEPTED_INPUT_ITEMS_MULTIPLIER.length) return itemCostMap;

        for (int i = 0; i < ACCEPTED_INPUT_ITEMS.length; i++) {
            itemCostMap.put(ACCEPTED_INPUT_ITEMS[i], ACCEPTED_INPUT_ITEMS_MULTIPLIER[i]);
        }
        return itemCostMap;
    }

    private boolean hasAcceptedFluid() {

        for (IFluidTank fluidTank : airOrFlueGasImport.getFluidTanks()) {

            for (Fluid fluid : ACCEPTED_INPUT_FLUIDS) {
                if (MiscMethods.isSameFluid(fluidTank.getFluid(), fluid)) {
                    inputGasFluidStack = fluidTank.getFluid();
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasAcceptedItem() {

        for (int i = 0;  i < coalOrCokeImport.getSlots(); i++) {
            ItemStack input = coalOrCokeImport.getStackInSlot(i);
            for (ItemStack stack : ACCEPTED_INPUT_ITEMS) {
                if (ModHandler.getFuelValue(input) > 0) {
                    inputItemSlot = i;
                    inputItemStack = stack;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isHeating() {
        return temp > 300 && hasEnoughInputGas(temp) && hasEnoughInputItem(temp);
    }

    public boolean canHeatUp() {
        return temp <= targetTemp - increaseTemp
                && hasEnoughInputGas(temp + increaseTemp)
                && hasEnoughInputItem(temp + increaseTemp);
    }

    private boolean hasGasCollectorItem() {

        for (int i = 0;  i < coalOrCokeImport.getSlots(); i++) {
            ItemStack input = coalOrCokeImport.getStackInSlot(i);
            if (input.isItemEqual((TKCYAMetaItems.GAS_COLLECTOR).getStackForm())) return true;
        }
        return false;
    }



    private boolean hasEnoughInputGas(int temperature) {
        if (!hasAcceptedFluid()) return false;
        return inputGasFluidStack.amount >= getTemperatureGasConsumption(temperature);
    }

    private boolean hasEnoughInputItem(int temperature) {
        return hasAcceptedItem() || itemHeatingValue >= getTemperatureItemConsumption(temperature);
    }

    public int getTemperatureGasConsumption(int temperature) {
        return inputFluidMultiplier * (temperature - 300) * height ; //TODO formula for consumption
    }

    public int getTemperatureItemConsumption(int temperature) {
        return inputItemMultiplier * (temperature - 300) * height ; //TODO formula for consumption
    }


    private void drainGas(int temperature) {
        airOrFlueGasImport.drain(new FluidStack(inputGasFluidStack.getFluid(), getTemperatureGasConsumption(temperature)), true);
    }
    
    private void drainItem(int temperature) {
        if (itemHeatingValue < getTemperatureItemConsumption(temperature)) {
            ItemStack stack = coalOrCokeImport.getStackInSlot(inputItemSlot);
            stack.shrink(1);
            itemHeatingValue += 1000 - getTemperatureItemConsumption(temperature);
        } else {
            itemHeatingValue -= getTemperatureItemConsumption(temperature);
        }
    }

    public boolean getHasGasOutputHatch() {
        return hasGasOutputHatchInt == 1;
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        return temp >= recipe.getProperty(TemperatureRecipeBuilder.TemperatureProperty.getInstance(), 0);
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);

        tooltip.add(I18n.format("tekcays_addon.machine.tkcya_blast_furnace.tooltip.1"));
        tooltip.add(I18n.format("tekcays_addon.machine.tkcya_blast_furnace.tooltip.2"));
        tooltip.add(I18n.format("tekcays_addon.machine.tkcya_blast_furnace.tooltip.3"));
        tooltip.add(I18n.format("tekcays_addon.machine.tkcya_blast_furnace.tooltip.4"));
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (!this.isStructureFormed()) {
            ITextComponent tooltip = new TextComponentTranslation("gregtech.multiblock.invalid_structure.tooltip");
            tooltip.setStyle((new Style()).setColor(TextFormatting.GRAY));
            textList.add((new TextComponentTranslation("gregtech.multiblock.invalid_structure")).setStyle((new Style()).setColor(TextFormatting.RED).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, tooltip))));
        } else {
            if (this.hasMaintenanceMechanics() && ConfigHolder.machines.enableMaintenance) {
                this.addMaintenanceText(textList);
            }
            if (!this.recipeMapWorkable.isWorkingEnabled()) {
                textList.add(new TextComponentTranslation("gregtech.multiblock.work_paused"));
            } else if (this.recipeMapWorkable.isActive()) {
                textList.add(new TextComponentTranslation("gregtech.multiblock.running"));
                int currentProgress = (int) (this.recipeMapWorkable.getProgressPercent() * 100.0D);
                if (this.recipeMapWorkable.getParallelLimit() != 1) {
                    textList.add(new TextComponentTranslation("gregtech.multiblock.parallel", this.recipeMapWorkable.getParallelLimit()));
                }

                textList.add(new TextComponentTranslation("gregtech.multiblock.progress", currentProgress));
            } else {
                textList.add(new TextComponentTranslation("gregtech.multiblock.idling"));
            }

            ///////////Current temperature

            textList.add(new TextComponentTranslation("tekcays_addon.multiblock.tkcya_blast_furnace.tooltip.1", temp));

            ///////////L/t consumption

            if (canAchieveTargetTemp) {

                textList.add(new TextComponentTranslation("tekcays_addon.multiblock.tkcya_blast_furnace.tooltip.4", getTemperatureGasConsumption(temp), inputGasFluidStack.getLocalizedName()));
            } else {
                textList.add(new TextComponentTranslation("tekcays_addon.multiblock.tkcya_blast_furnace.tooltip.5"));
            }

            ///////////Target Temperature

            textList.add(new TextComponentTranslation("tekcays_addon.multiblock.tkcya_blast_furnace.tooltip.6", targetTemp));


            ///////////If not enough input gas

            if (!hasEnoughInputGas(temp + increaseTemp))
                textList.add(new TextComponentTranslation("tekcays_addon.multiblock.tkcya_blast_furnace.tooltip.2")
                        .setStyle(new Style().setColor(TextFormatting.RED)));

            ///////////If not enough input item

            if (!hasEnoughInputItem(temp + increaseTemp))
                textList.add(new TextComponentTranslation("tekcays_addon.multiblock.tkcya_blast_furnace.tooltip.7")
                        .setStyle(new Style().setColor(TextFormatting.RED)));
        }
    }


    protected IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.PRIMITIVE_BRICKS);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.PRIMITIVE_BRICKS;
    }

    @Override
    protected ICubeRenderer getFrontOverlay() {
        return TKCYATextures.ELECTRIC_MELTER_OVERLAY;
    }


    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle("#YYY#", "YXXXY", "YXXXY", "YXXXY", "#YYY#")
                .aisle("#YSY#", "Y###Y", "Y###Y", "Y###Y", "#YYY#")
                .aisle("#YYY#", "Y###Y", "Y#I#Y", "Y###Y", "#YYY#").setRepeatable(1, 11)
                .aisle("#YYY#", "YOOOY", "YOOOY", "YOOOY", "#YYY#")
                .aisle("#####", "#####", "##C##", "#####", "#####").setRepeatable(0, 1)
                .where('S', selfPredicate())
                .where('Y', states(getCasingState()))
                .where('X', states(getCasingState())
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setExactLimit(1))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setExactLimit(1)))
                .where('O', states(getCasingState())
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMaxGlobalLimited(2))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMinGlobalLimited(1,1).setMaxGlobalLimited(2)))
                .where('I', heightIndicatorPredicate())
                .where('C', fluidOutputHatchPredicate())
                .where('#', air())
                .build();

    }


    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @Override
    public void invalidateStructure() {
        setTemp(300);
        setIncreaseTemp();
        setTargetTemp();
        super.invalidateStructure();
        resetTileAbilities();
    }

    // This function is highly useful for detecting the length of this multiblock.
    public static TraceabilityPredicate heightIndicatorPredicate() {
        return new TraceabilityPredicate((blockWorldState) -> {
            if (air().test(blockWorldState)) {
                blockWorldState.getMatchContext().increment("blastFurnaceHeight", 1);
                return true;
            } else
                return false;
        });
    }

    public static TraceabilityPredicate fluidOutputHatchPredicate() {
        return new TraceabilityPredicate((blockWorldState) -> {
            if (abilities(MultiblockAbility.EXPORT_FLUIDS).test(blockWorldState)) {
                blockWorldState.getMatchContext().set("blasFurnaceHasGasOutput", 1);
                return true;
            } else
                return false;
        });
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();
        this.height = context.getOrDefault("blastFurnaceHeight", 1);
        this.hasGasOutputHatchInt = context.getOrDefault("blastFurnaceHasGasOutput", 1);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityTKCYABlastFurnace(metaTileEntityId);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("temp", this.temp);
        data.setInteger("targetTemp", this.targetTemp);
        data.setBoolean("canAchieveTargetTemp", this.canAchieveTargetTemp);
        data.setBoolean("hasEnoughGas", this.hasEnoughGas);
        data.setBoolean("hasEnoughItem", this.hasEnoughItem);
        return data;
    }

    public void setTemp(int temp) {
        this.temp = temp;
        if (!getWorld().isRemote) {
            writeCustomData(600, buf -> buf.writeInt(temp));
            markDirty();
        }
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
        this.canAchieveTargetTemp = data.getBoolean("canAchieveTargetTemp");
        this.hasEnoughGas = data.getBoolean("hasEnoughGas");
        this.hasEnoughItem = data.getBoolean("hasEnoughItem");
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.temp);
        buf.writeInt(this.targetTemp);
        buf.writeBoolean(this.canAchieveTargetTemp);
        buf.writeBoolean(this.hasEnoughGas);
        buf.writeBoolean(this.hasEnoughItem);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.temp = buf.readInt();
        this.targetTemp = buf.readInt();
        this.canAchieveTargetTemp = buf.readBoolean();
        this.hasEnoughGas = buf.readBoolean();
        this.hasEnoughItem= buf.readBoolean();
    }

    @Override
    public int getLightValueForPart(IMultiblockPart sourcePart) {
        return sourcePart == null && temp > 300 ? 15 : 0;
    }


    @Override
    public void initializeAbilities() {
        this.airOrFlueGasImport = new FluidTankList(true, getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        this.coalOrCokeImport = new ItemHandlerList(getAbilities(MultiblockAbility.IMPORT_ITEMS));
        this.outputFluidInventory = new FluidTankList(true, getAbilities(MultiblockAbility.EXPORT_FLUIDS));
    }

    private void resetTileAbilities() {
        this.airOrFlueGasImport = new FluidTankList(true);
        this.coalOrCokeImport = new ItemStackHandler(0);
        this.outputFluidInventory = new FluidTankList(true);
    }


    ////////////////
    //Remove overlocking
    /////////////////

    private static class TKCYABlastFurnaceLogic extends MultiblockNoEnergyRecipeLogic {

        public TKCYABlastFurnaceLogic(RecipeMapMultiblockNoEnergyController tileEntity) {
            super(tileEntity);
        }

        @Override
        public int getParallelLimit() {
            return ((MetaTileEntityTKCYABlastFurnace) this.getMetaTileEntity()).height;
        }

        @Override
        protected int[] calculateOverclock(Recipe recipe) {
            return new int[]{0, recipe.getDuration()};
        }


    }

    ////////////////
    //Added particles/muffler effect if no gas recovery, code from GTCEu's PBF
    /////////////////

    @Override
    public void update() {
        super.update();

        if (isHeating() && !getHasGasOutputHatch()) {
            if (getWorld().isRemote) {
                pollutionParticles();
            }
        }
    }

    private void pollutionParticles() {
        BlockPos pos = this.getPos();
        EnumFacing facing = this.getFrontFacing().getOpposite();
        float xPos = facing.getXOffset() * 0.76F + pos.getX() + 0.5F;
        float yPos = facing.getYOffset() * 0.76F + pos.getY() + 0.25F;
        float zPos = facing.getZOffset() * 0.76F + pos.getZ() + 0.5F;

        float ySpd = facing.getYOffset() * 0.1F + 0.2F + 0.1F * GTValues.RNG.nextFloat();
        runMufflerEffect(xPos, yPos, zPos, 0, ySpd, 0);
    }


}
