package tekcays_addon.common.metatileentities.single.electric;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IActiveOutputSide;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import gregtech.core.sound.GTSoundEvents;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.apache.commons.lang3.ArrayUtils;
import tekcays_addon.api.metatileentity.WrenchAbleTieredMetaTileEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static codechicken.lib.fluid.FluidUtils.FLUID_HANDLER;

public class MetaTileEntityElectricPump extends WrenchAbleTieredMetaTileEntity implements IActiveOutputSide {
    private final int ENERGY_BASE_CONSUMPTION = (int) (GTValues.V[getTier()] / 2);
    private final int TRANSFER_RATE = (int) Math.pow((getTier() + 1) * 2, 2);
    private boolean isRunning;

    public MetaTileEntityElectricPump(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
        this.isRunning = false;
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
        ColourMultiplier multiplier = new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering()));
        IVertexOperation[] coloredPipeline = ArrayUtils.add(pipeline, multiplier);

        Textures.PIPE_IN_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
        Textures.FLUID_HATCH_INPUT_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
        Textures.PIPE_OUT_OVERLAY.renderSided(getOutputSide(), renderState, translation, pipeline);
        Textures.FLUID_HATCH_OUTPUT_OVERLAY.renderSided(getOutputSide(), renderState, translation, pipeline);
    }

    @Override
    public void update() {
        super.update();
        if (!getWorld().isRemote) {
            if (this.isBlockRedstonePowered() || energyContainer.getEnergyStored() < ENERGY_BASE_CONSUMPTION) {
                isRunning = false;
                return;
            }
            if (tryTransferFluid()) {
                energyContainer.removeEnergy(ENERGY_BASE_CONSUMPTION);
                isRunning = true;
            }
        }
    }

    private boolean tryTransferFluid() {
        TileEntity inputTe = getWorld().getTileEntity(getPos().offset(getFrontFacing()));
        if (inputTe == null) return false;

        TileEntity outputTe = getWorld().getTileEntity(getPos().offset(getOutputSide()));
        if (outputTe == null) return false;

        IFluidHandler inputFluidHandler = inputTe.getCapability(FLUID_HANDLER, getFrontFacing().getOpposite());
        if (inputFluidHandler == null) return false;

        IFluidHandler outputFluidHandler = outputTe.getCapability(FLUID_HANDLER, getOutputSide().getOpposite());
        if (outputFluidHandler == null) return false;

        GTTransferUtils.transferFluids(inputFluidHandler, outputFluidHandler, TRANSFER_RATE);

        return true;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcya.electric_pump.tooltip.1", TRANSFER_RATE));
        tooltip.add(I18n.format("tkcya.machine.tooltip.consumption", ENERGY_BASE_CONSUMPTION));
        tooltip.add(I18n.format("gregtech.universal.tooltip.energy_storage_capacity", energyContainer.getEnergyCapacity()));
        tooltip.add(I18n.format("tkcya.machine.redstone.inverse.tooltip"));
    }

    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE) {
            return side == getFrontFacing() ? GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE.cast(this) : null;
        }
        return super.getCapability(capability, side);
    }

    @Override
    public SoundEvent getSound() {
        return GTSoundEvents.MOTOR;
    }

    @Override
    public boolean isActive() {
        return this.isRunning;
    }

    @Override
    public boolean isAutoOutputItems() {
        return false;
    }

    @Override
    public boolean isAutoOutputFluids() {
        return true;
    }

    @Override
    public boolean isAllowInputFromOutputSideItems() {
        return false;
    }

    @Override
    public boolean isAllowInputFromOutputSideFluids() {
        return false;
    }
}
