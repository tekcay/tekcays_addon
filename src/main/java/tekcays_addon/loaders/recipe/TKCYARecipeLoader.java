package tekcays_addon.loaders.recipe;

import tekcays_addon.loaders.recipe.handlers.TKCYAMaterialRecipeHandler;

public class TKCYARecipeLoader {

    public static void init() {
        TKCYAMaterialRecipeHandler.register();
    }
}
