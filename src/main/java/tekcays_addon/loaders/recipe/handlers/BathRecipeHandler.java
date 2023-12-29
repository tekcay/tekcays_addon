package tekcays_addon.loaders.recipe.handlers;

import static gregtech.api.unification.material.Materials.*;

import gregtech.api.unification.ore.OrePrefix;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;

public class BathRecipeHandler {

    public static void treatingWoodInit() {
        TKCYARecipeMaps.PRIMITIVE_BATH.recipeBuilder()
                .input(OrePrefix.plank, Wood)
                .fluidInputs(Creosote.getFluid(100))
                .output(OrePrefix.plank, TreatedWood)
                .duration(200)
                .buildAndRegister();
    }
}
