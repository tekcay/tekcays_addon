package tekcays_addon.common.pipelike.fluidpipe;

import gregtech.api.pipenet.block.material.BlockMaterialPipe;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.properties.FluidPipeProperties;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.ConfigHolder;
import gregtech.common.pipelike.fluidpipe.BlockFluidPipe;
import gregtech.common.pipelike.fluidpipe.ItemBlockFluidPipe;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tekcays_addon.gtapi.unification.material.ore.OrePrefixValues;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockFluidPipeExtraInfo extends ItemBlockFluidPipe {


    public ItemBlockFluidPipeExtraInfo(BlockFluidPipe block) {
        super(block);
    }

    /**
     * The extracted method from {@link ItemBlock}
     */
    private void itemBlockAddInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        this.block.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        itemBlockAddInformation(stack, worldIn, tooltip, flagIn);

        FluidPipeProperties pipeProperties = blockPipe.createItemProperties(stack);
        tooltip.add(I18n.format("gregtech.universal.tooltip.fluid_transfer_rate", pipeProperties.getThroughput()));
        tooltip.add(I18n.format("gregtech.fluid_pipe.capacity", pipeProperties.getThroughput() * 20));
        tooltip.add(I18n.format("gregtech.fluid_pipe.max_temperature", pipeProperties.getMaxFluidTemperature()));
        if (pipeProperties.getTanks() > 1) tooltip.add(I18n.format("gregtech.fluid_pipe.channels", pipeProperties.getTanks()));

        BlockMaterialPipe<?, ?, ?> blockMaterialPipe = (BlockMaterialPipe<?, ?, ?>) blockPipe;
        OrePrefix orePrefix = blockMaterialPipe.getPrefix();
        tooltip.add(I18n.format("gregtech.fluid_pipe.unit", OrePrefixValues.getOrePrefixUnit(orePrefix)));

        if (TooltipHelper.isShiftDown()) {
            if (pipeProperties.isGasProof()) tooltip.add(I18n.format("gregtech.fluid_pipe.gas_proof"));
            else if (ModHandler.isMaterialWood(blockMaterialPipe.getItemMaterial(stack)))
                tooltip.add(I18n.format("gregtech.fluid_pipe.not_gas_proof"));
            if (pipeProperties.isAcidProof()) tooltip.add(I18n.format("gregtech.fluid_pipe.acid_proof"));
            if (pipeProperties.isCryoProof()) tooltip.add(I18n.format("gregtech.fluid_pipe.cryo_proof"));
            if (pipeProperties.isPlasmaProof()) tooltip.add(I18n.format("gregtech.fluid_pipe.plasma_proof"));
        } else {
            tooltip.add(I18n.format("gregtech.tooltip.fluid_pipe_hold_shift"));
        }

        if (ConfigHolder.misc.debug) {
            tooltip.add("MetaItem Id: " + orePrefix.name + blockMaterialPipe.getItemMaterial(stack).toCamelCaseString());
        }
    }
}
