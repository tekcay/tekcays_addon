package tekcays_addon.common.metatileentities.multi;

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
import gregtech.api.recipes.Recipe;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
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
import tekcays_addon.api.utils.MiscMethods;
import tekcays_addon.api.utils.TKCYALog;
import tekcays_addon.common.items.TKCYAMetaItems;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;


import static gregtech.api.util.RelativeDirection.*;
import static tekcays_addon.api.utils.TKCYAValues.*;


public class MetaTileEntityTKCYABlastFurnace extends RecipeMapMultiblockNoEnergyController {


    private boolean canAchieveTargetTemp;
    private boolean hasEnoughItem, hasEnoughGas;
    private boolean hasEnoughFluidHeatingValue, hasEnoughItemHeatingValue;
    private boolean hasAcceptedFluid, hasAcceptedItem, hasBothFluidAndItemFuel;
    private int temp, targetTemp, increaseTemp;
    private int height, hasGasOutputHatchInt;
    private FluidTankList airOrFlueGasImport;
    private IItemHandlerModifiable coalOrCokeImport;
    private int inputItemSlot, itemHeatingValue, fluidHeatingValue;

   private int inputFluidMultiplier, inputItemMultiplier;

    private IFluidTank tankToDrain;

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
        fluidHeatingValue = 0;

        hasAcceptedFluid = false;
        hasAcceptedItem = false;
        hasBothFluidAndItemFuel = false;

        hasEnoughGas = false;
        hasEnoughItem = false;

