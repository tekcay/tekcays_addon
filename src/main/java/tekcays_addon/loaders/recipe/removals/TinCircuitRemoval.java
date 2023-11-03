package tekcays_addon.loaders.recipe.removals;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import tekcays_addon.api.recipe.RecipeRemovalHelper;

public class TinCircuitRemoval {

    public static void init() {
        RecipeRemovalHelper.removeRecipeByFluidInput(RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES, Materials.Tin);
    }
}
