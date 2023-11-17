package tekcays_addon.common.pipelike.cable;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.pipelike.cable.BlockCable;
import gregtech.common.pipelike.cable.ItemBlockCable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tekcays_addon.api.pipelike.PipeLikeUtils;
import tekcays_addon.gtapi.unification.material.ore.OrePrefixValues;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockCableExtraInfo extends ItemBlockCable {
    public ItemBlockCableExtraInfo(BlockCable block) {
        super(block);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        OrePrefix orePrefix = OreDictUnifier.getPrefix(stack);
        if (OrePrefixValues.WIRES.contains(orePrefix)) {
            PipeLikeUtils.addUnitInfoToPipeLike(tooltip, orePrefix);
        }
    }
}
