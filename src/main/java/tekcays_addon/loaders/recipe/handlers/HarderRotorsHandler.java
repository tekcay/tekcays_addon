package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.GregTechAPI;
import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.items.MetaItems;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;
import tekcays_addon.gtapi.unification.material.ore.OrePrefixValues;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static tekcays_addon.gtapi.unification.material.ore.TKCYAOrePrefix.blade;
import static tekcays_addon.gtapi.unification.material.ore.TKCYAOrePrefix.curvedPlate;

public class HarderRotorsHandler {

    public static void init() {

        for (Material material : GregTechAPI.materialManager.getRegisteredMaterials()) {

            boolean curvedPlateCheck = curvedPlate.doGenerateItem(material);
            boolean bladeCheck = blade.doGenerateItem(material);
            boolean rotorCheck = rotor.doGenerateItem(material);
            boolean pipeCheck = material.hasProperty(PropertyKey.FLUID_PIPE) || material.hasProperty(PropertyKey.ITEM_PIPE);

            if (curvedPlateCheck) processCurvedPlate(material);
            if (bladeCheck) processBlade(material);
            if (rotorCheck && curvedPlateCheck && bladeCheck) processRotor(material);
            if (curvedPlateCheck && pipeCheck) processPipes(material);
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

        TKCYARecipeMaps.ROLLING_RECIPES.recipeBuilder()
                .input(plate, material)
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

    public static void processPipes(Material material) {

        if (material.hasProperty(PropertyKey.ITEM_PIPE)) {
            processSingleFluidPipes(OrePrefixValues.ITEM_PIPES_NORMAL, material, new AtomicInteger(5));
            processRestrictivePipes(material);

        } else if (material.hasProperty(PropertyKey.FLUID_PIPE)) {
            processSingleFluidPipes(OrePrefixValues.FLUID_PIPES_SINGLE, material, new AtomicInteger(15));
            processMultiFluidPipes(material);
        }
    }

    private static void processMultiFluidPipes(Material material) {
        multi(material, pipeQuadrupleFluid, 4);
        multi(material, pipeNonupleFluid, 9);
    }

    private static void multi(Material material, OrePrefix output, int multiplier) {
        TKCYARecipeMaps.NEW_ASSEMBLING.recipeBuilder()
                .input(pipeSmallFluid, material, multiplier)
                .circuitMeta(multiplier)
                .output(output, material)
                .fluidInputs(Materials.SolderingAlloy.getFluid(72 * multiplier))
                .duration(200 * multiplier)
                .EUt(20)
                .buildAndRegister();
    }

    private static void processRestrictivePipes(Material material) {

        for (Map.Entry<OrePrefix, OrePrefix> entry : OrePrefixValues.NORMAL_TO_RESTRICTIVE_PIPES.entrySet()) {

            int materialAmount = (int) OrePrefixValues.getOrePrefixUnitAsDouble(entry.getValue());

            TKCYARecipeMaps.NEW_ASSEMBLING.recipeBuilder()
                    .input(entry.getKey(), material)
                    .input(ring, Materials.Steel, 2)
                    .circuitMeta(materialAmount)
                    .output(entry.getValue(), material)
                    .fluidInputs(Materials.SolderingAlloy.getFluid(72 * materialAmount))
                    .duration(60 * materialAmount)
                    .EUt(20)
                    .buildAndRegister();
        }
    }

    private static void processSingleFluidPipes(List<OrePrefix> pipeOreprefix, Material material, AtomicInteger circuitMeta) {
        pipeOreprefix.forEach(pipe -> generateFluidPipeRecipes(pipe, material, circuitMeta.getAndIncrement()));
    }

    private static void generateFluidPipeRecipes(OrePrefix pipeOrePrefix, Material material, int circuitMeta) {

        if (pipeOrePrefix.equals(pipeTinyFluid)) {
            TKCYARecipeMaps.ROLLING_RECIPES.recipeBuilder()
                    .input(curvedPlate, material)
                    .circuitMeta(circuitMeta)
                    .output(pipeOrePrefix, material, 2)
                    .duration((int) (1 + material.getMass()))
                    .EUt(20)
                    .buildAndRegister();
        } else {

            int curvedPlateInput = (int) OrePrefixValues.getOrePrefixUnitAsDouble(pipeOrePrefix);

            TKCYARecipeMaps.ROLLING_RECIPES.recipeBuilder()
                    .input(curvedPlate, material, curvedPlateInput)
                    .circuitMeta(circuitMeta)
                    .output(pipeOrePrefix, material)
                    .duration((int) (curvedPlateInput * (1 + material.getMass())))
                    .EUt(20)
                    .buildAndRegister();
        }
    }
}
