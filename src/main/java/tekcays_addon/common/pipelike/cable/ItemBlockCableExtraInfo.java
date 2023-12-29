package tekcays_addon.common.pipelike.cable;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.pipelike.cable.BlockCable;
import gregtech.common.pipelike.cable.ItemBlockCable;
import tekcays_addon.api.pipelike.PipeLikeUtils;
import tekcays_addon.gtapi.unification.material.ore.OrePrefixValues;

public class ItemBlockCableExtraInfo extends ItemBlockCable {

    public ItemBlockCableExtraInfo(BlockCable block) {
        super(block);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<String> tooltip,
                               @NotNull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        OrePrefix orePrefix = OreDictUnifier.getPrefix(stack);
        if (OrePrefixValues.WIRES.contains(orePrefix)) {
            PipeLikeUtils.addUnitInfoToPipeLike(tooltip, orePrefix);
        }
    }
}
