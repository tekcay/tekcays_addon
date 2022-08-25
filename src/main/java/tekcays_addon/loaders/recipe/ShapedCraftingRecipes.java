package tekcays_addon.loaders.recipe;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import tekcays_addon.api.unification.TKCYAMaterials;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockLargeMultiblockCasing;
import tekcays_addon.common.items.TKCYAMetaItems;
import tekcays_addon.common.metatileentities.TKCYAMetaTileEntities;

import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.blocks.BlockSteamCasing.SteamCasingType.WOOD_WALL;
import static net.minecraft.init.Blocks.IRON_BARS;
import static tekcays_addon.api.unification.material.ore.TKCYAOrePrefix.*;
import static tekcays_addon.api.utils.TKCYAValues.MOLD_MATERIALS;

public class ShapedCraftingRecipes{

    public static void molds() {

        for (Material m : MOLD_MATERIALS) {

            ModHandler.addShapedRecipe("mold_empty" + m.getUnlocalizedName(), OreDictUnifier.get(moldEmpty, m),
                    "hf", "PP", "PP", 'P', new UnificationEntry(plate, m));
            ModHandler.addShapedRecipe("mold_rotor" + m.getUnlocalizedName(), OreDictUnifier.get(moldEmpty, m),
                    "  h", " S ", "   ", 'S', new UnificationEntry(moldEmpty, m));
            ModHandler.addShapedRecipe("mold_gear_small" + m.getUnlocalizedName(), OreDictUnifier.get(moldGearSmall, m),
                    "   ", " S ", "h  ", 'S', new UnificationEntry(moldEmpty, m));
            ModHandler.addShapedRecipe("mold_cylinder" + m.getUnlocalizedName(), OreDictUnifier.get(moldCylinder, m),
                    "   ", " S ", "  h", 'S', new UnificationEntry(moldEmpty, m));
            ModHandler.addShapedRecipe("mold_ball" + m.getUnlocalizedName(), OreDictUnifier.get(moldBall, m),
                    "   ", " S ", "h  ", 'S', new UnificationEntry(moldEmpty, m));
            ModHandler.addShapedRecipe("mold_ingot" + m.getUnlocalizedName(), OreDictUnifier.get(moldIngot, m),
                    "   ", " S ", " h ", 'S', new UnificationEntry(moldEmpty, m));
            ModHandler.addShapedRecipe("mold_bolt" + m.getUnlocalizedName(), OreDictUnifier.get(moldBolt, m),
                    "   ", " S ", "  h", 'S', new UnificationEntry(moldEmpty, m));
            ModHandler.addShapedRecipe("mold_gear" + m.getUnlocalizedName(), OreDictUnifier.get(moldGear, m),
                    "   ", " Sh", "   ", 'S', new UnificationEntry(moldEmpty, m));
            ModHandler.addShapedRecipe("mold_plate" + m.getUnlocalizedName(), OreDictUnifier.get(moldPlate, m),
                    " h ", " S ", "   ", 'S', new UnificationEntry(moldEmpty, m));
            ModHandler.addShapedRecipe("mold_block" + m.getUnlocalizedName(), OreDictUnifier.get(moldBlock, m),
                    "   ", "hS ", "   ", 'S', new UnificationEntry(moldEmpty, m));
            ModHandler.addShapedRecipe("mold_bottle" + m.getUnlocalizedName(), OreDictUnifier.get(moldBottle, m),
                    "   ", " S ", "  h", 'S', new UnificationEntry(moldEmpty, m));
            ModHandler.addShapedRecipe("mold_ring" + m.getUnlocalizedName(), OreDictUnifier.get(moldRing, m),
                    "   ", " S ", " x ", 'S', new UnificationEntry(moldEmpty, m));
            ModHandler.addShapedRecipe("mold_rotor" + m.getUnlocalizedName(), OreDictUnifier.get(moldRotor, m),
                    "  h", " S ", "   ", 'S', new UnificationEntry(moldEmpty, m));
        
        }
    }

    public static void galvanizedSteel() {
        ModHandler.addShapedRecipe("lv_machine_casing", MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.LV),
        "PPP", "PwP", "PPP", 'P', new UnificationEntry(plate, TKCYAMaterials.GalvanizedSteel));