        hasEnoughFluidHeatingValue = false;
        hasEnoughItemHeatingValue = false;
    }

    @Override
    protected void updateFormedValid() {
        super.updateFormedValid();
        setIncreaseTemp();
        setTargetTemp();

        hasAcceptedFluid = hasAcceptedFluid();
        hasAcceptedItem = hasAcceptedItem();

        if (hasAcceptedFluid) {
            setFluidMultiplier();
            hasEnoughGas = hasEnoughInputGas(temp);
        } else {
            doConvertRemainingGas();
            hasEnoughFluidHeatingValue = hasEnoughFluidHeatingValue(temp);
        }

        if (hasAcceptedItem) {
            setItemMultiplier();
            hasEnoughItem = true;
        } else hasEnoughItemHeatingValue = hasEnoughItemHeatingValue(temp);

        hasBothFluidAndItemFuel = hasBothFluidAndItemFuel();


        TKCYALog.logger.info("itemMultiplier = " + inputItemMultiplier);
        if (inputItemStack != null) {
            TKCYALog.logger.info("inputItemStack = " + inputItemStack.getDisplayName());
        }
        TKCYALog.logger.info("fluidMultiplier = " + inputFluidMultiplier);
        if (tankToDrain.getFluidAmount() != 0) {
            TKCYALog.logger.info("fluidToDrain = " + tankToDrain.getFluid().getLocalizedName());
        }


        ///// Case: No fuel or not enough fuel, and temp can be decreased
        if (temp - increaseTemp >= 300 && !hasBothFluidAndItemFuel) {
            canAchieveTargetTemp = false;
            if (getOffsetTimer() % 20 == 0) {
                setTemp(temp - increaseTemp);
            }
            return;
        }

        ///// Case: temp > targetTemp and has fuel
        if (temp >= targetTemp && hasBothFluidAndItemFuel) {
            canAchieveTargetTemp = true;
            int consum;

            if (getOffsetTimer() % 20 == 0) {

                //Gas
                consum = getTemperatureGasConsumption(temp);
                if (hasEnoughGas) drainGas(temp);
                else fluidHeatingValue -= consum;

                //Item
                consum = getTemperatureItemConsumption(temp);
                if (hasAcceptedItem) drainItem(temp);
                else itemHeatingValue -= consum;
            }
            return;
        }


        ///// Case: temp can be increased
        if (hasBothFluidAndItemFuel) {
            int consum = getTemperatureGasConsumption(temp + increaseTemp);
            canAchieveTargetTemp = true;
            if (getOffsetTimer() % 20 == 0) {
                if (tankToDrain.getFluidAmount() >= consum) drainGas(temp + increaseTemp);
                if (fluidHeatingValue >= consum) fluidHeatingValue -= consum;
                if (tankToDrain.getFluidAmount() + fluidHeatingValue >= consum)

                    drainItem(temp + increaseTemp);
                setTemp(temp + increaseTemp);
            }
        }
    }



    /** //TODO Update
     * Gets the multiplier related to the {@code FluidStack} and the {@code ItemStack} that are present in the
     * corresponding inputs, provided they are considered as fuels.
     * <br /><br />
     *
     */
    public void setFluidMultiplier() {
        getGasCostMap().entrySet().stream()
                .filter(e -> tankToDrain.getFluid().getFluid().equals(e.getKey()))
                .forEach(e -> inputFluidMultiplier = e.getValue());
    }

    public void setItemMultiplier() {
        getItemCostMap().entrySet().stream()
                .filter(e -> inputItemStack.isItemEqual(e.getKey()))
                .forEach(e -> inputItemMultiplier = e.getValue());
    }


    private boolean hasAcceptedFluid() {

        for (IFluidTank fluidTank : airOrFlueGasImport.getFluidTanks()) {

            for (Fluid fluid : getGasCostMap().keySet()) { //ACCEPTED_INPUT_FLUIDS
                if (fluidTank.getFluid().getFluid().equals(fluid)) {
                    tankToDrain = fluidTank;
                    hasAcceptedFluid = true;
                    return true;
                }
            }
        }
        hasAcceptedFluid = false;
        return false;
    }

    private boolean hasAcceptedItem() {

        for (int i = 0;  i < coalOrCokeImport.getSlots(); i++) {
            ItemStack input = coalOrCokeImport.getStackInSlot(i);
            for (ItemStack stack : getItemCostMap().keySet()) {  //ACCEPTED_INPUT_ITEMS
                if (input.isItemEqual(stack)) { //&&  ModHandler.getFuelValue(input) > 0
                    inputItemSlot = i;
                    inputItemStack = stack;
                    hasAcceptedItem = true;
                    return true;
                }
            }
        }
        hasAcceptedItem = false;
        return false;
    }

    public boolean hasBothFluidAndItemFuel() {
        return ((hasAcceptedFluid || hasAcceptedItem)
                || (hasEnoughGas || hasEnoughItem)
                || (hasEnoughFluidHeatingValue || hasEnoughItemHeatingValue));
    }

    private boolean hasGasCollectorItem() {

        for (int i = 0;  i < coalOrCokeImport.getSlots(); i++) {
            ItemStack input = coalOrCokeImport.getStackInSlot(i);
            if (input.isItemEqual((TKCYAMetaItems.GAS_COLLECTOR).getStackForm())) return true;
        }
        return false;
    }


    private boolean hasEnoughInputGas(int temperature) {
        if (!hasAcceptedFluid) return false;
        return tankToDrain.getFluidAmount() >= getTemperatureGasConsumption(temperature);
    }

    private boolean hasEnoughFluidHeatingValue(int temperature) {
        return fluidHeatingValue >= getTemperatureGasConsumption(temperature);
    }

    private boolean hasEnoughItemHeatingValue(int temperature) {
        return itemHeatingValue >= getTemperatureItemConsumption(temperature);
    }

    private void doConvertRemainingGas() {
        int toDrain = tankToDrain.getFluidAmount();
        tankToDrain.drain(toDrain, true);
        fluidHeatingValue += toDrain;
    }


    public int getTemperatureGasConsumption(int temperature) {
        return inputFluidMultiplier * (temperature - 300) * height + 1 ; //TODO formula for consumption
    }

    public int getTemperatureItemConsumption(int temperature) {
        return inputItemMultiplier * (temperature - 300) * height + 1 ; //TODO formula for consumption
    }


    private void drainGas(int temperature) {
        int consum = getTemperatureGasConsumption(temperature);
            airOrFlueGasImport.drain(new FluidStack(tankToDrain.getFluid(), consum), true);
    }
    
    private void drainItem(int temperature) {
        int consum = getTemperatureItemConsumption(temperature);
        if (itemHeatingValue < consum) {
            ItemStack stack = coalOrCokeImport.getStackInSlot(inputItemSlot);
            stack.shrink(1);
            itemHeatingValue += 1000 - consum;
        } else itemHeatingValue -= consum;
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
            textList.add((new TextComponentTranslation("gregtech.multiblock.invalid_structure"))
                    .setStyle((new Style()).setColor(TextFormatting.RED).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, tooltip))));
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

                textList.add(new TextComponentTranslation("tekcays_addon.multiblock.tkcya_blast_furnace.tooltip.4", getTemperatureGasConsumption(temp), tankToDrain.getFluid().getLocalizedName()));
            } else {
                textList.add(new TextComponentTranslation("tekcays_addon.multiblock.tkcya_blast_furnace.tooltip.5"));
            }

            ///////////Target Temperature

            textList.add(new TextComponentTranslation("tekcays_addon.multiblock.tkcya_blast_furnace.tooltip.6", targetTemp));


            ///////////If not enough input gas

            if (!hasEnoughGas)
                textList.add(new TextComponentTranslation("tekcays_addon.multiblock.tkcya_blast_furnace.tooltip.2")
                        .setStyle(new Style().setColor(TextFormatting.RED)));

            ///////////If not enough input item

            if (!hasEnoughItem)
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
        data.setBoolean("hasBothFluidAndItemFuel", this.hasBothFluidAndItemFuel);
        data.setBoolean("hasAcceptedFluid", this.hasAcceptedFluid);
        data.setBoolean("hasAcceptedItem", this.hasAcceptedItem);
        data.setBoolean("hasEnoughFluidHeatingValue", this.hasEnoughFluidHeatingValue);
        data.setBoolean("hasEnoughItemHeatingValue", this.hasEnoughItemHeatingValue);

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

        this.hasBothFluidAndItemFuel = data.getBoolean("hasBothFluidAndItemFuel");
        this.hasAcceptedFluid = data.getBoolean("hasAcceptedFluid");
        this.hasAcceptedItem = data.getBoolean("hasAcceptedItem");
        this.hasEnoughFluidHeatingValue = data.getBoolean("hasEnoughFluidHeatingValue");
        this.hasEnoughItemHeatingValue = data.getBoolean("hasEnoughItemHeatingValue");

    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.temp);

        buf.writeInt(this.targetTemp);
        buf.writeBoolean(this.canAchieveTargetTemp);
        buf.writeBoolean(this.hasEnoughGas);
        buf.writeBoolean(this.hasEnoughItem);
        buf.writeBoolean(this.hasBothFluidAndItemFuel);
        buf.writeBoolean(this.hasAcceptedFluid);
        buf.writeBoolean(this.hasAcceptedItem);
        buf.writeBoolean(this.hasEnoughFluidHeatingValue);
        buf.writeBoolean(this.hasEnoughItemHeatingValue);

    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.temp = buf.readInt();

        this.targetTemp = buf.readInt();
        this.canAchieveTargetTemp = buf.readBoolean();
        this.hasEnoughGas = buf.readBoolean();
        this.hasEnoughItem = buf.readBoolean();

        this.hasBothFluidAndItemFuel = buf.readBoolean();
        this.hasAcceptedFluid = buf.readBoolean();
        this.hasAcceptedItem = buf.readBoolean();
        this.hasEnoughFluidHeatingValue = buf.readBoolean();
        this.hasEnoughItemHeatingValue = buf.readBoolean();


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

    /*
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

     */


}

