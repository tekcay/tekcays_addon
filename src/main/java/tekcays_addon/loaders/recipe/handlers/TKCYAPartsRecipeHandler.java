package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.GTValues;
import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.properties.IngotProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import net.minecraft.item.ItemStack;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import tekcays_addon.api.unification.TKCYAMaterials;
import tekcays_addon.api.utils.TKCYAValues;
import tekcays_addon.common.items.TKCYAMetaItems;
import tekcays_addon.common.items.behaviors.ElectrodeBehavior;


import static gregtech.api.GTValues.LV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static tekcays_addon.api.unification.material.info.TKCYAMaterialFlags.POLYMER;
import static tekcays_addon.api.unification.material.ore.TKCYAOrePrefix.*;
import static tekcays_addon.api.utils.TKCYAValues.*;
import static tekcays_addon.loaders.recipe.handlers.CastingRecipeHandler.MOLD_PRODUCTION;

public class TKCYAPartsRecipeHandler {

    public static void initFoil() {

        foil.addProcessingHandler(PropertyKey.INGOT, TKCYAPartsRecipeHandler::processFoil);

    }

    public static void initElectrode() {

        for (Material m : ELECTRODE_MATERIALS) {

            ItemStack electrodeStack = TKCYAMetaItems.ELECTRODE.getStackForm();

            ElectrodeBehavior.getInstanceFor(electrodeStack).setPartMaterial(electrodeStack, m);

            LASER_ENGRAVER_RECIPES.recipeBuilder()
                    .input(stickLong, m)
                    .notConsumable(lens, Materials.Glass)
                    .outputs(electrodeStack)
                    .duration((int) m.getMass() * 5)
                    .EUt(24)
                    .buildAndRegister();
        }
    }



    public static void initPolarizing(){

        for (OrePrefix orePrefix : TKCYAValues.POLARIZING_PREFIXES) {
            orePrefix.addProcessingHandler(PropertyKey.INGOT, TKCYAPartsRecipeHandler::processPolarizing);
        }
    }

    public static void removeAlloySmelter() {

        for (OrePrefix orePrefix : OrePrefix.values()) {
            orePrefix.addProcessingHandler(PropertyKey.INGOT, TKCYAPartsRecipeHandler::processAlloySmelter);
        }
    }

    public static void removeExtractor() {

        for (OrePrefix orePrefix : OrePrefix.values()) {
            orePrefix.addProcessingHandler(PropertyKey.INGOT, TKCYAPartsRecipeHandler::processExtractor);
        }
    }


    public static void processFoil(OrePrefix foilPrefix, Material material, IngotProperty property) {

        GTRecipeHandler.removeRecipesByInputs(BENDER_RECIPES, OreDictUnifier.get(plate, material), IntCircuitIngredient.getIntegratedCircuit(1));

        TKCYARecipeMaps.CLUSTER_MILL_RECIPES.recipeBuilder()
            .input(plate, material)
            .output(foilPrefix, material, 4)
            .duration((int) material.getMass())
            .EUt(24)
            .buildAndRegister();
    }

    public static void processPolarizing(OrePrefix polarizingPrefix, Material material, IngotProperty property) {

        Material magneticMaterial = property.getMagneticMaterial();

        GTRecipeHandler.removeAllRecipes(POLARIZER_RECIPES);

        if (magneticMaterial != null && polarizingPrefix.doGenerateItem(magneticMaterial)) {
                ItemStack magneticStack = OreDictUnifier.get(polarizingPrefix, magneticMaterial);

                TKCYARecipeMaps.ADVANCED_POLARIZER_RECIPES.recipeBuilder() //polarizing
                        .input(polarizingPrefix, material)
                        .input(OrePrefix.dust, Materials.Magnetite)
                        .outputs(magneticStack)
                        .duration((int) ((int) material.getMass() * polarizingPrefix.getMaterialAmount(material) / GTValues.M))
                        .EUt(8 * getVoltageMultiplier(material))
                        .buildAndRegister();
        }
    }

    public static void processAlloySmelter(OrePrefix prefix, Material material, IngotProperty property) {
        GTRecipeHandler.removeAllRecipes(ALLOY_SMELTER_RECIPES);
    }

    public static void processExtractor(OrePrefix prefix, Material material, IngotProperty property) {
        GTRecipeHandler.removeAllRecipes(EXTRACTOR_RECIPES);
    }

