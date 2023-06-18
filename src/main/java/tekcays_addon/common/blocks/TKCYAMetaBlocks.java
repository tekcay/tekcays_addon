package tekcays_addon.common.blocks;

import tekcays_addon.common.blocks.blocks.BlockBrick;
import tekcays_addon.common.blocks.blocks.BlockCutWood;
import tekcays_addon.common.blocks.blocks.BlockDirt;
import tekcays_addon.common.blocks.blocks.BlockLargeMultiblockCasing;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TKCYAMetaBlocks {

    private TKCYAMetaBlocks() {

    }

    public static BlockLargeMultiblockCasing LARGE_MULTIBLOCK_CASING;
    public static BlockBrick BLOCK_BRICK;
    public static BlockCutWood BLOCK_CUT_WOOD;
    public static BlockDirt BLOCK_DIRT;

    public static void init() {
        LARGE_MULTIBLOCK_CASING = new BlockLargeMultiblockCasing();
        LARGE_MULTIBLOCK_CASING.setRegistryName("large_multiblock_casing");

        BLOCK_CUT_WOOD = new BlockCutWood();
        BLOCK_CUT_WOOD.setRegistryName("block_cut_wood");

        BLOCK_BRICK = new BlockBrick();
        BLOCK_BRICK.setRegistryName("block_brick");

        BLOCK_DIRT = new BlockDirt();
        BLOCK_DIRT.setRegistryName("block_dirt");
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemModels() {
        registerItemModel(LARGE_MULTIBLOCK_CASING);
        registerItemModel(BLOCK_BRICK);
        registerItemModel(BLOCK_CUT_WOOD);
        registerItemModel(BLOCK_DIRT);
    }

    @SideOnly(Side.CLIENT)
    private static void registerItemModel(Block block) {
        for (IBlockState state : block.getBlockState().getValidStates()) {
            //noinspection ConstantConditions
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block),
                    block.getMetaFromState(state),
                    new ModelResourceLocation(block.getRegistryName(),
                            MetaBlocks.statePropertiesToString(state.getProperties())));
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> String getPropertyName(IProperty<T> property, Comparable<?> value) {
        return property.getName((T) value);
    }
}
