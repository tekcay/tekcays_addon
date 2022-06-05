package tekcays_addon.loaders.recipe;

import gregtech.loaders.recipe.CraftingComponent;
import gregtech.loaders.recipe.MetaTileEntityLoader;
import tekcays_addon.common.TKCYAConfigHolder;
import tekcays_addon.common.metatileentities.TKCYAMetaTileEntities;

public class TKCYAMetaTileEntityLoader {

    public static void init() {
        if (TKCYAConfigHolder.foilOverhaul.enableFoilOverhaul) {
            MetaTileEntityLoader.registerMachineRecipe(TKCYAMetaTileEntities.CLUSTER_MILL,
                    "MMM", "CHC", "MMM",
                    'M', CraftingComponent.MOTOR, 'C', CraftingComponent.CIRCUIT, 'H', CraftingComponent.HULL);
        }
    }  

}
