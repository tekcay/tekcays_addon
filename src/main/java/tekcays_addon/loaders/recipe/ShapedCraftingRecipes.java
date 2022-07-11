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
import tekcays_addon.common.items.TKCYAMetaItems;
import tekcays_addon.common.metatileentities.TKCYAMetaTileEntities;

import static gregtech.common.blocks.BlockMetalCasing.MetalCasingType.STEEL_SOLID;
import static gregtech.common.blocks.BlockSteamCasing.SteamCasingType.WOOD_WALL;
import static net.minecraft.init.Blocks.IRON_BARS;
import static tekcays_addon.api.unification.material.ore.TKCYAOrePrefix.*;
import static tekcays_addon.api.utils.TKCYAValues.MOLD_MATERIALS;

public class ShapedCraftingRecipes{

    public static void molds() {

        for (Material m : MOLD_MATERIALS) {

            ModHandler.addShapedRecipe("mold_empty" + m.getUnlocalizedName(), OreDictUnifier.get(moldEmpty, m),
                    "hf", "PP", "PP", 'P', OreDictUnifier.get(OrePrefix.plate, m));
            ModHandler.addShapedRecipe("mold_rotor" + m.getUnlocalizedName(), OreDictUnifier.get(moldEmpty, m),
                    "  h", " S ", "   ", 'S', OreDictUnifier.get(moldEmpty, m));
            ModHandler.addShapedRecipe("mold_gear_small" + m.getUnlocalizedName(), OreDictUnifier.get(moldGearSmall, m),
                    "   ", "   ", "h S", 'S', OreDictUnifier.get(moldEmpty, m));
            ModHandler.addShapedRecipe("mold_cylinder" + m.getUnlocalizedName(), OreDictUnifier.get(moldCylinder, m),
                    "  S", "   ", "  h", 'S', OreDictUnifier.get(moldEmpty, m));
            ModHandler.addShapedRecipe("mold_ball" + m.getUnlocalizedName(), OreDictUnifier.get(moldBall, m),
                    "   ", " S ", "h  ", 'S', OreDictUnifier.get(moldEmpty, m));
            ModHandler.addShapedRecipe("mold_ingot" + m.getUnlocalizedName(), OreDictUnifier.get(moldIngot, m),
                    "   ", " S ", " h ", 'S', OreDictUnifier.get(moldEmpty, m));
            ModHandler.addShapedRecipe("mold_bolt" + m.getUnlocalizedName(), OreDictUnifier.get(moldBolt, m),
                    "   ", " S ", "  h", 'S', OreDictUnifier.get(moldEmpty, m));
            ModHandler.addShapedRecipe("mold_gear" + m.getUnlocalizedName(), OreDictUnifier.get(moldGear, m),
                    "   ", " Sh", "   ", 'S', OreDictUnifier.get(moldEmpty, m));
            ModHandler.addShapedRecipe("mold_plate" + m.getUnlocalizedName(), OreDictUnifier.get(moldPlate, m),
                    " h ", " S ", "   ", 'S', OreDictUnifier.get(moldEmpty, m));
        
        }
    }

    public static void galvanizedSteel() {
        ModHandler.addShapedRecipe("lv_machine_casing", MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.LV),
        "PPP", "PwP", "PPP", 'P', OreDictUnifier.get(OrePrefix.plate, TKCYAMaterials.GalvanizedSteel));

    }

    public static void gasCollector() {
        ModHandler.addShapedRecipe("gas_collector_MetaItem", TKCYAMetaItems.GAS_COLLECTOR.getStackForm(),
                "SSS", "SwS", "SSS", 'S', IRON_BARS);
    }

    public static void drums() {

        ModHandler.addShapedRecipe(true, "wooden_barrel", TKCYAMetaTileEntities.WOODEN_DRUM.getStackForm(), "rSs", "PRP", "PRP", 'S', MetaItems.STICKY_RESIN.getStackForm(), 'P', "plankWood", 'R', new UnificationEntry(OrePrefix.stickLong, Materials.Iron));
        ModHandler.addShapedRecipe(true, "bronze_drum", TKCYAMetaTileEntities.BRONZE_DRUM.getStackForm(), " h ", "PRP", "PRP", 'P', new UnificationEntry(OrePrefix.plate, Materials.Bronze), 'R', new UnificationEntry(OrePrefix.stickLong, Materials.Bronze));

        ModHandler.addShapedRecipe(true, "wood_multiblock_tank", TKCYAMetaTileEntities.WOODEN_TANK.getStackForm(), " R ", "rCs", " R ", 'R', new UnificationEntry(OrePrefix.ring, Materials.Lead), 'C', MetaBlocks.STEAM_CASING.getItemVariant(WOOD_WALL));
        ModHandler.addShapedRecipe(true, "steel_multiblock_tank", TKCYAMetaTileEntities.STEEL_TANK.getStackForm(), " R ", "hCw", " R ", 'R', new UnificationEntry(OrePrefix.ring, Materials.Steel), 'C', MetaBlocks.METAL_CASING.getItemVariant(STEEL_SOLID));
        //ModHandler.addShapedRecipe(true, "wood_tank_valve", TKCYAMetaTileEntities.WOODEN_TANK_VALVE.getStackForm(), " R ", "rCs", " O ", 'O', new UnificationEntry(OrePrefix.rotor, Materials.Lead), 'R', new UnificationEntry(OrePrefix.ring, Materials.Lead), 'C', MetaBlocks.STEAM_CASING.getItemVariant(WOOD_WALL));
        //ModHandler.addShapedRecipe(true, "steel_tank_valve", TKCYAMetaTileEntities.STEEL_TANK_VALVE.getStackForm(), " R ", "hCw", " O ", 'O', new UnificationEntry(OrePrefix.rotor, Materials.Steel), 'R', new UnificationEntry(OrePrefix.ring, Materials.Steel), 'C', MetaBlocks.METAL_CASING.getItemVariant(STEEL_SOLID));



    }

}
