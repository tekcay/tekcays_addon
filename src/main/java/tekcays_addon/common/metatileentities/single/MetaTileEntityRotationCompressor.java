package tekcays_addon.common.metatileentities.single;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.IActiveOutputSide;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.NotifiableFluidTank;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import gregtech.core.sound.GTSoundEvents;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.capability.containers.IPressureContainer;
import tekcays_addon.api.capability.containers.IRotationContainer;
import tekcays_addon.api.capability.impl.PressureContainer;
import tekcays_addon.api.capability.impl.RotationContainer;
import tekcays_addon.api.utils.capability.AdjacentCapabilityHelper;
import tekcays_addon.api.utils.FluidStackHelper;
import tekcays_addon.api.utils.PressureContainerHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.capability.GregtechDataCodes.IS_WORKING;
import static tekcays_addon.api.capability.TKCYATileCapabilities.CAPABILITY_ROTATIONAL_CONTAINER;
import static tekcays_addon.api.consts.NBTKeys.IS_RUNNING;
import static tekcays_addon.api.render.TKCYATextures.*;

public class MetaTileEntityRotationCompressor extends MetaTileEntity implements IDataInfoProvider, IActiveOutputSide, FluidStackHelper, PressureContainerHandler, AdjacentCapabilityHelper<IPressureContainer> {

    private IFluidTank importFluidTank;
    private IRotationContainer rotationContainer;
    private IPressureContainer pressureContainer;
    private final int maxSpeed, maxTorque, maxPower;
    private int speed, torque, power;
    private int baseTransferRate;
    private int pressure, minPressure, maxPressure;
    private final int tier;
    private int transferRate;
    private boolean isRunning;
    private int inputTankCapacity;


    public MetaTileEntityRotationCompressor(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId);
        this.tier = tier + 1;
        this.maxSpeed = 20 * this.tier;
        this.maxTorque = maxSpeed * 5;
        this.maxPower = maxSpeed * maxTorque;
        this.inputTankCapacity = 4000 * this.tier;
        this.importFluidTank = new NotifiableFluidTank(inputTankCapacity, this, false);
        initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityRotationCompressor(metaTileEntityId, tier - 1);
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();
        this.rotationContainer = new RotationContainer(this, maxPower, maxSpeed, maxTorque);
    }

    @Override
    public FluidTankList createImportFluidHandler() {
        this.importFluidTank = new NotifiableFluidTank(inputTankCapacity * tier, this, false);
        return new FluidTankList(false, importFluidTank);
    }

    @Override
    protected ModularUI createUI(EntityPlayer player) {
        return null;
    }

    @SideOnly(Side.CLIENT)
    protected SimpleOverlayRenderer getBaseRenderer() {
        return STEAM_CASING[tier - 1];
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        IVertexOperation[] colouredPipeline = ArrayUtils.add(pipeline, new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering())));
        getBaseRenderer().render(renderState, translation, colouredPipeline);
        ColourMultiplier multiplier = new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering()));
        colouredPipeline = ArrayUtils.add(pipeline, multiplier);
        Textures.PIPE_IN_OVERLAY.renderSided(getImportFluidSide(), renderState, translation, pipeline);
        ROTATION_STATIC.renderSided(getRotationSide(), renderState, translation, pipeline);
        ROTATION_WATER_OUTPUT_OVERLAY.renderSided(getPressureSide(), renderState, translation, pipeline);
    }

    private EnumFacing getImportFluidSide() {
        return getFrontFacing().getOpposite();
    }

    private EnumFacing getPressureSide() {
        return getFrontFacing().rotateYCCW();
    }

    private EnumFacing getRotationSide() {
        return getPressureSide().getOpposite();
    }

    @Nullable
    private FluidStack getImportFluidStack() {
        return importFluidTank.getFluid();
    }

    @Override
    public void update() {
        super.update();
        //Redstone stops fluid transfer
        if (this.isBlockRedstonePowered()) return;
        if (rotationContainer.getSpeed() == 0) return;

        if (!getWorld().isRemote) {
            pressureContainer = getAdjacentCapabilityContainer(this);
            if (pressureContainer != null) {
                pressure = pressureContainer.getPressure();
                transferRate = getBaseTransferRate();
            } else {
                transferRate = 0;
                return;
            }

            if (getImportFluidStack() == null) return;

            int toDrain = pressureContainer.changePressurizedFluidStack(getImportFluidStack(), transferRate);

            if (toDrain > 0) {
                importFluidTank.drain(toDrain, true);
            }
        }
    }

    public void setRunningState(boolean isRunning) {
        this.isRunning = isRunning;
        if (!getWorld().isRemote) {
            markDirty();
            writeCustomData(IS_WORKING, buf -> buf.writeBoolean(isRunning));
        }
    }

    @Override
    public boolean isActive() {
        return this.isRunning;
    }

    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {
        if (capability == CAPABILITY_ROTATIONAL_CONTAINER) {
            return side == getRotationSide() ? CAPABILITY_ROTATIONAL_CONTAINER.cast(rotationContainer) : null;
        }
        return super.getCapability(capability, side);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("tkcya.machine.steam_turbine.tooltip.1"));
        tooltip.add(I18n.format("tkcya.machine.steam_turbine.tooltip.steam_tank", inputTankCapacity));
        tooltip.add(I18n.format("tkcya.machine.steam_turbine.tooltip.max_speed", maxSpeed));
        super.addInformation(stack, player, tooltip, advanced);
    }

    @Nonnull
    @Override
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = new ObjectArrayList<>();
        list.add(new TextComponentTranslation("behavior.tricorder.steam.amount", getNullableFluidStackAmount(getImportFluidStack())));
        list.add(new TextComponentTranslation("behavior.tricorder.speed", speed));
        return list;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setBoolean(IS_RUNNING, isRunning);
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.isRunning = data.getBoolean(IS_RUNNING);
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(this.isRunning);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.isRunning = buf.readBoolean();
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == IS_WORKING) {
            this.isRunning = buf.readBoolean();
            scheduleRenderUpdate();
        }
    }

    @Override
    public SoundEvent getSound() {
        return GTSoundEvents.TURBINE;
    }

    //Implementations

    @Override
    public IPressureContainer getPressureContainer() {
        return this.pressureContainer;
    }

    @Override
    public int getBaseTransferRate() {
        return this.baseTransferRate;
    }

    @Override
    public int getPressure() {
        return this.pressure;
    }

    @Override
    public FluidTankList importFluidTanks() {
        return this.importFluids;
    }

    @Override
    public EnumFacing getOutputSide() {
        return getPressureSide();
    }

    @Override
    public Capability<IPressureContainer> getCapability() {
        return TKCYATileCapabilities.CAPABILITY_PRESSURE_CONTAINER;
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
