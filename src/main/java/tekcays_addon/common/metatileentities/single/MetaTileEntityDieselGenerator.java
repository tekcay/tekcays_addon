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
import gregtech.client.renderer.texture.cube.SimpleSidedCubeRenderer;
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
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;
import tekcays_addon.api.capability.AdjacentCapabilityHelper;
import tekcays_addon.api.metatileentity.IFreeFace;
import tekcays_addon.gtapi.capability.containers.IRotationContainer;
import tekcays_addon.gtapi.capability.impl.RotationContainer;
import tekcays_addon.gtapi.utils.FluidStackHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.capability.GregtechDataCodes.IS_WORKING;
import static gregtech.api.unification.material.Materials.Steam;
import static tekcays_addon.gtapi.capability.TKCYATileCapabilities.CAPABILITY_ROTATIONAL_CONTAINER;
import static tekcays_addon.gtapi.render.TKCYATextures.*;

public class MetaTileEntityDieselGenerator extends MetaTileEntity implements IDataInfoProvider, IActiveOutputSide, IFreeFace, AdjacentCapabilityHelper<IFluidHandler>, FluidStackHelper {

    private IFluidTank importFuelTank;
    private IRotationContainer rotationContainer;
    private final int tier;
    private int fuelTankCapacity;
    private int fuelConsumption;
    private int carbonDioxideOutputRate;
    @Setter
    private boolean isRunning;

    public MetaTileEntityDieselGenerator(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId);
        this.tier = tier + 1;
        this.fuelTankCapacity = 1000 * this.tier;
        this.rotationContainer = new RotationContainer(this, 20 * this.tier, 0, 0);
        super.initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityDieselGenerator(metaTileEntityId, tier - 1);
    }
    
    @Override
    public FluidTankList createImportFluidHandler() {
        this.importFuelTank = new NotifiableFluidTank(fuelTankCapacity, this, false);
        return new FluidTankList(false, importFuelTank);
    }

    @Override
    protected ModularUI createUI(EntityPlayer player) {
        return null;
    }

    @SideOnly(Side.CLIENT)
    protected SimpleSidedCubeRenderer getBaseRenderer() {
        return Textures.VOLTAGE_CASINGS[tier];
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        IVertexOperation[] colouredPipeline = ArrayUtils.add(pipeline, new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering())));
        getBaseRenderer().render(renderState, translation, colouredPipeline);
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
        return importFuelTank.getFluid();
    }

    private void increment() {
        changeParameters(30, 1, 1, 1);
    }

    private void decrement() {
        changeParameters(20, -1, -1, -1);
    }

    private void changeParameters(int offSetTimer, int speed, int fuelConsumption, int carbonDioxideOutputRate) {
        if (getOffsetTimer() % offSetTimer == 0) {
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
        if (getImportFluidStack().amount < fuelConsumption) {
            decrement();
            return;
        }

        if (!canOutputCarbonDioxide()) this.doExplosion(1);

        importFuelTank.drain(fuelConsumption, true);
        pushFluidsIntoNearbyHandlers(getOutputSide());
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

    private boolean isOutputSideFree() {
        return checkFaceFree(this.getPos(), getOutputSide());
    }

    @Nullable
    private IFluidHandler getAdjacentFluidHandler() {
        return getAdjacentCapabilityContainer(this);
    }

    private boolean canOutputCarbonDioxide() {
        return isOutputSideFree() || getAdjacentFluidHandler() != null;
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
            return side == getOutputSide() ? GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE.cast(this) : null;
        }
        if (capability.equals(CAPABILITY_ROTATIONAL_CONTAINER) && side == getRotationSide()) {
            return CAPABILITY_ROTATIONAL_CONTAINER.cast(rotationContainer);
        }
        return super.getCapability(capability, side);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("tkcya.machine.steam_turbine.tooltip.1"));
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

    @Nonnull
    @Override
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = new ObjectArrayList<>();
        list.add(new TextComponentTranslation("behavior.tricorder.steam.amount", getNullableFluidStackAmount(getImportFluidStack())));
        list.add(new TextComponentTranslation("behavior.tricorder.speed", rotationContainer.getSpeed()));
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

    @Override
    public EnumFacing getOutputSide() {
        return getFrontFacing().rotateYCCW();
    }

    @Override
    public Capability<IFluidHandler> getCapability() {
        return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
    }
}
