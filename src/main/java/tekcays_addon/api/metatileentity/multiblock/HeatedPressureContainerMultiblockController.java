package tekcays_addon.api.metatileentity.multiblock;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.capability.*;
import tekcays_addon.api.capability.impl.*;

import java.util.List;

import static tekcays_addon.api.metatileentity.multiblock.TKCYAMultiblockAbility.*;
import static tekcays_addon.api.utils.TKCYAValues.ATMOSPHERIC_PRESSURE;
import static tekcays_addon.api.utils.TKCYAValues.ROOM_TEMPERATURE;

public abstract class HeatedPressureContainerMultiblockController extends RecipeMapMultiblockController implements IPressureMachine, IHeatMachine, IPressureControlMachine {

    protected IPressureContainer pressureContainer;
    protected IHeatContainer heatContainer;
    protected IPressureControl pressureControl;
    protected int volume;
    protected int currentTemp = ROOM_TEMPERATURE;
    protected long currentPressure = ATMOSPHERIC_PRESSURE;
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

        List<IPressureControl> list2 = getAbilities(PRESSURE_CONTROL);
        this.pressureControl = list2.isEmpty() ? null : list2.get(0);
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
                .or(abilities(PRESSURE_CONTROL)).setExactLimit(1);
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
    public IPressureControl getPressureControl() {
        return this.pressureControl;
    }

    protected void updateLogic() {

        this.pressureContainer = getPressureContainer();
        this.pressureControl = getPressureControl();
        this.heatContainer = getHeatContainer();

        if (getOffsetTimer() % 20 == 0) {
            if (pressureControl != null) pressureControl.setPressure(currentPressure);
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
        return (int) (getPressurizedFluidStack().amount * currentPressure);
    }

    private void actualizeTemperature() {
        heatContainer.setTemperature(ROOM_TEMPERATURE + currentHeat / (20));
    }
}
