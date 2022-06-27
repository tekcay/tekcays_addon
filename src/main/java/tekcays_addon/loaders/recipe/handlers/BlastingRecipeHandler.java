package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.GTValues;
import gregtech.api.unification.material.Materials;
import tekcays_addon.api.unification.TKCYAMaterials;

import static tekcays_addon.api.recipes.TKCYARecipeMaps.CONVERTING_RECIPES;

public class BlastingRecipeHandler {

    public static void init() {


        //Steel
        CONVERTING_RECIPES.recipeBuilder()
                .setTemp(1800)
                .setPressure(5)
                .fluidInputs(Materials.Oxygen.getFluid(3000))
                .fluidInputs(TKCYAMaterials.PigIron.getFluid(GTValues.L))
                .fluidOutputs(Materials.Steel.getFluid(GTValues.L))
                .duration(200)
                .buildAndRegister();
    }

}
