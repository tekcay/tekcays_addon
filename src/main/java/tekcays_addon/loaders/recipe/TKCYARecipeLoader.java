package tekcays_addon.loaders.recipe;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.Material;
import tekcays_addon.common.TKCYAConfigHolder;
import tekcays_addon.loaders.recipe.handlers.*;

import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_FOIL;
import static tekcays_addon.loaders.recipe.handlers.TKCYAPartsRecipeHandler.*;
import static tekcays_addon.loaders.recipe.removals.RecipeRemovals.*;

public class TKCYARecipeLoader {

    public static void preload() {
    }
    public static void load() {

        TKCYAMetaTileEntityLoader.init();

        if (TKCYAConfigHolder.foilOverhaul.enableFoilOverhaul) {
            processFoil();
            foilRecipeRemovals();
        }


        if (TKCYAConfigHolder.magneticOverhaul.enableMagneticOverhaul) {
            processPolarizing();
            polarizerRecipeRemovals();
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
