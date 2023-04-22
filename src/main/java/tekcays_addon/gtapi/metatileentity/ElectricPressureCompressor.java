package tekcays_addon.gtapi.metatileentity;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IActiveOutputSide;
import gregtech.api.capability.impl.NotifiableFluidTank;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.TankWidget;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.apache.commons.lang3.ArrayUtils;
import tekcays_addon.gtapi.capability.containers.IVacuumContainer;
import tekcays_addon.gtapi.render.TKCYATextures;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.capability.GregtechDataCodes.UPDATE_OUTPUT_FACING;
import static net.minecraft.util.EnumFacing.UP;
import static tekcays_addon.api.consts.NBTKeys.OUTPUT_SIDE;

public abstract class ElectricPressureCompressor extends TieredMetaTileEntity implements IActiveOutputSide {

    protected int transferRate = 0;
    protected final int BASE_TRANSFER_RATE;
    private final int ENERGY_BASE_CONSUMPTION = (int) (GTValues.V[getTier()] * 15/16);
    private final boolean canHandleVacuum;
    private EnumFacing outputSide;
    private IVacuumContainer pressureContainer;
    private int fluidCapacity;
    private int tierMultiplier = (getTier() * getTier() + 1);
    protected IFluidTank fluidTank;

    public ElectricPressureCompressor(ResourceLocation metaTileEntityId, boolean canHandleVacuum, int tier) {
        super(metaTileEntityId, tier);
        this.canHandleVacuum = canHandleVacuum;
        this.BASE_TRANSFER_RATE = canHandleVacuum ? tierMultiplier : 2 * tierMultiplier;
        this.fluidCapacity = 1000 * (getTier() * getTier() + 1);
        this.fluidTank = new NotifiableFluidTank(fluidCapacity, this, false);
        initializeInventory();
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();
    }

    @Override
    protected ModularUI createUI(EntityPlayer player) {
        return createUITemplate(player).build(getHolder(), player);
    }

    protected abstract int getCurrentTransferRate();

    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        return ModularUI.builder(GuiTextures.BACKGROUND, 176, 166)
                .shouldColor(false)
                .widget(new LabelWidget(5, 5, getMetaFullName()))
                .widget(new LabelWidget(5, 25, String.format("Transfer rate: %d L/t", getCurrentTransferRate())))
                .widget(new TankWidget(fluidTank, 20, 60, 18, 18)
                        .setBackgroundTexture(GuiTextures.FLUID_SLOT)
                        .setAlwaysShowFull(true)
                        .setContainerClicking(true, true))
                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 0);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        ColourMultiplier multiplier = new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering()));
        IVertexOperation[] coloredPipeline = ArrayUtils.add(pipeline, multiplier);

        if (canHandleVacuum) Textures.PIPE_IN_OVERLAY.renderSided(getOutputSide(), renderState, translation, pipeline);
        else TKCYATextures.ROTATION_WATER_OUTPUT_OVERLAY.renderSided(getOutputSide(), renderState, translation, pipeline);
        Textures.ADV_PUMP_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
    }

    public EnumFacing getOutputSide() {
        return outputSide == null ? getFrontFacing().getOpposite() : outputSide;
    }

    public void setOutputSide(EnumFacing outputSide) {
        this.outputSide = outputSide;
        if (!getWorld().isRemote) {
            notifyBlockUpdate();
            writeCustomData(UPDATE_OUTPUT_FACING, buf -> buf.writeByte(outputSide.getIndex()));
            markDirty();
        }
    }

    @Override
    public void setFrontFacing(EnumFacing frontFacing) {
        super.setFrontFacing(frontFacing);
        if (this.outputSide == null) {
            //set initial output facing as opposite to front
            setOutputSide(frontFacing.getOpposite());
        }
    }

    @Override
    public boolean isValidFrontFacing(EnumFacing facing) {
        return facing != EnumFacing.DOWN && facing != UP;
    }

    @Override
    public boolean onWrenchClick(EntityPlayer playerIn, EnumHand hand, EnumFacing wrenchSide, CuboidRayTraceResult hitResult) {
        if (wrenchSide == null) return false;
        if (playerIn.isSneaking()) {
            if (wrenchSide == getFrontFacing() || !isValidFrontFacing(wrenchSide) || !hasFrontFacing()) return false;
            setFrontFacing(wrenchSide);
        } else {
            if (wrenchSide == getOutputSide() || wrenchSide == getFrontFacing()) return false;
            setOutputSide(wrenchSide);
        }
        return true;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            IFluidHandler fluidHandler = fluidInventory;
            if (fluidHandler.getTankProperties().length > 0) {
                return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(fluidHandler);
            }
            return null;
        }

        else if (capability == GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE) {
            if (side == getOutputSide()) {
            return GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE.cast(this);
            }
        return null;
    }
        return super.getCapability(capability, side);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        if (canHandleVacuum) tooltip.add(I18n.format("tkcya.machine.electric_pressure_compressor.vacuum.tooltip.description"));
        else tooltip.add(I18n.format("tkcya.machine.electric_pressure_compressor.pressure.tooltip.description"));
        tooltip.add(I18n.format("tkcya.machine.electric_pressure_compressor.tooltip.transfer", BASE_TRANSFER_RATE));
        tooltip.add(I18n.format("tkcya.machine.electric_pressure_compressor.tooltip.fluid_capacity", fluidCapacity));
        tooltip.add(I18n.format("gregtech.universal.tooltip.max_voltage_in", energyContainer.getInputVoltage(), GTValues.VNF[getTier()]));
        tooltip.add(I18n.format("tkcya.machine.tooltip.consumption", ENERGY_BASE_CONSUMPTION));
        tooltip.add(I18n.format("gregtech.universal.tooltip.energy_storage_capacity", energyContainer.getEnergyCapacity()));
        tooltip.add(I18n.format("tkcya.machine.redstone.inverse.tooltip"));

    }

    @Override
    public boolean isAutoOutputItems() {
        return false;
    }

    @Override
    public boolean isAutoOutputFluids() {
        return false;
    }

    @Override
    public boolean isAllowInputFromOutputSideItems() {
        return false;
    }

    @Override
    public boolean isAllowInputFromOutputSideFluids() {
        return false;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger(OUTPUT_SIDE, getOutputSide().getIndex());
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.outputSide = EnumFacing.VALUES[data.getInteger(OUTPUT_SIDE)];
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeByte(getOutputSide().getIndex());
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.outputSide = EnumFacing.VALUES[buf.readByte()];
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == UPDATE_OUTPUT_FACING) {
            this.outputSide = EnumFacing.VALUES[buf.readByte()];
            scheduleRenderUpdate();
        }
    }

}
