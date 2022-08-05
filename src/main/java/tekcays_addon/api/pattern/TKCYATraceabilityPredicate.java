package tekcays_addon.api.pattern;

import gregtech.api.GregTechAPI;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.block.VariantActiveBlock;
import gregtech.api.pattern.PatternStringError;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.util.BlockInfo;
import gregtech.common.blocks.BlockFireboxCasing;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;

import org.apache.commons.lang3.ArrayUtils;
import tekcays_addon.api.block.IPumpMachineBlockStats;
import tekcays_addon.api.utils.TKCYAValues;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockBrick;
import tekcays_addon.common.blocks.blocks.BlockPump;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.function.Supplier;

public class TKCYATraceabilityPredicate {

    public static Supplier<TraceabilityPredicate> FIREBOX_CASINGS = () -> new TraceabilityPredicate(blockWorldState -> {
        IBlockState blockState = blockWorldState.getBlockState();
        if ((blockState.getBlock() instanceof BlockFireboxCasing)) {
            BlockFireboxCasing blockFireboxCasing = (BlockFireboxCasing) blockState.getBlock();
            BlockFireboxCasing.FireboxCasingType fireboxCasingType = blockFireboxCasing.getState(blockState);
            Object currentfireboxType = blockWorldState.getMatchContext().getOrPut("FireboxType", fireboxCasingType);
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
            Object currentfireboxType = blockWorldState.getMatchContext().getOrPut("BrickType", brickType);
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


    /*
    public static Supplier<TraceabilityPredicate> PUMP_MACHINE1 = () -> new TraceabilityPredicate(blockWorldState -> {
        IBlockState blockState = blockWorldState.getBlockState();
        if ((blockState.getBlock() instanceof BlockPump)) {
            BlockPump blockPump = (BlockPump) blockState.getBlock();
            BlockPump.PumpType pumpType = blockPump.getState(blockState);
            Object currentpumpType = blockWorldState.getMatchContext().getOrPut("PumpType", pumpType);
            if (!currentpumpType.toString().equals(pumpType.getName())) {
                blockWorldState.setError(new PatternStringError("tkcya.multiblock.pattern.error.bricks"));
                return false;
            }
            blockWorldState.getMatchContext().getOrPut("VABlock", new LinkedList<>()).add(blockWorldState.getPos());
            return true;
        }
        return false;
    }, () -> ArrayUtils.addAll(
            Arrays.stream(BlockPump.PumpType.values()).map(type -> new BlockInfo(TKCYAMetaBlocks.PUMP_MACHINE.getState(type), null)).toArray(BlockInfo[]::new)))
            .addTooltips("tkcya.multiblock.pattern.error.bricks");

     */


    public static Supplier<TraceabilityPredicate> PUMP_MACHINE = () -> new TraceabilityPredicate(blockWorldState -> {
        IBlockState blockState = blockWorldState.getBlockState();
        if (TKCYAValues.PUMP_MACHINES.containsKey(blockState)) {
            IPumpMachineBlockStats stats = TKCYAValues.PUMP_MACHINES.get(blockState);
            Object currentPumpMachine = blockWorldState.getMatchContext().getOrPut("PumpType", stats);
            if (!currentPumpMachine.equals(stats)) {
                blockWorldState.setError(new PatternStringError("gregtech.multiblock.pattern.error.coils"));
                return false;
            }
            blockWorldState.getMatchContext().getOrPut("VABlock", new LinkedList<>()).add(blockWorldState.getPos());
            return true;
        }
        return false;
    }, () -> TKCYAValues.PUMP_MACHINES.entrySet().stream()

            // sort to make autogenerated jei previews not pick random coils each game load
            .sorted(Comparator.comparingInt(entry -> entry.getValue().getTier()))
            .map(entry -> new BlockInfo(entry.getKey(), null))
            .toArray(BlockInfo[]::new))
            .addTooltips("gregtech.multiblock.pattern.error.coils");
    
    
}
