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

public class ElectricMelterRecipeBuilder extends RecipeBuilder<ElectricMelterRecipeBuilder> {

    private int temp;

    public ElectricMelterRecipeBuilder() {
    }

    public ElectricMelterRecipeBuilder(Recipe recipe, RecipeMap<ElectricMelterRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
        this.temp = recipe.getRecipePropertyStorage().getRecipePropertyValue(TemperatureProperty.getInstance(), 0);
    }

    public ElectricMelterRecipeBuilder(RecipeBuilder<ElectricMelterRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public ElectricMelterRecipeBuilder copy() {
        return new ElectricMelterRecipeBuilder(this);
    }

    public ElectricMelterRecipeBuilder setTemp(int temperature) {
        this.temp = temperature;
        return this;
    }

    @Override
    public ValidationResult<Recipe> build() {
        this.EUt(1); // Allow parallelization to not / by zero
        Recipe recipe = new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
                duration, EUt, hidden, false);
        if (!recipe.getRecipePropertyStorage().store(TemperatureProperty.getInstance(), temp) || !recipe.getRecipePropertyStorage().store(PrimitiveProperty.getInstance(), true)) {
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
                .toString();
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
            minecraft.fontRenderer.drawString(I18n.format("gregtechfoodoption.recipe.baking_oven_temperature",
                    value), x, y, color);
        }
    }
}
