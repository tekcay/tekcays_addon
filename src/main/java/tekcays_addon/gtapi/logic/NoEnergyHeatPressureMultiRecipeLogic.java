package tekcays_addon.gtapi.logic;

import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import lombok.Getter;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.metatileentity.LogicType;
import tekcays_addon.api.recipe.PressureContainerCheckRecipeHelper;
import tekcays_addon.gtapi.capability.containers.IContainerDetector;
import tekcays_addon.gtapi.capability.containers.IHeatContainer;
import tekcays_addon.gtapi.capability.containers.IPressureContainer;

import static tekcays_addon.gtapi.consts.TKCYAValues.ROOM_TEMPERATURE;

public class NoEnergyHeatPressureMultiRecipeLogic extends ModulableLogic implements PressureContainerCheckRecipeHelper {

    @Getter
    protected IPressureContainer pressureContainer;
    @Getter
    protected IHeatContainer heatContainer;
    @Getter
    protected IContainerDetector containerDetector;
    @Getter
    protected int currentTemp;
    @Getter
    protected int currentPressure;
    protected int currentHeat;
    protected int volume;
    protected RecipeMapMultiblockController recipeMapMultiblockController;

    public NoEnergyHeatPressureMultiRecipeLogic(RecipeMapMultiblockController tileEntity) {
        super(tileEntity, LogicType.NO_ENERGY, LogicType.NO_OVERCLOCK);
        this.recipeMapMultiblockController = tileEntity;
    }

    @Override
    public void update() {

        this.pressureContainer = getPressureContainer();
        this.containerDetector = getContainerDetector();
        this.heatContainer = getHeatContainer();

        if (metaTileEntity.getOffsetTimer() % 20 == 0) {
            if (containerDetector != null) containerDetector.setCurrentValue(currentPressure);
        }

        if (pressureContainer == null) return;
        currentPressure = pressureContainer.getPressure();

        if (heatContainer == null) return;
        currentHeat = heatContainer.getHeat();
        actualizeTemperature();
        currentTemp = heatContainer.getTemperature();

        if (super.isWorking()) {
            pressureContainer.changePressurizedFluidStack(pressureContainer.getPressurizedFluidStack(), - getPressurizedFluidStackRate());
        }
    }

    private void actualizeTemperature() {
        heatContainer.setTemperature(ROOM_TEMPERATURE + currentHeat / (20));
    }

    private int getPressurizedFluidStackRate() {
        return pressureContainer.getPressurizedFluidStack().amount * currentPressure;
    }

    @Override
    public int getCurrentTemperature() {
        return 0;
    }


    @Override
    public FluidStack getFluidStack() {
        return pressureContainer.getPressurizedFluidStack();
    }
}
