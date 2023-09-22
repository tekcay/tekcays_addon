package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.material.Materials;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;

public class PetroChemistry {

    public static void init() {
        TKCYARecipeMaps.BATCH_DISTILLATION.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(1))
                .fluidInputs(Materials.Oil.getFluid(1000))
                .fluidOutputs(Materials.SulfuricHeavyFuel.getFluid(50))
                .fluidOutputs(Materials.SulfuricLightFuel.getFluid(100))
                .fluidOutputs(Materials.Steam.getFluid(50))
                .duration(500)
                .buildAndRegister();


        TKCYARecipeMaps.FLOW_DISTILLATION.recipeBuilder()
                .fluidInputs(Materials.Oil.getFluid(100))
                .fluidInputs(Materials.Water.getFluid(2000))
                .fluidOutputs(Materials.SulfuricHeavyFuel.getFluid(10))
                .fluidOutputs(Materials.SulfuricLightFuel.getFluid(90))
                .fluidOutputs(Materials.Steam.getFluid(50))
                .duration(500)
                .buildAndRegister();
    }

}
