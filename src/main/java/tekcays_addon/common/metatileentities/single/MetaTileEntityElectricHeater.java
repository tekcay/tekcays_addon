package tekcays_addon.common.metatileentities.single;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregicality.science.api.utils.NumberFormattingUtil;
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
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import tekcays_addon.api.capability.IHeatContainer;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.capability.impl.HeatContainer;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import org.apache.commons.lang3.ArrayUtils;
import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.api.utils.TKCYALog;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static net.minecraft.util.EnumFacing.*;
import static tekcays_addon.api.utils.TKCYAValues.EU_TO_HU;

public class MetaTileEntityElectricHeater extends TieredMetaTileEntity implements IDataInfoProvider, IActiveOutputSide {

    private final int HEAT_BASE_INCREASE = (int) (GTValues.V[getTier()] * EU_TO_HU);
    private final int ENERGY_BASE_CONSUMPTION = (int) (GTValues.V[getTier()]);
    private HeatContainer heatContainer;

    public MetaTileEntityElectricHeater(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityElectricHeater(metaTileEntityId, getTier());
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();
        this.heatContainer = new HeatContainer(this, 0, 20000);
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

        Textures.AIR_VENT_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
        TKCYATextures.HEAT_ACCEPTOR_VERTICALS_OVERLAY.renderSided(UP, renderState, translation, pipeline);
    }


    @Override
    public void update() {
        super.update();
        int currentHeat = heatContainer.getHeat();
        if (!getWorld().isRemote) {
            //Redstone stops heating
            if (this.isBlockRedstonePowered()) return;
            if (energyContainer.getEnergyStored() < ENERGY_BASE_CONSUMPTION) return;
            if (currentHeat + HEAT_BASE_INCREASE < heatContainer.getMaxHeat()) heatContainer.setHeat(currentHeat + HEAT_BASE_INCREASE);

            energyContainer.removeEnergy(ENERGY_BASE_CONSUMPTION);

            //Get the TileEntity that is placed right on top of the Heat.
            TileEntity te = getWorld().getTileEntity(getPos().offset(UP));
            if (te != null) {
                //Get the Capability of this Tile Entity on the DOWN FACE.
                IHeatContainer container = te.getCapability(TKCYATileCapabilities.CAPABILITY_HEAT_CONTAINER, DOWN);
                if (container != null) {
                    if (container.changeHeat(HEAT_BASE_INCREASE, true)) {
                        container.changeHeat(HEAT_BASE_INCREASE, false);
                        this.heatContainer.changeHeat(-HEAT_BASE_INCREASE, false);
                    }
                }
            }
        }
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcya.heater.tooltip.1"));
        tooltip.add(I18n.format("gregtech.universal.tooltip.max_voltage_in", energyContainer.getInputVoltage(), GTValues.VNF[getTier()]));
        tooltip.add(I18n.format("tkcya.machine.energy_conversion_efficiency",  TextFormatting.WHITE + String.format("%.02f", EU_TO_HU * 100) + "%"));
        tooltip.add(I18n.format("gregtech.universal.tooltip.energy_storage_capacity", energyContainer.getEnergyCapacity()));
        tooltip.add(I18n.format("tkcya.heater.tooltip.2", NumberFormattingUtil.formatDoubleToCompactString(Math.abs(HEAT_BASE_INCREASE))));
        tooltip.add(I18n.format("tkcya.machine.redstone.inverse.tooltip"));
        tooltip.add(I18n.format("gregtech.machine.item_controller.tooltip.consumption", ENERGY_BASE_CONSUMPTION));
        tooltip.add(I18n.format("tkcya.heater.tooltip.3"));
    }

    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE) {
            return side == UP ? GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE.cast(this) : null;
        }
        if (capability.equals(TKCYATileCapabilities.CAPABILITY_HEAT_CONTAINER)) {
            return TKCYATileCapabilities.CAPABILITY_HEAT_CONTAINER.cast(heatContainer);
        }
        return super.getCapability(capability, side);
    }

    @Nonnull
    @Override
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = new ObjectArrayList<>();
        list.add(new TextComponentTranslation("behavior.tricorder.current_heat", heatContainer.getHeat()));
        list.add(new TextComponentTranslation("behavior.tricorder.min_heat", heatContainer.getMinHeat()));
        list.add(new TextComponentTranslation("behavior.tricorder.max_heat", heatContainer.getMaxHeat()));
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
