package tekcays_addon.api.capability.impl;

import gregtech.api.pipenet.PipeNetWalker;
import gregtech.api.pipenet.tile.IPipeTile;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class LaserNetWalker extends PipeNetWalker {

    private double pressure = -1;

    public static void checkLaser(World world, BlockPos start, double pressure) {
        LaserNetWalker walker = new LaserNetWalker(world, start, 0);
        walker.pressure = pressure;
        walker.traversePipeNet();
    }

    protected LaserNetWalker(World world, BlockPos sourcePipe, int walkedBlocks) {
        super(world, sourcePipe, walkedBlocks);
    }

    @Override
    protected PipeNetWalker createSubWalker(World world, EnumFacing enumFacing, BlockPos blockPos, int i) {
        LaserNetWalker walker = new LaserNetWalker(world, blockPos, i);
        walker.pressure = pressure;
        return walker;
    }

    @Override
    protected void checkPipe(IPipeTile<?, ?> iPipeTile, BlockPos blockPos) {
        /*
        TileEntityLaserPipe pipe = (TileEntityLaserPipe) iPipeTile;
        pipe.checkLaser(pressure);

         */
    }

    @Override
    protected void checkNeighbour(IPipeTile<?, ?> iPipeTile, BlockPos blockPos, EnumFacing enumFacing, @Nullable TileEntity tileEntity) {

    }

    @Override
    protected boolean isValidPipe(IPipeTile<?, ?> iPipeTile, IPipeTile<?, ?> iPipeTile1, BlockPos blockPos, EnumFacing enumFacing) {
        /*
        return iPipeTile1 instanceof TileEntityLaserPipe;
         */
        return false;
    }
}
