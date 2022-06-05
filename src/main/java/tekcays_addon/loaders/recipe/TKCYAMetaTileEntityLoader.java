package tekcays_addon.loaders.recipe;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.loaders.recipe.MetaTileEntityLoader;
import tekcays_addon.common.TKCYAConfigHolder;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.ModHandler.removeRecipeByName;
import static gregtech.api.recipes.ModHandler.removeTieredRecipeByName;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.api.unification.ore.OrePrefix.cableGtSingle;
import static gregtech.loaders.recipe.CraftingComponent.*;
import static tekcays_addon.common.metatileentities.TKCYAMetaTileEntities.*;
import gregicality.multiblocks.common.metatileentities.GCYMMetaTileEntities;

public class TKCYAMetaTileEntityLoader {

    public static void init() {
        if (TKCYAConfigHolder.foilOverhaul.enableFoilOverhaul) {
            MetaTileEntityLoader.registerMachineRecipe(CLUSTER_MILL,
                    "MMM", "CHC", "MMM",
                    'M', MOTOR, 'C', CIRCUIT, 'H', HULL);
        }

        if (TKCYAConfigHolder.magneticOverhaul.enableMagneticOverhaul) {
            removeTieredRecipeByName("gregtech:gregtech.machine.polarizer.", LV, UV); //removed recipes for GTCEu polarizers
            MetaTileEntityLoader.registerMachineRecipe(ADVANCED_POLARIZER,
                    "ZSZ", "WMW", "ZSZ",
                    'M', HULL, 'S', STICK_ELECTROMAGNETIC, 'Z', COIL_ELECTRIC, 'W', CABLE);

            // Remove GCYM Large Polarizer Controller recipe and replace the GTCEu Polaryzer by the Advanced Polarizer
            removeRecipeByName("large_polarizer");
            ModHandler.addShapedRecipe(true, "large_polarizer", GCYMMetaTileEntities.LARGE_POLARIZER.getStackForm(),
                    "PSP", "BCO", "WSW",
                    'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                    'B', ADVANCED_POLARIZER[4].getStackForm(),
                    'O', MetaTileEntities.ELECTROMAGNETIC_SEPARATOR[IV].getStackForm(),
                    'S', new UnificationEntry(wireGtQuadruple, Osmium),
                    'P', new UnificationEntry(plate, BlackSteel),
                    'W', new UnificationEntry(cableGtSingle, Platinum));

        }
    }

}
