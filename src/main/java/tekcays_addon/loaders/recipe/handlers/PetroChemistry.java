package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.material.Materials;
import tekcays_addon.api.recipeproperties.FluidOutputTime;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;

import static tekcays_addon.gtapi.consts.TKCYAValues.MINUTE;

public class PetroChemistry {

    public static void init() {
        TKCYARecipeMaps.BATCH_DISTILLATION.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(1))
                .fluidInputs(Materials.Oil.getFluid(1000))
                .fluidOutputs(Materials.SulfuricGas.getFluid(50))
                .fluidOutputs(Materials.SulfuricLightFuel.getFluid(50))
                .fluidOutputs(Materials.SulfuricHeavyFuel.getFluid(100))
                .fluidOutputTime(new FluidOutputTime[]{
                        new FluidOutputTime(Materials.SulfuricGas.getFluid(), MINUTE),
                        new FluidOutputTime(Materials.SulfuricLightFuel.getFluid(), 7 * MINUTE),
                        new FluidOutputTime(Materials.SulfuricHeavyFuel.getFluid(), 10 * MINUTE)
                })
                .duration(12 * MINUTE)
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
