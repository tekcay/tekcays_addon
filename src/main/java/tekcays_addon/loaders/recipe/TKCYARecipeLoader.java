package tekcays_addon.loaders.recipe;

import tekcays_addon.common.TKCYAConfigHolder;
import tekcays_addon.loaders.recipe.handlers.*;

public class TKCYARecipeLoader {

    public static void preload() {
    }
    public static void load() {
        TKCYAPartsRecipeHandler.register();
        TKCYAPolarizingRecipeHandler.register();
        TKCYAMetaTileEntityLoader.init();
    }

    public static void loadLatest() {
        if (TKCYAConfigHolder.meltingOverhaul.enableMeltingOverhaul) {
            TKCYAMeltingRecipeHandler.init();
        }
    }

}