        ModHandler.addShapedRecipe("ulv_machine_casing", MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.ULV),
                "PPP", "PwP", "PPP", 'P', new UnificationEntry(plate, Materials.Potin));

        ModHandler.addShapedRecipe("ulv_machine_hull", MetaTileEntities.HULL[0].getStackForm(),
                " w ", "PBP", "WCW",
                'W', new UnificationEntry(cableGtSingle, Materials.RedAlloy),
                'P', new UnificationEntry(plate, Materials.Potin),
                'B', new UnificationEntry(plate, Materials.Brass),
                'C', MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.ULV));
    }

    public static void gasCollector() {
        ModHandler.addShapedRecipe("gas_collector_MetaItem", TKCYAMetaItems.GAS_COLLECTOR.getStackForm(),
                "SSS", "SwS", "SSS", 'S', IRON_BARS);
    }

    public static void drums() {

        ModHandler.addShapedRecipe(true, "drum_wood", TKCYAMetaTileEntities.WOODEN_DRUM.getStackForm(),
                "rSs", "PRP", "PRP",
                'S', MetaItems.STICKY_RESIN.getStackForm(),
                'P', "plankWood", 'R', new UnificationEntry(stickLong, Materials.Iron));

        ModHandler.addShapedRecipe(true, "drum_bronze", TKCYAMetaTileEntities.BRONZE_DRUM.getStackForm(),
                " w ", "PRP", "PRP",
                'P', new UnificationEntry(plate, Materials.Bronze),
                'R', new UnificationEntry(OrePrefix.stickLong, Materials.Bronze));

    }

    public static void tanksAndValves() {

        ModHandler.addShapedRecipe(true, "wood_tank", TKCYAMetaTileEntities.WOODEN_TANK.getStackForm(),
                " R ", "rCs", " R ",
                'R', new UnificationEntry(OrePrefix.ring, Materials.Lead),
                'C', MetaBlocks.STEAM_CASING.getItemVariant(WOOD_WALL));

        ModHandler.addShapedRecipe(true, "steel_tank", TKCYAMetaTileEntities.STEEL_TANK.getStackForm(),
                " R ", "hCw", " R ",
                'R', new UnificationEntry(OrePrefix.ring, Materials.Steel),
                'C', TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.STEEL_WALL));

        ModHandler.addShapedRecipe(true, "stainless_steel_tank", TKCYAMetaTileEntities.STAINLESS_STEEL_TANK.getStackForm(),
                " R ", "hCw", " R ",
                'R', new UnificationEntry(OrePrefix.ring, Materials.StainlessSteel),
                'C', TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.STAINLESS_STEEL_WALL));

        ModHandler.addShapedRecipe(true, "galvanized_steel_tank", TKCYAMetaTileEntities.GALVANIZED_STEEL_TANK.getStackForm(),
                " R ", "hCw", " R ",
                'R', new UnificationEntry(OrePrefix.ring, TKCYAMaterials.GalvanizedSteel),
                'C', TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.GALVANIZED_STEEL_WALL));



        ModHandler.addShapedRecipe(true, "tank_valve.steel", TKCYAMetaTileEntities.STEEL_TANK_VALVE.getStackForm(),
                " R ", "hCw", " O ",
                'O', new UnificationEntry(OrePrefix.rotor, Materials.Steel),
                'R', new UnificationEntry(OrePrefix.ring, Materials.Steel), 'C', TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.STEEL_WALL));

        ModHandler.addShapedRecipe(true, "tank_valve.stainless_steel",
                TKCYAMetaTileEntities.STAINLESS_STEEL_TANK_VALVE.getStackForm(),
                " R ", "hCw", " O ",
                'O', new UnificationEntry(OrePrefix.rotor, Materials.StainlessSteel),
                'R', new UnificationEntry(OrePrefix.ring, Materials.StainlessSteel),
                'C', TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.STAINLESS_STEEL_WALL));

        ModHandler.addShapedRecipe(true, "tank_valve.galvanized_steel",
                TKCYAMetaTileEntities.GALVANIZED_STEEL_TANK_VALVE.getStackForm(),
                " R ", "hCw", " O ",
                'O', new UnificationEntry(OrePrefix.rotor, TKCYAMaterials.GalvanizedSteel),
                'R', new UnificationEntry(OrePrefix.ring, TKCYAMaterials.GalvanizedSteel),
                'C', TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.GALVANIZED_STEEL_WALL));


        ModHandler.addShapedRecipe(true, "wall.steel",
                TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.STEEL_WALL),
                "hdw", "PP ", "PP ",
                'P', new UnificationEntry(plate, Materials.Steel));

        ModHandler.addShapedRecipe(true, "wall.stainless_steel",
                TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.STAINLESS_STEEL_WALL),
                "hdw", "PP ", "PP ",
                'P', new UnificationEntry(plate, Materials.StainlessSteel));

        ModHandler.addShapedRecipe(true, "wall.galvanized_steel",
                TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.GALVANIZED_STEEL_WALL),
                "hdw", "PP ", "PP ",
                'P', new UnificationEntry(plate, TKCYAMaterials.GalvanizedSteel));

    }

}
