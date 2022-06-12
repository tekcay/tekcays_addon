package tekcays_addon.loaders.recipe;


import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.unification.material.Material;
import tekcays_addon.api.utils.TKCYALog;
import tekcays_addon.common.TKCYAConfigHolder;
import tekcays_addon.loaders.recipe.handlers.*;

import static gregtech.api.recipes.RecipeMaps.*;
import static tekcays_addon.api.utils.RegistriesList.MAGNETIC_MATERIALS;
import static tekcays_addon.loaders.recipe.handlers.TKCYAPartsRecipeHandler.*;
import static tekcays_addon.loaders.recipe.removals.RecipeRemovals.*;

public class TKCYARecipeLoader {

    public static void preload() {
    }
    public static void load() {

        TKCYAMetaTileEntityLoader.init();

        if (TKCYAConfigHolder.foilOverhaul.enableFoilOverhaul) {
            //processFoil();
            //foilRecipeRemovals();
        }


        if (TKCYAConfigHolder.magneticOverhaul.enableMagneticOverhaul) {
            processPolarizing();
            polarizerRecipeRemovals();
            MAGNETIC_MATERIALS.list.forEach(m -> TKCYALog.logger.info("Magnetic materials = " + m));
            MAGNETIC_MATERIALS.map.values().forEach(m -> TKCYALog.logger.info("Corresponding non magnetic materials = " + m));
        }

        if (TKCYAConfigHolder.meltingOverhaul.enableMeltingOverhaul) {
            GTRecipeHandler.removeAllRecipes(EXTRACTOR_RECIPES);
        }

        if (TKCYAConfigHolder.meltingOverhaul.enableAlloyingOverhaul) {
            GTRecipeHandler.removeAllRecipes(ALLOY_SMELTER_RECIPES);
        }

    }

    public static void loadLatest() {

        if (TKCYAConfigHolder.magneticOverhaul.enableMagneticOverhaul) {
            //GTRecipeHandler.removeAllRecipes(POLARIZER_RECIPES);
        }

        if (TKCYAConfigHolder.meltingOverhaul.enableMeltingOverhaul) {
            TKCYAMeltingRecipeHandler.init();
            //GTRecipeHandler.removeAllRecipes(EXTRACTOR_RECIPES);
        }

        if (TKCYAConfigHolder.meltingOverhaul.enableAlloyingOverhaul) {
            TKCYAAlloyingCrucibleRecipeHandler.init();
            //GTRecipeHandler.removeAllRecipes(ALLOY_SMELTER_RECIPES);
        }
    }

}
