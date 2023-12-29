package tekcays_addon.gtapi.logic;

import gregtech.api.GTValues;
import gregtech.api.capability.IWorkable;
import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.logic.IParallelableRecipeLogic;

public class SingleBlockPrimitiveRecipeLogic extends AbstractRecipeLogic
                                             implements IWorkable, IParallelableRecipeLogic {

    public SingleBlockPrimitiveRecipeLogic(MetaTileEntity tileEntity, RecipeMap<?> recipeMap) {
        super(tileEntity, recipeMap);
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
    public long getMaxVoltage() {
        return GTValues.LV;
    }

    @Override
    public boolean isAllowOverclocking() {
        return false;
    }

    /*
     * @Override
     * protected int[] runOverclockingLogic(RecipePropertyStorage propertyStorage, int recipeEUt, long maxVoltage, int
     * recipeDuration, int maxOverclocks) {
     * return standardOverclockingLogic(1,
     * getMaxVoltage(),
     * recipeDuration,
     * getOverclockingDurationDivisor(),
     * getOverclockingVoltageMultiplier(),
     * maxOverclocks
     * );
     * }
     * 
     */

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
