package tekcays_addon.common.metatileentities.multi;

import com.google.common.collect.Lists;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.items.metaitem.MetaItem;
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
import gregtech.api.util.RelativeDirection;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.api.recipes.builders.ConvertingRecipeBuilder;
import tekcays_addon.api.render.TKCYATextures;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import static tekcays_addon.api.utils.TKCYAValues.ELECTRIC_PUMPS;

public class MetaTileEntityElectricPressureBlastFurnace extends RecipeMapMultiblockController{

    // Temperature
    private int temp, targetTemp, increaseTemp;
    private boolean canAchieveTargetTemp;
    //GasPressure
    private static int pressureMultiplier = 100; //to make stuff easier
    private int pressure, targetPressure, increasePressure;
    private boolean canAchieveTargetPressure;
    //Energy
    private boolean hasEnoughEnergy;
    private IEnergyContainer energyImport;

    DecimalFormat df = new DecimalFormat("##,#");

    public static String getShowablePressure(int pressure) {
       return BigDecimal.valueOf(pressure).divide(BigDecimal.valueOf(pressureMultiplier), 1, BigDecimal.ROUND_UP).toString();

        //return String.format("%.1f", (float) (pressure/pressureMultiplier));
    }


    public MetaTileEntityElectricPressureBlastFurnace(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYARecipeMaps.CONVERTING_RECIPES);
        this.recipeMapWorkable = new ElectricPressureBlastFurnaceLogic(this);

