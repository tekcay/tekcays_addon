package tekcays_addon.loaders.recipe.handlers;


import gregtech.api.unification.material.Materials;
import net.minecraft.init.Items;

import static tekcays_addon.api.recipes.TKCYARecipeMaps.FERMENTATION_RECIPES;


public class FermentationHandler {

    public static void init() {
        FERMENTATION_RECIPES.recipeBuilder()
                .input(Items.WHEAT_SEEDS, 64)
                .fluidOutputs(Materials.SeedOil.getFluid(500))
                .duration(30*60*20) //30 min
                .buildAndRegister();
    }
}
