package tekcays_addon.api.recipes.builders;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.recipes.recipeproperties.RecipeProperty;
import gregtech.api.recipes.recipeproperties.RecipePropertyStorage;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTUtility;
import gregtech.api.util.ValidationResult;
import org.apache.commons.lang3.builder.ToStringBuilder;
import tekcays_addon.api.recipes.recipeproperties.MinPressureProperty;
import tekcays_addon.api.recipes.recipeproperties.PressureProperty;
import tekcays_addon.api.utils.recipe.RecipeBuilderHelper;

import javax.annotation.Nonnull;

import java.util.List;

import static tekcays_addon.api.utils.TKCYAValues.ATMOSPHERIC_PRESSURE;
import static tekcays_addon.api.utils.TKCYAValues.MIN_TEMPERATURE_PROPERTY;

@SuppressWarnings("unused")
public class NoEnergyPressureRecipeBuilder extends RecipeBuilder<NoEnergyPressureRecipeBuilder> implements RecipeBuilderHelper<NoEnergyPressureRecipeBuilder> {

    @SuppressWarnings("unused")
    public NoEnergyPressureRecipeBuilder() {

    }

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
    public boolean applyProperty(@Nonnull String key, Object value) {
        if (key.equals(PressureProperty.KEY)) {
            this.pressure(((Number) value).longValue());
            return true;
        }
        return super.applyProperty(key, value);
    }

    @Nonnull
    public NoEnergyPressureRecipeBuilder pressure(long pressure) {
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
        if (this.recipePropertyStorage == null) this.recipePropertyStorage = new RecipePropertyStorage();
        if (this.recipePropertyStorage.hasRecipeProperty(MinPressureProperty.getInstance())) {
            if (this.recipePropertyStorage.getRecipePropertyValue(MinPressureProperty.getInstance(), 0L) <= 0) {
                this.recipePropertyStorage.store(MinPressureProperty.getInstance(), ATMOSPHERIC_PRESSURE);
            }
        } else {
            this.recipePropertyStorage.store(MinPressureProperty.getInstance(), ATMOSPHERIC_PRESSURE);
        }

        return super.build();
    }

    public double getPressure() {
        return this.recipePropertyStorage == null ? 0.0D :
                this.recipePropertyStorage.getRecipePropertyValue(MinPressureProperty.getInstance(), 0L);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(MinPressureProperty.getInstance().getKey())
                .toString();
    }


    //Implementations
    @Override
    public IRecipePropertyStorage getRecipePropertyStorage() {
        return this.recipePropertyStorage;
    }

    @Override
    public NoEnergyPressureRecipeBuilder getRecipeBuilder() {
        return this;
    }

    @Override
    public List<RecipeProperty<?>> getRecipePropertiesInstance() {
        return null;
    }

    @Override
    public void setRecipeStatus(EnumValidationResult enumValidationResult) {
        recipeStatus = enumValidationResult;
    }
}
