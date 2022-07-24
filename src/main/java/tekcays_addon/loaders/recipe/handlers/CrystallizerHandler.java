package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.GTValues;
import gregtech.api.unification.material.Materials;
import gregtech.common.items.MetaItems;
import tekcays_addon.api.recipes.TKCYARecipeMaps;

public class CrystallizerHandler {

    public static void boules() {

        TKCYARecipeMaps.CRYSTALLIZATION.recipeBuilder()
                .fluidInputs(Materials.Silicon.getFluid(32 * GTValues.L))
                .fluidInputs(Materials.GalliumArsenide.getFluid(GTValues.L/4))
                .output(MetaItems.SILICON_BOULE)
                .duration(450 * 20)
                .buildAndRegister();

    }



}
