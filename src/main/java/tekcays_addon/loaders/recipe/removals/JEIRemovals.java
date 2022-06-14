package tekcays_addon.loaders.recipe.removals;

import gregtech.api.GTValues;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import tekcays_addon.common.TKCYAConfigHolder;

import static gregtech.common.metatileentities.MetaTileEntities.*;
import static gregicality.multiblocks.common.metatileentities.GCYMMetaTileEntities.*;

@JEIPlugin
public class JEIRemovals implements IModPlugin { {
}

    @Override
    public void register(IModRegistry registry)
    {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        IIngredientBlacklist ingredientBlacklist = jeiHelpers.getIngredientBlacklist();

        if (TKCYAConfigHolder.miscOverhaul.enableMagneticOverhaul) {
            for (int i = GTValues.LV; i < GTValues.UV + 1; i++) {
                ingredientBlacklist.addIngredientToBlacklist(POLARIZER[i].getStackForm());
            }
        }

        if (TKCYAConfigHolder.meltingOverhaul.enableMeltingOverhaul) {
            for (int i = GTValues.LV; i < GTValues.UV + 1; i++) {
                ingredientBlacklist.addIngredientToBlacklist(EXTRACTOR[i].getStackForm());
            }
            ingredientBlacklist.addIngredientToBlacklist(STEAM_EXTRACTOR_BRONZE.getStackForm());
            ingredientBlacklist.addIngredientToBlacklist(STEAM_EXTRACTOR_STEEL.getStackForm());
            ingredientBlacklist.addIngredientToBlacklist(LARGE_EXTRACTOR.getStackForm());
        }

        if (TKCYAConfigHolder.meltingOverhaul.enableAlloyingOverhaul) {
            for (int i = GTValues.LV; i < GTValues.UV + 1; i++) {
                ingredientBlacklist.addIngredientToBlacklist(ALLOY_SMELTER[i].getStackForm());
            }
            ingredientBlacklist.addIngredientToBlacklist(STEAM_ALLOY_SMELTER_BRONZE.getStackForm());
            ingredientBlacklist.addIngredientToBlacklist(STEAM_ALLOY_SMELTER_STEEL.getStackForm());
        }

        if (TKCYAConfigHolder.meltingOverhaul.enableCastingOverhaul) {
            for (int i = GTValues.LV; i < GTValues.UV + 1; i++) {
                ingredientBlacklist.addIngredientToBlacklist(FLUID_SOLIDIFIER[i].getStackForm());
            }
        }

        if (TKCYAConfigHolder.energyOverhaul.disableGasTurbinesOverhaul) {
            for (int i = GTValues.LV; i < GTValues.HV + 1; i++) {
                ingredientBlacklist.addIngredientToBlacklist(GAS_TURBINE[i].getStackForm());
            }
            ingredientBlacklist.addIngredientToBlacklist(LARGE_GAS_TURBINE);
        }


    }

}
