package tekcays_addon.gtapi.metatileentity.multiblock;


import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.gtapi.capability.containers.IContainerDetector;
import tekcays_addon.gtapi.capability.containers.IHeatContainer;
import tekcays_addon.gtapi.capability.containers.IPressureContainer;
import tekcays_addon.gtapi.capability.list.HeatContainerList;
import tekcays_addon.gtapi.capability.machines.IHeatMachine;
import tekcays_addon.gtapi.capability.machines.IPressureControlMachine;
import tekcays_addon.gtapi.capability.machines.IPressureMachine;

import java.util.List;

import static tekcays_addon.gtapi.consts.TKCYAValues.*;
import static tekcays_addon.gtapi.metatileentity.multiblock.TKCYAMultiblockAbility.*;

public abstract class HeatedPressureContainerMultiblockController extends RecipeMapMultiblockController implements IPressureMachine, IHeatMachine, IPressureControlMachine {

    protected IPressureContainer pressureContainer;
    protected IHeatContainer heatContainer;
    protected IContainerDetector containerDetector;
    protected int volume;
    protected int currentTemp = ROOM_TEMPERATURE;
    protected int currentPressure = ATMOSPHERIC_PRESSURE;
    protected int currentHeat;

    public HeatedPressureContainerMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap) {
        super(metaTileEntityId, recipeMap);
        this.recipeMapWorkable = new MultiblockRecipeLogic(this);
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        this.heatContainer = new HeatContainerList(getAbilities(HEAT_CONTAINER));

        List<IPressureContainer> list = getAbilities(PRESSURE_CONTAINER);
        this.pressureContainer = list.isEmpty() ? null : list.get(0);

        List<IContainerDetector> list2 = getAbilities(CONTAINER_CONTROL);
        this.containerDetector = list2.isEmpty() ? null : list2.get(0);
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
    }

    @Override
    public TraceabilityPredicate autoAbilities(boolean checkEnergyIn, boolean checkMaintenance, boolean checkItemIn, boolean checkItemOut, boolean checkFluidIn, boolean checkFluidOut, boolean checkMuffler) {
        TraceabilityPredicate predicate = super.autoAbilities(checkEnergyIn, checkMaintenance, checkItemIn, checkItemOut, checkFluidIn, checkFluidOut, checkMuffler);
        predicate = predicate
                .or(abilities(PRESSURE_CONTAINER)).setExactLimit(1)
                .or(abilities(CONTAINER_CONTROL)).setExactLimit(1);
        return predicate;
    }

    @Override
    public IPressureContainer getPressureContainer() {
        return this.pressureContainer;
    }

    @Override
    public IHeatContainer getHeatContainer() {
        return this.heatContainer;
    }

    @Override
    public IContainerDetector getPressureControl() {
        return this.containerDetector;
    }

    protected void updateLogic() {

        this.pressureContainer = getPressureContainer();
        this.containerDetector = getPressureControl();
        this.heatContainer = getHeatContainer();

        if (getOffsetTimer() % 20 == 0) {
            if (containerDetector != null) containerDetector.setCurrentValue(currentPressure);
        }

        if (pressureContainer == null) return;
        currentPressure = pressureContainer.getPressure();

        if (heatContainer == null) return;
        currentHeat = heatContainer.getHeat();
        actualizeTemperature();
        currentTemp = heatContainer.getTemperature();

        if (recipeMapWorkable.isWorking()) {
            pressureContainer.changePressurizedFluidStack(getPressurizedFluidStack(), - getPressurizedFluidStackRate());
        }
    }

    private FluidStack getPressurizedFluidStack() {
        return this.pressureContainer.getPressurizedFluidStack();
    }

    //TODO find a formula
    private int getPressurizedFluidStackRate() {
        return getPressurizedFluidStack().amount * currentPressure;
    }

    private void actualizeTemperature() {
        heatContainer.setTemperature(ROOM_TEMPERATURE + currentHeat / (20));
    }
}
