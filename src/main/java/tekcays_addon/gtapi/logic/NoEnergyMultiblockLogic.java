package tekcays_addon.gtapi.logic;

import gregtech.api.GTValues;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.recipes.Recipe;
import tekcays_addon.gtapi.metatileentity.multiblock.NoEnergyRecipeMapMultiBlockController;


public class NoEnergyMultiblockLogic extends MultiblockRecipeLogic {

    public NoEnergyMultiblockLogic(NoEnergyRecipeMapMultiBlockController tileEntity) {
        super(tileEntity);
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

    /**
     * Used to reset cached values in the Recipe Logic on structure deform
     */
    public void invalidate() {
        previousRecipe = null;
        progressTime = 0;
        maxProgressTime = 0;
        recipeEUt = 0;
        fluidOutputs = null;
        itemOutputs = null;
        setActive(false); // this marks dirty for us
    }




}
