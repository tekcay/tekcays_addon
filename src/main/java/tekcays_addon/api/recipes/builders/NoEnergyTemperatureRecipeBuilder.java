package tekcays_addon.api.recipes.builders;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.PrimitiveProperty;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTUtility;
import gregtech.api.util.ValidationResult;
import org.apache.commons.lang3.builder.ToStringBuilder;
import tekcays_addon.api.recipes.recipeproperties.NoEnergyTemperatureProperty;

import javax.annotation.Nonnull;

public class NoEnergyTemperatureRecipeBuilder extends RecipeBuilder<NoEnergyTemperatureRecipeBuilder> {

    public NoEnergyTemperatureRecipeBuilder() {
    }

    public NoEnergyTemperatureRecipeBuilder(Recipe recipe, RecipeMap<NoEnergyTemperatureRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
     }

    public NoEnergyTemperatureRecipeBuilder(RecipeBuilder<NoEnergyTemperatureRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public ValidationResult<Recipe> build() {
        this.EUt(1); // secretly force to 1 to allow recipe matching to work properly
        this.applyProperty(PrimitiveProperty.getInstance(), true);
        return super.build();
    }


    @Override
    public NoEnergyTemperatureRecipeBuilder copy() {
        return new NoEnergyTemperatureRecipeBuilder(this);
    }

    @Nonnull
    public NoEnergyTemperatureRecipeBuilder temperature(int temperature) {
        this.applyProperty(NoEnergyTemperatureProperty.getInstance(), temperature);
        return this;
    }

    @Override
    public boolean applyProperty(@Nonnull String key, Object value) {
        if (key.equals(NoEnergyTemperatureProperty.KEY)) {
            this.temperature(((Number) value).intValue());
            return true;
        }
        return super.applyProperty(key, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(NoEnergyTemperatureProperty.getInstance().getKey(), GTUtility.formatNumbers(getTemperature()))
                .toString();
    }

    public int getTemperature() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.getRecipePropertyValue(NoEnergyTemperatureProperty.getInstance(), 0);
    }
}

