package tekcays_addon.api.capability.impl;

import gregtech.api.GTValues;
import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.metatileentity.multiblock.ParallelLogicType;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import tekcays_addon.api.metatileentity.mutiblock.RecipeMapNoEnergyMultiblockController;

import static gregtech.api.recipes.logic.OverclockingLogic.standardOverclockingLogic;

import javax.annotation.Nonnull;

/**
 * Recipe Logic for a Multiblock that does not require power.
 */
public class NoEnergyRecipeLogic extends AbstractRecipeLogic {

    public NoEnergyRecipeLogic(RecipeMapNoEnergyMultiblockController tileEntity, RecipeMap<?> recipeMap) {
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
    protected long getMaxVoltage() {
        return GTValues.LV;
    }

    @Override
    protected int[] runOverclockingLogic(IRecipePropertyStorage propertyStorage, int recipeEUt, long maxVoltage, int recipeDuration, int maxOverclocks) {
        return standardOverclockingLogic(1,
                getMaxVoltage(),
                recipeDuration,
                getOverclockingDurationDivisor(),
                getOverclockingVoltageMultiplier(),
                maxOverclocks
        );
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

    //Parallel stuff

    @Override
    public ParallelLogicType getParallelLogicType() {
        return ParallelLogicType.APPEND_ITEMS;
    }

    @Override
    public void applyParallelBonus(@Nonnull RecipeBuilder<?> builder) {
        // the builder automatically multiplies by -1, so nothing extra is needed here
        builder.duration(builder.getDuration() / builder.getParallel());
    }
}
