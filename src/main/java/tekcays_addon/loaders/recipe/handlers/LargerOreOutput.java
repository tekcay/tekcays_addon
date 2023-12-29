package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;

public class LargerOreOutput {

    /**
     * Makes ores output up to 10 crushed and remove the corresponding GTCEu {@link RecipeMaps#MACERATOR_RECIPES}
     */
    public static void init() {
        OrePrefix.ore.addProcessingHandler(LargerOreOutput::crushOres);
        OrePrefix.ore.addProcessingHandler(LargerOreOutput::removeGTCEuMaceratorRecipes);
    }

    private static void crushOres(OrePrefix ore, Material material) {
        TKCYARecipeMaps.CRUSHING.recipeBuilder()
                .duration(20 * 20)
                .EUt(30)
                .input(ore, material)
                .output(OrePrefix.crushed, material)
                .chancedOutput(OrePrefix.crushed, material, 1, 9000, 0)
                .chancedOutput(OrePrefix.crushed, material, 1, 8000, 0)
                .chancedOutput(OrePrefix.crushed, material, 1, 7000, 0)
                .chancedOutput(OrePrefix.crushed, material, 1, 6000, 0)
                .chancedOutput(OrePrefix.crushed, material, 1, 5000, 0)
                .chancedOutput(OrePrefix.crushed, material, 1, 4000, 0)
                .chancedOutput(OrePrefix.crushed, material, 1, 3000, 0)
                .chancedOutput(OrePrefix.crushed, material, 1, 2000, 0)
                .buildAndRegister();
    }

    private static void removeGTCEuMaceratorRecipes(OrePrefix orePrefix, Material material) {
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.MACERATOR_RECIPES, OreDictUnifier.get(orePrefix, material));
    }
}
