package tekcays_addon.api.pattern;


import gregtech.api.pattern.TraceabilityPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import tekcays_addon.common.blocks.blocks.BlockPump;

import static gregtech.api.metatileentity.multiblock.MultiblockControllerBase.air;


public class TKCYATraceabilityPredicate {


    public static TraceabilityPredicate pumpMachinePredicate() {
        return new TraceabilityPredicate(blockWorldState -> {
            IBlockState state = blockWorldState.getBlockState();
            Block block = state.getBlock();
            if (block instanceof BlockPump) {
                int targetVacuum = blockWorldState.getMatchContext().getOrPut("TargetVacuum", 0);
                blockWorldState.getMatchContext().set("TargetVacuum", ((BlockPump) block).getState(state).getTargetVacuum() + targetVacuum);
                return true;
            }
            return false;
        });
    }

    public static TraceabilityPredicate heightIndicatorPredicate() {
        return new TraceabilityPredicate((blockWorldState) -> {
            if (air().test(blockWorldState)) {
                blockWorldState.getMatchContext().increment("height", 1);
                return true;
            } else
                return false;
        });
    }


    
    
}
