package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.GTValues;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;

import static tekcays_addon.api.recipes.TKCYARecipeMaps.BLASTING_RECIPES;

public class BlastingRecipeHandler {

    public static void init() {


        //Steel
        BLASTING_RECIPES.recipeBuilder()
                .setTemp(1800)
                .setPressure(5)
                .input(OrePrefix.dust, Materials.WroughtIron)
                .fluidInputs(Materials.Oxygen.getFluid(3000))
                .fluidOutputs(Materials.Steel.getFluid(GTValues.L))
                .duration(200)
                .buildAndRegister();


    }

}
