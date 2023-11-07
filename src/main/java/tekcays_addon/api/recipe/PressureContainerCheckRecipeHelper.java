package tekcays_addon.api.recipe;

import gregtech.api.recipes.Recipe;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.gtapi.recipes.recipeproperties.*;

import javax.annotation.Nonnull;

import static tekcays_addon.api.utils.Misc.isBetweenInclusive;
import static tekcays_addon.gtapi.consts.TKCYAValues.EMPTY_INT_TWO_ARRAY;

public interface PressureContainerCheckRecipeHelper {

    int getCurrentPressure();
    int getCurrentTemperature();

    /**
     *
     * @return the pressurized {@link FluidStack}
     */
    @Nonnull
    FluidStack getFluidStack();

    default boolean checkRecipeHelper(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        Integer[] pressures = recipe.getProperty(IntervalPressureProperty.getInstance(), EMPTY_INT_TWO_ARRAY);
        Integer[] temperatures = recipe.getProperty(IntervalTemperatureProperty.getInstance(), EMPTY_INT_TWO_ARRAY);

        if (!isBetweenInclusive(pressures[0], pressures[1], getCurrentPressure())) return false;
        if (!isBetweenInclusive(temperatures[0], temperatures[1], getCurrentTemperature())) return false;
        return getFluidStack().isFluidEqual(recipe.getProperty(PressurizedFluidStackProperty.getInstance(), null));
    }
}
