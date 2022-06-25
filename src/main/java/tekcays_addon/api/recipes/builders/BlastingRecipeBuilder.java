package tekcays_addon.api.recipes.builders;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.PrimitiveProperty;
import gregtech.api.recipes.recipeproperties.RecipeProperty;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import gregtech.api.util.ValidationResult;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.apache.commons.lang3.builder.ToStringBuilder;
import tekcays_addon.api.recipes.recipeproperties.PressureProperty;

public class BlastingRecipeBuilder extends RecipeBuilder<BlastingRecipeBuilder> {

    private int temp, pressure;

    public BlastingRecipeBuilder() {
    }

    public BlastingRecipeBuilder(Recipe recipe, RecipeMap<BlastingRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
        this.temp = recipe.getRecipePropertyStorage().getRecipePropertyValue(TemperatureProperty.getInstance(), 0);
        this.pressure= recipe.getRecipePropertyStorage().getRecipePropertyValue(PressureProperty.getInstance(), 0);
    }

    public BlastingRecipeBuilder(RecipeBuilder<BlastingRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public BlastingRecipeBuilder copy() {
        return new BlastingRecipeBuilder(this);
    }

    public BlastingRecipeBuilder setTemp(int temperature) {
        this.temp = temperature;
        return this;
    }

    public BlastingRecipeBuilder setPressure(int pressure) {
        this.pressure = pressure;
        return this;
    }

    @Override
    public ValidationResult<Recipe> build() {
        this.EUt(1); // Allow parallelization to not / by zero
        Recipe recipe = new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
                duration, EUt, hidden, false);
        if (!recipe.getRecipePropertyStorage().store(TemperatureProperty.getInstance(), temp)
                || !recipe.getRecipePropertyStorage().store(PressureProperty.getInstance(), pressure)
                || !recipe.getRecipePropertyStorage().store(PrimitiveProperty.getInstance(), true)) {
            return ValidationResult.newResult(EnumValidationResult.INVALID, recipe);
        }

        return ValidationResult.newResult(finalizeAndValidate(), recipe);
    }

    @Override
    protected EnumValidationResult finalizeAndValidate() {
        if (this.temp <= 300) {
            GTLog.logger.error("Temperature cannot be less or equal to 300", new IllegalArgumentException());
            this.recipeStatus = EnumValidationResult.INVALID;
        }

        if (this.pressure < 0) {
            GTLog.logger.error("Pressure cannot be negative", new IllegalArgumentException());
            this.recipeStatus = EnumValidationResult.INVALID;
        }

        if (this.duration <= 0) {
            GTLog.logger.error("Duration cannot be less or equal to 0", new IllegalArgumentException());
            this.recipeStatus = EnumValidationResult.INVALID;
        }

        if (this.recipeStatus == EnumValidationResult.INVALID) {
            GTLog.logger.error("Invalid recipe, read the errors above: {}", this);
        }

        return this.recipeStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(TemperatureProperty.getInstance().getKey(), temp)
                .append(PressureProperty.getInstance().getKey(), pressure)
                .toString();
    }

    public static class PressureProperty extends RecipeProperty<Integer> {

        private static final String KEY = "pressure";

        private static PressureProperty INSTANCE;


        protected PressureProperty() {
            super(KEY, Integer.class);
        }

        public static PressureProperty getInstance() {
            if (INSTANCE == null) {
                INSTANCE = new PressureProperty();
            }

            return INSTANCE;
        }

        @Override
        public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
            minecraft.fontRenderer.drawString(I18n.format("tekcays_addon.recipe.blasting_pressure",
                    value), x, y, color);
        }
    }

    public static class TemperatureProperty extends RecipeProperty<Integer> {

        private static final String KEY = "temperature";

        private static TemperatureProperty INSTANCE;


        protected TemperatureProperty() {
            super(KEY, Integer.class);
        }

        public static TemperatureProperty getInstance() {
            if (INSTANCE == null) {
                INSTANCE = new TemperatureProperty();
            }

            return INSTANCE;
        }

        @Override
        public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
            minecraft.fontRenderer.drawString(I18n.format("tekcays_addon.recipe.blasting_temperature",
                    value), x, y, color);
        }
    }
}

