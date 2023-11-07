package tekcays_addon.gtapi.metatileentity.multiblock;

import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMap;
import lombok.Getter;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.metatileentity.LogicType;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static tekcays_addon.gtapi.consts.TKCYAValues.MAX_PRESSURE;
import static tekcays_addon.gtapi.consts.TKCYAValues.ROOM_TEMPERATURE;
import static tekcays_addon.gtapi.metatileentity.multiblock.TKCYAMultiblockAbility.HEAT_CONTAINER;
import static tekcays_addon.gtapi.metatileentity.multiblock.TKCYAMultiblockAbility.ROTATION_CONTAINER;

public abstract class ModulableRecipeMapController extends RecipeMapMultiblockController implements IPressureMachine, IHeatMachine, IRotationMachine, IContainerDetectorMachine, PressureContainerCheckRecipeHelper {

    private IHeatContainer heatContainer;
    private IPressureContainer pressureContainer;
    private IRotationContainer rotationContainer;
    private IContainerDetector containerDetector;
    boolean checkEnergyIn, checkMaintenance, checkMuffler;
    @Getter
    private int currentTemp, currentHeat, currentPressure, volume;
    private final List<LogicType> logicTypes;

    public ModulableRecipeMapController(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap, LogicType logicType, LogicType... logicTypes) {
        super(metaTileEntityId, recipeMap);
        this.recipeMapWorkable = new ModulableLogic(this, logicTypes);
        this.logicTypes = LogicType.setLogicType(logicType, logicTypes);
        this.checkEnergyIn = !this.logicTypes.contains(LogicType.NO_ENERGY);
        this.checkMaintenance = !this.logicTypes.contains(LogicType.NO_MAINTENANCE);
        this.checkMuffler = !this.logicTypes.contains(LogicType.NO_MUFFLER);
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
    public TraceabilityPredicate autoAbilities(boolean checkEnergyIn, boolean checkMaintenance, boolean checkItemIn, boolean checkItemOut, boolean checkFluidIn, boolean checkFluidOut, boolean checkMuffler) {
        TraceabilityPredicate predicate = super.autoAbilities(this.checkEnergyIn, this.checkMaintenance, checkItemIn, checkItemOut, checkFluidIn, checkFluidOut, this.checkMuffler);
        if (logicTypes.contains(LogicType.PRESSURE)) {
            predicate.or(abilities(TKCYAMultiblockAbility.PRESSURE_CONTAINER)).setExactLimit(1).setPreviewCount(1);
        }
        if (logicTypes.contains(LogicType.DETECTOR)) {
            predicate.or(abilities(TKCYAMultiblockAbility.CONTAINER_CONTROL)).setPreviewCount(1);
        }

        if (logicTypes.contains(LogicType.ROTATION)) {
            predicate.or(abilities(ROTATION_CONTAINER).setExactLimit(1).setPreviewCount(1));
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
                pressureContainer.changePressurizedFluidStack(getPressurizedFluidStack(), -getPressurizedFluidStackRate());
            }
        }
    }

    @Override
    public int getCurrentTemperature() {
        return getCurrentTemp();
    }

    @Nonnull
    @Override
    public FluidStack getFluidStack() {
        return getPressureContainer().getPressurizedFluidStack();
    }

    private FluidStack getPressurizedFluidStack() {
        return this.pressureContainer.getPressurizedFluidStack();
    }

    //TODO find a formula
    private int getPressurizedFluidStackRate() {
        return getPressurizedFluidStack().amount * currentPressure;
    }

    protected void actualizeTemperature() {
        getHeatContainer().setTemperature(ROOM_TEMPERATURE + getCurrentHeat() / (20));
    }



}
