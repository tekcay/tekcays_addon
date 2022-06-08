package tekcays_addon.api.recipes.builders;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.recipeproperties.PrimitiveProperty;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.ValidationResult;
import stanhebben.zenscript.annotations.ZenMethod;

public class PrimitiveMelterRecipeBuilder extends RecipeBuilder<PrimitiveMelterRecipeBuilder> {
    private int temperature = -1;

    public PrimitiveMelterRecipeBuilder() {
    }

    private PrimitiveMelterRecipeBuilder(PrimitiveMelterRecipeBuilder bakingOvenRecipeBuilder) {
        super(bakingOvenRecipeBuilder);
        this.temperature = bakingOvenRecipeBuilder.getTemperature();
    }

    @ZenMethod
    public static PrimitiveMelterRecipeBuilder start() {
        return new PrimitiveMelterRecipeBuilder();
    }

    @Override
    public PrimitiveMelterRecipeBuilder copy() {
        return new PrimitiveMelterRecipeBuilder(this);
    }

    @Override
    public ValidationResult<Recipe> build() {
        this.EUt(1);
        Recipe recipe = new Recipe(this.inputs, this.outputs, this.chancedOutputs, this.fluidInputs, this.fluidOutputs, this.duration, this.EUt, this.hidden, false);
        return !recipe.setProperty(PrimitiveProperty.getInstance(), true) ? ValidationResult.newResult(EnumValidationResult.INVALID, recipe) : ValidationResult.newResult(this.finalizeAndValidate(), recipe);
    }

    @ZenMethod
    public PrimitiveMelterRecipeBuilder temperature(int temperature) {
        this.temperature = temperature;
        return this;
    }

    @ZenMethod
    public int getTemperature() {
        return temperature;
    }
}
