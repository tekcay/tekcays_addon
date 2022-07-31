package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.GTValues;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.MetaItems;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.api.utils.TKCYALog;

import static tekcays_addon.api.utils.TKCYAValues.*;
import static tekcays_addon.loaders.recipe.handlers.TKCYACastingTableRecipeHandler.MOLD_PRODUCTION;

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
