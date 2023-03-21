package tekcays_addon.api.recipes;

import gregicality.science.api.recipes.recipeproperties.NoCoilTemperatureProperty;
import gregicality.science.api.recipes.recipeproperties.PressureProperty;
import gregtech.api.recipes.Recipe;
import gregtech.api.unification.material.Material;
import net.minecraftforge.fluids.Fluid;
import tekcays_addon.api.recipes.recipeproperties.GasProperty;
import tekcays_addon.api.recipes.recipeproperties.MaxPressureProperty;
import tekcays_addon.api.recipes.recipeproperties.MinPressureProperty;
import tekcays_addon.api.utils.IMaterialHelper;
import tekcays_addon.api.utils.MaterialHelper;

import javax.annotation.Nonnull;

import static gregtech.api.unification.material.Materials.Air;

public interface CheckRecipeHelper {

    int getCurrentPressure();
    int getCurrentTemperature();
    Fluid getFluid();
    IMaterialHelper materialHelper = new MaterialHelper();

    default Material getGas() {
        return materialHelper.getMaterial(getFluid());
    }

    default boolean checkRecipeHelper(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        int currentPressure = getCurrentPressure();
        if (currentPressure < recipe.getProperty(MinPressureProperty.getInstance(), 0)) return false;
        if (currentPressure > recipe.getProperty(MaxPressureProperty.getInstance(), 0)) return false;
        if (getCurrentTemperature() < recipe.getProperty(NoCoilTemperatureProperty.getInstance(), 0)) return false;
        return  (!getGas().equals(recipe.getProperty(GasProperty.getInstance(), Air)));
    }
}
