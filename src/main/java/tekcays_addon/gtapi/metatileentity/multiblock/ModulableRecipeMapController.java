package tekcays_addon.gtapi.metatileentity.multiblock;

import static tekcays_addon.api.metatileentity.MultiAmperageControllerMethods.areAllEnergyHatchesTheSameVoltage;
import static tekcays_addon.gtapi.consts.TKCYAValues.MAX_PRESSURE;
import static tekcays_addon.gtapi.consts.TKCYAValues.ROOM_TEMPERATURE;
import static tekcays_addon.gtapi.metatileentity.multiblock.TKCYAMultiblockAbility.HEAT_CONTAINER;
import static tekcays_addon.gtapi.metatileentity.multiblock.TKCYAMultiblockAbility.ROTATION_CONTAINER;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import lombok.Getter;
import tekcays_addon.api.metatileentity.LogicType;
import tekcays_addon.api.metatileentity.MultiAmperageControllerMethods;
import tekcays_addon.api.recipe.PressureContainerCheckRecipeHelper;
import tekcays_addon.gtapi.capability.containers.IContainerDetector;
import tekcays_addon.gtapi.capability.containers.IHeatContainer;
import tekcays_addon.gtapi.capability.containers.IPressureContainer;
import tekcays_addon.gtapi.capability.containers.IRotationContainer;
import tekcays_addon.gtapi.capability.impl.PressureContainer;
import tekcays_addon.gtapi.capability.list.HeatContainerList;
import tekcays_addon.gtapi.capability.machines.IContainerDetectorMachine;
import tekcays_addon.gtapi.capability.machines.IHeatMachine;
import tekcays_addon.gtapi.capability.machines.IPressureMachine;
import tekcays_addon.gtapi.capability.machines.IRotationMachine;
import tekcays_addon.gtapi.logic.ModulableLogic;

