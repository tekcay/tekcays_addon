package tekcays_addon.gtapi.unification.material.info;

import gregtech.api.GTValues;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.wood.BlockGregPlanks;
import gregtech.common.items.MetaItems;
import gregtech.loaders.WoodTypeEntry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockCutWood;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static gregtech.api.unification.material.Materials.TreatedWood;

@AllArgsConstructor
@Getter
/**
 * From {@link WoodRecipeLoader}, used in {@link WoodTypeEntry}
 */
public class WoodTypeEntryWrapper {
    
    private WoodTypeEntry woodTypeEntry;
    private ItemStack cutWood;

    private static final String mcModId = "minecraft";


    private static final WoodTypeEntry OAK_WOOD_ENTRY = new WoodTypeEntry.Builder(mcModId, "oak")
            .planks(new ItemStack(Blocks.PLANKS), "oak_planks")
            .log(new ItemStack(Blocks.LOG)).removeCharcoalRecipe()
            .door(new ItemStack(Items.OAK_DOOR), "wooden_door")
            .slab(new ItemStack(Blocks.WOODEN_SLAB), "oak_wooden_slab")
            .fence(new ItemStack(Blocks.OAK_FENCE), "fence")
            .fenceGate(new ItemStack(Blocks.OAK_FENCE_GATE), "fence_gate")
            .stairs(new ItemStack(Blocks.OAK_STAIRS))
            .boat(new ItemStack(Items.BOAT), "boat")
            .registerAllUnificationInfo()
            .build();

    private static final WoodTypeEntry BIRCH_WOOD_ENTRY = new WoodTypeEntry.Builder(mcModId, "birch")
            .planks(new ItemStack(Blocks.PLANKS, 1, 2), "birch_planks")
            .log(new ItemStack(Blocks.LOG, 1, 2)).removeCharcoalRecipe()
            .door(new ItemStack(Items.BIRCH_DOOR), "birch_door")
            .slab(new ItemStack(Blocks.WOODEN_SLAB), "brich_wooden_slab")
            .fence(new ItemStack(Blocks.BIRCH_FENCE), "birch_fence")
            .fenceGate(new ItemStack(Blocks.BIRCH_FENCE_GATE), "birch_fence_gate")
            .stairs(new ItemStack(Blocks.BIRCH_STAIRS))
            .boat(new ItemStack(Items.BIRCH_BOAT), "birch_boat")
            .registerAllUnificationInfo()
            .build();

    private static final WoodTypeEntry SPRUCE_WOOD_ENTRY = new WoodTypeEntry.Builder(mcModId, "spruce")
            .planks(new ItemStack(Blocks.PLANKS, 1, 1), "spruce_planks")
            .log(new ItemStack(Blocks.LOG, 1, 1)).removeCharcoalRecipe()
            .door(new ItemStack(Items.SPRUCE_DOOR), "spruce_door")
            .slab(new ItemStack(Blocks.WOODEN_SLAB), "spruce_slab")
            .fence(new ItemStack(Blocks.SPRUCE_FENCE), "spruce_fence")
            .fenceGate(new ItemStack(Blocks.SPRUCE_FENCE_GATE), "spruce_fence_gate")
            .stairs(new ItemStack(Blocks.SPRUCE_STAIRS))
            .boat(new ItemStack(Items.SPRUCE_BOAT), "spruce_boat")
            .registerAllUnificationInfo()
            .build();

    private static final WoodTypeEntry JUNGLE_WOOD_ENTRY = new WoodTypeEntry.Builder(mcModId, "jungle")
            .planks(new ItemStack(Blocks.PLANKS, 1, 3), "jungle_planks")
            .log(new ItemStack(Blocks.LOG, 1, 3)).removeCharcoalRecipe()
            .door(new ItemStack(Items.JUNGLE_DOOR), "jungle_door")
            .slab(new ItemStack(Blocks.WOODEN_SLAB),"jungle_slab")
            .fence(new ItemStack(Blocks.JUNGLE_FENCE), "jungle_fence")
            .fenceGate(new ItemStack(Blocks.JUNGLE_FENCE_GATE), "jungle_fence_gate")
            .stairs(new ItemStack(Blocks.JUNGLE_STAIRS))
            .boat(new ItemStack(Items.JUNGLE_BOAT), "jungle_boat")
            .registerAllUnificationInfo()
            .build();

    private static final WoodTypeEntry ACACIA_WOOD_ENTRY = new WoodTypeEntry.Builder(mcModId, "acacia")
            .planks(new ItemStack(Blocks.PLANKS, 1, 4), "acacia_planks")
            .log(new ItemStack(Blocks.LOG2)).removeCharcoalRecipe()
            .door(new ItemStack(Items.ACACIA_DOOR), "acacia_door")
            .slab(new ItemStack(Blocks.WOODEN_SLAB),"acacia_slab")
            .fence(new ItemStack(Blocks.ACACIA_FENCE), "acacia_fence")
            .fenceGate(new ItemStack(Blocks.ACACIA_FENCE_GATE), "acacia_fence_gate")
            .stairs(new ItemStack(Blocks.ACACIA_STAIRS))
            .boat(new ItemStack(Items.ACACIA_BOAT), "acacia_boat")
            .registerAllUnificationInfo()
            .build();

