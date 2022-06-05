package tekcays_addon.loaders.recipe;

import gregtech.api.GTValues;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import tekcays_addon.common.TKCYAConfigHolder;


import static gregtech.common.metatileentities.MetaTileEntities.POLARIZER;

@JEIPlugin
public class JEIRemovals implements IModPlugin { {
}

    @Override
    public void register(IModRegistry registry)
    {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        IIngredientBlacklist ingredientBlacklist = jeiHelpers.getIngredientBlacklist();

        if (TKCYAConfigHolder.magneticOverhaul.enableMagneticOverhaul) {
            for (int i = GTValues.LV; i < GTValues.UV + 1; i++) {
                ingredientBlacklist.addIngredientToBlacklist(POLARIZER[i].getStackForm());
            }
        }

    }

}
