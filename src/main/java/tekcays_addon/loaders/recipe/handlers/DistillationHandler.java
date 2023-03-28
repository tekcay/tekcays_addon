package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;

import static gregtech.api.recipes.RecipeMaps.DISTILLATION_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.api.unification.TKCYAMaterials.*;

public class DistillationHandler {

    public static void init() {
        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(LightlySteamCrackedMethane.getFluid(1000))
                .fluidOutputs(DistilledWater.getFluid(1000))
                .fluidOutputs(CarbonMonoxide.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(1500))
                .duration(120).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(ModeratelySteamCrackedMethane.getFluid(1000))
                .fluidOutputs(DistilledWater.getFluid(2000))
                .fluidOutputs(CarbonMonoxide.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(3000))
                .duration(120).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(SeverelySteamCrackedMethane.getFluid(1000))
                .fluidOutputs(DistilledWater.getFluid(4000))
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(4000))
                .duration(120).EUt(120).buildAndRegister();
    }
}
