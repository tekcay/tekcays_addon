package tekcays_addon.loaders.recipe.handlers;


import gregtech.api.unification.material.Materials;
import net.minecraft.init.Items;
import tekcays_addon.gtapi.unification.TKCYAMaterials;

import static tekcays_addon.gtapi.recipes.TKCYARecipeMaps.FERMENTATION_RECIPES;
import static tekcays_addon.gtapi.utils.TKCYAValues.MINUTE;


public class FermentationHandler {

    public static void init() {

        FERMENTATION_RECIPES.recipeBuilder()
                .input(Items.WHEAT_SEEDS)
                .fluidOutputs(Materials.SeedOil.getFluid(5))
                .duration(30 * MINUTE)
                .buildAndRegister();

        FERMENTATION_RECIPES.recipeBuilder()
                .input(Items.PUMPKIN_SEEDS)
                .fluidOutputs(TKCYAMaterials.PumpkinOil.getFluid(5))
                .duration(30 * MINUTE)
                .buildAndRegister();

        FERMENTATION_RECIPES.recipeBuilder()
                .input(Items.MELON_SEEDS)
                .fluidOutputs(TKCYAMaterials.MelonOil.getFluid(5))
                .duration(30 * MINUTE)
                .buildAndRegister();
    }
}
