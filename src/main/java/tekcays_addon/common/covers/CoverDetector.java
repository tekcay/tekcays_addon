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
import tekcays_addon.api.ContainerStructuring;
import tekcays_addon.api.consts.DetectorModes;
import tekcays_addon.api.detectors.CoverDetectorWrapper;
import tekcays_addon.api.detectors.DetectorControllerHelper;
import tekcays_addon.api.gui.CoverGuiHandler;
import tekcays_addon.gtapi.utils.TKCYALog;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static tekcays_addon.api.consts.DetectorModes.*;
import static tekcays_addon.api.consts.NBTKeys.*;


public class CoverDetector extends CoverBehavior implements ITickable, CoverWithUI, CoverGuiHandler, DetectorControllerHelper {

    private int threshold;
    private final CoverDetectorWrapper wrapper;
    private final int MAX_VALUE = 10_000;
    private DetectorModes detectorMode;

    public CoverDetector(ICoverable coverHolder, EnumFacing attachedSide, CoverDetectorWrapper wrapper) {
        super(coverHolder, attachedSide);
        this.wrapper = wrapper;
        this.detectorMode = DetectorModes.HIGHER;
    }

    @Override
    public boolean canAttach() {
        return coverHolder.getCapability(wrapper.getCapability(), null) != null;
    }

    @Override
    public void renderCover(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline, Cuboid6 plateBox, BlockRenderLayer layer) {
        if (attachedSide == null) return;
        this.wrapper.getTextures().renderSided(attachedSide, plateBox, renderState, pipeline, translation);
    }

    @Override
    public EnumActionResult onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, CuboidRayTraceResult hitResult) {
        if (!coverHolder.getWorld().isRemote) {
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
        return this.wrapper.getValueRetrievingFunction().apply(coverHolder, attachedSide);
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
        coverHolder.markDirty();
    }

    private void setDetectorMode(DetectorModes detectorMode, EntityPlayer player) {
        this.detectorMode = changeDetectModeAndSendMessage(detectorMode, player);
        coverHolder.markDirty();
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
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setString(DETECTOR_MODE_KEY, this.detectorMode.name());
        tagCompound.setInteger(THRESHOLD_KEY, this.threshold);
        return tagCompound;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
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
