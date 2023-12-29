package tekcays_addon.gtapi.recipes.builders;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.RecipePropertyStorage;
import gregtech.api.recipes.recipeproperties.TemperatureProperty;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import gregtech.api.util.ValidationResult;
import tekcays_addon.gtapi.recipes.recipeproperties.HeatProperty;
import tekcays_addon.gtapi.recipes.recipeproperties.PressureProperty;

@SuppressWarnings("unused")
public class HeatAndPressureRecipeBuilder extends RecipeBuilder<HeatAndPressureRecipeBuilder> {

    @SuppressWarnings("unused")
    public HeatAndPressureRecipeBuilder() {}

    @SuppressWarnings("unused")
    public HeatAndPressureRecipeBuilder(Recipe recipe, RecipeMap<HeatAndPressureRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public HeatAndPressureRecipeBuilder(HeatAndPressureRecipeBuilder builder) {
        super(builder);
    }

    @Override
    public HeatAndPressureRecipeBuilder copy() {
        return new HeatAndPressureRecipeBuilder(this);
    }

    @Override
    public boolean applyProperty(@NotNull String key, Object value) {
        if (key.equals(TemperatureProperty.KEY)) {
            this.temperature(((Number) value).intValue());
            return true;
        }
        if (key.equals(PressureProperty.KEY)) {
            this.pressure(((Number) value).intValue());
            return true;
        }
        return super.applyProperty(key, value);
    }

    @NotNull
    public HeatAndPressureRecipeBuilder temperature(int temperature) {
        if (temperature <= 0) {
            GTLog.logger.error("Temperature cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(TemperatureProperty.getInstance(), temperature);
        return this;
    }

    @NotNull
    public HeatAndPressureRecipeBuilder pressure(int pressure) {
        if (pressure <= 0) {
            GTLog.logger.error("Pressure cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(PressureProperty.getInstance(), pressure);
        return this;
    }

    @Override
    public ValidationResult<Recipe> build() {
        if (this.recipePropertyStorage == null) this.recipePropertyStorage = new RecipePropertyStorage();
        if (this.recipePropertyStorage.hasRecipeProperty(TemperatureProperty.getInstance())) {
            if (this.recipePropertyStorage.getRecipePropertyValue(TemperatureProperty.getInstance(), 0) <= 0) {
                this.recipePropertyStorage.store(TemperatureProperty.getInstance(), 0);
            }
        } else {
            this.recipePropertyStorage.store(HeatProperty.getInstance(), 0);
        }

        if (this.recipePropertyStorage.hasRecipeProperty(PressureProperty.getInstance())) {
            if (this.recipePropertyStorage.getRecipePropertyValue(PressureProperty.getInstance(), 0) <= 0) {
                this.recipePropertyStorage.store(PressureProperty.getInstance(), 0);
            }
        } else {
            this.recipePropertyStorage.store(PressureProperty.getInstance(), 0);
        }
        return super.build();
    }

    public int getTemperature() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.getRecipePropertyValue(TemperatureProperty.getInstance(), 0);
    }

    public int getPressure() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.getRecipePropertyValue(PressureProperty.getInstance(), 0);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("Temperature", getTemperature())
                .append("Pressure", getPressure())
                .toString();
    }
}
