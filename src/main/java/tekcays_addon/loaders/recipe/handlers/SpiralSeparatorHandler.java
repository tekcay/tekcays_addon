package tekcays_addon.loaders.recipe.handlers;

import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.common.items.TKCYAMetaItems;

import static gregtech.api.unification.material.Materials.Stone;
import static gregtech.api.unification.ore.OrePrefix.dust;

public class SpiralSeparatorHandler {

    public static void init() {

        TKCYARecipeMaps.SPIRAL_SEPARATION.recipeBuilder()
                .inputs(TKCYAMetaItems.DUST_MIXTURE.getStackForm())
                .output(dust, Stone)
                .duration(100)
                .EUt(120)
                .buildAndRegister();


    }

}
