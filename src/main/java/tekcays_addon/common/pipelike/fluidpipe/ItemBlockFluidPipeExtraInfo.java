package tekcays_addon.common.pipelike.fluidpipe;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.common.pipelike.fluidpipe.BlockFluidPipe;
import gregtech.common.pipelike.fluidpipe.ItemBlockFluidPipe;
import tekcays_addon.api.pipelike.PipeLikeUtils;

public class ItemBlockFluidPipeExtraInfo extends ItemBlockFluidPipe {

    public ItemBlockFluidPipeExtraInfo(BlockFluidPipe block) {
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
