package tekcays_addon.api.utils.recipe;

import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.recipes.recipeproperties.*;

import static tekcays_addon.api.utils.TKCYAValues.EMPTY_INT_TWO_ARRAY;
import static tekcays_addon.api.utils.TKCYAValues.EMPTY_LONG_TWO_ARRAY;

public class RecipePropertyGetters {

    private final IRecipePropertyStorage recipePropertyStorage;

    public RecipePropertyGetters(IRecipePropertyStorage recipePropertyStorage) {
        this.recipePropertyStorage = recipePropertyStorage;
    }

    public Long[] getIntervalPressure() {
        return this.recipePropertyStorage == null ? EMPTY_LONG_TWO_ARRAY :
                this.recipePropertyStorage.getRecipePropertyValue(IntervalPressureProperty.getInstance(), EMPTY_LONG_TWO_ARRAY);
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

    public long getMinPressure() {
        return this.recipePropertyStorage == null ? 0L :
                this.recipePropertyStorage.getRecipePropertyValue(MinPressureProperty.getInstance(), 0L);
    }

    public long getMaxPressure() {
        return this.recipePropertyStorage == null ? 0L :
                this.recipePropertyStorage.getRecipePropertyValue(MaxPressureProperty.getInstance(), 0L);
    }

    public FluidStack getGas() {
        return this.recipePropertyStorage == null ? null :
                this.recipePropertyStorage.getRecipePropertyValue(PressurizedFluidStackProperty.getInstance(), null);
    }
}