        temp = 300;
        pressure = 1 * pressureMultiplier;
        targetPressure = 5 * pressureMultiplier;
    }

    private long getEnergy() {
        return energyImport.getEnergyStored();
    }

    @Override
    public IEnergyContainer getEnergyContainer() {
        return energyImport;
    }

    public void setTemp(int temp) {
        this.temp = temp;
        if (!getWorld().isRemote) {
            writeCustomData(600, buf -> buf.writeInt(temp));
            markDirty();
        }
    }

    public void setPressure (int pressure) {
        this.pressure = pressure;
        if (!getWorld().isRemote) {
            writeCustomData(600, buf -> buf.writeFloat(pressure));
            markDirty();
        }
    }

    @Override
    public void updateFormedValid() {
        super.updateFormedValid();

        getTargetPressure();
        getIncreasePressure();

        hasEnoughEnergy = drainEnergy();

        if (getOffsetTimer() % 20 == 0 && !recipeMapWorkable.isActive()) {
            stepTowardsTargetTemp();
            stepTowardsTargetPressure();
        }
        else {
            if (temp >= targetTemp) {
                canAchieveTargetTemp = true;
            }
            if (pressure >= targetPressure) {
                canAchieveTargetPressure = true;
            }
        }
    }

    private void getIncreasePressure(){
        increasePressure = 5;
    }

    private void getTargetPressure(){ //Depends of the pump tier

        boolean containsPump = false;
        for (int slotIndex = 0; slotIndex < getInputInventory().getSlots(); slotIndex++) {
            int tier = 0;
            for (MetaItem.MetaValueItem pump : ELECTRIC_PUMPS) {
                tier++;
                //if (!getInputInventory().isItemValid(slotIndex, pump.getStackForm())) continue;
                if (!getInputInventory().extractItem(slotIndex, 1, true).isItemEqual(pump.getStackForm())) continue;
                targetPressure = tier * 10 * pressureMultiplier;
                containsPump = true;
            }
        }
        if (!containsPump) targetPressure = 500;
    }

    public int temperatureEnergyCost(int temp) {

        if (temp > 300 && temp < 2100) {
            return (int) (0.000042 * Math.pow(temp - 300, 2));
        }
        if (temp < 4000) {
            return (int) (0.000052 * Math.pow(temp - 300, 2));
        }
        if (temp < 6000) {
            return (int) (0.000062 * Math.pow(temp - 300, 2));
        }
        return 0;
    }

    public int pressureEnergyCost(int pressure) {
        return pressure <= 1 * pressureMultiplier ? 0 : (int) (2 * pressure * Math.log(pressure) / pressureMultiplier);
    }

    private void stepTowardsTargetTemp() {
        canAchieveTargetTemp = true;

        if (temp >= targetTemp) return;
        if (getEnergy() >= temperatureEnergyCost(temp + increaseTemp)  && hasEnoughEnergy) {
            setTemp(temp + increaseTemp);
        } else {
            canAchieveTargetTemp = false;
        }
    }

    private void stepTowardsTargetPressure() {
        canAchieveTargetPressure = true;

        if (pressure >= targetPressure) return;
        if (getEnergy() >= pressureEnergyCost(pressure + increasePressure)  && hasEnoughEnergy) {
            setPressure(pressure + increasePressure);

        } else {
            canAchieveTargetPressure = false;
        }
    }

    private boolean drainEnergy() {

        if (getEnergy() != 0 && getEnergy() >= temperatureEnergyCost(temp) + pressureEnergyCost(pressure)) {
            energyImport.removeEnergy(temperatureEnergyCost(temp) + pressureEnergyCost(pressure));
            return true;
        }
        if (temp - increaseTemp < 300) return false; //TODO
        if (pressure - increasePressure < 1) return false;
        if (getOffsetTimer() % 20 == 0) {
            setTemp(temp - increaseTemp);
            setPressure(pressure - increasePressure);
        }

        return false;
    }


    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        return temp >= recipe.getProperty(ConvertingRecipeBuilder.TemperatureProperty.getInstance(), 0)
                & pressure/pressureMultiplier >= recipe.getProperty(ConvertingRecipeBuilder.PressureProperty.getInstance(), 0);
    }


    @Override
    public void initializeAbilities() {
        this.energyImport = new EnergyContainerList(getAbilities(MultiblockAbility.INPUT_ENERGY));
        this.inputInventory = new ItemHandlerList(getAbilities(MultiblockAbility.IMPORT_ITEMS));
        this.outputInventory = new ItemHandlerList(getAbilities(MultiblockAbility.EXPORT_ITEMS));
        this.inputFluidInventory= new FluidTankList(true, getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        this.outputFluidInventory = new FluidTankList(true, getAbilities(MultiblockAbility.EXPORT_FLUIDS));
    }


    public void resetTileAbilities() {
        this.energyImport = new EnergyContainerList(Lists.newArrayList());
        this.inputInventory = new ItemStackHandler(0);
        this.outputInventory = new ItemStackHandler(0);
        this.inputFluidInventory= new FluidTankList(true);
        this.outputFluidInventory = new FluidTankList(true);
    }



    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);

        tooltip.add(I18n.format("tekcays_addon.machine.electric_converter.tooltip.1"));//Only runs recipes if the required temperature is set.
        tooltip.add(I18n.format("tekcays_addon.machine.electric_converter.tooltip.2"));//The §aEU§7 per tick required is exponentially related to the current temperature and pressure.
        tooltip.add(I18n.format("tekcays_addon.machine.electric_converter.tooltip.3"));//The target temperature and the temperature increasing speed are set by the coils.
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

            textList.add(new TextComponentTranslation("tekcays_addon.multiblock.electric_converter.tooltip.1", temp));

            ///////////Current pressure

            textList.add(new TextComponentTranslation("tekcays_addon.multiblock.electric_converter.tooltip.7", getShowablePressure(pressure)));

            ///////////EU/t consumption

            if (getEnergy() > temperatureEnergyCost(this.temp) + pressureEnergyCost(this.pressure)) {

                textList.add(new TextComponentTranslation("tekcays_addon.multiblock.electric_converter.tooltip.4", temperatureEnergyCost(temp) + pressureEnergyCost(pressure)));
            } else {
                textList.add(new TextComponentTranslation("tekcays_addon.multiblock.electric_converter.tooltip.4", 0));
            }

            ///////////Target Temperature

            textList.add(new TextComponentTranslation("tekcays_addon.multiblock.electric_converter.tooltip.5", targetTemp));

            ///////////Target Pressure

            textList.add(new TextComponentTranslation("tekcays_addon.multiblock.electric_converter.tooltip.6", getShowablePressure(targetPressure)));


            if (!canAchieveTargetTemp && hasEnoughEnergy)
                textList.add(new TextComponentTranslation("tekcays_addon.multiblock.electric_converter.tooltip.2")
                        .setStyle(new Style().setColor(TextFormatting.RED)));

        }
    }


    protected IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.INVAR_HEATPROOF);
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
                .aisle("XXX", "CCC", "CCC", "XXX")
                .aisle("SXX", "C#C", "C#C", "XMX")
                .aisle("XXX", "CCC", "CCC", "XXX")
                .where('S', selfPredicate())
                .where('X', states(getCasingState()).setMinGlobalLimited(5)
                        .or(autoAbilities(true, true, true, true, true, true, false)))
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
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
        setPressure(1 * pressureMultiplier);
        targetPressure = 5 * pressureMultiplier;
        super.invalidateStructure();
        resetTileAbilities();
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();
        Object type = context.get("CoilType");
        if (type instanceof BlockWireCoil.CoilType) {
            this.targetTemp = ((BlockWireCoil.CoilType) type).getCoilTemperature();
            this.increaseTemp = (int) (((BlockWireCoil.CoilType) type).getCoilTemperature() / 200);
        } else {
            this.targetTemp = BlockWireCoil.CoilType.CUPRONICKEL.getCoilTemperature();
            this.increaseTemp = (int) ((BlockWireCoil.CoilType.CUPRONICKEL).getCoilTemperature() / 200);
        }

    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityElectricPressureBlastFurnace(metaTileEntityId);
    }



    ////Data

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("temp", this.temp);
        data.setInteger("targetTemp", this.targetTemp);
        data.setBoolean("canAchieveTargetTemp", this.canAchieveTargetTemp);
        data.setInteger("pressure", this.pressure);
        data.setInteger("targetPressure", this.targetPressure);
        data.setBoolean("canAchieveTargetPressure", this.canAchieveTargetPressure);
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
        this.canAchieveTargetTemp = data.getBoolean("canAchieveTargetTemp");
        this.pressure = data.getInteger("pressure");
        this.targetPressure = data.getInteger("targetPressure");
        this.canAchieveTargetPressure= data.getBoolean("canAchieveTargetPressure");
        this.hasEnoughEnergy = data.getBoolean("hasEnoughEnergy");
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.temp);
        buf.writeInt(this.targetTemp);
        buf.writeBoolean(this.canAchieveTargetTemp);
        buf.writeInt(this.pressure);
        buf.writeInt(this.targetPressure);
        buf.writeBoolean(this.canAchieveTargetPressure);
        buf.writeBoolean(this.hasEnoughEnergy);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.temp = buf.readInt();
        this.targetTemp = buf.readInt();
        this.canAchieveTargetTemp = buf.readBoolean();
        this.pressure = buf.readInt();
        this.targetPressure = buf.readInt();
        this.canAchieveTargetPressure = buf.readBoolean();
        this.hasEnoughEnergy = buf.readBoolean();
    }
    @Override
    public int getLightValueForPart(IMultiblockPart sourcePart) {
        return sourcePart == null && temp > 300 ? 15 : 0;
    }
    public boolean canCreateSound() {
        return temp > 300 || pressure > 1 || this.recipeMapWorkable.isActive();
    }
    @Override
    public SoundEvent getSound() {
        return GTSounds.FURNACE;
    }

    /*
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, this.getFrontFacing(), temp > 300, this.recipeMapWorkable.isWorkingEnabled());
    }

     */



    private static class ElectricPressureBlastFurnaceLogic extends MultiblockRecipeLogic {

        public ElectricPressureBlastFurnaceLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        protected int[] calculateOverclock(Recipe recipe) {
            return new int[]{0, recipe.getDuration()};
        }


    }


}

