package tekcays_addon.common.metatileentities.single;

import static gregtech.api.capability.GregtechDataCodes.IS_WORKING;
import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.api.fluids.FluidStackHelper.*;
import static tekcays_addon.gtapi.capability.TKCYATileCapabilities.CAPABILITY_ROTATIONAL_CONTAINER;
import static tekcays_addon.gtapi.consts.TKCYAValues.STEAM_TO_WATER;
import static tekcays_addon.gtapi.render.TKCYATextures.*;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
import lombok.Setter;
import tekcays_addon.gtapi.capability.containers.IRotationContainer;
import tekcays_addon.gtapi.capability.containers.ISteamConsumer;
import tekcays_addon.gtapi.capability.impl.RotationContainer;
import tekcays_addon.gtapi.capability.impl.SteamConsumer;

public class MetaTileEntitySteamTurbine extends MetaTileEntity
                                        implements IDataInfoProvider, IActiveOutputSide {

    private IFluidTank importFluidTank, exportFluidTank;
    private IRotationContainer rotationContainer;
    private ISteamConsumer steamConsumer;
    private final int tier;
    @Setter
    private boolean isRunning;
    private final int steamTankCapacity, waterTankCapacity;

    public MetaTileEntitySteamTurbine(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId);
        this.tier = tier + 1;
        this.steamTankCapacity = 4000 * this.tier;
        this.waterTankCapacity = 1000 * this.tier;
        this.rotationContainer = new RotationContainer(this, 20 * this.tier, 0, 0);
        this.steamConsumer = new SteamConsumer(this, STEAM_TO_WATER * this.tier, this.tier);
        super.initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntitySteamTurbine(metaTileEntityId, tier - 1);
    }

    @Override
    public FluidTankList createExportFluidHandler() {
        this.exportFluidTank = new NotifiableFluidTank(waterTankCapacity * tier, this, true);
        return new FluidTankList(false, exportFluidTank);
    }

    @Override
    public FluidTankList createImportFluidHandler() {
        this.importFluidTank = new NotifiableFluidTank(steamTankCapacity * tier, this, false);
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
        IVertexOperation[] colouredPipeline = ArrayUtils.add(pipeline,
                new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering())));
        getBaseRenderer().render(renderState, translation, colouredPipeline);
        ColourMultiplier multiplier = new ColourMultiplier(
                GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering()));
        colouredPipeline = ArrayUtils.add(pipeline, multiplier);
        Textures.PIPE_IN_OVERLAY.renderSided(getInputSide(), renderState, translation, pipeline);
        ROTATION_TURBINE_OVERLAY.renderOrientedState(renderState, translation, pipeline, getFrontFacing(), isRunning,
                true);
        ROTATION_WATER_OUTPUT_OVERLAY.renderSided(getFluidOutputSide(), renderState, translation, pipeline);
    }

    private EnumFacing getInputSide() {
        return getFrontFacing().rotateY();
    }

    private EnumFacing getFluidOutputSide() {
        return getFrontFacing().rotateYCCW();
    }

    private EnumFacing getRotationSide() {
        return getFrontFacing().getOpposite();
    }

    @Nullable
    private FluidStack getImportFluidStack() {
        return importFluidTank.getFluid();
    }

    @Nullable
    private FluidStack getExportFluidStack() {
        return exportFluidTank.getFluid();
    }

    private void increment() {
        changeParameters(30, 1, STEAM_TO_WATER, 1);
    }

    private void decrement() {
        changeParameters(20, -1, -STEAM_TO_WATER, -1);
    }

    private void changeParameters(int offSetTimer, int waterOutputRate, int steamConsumption, int speed) {
        if (getOffsetTimer() % offSetTimer == 0) {
            steamConsumer.changeWaterOutputRate(waterOutputRate);
            steamConsumer.changeSteamConsumption(steamConsumption);
            rotationContainer.changeSpeed(speed);
        }
    }

    @Override
    public void update() {
        super.update();

        if (rotationContainer.getSpeed() == 0) setRunningState(false);

        if (getImportFluidStack() == null || !getImportFluidStack().isFluidEqual(Steam.getFluid(1))) {
            decrement();
            return;
        }
        if (getImportFluidStack().amount < steamConsumer.getSteamConsumption()) {
            decrement();
            return;
        }

        importFluidTank.drain(steamConsumer.getSteamConsumption(), true);
        exportFluidTank.fill(DistilledWater.getFluid(steamConsumer.getWaterOutputRate()), true);
        pushFluidsIntoNearbyHandlers(getFluidOutputSide());
        transferRotation();
        increment();
        setRunningState(true);

        if (getExportFluidStack() != null && getExportFluidStack().amount >= waterTankCapacity ||
                rotationContainer.getSpeed() > rotationContainer.getMaxSpeed()) {
            this.doExplosion(1);
        }
    }

    public void setRunningState(boolean isRunning) {
        this.isRunning = isRunning;
        if (!getWorld().isRemote) {
            markDirty();
            writeCustomData(IS_WORKING, buf -> buf.writeBoolean(isRunning));
        }
    }

    private void transferRotation() {
        // Get the TileEntity that is placed right on top of the Heat.
        TileEntity te = getWorld().getTileEntity(getPos().offset(getRotationSide()));
        if (te != null) {
            // Get the Capability of this Tile Entity on the opposite face.
            IRotationContainer container = te.getCapability(CAPABILITY_ROTATIONAL_CONTAINER,
                    getRotationSide().getOpposite());
            if (container != null) {
                container.setRotationParams(rotationContainer);
            }
        }
    }

    @Override
    public boolean isActive() {
        return this.isRunning;
    }

    @Override
    @Nullable
    public <T> T getCapability(@NotNull Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE) {
            return side == getFluidOutputSide() ? GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE.cast(this) :
                    null;
        }
        if (capability.equals(CAPABILITY_ROTATIONAL_CONTAINER) && side == getRotationSide()) {
            return CAPABILITY_ROTATIONAL_CONTAINER.cast(rotationContainer);
        }
        return super.getCapability(capability, side);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("tkcya.machine.steam_turbine.tooltip.1"));
        tooltip.add(I18n.format("tkcya.machine.steam_turbine.tooltip.steam_tank", steamTankCapacity));
        tooltip.add(I18n.format("tkcya.machine.steam_turbine.tooltip.water_tank", waterTankCapacity));
        steamConsumer.addTooltip(tooltip);
        rotationContainer.addTooltip(tooltip);
        super.addInformation(stack, player, tooltip, advanced);
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

    @NotNull
    @Override
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = new ObjectArrayList<>();
        list.add(new TextComponentTranslation("behavior.tricorder.steam.amount",
                getNullableFluidStackAmount(getImportFluidStack())));
        list.add(new TextComponentTranslation("behavior.tricorder.speed", rotationContainer.getSpeed()));
        list.add(new TextComponentTranslation("behavior.tricorder.turbine.steam_consumption",
                steamConsumer.getSteamConsumption()));
        list.add(new TextComponentTranslation("behavior.tricorder.turbine.output_rate",
                steamConsumer.getWaterOutputRate()));
        return list;
    }

    @Override
    public void receiveCustomData(int dataId, @NotNull PacketBuffer buf) {
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
}
