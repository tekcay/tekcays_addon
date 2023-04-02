package tekcays_addon.api.recipes.builders;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.recipes.recipeproperties.PrimitiveProperty;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.ValidationResult;
import org.apache.commons.lang3.builder.ToStringBuilder;
import tekcays_addon.api.recipes.recipeproperties.IntervalPressureProperty;
import tekcays_addon.api.recipes.recipeproperties.IntervalTemperatureProperty;
import tekcays_addon.api.recipes.recipeproperties.PressurizedFluidStackProperty;
import tekcays_addon.api.utils.recipe.RecipeBuilderHelper;

import javax.annotation.Nonnull;


public class NoEnergyTemperaturePressureIntervalRecipeBuilder extends RecipeBuilder<NoEnergyTemperaturePressureIntervalRecipeBuilder>
implements RecipeBuilderHelper<NoEnergyTemperaturePressureIntervalRecipeBuilder> {

    public NoEnergyTemperaturePressureIntervalRecipeBuilder() {

    }

    @SuppressWarnings(value ="unused")
    public NoEnergyTemperaturePressureIntervalRecipeBuilder(Recipe recipe, RecipeMap<NoEnergyTemperaturePressureIntervalRecipeBuilder> recipeMap) {
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
    public boolean applyProperty(@Nonnull String key, Object value) {
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
