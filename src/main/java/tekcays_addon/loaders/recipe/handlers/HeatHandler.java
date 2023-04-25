package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.metatileentities.MetaTileEntities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import tekcays_addon.common.metatileentities.TKCYAMetaTileEntities;


import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.gtapi.unification.TKCYAMaterials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static tekcays_addon.gtapi.unification.material.ore.TKCYAOrePrefix.curvedPlate;
import static tekcays_addon.gtapi.utils.FuelHeaterTiers.FUEL_HEATERS;


public class HeatHandler {

    public static void init() {

        //Heat Acceptor
        ModHandler.addShapedRecipe(true, "heat_acceptor", TKCYAMetaTileEntities.HEAT_ACCEPTOR[0].getStackForm(),
                "NCN", "hLw", "NCN",
                'L', new UnificationEntry(stickLong, Copper),
                'C', new UnificationEntry(plateDouble, Copper),
                'N', new UnificationEntry(plate, Invar));

        //Electric Heater
        ModHandler.addShapedRecipe(true, "electric_heater_lv", TKCYAMetaTileEntities.ELECTRIC_HEATER[0].getStackForm(),
                "SWS", "WHW", "SWd",
                'S', new UnificationEntry(screw, GalvanizedSteel),
                'W', new UnificationEntry(wireGtSingle, Copper),
                'H', MetaTileEntities.HULL[1].getStackForm());

        ModHandler.addShapedRecipe(true, "electric_heater_mv", TKCYAMetaTileEntities.ELECTRIC_HEATER[1].getStackForm(),
                "SWS", "WHW", "SWd",
                'S', new UnificationEntry(screw, Aluminium),
                'W', new UnificationEntry(wireGtDouble, Constantan),
                'H', MetaTileEntities.HULL[2].getStackForm());

        ModHandler.addShapedRecipe(true, "electric_heater_hv", TKCYAMetaTileEntities.ELECTRIC_HEATER[2].getStackForm(),
                "SWS", "WHW", "SWd",
                'S', new UnificationEntry(screw, StainlessSteel),
                'W', new UnificationEntry(wireGtQuadruple, Kanthal),
                'H', MetaTileEntities.HULL[3].getStackForm());

        ModHandler.addShapedRecipe(true, "electric_heater_ev", TKCYAMetaTileEntities.ELECTRIC_HEATER[3].getStackForm(),
                "SWS", "WHW", "SWd",
                'S', new UnificationEntry(screw, Titanium),
                'W', new UnificationEntry(wireGtOctal, Nichrome),
                'H', MetaTileEntities.HULL[4].getStackForm());

        ModHandler.addShapedRecipe(true, "electric_heater_iv", TKCYAMetaTileEntities.ELECTRIC_HEATER[4].getStackForm(),
                "SWS", "WHW", "SWd",
                'S', new UnificationEntry(screw, TungstenCarbide),
                'W', new UnificationEntry(wireGtHex, SiliconCarbide),
                'H', MetaTileEntities.HULL[5].getStackForm());

        //Solid fuel burner
        ModHandler.addShapedRecipe(true, "brick_solid_fuel_heater", TKCYAMetaTileEntities.SOLID_FUEL_HEATER[0].getStackForm(),
                "BBB", "BBB", "BSB",
                'S', new UnificationEntry(stick, Wood),
                'B', new ItemStack(Items.BRICK));

        for (int i = 1; i < FUEL_HEATERS.size(); i++) {
            Material m = FUEL_HEATERS.get(i).getMaterial();
            ModHandler.addShapedRecipe(true, m.getUnlocalizedName() + "_solid_fuel_heater", TKCYAMetaTileEntities.SOLID_FUEL_HEATER[i].getStackForm(),
                    "PDP", "PwP", "BBB",
                    'D', new UnificationEntry(plateDouble, Copper),
                    'P', new UnificationEntry(plate, m),
                    'B', new ItemStack(Blocks.BRICK_BLOCK));

            ModHandler.addShapedRecipe(true, m.getUnlocalizedName() + "_liquid_fuel_heater", TKCYAMetaTileEntities.LIQUID_FUEL_HEATER[i].getStackForm(),
                    "PDP", "SwS", "BBB",
                    'D', new UnificationEntry(plateDouble, Copper),
                    'P', new UnificationEntry(plate, m),
                    'S', new UnificationEntry(pipeSmallFluid, m),
                    'B', new ItemStack(Blocks.BRICK_BLOCK));

            ModHandler.addShapedRecipe(true, m.getUnlocalizedName() + "_gas_fuel_heater", TKCYAMetaTileEntities.GAS_FUEL_HEATER[i].getStackForm(),
                    "PDP", "BwB", "BSB",
                    'D', new UnificationEntry(plateDouble, Copper),
                    'P', new UnificationEntry(plate, m),
                    'S', new UnificationEntry(pipeSmallFluid, m),
                    'B', new ItemStack(Blocks.BRICK_BLOCK));

            ModHandler.addShapedRecipe(true, m.getUnlocalizedName() + "_fluidized_fuel_heater", TKCYAMetaTileEntities.FLUIDIZED_FUEL_HEATER[i].getStackForm(),
                    "PDP", "CwC", "BRB",
                    'D', new UnificationEntry(plateDouble, Copper),
                    'P', new UnificationEntry(plate, m),
                    'C', new UnificationEntry(curvedPlate, m),
                    'R', new UnificationEntry(rotor, m),
                    'B', new ItemStack(Blocks.BRICK_BLOCK));
        }



    }

}
