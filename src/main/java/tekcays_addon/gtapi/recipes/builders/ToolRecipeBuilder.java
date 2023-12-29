package tekcays_addon.gtapi.recipes.builders;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.EmptyRecipePropertyStorage;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.recipes.recipeproperties.PrimitiveProperty;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.ValidationResult;
import tekcays_addon.api.recipe.RecipeBuilderHelper;

@SuppressWarnings("unused")
public class ToolRecipeBuilder extends RecipeBuilder<ToolRecipeBuilder>
                               implements RecipeBuilderHelper<ToolRecipeBuilder> {

    @SuppressWarnings("unused")
    public ToolRecipeBuilder() {
        recipePropertyStorage = EmptyRecipePropertyStorage.INSTANCE.copy();
    }

    @SuppressWarnings("unused")
    public ToolRecipeBuilder(Recipe recipe, RecipeMap<ToolRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public ToolRecipeBuilder(ToolRecipeBuilder builder) {
        super(builder);
    }

    @Override
    public ToolRecipeBuilder copy() {
        return new ToolRecipeBuilder(this);
    }

    @Override
    public boolean applyProperty(@NotNull String key, Object value) {
        applyPropertyHelper(key, value);
        return super.applyProperty(key, value);
    }

    @Override
    public ValidationResult<Recipe> build() {
        this.EUt(1); // secretly force to 1 to allow recipe matching to work properly
        this.duration(10);
        applyProperty(PrimitiveProperty.getInstance(), true);
        return super.build();
    }

    @Override
    public IRecipePropertyStorage getRecipePropertyStorage() {
        return this.recipePropertyStorage;
    }

    @Override
    public ToolRecipeBuilder getRecipeBuilder() {
        return this;
    }

    @Override
    public void setRecipeStatus(EnumValidationResult enumValidationResult) {
        recipeStatus = enumValidationResult;
    }
}
