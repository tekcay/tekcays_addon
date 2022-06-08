package tekcays_addon.loaders.recipe;

import tekcays_addon.loaders.recipe.handlers.TKCYAMeltingRecipeHandler;
import tekcays_addon.loaders.recipe.handlers.TKCYAPartsRecipeHandler;
import tekcays_addon.loaders.recipe.handlers.TKCYAPolarizingRecipeHandler;

public class TKCYARecipeLoader {

    public static void init() {
        TKCYAPartsRecipeHandler.register();
        TKCYAPolarizingRecipeHandler.register();
        TKCYAMetaTileEntityLoader.init();
        TKCYAMeltingRecipeHandler.init();
    }

}
