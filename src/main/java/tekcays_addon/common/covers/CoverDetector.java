package tekcays_addon.common.covers;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.cover.CoverBase;
import gregtech.api.cover.CoverDefinition;
import gregtech.api.cover.CoverWithUI;
import gregtech.api.cover.CoverableView;
import gregtech.api.gui.ModularUI;
import lombok.Getter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import tekcays_addon.api.consts.DetectorModes;
import tekcays_addon.api.detectors.CoverDetectorWrapper;
import tekcays_addon.api.detectors.DetectorControllerHelper;
import tekcays_addon.api.gui.CoverGuiHandler;

import javax.annotation.Nonnull;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static tekcays_addon.api.consts.DetectorModes.changeDetectModeAndSendMessage;
import static tekcays_addon.api.consts.NBTKeys.DETECTOR_MODE_KEY;
import static tekcays_addon.api.consts.NBTKeys.THRESHOLD_KEY;


public class CoverDetector extends CoverBase implements ITickable, CoverWithUI, CoverGuiHandler, DetectorControllerHelper {

    private int threshold;
    private CoverDetectorWrapper wrapper;
    private final int MAX_VALUE = 10_000;
    private DetectorModes detectorMode;
    @Getter
    private int redstoneSignalOutput = 0;

    public CoverDetector(@NotNull CoverDefinition definition, @NotNull CoverableView coverableView, @NotNull EnumFacing attachedSide) {
        super(definition, coverableView, attachedSide);
    }

    public CoverDetector(@NotNull CoverDefinition definition, @NotNull CoverableView coverableView, @NotNull EnumFacing attachedSide, CoverDetectorWrapper wrapper) {
        super(definition, coverableView, attachedSide);
        this.wrapper = wrapper;
    }

    /*
    public CoverDetector(ICoverable coverHolder, EnumFacing attachedSide, CoverDetectorWrapper wrapper) {
        super(coverHolder, attachedSide);
        this.wrapper = wrapper;
        this.detectorMode = DetectorModes.HIGHER;
    }
     */

        @Override
    public boolean canAttach(@NotNull CoverableView coverable, @NotNull EnumFacing side) {
            return coverable.getCapability(wrapper.getCapability(), side) != null;
    }

    @Override
    public void renderCover(@Nonnull CCRenderState renderState, @Nonnull Matrix4 translation, IVertexOperation[] pipeline, @Nonnull Cuboid6 plateBox, @Nonnull BlockRenderLayer layer) {
        this.wrapper.getTextures().renderSided(getAttachedSide(), plateBox, renderState, pipeline, translation);
    }

    @Override
    public EnumActionResult onScrewdriverClick(@Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull CuboidRayTraceResult hitResult) {

        if (!getCoverableView().getWorld().isRemote) {
            if (playerIn.isSneaking()) {
                setDetectorMode(detectorMode, playerIn);
                return EnumActionResult.SUCCESS;
            } else {
                openUI((EntityPlayerMP) playerIn);
            }
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ModularUI createUI(EntityPlayer player) {
        return createUIHelper(player);
    }

    private int getContainerValue() {
        return this.wrapper.getValueRetrievingFunction().apply(getCoverableView(), getAttachedSide());
    }

    public final void setRedstoneSignalOutput(int redstoneSignalOutput) {
        this.redstoneSignalOutput = redstoneSignalOutput;
        getCoverableView().notifyBlockUpdate();
        getCoverableView().markDirty();
    }

    @Override
    public void update() {
        int currentValue = getContainerValue();
        updateRedstoneBehavior(detectorMode, currentValue, threshold, this::setRedstoneSignalOutput);
    }

    @Override
    public String getUITitle() {
        return wrapper.getUiTitle();
    }

    @Override
    public int getThreshold() {
        return this.threshold;
    }

    @Override
    public void setThreshold(int threshold) {
        this.threshold = threshold;
        getCoverableView().markDirty();
    }

    private void setDetectorMode(DetectorModes detectorMode, EntityPlayer player) {
        this.detectorMode = changeDetectModeAndSendMessage(detectorMode, player);
        getCoverableView().markDirty();
    }

    @Override
    public void adjustThreshold(int amount) {
        setThreshold(MathHelper.clamp(getThreshold() + amount, 1, MAX_VALUE));
    }

    @Override
    public String getCurrentMeasureText() {
        return wrapper.getCurrentMeasureText();
    }

    @Override
    public int getMaxMeasure() {
        return MAX_VALUE;
    }

    @Override
    public int getContainerMeasure() {
        return this.getContainerValue();
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
        return wrapper.getUnit();
    }

    @Override
    public DetectorModes getCurrentDetectorMode() {
        return this.detectorMode;
    }

    @Override
    public boolean canConnectRedstone() {
        return true;
    }

    @Override
    public void writeToNBT(@Nonnull NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setString(DETECTOR_MODE_KEY, this.detectorMode.name());
        tagCompound.setInteger(THRESHOLD_KEY, this.threshold);
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.detectorMode = DetectorModes.valueOf(tagCompound.getString(DETECTOR_MODE_KEY));
        this.threshold = tagCompound.getInteger(THRESHOLD_KEY);
    }

    @Override
    public void writeInitialSyncData(PacketBuffer packetBuffer) {
        packetBuffer.writeString(this.detectorMode.name());
        packetBuffer.writeInt(this.threshold);
    }

    @Override
    public void readInitialSyncData(PacketBuffer packetBuffer) {
        this.detectorMode = DetectorModes.valueOf(packetBuffer.readString(6));
        this.threshold = packetBuffer.readInt();
    }

    @Override
    public EnumFacing getFrontFacing() {
        return null;
    }
}
