package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.GTValues;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import tekcays_addon.api.unification.TKCYAMaterials;

import static tekcays_addon.api.recipes.TKCYARecipeMaps.BLASTING_RECIPES;
import static tekcays_addon.api.utils.TKCYAValues.MINUTE;

public class BlastingRecipeHandler {

    public static void init() {


        //Pig Iron

        ////From Limonite
        BLASTING_RECIPES.recipeBuilder()
                .temperature(1800)
                .input(OrePrefix.dust, Materials.YellowLimonite)
                .input(OrePrefix.dust, Materials.Coke, 2)
                .fluidOutputs(TKCYAMaterials.PigIron.getFluid(GTValues.L))
                .duration(2 * MINUTE)
                .buildAndRegister();

        BLASTING_RECIPES.recipeBuilder()
                .temperature(1800)
                .input(OrePrefix.dust, Materials.BrownLimonite)
                .input(OrePrefix.dust, Materials.Coke, 2)
                .fluidOutputs(TKCYAMaterials.PigIron.getFluid(GTValues.L))
                .duration(2 * MINUTE)
                .buildAndRegister();

        ////From Hematite
        BLASTING_RECIPES.recipeBuilder()
                .temperature(2 * MINUTE)
                .input(OrePrefix.dust, Materials.Hematite, 2)
                .input(OrePrefix.dust, Materials.Coke, 2)
                .fluidOutputs(TKCYAMaterials.PigIron.getFluid(GTValues.L))
                .duration(200)
                .buildAndRegister();

        ////From Magnetite
        BLASTING_RECIPES.recipeBuilder()
                .temperature(1800)
                .input(OrePrefix.dust, Materials.Magnetite, 3)
                .input(OrePrefix.dust, Materials.Coke, 2)
                .fluidOutputs(TKCYAMaterials.PigIron.getFluid(GTValues.L))
                .duration(2 * MINUTE)
                .buildAndRegister();

        //Tin

        ////From Cassiterite
        BLASTING_RECIPES.recipeBuilder()
                .temperature(1500)
                .input(OrePrefix.dust, Materials.Cassiterite)
                .input(OrePrefix.dust, Materials.Coke, 2)
                .fluidOutputs(Materials.Tin.getFluid(GTValues.L))
                .duration(2 * MINUTE)
                .buildAndRegister();

    }

}
