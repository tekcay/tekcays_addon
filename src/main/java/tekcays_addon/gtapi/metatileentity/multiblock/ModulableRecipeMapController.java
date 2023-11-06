package tekcays_addon.gtapi.metatileentity.multiblock;

import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMap;
import net.minecraft.util.ResourceLocation;
import tekcays_addon.api.metatileentity.LogicType;
import tekcays_addon.gtapi.capability.containers.IContainerDetector;
import tekcays_addon.gtapi.capability.containers.IHeatContainer;
import tekcays_addon.gtapi.capability.containers.IPressureContainer;
import tekcays_addon.gtapi.capability.impl.HeatContainer;
import tekcays_addon.gtapi.capability.impl.PressureContainer;
import tekcays_addon.gtapi.capability.machines.IContainerDetectorMachine;
import tekcays_addon.gtapi.capability.machines.IHeatMachine;
import tekcays_addon.gtapi.capability.machines.IPressureMachine;
import tekcays_addon.gtapi.logic.ModulableLogic;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static tekcays_addon.gtapi.consts.TKCYAValues.MAX_PRESSURE;

public abstract class ModulableRecipeMapController extends RecipeMapMultiblockController implements IPressureMachine, IHeatMachine, IContainerDetectorMachine {

    private IHeatContainer heatContainer;
    private IPressureContainer pressureContainer;
    private IContainerDetector containerDetector;
    private List<LogicType> logicTypes;
    boolean checkEnergyIn, checkMaintenance, checkMuffler;

    public ModulableRecipeMapController(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap, LogicType logicType, LogicType... logicTypes) {
        super(metaTileEntityId, recipeMap);
        this.recipeMapWorkable = new ModulableLogic(this, logicTypes);
        setLogicType(logicType, logicTypes);
        this.checkEnergyIn = !this.logicTypes.contains(LogicType.NO_ENERGY);
        this.checkMaintenance = !this.logicTypes.contains(LogicType.NO_MAINTENANCE);
        this.checkMuffler = !this.logicTypes.contains(LogicType.NO_MUFFLER);
    }

    private void setLogicType(LogicType logicType, LogicType... logicTypes) {
        this.logicTypes = new ArrayList<>();
        this.logicTypes.add(logicType);
        Collections.addAll(this.logicTypes, logicTypes);
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

            List<IHeatContainer> list = getAbilities(TKCYAMultiblockAbility.HEAT_CONTAINER);
            if (list.isEmpty()) {
                this.heatContainer = new HeatContainer(this, 0, 2000000);
            } else {
                this.heatContainer = list.get(0);
            }
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
            predicate = predicate.or(abilities(TKCYAMultiblockAbility.PRESSURE_CONTAINER)).setExactLimit(1).setPreviewCount(1);
        }
        if (logicTypes.contains(LogicType.DETECTOR)) {
            predicate = predicate.or(abilities(TKCYAMultiblockAbility.CONTAINER_CONTROL)).setPreviewCount(1);
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
    public IContainerDetector getContainerDetector() {
        if (logicTypes.contains(LogicType.DETECTOR)) return this.containerDetector;
        return null;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        if (logicTypes.contains(LogicType.HEAT)) getHeatContainer().resetContainer();
        if (logicTypes.contains(LogicType.PRESSURE)) getPressureContainer().resetContainer();
    }

}
