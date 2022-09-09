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
            ModHandler.addShapedRecipe("mold_rotor" + m.getUnlocalizedName(), OreDictUnifier.get(moldRotor, m),
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

        
        }
    }

    public static void ulvPotin() {

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



}