public abstract class ModulableRecipeMapController extends RecipeMapMultiblockController
                                                   implements IPressureMachine, IHeatMachine, IRotationMachine,
                                                   IContainerDetectorMachine, PressureContainerCheckRecipeHelper {

    private IHeatContainer heatContainer;
    private IPressureContainer pressureContainer;
    private IRotationContainer rotationContainer;
    private IContainerDetector containerDetector;
    boolean checkEnergyIn, checkMaintenance, checkMuffler;
    protected List<IEnergyContainer> inputEnergyHatches;
    protected boolean areAllEnergyHatchesTheSameVoltage = false;
    @Getter
    private int currentTemp, currentHeat, currentPressure, volumeContainer;
    private final List<LogicType> logicTypes;

    public ModulableRecipeMapController(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap, LogicType logicType,
                                        LogicType... logicTypes) {
        super(metaTileEntityId, recipeMap);
        this.recipeMapWorkable = new ModulableLogic(this, logicTypes);
        this.logicTypes = LogicType.setLogicType(logicType, logicTypes);
        this.checkEnergyIn = !this.logicTypes.contains(LogicType.NO_ENERGY);
        this.checkMaintenance = !this.logicTypes.contains(LogicType.NO_MAINTENANCE);
        this.checkMuffler = !this.logicTypes.contains(LogicType.NO_MUFFLER);
        if (this.logicTypes.contains(LogicType.MULTI_AMPER)) this.inputEnergyHatches = new ArrayList<>();
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();

        if (logicTypes.contains(LogicType.PRESSURE)) {
            List<IPressureContainer> list = getAbilities(TKCYAMultiblockAbility.PRESSURE_CONTAINER);
            if (list.isEmpty()) {
                this.pressureContainer = new PressureContainer(this, 0, MAX_PRESSURE);
            } else {
                this.pressureContainer = list.get(0);
            }
        }

        if (logicTypes.contains(LogicType.HEAT)) {
            this.heatContainer = new HeatContainerList(getAbilities(HEAT_CONTAINER));
        }

        if (logicTypes.contains(LogicType.ROTATION)) {
            List<IRotationContainer> list = getAbilities(ROTATION_CONTAINER);
            this.rotationContainer = list.isEmpty() ? null : list.get(0);
        }
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return checkMaintenance;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return checkMuffler;
    }

    @Override
    public TraceabilityPredicate autoAbilities() {
        return autoAbilities(checkEnergyIn, checkMaintenance, true, true, true, true, checkMuffler);
    }

    @Override
    public TraceabilityPredicate autoAbilities(boolean checkEnergyIn, boolean checkMaintenance, boolean checkItemIn,
                                               boolean checkItemOut, boolean checkFluidIn, boolean checkFluidOut,
                                               boolean checkMuffler) {
        TraceabilityPredicate predicate = super.autoAbilities(this.checkEnergyIn, this.checkMaintenance, checkItemIn,
                checkItemOut, checkFluidIn, checkFluidOut, this.checkMuffler);
        if (logicTypes.contains(LogicType.PRESSURE)) {
            predicate = predicate.or(abilities(TKCYAMultiblockAbility.PRESSURE_CONTAINER)).setExactLimit(1)
                    .setPreviewCount(1);
        }
        if (logicTypes.contains(LogicType.DETECTOR)) {
            predicate = predicate.or(abilities(TKCYAMultiblockAbility.CONTAINER_CONTROL)).setPreviewCount(1);
        }

        if (logicTypes.contains(LogicType.ROTATION)) {
            predicate = predicate.or(abilities(ROTATION_CONTAINER).setExactLimit(1).setPreviewCount(1));
        }
        return predicate;
    }

    @Nullable
    @Override
    public IPressureContainer getPressureContainer() {
        if (logicTypes.contains(LogicType.PRESSURE)) return this.pressureContainer;
        return null;
    }

    @Nullable
    @Override
    public IHeatContainer getHeatContainer() {
        if (logicTypes.contains(LogicType.HEAT)) return this.heatContainer;
        return null;
    }

    @Nullable
    @Override
    public IRotationContainer getRotationContainer() {
        if (logicTypes.contains(LogicType.ROTATION)) return this.rotationContainer;
        return null;
    }

    @Nullable
    @Override
    public IContainerDetector getContainerDetector() {
        if (logicTypes.contains(LogicType.DETECTOR)) return this.containerDetector;
        return null;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        if (logicTypes.contains(LogicType.MULTI_AMPER)) {
            inputEnergyHatches = this.getAbilities(MultiblockAbility.INPUT_ENERGY);
        }
    }

    @Override
    public boolean isStructureFormed() {
        if (logicTypes.contains(LogicType.MULTI_AMPER) && !inputEnergyHatches.isEmpty()) {
            areAllEnergyHatchesTheSameVoltage = areAllEnergyHatchesTheSameVoltage(inputEnergyHatches);
            return super.isStructureFormed() && areAllEnergyHatchesTheSameVoltage;
        }
        return super.isStructureFormed();
    }

    @Override
    public void updateFormedValid() {
        super.updateFormedValid();

        if (logicTypes.contains(LogicType.HEAT) && heatContainer != null) {
            currentHeat = heatContainer.getHeat();
            actualizeTemperature();
            currentTemp = heatContainer.getTemperature();
        }

        if (logicTypes.contains(LogicType.PRESSURE) && pressureContainer != null) {
            currentPressure = pressureContainer.getPressure();
            if (logicTypes.contains(LogicType.DETECTOR) && getOffsetTimer() % 20 == 0) {
                containerDetector.setCurrentValue(currentPressure);
            }
            if (recipeMapWorkable.isWorking()) {
                pressureContainer.changePressurizedFluidStack(getPressurizedFluidStack(),
                        -getPressurizedFluidStackRate());
            }
        }
    }

    @Override
    public int getCurrentTemperature() {
        return getCurrentTemp();
    }

    @NotNull
    @Override
    public FluidStack getFluidStack() {
        return getPressureContainer().getPressurizedFluidStack();
    }

    private FluidStack getPressurizedFluidStack() {
        return this.pressureContainer.getPressurizedFluidStack();
    }

    // TODO find a formula
    private int getPressurizedFluidStackRate() {
        return getPressurizedFluidStack().amount * currentPressure;
    }

    protected void actualizeTemperature() {
        getHeatContainer().setTemperature(ROOM_TEMPERATURE + getCurrentHeat() / (20));
    }

    @Override
    public boolean checkRecipe(@NotNull Recipe recipe, boolean consumeIfSuccess) {
        if (logicTypes.contains(LogicType.MULTI_AMPER)) {
            return MultiAmperageControllerMethods.multiAmperageRecipeChecker(recipe, inputEnergyHatches,
                    areAllEnergyHatchesTheSameVoltage);
        }
        return super.checkRecipe(recipe, consumeIfSuccess);
    }

    // implementations

    @Override
    public void dropAllCovers() {
        super.dropAllCovers();
    }

    @Override
    public void dropCover(@NotNull EnumFacing side) {
        super.dropCover(side);
    }

    @Override
    public void updateCovers() {
        super.updateCovers();
    }

    @Override
    public void renderCovers(@NotNull CCRenderState renderState, @NotNull Matrix4 translation,
                             @NotNull BlockRenderLayer layer) {
        super.renderCovers(renderState, translation, layer);
    }

    @Override
    public void addCoverCollisionBoundingBox(@NotNull List<? super IndexedCuboid6> collisionList) {
        super.addCoverCollisionBoundingBox(collisionList);
    }

    @Override
    public boolean hasCapability(@NotNull Capability<?> capability,
                                 @org.jetbrains.annotations.Nullable EnumFacing facing) {
        return super.hasCapability(capability, facing);
    }

    @Override
    public boolean hasCover(@NotNull EnumFacing side) {
        return super.hasCover(side);
    }

    @Override
    public int getItemOutputLimit() {
        return super.getItemOutputLimit();
    }

    @Override
    public int getFluidOutputLimit() {
        return super.getFluidOutputLimit();
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return super.getBreakdownSound();
    }
}
