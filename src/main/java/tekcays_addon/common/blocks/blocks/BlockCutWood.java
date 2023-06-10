package tekcays_addon.common.blocks.blocks;

import gregtech.api.block.VariantBlock;
import gregtech.api.block.VariantItemBlock;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import lombok.Getter;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tekcays_addon.gtapi.render.TKCYATextures;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static net.minecraft.init.Blocks.LOG;
import static net.minecraft.init.Blocks.LOG2;

public class BlockCutWood extends VariantBlock<BlockCutWood.CutWoodType> {


    public BlockCutWood() {
        super(Material.WOOD);
        setTranslationKey("block_cut_wood");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.WOOD);
        setHarvestLevel("axe", 1);
        setDefaultState(getState(CutWoodType.OAK));
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


    @Getter
    public enum CutWoodType implements IStringSerializable {

        ACACIA("acacia"),
        BIRCH("birch"),
        JUNGLE("jungle"),
        OAK("oak"),
        RUBBER("rubber"),
        SPRUCE("spruce");

        private final String name;


        CutWoodType(String name) {
            this.name = name;
        }
    }
}
