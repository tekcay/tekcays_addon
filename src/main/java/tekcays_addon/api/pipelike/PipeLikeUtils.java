package tekcays_addon.api.pipelike;

import gregtech.api.pipenet.block.BlockPipe;
import gregtech.api.pipenet.block.material.BlockMaterialPipe;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.client.resources.I18n;
import tekcays_addon.gtapi.unification.material.ore.OrePrefixValues;

import javax.annotation.Nonnull;
import java.util.List;

public class PipeLikeUtils {

    public static void addUnitInfoToPipeLike(@Nonnull List<String> tooltip, OrePrefix orePrefix) {
        tooltip.add(I18n.format("gregtech.fluid_pipe.unit", OrePrefixValues.getOrePrefixUnit(orePrefix)));
    }

    public static void addUnitInfoToPipeLike(@Nonnull List<String> tooltip, BlockMaterialPipe<?, ?, ?> blockMaterialPipe) {
        OrePrefix orePrefix = blockMaterialPipe.getPrefix();
        addUnitInfoToPipeLike(tooltip, orePrefix);
    }

    public static void addUnitInfoToPipeLike(@Nonnull List<String> tooltip, BlockPipe<?, ?, ?> blockPipe) {
        BlockMaterialPipe<?, ?, ?> blockMaterialPipe = (BlockMaterialPipe<?, ?, ?>) blockPipe;
        addUnitInfoToPipeLike(tooltip, blockMaterialPipe);
    }
}
