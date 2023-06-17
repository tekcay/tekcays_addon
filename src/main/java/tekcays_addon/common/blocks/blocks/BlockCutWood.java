package tekcays_addon.common.blocks.blocks;

import gregtech.api.block.VariantBlock;
import gregtech.api.block.VariantItemBlock;
import gregtech.common.blocks.MetaBlocks;
import lombok.Getter;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class BlockCutWood extends VariantBlock<BlockCutWood.CutWoodType> {


    public static final String TRANSLATION_KEY = "block_cut_wood";
    public static final String LOG = "log";
    public static final String PLANK = "planks";
    public static final String SLAB = "wooden_slab";

    public BlockCutWood() {
        super(Material.WOOD);
        setTranslationKey(TRANSLATION_KEY);
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.WOOD);
        setHarvestLevel("axe", 1);
        setDefaultState(getState(CutWoodType.OAK));
    }

    @Nullable
    public static CutWoodType getCutWoodTypeFromLog(IBlockState block) {

        if (block.equals(Blocks.LOG2.getDefaultState())) {
            return CutWoodType.ACACIA;

        } else if (block.equals(MetaBlocks.RUBBER_LOG.getDefaultState())) {
            return CutWoodType.RUBBER;

        } else if (block.equals(getLog(BlockPlanks.EnumType.OAK))) {
            return CutWoodType.OAK;

        } else if (block.equals(getLog(BlockPlanks.EnumType.BIRCH))) {
            return CutWoodType.BIRCH;

        } else if (block.equals(getLog(BlockPlanks.EnumType.SPRUCE))) {
            return CutWoodType.SPRUCE;

        } else if (block.equals(getLog(BlockPlanks.EnumType.JUNGLE))) {
            return CutWoodType.JUNGLE;
        }

        return null;
    }


    @Nullable
    public static IBlockState getPlankFromCutWood(IBlockState block) {
        CutWoodType cutWoodType = getCustomType(block);
        return cutWoodType == null ? null : getPlank(cutWoodType.getVariant());
    }

    @Nullable
    public static IBlockState getSlabFromPlank(IBlockState block) {
        CutWoodType cutWoodType = getCustomType(block);
        return cutWoodType == null ? null : getSlab(cutWoodType.getVariant());
    }

    public static CutWoodType getCustomType(IBlockState blockState) {
        String name = blockState.toString().split("=")[1].split("]")[0];
        return getCustomType(name);
    }

    public static CutWoodType getCustomType(String name) {
        return Arrays.stream(CutWoodType.values())
                .filter(cutWoodType -> cutWoodType.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public static CutWoodType getCustomType(BlockPlanks.EnumType variant) {
        return Arrays.stream(CutWoodType.values())
                .filter(cutWoodType -> cutWoodType.getVariant().equals(variant))
                .findFirst()
                .orElse(null);
    }

    /**
     * 0 is blockType, 1 is woodType
     *
     * @param blockState
     * @return
     */
    public static String[] getInfo(IBlockState blockState) {
        String intermediate = blockState.toString().split(":")[1];
        return intermediate.split("\\[");
    }

    public static @Nullable IBlockState getUpperBlockBlockState(String[] infos) {
        CutWoodType cutWoodType = getCustomType(infos[1]);
        switch (infos[0]) {
            case LOG : {
                return getLog(cutWoodType.getVariant());
            }
            case TRANSLATION_KEY : {
                return getCutLog(cutWoodType.getVariant());
            }
            case PLANK : {
                return getPlank(cutWoodType.getVariant());
            }
            default : return null;
        }
    }

    /*
    public static @Nullable String getBlockType(IBlockState blockState) {
        String[] infos = blockState.toString().split(":")[1].split("\\[");
        CutWoodType cutWoodType = getCustomType(infos[1]);
        switch (infos[0]) {
            case LOG : return getLog(cutWoodType.getVariant());
            case TRANSLATION_KEY : return getCutLog(cutWoodType.getVariant());
            case PLANK : return getPlank(cutWoodType.getVariant());
            default : return null;
        }
    }

     */

    public static @Nullable IBlockState getUpperBlockBlockState(IBlockState blockState) {
        String[] infos = blockState.toString().split(":")[1].split("\\[");
        CutWoodType cutWoodType = getCustomType(infos[1]);
        switch (infos[0]) {
            case LOG : return getLog(cutWoodType.getVariant());
            case TRANSLATION_KEY : return getCutLog(cutWoodType.getVariant());
            case PLANK : return getPlank(cutWoodType.getVariant());
            default : return null;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack itemStack, @Nullable World worldIn, List<String> lines, @Nonnull ITooltipFlag tooltipFlag) {
        super.addInformation(itemStack, worldIn, lines, tooltipFlag);

        // noinspection rawtypes, unchecked
        VariantItemBlock itemBlock = (VariantItemBlock<CutWoodType, BlockCutWood>) itemStack.getItem();
        IBlockState stackState = itemBlock.getBlockState(itemStack);
        CutWoodType cutWoodType = getState(stackState);
    }

    private static IBlockState getPlank(@Nullable BlockPlanks.EnumType plank) {
        if (plank == null) return MetaBlocks.PLANKS.getDefaultState();
        return Blocks.PLANKS.getDefaultState().withProperty(BlockPlanks.VARIANT, plank);
    }

    private static IBlockState getSlab(@Nullable BlockPlanks.EnumType plank) {
        if (plank == null) return MetaBlocks.WOOD_SLAB.getDefaultState();
        return Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockPlanks.VARIANT, plank);
    }

    private static IBlockState getLog(@Nullable BlockPlanks.EnumType plank) {
        if (plank == null) return MetaBlocks.RUBBER_LOG.getDefaultState();
        return Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, plank);
    }

    private static IBlockState getCutLog(@Nullable BlockPlanks.EnumType plank) {
        if (plank == null) return MetaBlocks.RUBBER_LOG.getDefaultState();
        return TKCYAMetaBlocks.BLOCK_CUT_WOOD.getState(getCustomType(plank));
    }

    @Getter
    public enum CutWoodType implements IStringSerializable {

        ACACIA("acacia", BlockPlanks.EnumType.ACACIA),
        BIRCH("birch", BlockPlanks.EnumType.BIRCH),
        DARK_OAK("dark_oak", BlockPlanks.EnumType.DARK_OAK),
        JUNGLE("jungle", BlockPlanks.EnumType.JUNGLE),
        OAK("oak", BlockPlanks.EnumType.OAK),
        RUBBER("rubber", null),
        SPRUCE("spruce", BlockPlanks.EnumType.SPRUCE);

        private final String name;
        private final BlockPlanks.EnumType variant;

        CutWoodType(String name, BlockPlanks.EnumType variant) {
            this.name = name;
            this.variant = variant;
        }
    }
}
