package tekcays_addon.common.metatileentities.single;

import static gregtech.api.capability.GregtechDataCodes.IS_WORKING;
import static tekcays_addon.api.consts.NBTKeys.IS_RUNNING;
import static tekcays_addon.api.fluids.FluidStackHelper.*;
import static tekcays_addon.gtapi.capability.TKCYATileCapabilities.CAPABILITY_PRESSURE_CONTAINER;
import static tekcays_addon.gtapi.capability.TKCYATileCapabilities.CAPABILITY_ROTATIONAL_CONTAINER;
import static tekcays_addon.gtapi.render.TKCYATextures.*;

import java.util.List;

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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
import tekcays_addon.api.capability.AdjacentCapabilityHelper;
import tekcays_addon.gtapi.capability.TKCYATileCapabilities;
import tekcays_addon.gtapi.capability.containers.IPressureContainer;
import tekcays_addon.gtapi.capability.containers.IRotationContainer;
import tekcays_addon.gtapi.capability.impl.RotationContainer;
import tekcays_addon.gtapi.utils.PressureContainerHandler;

public class MetaTileEntityRotationCompressor extends MetaTileEntity
                                              implements IDataInfoProvider, IActiveOutputSide,
                                              PressureContainerHandler, AdjacentCapabilityHelper<IPressureContainer> {

    private IFluidTank importSteamTank;
    private IFluidTank importLubTank;
    private IRotationContainer rotationContainer;
    private IPressureContainer pressureContainer;
    private int baseTransferRate;
    private final int tier;
    private int transferRate;
    private boolean isRunning;
    private final int inputTankCapacity;

    public MetaTileEntityRotationCompressor(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId);
        this.tier = tier + 1;
        this.rotationContainer = new RotationContainer(this, 20 * this.tier, 100 * this.tier,
                (int) (2000 * Math.pow(this.tier * this.tier, 2)));
        this.inputTankCapacity = 4000 * this.tier;

        super.initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityRotationCompressor(metaTileEntityId, tier - 1);
    }

    @Override
    public FluidTankList createImportFluidHandler() {
        this.importSteamTank = new NotifiableFluidTank(inputTankCapacity, this, false);
        this.importLubTank = new NotifiableFluidTank(inputTankCapacity / 1000, this, false);
        return new FluidTankList(false, importSteamTank, importLubTank);
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
        IVertexOperation[] colouredPipeline = ArrayUtils.add(pipeline,
                new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering())));
        getBaseRenderer().render(renderState, translation, colouredPipeline);
        ColourMultiplier multiplier = new ColourMultiplier(
                GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering()));
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
    private FluidStack getImportSteamFluidStack() {
        return importSteamTank.getFluid();
    }

    @Nullable
    private FluidStack getImportLubFluidStack() {
        return importLubTank.getFluid();
    }

    @Override
    public void update() {
        super.update();
        // Redstone stops fluid transfer
        if (this.isBlockRedstonePowered()) return;
        if (rotationContainer.getSpeed() == 0) return;

        importLubTank.drain(rotationContainer.getSpeed() / 2, true);

        if (!getWorld().isRemote) {
            pressureContainer = getAdjacentCapabilityContainer(this);
            if (pressureContainer != null) {
                transferRate = rotationContainer.getSpeed();
            } else {
                transferRate = 0;
                return;
            }

            if (getImportSteamFluidStack() == null) return;

            int toDrain = pressureContainer.changePressurizedFluidStack(getImportSteamFluidStack(), transferRate);

            if (toDrain > 0) {
                importSteamTank.drain(toDrain, true);
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
    public <T> T getCapability(@NotNull Capability<T> capability, EnumFacing side) {
        if (capability == CAPABILITY_ROTATIONAL_CONTAINER && side == getRotationSide()) {
            return CAPABILITY_ROTATIONAL_CONTAINER.cast(rotationContainer);
        }
        if (capability == CAPABILITY_PRESSURE_CONTAINER && side == getPressureSide()) {
            return CAPABILITY_PRESSURE_CONTAINER.cast(pressureContainer);
        }
        return super.getCapability(capability, side);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("tkcya.machine.steam_turbine.tooltip.1"));
        tooltip.add(I18n.format("tkcya.machine.steam_turbine.tooltip.steam_tank", inputTankCapacity));
        rotationContainer.addTooltip(tooltip);
        // super.addInformation(stack, player, tooltip, advanced);
    }

    @NotNull
    @Override
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = new ObjectArrayList<>();
        list.add(new TextComponentTranslation("behavior.tricorder.steam.amount",
                getNullableFluidStackAmount(getImportSteamFluidStack())));
        list.add(new TextComponentTranslation("behavior.tricorder.lub.amount",
                getNullableFluidStackAmount(getImportLubFluidStack())));
        list.add(new TextComponentTranslation("behavior.tricorder.speed", rotationContainer.getSpeed()));
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

    // Implementations

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
        return pressureContainer.getPressure();
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