    public static void processCasting(OrePrefix prefix, Material material, IngotProperty ingotProperty) {
        GTRecipeHandler.removeAllRecipes(FLUID_SOLIDFICATION_RECIPES);

        for (Material m : MOLD_MATERIALS) {
            if (!material.hasProperty(PropertyKey.FLUID)) continue;

            int fluidInputs = (int) (prefix.getMaterialAmount(material) * GTValues.L / GTValues.M);

            //Polymers are made in the crystallizer
            if (material.hasFlags(POLYMER)) {

                TKCYARecipeMaps.CRYSTALLIZATION.recipeBuilder()
                        .fluidInputs(material.getFluid(fluidInputs))
                        .notConsumable(MOLD_PRODUCTION.get(prefix), m)
                        .output(prefix, material)
                        .duration(material.getFluid().getTemperature() * fluidInputs / 273)
                        .buildAndRegister();

            }
            else if (m.getFluid().getTemperature() > material.getFluid().getTemperature()) { //Compares temperatures of the mold material w/ and the fluid material

                TKCYARecipeMaps.CASTING_TABLE_RECIPES.recipeBuilder()
                        .fluidInputs(material.getFluid((int) (prefix.getMaterialAmount(material) * GTValues.L / GTValues.M)))
                        .notConsumable(MOLD_PRODUCTION.get(prefix), m)
                        .output(prefix, material)
                        .duration((int) (prefix.getMaterialAmount(material) * material.getFluid().getTemperature() / GTValues.M))
                        .buildAndRegister();

                //Air Cooled

                TKCYARecipeMaps.CASTING_TABLE_RECIPES.recipeBuilder()
                        .fluidInputs(material.getFluid(fluidInputs), Materials.Air.getFluid(material.getFluid().getTemperature()))
                        .notConsumable(MOLD_PRODUCTION.get(prefix), m)
                        .output(prefix, material)
                        .duration((int) (0.8 * prefix.getMaterialAmount(material) * material.getFluid().getTemperature() / GTValues.M))
                        .buildAndRegister();

                TKCYARecipeMaps.ELECTRIC_CASTING_RECIPES.recipeBuilder()
                        .fluidInputs(material.getFluid(fluidInputs), Materials.Air.getFluid(material.getFluid().getTemperature()))
                        .notConsumable(MOLD_PRODUCTION.get(prefix), m)
                        .output(prefix, material)
                        .duration((int) (0.6 * prefix.getMaterialAmount(material) * material.getFluid().getTemperature() / GTValues.M))
                        .EUt(30)
                        .buildAndRegister();


                //When using a gas collector, it outputs Hot Air

                TKCYARecipeMaps.ELECTRIC_CASTING_RECIPES.recipeBuilder()
                        .fluidInputs(material.getFluid(fluidInputs), Materials.Air.getFluid(material.getFluid().getTemperature()))
                        .notConsumable(MOLD_PRODUCTION.get(prefix), m)
                        .notConsumable(TKCYAMetaItems.GAS_COLLECTOR)
                        .output(prefix, material)
                        .fluidOutputs(TKCYAMaterials.HotAir.getFluid(material.getFluid().getTemperature()))
                        .duration((int) (0.6 * prefix.getMaterialAmount(material) * material.getFluid().getTemperature() / GTValues.M))
                        .EUt(30)
                        .buildAndRegister();

            } else { //Otherwise, it burns the mold

                TKCYARecipeMaps.CASTING_TABLE_RECIPES.recipeBuilder()
                        .fluidInputs(material.getFluid(fluidInputs))
                        .input(MOLD_PRODUCTION.get(prefix), m)
                        .output(dust, Materials.Ash)
                        .duration(20)
                        .hidden()
                        .buildAndRegister();

                TKCYARecipeMaps.ELECTRIC_CASTING_RECIPES.recipeBuilder()
                        .fluidInputs(material.getFluid((int) (prefix.getMaterialAmount(material) * GTValues.L / GTValues.M)))
                        .input(MOLD_PRODUCTION.get(prefix), m)
                        .output(dust, Materials.Ash)
                        .duration(20)
                        .EUt(30)
                        .hidden()
                        .buildAndRegister();

            }
        }
    }

    public static void processMolds(){

        for (Material m : MOLD_MATERIALS) {

            //Empty molds
            BENDER_RECIPES.recipeBuilder()
                    .duration(180)
                    .EUt(12)
                    .circuitMeta(4)
                    .input(plate, m, 4)
                    .output(moldEmpty, m)
                    .buildAndRegister();

            for (OrePrefix prefix : MOLD_PRODUCTION.values()) {

                //Molds
                FORMING_PRESS_RECIPES.recipeBuilder() //TODO To fix
                        .duration(120)
                        .EUt(22)
                        .notConsumable(prefix, m)
                        .input(moldEmpty, m)
                        .output(prefix, m)
                        .buildAndRegister();
            }
        }
    }



    private static int getVoltageMultiplier(Material material) {
        return material.getBlastTemperature() >= 1200 ? VA[LV] : 2;
    }


}





