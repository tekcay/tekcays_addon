package tekcays_addon.gtapi.logic;

import gregtech.api.GTValues;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.recipes.Recipe;
import tekcays_addon.api.metatileentity.LogicType;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class ModulableLogic extends MultiblockRecipeLogic {

    private final List<LogicType> logicTypes;

    public ModulableLogic(RecipeMapMultiblockController tileEntity, LogicType... logicTypes) {
        super(tileEntity);
        this.logicTypes = Arrays.asList(logicTypes);
    }

    @Nonnull
    @Override
    protected int[] calculateOverclock(@Nonnull Recipe recipe) {
        if (logicTypes.contains(LogicType.NO_OVERCLOCK) || logicTypes.contains(LogicType.NO_ENERGY)) {
            return new int[]{0, recipe.getDuration()};
        }
        return super.calculateOverclock(recipe);
    }

    @Override
    protected long getEnergyInputPerSecond() {
        if (logicTypes.contains(LogicType.NO_ENERGY)) return Integer.MAX_VALUE;
        return super.getEnergyInputPerSecond();
    }

    @Override
    protected long getEnergyStored() {
        if (logicTypes.contains(LogicType.NO_ENERGY)) return Integer.MAX_VALUE;
        return super.getEnergyInputPerSecond();
    }

    @Override
    protected long getEnergyCapacity() {
        if (logicTypes.contains(LogicType.NO_ENERGY)) return Integer.MAX_VALUE;
        return super.getEnergyInputPerSecond();
    }

    @Override
    protected boolean drawEnergy(int recipeEUt, boolean simulate) {
        if (logicTypes.contains(LogicType.NO_ENERGY)) return true;
        return super.drawEnergy(recipeEUt, simulate);
    }

    @Override
    public long getMaxVoltage() {
        if (logicTypes.contains(LogicType.NO_ENERGY)) return 32;
        return super.getMaxVoltage();
    }

    @Override
    public long getMaximumOverclockVoltage() {
        if (logicTypes.contains(LogicType.NO_ENERGY)) return GTValues.V[GTValues.LV];
        return super.getMaximumOverclockVoltage();
    }

    @Override
    public void invalidate() {
            previousRecipe = null;
            progressTime = 0;
            maxProgressTime = 0;
            recipeEUt = 0;
            fluidOutputs = null;
            itemOutputs = null;
            setActive(false);
    }
}
