package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.GregTechAPI;
import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.items.MetaItems;

import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static tekcays_addon.gtapi.unification.material.ore.TKCYAOrePrefix.blade;
import static tekcays_addon.gtapi.unification.material.ore.TKCYAOrePrefix.curvedPlate;

public class HarderRotorsHandler {

    public static void init() {

        for (Material material : GregTechAPI.materialManager.getRegisteredMaterials()) {
            if (material.hasAnyOfFlags(MaterialFlags.GENERATE_ROUND, MaterialFlags.GENERATE_ROTOR)) {
                processCurvedPlate(material);
                processBlade(material);
                processRotor(material);
            }
        }
    }

    public static void processRotor(Material material) {

        GTRecipeHandler.removeRecipesByInputs(EXTRUDER_RECIPES, OreDictUnifier.get(ingot, material, 4), MetaItems.SHAPE_EXTRUDER_ROTOR.getStackForm());

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(blade, material, 4)
                .input(round, material)
                .input(screw, material, 4)
                .output(rotor, material)
                .duration((int) material.getMass())
                .EUt(24)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(blade, material, 4)
                .input(round, material)
                .output(rotor, material)
                .fluidInputs(Materials.SolderingAlloy.getFluid(72))
                .duration((int) material.getMass())
                .EUt(24)
                .buildAndRegister();
    }

    public static void processCurvedPlate(Material material) {

        ModHandler.addShapedRecipe(material.getUnlocalizedName() + "_curved_plate", OreDictUnifier.get(curvedPlate, material),
                " h ", " P ", "   ", 'P', new UnificationEntry(plate, material));

        BENDER_RECIPES.recipeBuilder()
                .input(plate, material)
                .circuitMeta(10)
                .output(curvedPlate, material)
                .duration((int) material.getMass())
                .EUt(24)
                .buildAndRegister();
    }

    public static void processBlade(Material material) {

        CUTTER_RECIPES.recipeBuilder()
                .input(curvedPlate, material)
                .output(blade, material)
                .duration((int) material.getMass())
                .EUt(24)
                .buildAndRegister();
    }
}
