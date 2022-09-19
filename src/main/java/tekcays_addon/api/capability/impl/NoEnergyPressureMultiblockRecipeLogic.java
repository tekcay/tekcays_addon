package tekcays_addon.api.capability.impl;

import gregicality.science.api.GCYSValues;
import gregicality.science.api.capability.IPressureContainer;
import gregicality.science.api.capability.IPressureMachine;
import gregicality.science.api.capability.impl.PressureMultiblockRecipeLogic;
import gregicality.science.api.recipes.recipeproperties.PressureProperty;
import gregtech.api.GTValues;
import gregtech.api.recipes.Recipe;
import tekcays_addon.api.metatileentity.multiblock.NoEnergyPressureMultiblockController;
import tekcays_addon.api.utils.TKCYAValues;


public class NoEnergyPressureMultiblockRecipeLogic extends PressureMultiblockRecipeLogic {

    private double recipePressure = GCYSValues.EARTH_PRESSURE;

    public NoEnergyPressureMultiblockRecipeLogic(NoEnergyPressureMultiblockController tileEntity) {
        super(tileEntity);
    }

    @Override
    protected void updateRecipeProgress() {
        // do not simulate pressure so it keeps growing towards atmospheric
        if (this.canRecipeProgress && drawPressure(this.recipePressure, false)) {
            if (++this.progressTime > this.maxProgressTime) {
                this.completeRecipe();
            }
        }
    }

    @Override
    protected boolean drawPressure(double pressure, boolean simulate) {
        IPressureContainer container = this.getPressureContainer();
        final double containerPressure = container.getPressure();
        double pressureToChange;

        // pressure changes by 1 percent per tick
        if (pressure != GCYSValues.EARTH_PRESSURE) pressureToChange = (double) (TKCYAValues.STEAM_AIR_COMPRESSOR_PRESSURE_INCREASE / 20);
        else return true;

        if (pressure > GCYSValues.EARTH_PRESSURE) pressureToChange = -(double) (TKCYAValues.STEAM_AIR_COMPRESSOR_PRESSURE_INCREASE / 20);

        // P * V = n
        return container.changeParticles(pressureToChange * container.getVolume(), simulate);
    }

    protected IPressureContainer getPressureContainer() {
        return ((IPressureMachine) this.metaTileEntity).getPressureContainer();
    }

    @Override
    protected void setupRecipe(Recipe recipe) {
        super.setupRecipe(recipe);
        if (recipe.getRecipePropertyStorage() != null && recipe.hasProperty(PressureProperty.getInstance())) {
            this.recipePressure = recipe.getProperty(PressureProperty.getInstance(), GCYSValues.EARTH_PRESSURE);
        }
    }
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
}
