package tekcays_addon.loaders.recipe.handlers;

import static gregtech.api.recipes.RecipeMaps.BENDER_RECIPES;
import static gregtech.api.recipes.RecipeMaps.FORMING_PRESS_RECIPES;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.api.unification.ore.OrePrefix.plate;
import static tekcays_addon.gtapi.consts.TKCYAValues.MOLD_MATERIALS;
import static tekcays_addon.gtapi.unification.material.info.TKCYAMaterialFlags.POLYMER;
import static tekcays_addon.gtapi.unification.material.ore.TKCYAOrePrefix.moldEmpty;
import static tekcays_addon.loaders.recipe.handlers.CastingRecipeHandler.MOLD_PRODUCTION;

import gregtech.api.GTValues;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.IngotProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import tekcays_addon.common.items.TKCYAMetaItems;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;
import tekcays_addon.gtapi.unification.TKCYAMaterials;

public class TKCYAPartsRecipeHandler {

    public static void processCasting(OrePrefix prefix, Material material, IngotProperty ingotProperty) {
        // GTRecipeHandler.removeAllRecipes(FLUID_SOLIDFICATION_RECIPES);

        for (Material m : MOLD_MATERIALS) {
            if (!material.hasProperty(PropertyKey.FLUID)) continue;

            int fluidInputs = (int) (prefix.getMaterialAmount(material) * GTValues.L / GTValues.M);

            // Polymers are made in the crystallizer
            if (material.hasFlags(POLYMER)) {

                TKCYARecipeMaps.CRYSTALLIZATION.recipeBuilder()
                        .fluidInputs(material.getFluid(fluidInputs))
                        .notConsumable(MOLD_PRODUCTION.get(prefix), m)
                        .output(prefix, material)
                        .duration(material.getFluid().getTemperature() * fluidInputs / 273)
                        .buildAndRegister();

            } else if (m.getFluid().getTemperature() > material.getFluid().getTemperature()) { // Compares temperatures
                                                                                               // of the mold material
                                                                                               // w/ and the fluid
                                                                                               // material

                TKCYARecipeMaps.CASTING_TABLE_RECIPES.recipeBuilder()
                        .fluidInputs(
                                material.getFluid((int) (prefix.getMaterialAmount(material) * GTValues.L / GTValues.M)))
                        .notConsumable(MOLD_PRODUCTION.get(prefix), m)
                        .output(prefix, material)
                        .duration((int) (prefix.getMaterialAmount(material) * material.getFluid().getTemperature() /
                                GTValues.M))
                        .buildAndRegister();

                // Air Cooled

                TKCYARecipeMaps.CASTING_TABLE_RECIPES.recipeBuilder()
                        .fluidInputs(material.getFluid(fluidInputs),
                                Materials.Air.getFluid(material.getFluid().getTemperature()))
                        .notConsumable(MOLD_PRODUCTION.get(prefix), m)
                        .output(prefix, material)
                        .duration((int) (0.8 * prefix.getMaterialAmount(material) *
                                material.getFluid().getTemperature() / GTValues.M))
                        .buildAndRegister();

                TKCYARecipeMaps.ELECTRIC_CASTING_RECIPES.recipeBuilder()
                        .fluidInputs(material.getFluid(fluidInputs),
                                Materials.Air.getFluid(material.getFluid().getTemperature()))
                        .notConsumable(MOLD_PRODUCTION.get(prefix), m)
                        .output(prefix, material)
                        .duration((int) (0.6 * prefix.getMaterialAmount(material) *
                                material.getFluid().getTemperature() / GTValues.M))
                        .EUt(30)
                        .buildAndRegister();

                // When using a gas collector, it outputs Hot Air

                TKCYARecipeMaps.ELECTRIC_CASTING_RECIPES.recipeBuilder()
                        .fluidInputs(material.getFluid(fluidInputs),
                                Materials.Air.getFluid(material.getFluid().getTemperature()))
                        .notConsumable(MOLD_PRODUCTION.get(prefix), m)
                        .notConsumable(TKCYAMetaItems.GAS_COLLECTOR)
                        .output(prefix, material)
                        .fluidOutputs(TKCYAMaterials.HotAir.getFluid(material.getFluid().getTemperature()))
                        .duration((int) (0.6 * prefix.getMaterialAmount(material) *
                                material.getFluid().getTemperature() / GTValues.M))
                        .EUt(30)
                        .buildAndRegister();

            } else { // Otherwise, it burns the mold

                TKCYARecipeMaps.CASTING_TABLE_RECIPES.recipeBuilder()
                        .fluidInputs(material.getFluid(fluidInputs))
                        .input(MOLD_PRODUCTION.get(prefix), m)
                        .output(dust, Materials.Ash)
                        .duration(20)
                        .hidden()
                        .buildAndRegister();

                TKCYARecipeMaps.ELECTRIC_CASTING_RECIPES.recipeBuilder()
                        .fluidInputs(
                                material.getFluid((int) (prefix.getMaterialAmount(material) * GTValues.L / GTValues.M)))
                        .input(MOLD_PRODUCTION.get(prefix), m)
                        .output(dust, Materials.Ash)
                        .duration(20)
                        .EUt(30)
                        .hidden()
                        .buildAndRegister();

            }
        }
    }

    public static void processMolds() {
        for (Material m : MOLD_MATERIALS) {

            // Empty molds
            BENDER_RECIPES.recipeBuilder()
                    .duration(180)
                    .EUt(12)
                    .circuitMeta(4)
                    .input(plate, m, 4)
                    .output(moldEmpty, m)
                    .buildAndRegister();

            for (OrePrefix prefix : MOLD_PRODUCTION.values()) {

                // Molds
                FORMING_PRESS_RECIPES.recipeBuilder() // TODO To fix
                        .duration(120)
                        .EUt(22)
                        .notConsumable(prefix, m)
                        .input(moldEmpty, m)
                        .output(prefix, m)
                        .buildAndRegister();
            }
        }
    }
}
