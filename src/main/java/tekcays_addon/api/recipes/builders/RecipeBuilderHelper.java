package tekcays_addon.api.recipes.builders;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.recipes.recipeproperties.RecipeProperty;
import gregtech.api.recipes.recipeproperties.RecipePropertyStorage;
import gregtech.api.recipes.recipeproperties.TemperatureProperty;
import gregtech.api.util.EnumValidationResult;
import tekcays_addon.api.recipes.recipeproperties.HeatProperty;
import tekcays_addon.api.recipes.recipeproperties.MaxPressureProperty;
import tekcays_addon.api.recipes.recipeproperties.MinPressureProperty;
import tekcays_addon.api.utils.TKCYALog;

import javax.annotation.Nonnull;
import java.util.List;

public interface RecipeBuilderHelper<T extends RecipeBuilder<T>> {

    IRecipePropertyStorage getRecipePropertyStorage();
    T getRecipeBuilder();
    List<RecipeProperty> getRecipePropertyInstance();
    void setRecipeStatus(EnumValidationResult enumValidationResult);


    default void buildHelper() {
        IRecipePropertyStorage recipePropertyStorage = getRecipePropertyStorage();
        if (recipePropertyStorage == null) recipePropertyStorage = new RecipePropertyStorage();
        for (RecipeProperty recipeProperty : getRecipePropertyInstance()) {
            if (recipePropertyStorage.hasRecipeProperty(recipeProperty)) {
                if (recipePropertyStorage.getRecipePropertyValue(recipeProperty, 0) <= 0) {
                    recipePropertyStorage.store(recipeProperty, 0);
                }
            } else {
                recipePropertyStorage.store(HeatProperty.getInstance(), 0);
            }
        }
    }

    default T validateValue(int value, RecipeProperty recipeProperty) {
        T recipeBuilder =  getRecipeBuilder();
        if (value <= 0) {
            TKCYALog.logger.error(recipeProperty.getKey() + " cannot be less than or equal to 0", new IllegalArgumentException());
            setRecipeStatus(EnumValidationResult.INVALID);
        }
        getRecipeBuilder().applyProperty(recipeProperty, value);
        return recipeBuilder;
    }

    //RecipeProperties
    @Nonnull
    default T temperature (int temperature) {
        return validateValue(temperature, TemperatureProperty.getInstance());
    }

    @Nonnull
    default T minPressure(int minPressure) {
        return validateValue(minPressure, MinPressureProperty.getInstance());
    }

    @Nonnull
    default T maxPressure(int maxPressure) {
        return validateValue(maxPressure, MaxPressureProperty.getInstance());
    }

    //Getters for their values
    default int getTemperature() {
        return getRecipePropertyStorage()== null ? 0 :
                getRecipePropertyStorage().getRecipePropertyValue(TemperatureProperty.getInstance(), 0);
    }

    default int getMinPressure() {
        return getRecipePropertyStorage() == null ? 0 :
                getRecipePropertyStorage().getRecipePropertyValue(MinPressureProperty.getInstance(), 0);
    }

    default int getMaxPressure() {
        return getRecipePropertyStorage() == null ? 0 :
                getRecipePropertyStorage().getRecipePropertyValue(MaxPressureProperty.getInstance(), 0);
    }


    default boolean applyPropertyHelper(@Nonnull String key, Object value) {
        for (RecipeProperty recipeProperty : getRecipePropertyInstance()) {
            if (recipeProperty instanceof TemperatureProperty) {
                if (applyTemperatureHelper(key, value)) return true;
            }
            if (recipeProperty instanceof MinPressureProperty) {
                if (applyMinPressureHelper(key, value)) return true;
            }
            if (recipeProperty instanceof MaxPressureProperty) {
                if (applyMaxPressureHelper(key, value)) return true;
            }
        }
        return false;
    }

    default boolean applyTemperatureHelper(@Nonnull String key, Object value) {
        if (!key.equals(TemperatureProperty.KEY)) return false;
        this.temperature(((Number) value).intValue());
        return true;
    }

    default boolean applyMinPressureHelper(@Nonnull String key, Object value) {
        if (!key.equals(MinPressureProperty.KEY)) return false;
        this.minPressure(((Number) value).intValue());
        return true;
    }

    default boolean applyMaxPressureHelper(@Nonnull String key, Object value) {
        if (!key.equals(MaxPressureProperty.KEY)) return false;
        this.maxPressure(((Number) value).intValue());
        return true;
    }






}
