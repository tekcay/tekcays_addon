package tekcays_addon.api.recipe;

import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.gtapi.recipes.recipeproperties.*;

import static tekcays_addon.gtapi.consts.TKCYAValues.*;

public class RecipePropertyGetters {

    private final IRecipePropertyStorage recipePropertyStorage;

    public RecipePropertyGetters(IRecipePropertyStorage recipePropertyStorage) {
        this.recipePropertyStorage = recipePropertyStorage;
    }

    public Integer[] getIntervalPressure() {
        return this.recipePropertyStorage == null ? EMPTY_INT_TWO_ARRAY :
                this.recipePropertyStorage.getRecipePropertyValue(IntervalPressureProperty.getInstance(), EMPTY_INT_TWO_ARRAY);
    }

    public Integer[] getIntervalTemperature() {
        return this.recipePropertyStorage == null ? EMPTY_INT_TWO_ARRAY :
                this.recipePropertyStorage.getRecipePropertyValue(IntervalTemperatureProperty.getInstance(), EMPTY_INT_TWO_ARRAY);
    }

    public int getMinTemperature() {
        return this.recipePropertyStorage== null ? 0 :
                this.recipePropertyStorage.getRecipePropertyValue(MinTemperatureProperty.getInstance(), 0);
    }

    public int getMaxTemperature() {
        return this.recipePropertyStorage== null ? 0 :
                this.recipePropertyStorage.getRecipePropertyValue(MaxTemperatureProperty.getInstance(), 0);
    }

    public int getMinPressure() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.getRecipePropertyValue(MinPressureProperty.getInstance(), 0);
    }

    public int getMaxPressure() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.getRecipePropertyValue(MaxPressureProperty.getInstance(), 0);
    }

    public FluidStack getPressurizedFluidStack() {
        return this.recipePropertyStorage == null ? null :
                this.recipePropertyStorage.getRecipePropertyValue(PressurizedFluidStackProperty.getInstance(), null);
    }
}
