package tekcays_addon.common.blocks.blocks;

import gregtech.api.block.VariantBlock;
import gregtech.api.block.VariantItemBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BlockBrick extends VariantBlock<BlockBrick.BrickType> {

    public BlockBrick() {
        super(net.minecraft.block.material.Material.IRON);
        setTranslationKey("block_brick");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("pickaxe", 1);
        setDefaultState(getState(BrickType.BRICK));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack itemStack, @Nullable World worldIn, List<String> lines, @Nonnull ITooltipFlag tooltipFlag) {
        super.addInformation(itemStack, worldIn, lines, tooltipFlag);

        // noinspection rawtypes, unchecked
        VariantItemBlock itemBlock = (VariantItemBlock<BrickType, BlockBrick>) itemStack.getItem();
        IBlockState stackState = itemBlock.getBlockState(itemStack);
        BrickType brickType = getState(stackState);

        lines.add(I18n.format("tile.block_brick.tooltip_heat", brickType.brickTemperature));
    }

    public static final int BRICK = 0;
    public static final int REINFORCED_BRICK = 1;
    public static final int FIRECLAY_BRICK = 2;
    public static final int STRONG_BRICK = 3;

    public enum BrickType implements IStringSerializable {

        BRICK("brick", 1600, BlockBrick.BRICK),
        REINFORCED_BRICK("reinforced_brick", 2100, BlockBrick.REINFORCED_BRICK),
        FIRECLAY_BRICK("fireclay_brick", 3600, BlockBrick.FIRECLAY_BRICK),
        STRONG_BRICK("strong_brick", 5000, BlockBrick.STRONG_BRICK);


        private final String name;
        private final int brickTemperature;
        private final int textureId;

        BrickType(String name, int brickTemperature, int textureId) {
            this.name = name;
            this.brickTemperature = brickTemperature;
            this.textureId = textureId;
        }

        @Nonnull
        @Override
        public String getName() {
            return this.name;
        }

        public int getBrickTemperature() {
            return brickTemperature;
        }

        public int getTextureId() {
            return textureId;
        }

    }
}
