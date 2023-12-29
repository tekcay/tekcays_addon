package tekcays_addon.common.metatileentities.single.electric;

import static codechicken.lib.fluid.FluidUtils.FLUID_HANDLER;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.IFluidHandler;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import tekcays_addon.api.metatileentity.TransferMetaTileEntity;

public class MetaTileEntityElectricPump extends TransferMetaTileEntity {

    private final int TRANSFER_RATE = (int) Math.pow((getTier() + 1) * 2, 2);

    public MetaTileEntityElectricPump(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityElectricPump(metaTileEntityId, getTier());
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        ColourMultiplier multiplier = new ColourMultiplier(
                GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering()));
        IVertexOperation[] coloredPipeline = ArrayUtils.add(pipeline, multiplier);

        Textures.PIPE_IN_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
        Textures.FLUID_HATCH_INPUT_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
        Textures.PIPE_OUT_OVERLAY.renderSided(getOutputSide(), renderState, translation, pipeline);
        Textures.FLUID_HATCH_OUTPUT_OVERLAY.renderSided(getOutputSide(), renderState, translation, pipeline);
    }

    @Override
    protected boolean tryTransfer() {
        if (!getTileEntities()) return false;

        IFluidHandler inputFluidHandler = inputTe.getCapability(FLUID_HANDLER, getFrontFacing().getOpposite());
        if (inputFluidHandler == null) return false;

        IFluidHandler outputFluidHandler = outputTe.getCapability(FLUID_HANDLER, getOutputSide().getOpposite());
        if (outputFluidHandler == null) return false;

        GTTransferUtils.transferFluids(inputFluidHandler, outputFluidHandler, TRANSFER_RATE);

        return true;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @NotNull List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcya.electric_pump.tooltip.1", TRANSFER_RATE));
        tooltip.add(I18n.format("tkcya.machine.tooltip.consumption", ENERGY_BASE_CONSUMPTION));
        tooltip.add(
                I18n.format("gregtech.universal.tooltip.energy_storage_capacity", energyContainer.getEnergyCapacity()));
        tooltip.add(I18n.format("tkcya.machine.redstone.inverse.tooltip"));
    }
}
