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
import lombok.Setter;
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
import tekcays_addon.gtapi.capability.containers.IRotationContainer;
import tekcays_addon.gtapi.capability.containers.ISteamConsumer;
import tekcays_addon.gtapi.capability.impl.RotationContainer;
import tekcays_addon.gtapi.capability.impl.SteamConsumer;
import tekcays_addon.gtapi.utils.FluidStackHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.capability.GregtechDataCodes.IS_WORKING;
import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.gtapi.capability.TKCYATileCapabilities.CAPABILITY_ROTATIONAL_CONTAINER;
import static tekcays_addon.gtapi.render.TKCYATextures.*;
import static tekcays_addon.gtapi.utils.TKCYAValues.STEAM_TO_WATER;

public class MetaTileEntitySteamTurbine extends MetaTileEntity implements IDataInfoProvider, IActiveOutputSide, FluidStackHelper {

    private IFluidTank importFluidTank, exportFluidTank;
    private IRotationContainer rotationContainer;
    private ISteamConsumer steamConsumer;
    private int maxSpeed, maxTorque, maxPower;
    private final int tier;
    private final int maxSteamConsumption, maxWaterOutputRate ;
    @Setter
    private boolean isRunning;
    private final int steamTankCapacity, waterTankCapacity;

    public MetaTileEntitySteamTurbine(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId);
        this.tier = tier + 1;
        this.maxSteamConsumption = STEAM_TO_WATER * this.tier;
        this.maxWaterOutputRate = this.tier;
        this.maxSpeed = 20 * this.tier;
        this.steamTankCapacity = 4000 * this.tier;
        this.waterTankCapacity = 1000 * this.tier;
        this.importFluidTank = new NotifiableFluidTank(steamTankCapacity, this, false);
        this.exportFluidTank = new NotifiableFluidTank(waterTankCapacity, this, true);
        initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntitySteamTurbine(metaTileEntityId, tier - 1);
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();
        this.rotationContainer = new RotationContainer(this,maxSpeed, maxTorque, maxPower);
        this.steamConsumer = new SteamConsumer(this);
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
        IVertexOperation[] colouredPipeline = ArrayUtils.add(pipeline, new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering())));
        getBaseRenderer().render(renderState, translation, colouredPipeline);
        ColourMultiplier multiplier = new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering()));
        colouredPipeline = ArrayUtils.add(pipeline, multiplier);
        Textures.PIPE_IN_OVERLAY.renderSided(getInputSide(), renderState, translation, pipeline);
        ROTATION_TURBINE_OVERLAY.renderOrientedState(renderState, translation, pipeline, getFrontFacing(), isRunning, true);
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
        if (getOffsetTimer() % (10 * STEAM_TO_WATER) == 0) {
            steamConsumer.changeWaterOutputRate(1);
            steamConsumer.changeSteamConsumption(STEAM_TO_WATER);
            rotationContainer.changeSpeed(1);
        }
    }

    private void decrement() {
        if (getOffsetTimer() % 20 == 0) {
            steamConsumer.changeWaterOutputRate(-1);
            steamConsumer.changeSteamConsumption(-STEAM_TO_WATER);
            rotationContainer.changeSpeed(-1);
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

        /*
        if (getExportFluidStack() != null && getExportFluidStack().amount >= waterTankCapacity
            || steamConsumer.getSteamConsumption() > maxSteamConsumption
            || rotationContainer.getSpeed() > maxSpeed) {
            this.doExplosion(2);
        }

         */

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
        TileEntity te = getWorld().getTileEntity(getPos().offset(getRotationSide()));
        if (te != null) {
            //Get the Capability of this Tile Entity on the opposite face.
            IRotationContainer container = te.getCapability(CAPABILITY_ROTATIONAL_CONTAINER, getRotationSide().getOpposite());
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
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE) {
            return side == getFluidOutputSide() ? GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE.cast(this) : null;
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
        tooltip.add(I18n.format("tkcya.machine.steam_turbine.tooltip.steam_input", maxSteamConsumption));
        tooltip.add(I18n.format("tkcya.machine.steam_turbine.tooltip.water_output", maxWaterOutputRate));
        tooltip.add(I18n.format("tkcya.machine.steam_turbine.tooltip.max_speed", maxSpeed));
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

    @Nonnull
    @Override
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = new ObjectArrayList<>();
        list.add(new TextComponentTranslation("behavior.tricorder.steam.amount", getNullableFluidStackAmount(getImportFluidStack())));
        list.add(new TextComponentTranslation("behavior.tricorder.speed", rotationContainer.getSpeed()));
        list.add(new TextComponentTranslation("behavior.tricorder.turbine.steam_consumption", steamConsumer.getSteamConsumption()));
        list.add(new TextComponentTranslation("behavior.tricorder.turbine.output_rate", steamConsumer.getWaterOutputRate()));
        return list;
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
}
