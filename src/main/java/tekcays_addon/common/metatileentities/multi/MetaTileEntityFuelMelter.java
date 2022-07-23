package tekcays_addon.common.metatileentities.multi;

import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
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
import gregtech.api.util.RelativeDirection;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockFireboxCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.ItemStackHandler;
import tekcays_addon.api.metatileentity.mutiblock.RecipeMapMultiblockNoEnergyController;
import tekcays_addon.api.pattern.TKCYATraceabilityPredicate;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.api.recipes.builders.TemperatureRecipeBuilder;
import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.api.utils.MiscMethods;
import tekcays_addon.api.utils.TKCYALog;

import javax.annotation.Nullable;
import javax.annotation.Nonnull;
import java.util.List;


public class MetaTileEntityFuelMelter extends RecipeMapMultiblockNoEnergyController {


    private int temp, targetTemp, increaseTemp;
    private boolean canAchieveTargetTemp, hasEnoughFuel;
    private FluidTankList fuelFluidImport;

    public MetaTileEntityFuelMelter(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYARecipeMaps.MELTER_RECIPES);
        //this.recipeMapWorkable = new FuelMelterLogic(this);

        temp = 300;
    }

    @Override
    protected void updateFormedValid() {
        super.updateFormedValid();

        hasEnoughFuel = drainFuel();

        if (getOffsetTimer() % 20 == 0 && !recipeMapWorkable.isActive()) {
            stepTowardsTargetTemp(getFuelAmount());
        } else if (temp >= targetTemp) {
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
        return temp >= recipe.getProperty(TemperatureRecipeBuilder.TemperatureProperty.getInstance(), 0);
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

        tooltip.add(I18n.format("tekcays_addon.machine.fuel_melter.tooltip.1"));
        tooltip.add(I18n.format("tekcays_addon.machine.fuel_melter.tooltip.2"));
        tooltip.add(I18n.format("tekcays_addon.machine.fuel_melter.tooltip.3"));
        tooltip.add(I18n.format("tekcays_addon.machine.fuel_melter.tooltip.4"));
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

            textList.add(new TextComponentTranslation("tekcays_addon.multiblock.fuel_melter.tooltip.1", temp));

            ///////////L/t consumption

            if (getFuelAmount() > temperatureFuelCost(this.temp)) {

                textList.add(new TextComponentTranslation("tekcays_addon.multiblock.fuel_melter.tooltip.4", temperatureFuelCost(temp)));
            } else {
                textList.add(new TextComponentTranslation("tekcays_addon.multiblock.fuel_melter.tooltip.4", 0));
            }



            ///////////Target Temperature

            textList.add(new TextComponentTranslation("tekcays_addon.multiblock.fuel_melter.tooltip.5", targetTemp));


            if (!canAchieveTargetTemp && hasEnoughFuel)
                textList.add(new TextComponentTranslation("tekcays_addon.multiblock.fuel_melter.tooltip.2")
                        .setStyle(new Style().setColor(TextFormatting.RED)));


        }
    }


    protected IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.INVAR_HEATPROOF);
    }

    public static TraceabilityPredicate firebox() {
        return TKCYATraceabilityPredicate.FIREBOX_CASINGS.get();
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.HEAT_PROOF_CASING;
    }

    @Override
    protected ICubeRenderer getFrontOverlay() {
        return TKCYATextures.ELECTRIC_MELTER_OVERLAY;
    }


    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RelativeDirection.FRONT, RelativeDirection.UP, RelativeDirection.RIGHT)
                .aisle("FFF", "CCC", "CCC", "XXX")
                .aisle("SFF", "C#C", "C#C", "XMX")
                .aisle("FFF", "CCC", "CCC", "XXX")
                .where('S', selfPredicate())
                .where('X', states(getCasingState()).setMinGlobalLimited(4)
                        .or(autoAbilities(false, true, true, false, false, true, false)))
                //.where('I', abilities(MultiblockAbility.IMPORT_FLUIDS))
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('F', states(getCasingState()).setMinGlobalLimited(7) //firebox())
                                .or(abilities(MultiblockAbility.IMPORT_FLUIDS)))
                .where('C', heatingCoils())
                .where('#', air())
                .build();

    }


    /*
    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        MultiblockShapeInfo.Builder builder = MultiblockShapeInfo.builder()
                .aisle("XEM", "CCC", "CCC", "XXX")
                .aisle("FXD", "C#C", "C#C", "XHX")
                .aisle("ISO", "CCC", "CCC", "XXX")
                .where('X', MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.INVAR_HEATPROOF))
                .where('S', TKCYAMetaTileEntities.ELECTRIC_MELTER, EnumFacing.SOUTH)
                .where('#', Blocks.AIR.getDefaultState())
                .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.MV], EnumFacing.NORTH)
                .where('I', MetaTileEntities.ITEM_IMPORT_BUS[GTValues.LV], EnumFacing.SOUTH)
                .where('O', MetaTileEntities.ITEM_EXPORT_BUS[GTValues.LV], EnumFacing.SOUTH)
                .where('F', MetaTileEntities.FLUID_IMPORT_HATCH[GTValues.LV], EnumFacing.WEST)
                .where('D', MetaTileEntities.FLUID_EXPORT_HATCH[GTValues.LV], EnumFacing.EAST)
                .where('H', MetaTileEntities.MUFFLER_HATCH[GTValues.LV], EnumFacing.UP)
                .where('M', () -> ConfigHolder.machines.enableMaintenance ? MetaTileEntities.MAINTENANCE_HATCH : MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.INVAR_HEATPROOF), EnumFacing.NORTH);
        Arrays.stream(BlockWireCoil.CoilType.values())
                .sorted(Comparator.comparingInt(BlockWireCoil.CoilType::getLevel))
                .forEach(coilType -> shapeInfo.add(builder.where('C', MetaBlocks.WIRE_COIL.getState(coilType)).build()));
        return shapeInfo;
    }


     */
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
        //replaceFireboxAsActive(false);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();
        //replaceFireboxAsActive(true);
        Object coilType = context.get("CoilType");
        //Object fireboxType = context.get("FireboxType");
        if (coilType instanceof BlockWireCoil.CoilType) {
            this.targetTemp = ((BlockWireCoil.CoilType) coilType).getCoilTemperature();
            this.increaseTemp = ((BlockWireCoil.CoilType) coilType).getCoilTemperature() / 200;
        } else {
            this.targetTemp = BlockWireCoil.CoilType.CUPRONICKEL.getCoilTemperature();
            this.increaseTemp = ((BlockWireCoil.CoilType) coilType).getCoilTemperature() / 200;
        }

        /*
        if (fireboxType instanceof BlockFireboxCasing.FireboxCasingType) {
            this.increaseTemp = ((BlockFireboxCasing.FireboxCasingType) fireboxType).hashCode() * 4;
        } else {
            this.increaseTemp = BlockFireboxCasing.FireboxCasingType.BRONZE_FIREBOX.hashCode() * 4;
        }

         */

    }

    public void replaceFireboxAsActive(boolean isActive) {
        BlockPos centerPos = getPos().offset(getFrontFacing().getOpposite()).down();
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                BlockPos blockPos = centerPos.add(x, 0, z); //
                IBlockState blockState = getWorld().getBlockState(blockPos);
                if (blockState.getBlock() instanceof BlockFireboxCasing) {
                    blockState = blockState.withProperty(BlockFireboxCasing.ACTIVE, isActive);
                    getWorld().setBlockState(blockPos, blockState);
                }
            }
        }
    }


    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityFuelMelter(metaTileEntityId);
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

    private boolean isFireboxPart(IMultiblockPart sourcePart) {
        return isStructureFormed() && (((MetaTileEntity) sourcePart).getPos().getY() < getPos().getY());
    }




    private static class FuelMelterLogic extends MultiblockRecipeLogic {

        public FuelMelterLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        protected int[] calculateOverclock(Recipe recipe) {
            return new int[]{0, recipe.getDuration()};
        }


    }


}

