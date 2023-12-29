package tekcays_addon.loaders.recipe.chains;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static tekcays_addon.gtapi.unification.TKCYAMaterials.AcidicScheeliteSolution;
import static tekcays_addon.gtapi.unification.TKCYAMaterials.AcidicTungstateSolution;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Material;
import tekcays_addon.api.material.MaterialHelper;
import tekcays_addon.api.recipe.RecipeRemovalHelper;

public class TungstenChain {

    public static void init() {
        RecipeRemovalHelper.removeRecipeByInput(RecipeMaps.CHEMICAL_BATH_RECIPES, dust, Scheelite);
        RecipeRemovalHelper.removeRecipeByInput(RecipeMaps.CHEMICAL_BATH_RECIPES, dust, Tungstate);

        registerRecipes(Scheelite, HydrochloricAcid, AcidicScheeliteSolution);
        registerRecipes(Tungstate, HydrochloricAcid, AcidicTungstateSolution);
    }

    private static void registerRecipes(Material input, Material bathFluid, Material fluidOutput) {
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(dust, input, MaterialHelper.getInputAmountFromSubComposition(fluidOutput))
                .fluidInputs(bathFluid.getFluid(1000))
                .fluidOutputs(fluidOutput.getFluid(1000))
                .duration((int) input.getMass())
                .EUt(30)
                .buildAndRegister();
    }
}
