package tekcays_addon.api.recipes.builders;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.recipeproperties.PrimitiveProperty;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.ValidationResult;
import stanhebben.zenscript.annotations.ZenMethod;

public class MelterRecipeBuilder {

    public class MelterRecipeBuilder extends RecipeBuilder<MelterRecipeBuilder> {
        private int temperature = -1;

        public MelterRecipeBuilder() {
        }

        private MelterRecipeBuilder(MelterRecipeBuilder bakingOvenRecipeBuilder) {
            super(bakingOvenRecipeBuilder);
            this.temperature = bakingOvenRecipeBuilder.getTemperature();
        }

        @ZenMethod
        public static MelterRecipeBuilder start() {
            return new MelterRecipeBuilder();
        }

        @Override
        public MelterRecipeBuilder copy() {
            return new MelterRecipeBuilder(this);
        }

        @Override
        public ValidationResult<Recipe> build() {
            this.EUt(1);
            Recipe recipe = new Recipe(this.inputs, this.outputs, this.chancedOutputs, this.fluidInputs, this.fluidOutputs, this.duration, this.EUt, this.hidden, false);
            return !recipe.setProperty(PrimitiveProperty.getInstance(), true) ? ValidationResult.newResult(EnumValidationResult.INVALID, recipe) : ValidationResult.newResult(this.finalizeAndValidate(), recipe);
        }

        @ZenMethod
        public MelterRecipeBuilder temperature(int temperature) {
            this.temperature = temperature;
            return this;
        }

        @ZenMethod
        public int getTemperature() {
            return temperature;
        }
    }


}
