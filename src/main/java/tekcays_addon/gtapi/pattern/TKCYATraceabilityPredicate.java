package tekcays_addon.gtapi.pattern;

import gregtech.api.pattern.PatternStringError;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.util.BlockInfo;
import gregtech.common.blocks.BlockFireboxCasing;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;

import org.apache.commons.lang3.ArrayUtils;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockBrick;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.function.Supplier;

public class TKCYATraceabilityPredicate extends TraceabilityPredicate {



    public static Supplier<TraceabilityPredicate> FIREBOX_CASINGS = () -> new TraceabilityPredicate(blockWorldState -> {
        IBlockState blockState = blockWorldState.getBlockState();
        if ((blockState.getBlock() instanceof BlockFireboxCasing)) {
            BlockFireboxCasing blockFireboxCasing = (BlockFireboxCasing) blockState.getBlock();
            BlockFireboxCasing.FireboxCasingType fireboxCasingType = blockFireboxCasing.getState(blockState);
            Object currentfireboxType = blockWorldState.getMatchContext().getOrPut("fireboxType", fireboxCasingType);
            if (!currentfireboxType.toString().equals(fireboxCasingType.getName())) {
                blockWorldState.setError(new PatternStringError("gregtech.multiblock.pattern.error.coils"));
                return false;
            }
            blockWorldState.getMatchContext().getOrPut("VABlock", new LinkedList<>()).add(blockWorldState.getPos());
            return true;
        }
        return false;
    }, () -> ArrayUtils.addAll(
            Arrays.stream(BlockFireboxCasing.FireboxCasingType.values()).map(type -> new BlockInfo(MetaBlocks.BOILER_FIREBOX_CASING.getState(type), null)).toArray(BlockInfo[]::new)))
            .addTooltips("gregtech.multiblock.pattern.error.fireboxes");



    public static Supplier<TraceabilityPredicate> BLOCK_BRICKS = () -> new TraceabilityPredicate(blockWorldState -> {
        IBlockState blockState = blockWorldState.getBlockState();
        if ((blockState.getBlock() instanceof BlockBrick)) {
            BlockBrick blockBrick = (BlockBrick) blockState.getBlock();
            BlockBrick.BrickType brickType = blockBrick.getState(blockState);
            Object currentfireboxType = blockWorldState.getMatchContext().getOrPut("brickType", brickType);
            if (!currentfireboxType.toString().equals(brickType.getName())) {
                blockWorldState.setError(new PatternStringError("tkcya.multiblock.pattern.error.bricks"));
                return false;
            }
            blockWorldState.getMatchContext().getOrPut("VABlock", new LinkedList<>()).add(blockWorldState.getPos());
            return true;
        }
        return false;
    }, () -> ArrayUtils.addAll(
            Arrays.stream(BlockBrick.BrickType.values()).map(type -> new BlockInfo(TKCYAMetaBlocks.BLOCK_BRICK.getState(type), null)).toArray(BlockInfo[]::new)))
            .addTooltips("tkcya.multiblock.pattern.error.bricks");
    
    
}
