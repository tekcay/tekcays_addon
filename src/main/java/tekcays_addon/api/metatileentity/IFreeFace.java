package tekcays_addon.api.metatileentity;

import static codechicken.lib.util.ClientUtils.getWorld;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public interface IFreeFace {

    /**
     * Checks if a {@code MetaTileEntity} has {@code Air} in the block adjacent to its face.
     * 
     * @param pos    the position of the {@code MetaTileEntity}
     * @param facing the side of the {@code MetaTileEntity} to consider
     * @return
     */
    default boolean checkFaceFree(BlockPos pos, EnumFacing facing) {
        BlockPos position = pos.offset(facing);
        try {
            IBlockState blockState = getWorld().getBlockState(position);
            return blockState.getBlock().isAir(blockState, getWorld(), position);
        } catch (NullPointerException e) {
            return true;
        }
    }
}
