package tekcays_addon.api.pipelike;

import java.util.List;

import net.minecraft.client.resources.I18n;

import org.jetbrains.annotations.NotNull;

import gregtech.api.pipenet.block.BlockPipe;
import gregtech.api.pipenet.block.material.BlockMaterialPipe;
import gregtech.api.unification.ore.OrePrefix;
import tekcays_addon.gtapi.unification.material.ore.OrePrefixValues;

public class PipeLikeUtils {

    public static void addUnitInfoToPipeLike(@NotNull List<String> tooltip, OrePrefix orePrefix) {
        tooltip.add(I18n.format("tkcya.oreprefix.unit", OrePrefixValues.getOrePrefixUnitAsText(orePrefix)));
    }

    public static void addUnitInfoToPipeLike(@NotNull List<String> tooltip,
                                             BlockMaterialPipe<?, ?, ?> blockMaterialPipe) {
        OrePrefix orePrefix = blockMaterialPipe.getPrefix();
        addUnitInfoToPipeLike(tooltip, orePrefix);
    }

    public static void addUnitInfoToPipeLike(@NotNull List<String> tooltip, BlockPipe<?, ?, ?> blockPipe) {
        BlockMaterialPipe<?, ?, ?> blockMaterialPipe = (BlockMaterialPipe<?, ?, ?>) blockPipe;
        addUnitInfoToPipeLike(tooltip, blockMaterialPipe);
    }
}
