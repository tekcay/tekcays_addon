package tekcays_addon.gtapi.unification.material.ore;

import static gregtech.api.GTValues.W;
import static gregtech.api.unification.OreDictUnifier.registerOre;
import static gregtech.api.unification.material.Materials.Wood;
import static tekcays_addon.gtapi.unification.material.ore.TKCYAOrePrefix.bottleGlass;
import static tekcays_addon.gtapi.unification.material.ore.TKCYAOrePrefix.cutWood;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockCutWood;

public class OreDictAdditions {

    public static void misc() {
        registerOre(new ItemStack(Items.GLASS_BOTTLE, 1, W), bottleGlass, null);
    }

    public static void woodOreInit() {
        registerOre(TKCYAMetaBlocks.BLOCK_CUT_WOOD.getItemVariant(BlockCutWood.CutWoodType.ACACIA), cutWood, Wood);
        registerOre(TKCYAMetaBlocks.BLOCK_CUT_WOOD.getItemVariant(BlockCutWood.CutWoodType.JUNGLE), cutWood, Wood);
        registerOre(TKCYAMetaBlocks.BLOCK_CUT_WOOD.getItemVariant(BlockCutWood.CutWoodType.OAK), cutWood, Wood);
        registerOre(TKCYAMetaBlocks.BLOCK_CUT_WOOD.getItemVariant(BlockCutWood.CutWoodType.RUBBER), cutWood, Wood);
        registerOre(TKCYAMetaBlocks.BLOCK_CUT_WOOD.getItemVariant(BlockCutWood.CutWoodType.BIRCH), cutWood, Wood);
        registerOre(TKCYAMetaBlocks.BLOCK_CUT_WOOD.getItemVariant(BlockCutWood.CutWoodType.SPRUCE), cutWood, Wood);
    }
}
