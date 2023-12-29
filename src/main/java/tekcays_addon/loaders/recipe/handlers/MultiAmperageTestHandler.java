package tekcays_addon.loaders.recipe.handlers;

import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.gtapi.recipes.TKCYARecipeMaps.MULTI_AMPERAGE_RECIPE_BUILDER_RECIPE_MAP;

import gregtech.api.GTValues;

public class MultiAmperageTestHandler {

    public static void init() {
        // H2O -> H2 + O2

        MULTI_AMPERAGE_RECIPE_BUILDER_RECIPE_MAP.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(2000), Oxygen.getFluid(1000))
                .duration(100)
                .EUt(90)
                .voltageTier(GTValues.LV)
                .amperage(3)
                .buildAndRegister();
    }
}
