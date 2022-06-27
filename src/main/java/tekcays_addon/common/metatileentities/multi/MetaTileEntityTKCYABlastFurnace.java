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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.ItemStackHandler;
import tekcays_addon.api.capability.impl.MultiblockNoEnergyRecipeLogic;
import tekcays_addon.api.metatileentity.mutiblock.RecipeMapMultiblockNoEnergyController;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.api.recipes.builders.MelterRecipeBuilder;
import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.api.utils.MiscMethods;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.util.RelativeDirection.*;


public class MetaTileEntityTKCYABlastFurnace extends RecipeMapMultiblockNoEnergyController {


    private int temp, targetTemp, increaseTemp;
    private boolean canAchieveTargetTemp, hasEnoughFuel;
    private int height;
    private FluidTankList fuelFluidImport;

    public void setIncreaseTemp() {
        increaseTemp = 5;
    }

    public void setTargetTemp() {
        targetTemp = 2000;
    }

    public MetaTileEntityTKCYABlastFurnace(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYARecipeMaps.MELTER_RECIPES);
        this.recipeMapWorkable = new TKCYABlastFurnaceLogic(this);

        temp = 300;
    }

    @Override
    protected void updateFormedValid() {
        super.updateFormedValid();
        setIncreaseTemp();
        setTargetTemp();

        hasEnoughFuel = drainFuel();

        if (getOffsetTimer() % 20 == 0 && !recipeMapWorkable.isActive()) {
            stepTowardsTargetTemp(getFuelAmount());        }
        else if (temp >= targetTemp) {
            canAchieveTargetTemp = true;
        }
    }

    private void stepTowardsTargetTemp(int fuelAmount) {
        canAchieveTargetTemp = true;

        if (temp >= targetTemp) return;
        if (fuelAmount >= temperatureFuelCost(temp + increaseTemp) && hasEnoughFuel) {
            setTemp(temp + increaseTemp);
        } else {
            canAchieveTargetTemp = false;
        }
    }

    public int temperatureFuelCost(int temp) {
        return temp <= 300 ? 0 : 1; //(int) Math.exp(((double) temp - 100) / 300)
    }

    private boolean drainFuel() {

        if (getFuelAmount() >= temperatureFuelCost(this.temp)) {
            fuelFluidImport.drain(temperatureFuelCost(this.temp), true);
            return true;
        }

        if (temp - increaseTemp < 300) return false;
        if (getOffsetTimer() % 20 == 0) {
            setTemp(temp - increaseTemp);
        }

        return false;
    }


    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        return temp >= recipe.getProperty(MelterRecipeBuilder.TemperatureProperty.getInstance(), 0);
    }


    private int getFuelAmount() {

        int fuelAmount = 0;
        for (IFluidTank fluidTank : fuelFluidImport.getFluidTanks()) {

            FluidStack fuelStack = fluidTank.drain(Integer.MAX_VALUE, false);
            if (!MiscMethods.isFuel(fuelStack)) continue;
            fuelAmount += fuelStack.amount;
        }

        return fuelAmount;
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


            if (this.recipeMapWorkable.isHasNotEnoughEnergy()) {
                textList.add((new TextComponentTranslation("gregtech.multiblock.not_enough_energy")).setStyle((new Style()).setColor(TextFormatting.RED)));
            }

            ///////////Current temperature

            textList.add(new TextComponentTranslation("tekcays_addon.multiblock.tkcya_blast_furnace.tooltip.1", temp));

            ///////////L/t consumption

            if (getFuelAmount() > temperatureFuelCost(this.temp)) {

                textList.add(new TextComponentTranslation("tekcays_addon.multiblock.tkcya_blast_furnace.tooltip.4", temperatureFuelCost(temp)));
            } else {
                textList.add(new TextComponentTranslation("tekcays_addon.multiblock.tkcya_blast_furnace.tooltip.4", 0));
            }



            ///////////Target Temperature

            textList.add(new TextComponentTranslation("tekcays_addon.multiblock.tkcya_blast_furnace.tooltip.5", targetTemp));


            if (!canAchieveTargetTemp && hasEnoughFuel)
                textList.add(new TextComponentTranslation("tekcays_addon.multiblock.tkcya_blast_furnace.tooltip.2")
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
                .aisle("#SY#", "YXXY", "YXXY", "#YY#")
                .aisle("#YY#", "Y##Y", "Y#IY", "#YY#").setRepeatable(1, 11)
                .aisle("#YY#", "YOOY", "YOOY", "#YY#")
                .where('S', selfPredicate())
                .where('Y', states(getCasingState()))
                .where('X', states(getCasingState())
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setExactLimit(1)))
                .where('O', states(getCasingState())
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setExactLimit(2))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setExactLimit(2)))
                .where('I', isIndicatorPredicate())
                .where('#', air())
                .build();

    }


    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return true;
    }

    @Override
    public void invalidateStructure() {
        setTemp(300);
        super.invalidateStructure();
        resetTileAbilities();
    }

    // This function is highly useful for detecting the length of this multiblock.
    public static TraceabilityPredicate isIndicatorPredicate() {
        return new TraceabilityPredicate((blockWorldState) -> {
            if (air().test(blockWorldState)) {
                blockWorldState.getMatchContext().increment("blastFurnaceHeight", 1);
                return true;
            } else
                return false;
        });
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();
        this.height = context.getOrDefault("blastFurnaceHeight", 1) - 1;
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
        data.setBoolean("hasEnoughFuel", this.hasEnoughFuel);
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
        this.hasEnoughFuel = data.getBoolean("hasEnoughFuel");
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.temp);
        buf.writeInt(this.targetTemp);
        buf.writeBoolean(this.canAchieveTargetTemp);
        buf.writeBoolean(this.hasEnoughFuel);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.temp = buf.readInt();
        this.targetTemp = buf.readInt();
        this.canAchieveTargetTemp = buf.readBoolean();
        this.hasEnoughFuel = buf.readBoolean();
    }

    @Override
    public int getLightValueForPart(IMultiblockPart sourcePart) {
        return sourcePart == null && temp > 300 ? 15 : 0;
    }

    public boolean canCreateSound() {
        return temp > 300 && this.recipeMapWorkable.isWorkingEnabled();
    }

    @Override
    public void initializeAbilities() {
        this.fuelFluidImport = new FluidTankList(true, getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        this.inputInventory = new ItemHandlerList(getAbilities(MultiblockAbility.IMPORT_ITEMS));
        this.outputFluidInventory = new FluidTankList(true, getAbilities(MultiblockAbility.EXPORT_FLUIDS));
    }

    private void resetTileAbilities() {
        this.fuelFluidImport = new FluidTankList(true);
        this.inputInventory = new ItemStackHandler(0);
        this.outputFluidInventory = new FluidTankList(true);
    }


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


}

