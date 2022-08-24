package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.GTValues;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import tekcays_addon.api.unification.TKCYAMaterials;

import static tekcays_addon.api.recipes.TKCYARecipeMaps.BLASTING_RECIPES;

public class BlastingRecipeHandler {

    public static void init() {


        //Pig Iron

        ////From Limonite
        BLASTING_RECIPES.recipeBuilder()
                .setTemp(1800)
                .input(OrePrefix.dust, Materials.YellowLimonite)
                .fluidOutputs(TKCYAMaterials.PigIron.getFluid(GTValues.L))
                .duration(200)
                .buildAndRegister();

        BLASTING_RECIPES.recipeBuilder()
                .setTemp(1800)
                .input(OrePrefix.dust, Materials.BrownLimonite)
                .fluidOutputs(TKCYAMaterials.PigIron.getFluid(GTValues.L))
                .duration(200)
                .buildAndRegister();

        ////From BandedIron
        BLASTING_RECIPES.recipeBuilder()
                .setTemp(1800)
                .input(OrePrefix.dust, Materials.BandedIron)
                .fluidOutputs(TKCYAMaterials.PigIron.getFluid(2 * GTValues.L))
                .duration(200)
                .buildAndRegister();

        ////From Magnetite
        BLASTING_RECIPES.recipeBuilder()
                .setTemp(1800)
                .input(OrePrefix.dust, Materials.Magnetite)
                .fluidOutputs(TKCYAMaterials.PigIron.getFluid(3 * GTValues.L))
                .duration(200)
                .buildAndRegister();

        //Tin

        ////From Cassiterite
        BLASTING_RECIPES.recipeBuilder()
                .setTemp(1200)
                .input(OrePrefix.dust, Materials.Cassiterite)
                .fluidOutputs(Materials.Tin.getFluid(GTValues.L))
                .duration(200)
                .buildAndRegister();
    }

}
