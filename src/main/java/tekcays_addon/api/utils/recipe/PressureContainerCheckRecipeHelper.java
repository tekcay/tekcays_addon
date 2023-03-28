package tekcays_addon.api.utils.recipe;

import gregtech.api.recipes.Recipe;
import gregtech.api.util.GTUtility;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.recipes.recipeproperties.*;

import javax.annotation.Nonnull;

import static tekcays_addon.api.utils.TKCYAValues.EMPTY_LONG_TWO_ARRAY;

public interface PressureContainerCheckRecipeHelper {

    long getCurrentPressure();
    int getCurrentTemperature();
    FluidStack getFluidStack();

    default boolean checkRecipeHelper(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        Long[] pressures = recipe.getProperty(IntervalPressureProperty.getInstance(), EMPTY_LONG_TWO_ARRAY);

        if (!GTUtility.isBetweenInclusive(pressures[0], pressures[1], getCurrentPressure())) return false;
        if (getCurrentTemperature() < recipe.getProperty(NoCoilTemperatureProperty.getInstance(), 0)) return false;
        return getFluidStack().isFluidEqual(recipe.getProperty(PressurizedFluidStackProperty.getInstance(), null));
    }
}
