package tekcays_addon.loaders.recipe.removals;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.GTRecipeInput;
import gregtech.api.unification.material.Materials;

public class TinCircuitRemoval {

    public static void init() {

        for (Recipe recipe : RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.getRecipeList()) {
            for (GTRecipeInput gtRecipeInput : recipe.getFluidInputs()) {
                if (gtRecipeInput.getInputFluidStack().getFluid() == Materials.Tin.getFluid()) {
                    RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.removeRecipe(recipe);
                }
            }
        }
    }
}
