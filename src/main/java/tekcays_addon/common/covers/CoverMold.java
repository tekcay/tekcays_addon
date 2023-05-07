package tekcays_addon.common.covers;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.cover.ICoverable;
import lombok.Getter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import tekcays_addon.api.covers.molds.CoverMoldWrapper;

@Getter
public class CoverMold extends CoverBehavior implements ITickable {
    private final CoverMoldWrapper wrapper;

    public CoverMold(ICoverable coverHolder, EnumFacing attachedSide, CoverMoldWrapper wrapper) {
        super(coverHolder, attachedSide);
        this.wrapper = wrapper;
    }

    @Override
    public boolean canAttach() {
        return true;
        //return coverHolder.getCapability(wrapper.getCapability(), null) != null;
    }

    @Override
    public void renderCover(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline, Cuboid6 plateBox, BlockRenderLayer layer) {
        if (attachedSide == null) return;
        this.wrapper.getTextures().renderSided(attachedSide, plateBox, renderState, pipeline, translation);
    }

    @Override
    public EnumActionResult onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, CuboidRayTraceResult hitResult) {
        return EnumActionResult.PASS;
    }

    @Override
    public void update() {
    }

    /*
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

     */
}
