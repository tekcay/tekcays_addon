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

public class SeparationFactorRecipeBuilder extends RecipeBuilder<SeparationFactorRecipeBuilder> {

    private int separationFactor;

    public SeparationFactorRecipeBuilder() {
    }

    public SeparationFactorRecipeBuilder(Recipe recipe, RecipeMap<SeparationFactorRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
        this.separationFactor = recipe.getRecipePropertyStorage().getRecipePropertyValue(SeparationFactorProperty.getInstance(), 1);
    }

    public SeparationFactorRecipeBuilder(RecipeBuilder<SeparationFactorRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public SeparationFactorRecipeBuilder copy() {
        return new SeparationFactorRecipeBuilder(this);
    }

    public SeparationFactorRecipeBuilder minSeparationFactor(int separationFactor) {
        this.separationFactor = separationFactor;
        return this;
    }

    @Override
    public ValidationResult<Recipe> build() {
        this.EUt(1); // Allow parallelization to not / by zero
        Recipe recipe = new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
                duration, EUt, hidden, false);
        if (!recipe.getRecipePropertyStorage().store(SeparationFactorProperty.getInstance(), separationFactor) || !recipe.getRecipePropertyStorage().store(PrimitiveProperty.getInstance(), true)) {
            return ValidationResult.newResult(EnumValidationResult.INVALID, recipe);
        }

        return ValidationResult.newResult(finalizeAndValidate(), recipe);
    }


    @Override
    protected EnumValidationResult finalizeAndValidate() {
        if (this.separationFactor <= 0) {
            GTLog.logger.error("SeparationFactor cannot be less or equal to 0", new IllegalArgumentException());
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
                .append(SeparationFactorProperty.getInstance().getKey(), separationFactor)
                .toString();
    }

    public static class SeparationFactorProperty extends RecipeProperty<Integer> {

        private static final String KEY = "separationFactor";

        private static SeparationFactorProperty INSTANCE;


        protected SeparationFactorProperty() {
            super(KEY, Integer.class);
        }

        public static SeparationFactorProperty getInstance() {
            if (INSTANCE == null) {
                INSTANCE = new SeparationFactorProperty();
            }
            return INSTANCE;
        }

        @Override
        public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
            minecraft.fontRenderer.drawString(I18n.format("tkcya.recipe.separationFactor",
                    value), x, y, color);
        }
    }
}

