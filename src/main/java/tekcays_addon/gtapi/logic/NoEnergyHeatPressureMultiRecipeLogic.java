package tekcays_addon.gtapi.logic;

import gregtech.api.GTValues;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.recipes.Recipe;
import lombok.Getter;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.recipe.PressureContainerCheckRecipeHelper;
import tekcays_addon.gtapi.capability.containers.IContainerDetector;
import tekcays_addon.gtapi.capability.containers.IHeatContainer;
import tekcays_addon.gtapi.capability.containers.IPressureContainer;

import javax.annotation.Nonnull;

import static tekcays_addon.gtapi.consts.TKCYAValues.ROOM_TEMPERATURE;

public class NoEnergyHeatPressureMultiRecipeLogic extends MultiblockRecipeLogic implements PressureContainerCheckRecipeHelper {

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
        super(tileEntity);
        this.recipeMapMultiblockController = tileEntity;
    }

    @Nonnull
    @Override
    protected int[] calculateOverclock(Recipe recipe) {
        return new int[]{0, recipe.getDuration()};
    }

    @Override
    protected long getEnergyInputPerSecond() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected long getEnergyStored() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected long getEnergyCapacity() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected boolean drawEnergy(int recipeEUt, boolean simulate) {
        return true; // spoof energy being drawn
    }

    @Override
    protected long getMaxVoltage() {
        return 32;
    }

    @Override
    public long getMaximumOverclockVoltage() {
        return GTValues.V[GTValues.LV];
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
