package tekcays_addon.common.metatileentities.single;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.GregtechTileCapabilities;
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
import net.minecraft.tileentity.TileEntity;
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
import tekcays_addon.api.utils.AdjacentCapabilityHelper;
import tekcays_addon.api.utils.FluidStackHelper;
import tekcays_addon.api.utils.PressureContainerHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.capability.GregtechDataCodes.IS_WORKING;
import static gregtech.api.unification.material.Materials.Steam;
import static tekcays_addon.api.consts.NBTKeys.IS_RUNNING;
import static tekcays_addon.api.render.TKCYATextures.*;
import static tekcays_addon.api.utils.TKCYAValues.STEAM_TO_WATER;

public class MetaTileEntityRotationCompressor extends MetaTileEntity implements IDataInfoProvider, IActiveOutputSide, FluidStackHelper, PressureContainerHandler, AdjacentCapabilityHelper<IPressureContainer> {

    private IFluidTank importFluidTank;
    private RotationContainer rotationContainer;
    private PressureContainer pressureContainer;
    private int maxSpeed, maxTorque, maxPower;
    private int speed, torque, power;
    private int baseTransferRate;
    private int pressure, minPressure, maxPressure;
    private final int tier;
    private final int maxSteamConsumption;
    private final int maxWaterOutputRate;
    private int steamConsumption, waterOutputRate;
    private boolean isRunning;
    private int inputTankCapacity;


    public MetaTileEntityRotationCompressor(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId);
        this.tier = tier + 1;
        this.maxSteamConsumption = STEAM_TO_WATER * this.tier;
        this.maxWaterOutputRate = this.tier;
        this.maxSpeed = 20 * this.tier;
        this.inputTankCapacity = 4000 * this.tier;
        this.rotationContainer = new RotationContainer(this, maxPower, maxSpeed, maxTorque);
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
        this.pressureContainer = new PressureContainer(this, minPressure, maxPressure);
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
        Textures.PIPE_IN_OVERLAY.renderSided(getInputSide(), renderState, translation, pipeline);
        ROTATION_TURBINE_OVERLAY.renderOrientedState(renderState, translation, pipeline, getFrontFacing(), isRunning, true);
        ROTATION_WATER_OUTPUT_OVERLAY.renderSided(getOutputSide(), renderState, translation, pipeline);
    }

    private EnumFacing getInputSide() {
        return getFrontFacing().rotateY();
    }

    private EnumFacing getRotationSide() {
        return getFrontFacing().getOpposite();
    }

    @Nullable
    private FluidStack getImportFluidStack() {
        return importFluidTank.getFluid();
    }

    private void increment() {
        if (getOffsetTimer() % (10 * STEAM_TO_WATER) == 0) {
            steamConsumption += STEAM_TO_WATER;
            waterOutputRate ++;
            speed ++;
        }
    }

    private void decrement() {
        if (getOffsetTimer() % 40 == 0) {
            steamConsumption = Math.max(steamConsumption - STEAM_TO_WATER, 0);
            waterOutputRate = Math.max(waterOutputRate - 1, 0);
            speed = Math.max(speed - 1, 0);
        }
    }

    @Override
    public void update() {
        super.update();
        if (getImportFluidStack() == null || !getImportFluidStack().isFluidEqual(Steam.getFluid(1))) {
            decrement();
            setRunningState(false);
            return;
        }
        if (getImportFluidStack().amount < steamConsumption) {
            decrement();
            setRunningState(false);
            return;
        }

        importFluidTank.drain(steamConsumption, true);
        pushFluidsIntoNearbyHandlers(getOutputSide());
        transferRotation();
        increment();
        setRunningState(true);

    }

    public void setRunningState(boolean isRunning) {
        this.isRunning = isRunning;
        if (!getWorld().isRemote) {
            markDirty();
            writeCustomData(IS_WORKING, buf -> buf.writeBoolean(isRunning));
        }
    }

    private void transferRotation() {
        //Get the TileEntity that is placed right on top of the Heat.
        TileEntity te = getWorld().getTileEntity(getPos().offset(getFrontFacing()));
        if (te != null) {
            //Get the Capability of this Tile Entity on the opposite face.
            IRotationContainer container = te.getCapability(TKCYATileCapabilities.CAPABILITY_ROTATIONAL_CONTAINER, getFrontFacing().getOpposite());
            if (container != null) {
                container.setSpeed(speed);
                container.setPower(power);
                container.setTorque(torque);
            }
        }
    }

    @Override
    public boolean isActive() {
        return this.isRunning;
    }

    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE) {
            return side == getOutputSide() ? GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE.cast(this) : null;
        }
        if (capability.equals(TKCYATileCapabilities.CAPABILITY_ROTATIONAL_CONTAINER)) {
            return side == getRotationSide() ? TKCYATileCapabilities.CAPABILITY_ROTATIONAL_CONTAINER.cast(rotationContainer) : null;
        }
        return super.getCapability(capability, side);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("tkcya.machine.steam_turbine.tooltip.1"));
        tooltip.add(I18n.format("tkcya.machine.steam_turbine.tooltip.steam_tank", inputTankCapacity));
        tooltip.add(I18n.format("tkcya.machine.steam_turbine.tooltip.steam_input", maxSteamConsumption));
        tooltip.add(I18n.format("tkcya.machine.steam_turbine.tooltip.water_output", maxWaterOutputRate));
        tooltip.add(I18n.format("tkcya.machine.steam_turbine.tooltip.max_speed", maxSpeed));
        super.addInformation(stack, player, tooltip, advanced);
    }

    @Nonnull
    @Override
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = new ObjectArrayList<>();
        list.add(new TextComponentTranslation("behavior.tricorder.steam.amount", getNullableFluidStackAmount(getImportFluidStack())));
        list.add(new TextComponentTranslation("behavior.tricorder.speed", speed));
        list.add(new TextComponentTranslation("behavior.tricorder.turbine.steam_consumption", steamConsumption));
        list.add(new TextComponentTranslation("behavior.tricorder.turbine.output_rate", waterOutputRate));
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
        return getFrontFacing().rotateYCCW();
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
