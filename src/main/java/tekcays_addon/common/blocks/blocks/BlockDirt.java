package tekcays_addon.common.blocks.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.IStringSerializable;
import gregtech.api.block.VariantBlock;

import javax.annotation.Nonnull;

public class BlockDirt extends VariantBlock<BlockDirt.DirtType> {

    public BlockDirt() {
        super(Material.GROUND);
        setTranslationKey("block_dirt");
        setHardness(1.0f);
        setResistance(1.0f);
        setSoundType(SoundType.GROUND);
        setHarvestLevel("shovel", 1);
        setDefaultState(getState(DirtType.DIRT));
    }

    public static final int DIRT = 0;

    public enum DirtType implements IStringSerializable {

        DIRT("dirt", BlockDirt.DIRT);


        private final String name;
        private final int textureId;

        DirtType(String name, int textureId) {
            this.name = name;
            this.textureId = textureId;
        }

        @Nonnull
        @Override
        public String getName() {
            return this.name;
        }

        public int getTextureId() {
            return textureId;
        }
    }
}
