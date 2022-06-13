package tekcays_addon.loaders.recipe;

import tekcays_addon.common.TKCYAConfigHolder;
import tekcays_addon.loaders.recipe.handlers.*;

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

        if (TKCYAConfigHolder.meltingOverhaul.enableCastingOverhaul) {
            GasCollectorRecipeHandler.init();
            ShapedCraftingRecipes.molds();
            TKCYAPartsRecipeHandler.processMolds();
        }

        if (TKCYAConfigHolder.energyOverhaul.disableGasTurbinesOverhaul) {
            BurningGasBoilerRecipeHandler.init();
        }


    }


    public static void loadLatest() {

        if (TKCYAConfigHolder.meltingOverhaul.enableMeltingOverhaul) {
            TKCYAMeltingRecipeHandler.init();
        }

        if (TKCYAConfigHolder.meltingOverhaul.enableAlloyingOverhaul) {
            TKCYAAlloyingCrucibleRecipeHandler.init();
        }

        if (TKCYAConfigHolder.meltingOverhaul.enableCastingOverhaul) {
            TKCYACastingTableRecipeHandler.init();
        }

    }

}
