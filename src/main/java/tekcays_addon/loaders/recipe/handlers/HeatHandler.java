package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.metatileentities.MetaTileEntities;
import tekcays_addon.common.metatileentities.TKCYAMetaTileEntities;


import static gregicality.science.api.unification.materials.GCYSMaterials.SiliconCarbide;
import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.api.unification.TKCYAMaterials.*;
import static gregtech.api.unification.ore.OrePrefix.*;


public class HeatHandler {

    public static void init() {

        //Heat Acceptor
        ModHandler.addShapedRecipe("heat_acceptor", TKCYAMetaTileEntities.HEAT_ACCEPTOR[0].getStackForm(),
                "NCN", "hLw", "NCN",
                'L', new UnificationEntry(stickLong, Copper),
                'C', new UnificationEntry(plateDouble, Copper),
                'N', new UnificationEntry(plate, Invar));

        //Electric Heater
        ModHandler.addShapedRecipe("electric_heater_lv", TKCYAMetaTileEntities.ELECTRIC_HEATER[0].getStackForm(),
                "SWS", "WHW", "SWd",
                'S', new UnificationEntry(screw, GalvanizedSteel),
                'W', new UnificationEntry(wireGtSingle, Copper),
                'H', MetaTileEntities.HULL[1].getStackForm());

        ModHandler.addShapedRecipe("electric_heater_mv", TKCYAMetaTileEntities.ELECTRIC_HEATER[1].getStackForm(),
                "SWS", "WHW", "SWd",
                'S', new UnificationEntry(screw, Aluminium),
                'W', new UnificationEntry(wireGtDouble, Constantan),
                'H', MetaTileEntities.HULL[2].getStackForm());

        ModHandler.addShapedRecipe("electric_heater_hv", TKCYAMetaTileEntities.ELECTRIC_HEATER[2].getStackForm(),
                "SWS", "WHW", "SWd",
                'S', new UnificationEntry(screw, StainlessSteel),
                'W', new UnificationEntry(wireGtQuadruple, Kanthal),
                'H', MetaTileEntities.HULL[3].getStackForm());

        ModHandler.addShapedRecipe("electric_heater_ev", TKCYAMetaTileEntities.ELECTRIC_HEATER[3].getStackForm(),
                "SWS", "WHW", "SWd",
                'S', new UnificationEntry(screw, Titanium),
                'W', new UnificationEntry(wireGtOctal, Nichrome),
                'H', MetaTileEntities.HULL[4].getStackForm());

        ModHandler.addShapedRecipe("electric_heater_iv", TKCYAMetaTileEntities.ELECTRIC_HEATER[4].getStackForm(),
                "SWS", "WHW", "SWd",
                'S', new UnificationEntry(screw, TungstenCarbide),
                'W', new UnificationEntry(wireGtHex, SiliconCarbide),
                'H', MetaTileEntities.HULL[5].getStackForm());

    }

}
