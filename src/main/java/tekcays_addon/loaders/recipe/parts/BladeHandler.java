package tekcays_addon.loaders.recipe.parts;

import static gregtech.api.recipes.RecipeMaps.CUTTER_RECIPES;
import static tekcays_addon.gtapi.unification.material.ore.TKCYAOrePrefix.blade;
import static tekcays_addon.gtapi.unification.material.ore.TKCYAOrePrefix.curvedPlate;

import gregtech.api.unification.material.Material;

public class BladeHandler {

    public static void init(Material material) {
        CUTTER_RECIPES.recipeBuilder()
                .input(curvedPlate, material)
                .output(blade, material)
                .duration((int) material.getMass())
                .EUt(24)
                .buildAndRegister();
    }
}
