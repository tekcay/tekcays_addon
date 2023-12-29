package tekcays_addon.gtapi.recipes.builders;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.recipes.recipeproperties.PrimitiveProperty;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.ValidationResult;
import tekcays_addon.api.recipe.RecipeBuilderHelper;
import tekcays_addon.gtapi.recipes.recipeproperties.IntervalPressureProperty;
import tekcays_addon.gtapi.recipes.recipeproperties.IntervalTemperatureProperty;
import tekcays_addon.gtapi.recipes.recipeproperties.PressurizedFluidStackProperty;

public class NoEnergyTemperaturePressureIntervalRecipeBuilder extends
                                                              RecipeBuilder<NoEnergyTemperaturePressureIntervalRecipeBuilder>
                                                              implements
                                                              RecipeBuilderHelper<NoEnergyTemperaturePressureIntervalRecipeBuilder> {

    public NoEnergyTemperaturePressureIntervalRecipeBuilder() {}

    @SuppressWarnings(value = "unused")
    public NoEnergyTemperaturePressureIntervalRecipeBuilder(Recipe recipe,
                                                            RecipeMap<NoEnergyTemperaturePressureIntervalRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public NoEnergyTemperaturePressureIntervalRecipeBuilder(NoEnergyTemperaturePressureIntervalRecipeBuilder builder) {
        super(builder);
    }

    @Override
    public NoEnergyTemperaturePressureIntervalRecipeBuilder copy() {
        return new NoEnergyTemperaturePressureIntervalRecipeBuilder(this);
    }

    @Override
    public boolean applyProperty(@NotNull String key, Object value) {
        applyPropertyHelper(key, value);
        return super.applyProperty(key, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(IntervalPressureProperty.getInstance().getKey())
                .append(IntervalTemperatureProperty.getInstance().getKey())
                .append(PressurizedFluidStackProperty.getInstance().getKey())
                .toString();
    }

    @Override
    public ValidationResult<Recipe> build() {
        this.EUt = 1; // secretly force to 1 to allow recipe matching to work properly
        applyProperty(PrimitiveProperty.getInstance(), true);
        return super.build();
    }

    @Override
    public IRecipePropertyStorage getRecipePropertyStorage() {
        return this.recipePropertyStorage;
    }

    @Override
    public NoEnergyTemperaturePressureIntervalRecipeBuilder getRecipeBuilder() {
        return this;
    }

    @Override
    public void setRecipeStatus(EnumValidationResult enumValidationResult) {
        recipeStatus = enumValidationResult;
    }
}
