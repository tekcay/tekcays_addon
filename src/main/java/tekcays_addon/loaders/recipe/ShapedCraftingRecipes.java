package tekcays_addon.loaders.recipe;

import static gregtech.api.unification.ore.OrePrefix.cableGtSingle;
import static gregtech.api.unification.ore.OrePrefix.plate;
import static net.minecraft.init.Blocks.IRON_BARS;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import tekcays_addon.common.items.TKCYAMetaItems;

public class ShapedCraftingRecipes {

    public static void ulvPotin() {
        ModHandler.addShapedRecipe("ulv_machine_casing",
                MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.ULV),
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
