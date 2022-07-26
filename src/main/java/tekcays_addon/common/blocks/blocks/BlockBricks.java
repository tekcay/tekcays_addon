package tekcays_addon.common.blocks.blocks;

import gregtech.api.block.VariantActiveBlock;
import gregtech.api.block.VariantItemBlock;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BlockBricks extends VariantActiveBlock<BlockBricks.BrickType> {

    public BlockBricks() {
        super(net.minecraft.block.material.Material.IRON);
        setTranslationKey("block_brick");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("wrench", 2);
        setDefaultState(getState(BrickType.BRICK));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack itemStack, @Nullable World worldIn, List<String> lines, @Nonnull ITooltipFlag tooltipFlag) {
        super.addInformation(itemStack, worldIn, lines, tooltipFlag);

        // noinspection rawtypes, unchecked
        VariantItemBlock itemBlock = (VariantItemBlock<BrickType, BlockBricks>) itemStack.getItem();
        IBlockState stackState = itemBlock.getBlockState(itemStack);
        BrickType brickType = getState(stackState);

        lines.add(I18n.format("tile.block_brick.tooltip_heat", brickType.brickTemperature));

        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            int coilTier = brickType.ordinal();
            lines.add(I18n.format("tile.block_brick.tooltip_smelter"));
            lines.add(I18n.format("tile.block_brick.tooltip_pyro"));
            lines.add(I18n.format("tile.block_brick.tooltip_speed_pyro", coilTier == 0 ? 75 : 50 * (coilTier + 1)));
            lines.add(I18n.format("tile.block_brick.tooltip_cracking"));
            lines.add(I18n.format("tile.block_brick.tooltip_energy_cracking", 100 - 5 * coilTier));
        } else {
            lines.add(I18n.format("gregtech.tooltip.hold_shift"));
        }
    }

    public enum BrickType implements IStringSerializable {

        BRICK("brick", 1200, Materials.Cupronickel),
        REINFORCED_BRICK("reinforce_brick", 2700, Materials.Kanthal),
        FIRECLAY_BRICK("fireclay_brick", 3600, Materials.Fireclay),
        STRONG_BRICK("strong_brick", 5000, Materials.Nichrome);


        private final String name;
        //electric blast furnace properties
        private final int brickTemperature;
        private final Material material;

        BrickType(String name, int brickTemperature, Material material) {
            this.name = name;
            this.brickTemperature = brickTemperature;
            this.material = material;
        }

        @Nonnull
        @Override
        public String getName() {
            return this.name;
        }

        public int getBrickTemperature() {
            return brickTemperature;
        }

        public Material getMaterial() {
            return material;
        }

        @Override
        public String toString() {
            return getName();
        }
    }
}
