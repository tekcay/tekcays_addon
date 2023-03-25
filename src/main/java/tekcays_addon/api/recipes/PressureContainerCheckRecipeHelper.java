package tekcays_addon.api.recipes;

import gregicality.science.api.recipes.recipeproperties.NoCoilTemperatureProperty;
import gregtech.api.recipes.Recipe;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.recipes.recipeproperties.PressurizedFluidStackProperty;
import tekcays_addon.api.recipes.recipeproperties.MaxPressureProperty;
import tekcays_addon.api.recipes.recipeproperties.MinPressureProperty;

import javax.annotation.Nonnull;

public interface PressureContainerCheckRecipeHelper {

    long getCurrentPressure();
    int getCurrentTemperature();
    FluidStack getFluidStack();

    default boolean checkRecipeHelper(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        long currentPressure = getCurrentPressure();
        if (currentPressure < recipe.getProperty(MinPressureProperty.getInstance(), 0)) return false;
        if (currentPressure > recipe.getProperty(MaxPressureProperty.getInstance(), 0)) return false;
        if (getCurrentTemperature() < recipe.getProperty(NoCoilTemperatureProperty.getInstance(), 0)) return false;
        return getFluidStack().isFluidEqual(recipe.getProperty(PressurizedFluidStackProperty.getInstance(), null));
    }
}
