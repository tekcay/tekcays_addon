package tekcays_addon.api.metatileentity;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.IActiveOutputSide;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.TankWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
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
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import org.apache.commons.lang3.ArrayUtils;
import tekcays_addon.api.capability.IVacuumContainer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.capability.GregtechDataCodes.UPDATE_OUTPUT_FACING;
import static gregtech.api.unification.material.Materials.Air;
import static net.minecraft.util.EnumFacing.UP;
import static tekcays_addon.api.utils.TKCYAValues.ATMOSPHERIC_PRESSURE;

public abstract class ElectricPressureCompressor extends TieredMetaTileEntity implements IActiveOutputSide {

    private int transferRate = 0;
    private final int BASE_TRANSFER_RATE;
    private final int ENERGY_BASE_CONSUMPTION = (int) (GTValues.V[getTier()] * 15/16);
    private final boolean canHandleVacuum;
    private EnumFacing outputSide;
    private IVacuumContainer pressureContainer;
    private int fluidCapacity;
    private int tierMultiplier = (getTier() * getTier() + 1);
    private IFluidTank fluidTank;

    public ElectricPressureCompressor(ResourceLocation metaTileEntityId, boolean canHandleVacuum, int tier) {
        super(metaTileEntityId, tier);
        this.canHandleVacuum = canHandleVacuum;
        this.BASE_TRANSFER_RATE = canHandleVacuum ? 50 * tierMultiplier : 100 * tierMultiplier;
        this.fluidCapacity = 1000 * (getTier() * getTier() + 1);
        initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new ElectricPressureCompressor(metaTileEntityId, canHandleVacuum, getTier());
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();
    }

    @Override
    protected FluidTankList createImportFluidHandler() {
        return new FluidTankList(false, new FluidTank(fluidCapacity));
    }

    @Override
    protected FluidTankList createExportFluidHandler() {
        return new FluidTankList(false, new FluidTank(fluidCapacity));
    }

    private int getTankFluidAmount(FluidTankList tank) {
        return tank.getTankAt(0).getFluidAmount();
    }

    @Override
    protected ModularUI createUI(EntityPlayer player) {
        return createUITemplate(player).build(getHolder(), player);
    }

    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        return ModularUI.builder(GuiTextures.BACKGROUND, 176, 166)
                .shouldColor(false)
                .widget(new LabelWidget(5, 5, getMetaFullName()))
                .widget(new LabelWidget(5, 25, String.format("Transfer rate: %d L/t", transferRate)))
                .widget(new TankWidget((canHandleVacuum ? exportFluids : importFluids).getTankAt(0), 20, 60, 18, 18)
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
        else Textures.PIPE_OUT_OVERLAY.renderSided(getOutputSide(), renderState, translation, pipeline);
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

    //To prevent the heater to heat on the bottom face
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

    private void setTransferRate(int pressure) {
        double pressurePercentage = canHandleVacuum ? (double) (100 - ((ATMOSPHERIC_PRESSURE - pressure) / ATMOSPHERIC_PRESSURE)) : (double) pressure / pressureContainer.getMaxPressure();
        transferRate = (int) (BASE_TRANSFER_RATE * pressurePercentage);
    }

    @Override
    public void update() {
        super.update();
    }

    protected int fillExportTank(int amount, boolean doFill) {
        return this.exportFluids.fill(Air.getFluid(amount), doFill);
    }


    protected int drainImportTank(Fluid fluid, int amount, boolean doFill) {
        return this.importFluids.drain(new FluidStack(fluid, amount), doFill).amount;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        if (canHandleVacuum) tooltip.add(I18n.format("tkcya.machine.electric_pressure_compressor.vacuum.tooltip.description"));
        else tooltip.add(I18n.format("tkcya.machine.electric_pressure_compressor.pressure.tooltip.description"));
        tooltip.add(I18n.format("tkcya.machine.electric_pressure_compressor.tooltip.fluid_capacity", fluidCapacity));
        tooltip.add(I18n.format("gregtech.universal.tooltip.max_voltage_in", energyContainer.getInputVoltage(), GTValues.VNF[getTier()]));
        tooltip.add(I18n.format("tkcya.machine.tooltip.consumption", ENERGY_BASE_CONSUMPTION));
        tooltip.add(I18n.format("gregtech.universal.tooltip.energy_storage_capacity", energyContainer.getEnergyCapacity()));
        tooltip.add(I18n.format("tkcya.machine.electric_pressure_compressor.tooltip.transfer", BASE_TRANSFER_RATE));
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
        data.setInteger("outputSide", getOutputSide().getIndex());
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.outputSide = EnumFacing.VALUES[data.getInteger("outputSide")];
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
