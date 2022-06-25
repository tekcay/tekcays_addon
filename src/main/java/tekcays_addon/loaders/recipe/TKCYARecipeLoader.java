package tekcays_addon.loaders.recipe;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.RecipeMaps;
import tekcays_addon.loaders.recipe.chains.*;
import tekcays_addon.loaders.recipe.handlers.*;
import tekcays_addon.loaders.recipe.removals.RecipesRemovalHandler;

import static tekcays_addon.common.TKCYAConfigHolder.*;

public class TKCYARecipeLoader {

    public static void preload() {
    }
    public static void load() {

        TKCYAMetaTileEntityLoader.init();

        if (miscOverhaul.enableFoilOverhaul) {
            TKCYAPartsRecipeHandler.initFoil();
        }

        if (meltingOverhaul.enableMeltingOverhaul) {
            TKCYAPartsRecipeHandler.removeExtractor();
        }

        if (meltingOverhaul.enableAlloyingOverhaul) {
            TKCYAPartsRecipeHandler.removeAlloySmelter();
            GTRecipeHandler.removeAllRecipes(RecipeMaps.PRIMITIVE_BLAST_FURNACE_RECIPES);
        }

        if (miscOverhaul.enableMagneticOverhaul) {
            TKCYAPartsRecipeHandler.initPolarizing();
        }

        if (meltingOverhaul.enableCastingOverhaul) {
            GasCollectorRecipeHandler.init();
            ShapedCraftingRecipes.molds();
            TKCYAPartsRecipeHandler.processMolds();
        }

        if (energyOverhaul.disableGasTurbinesOverhaul) {
            BurningGasBoilerRecipeHandler.init();
        }

        if (miscOverhaul.enableCoilOverhaul) {
            Coils.init();
        }

        if (miscOverhaul.enableGalvanizedSteel) {
            RecipesRemovalHandler.steelRemovalsInit();
            BathRecipeHandler.treatingWoodInit();
            ShapedCraftingRecipes.galvanizedSteel();
            AssemblerRecipeHandler.galvanizedSteel();
        }

    }


    public static void loadLatest() {

        if (miscOverhaul.enableGalvanizedSteel) {
            BathRecipeHandler.galvanizingSteelInit();
        }

        if (meltingOverhaul.enableMeltingOverhaul) {
            TKCYAMeltingRecipeHandler.init();
        }

        if (meltingOverhaul.enableAlloyingOverhaul) {
            TKCYAAlloyingCrucibleRecipeHandler.init();
        }

        if (meltingOverhaul.enableCastingOverhaul) {
            TKCYACastingTableRecipeHandler.init();
        }

    }

}
