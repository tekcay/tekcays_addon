package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;

public class GasCollectorRecipeHandler {

    public static void init() {
        RecipeMaps.GAS_COLLECTOR_RECIPES.recipeBuilder()
                .notConsumable(OrePrefix.rotor, Materials.Bronze)
                .fluidOutputs(Materials.Air.getFluid(10))
                .EUt(2)
                .duration(20)
                .dimension(0)
                .buildAndRegister();
    }
}
