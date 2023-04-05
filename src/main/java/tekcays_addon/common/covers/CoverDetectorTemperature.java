package tekcays_addon.common.covers;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.cover.CoverWithUI;
import gregtech.api.cover.ICoverable;
import gregtech.api.gui.ModularUI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.consts.DetectorModes;
import tekcays_addon.api.metatileentity.gui.MetaTileEntityGuiHandler;
import tekcays_addon.api.render.TKCYATextures;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static tekcays_addon.api.consts.UnitSymbol.KELVIN;


public class CoverDetectorTemperature extends CoverBehavior implements ITickable, CoverWithUI, MetaTileEntityGuiHandler {

    private boolean isInverted;
    private int temperatureThreshold;
    private final int MAX_TEMPERATURE = 10_000;
    private DetectorModes detectorModes;

    public CoverDetectorTemperature(ICoverable coverHolder, EnumFacing attachedSide) {
        super(coverHolder, attachedSide);
        this.isInverted = false;
        this.detectorModes = DetectorModes.HIGHER;
    }

    @Override
    public boolean canAttach() {
        return coverHolder.getCapability(TKCYATileCapabilities.CAPABILITY_HEAT_CONTAINER, null) != null;
    }

    @Override
    public void renderCover(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline, Cuboid6 plateBox, BlockRenderLayer layer) {
        TKCYATextures.DETECTOR_TEMPERATURE.renderSided(attachedSide, plateBox, renderState, pipeline, translation);
    }


    public void onScrewdriverSneakClick(EntityPlayer playerIn, EnumHand hand, CuboidRayTraceResult hitResult) {

        if (this.isInverted) {
            this.setInverted();
            this.detectorModes = DetectorModes.LOWER;
            playerIn.sendMessage(new TextComponentTranslation("tkcya.cover.temperature_detector.message_temperature_storage_normal"));
        } else {
            this.setInverted();
            this.detectorModes = DetectorModes.HIGHER;
            playerIn.sendMessage(new TextComponentTranslation("tkcya.cover.temperature_detector.message_temperature_storage_inverted"));
        }
    }

    @Override
    public EnumActionResult onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, CuboidRayTraceResult hitResult) {

        if (!coverHolder.getWorld().isRemote) {
            if (playerIn.isSneaking()) {
                onScrewdriverSneakClick(playerIn, hand, hitResult);
                return EnumActionResult.SUCCESS;
            } else openUI((EntityPlayerMP) playerIn);
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ModularUI createUI(EntityPlayer player) {
        return createUIHelper(player);
    }

    private void setInverted() {
        this.isInverted = !this.isInverted;
        if (!this.coverHolder.getWorld().isRemote) {
            this.coverHolder.writeCoverData(this, 101, b -> b.writeBoolean(this.isInverted));
            this.coverHolder.notifyBlockUpdate();
            this.coverHolder.markDirty();
        }
    }

    private int getHeatContainerTemperature() {
        return coverHolder.getCapability(TKCYATileCapabilities.CAPABILITY_HEAT_CONTAINER, EnumFacing.DOWN).getTemperature();
    }

    @Override
    public void update() {

        int currentTemp = getHeatContainerTemperature();
        if (currentTemp == 0) return;

        if (!isInverted) {
            if (currentTemp > temperatureThreshold) setRedstoneSignalOutput(15);
            else setRedstoneSignalOutput(0);
        } else {
            if (currentTemp > temperatureThreshold) setRedstoneSignalOutput(0);
            else setRedstoneSignalOutput(15);
        }
    }

    @Override
    public String getUITitle() {
        return "Temperature Detector";
    }

    @Override
    public int getThreshold() {
        return this.temperatureThreshold;
    }

    @Override
    public void setThreshold(int threshold) {
        this.temperatureThreshold = threshold;
        coverHolder.markDirty();
    }

    @Override
    public void adjustThreshold(int amount) {
        setThreshold(MathHelper.clamp(getThreshold() + amount, 1, MAX_TEMPERATURE));
    }

    @Override
    public String getCurrentMeasureText() {
        return "Current temperature: %d %s";
    }

    @Override
    public int getMaxMeasure() {
        return MAX_TEMPERATURE;
    }

    @Override
    public int getContainerMeasure() {
        return this.getHeatContainerTemperature();
    }

    @Override
    public Supplier<String> convertThresholdToString() {
        return () -> Integer.toString(getThreshold());
    }

    @Override
    public Consumer<String> setThresholdFromString() {
        return val -> setThreshold(Integer.parseInt(val));
    }

    @Override
    public ModularUI buildUI(ModularUI.Builder builder, EntityPlayer player) {
        return builder.build(this, player);
    }

    @Override
    public String getUnitSymbol() {
        return KELVIN;
    }

    @Override
    public DetectorModes getCurrentDetectorMode() {
        return this.detectorModes;
    }

    @Override
    public boolean canConnectRedstone() {
        return true;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setBoolean("isInverted", this.isInverted);
        tagCompound.setInteger("temperatureThreshold", this.temperatureThreshold);
        return tagCompound;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.isInverted = tagCompound.getBoolean("isInverted");
        this.temperatureThreshold = tagCompound.getInteger("temperatureThreshold");
    }

    @Override
    public void writeInitialSyncData(PacketBuffer packetBuffer) {
        packetBuffer.writeBoolean(this.isInverted);
        packetBuffer.writeInt(this.temperatureThreshold);
    }

    @Override
    public void readInitialSyncData(PacketBuffer packetBuffer) {
        this.isInverted = packetBuffer.readBoolean();
        this.temperatureThreshold = packetBuffer.readInt();
    }
}
