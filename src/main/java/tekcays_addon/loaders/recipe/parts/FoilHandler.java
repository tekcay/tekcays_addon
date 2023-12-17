package tekcays_addon.loaders.recipe.parts;

import gregtech.api.unification.material.Material;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;

import static gregtech.api.unification.ore.OrePrefix.foil;
import static gregtech.api.unification.ore.OrePrefix.plate;

public class FoilHandler {

    public static void process(Material material) {
        TKCYARecipeMaps.CLUSTER_MILL_RECIPES.recipeBuilder()
                .input(plate, material)
                .output(foil, material, 4)
                .duration((int) material.getMass())
                .EUt(24)
                .buildAndRegister();
    }
}
