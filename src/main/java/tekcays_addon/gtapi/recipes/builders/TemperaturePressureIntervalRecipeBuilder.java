package tekcays_addon.gtapi.recipes.builders;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.util.EnumValidationResult;
import tekcays_addon.api.recipe.RecipeBuilderHelper;
import tekcays_addon.gtapi.recipes.recipeproperties.*;

public class TemperaturePressureIntervalRecipeBuilder extends RecipeBuilder<TemperaturePressureIntervalRecipeBuilder>
                                                      implements
                                                      RecipeBuilderHelper<TemperaturePressureIntervalRecipeBuilder> {

    public TemperaturePressureIntervalRecipeBuilder() {}

    @SuppressWarnings(value = "unused")
    public TemperaturePressureIntervalRecipeBuilder(Recipe recipe,
                                                    RecipeMap<TemperaturePressureIntervalRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public TemperaturePressureIntervalRecipeBuilder(TemperaturePressureIntervalRecipeBuilder builder) {
        super(builder);
    }

    @Override
    public TemperaturePressureIntervalRecipeBuilder copy() {
        return new TemperaturePressureIntervalRecipeBuilder(this);
    }

    @Override
    public boolean applyProperty(@NotNull String key, Object value) {
        applyPropertyHelper(key, value);
        return super.applyProperty(key, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(IntervalPressureProperty.getInstance().getKey())
                .append(IntervalPressureProperty.getInstance().getKey())
                .append(PressurizedFluidStackProperty.getInstance().getKey())
                .toString();
    }

    @Override
    public IRecipePropertyStorage getRecipePropertyStorage() {
        return this.recipePropertyStorage;
    }

    @Override
    public TemperaturePressureIntervalRecipeBuilder getRecipeBuilder() {
        return this;
    }

    @Override
    public void setRecipeStatus(EnumValidationResult enumValidationResult) {
        recipeStatus = enumValidationResult;
    }
}
