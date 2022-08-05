package tekcays_addon.loaders.recipe;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.loaders.recipe.CraftingComponent;
import gregtech.loaders.recipe.MetaTileEntityLoader;
import tekcays_addon.common.TKCYAConfigHolder;
import gregicality.multiblocks.common.metatileentities.GCYMMetaTileEntities;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockPump;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.ModHandler.removeRecipeByName;
import static gregtech.api.recipes.ModHandler.removeTieredRecipeByName;
import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.api.unification.TKCYAMaterials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.blocks.BlockSteamCasing.SteamCasingType.WOOD_WALL;
import static gregtech.loaders.recipe.CraftingComponent.*;
import static tekcays_addon.common.metatileentities.TKCYAMetaTileEntities.*;
import static net.minecraft.init.Items.BRICK;

public class TKCYAMetaTileEntityLoader {

    public static void init() {
        if (TKCYAConfigHolder.miscOverhaul.enableFoilOverhaul) {
            MetaTileEntityLoader.registerMachineRecipe(CLUSTER_MILL,
                    "MMM", "CHC", "MMM",
                    'M', MOTOR, 'C', CIRCUIT, 'H', HULL);
        }

        if (TKCYAConfigHolder.meltingOverhaul.enableCastingOverhaul) {
            MetaTileEntityLoader.registerMachineRecipe(ELECTRIC_CASTING_TABLE,
                    "NPN", "CHC", "NPN",
                    'P', PUMP, 'N', PIPE_NORMAL, 'H', HULL, 'C', CIRCUIT);

            ModHandler.addShapedRecipe(true, "casting_table", CASTING_TABLE.getStackForm(),
                    "PPP", "PhP", "PPP", 'P', MetaItems.COKE_OVEN_BRICK);
            ModHandler.addShapedRecipe(true, "primitive_fermenter", PRIMITIVE_FERMENTER.getStackForm(),
                    " h ", " P ", " w ", 'P', MetaBlocks.STEAM_CASING.getItemVariant(WOOD_WALL));
        }

        if (TKCYAConfigHolder.miscOverhaul.enableMagneticOverhaul) {
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

        if (TKCYAConfigHolder.miscOverhaul.enableGalvanizedSteel) {
            ModHandler.addShapedRecipe(true, "primitive_bath", PRIMITIVE_BATH.getStackForm(),
                    "BBB", "BhB", "BBB", 'B', BRICK);
        }

        if (TKCYAConfigHolder.distillationOverhaul.enableDistillationOverhaul) {
            ModHandler.addShapedRecipe(true, "pump_machine_lv", TKCYAMetaBlocks.PUMP_MACHINE.getItemVariant(BlockPump.PumpType.PUMP_MACHINE_LV),
                    "CBC", "PHP", "MRM",
                    'C', CIRCUIT.getIngredient(LV),
                    'B', MetaItems.FLUID_REGULATOR_LV.getStackForm(),
                    'M', MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                    'H', MetaTileEntities.HULL[1].getStackForm(),
                    'P', new UnificationEntry(pipeNormalFluid, TinAlloy),
                    'R', new UnificationEntry(rotor, Steel));

            ModHandler.addShapedRecipe(true, "pump_machine_mv", TKCYAMetaBlocks.PUMP_MACHINE.getItemVariant(BlockPump.PumpType.PUMP_MACHINE_MV),
                    "CBC", "PHP", "MRM",
                    'C', CIRCUIT.getIngredient(MV),
                    'B', MetaItems.FLUID_REGULATOR_MV.getStackForm(),
                    'M', MetaItems.ELECTRIC_MOTOR_MV.getStackForm(),
                    'H', MetaTileEntities.HULL[2].getStackForm(),
                    'P', new UnificationEntry(pipeNormalFluid, GalvanizedSteel),
                    'R', new UnificationEntry(rotor, GalvanizedSteel));

            ModHandler.addShapedRecipe(true, "pump_machine_hv", TKCYAMetaBlocks.PUMP_MACHINE.getItemVariant(BlockPump.PumpType.PUMP_MACHINE_HV),
                    "CBC", "PHP", "MRM",
                    'C', CIRCUIT.getIngredient(HV),
                    'B', MetaItems.FLUID_REGULATOR_HV.getStackForm(),
                    'M', MetaItems.ELECTRIC_MOTOR_HV.getStackForm(),
                    'H', MetaTileEntities.HULL[3].getStackForm(),
                    'P', new UnificationEntry(pipeNormalFluid, StainlessSteel),
                    'R', new UnificationEntry(rotor, Monel));

            ModHandler.addShapedRecipe(true, "pump_machine_ev", TKCYAMetaBlocks.PUMP_MACHINE.getItemVariant(BlockPump.PumpType.PUMP_MACHINE_EV),
                    "CBC", "PHP", "MRM",
                    'C', CIRCUIT.getIngredient(EV),
                    'B', MetaItems.FLUID_REGULATOR_EV.getStackForm(),
                    'M', MetaItems.ELECTRIC_MOTOR_EV.getStackForm(),
                    'H', MetaTileEntities.HULL[4].getStackForm(),
                    'P', new UnificationEntry(pipeNormalFluid, Titanium),
                    'R', new UnificationEntry(rotor, StainlessSteel));
        }

    }

}
