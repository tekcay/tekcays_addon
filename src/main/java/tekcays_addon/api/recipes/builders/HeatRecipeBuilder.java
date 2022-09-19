package tekcays_addon.api.recipes.builders;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.RecipePropertyStorage;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import gregtech.api.util.ValidationResult;
import org.apache.commons.lang3.builder.ToStringBuilder;
import tekcays_addon.api.recipes.recipeproperties.HeatProperty;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
public class HeatRecipeBuilder extends RecipeBuilder<HeatRecipeBuilder> {

    @SuppressWarnings("unused")
    public HeatRecipeBuilder() {

    }

    @SuppressWarnings("unused")
    public HeatRecipeBuilder(Recipe recipe, RecipeMap<HeatRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public HeatRecipeBuilder(HeatRecipeBuilder builder) {
        super(builder);
    }

    @Override
    public HeatRecipeBuilder copy() {
        return new HeatRecipeBuilder(this);
    }

    @Override
    public boolean applyProperty(@Nonnull String key, Object value) {
        if (key.equals(HeatProperty.KEY)) {
            this.HUt(((Number) value).intValue());
            return true;
        }
        return super.applyProperty(key, value);
    }

    @Nonnull
    public HeatRecipeBuilder HUt(int HUt) {
        if (HUt <= 0) {
            GTLog.logger.error("Heat cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(HeatProperty.getInstance(), HUt);
        return this;
    }

    @Override
    public ValidationResult<Recipe> build() {
        if (this.recipePropertyStorage == null) this.recipePropertyStorage = new RecipePropertyStorage();
        if (this.recipePropertyStorage.hasRecipeProperty(HeatProperty.getInstance())) {
            if (this.recipePropertyStorage.getRecipePropertyValue(HeatProperty.getInstance(), 0) <= 0) {
                this.recipePropertyStorage.store(HeatProperty.getInstance(), 0);
            }
        } else {
            this.recipePropertyStorage.store(HeatProperty.getInstance(), 0);
        }
        return super.build();
    }


    public int getHeat() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.getRecipePropertyValue(HeatProperty.getInstance(), 0);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("", getHeat())
                .toString();
    }
}
