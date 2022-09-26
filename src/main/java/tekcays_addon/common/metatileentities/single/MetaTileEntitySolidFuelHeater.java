package tekcays_addon.common.metatileentities.single;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregicality.science.api.utils.NumberFormattingUtil;
import gregtech.api.capability.*;
import gregtech.api.capability.impl.ItemFuelInfo;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.ModHandler;
import gregtech.api.sound.GTSounds;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.SimpleSidedCubeRenderer;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import javafx.scene.paint.Material;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;
import tekcays_addon.api.capability.IHeatContainer;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.capability.impl.HeatContainer;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static gregtech.api.capability.GregtechDataCodes.IS_WORKING;
import static net.minecraft.util.EnumFacing.DOWN;
import static net.minecraft.util.EnumFacing.UP;
import static tekcays_addon.api.utils.TKCYAValues.EU_TO_HU;

public class MetaTileEntitySolidFuelHeater extends MetaTileEntity implements IDataInfoProvider, IActiveOutputSide, IFuelable {

    private static final int HEAT_BASE_INCREASE = 8;
    private HeatContainer heatContainer;
    private final Material material;
    private int fuelBurnTimeLeft;
    private int fuelMaxBurnTime;
    protected boolean isBurning;
    private final ICubeRenderer renderer;
    private final int tier;

    public MetaTileEntitySolidFuelHeater(ResourceLocation metaTileEntityId, Material material, ICubeRenderer renderer) {
        super(metaTileEntityId);
        this.material = material;
        this.renderer = renderer;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntitySolidFuelHeater(metaTileEntityId, material);
    }

    @Override
    protected void initializeInventory() {
        this.importItems = this.createImportItemHandler();
        this.exportItems = this.createExportItemHandler();
        this.heatContainer = new HeatContainer(this, 0, 20000);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return null;
    }


    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        IVertexOperation[] colouredPipeline = ArrayUtils.add(pipeline, new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering())));
        getBaseRenderer().render(renderState, translation, colouredPipeline);
        renderer.renderOrientedState(renderState, translation, pipeline, getFrontFacing(), isBurning(), true);
        ColourMultiplier multiplier = new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering()));
        colouredPipeline = ArrayUtils.add(pipeline, multiplier);

        Textures.AIR_VENT_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
        Textures.PIPE_OUT_OVERLAY.renderSided(UP, renderState, translation, pipeline);
    }

    /**
     *
     * @return {@code} false if no fuel has been consummed
     */
    protected boolean tryConsumeNewFuel() {
        ItemStack fuelInSlot = importItems.extractItem(0, 1, true);
        if (fuelInSlot.isEmpty()) return false;
        // Prevent consuming buckets with burn time
        if(FluidUtil.getFluidHandler(fuelInSlot) != null) {
            return false;
        }
        int burnTime = TileEntityFurnace.getItemBurnTime(fuelInSlot);
        if (burnTime <= 0) return false;
        importItems.extractItem(0, 1, false);
        ItemStack remainderAsh = ModHandler.getBurningFuelRemainder(fuelInSlot);
        if (!remainderAsh.isEmpty()) { //we don't care if we can't insert ash - it's chanced anyway
            exportItems.insertItem(0, remainderAsh, false);
        }
        setFuelMaxBurnTime(burnTime);
        return true;
    }

    public void setFuelMaxBurnTime(int fuelMaxBurnTime) {
        this.fuelMaxBurnTime = fuelMaxBurnTime;
        this.fuelBurnTimeLeft = fuelMaxBurnTime;
        if (!getWorld().isRemote) {
            markDirty();
        }
    }

    //For TOP, needs to implement IFuelable
    @Override
    public Collection<IFuelInfo> getFuels() {
        ItemStack fuelInSlot = importItems.extractItem(0, Integer.MAX_VALUE, true);
        if (fuelInSlot == ItemStack.EMPTY)
            return Collections.emptySet();
        final int fuelRemaining = fuelInSlot.getCount();
        final int fuelCapacity = importItems.getSlotLimit(0);
        final long burnTime = (long) fuelRemaining * TileEntityFurnace.getItemBurnTime(fuelInSlot) * tier;
        return Collections.singleton(new ItemFuelInfo(fuelInSlot, fuelRemaining, fuelCapacity, 1, burnTime));
    }


    @Override
    public void update() {
        super.update();
        if (tryConsumeNewFuel() || fuelBurnTimeLeft > 0) setBurning(true);
        int currentHeat = heatContainer.getHeat();
        if (!getWorld().isRemote) {
            if (heatContainer.getHeat() < HEAT_BASE_INCREASE) return;
            if (currentHeat + HEAT_BASE_INCREASE < heatContainer.getMaxHeat()) heatContainer.setHeat(currentHeat + HEAT_BASE_INCREASE);


            TileEntity te = getWorld().getTileEntity(getPos().offset(UP));
            if (te != null) {
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
        tooltip.add(I18n.format("tkcya.electric_heater.tooltip.1"));
        tooltip.add(I18n.format("tkcya.machine.energy_conversion_efficiency",  TextFormatting.WHITE + String.format("%.02f", EU_TO_HU * 100) + "%"));
        tooltip.add(I18n.format("tkcya.electric_heater.tooltip.2", NumberFormattingUtil.formatDoubleToCompactString(Math.abs(HEAT_BASE_INCREASE))));
        tooltip.add(I18n.format("tkcya.machine.redstone.inverse.tooltip"));
        tooltip.add(I18n.format("tkcya.electric_heater.tooltip.3"));
    }

    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE) {
            return side == UP ? GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE.cast(this) : null;
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
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("FuelBurnTimeLeft", fuelBurnTimeLeft);
        data.setInteger("FuelMaxBurnTime", fuelMaxBurnTime);
        data.setTag("ContainerInventory", containerInventory.serializeNBT());
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.fuelBurnTimeLeft = data.getInteger("FuelBurnTimeLeft");
        this.fuelMaxBurnTime = data.getInteger("FuelMaxBurnTime");
        this.containerInventory.deserializeNBT(data.getCompoundTag("ContainerInventory"));
        this.isBurning = fuelBurnTimeLeft > 0;
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(isBurning);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.isBurning = buf.readBoolean();
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == IS_WORKING) {
            this.isBurning = buf.readBoolean();
            scheduleRenderUpdate();
        }
    }

    @Override
    public boolean isActive() {
        return isBurning;
    }

    @SideOnly(Side.CLIENT)
    protected SimpleSidedCubeRenderer getBaseRenderer() {
        if (isHighPressure) {
            return Textures.STEAM_BRICKED_CASING_STEEL;
        } else {
            return Textures.STEAM_BRICKED_CASING_BRONZE;
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Pair<TextureAtlasSprite, Integer> getParticleTexture() {
        return Pair.of(getBaseRenderer().getParticleSprite(), getPaintingColorForRendering());
    }



    @Override
    public SoundEvent getSound() {
        return GTSounds.BOILER;
    }

    @Override
    public int getDefaultPaintingColor() {
        return 0xFFFFFF;
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
