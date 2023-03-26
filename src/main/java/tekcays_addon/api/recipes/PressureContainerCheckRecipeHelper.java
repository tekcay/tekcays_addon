package tekcays_addon.api.recipes;

import gregtech.api.recipes.Recipe;
import gregtech.api.util.GTUtility;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.recipes.recipeproperties.NoCoilTemperatureProperty;
import tekcays_addon.api.recipes.recipeproperties.PressurizedFluidStackProperty;
import tekcays_addon.api.recipes.recipeproperties.MaxPressureProperty;
import tekcays_addon.api.recipes.recipeproperties.MinPressureProperty;
import tekcays_addon.api.utils.TKCYALog;

import javax.annotation.Nonnull;

public interface PressureContainerCheckRecipeHelper {

    long getCurrentPressure();
    int getCurrentTemperature();
    FluidStack getFluidStack();

    default boolean checkRecipeHelper(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        long currentPressure = getCurrentPressure();
        TKCYALog.logger.info("current Pressure is {}, MinPressure is {}, MaxPressure is {}", currentPressure, recipe.getProperty(MinPressureProperty.getInstance(), 0L), recipe.getProperty(MaxPressureProperty.getInstance(), 0L));
        TKCYALog.logger.info("current temperature is {} K, required temperature is {} K", getCurrentTemperature(), recipe.getProperty(NoCoilTemperatureProperty.getInstance(), 0));
        TKCYALog.logger.info("current fluid is {}, required fluid is {}", getFluidStack().getLocalizedName(), recipe.getProperty(PressurizedFluidStackProperty.getInstance(), null).getLocalizedName());
        if (!GTUtility.isBetweenInclusive(
                recipe.getProperty(MinPressureProperty.getInstance(), 0L),
                recipe.getProperty(MaxPressureProperty.getInstance(), 0L),
                currentPressure)) return false;
        if (getCurrentTemperature() < recipe.getProperty(NoCoilTemperatureProperty.getInstance(), 0)) return false;
        return getFluidStack().isFluidEqual(recipe.getProperty(PressurizedFluidStackProperty.getInstance(), null));
    }
}
