package tekcays_addon.api.metatileentity;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregicality.science.api.utils.NumberFormattingUtil;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IActiveOutputSide;
import gregtech.api.capability.IFuelInfo;
import gregtech.api.capability.IFuelable;
import gregtech.api.capability.impl.ItemFuelInfo;
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.sound.GTSounds;
import gregtech.api.unification.material.Material;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;
import tekcays_addon.api.capability.IHeatContainer;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.capability.impl.HeatContainer;
import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.api.utils.FuelWithProperties;
import tekcays_addon.api.utils.TKCYALog;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static gregtech.api.capability.GregtechDataCodes.IS_WORKING;
import static net.minecraft.util.EnumFacing.*;
import static tekcays_addon.api.utils.HeatersMethods.getBurnTime;
import static tekcays_addon.api.utils.HeatersMethods.getTextures;

public abstract class FuelHeater extends MetaTileEntity implements IDataInfoProvider, IActiveOutputSide{

    protected int heatIncreaseRate;
    protected HeatContainer heatContainer;
    protected boolean isBurning;
    protected final tekcays_addon.api.utils.FuelHeater fuelHeater;
    private final Material material;
    private final float efficiency;
    private final int powerMultiplier;
    protected int burnTimeLeft;

    public FuelHeater(ResourceLocation metaTileEntityId, tekcays_addon.api.utils.FuelHeater fuelHeater) {
        super(metaTileEntityId);
        this.fuelHeater = fuelHeater;
        this.efficiency = fuelHeater.getEfficiency();
        this.powerMultiplier = fuelHeater.getPowerMultiplier();
        this.material = fuelHeater.getMaterial();
        this.isBurning = false;
    }

    public int setHeatIncreaseRate(int heatBaseIncrease) {
        return heatIncreaseRate = heatBaseIncrease * powerMultiplier;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        IVertexOperation[] colouredPipeline = ArrayUtils.add(pipeline, new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering())));
        getBaseRenderer().render(renderState, translation, colouredPipeline);
        ColourMultiplier multiplier = new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering()));
        colouredPipeline = ArrayUtils.add(pipeline, multiplier);
        TKCYATextures.HEAT_ACCEPTOR_VERTICALS_OVERLAY.renderSided(UP, renderState, translation, pipeline);
        TKCYATextures.BRICK.renderSided(DOWN, renderState, translation, pipeline);
        TKCYATextures.HALF_BRICK.renderSided(getFrontFacing().getOpposite(), renderState, translation, pipeline);
        TKCYATextures.HALF_BRICK.renderSided(getFrontFacing().rotateY(), renderState, translation, pipeline);
        TKCYATextures.HALF_BRICK.renderSided(getFrontFacing().rotateYCCW(), renderState, translation, pipeline);
    }

    @SideOnly(Side.CLIENT)
    protected SimpleOverlayRenderer getBaseRenderer() {
        return getTextures(material);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Pair<TextureAtlasSprite, Integer> getParticleTexture() {
        return Pair.of(getBaseRenderer().getParticleSprite(), getPaintingColorForRendering());
    }

    protected void tryConsumeNewFuel() {
    }

    protected void setBurnTimeLeft(int amount) {
        burnTimeLeft =  amount;
    }

    @Override
    public void update() {
        super.update();
        if (burnTimeLeft <= 0) {
            setBurning(false);
            tryConsumeNewFuel();
        }
        if (burnTimeLeft > 0) {
            setBurning(true);
            int currentHeat = heatContainer.getHeat();
            if (!getWorld().isRemote) {
                if (currentHeat + heatIncreaseRate < heatContainer.getMaxHeat())
                    heatContainer.setHeat(currentHeat + heatIncreaseRate);
                transferHeat(heatIncreaseRate);
            }
            burnTimeLeft -= 1;
            markDirty();
        }
    }

    /*
    //For TOP, needs to implement IFuelable
    @Override
    public Collection<IFuelInfo> getFuels() {
        TKCYALog.logger.info("TOP got here");
        ItemStack fuelInSlot = importItems.extractItem(0, Integer.MAX_VALUE, true);
        TKCYALog.logger.info("TOP : stackSize : " + fuelInSlot.getCount());
        if (fuelInSlot == ItemStack.EMPTY)
            return Collections.emptySet();
        final int fuelRemaining = fuelInSlot.getCount();
        TKCYALog.logger.info("TOP : fuelInSlot = " + fuelInSlot);
        final int fuelCapacity = importItems.getSlotLimit(0);
        TKCYALog.logger.info("TOP : fuelRemaining = " + fuelRemaining);
        final long burnTime = (long) fuelRemaining * getBurnTime(fuelInSlot, fuelHeater);
        TKCYALog.logger.info("TOP : fuelCapacity = " + fuelCapacity);
        return Collections.singleton(new ItemFuelInfo(fuelInSlot, fuelRemaining, fuelCapacity, 1, burnTime));
    }

     */

    public void transferHeat(int heatIncreaseRate) {
        //Get the TileEntity that is placed right on top of the Heat.
        TileEntity te = getWorld().getTileEntity(getPos().offset(UP));
        if (te != null) {
            //Get the Capability of this Tile Entity on the DOWN FACE.
            IHeatContainer container = te.getCapability(TKCYATileCapabilities.CAPABILITY_HEAT_CONTAINER, DOWN);
            if (container != null) {
                if (container.changeHeat(heatIncreaseRate, true)) {
                    container.changeHeat(heatIncreaseRate, false);
                    this.heatContainer.changeHeat(-heatIncreaseRate, false);
                }
            }
        }
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcya.heater.tooltip.1"));
        tooltip.add(I18n.format("tkcya.heater.tooltip.2", NumberFormattingUtil.formatDoubleToCompactString(Math.abs(heatIncreaseRate))));
        tooltip.add(I18n.format("tkcya.machine.energy_conversion_efficiency",  TextFormatting.WHITE + String.format("%.02f", efficiency * 100.00F) + "%"));
        tooltip.add(I18n.format("tkcya.machine.redstone.inverse.tooltip"));
        tooltip.add(I18n.format("tkcya.heater.tooltip.3"));
    }

    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE) {
            return side == DOWN ? GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE.cast(this) : null;
        }
        if (capability.equals(TKCYATileCapabilities.CAPABILITY_HEAT_CONTAINER)) {
            return side == UP ? TKCYATileCapabilities.CAPABILITY_HEAT_CONTAINER.cast(heatContainer) : null;
        }
        return super.getCapability(capability, side);
    }

    public boolean isBurning() {
        return isBurning;
    }

    public void setBurning(boolean burning) {
        this.isBurning = burning;
        if (!getWorld().isRemote) {
            markDirty();
            writeCustomData(IS_WORKING, buf -> buf.writeBoolean(burning));
        }
    }


    @Override
    public boolean isActive() {
        return isBurning;
    }

    @Nonnull
    @Override
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = new ObjectArrayList<>();
        list.add(new TextComponentTranslation("behavior.tricorder.current_heat", heatContainer.getHeat()));
        list.add(new TextComponentTranslation("behavior.tricorder.min_heat", heatContainer.getMinHeat()));
        list.add(new TextComponentTranslation("behavior.tricorder.max_heat", heatContainer.getMaxHeat()));
        list.add(new TextComponentTranslation("behavior.tricorder.burn_time_left", burnTimeLeft));
        return list;
    }


    @Override
    public SoundEvent getSound() {
        return GTSounds.FURNACE;
    }

    @Override
    public int getDefaultPaintingColor() {
        return 0xFFFFFF;
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
