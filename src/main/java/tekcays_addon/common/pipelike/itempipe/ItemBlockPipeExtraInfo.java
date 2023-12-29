package tekcays_addon.common.pipelike.itempipe;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.common.pipelike.itempipe.BlockItemPipe;
import gregtech.common.pipelike.itempipe.ItemBlockItemPipe;
import tekcays_addon.api.pipelike.PipeLikeUtils;

public class ItemBlockPipeExtraInfo extends ItemBlockItemPipe {

    public ItemBlockPipeExtraInfo(BlockItemPipe block) {
        super(block);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<String> tooltip,
                               @NotNull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        PipeLikeUtils.addUnitInfoToPipeLike(tooltip, blockPipe);
    }
}
