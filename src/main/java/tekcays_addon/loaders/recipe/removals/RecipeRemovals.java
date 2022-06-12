package tekcays_addon.loaders.recipe.removals;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;

import java.util.List;
import java.util.Map;

import static gregtech.api.recipes.RecipeMaps.BENDER_RECIPES;
import static gregtech.api.recipes.RecipeMaps.POLARIZER_RECIPES;
import static gregtech.api.unification.ore.OrePrefix.plate;
import static tekcays_addon.api.utils.MiscMethods.*;
import static tekcays_addon.api.utils.RegistriesList.*;

public class RecipeRemovals {

    public static void foilRecipeRemovals(){

        List<Material> foilMaterials = FOIL_MATERIALS.list;

        for (Material material : foilMaterials) {
            GTRecipeHandler.removeRecipesByInputs(BENDER_RECIPES, OreDictUnifier.get(plate, material), IntCircuitIngredient.getIntegratedCircuit(1));
        }
    }

    public static void polarizerRecipeRemovals() {


        MAGNETIC_MATERIALS.map.forEach((m, magneticMaterial) -> {

            for (OrePrefix prefix : getMaterialOrePrefixesList(magneticMaterial)) {

                if (!prefix.doGenerateItem(magneticMaterial)) continue;

                GTRecipeHandler.removeRecipesByInputs(POLARIZER_RECIPES, OreDictUnifier.get(prefix, m));

            }
        });
    }

}
