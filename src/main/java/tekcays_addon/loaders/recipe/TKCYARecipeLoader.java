package tekcays_addon.loaders.recipe;

import gregtech.api.recipes.GTRecipeHandler;
import tekcays_addon.common.TKCYAConfigHolder;
import tekcays_addon.loaders.recipe.handlers.*;

import static gregtech.api.recipes.RecipeMaps.ALLOY_SMELTER_RECIPES;
import static gregtech.api.recipes.RecipeMaps.EXTRACTOR_RECIPES;


public class TKCYARecipeLoader {

    public static void preload() {
    }
    public static void load() {

        TKCYAMetaTileEntityLoader.init();

        if (TKCYAConfigHolder.foilOverhaul.enableFoilOverhaul) {
            TKCYAPartsRecipeHandler.initFoil();
        }

        if (TKCYAConfigHolder.meltingOverhaul.enableMeltingOverhaul) {
            TKCYAPartsRecipeHandler.removeExtractor();
        }

        if (TKCYAConfigHolder.meltingOverhaul.enableAlloyingOverhaul) {
            TKCYAPartsRecipeHandler.removeAlloySmelter();
        }

        if (TKCYAConfigHolder.magneticOverhaul.enableMagneticOverhaul) {
            TKCYAPartsRecipeHandler.initPolarizing();
        }
    }


    public static void loadLatest() {

        if (TKCYAConfigHolder.meltingOverhaul.enableMeltingOverhaul) {
            TKCYAMeltingRecipeHandler.init();
        }

        if (TKCYAConfigHolder.meltingOverhaul.enableAlloyingOverhaul) {
            TKCYAAlloyingCrucibleRecipeHandler.init();
        }
    }

}
