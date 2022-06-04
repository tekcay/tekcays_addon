package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterial;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.DustProperty;
import gregtech.api.unification.material.properties.GemProperty;
import gregtech.api.unification.material.properties.IngotProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.ConfigHolder;
import gregtech.common.items.MetaItems;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.BENDER_RECIPES;
import static gregtech.api.recipes.RecipeMaps.LATHE_RECIPES;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.ore.OrePrefix.*;

public class TKCYAPartsRecipeHandler {

    private TKCYAPartsRecipeHandler() {
    }

    public static void register() {

        OrePrefix.foil.addProcessingHandler(PropertyKey.INGOT, tekcays_addon.loaders.recipe.handlers.TKCYAPartsRecipeHandler::processFoil);

    }

    public static void processFoil(OrePrefix foilPrefix, Material material, IngotProperty property) {
        if (!material.hasFlag(NO_SMASHING))
            ModHandler.addShapedRecipe(String.format("foil_%s", material),
                    OreDictUnifier.get(foilPrefix, material, 2),
                    "hP ", 'P', new UnificationEntry(plate, material));

        RecipeMaps.BENDER_RECIPES.recipeBuilder()
                .input(plate, material)
                .output(foilPrefix, material, 4)
                .duration((int) material.getMass())
                .EUt(24)
                .circuitMeta(1)
                .buildAndRegister();

        if (material.hasFlag(NO_SMASHING)) {
            RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(ingot, material)
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_FOIL)
                    .output(foilPrefix, material, 4)
                    .duration((int) material.getMass())
                    .EUt(24)
                    .buildAndRegister();

            RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(dust, material)
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_FOIL)
                    .output(foilPrefix, material, 4)
                    .duration((int) material.getMass())
                    .EUt(24)
                    .buildAndRegister();
        }
    }

}

