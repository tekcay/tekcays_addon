package tekcays_addon.common.blocks.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.IStringSerializable;

import org.jetbrains.annotations.NotNull;

import gregtech.api.block.VariantBlock;

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

        STEEL_WALL("steel_wall"),
        GALVANIZED_STEEL_WALL("galvanized_steel_wall"),
        STAINLESS_STEEL_WALL("stainless_steel_wall");

        private final String name;

        CasingType(String name) {
            this.name = name;
        }

        @NotNull
        @Override
        public String getName() {
            return this.name;
        }
    }
}
