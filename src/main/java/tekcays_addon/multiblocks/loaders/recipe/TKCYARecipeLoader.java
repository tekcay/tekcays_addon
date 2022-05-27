package tekcays_addon.multiblocks.loaders.recipe;

import tekcays_addon.multiblocks.loaders.recipe.handlers.TKCYAMaterialRecipeHandler;

public class TKCYARecipeLoader {

    public static void init() {
        TKCYAMetaTileEntityLoader.init();
        TKCYACasingLoader.init();
        TKCYAMixerRecipes.init();
        TKCYAMiscRecipes.init();
        TKCYAMaterialRecipeHandler.register();
    }
}
