package tekcays_addon.gtapi.recipes.builders;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import gregtech.api.util.ValidationResult;
import tekcays_addon.api.recipe.RecipeBuilderHelper;
import tekcays_addon.gtapi.recipes.recipeproperties.MinPressureProperty;

@SuppressWarnings("unused")
public class NoEnergyPressureRecipeBuilder extends RecipeBuilder<NoEnergyPressureRecipeBuilder>
                                           implements RecipeBuilderHelper<NoEnergyPressureRecipeBuilder> {

    @SuppressWarnings("unused")
    public NoEnergyPressureRecipeBuilder() {}

    @SuppressWarnings("unused")
    public NoEnergyPressureRecipeBuilder(Recipe recipe, RecipeMap<NoEnergyPressureRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public NoEnergyPressureRecipeBuilder(NoEnergyPressureRecipeBuilder builder) {
        super(builder);
    }

    @Override
    public NoEnergyPressureRecipeBuilder copy() {
        return new NoEnergyPressureRecipeBuilder(this);
    }

    @Override
    public boolean applyProperty(@NotNull String key, Object value) {
        applyPropertyHelper(key, value);
        return super.applyProperty(key, value);
    }

    @NotNull
    public NoEnergyPressureRecipeBuilder pressure(int pressure) {
        if (pressure <= 0) {
            GTLog.logger.error("Pressure cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(MinPressureProperty.getInstance(), pressure);
        return this;
    }

    @Override
    public ValidationResult<Recipe> build() {
        this.EUt = 1; // secretly force to 1 to allow recipe matching to work properly
        return super.build();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                // .appendSuper(super.toString())
                .append(MinPressureProperty.getInstance().getKey())
                .toString();
    }

    // Implementations
    @Override
    public IRecipePropertyStorage getRecipePropertyStorage() {
        return this.recipePropertyStorage;
    }

    @Override
    public NoEnergyPressureRecipeBuilder getRecipeBuilder() {
        return this;
    }

    @Override
    public void setRecipeStatus(EnumValidationResult enumValidationResult) {
        recipeStatus = enumValidationResult;
    }
}
