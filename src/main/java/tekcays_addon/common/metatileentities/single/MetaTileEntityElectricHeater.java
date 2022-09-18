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
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import tekcays_addon.api.capability.IHeatContainer;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.capability.impl.HeatContainer;

import org.apache.commons.lang3.ArrayUtils;
import tekcays_addon.api.utils.TKCYALog;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static net.minecraft.util.EnumFacing.*;

public class MetaTileEntityElectricHeater extends TieredMetaTileEntity implements IActiveOutputSide {

    private final int HEAT_BASE_INCREASE = (int) (GTValues.V[getTier()] / 4);
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
        this.heatContainer = new HeatContainer(this, 0, 1000);
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
        Textures.PIPE_OUT_OVERLAY.renderSided(UP, renderState, translation, pipeline);
    }


    @Override
    public void update() {
        super.update();
        if (!getWorld().isRemote && getOffsetTimer() % 20 == 0) {
            //Redstone stops heating
            if (this.isBlockRedstonePowered()) return;

            TKCYALog.logger.info("stored energy = " + energyContainer.getEnergyStored());
            TKCYALog.logger.info("EU base consumption = " + ENERGY_BASE_CONSUMPTION);

            if (energyContainer.getEnergyStored() < ENERGY_BASE_CONSUMPTION) return;
            int currentHeat = heatContainer.getHeat();

            TKCYALog.logger.info("currentHeat = " + currentHeat);
            TKCYALog.logger.info("maxHeat = " + heatContainer.getMaxHeat());

            if (currentHeat + HEAT_BASE_INCREASE < heatContainer.getMaxHeat()) heatContainer.setHeat(currentHeat + HEAT_BASE_INCREASE);

            TKCYALog.logger.info("got here");
            energyContainer.removeEnergy(ENERGY_BASE_CONSUMPTION);

            TileEntity te = getWorld().getTileEntity(getPos().offset(UP));
            if (te != null) {
                TKCYALog.logger.info("null ?");
                IHeatContainer container = te.getCapability(TKCYATileCapabilities.CAPABILITY_HEAT_CONTAINER, DOWN);
                TKCYALog.logger.info("prout ?");
                if (container != null) {
                    TKCYALog.logger.info("zut ?");
                    /*
                    if (container.changeHeat(HEAT_BASE_INCREASE, true)) {
                        container.changeHeat(HEAT_BASE_INCREASE, false);
                        this.heatContainer.changeHeat(-HEAT_BASE_INCREASE, false);
                    }

                     */
                }
            }

        }
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcya.electric_heater.tooltip.1"));
        tooltip.add(I18n.format("tkcya.electric_heater.tooltip.2", NumberFormattingUtil.formatDoubleToCompactString(Math.abs(HEAT_BASE_INCREASE))));
        tooltip.add(I18n.format("tkcya.electric_heater.tooltip.3"));
    }

    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {
        /*
        if (capability == GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE) {
            return side == UP ? GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE.cast(this) : null;
        }

         */
        if (capability.equals(TKCYATileCapabilities.CAPABILITY_HEAT_CONTAINER)) {
            return TKCYATileCapabilities.CAPABILITY_HEAT_CONTAINER.cast(heatContainer);
        }
        return super.getCapability(capability, side);
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
