package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.IngotProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.MetaItems;

import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.ore.OrePrefix.*;

public class HarderRotorsHandler {

    public static void init() {
        rotor.addProcessingHandler(PropertyKey.INGOT, HarderRotorsHandler::processRotor);
    }

    public static void processRotor(OrePrefix rotorPrefix, Material material, IngotProperty property) {

        GTRecipeHandler.removeRecipesByInputs(EXTRUDER_RECIPES, OreDictUnifier.get(ingot, material, 4), MetaItems.SHAPE_EXTRUDER_ROTOR.getStackForm());

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, material, 4)
                .input(ring, material)
                .input(screw, material)
                .output(rotorPrefix, material)
                .duration((int) material.getMass())
                .EUt(24)
                .buildAndRegister();
    }
}
