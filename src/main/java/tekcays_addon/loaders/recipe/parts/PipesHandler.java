package tekcays_addon.loaders.recipe.parts;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import tekcays_addon.api.utils.BooleanHelpers;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;
import tekcays_addon.gtapi.unification.material.ore.OrePrefixValues;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static gregtech.api.unification.ore.OrePrefix.*;
import static tekcays_addon.gtapi.unification.material.ore.TKCYAOrePrefix.curvedPlate;

public class PipesHandler {

    public static void init(Material material) {
        processPipes(material);
    }


    private static void processPipes(Material material) {

        if (BooleanHelpers.anyMatch(material, Materials.Wood, Materials.TreatedWood)) return;

        if (material.hasProperty(PropertyKey.ITEM_PIPE)) {
            processItemPipes(material);;

        } else if (material.hasProperty(PropertyKey.FLUID_PIPE)) {
            processSingleFluidPipes(material, new AtomicInteger(15));
            processMultiFluidPipes(material);
        }
    }


    private static void processItemPipes(Material material) {

        int circuitMeta = 0;

        for (Map.Entry<OrePrefix, OrePrefix> entry : OrePrefixValues.NORMAL_TO_RESTRICTIVE_PIPES.entrySet()) {

            int materialAmount = (int) OrePrefixValues.getOrePrefixUnitAsDouble(entry.getValue());

            TKCYARecipeMaps.ROLLING_RECIPES.recipeBuilder()
                    .input(curvedPlate, material, materialAmount)
                    .circuitMeta(circuitMeta)
                    .output(entry.getKey(), material)
                    .duration((int) (materialAmount * (1 + material.getMass())))
                    .EUt(20)
                    .buildAndRegister();

            TKCYARecipeMaps.NEW_ASSEMBLING.recipeBuilder()
                    .input(entry.getKey(), material)
                    .input(ring, Materials.Steel, 2)
                    .circuitMeta(circuitMeta++)
                    .output(entry.getValue(), material)
                    .fluidInputs(Materials.SolderingAlloy.getFluid(72 * materialAmount))
                    .duration(60 * materialAmount)
                    .EUt(20)
                    .buildAndRegister();
        }
    }

    private static void processSingleFluidPipes(Material material, AtomicInteger circuitMeta) {
        OrePrefixValues.FLUID_PIPES_SINGLE.forEach(pipe -> generateFluidPipeRecipes(pipe, material, circuitMeta.getAndIncrement()));
    }

    private static void generateFluidPipeRecipes(OrePrefix pipeOrePrefix, Material material, int circuitMeta) {

        if (pipeOrePrefix.equals(pipeTinyFluid)) {

            TKCYARecipeMaps.ROLLING_RECIPES.recipeBuilder()
                    .input(curvedPlate, material, 1)
                    .circuitMeta(circuitMeta)
                    .output(pipeTinyFluid, material, 2)
                    .duration(20)
                    .EUt(20)
                    .buildAndRegister();
        } else {
            int curvedPlateInput = (int) OrePrefixValues.getOrePrefixUnitAsDouble(pipeOrePrefix);

            TKCYARecipeMaps.ROLLING_RECIPES.recipeBuilder()
                    .input(curvedPlate, material, curvedPlateInput)
                    .circuitMeta(circuitMeta)
                    .output(pipeOrePrefix, material)
                    .duration(30 * curvedPlateInput)
                    .EUt(20)
                    .buildAndRegister();
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
}
