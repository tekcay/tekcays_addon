package tekcays_addon.api.metatileentity;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IActiveOutputSide;
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityUIFactory;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import gregtech.core.sound.GTSoundEvents;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;
import tekcays_addon.api.capability.containers.IHeatContainer;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.capability.impl.HeatContainer;
import tekcays_addon.api.render.TKCYATextures;
import tekcays_addon.api.utils.FuelHeaterTiers;
import tekcays_addon.common.blocks.blocks.BlockBrick;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static gregtech.api.capability.GregtechDataCodes.IS_WORKING;
import static net.minecraft.util.EnumFacing.*;

public abstract class FuelHeater extends MetaTileEntity implements IDataInfoProvider, IActiveOutputSide, IFreeFace{

    protected int heatIncreaseRate;
    protected HeatContainer heatContainer;
    private boolean isBurning;
    protected final FuelHeaterTiers fuelHeater;
    private final float efficiency;
    private final int powerMultiplier;
    private int burnTimeLeft;
    private boolean canIgnite;
    private final int IGNITION_CHANCE_WOOD_STICK = 30;

    public FuelHeater(ResourceLocation metaTileEntityId, FuelHeaterTiers fuelHeater) {
        super(metaTileEntityId);
        this.fuelHeater = fuelHeater;
        this.efficiency = fuelHeater.getEfficiency();
        this.powerMultiplier = fuelHeater.getPowerMultiplier();
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
        TKCYATextures.BRICKS[BlockBrick.BrickType.BRICK.getTextureId()].renderSided(DOWN, renderState, translation, pipeline);
        TKCYATextures.HALF_BRICK.renderSided(getFrontFacing().getOpposite(), renderState, translation, pipeline);
        TKCYATextures.HALF_BRICK.renderSided(getFrontFacing().rotateY(), renderState, translation, pipeline);
        TKCYATextures.HALF_BRICK.renderSided(getFrontFacing().rotateYCCW(), renderState, translation, pipeline);
    }

    @SideOnly(Side.CLIENT)
    protected SimpleOverlayRenderer getBaseRenderer() {
        return TKCYATextures.STEAM_CASING[fuelHeater.getTextureId()];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Pair<TextureAtlasSprite, Integer> getParticleTexture() {
        return Pair.of(getBaseRenderer().getParticleSprite(), getPaintingColorForRendering());
    }

    protected abstract void tryConsumeNewFuel();

    protected void setBurnTimeLeft(int amount) {
        this.burnTimeLeft =  amount;
    }

    private void setIgnition() {
        canIgnite = true;
    }

    @Override
    public void update() {
        super.update();

        if (burnTimeLeft <= 0) {
            tryConsumeNewFuel();
            if (isBurning() && burnTimeLeft <= 0) setBurning(false);
        }

        if (canIgnite && burnTimeLeft > 0) setBurning(true);

        if (isBurning()) {

            if (!this.checkFaceFree(getPos(), getFrontFacing())) {
                setBurning(false);
                canIgnite = false;
                return;
            }

            int currentHeat = heatContainer.getHeat();
            if (!getWorld().isRemote) {
                if (currentHeat + heatIncreaseRate < heatContainer.getMaxHeat())
                    heatContainer.setHeat(currentHeat + heatIncreaseRate);
                transferHeat(heatIncreaseRate);
            }

            --burnTimeLeft;
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
                container.changeHeat(heatIncreaseRate);
                this.heatContainer.changeHeat(-heatIncreaseRate);
            }
        }
    }

    /**
     * Called when player clicks on specific side of this meta tile entity
     *
     * @return true if something happened, so animation will be played
     */
    @Override
    public boolean onRightClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {
        if (!playerIn.isSneaking() && openGUIOnRightClick()) {
            if (getWorld() != null && !getWorld().isRemote) {
                MetaTileEntityUIFactory.INSTANCE.openUI(getHolder(), (EntityPlayerMP) playerIn);
            }
            return true;
        } else {

            if (facing != getFrontFacing()) return false;

            ItemStack itemInHand = playerIn.getHeldItemMainhand();
            if (playerIn.isSneaking() && itemInHand.getItem().equals(Items.STICK)) {
                Random rand = new Random();
                if (rand.nextInt(100) < IGNITION_CHANCE_WOOD_STICK) setIgnition();
                itemInHand.setCount(itemInHand.getCount() - 1);
            }
        }
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcya.heater.tooltip.1"));
        tooltip.add(I18n.format("tkcya.heater.tooltip.2", heatIncreaseRate));
        tooltip.add(I18n.format("tkcya.machine.energy_conversion_efficiency",  TextFormatting.WHITE + String.format("%.02f", efficiency * 100.00F) + "%"));
        tooltip.add(I18n.format("tkcya.machine.free_front_face.tooltip"));
        tooltip.add(I18n.format("tkcya.machine.fuel_heater.ignition.tooltip", TextFormatting.WHITE + String.format("%d%%", IGNITION_CHANCE_WOOD_STICK)));
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
        return this.isBurning;
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
        return list;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("BurnTimeLeft", burnTimeLeft);
        data.setBoolean("isBurning", isBurning());
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.burnTimeLeft = data.getInteger("BurnTimeLeft");
        this.isBurning = data.getBoolean("isBurning");
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(isBurning());
        buf.writeInt(burnTimeLeft);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.isBurning = buf.readBoolean();
        this.burnTimeLeft = buf.readInt();
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
    public SoundEvent getSound() {
        return GTSoundEvents.FURNACE;
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