    private static final WoodTypeEntry DARK_OAK_WOOD_ENTRY = new WoodTypeEntry.Builder(mcModId, "dark_oak")
            .planks(new ItemStack(Blocks.PLANKS, 1, 5), "dark_oak_planks")
            .log(new ItemStack(Blocks.LOG2, 1, 1)).removeCharcoalRecipe()
            .door(new ItemStack(Items.DARK_OAK_DOOR), "dark_oak_door")
            .slab(new ItemStack(Blocks.WOODEN_SLAB), "dark_oak_slab")
            .fence(new ItemStack(Blocks.DARK_OAK_FENCE), "dark_oak_fence")
            .fenceGate(new ItemStack(Blocks.DARK_OAK_FENCE_GATE), "dark_oak_fence_gate")
            .stairs(new ItemStack(Blocks.DARK_OAK_STAIRS))
            .boat(new ItemStack(Items.DARK_OAK_BOAT), "dark_oak_boat")
            .registerAllUnificationInfo()
            .build();

    private static final WoodTypeEntry RUBBER_WOOD_ENTRY = new WoodTypeEntry.Builder(GTValues.MODID, "rubber")
            .planks(MetaBlocks.PLANKS.getItemVariant(BlockGregPlanks.BlockType.RUBBER_PLANK), null)
            .log(new ItemStack(MetaBlocks.RUBBER_LOG)).addCharcoalRecipe()
            .door(MetaItems.RUBBER_WOOD_DOOR.getStackForm(), null)
            .slab(new ItemStack(MetaBlocks.WOOD_SLAB), null)
            .fence(new ItemStack(MetaBlocks.RUBBER_WOOD_FENCE), null)
            .fenceGate(new ItemStack(MetaBlocks.RUBBER_WOOD_FENCE_GATE), null)
            .stairs(new ItemStack(MetaBlocks.RUBBER_WOOD_STAIRS)).addStairsRecipe()
            .boat(MetaItems.RUBBER_WOOD_BOAT.getStackForm(), null)
            .registerAllOres()
            .registerAllUnificationInfo()
            .build();

    public static final WoodTypeEntry TREATED_WOOD_ENTRY = new WoodTypeEntry.Builder(GTValues.MODID, "treated")
            .planks(MetaBlocks.PLANKS.getItemVariant(BlockGregPlanks.BlockType.TREATED_PLANK), null)
            .door(MetaItems.TREATED_WOOD_DOOR.getStackForm(), null)
            .slab(new ItemStack(MetaBlocks.WOOD_SLAB), null)
            .fence(new ItemStack(MetaBlocks.TREATED_WOOD_FENCE), null)
            .fenceGate(new ItemStack(MetaBlocks.TREATED_WOOD_FENCE_GATE), null)
            .stairs(new ItemStack(MetaBlocks.TREATED_WOOD_STAIRS)).addStairsRecipe()
            .boat(MetaItems.TREATED_WOOD_BOAT.getStackForm(), null)
            .material(TreatedWood)
            .registerAllOres()
            .registerAllUnificationInfo()
            .build();
    
    public static final WoodTypeEntryWrapper OAK = new WoodTypeEntryWrapper(OAK_WOOD_ENTRY, TKCYAMetaBlocks.BLOCK_CUT_WOOD.getItemVariant(BlockCutWood.CutWoodType.OAK));
    public static final WoodTypeEntryWrapper BIRCH = new WoodTypeEntryWrapper(BIRCH_WOOD_ENTRY, TKCYAMetaBlocks.BLOCK_CUT_WOOD.getItemVariant(BlockCutWood.CutWoodType.BIRCH));
    public static final WoodTypeEntryWrapper JUNGLE = new WoodTypeEntryWrapper(JUNGLE_WOOD_ENTRY, TKCYAMetaBlocks.BLOCK_CUT_WOOD.getItemVariant(BlockCutWood.CutWoodType.JUNGLE));
    public static final WoodTypeEntryWrapper ACACIA = new WoodTypeEntryWrapper(ACACIA_WOOD_ENTRY, TKCYAMetaBlocks.BLOCK_CUT_WOOD.getItemVariant(BlockCutWood.CutWoodType.ACACIA));
    public static final WoodTypeEntryWrapper SPRUCE = new WoodTypeEntryWrapper(SPRUCE_WOOD_ENTRY, TKCYAMetaBlocks.BLOCK_CUT_WOOD.getItemVariant(BlockCutWood.CutWoodType.SPRUCE));
    public static final WoodTypeEntryWrapper DARK_OAK = new WoodTypeEntryWrapper(DARK_OAK_WOOD_ENTRY, TKCYAMetaBlocks.BLOCK_CUT_WOOD.getItemVariant(BlockCutWood.CutWoodType.DARK_OAK));
    public static final WoodTypeEntryWrapper RUBBER = new WoodTypeEntryWrapper(RUBBER_WOOD_ENTRY, TKCYAMetaBlocks.BLOCK_CUT_WOOD.getItemVariant(BlockCutWood.CutWoodType.RUBBER));
    

    public static final List<WoodTypeEntryWrapper> WOOD_TYPE_ENTRIES_RAW = Arrays.asList(OAK, BIRCH, DARK_OAK, JUNGLE, ACACIA, RUBBER, SPRUCE);
    public static final List<WoodTypeEntry> WOOD_TYPE_ENTRIES = Arrays.asList(OAK_WOOD_ENTRY, BIRCH_WOOD_ENTRY, DARK_OAK_WOOD_ENTRY, JUNGLE_WOOD_ENTRY, ACACIA_WOOD_ENTRY, RUBBER_WOOD_ENTRY, SPRUCE_WOOD_ENTRY, TREATED_WOOD_ENTRY);
    
}
