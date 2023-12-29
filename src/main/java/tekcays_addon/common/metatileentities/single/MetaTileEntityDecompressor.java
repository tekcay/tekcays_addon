package tekcays_addon.common.metatileentities.single;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.IActiveOutputSide;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.NotifiableFluidTank;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.TankWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import gregtech.core.sound.GTSoundEvents;
import tekcays_addon.gtapi.capability.containers.IDecompression;
import tekcays_addon.gtapi.capability.impl.DecompressionContainer;

public class MetaTileEntityDecompressor extends TieredMetaTileEntity implements IActiveOutputSide {

    private IFluidTank importFluidTank;
    private final IDecompression decompression;
    private final int tankCapacity;
    private EnumFacing outputSide;
    private final int tier;
    private final int ENERGY_BASE_CONSUMPTION = (int) (GTValues.V[getTier()]);

    public MetaTileEntityDecompressor(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
        this.tier = tier;
        this.tankCapacity = 1000 * (tier + 1);
        super.initializeInventory();
        this.decompression = new DecompressionContainer(this, fluidInventory);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityDecompressor(metaTileEntityId, tier);
    }

    @Override
    public FluidTankList createImportFluidHandler() {
        this.importFluidTank = new NotifiableFluidTank(tankCapacity, this, false);
        return new FluidTankList(false, importFluidTank);
    }

    @Override
    public void update() {
        super.update();
        if (isBlockRedstonePowered()) {
            decompression.setActivity(false);
            decompression.setCompressAbility(false);
            return;
        }
        if (energyContainer.getEnergyStored() > ENERGY_BASE_CONSUMPTION) {
            decompression.setCompressAbility(true);
        } else {
            decompression.setCompressAbility(false);
            return;
        }
        if (decompression.isActive()) {
            energyContainer.removeEnergy(ENERGY_BASE_CONSUMPTION);
            BlockPos pos = this.getPos().offset(getFrontFacing());
            TileEntity tileEntity = getWorld().getTileEntity(pos);
            if (tileEntity == null) return;

            IFluidHandler fluidHandler = tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,
                    getFrontFacing().getOpposite());
            if (fluidHandler == null) return;

            GTTransferUtils.transferFluids(importFluids, fluidHandler);
            pushFluidsIntoNearbyHandlers(getFrontFacing());
        }
    }

    @Override
    protected ModularUI createUI(EntityPlayer player) {
        return createUITemplate(player).build(getHolder(), player);
    }

    protected ModularUI.Builder createUITemplate(@NotNull EntityPlayer entityPlayer) {
        return ModularUI.defaultBuilder()
                .widget(new LabelWidget(6, 6, getMetaFullName()))
                .widget(new TankWidget(importFluidTank, 52, 18, 72, 61)
                        .setBackgroundTexture(GuiTextures.SLOT)
                        .setContainerClicking(true, true))
                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 0);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        IVertexOperation[] colouredPipeline = ArrayUtils.add(pipeline,
                new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering())));
        getBaseRenderer().render(renderState, translation, colouredPipeline);
        Textures.PIPE_OUT_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("tkcya.machine.decompressor.tooltip.1"));
        tooltip.add(I18n.format("tkcya.machine.decompressor.tooltip.2"));
        tooltip.add(I18n.format("tkcya.machine.decompressor.tooltip.3"));
        tooltip.add(I18n.format("tkcya.general.fluid_capacity", tankCapacity));
        tooltip.add(I18n.format("gregtech.universal.tooltip.max_voltage_in", energyContainer.getInputVoltage(),
                GTValues.VNF[getTier()]));
        tooltip.add(I18n.format("tkcya.machine.tooltip.consumption", ENERGY_BASE_CONSUMPTION));
        tooltip.add(
                I18n.format("gregtech.universal.tooltip.energy_storage_capacity", energyContainer.getEnergyCapacity()));
        tooltip.add(I18n.format("tkcya.machine.redstone.inverse.tooltip"));
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

    @Override
    public SoundEvent getSound() {
        return GTSoundEvents.TURBINE;
    }
}
