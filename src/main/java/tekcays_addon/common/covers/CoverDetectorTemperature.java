package tekcays_addon.common.covers;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.cover.ICoverable;
import gregtech.client.renderer.texture.Textures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.*;
import net.minecraft.util.text.TextComponentTranslation;
import tekcays_addon.api.capability.IHeatContainer;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.render.TKCYATextures;

public class CoverDetectorTemperature extends CoverBehavior implements ITickable {

    private boolean isInverted;

    public CoverDetectorTemperature(ICoverable coverHolder, EnumFacing attachedSide) {
        super(coverHolder, attachedSide);
        this.isInverted = false;
    }

    @Override
    public boolean canAttach() {
        return coverHolder.getCapability(TKCYATileCapabilities.CAPABILITY_HEAT_CONTAINER, null) != null;
    }

    @Override
    public void renderCover(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline, Cuboid6 plateBox, BlockRenderLayer layer) {
        TKCYATextures.DETECTOR_TEMPERATURE.renderSided(attachedSide, plateBox, renderState, pipeline, translation);
    }

    @Override
    public EnumActionResult onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, CuboidRayTraceResult hitResult) {
        if (this.coverHolder.getWorld().isRemote) {
            return EnumActionResult.SUCCESS;
        }

        if (this.isInverted) {
            this.setInverted();
            playerIn.sendMessage(new TextComponentTranslation("tkcya.cover.temperature_detector.message_temperature_storage_normal"));
        } else {
            this.setInverted();
            playerIn.sendMessage(new TextComponentTranslation("tkcya.cover.temperature_detector.message_temperature_storage_inverted"));
        }
        return EnumActionResult.SUCCESS;
    }

    private void setInverted() {
        this.isInverted = !this.isInverted;
        if (!this.coverHolder.getWorld().isRemote) {
            this.coverHolder.writeCoverData(this, 100, b -> b.writeBoolean(this.isInverted));
            this.coverHolder.notifyBlockUpdate();
            this.coverHolder.markDirty();
        }
    }

    @Override
    public void update() {
        if (this.coverHolder.getOffsetTimer() % 20 != 0)
            return;

        IHeatContainer heatContainer = coverHolder.getCapability(TKCYATileCapabilities.CAPABILITY_HEAT_CONTAINER, null);
        if (heatContainer != null) {
            long currentTemperature = heatContainer.getTemperature();
            long maxTemperature = heatContainer.getMaxTemperature();

            if (maxTemperature == 0)
                return;

            int outputAmount = (int) (15.0 * currentTemperature / maxTemperature);

            if (this.isInverted)
                outputAmount = 15 - outputAmount;

            setRedstoneSignalOutput(outputAmount);
        }
    }

    @Override
    public boolean canConnectRedstone() {
        return true;
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setBoolean("isInverted", this.isInverted);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.isInverted = tagCompound.getBoolean("isInverted");
    }

    @Override
    public void writeInitialSyncData(PacketBuffer packetBuffer) {
        packetBuffer.writeBoolean(this.isInverted);
    }

    @Override
    public void readInitialSyncData(PacketBuffer packetBuffer) {
        this.isInverted = packetBuffer.readBoolean();
    }
}
