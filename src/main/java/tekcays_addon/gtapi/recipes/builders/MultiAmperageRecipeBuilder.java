package tekcays_addon.gtapi.recipes.builders;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.util.EnumValidationResult;
import org.apache.commons.lang3.builder.ToStringBuilder;
import tekcays_addon.api.recipe.RecipeBuilderHelper;
import tekcays_addon.gtapi.recipes.recipeproperties.MultiAmperageProperty;
import tekcays_addon.gtapi.recipes.recipeproperties.VoltageProperty;

import javax.annotation.Nonnull;


public class MultiAmperageRecipeBuilder extends RecipeBuilder<MultiAmperageRecipeBuilder>
        implements RecipeBuilderHelper<MultiAmperageRecipeBuilder> {

    public MultiAmperageRecipeBuilder() {

    }

    @SuppressWarnings(value ="unused")
    public MultiAmperageRecipeBuilder(Recipe recipe, RecipeMap<MultiAmperageRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public MultiAmperageRecipeBuilder(MultiAmperageRecipeBuilder builder) {
        super(builder);
    }

    @Override
    public MultiAmperageRecipeBuilder copy() {
        return new MultiAmperageRecipeBuilder(this);
    }

    @Override
    public boolean applyProperty(@Nonnull String key, Object value) {
        applyPropertyHelper(key, value);
        return super.applyProperty(key, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(VoltageProperty.getInstance().getKey())
                .append(MultiAmperageProperty.getInstance().getKey())
                .toString();
    }

    @Override
    public IRecipePropertyStorage getRecipePropertyStorage() {
        return this.recipePropertyStorage;
    }

    @Override
    public MultiAmperageRecipeBuilder getRecipeBuilder() {
        return this;
    }

    @Override
    public void setRecipeStatus(EnumValidationResult enumValidationResult) {
        recipeStatus = enumValidationResult;
    }

}
