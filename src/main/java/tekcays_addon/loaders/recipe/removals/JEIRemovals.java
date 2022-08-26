package tekcays_addon.loaders.recipe.removals;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.recipes.ModHandler;
import gregtech.common.metatileentities.MetaTileEntities;
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

            ingredientBlacklist.addIngredientToBlacklist(PRIMITIVE_BLAST_FURNACE.getStackForm());

        }

        if (TKCYAConfigHolder.meltingOverhaul.enableCastingOverhaul) {
            for (int i = GTValues.LV; i < GTValues.UV + 1; i++) {
                ingredientBlacklist.addIngredientToBlacklist(FLUID_SOLIDIFIER[i].getStackForm());
            }
        }

        if (TKCYAConfigHolder.energyOverhaul.disableGasTurbinesOverhaul) {
            for (int i = GTValues.LV; i < GTValues.HV; i++) {
                ingredientBlacklist.addIngredientToBlacklist(GAS_TURBINE[i].getStackForm());
            }
            ingredientBlacklist.addIngredientToBlacklist(LARGE_GAS_TURBINE.getStackForm());
        }

        if (TKCYAConfigHolder.miscOverhaul.enableElectrolysisOverhaul) {
            for (int i = GTValues.LV; i < GTValues.UV; i++) {
                ingredientBlacklist.addIngredientToBlacklist(ELECTROLYZER[i].getStackForm());
            }
        }

        if (TKCYAConfigHolder.storageOverhaul.enableDrumsOverhaul) {
            ingredientBlacklist.addIngredientToBlacklist(WOODEN_DRUM.getStackForm());
            ingredientBlacklist.addIngredientToBlacklist(BRONZE_DRUM.getStackForm());
            ingredientBlacklist.addIngredientToBlacklist(STEEL_DRUM.getStackForm());
            ingredientBlacklist.addIngredientToBlacklist(ALUMINIUM_DRUM.getStackForm());
            ingredientBlacklist.addIngredientToBlacklist(STAINLESS_STEEL_DRUM.getStackForm());
        }

        if (TKCYAConfigHolder.storageOverhaul.enableMultiblockTanksOverhaul) {
            ingredientBlacklist.addIngredientToBlacklist(STEEL_TANK.getStackForm());
            ingredientBlacklist.addIngredientToBlacklist(WOODEN_TANK.getStackForm());

            ingredientBlacklist.addIngredientToBlacklist(STEEL_TANK_VALVE.getStackForm());
        }

        if (TKCYAConfigHolder.storageOverhaul.removeOPTanks) {
            for (int i = 0; i < QUANTUM_TANK.length; i++) {
                ingredientBlacklist.addIngredientToBlacklist(QUANTUM_TANK[i].getStackForm());
                ingredientBlacklist.addIngredientToBlacklist(QUANTUM_CHEST[i].getStackForm());
            }
        }

        if (TKCYAConfigHolder.crackingOverhaul.enableCrackingOverhaul) {
            ingredientBlacklist.addIngredientToBlacklist(CRACKER.getStackForm());
        }


        if (TKCYAConfigHolder.miscOverhaul.disableHighTierMachines) {

            /*

            for ()

            for (int i = GTValues.LuV; i < GTValues.UV + 1; i++) {
                ingredientBlacklist.addIngredientToBlacklist(GAS_TURBINE[i].getStackForm());
            }

             */
        }




    }

}
