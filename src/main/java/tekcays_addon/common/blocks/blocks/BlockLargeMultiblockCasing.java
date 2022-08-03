package tekcays_addon.common.blocks.blocks;

import gregtech.api.block.VariantBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;

public class BlockLargeMultiblockCasing extends VariantBlock<BlockLargeMultiblockCasing.CasingType> {

    public BlockLargeMultiblockCasing() {
        super(Material.IRON);
        setTranslationKey("large_multiblock_casing");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("pickaxe", 2);
        setDefaultState(getState(CasingType.MONEL_CASING));
    }

    public enum CasingType implements IStringSerializable {

        MONEL_CASING("monel_casing"),
        BATCH_DISTILLATION_TOWER_CASING("batch_distillation_tower_casing"),
        HEAT_ACCEPTOR("heat_acceptor"),
        STEEL_WALL("steel_wall"),
        GALVANIZED_STEEL_WALL("galvanized_steel_wall"),
        STAINLESS_STEEL_WALL("stainless_steel_wall");

        private final String name;

        CasingType(String name) {
            this.name = name;
        }

        @Nonnull
        @Override
        public String getName() {
            return this.name;
        }

    }
}
