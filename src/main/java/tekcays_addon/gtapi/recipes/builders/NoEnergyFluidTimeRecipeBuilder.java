package tekcays_addon.gtapi.recipes.builders;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.recipes.recipeproperties.PrimitiveProperty;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.ValidationResult;
import org.apache.commons.lang3.builder.ToStringBuilder;
import tekcays_addon.api.recipe.RecipeBuilderHelper;
import tekcays_addon.gtapi.recipes.recipeproperties.FluidOutputTimeProperty;

import javax.annotation.Nonnull;

public class NoEnergyFluidTimeRecipeBuilder extends RecipeBuilder<NoEnergyFluidTimeRecipeBuilder> implements RecipeBuilderHelper<NoEnergyFluidTimeRecipeBuilder> {

    public NoEnergyFluidTimeRecipeBuilder() {

    }

    @SuppressWarnings(value ="unused")
    public NoEnergyFluidTimeRecipeBuilder(Recipe recipe, RecipeMap<NoEnergyFluidTimeRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public NoEnergyFluidTimeRecipeBuilder(NoEnergyFluidTimeRecipeBuilder builder) {
        super(builder);
    }

    @Override
    public NoEnergyFluidTimeRecipeBuilder copy() {
        return new NoEnergyFluidTimeRecipeBuilder(this);
    }

    @Override
    public boolean applyProperty(@Nonnull String key, Object value) {
        this.EUt(1);
        applyPropertyHelper(key, value);
        return super.applyProperty(key, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(FluidOutputTimeProperty.getInstance().getKey())
                .toString();
    }

    @Override
    public ValidationResult<Recipe> build() {
        this.EUt = 1; // secretly force to 1 to allow recipe matching to work properly
        this.duration(10);
        applyProperty(PrimitiveProperty.getInstance(), true);
        return super.build();
    }

    @Override
    public IRecipePropertyStorage getRecipePropertyStorage() {
        return this.recipePropertyStorage;
    }

    @Override
    public NoEnergyFluidTimeRecipeBuilder getRecipeBuilder() {
        return this;
    }

    @Override
    public void setRecipeStatus(EnumValidationResult enumValidationResult) {
        recipeStatus = enumValidationResult;
    }

}
