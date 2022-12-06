package tekcays_addon.common.metatileentities.single;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IActiveOutputSide;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import org.apache.commons.lang3.ArrayUtils;
import tekcays_addon.api.capability.IPressureContainer;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.capability.impl.PressureContainer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static net.minecraft.util.EnumFacing.DOWN;
import static net.minecraft.util.EnumFacing.UP;
import static tekcays_addon.api.utils.TKCYAValues.*;

public class MetaTileEntityElectricPressureCompressor extends TieredMetaTileEntity implements IDataInfoProvider, IActiveOutputSide {

    private final int PU_BASE_INCREASE;
    private final int ENERGY_BASE_CONSUMPTION = (int) (GTValues.V[getTier()]);
    private final boolean canHandleVacuum;
    private EnumFacing outputSide;
    private PressureContainer pressureContainer;

    public MetaTileEntityElectricPressureCompressor(ResourceLocation metaTileEntityId, int tier, boolean canHandleVacuum) {
        super(metaTileEntityId, tier);
        this.canHandleVacuum = canHandleVacuum;
        this.PU_BASE_INCREASE = canHandleVacuum ? (int) (GTValues.V[getTier()] * EU_TO_VU / 2) : (int) (GTValues.V[getTier()] * EU_TO_PU / 2);
        this.outputSide = getFrontFacing().getOpposite();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityElectricPressureCompressor(metaTileEntityId, getTier(), canHandleVacuum);
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();
        this.pressureContainer = new PressureContainer(this, canHandleVacuum);
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

        Textures.PIPE_OUT_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
        Textures.ADV_PUMP_OVERLAY.renderSided(getFrontFacing().getOpposite(), renderState, translation, pipeline);
    }

    private EnumFacing getOutputSide() {
        return this.outputSide;
    }

    private void setOutputSide(EnumFacing facing) {
        this.outputSide = facing;
    }

    private boolean isValidOutputSide(EnumFacing facing) {
        return facing != getFrontFacing();
    }


    @Override
    public void update() {
        super.update();
        int currentPU = pressureContainer.getPU();
        if (!getWorld().isRemote) {

            //Redstone stops heating
            if (this.isBlockRedstonePowered()) return;
            if (energyContainer.getEnergyStored() < ENERGY_BASE_CONSUMPTION) return;
            if (currentPU != 0) return;

            pressureContainer.setPressure(currentPU + PU_BASE_INCREASE);
            energyContainer.removeEnergy(ENERGY_BASE_CONSUMPTION);
            tryTransferPU(getFrontFacing());
        }
    }

    private void tryTransferPU(EnumFacing side) {
        TileEntity te = getWorld().getTileEntity(getPos().offset(side));
        if (te != null) {

            //Get the Capability of this Tile Entity on the DOWN FACE.
            IPressureContainer container = te.getCapability(TKCYATileCapabilities.CAPABILITY_PRESSURE_CONTAINER, DOWN);
            if (container == null) return;
            if (!side.equals(getFrontFacing())) return;
            container.changePU(PU_BASE_INCREASE);
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcya.electric_cooler.tooltip.1"));
        tooltip.add(I18n.format("gregtech.universal.tooltip.max_voltage_in", energyContainer.getInputVoltage(), GTValues.VNF[getTier()]));
        tooltip.add(I18n.format("tkcya.machine.energy_conversion_efficiency",  TextFormatting.WHITE + String.format("%.02f", EU_TO_HU * 50) + "%"));
        tooltip.add(I18n.format("gregtech.universal.tooltip.energy_storage_capacity", energyContainer.getEnergyCapacity()));
        tooltip.add(I18n.format("tkcya.electric_cooler.tooltip.2", PU_BASE_INCREASE));
        tooltip.add(I18n.format("tkcya.electric_cooler.tooltip.3", PU_BASE_INCREASE));
        tooltip.add(I18n.format("tkcya.machine.redstone.inverse.tooltip"));
        tooltip.add(I18n.format("gregtech.machine.item_controller.tooltip.consumption", ENERGY_BASE_CONSUMPTION));
    }

    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE) {
            return side == getFrontFacing() ? GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE.cast(this) : null;
        }
        if (capability.equals(TKCYATileCapabilities.CAPABILITY_PRESSURE_CONTAINER)) {
            return TKCYATileCapabilities.CAPABILITY_PRESSURE_CONTAINER.cast(pressureContainer);
        }
        return super.getCapability(capability, side);
    }

    //To prevent the heater to heat on the bottom face
    @Override
    public boolean isValidFrontFacing(EnumFacing facing) {
        return facing != EnumFacing.DOWN && facing != UP;
    }

    @Override
    public boolean onWrenchClick(EntityPlayer playerIn, EnumHand hand, EnumFacing wrenchSide, CuboidRayTraceResult hitResult) {
        if (playerIn.isSneaking()) {
            if (wrenchSide == getFrontFacing() || !isValidFrontFacing(wrenchSide) || !hasFrontFacing()) return false;
            if (wrenchSide != null && !getWorld().isRemote) setFrontFacing(wrenchSide);
        } else {
            if (wrenchSide == getOutputSide() || !isValidOutputSide(wrenchSide)) return false;
            if (wrenchSide != null && !getWorld().isRemote) setOutputSide(wrenchSide);
        }
        return true;
    }

    @Nonnull
    @Override
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = new ObjectArrayList<>();
        if (canHandleVacuum) list.add(new TextComponentTranslation("behavior.tricorder.current_vu", pressureContainer.getPU()));
        else list.add(new TextComponentTranslation("behavior.tricorder.current_pu", pressureContainer.getPU()));
        return list;
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

}
