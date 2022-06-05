package tekcays_addon.loaders.recipe;

import tekcays_addon.loaders.recipe.handlers.TKCYAPartsRecipeHandler;

public class TKCYARecipeLoader {

    public static void init() {
        TKCYAPartsRecipeHandler.register();
        TKCYAMetaTileEntityLoader.init();
    }

}
