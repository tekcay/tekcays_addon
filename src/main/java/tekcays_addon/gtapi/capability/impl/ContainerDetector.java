package tekcays_addon.gtapi.capability.impl;

import static tekcays_addon.api.consts.NBTKeys.*;
import static tekcays_addon.gtapi.capability.TKCYATileCapabilities.CAPABILITY_CONTAINER_DETECTOR;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import lombok.Getter;
import lombok.Setter;
import tekcays_addon.api.consts.DetectorModes;
import tekcays_addon.gtapi.capability.containers.IContainerDetector;

@Getter
@Setter
public class ContainerDetector extends MTETrait implements IContainerDetector {

    private int threshold;
    private int currentValue;
    private DetectorModes detectorMode;

    public ContainerDetector(MetaTileEntity metaTileEntity) {
        super(metaTileEntity);
        this.threshold = 0;
        this.currentValue = 0;
        this.detectorMode = DetectorModes.EQUAL;
    }

    @NotNull
    @Override
    public String getName() {
        return "ContainerDetector";
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability) {
        if (capability == CAPABILITY_CONTAINER_DETECTOR) {
            return CAPABILITY_CONTAINER_DETECTOR.cast(this);
        }
        return null;
    }

    @NotNull
    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = super.serializeNBT();
        compound.setInteger(THRESHOLD_KEY, this.threshold);
        compound.setInteger(CURRENT_VALUE_KEY, this.currentValue);
        compound.setString(DETECTOR_MODE_KEY, this.detectorMode.name());
        return compound;
    }

    @Override
    public void deserializeNBT(@NotNull NBTTagCompound compound) {
        this.currentValue = compound.getInteger(CURRENT_VALUE_KEY);
        this.threshold = compound.getInteger(THRESHOLD_KEY);
        this.detectorMode = DetectorModes.valueOf(compound.getString(DETECTOR_MODE_KEY));
    }

    @Override
    public void writeInitialData(@NotNull PacketBuffer buffer) {
        super.writeInitialData(buffer);
        buffer.writeInt(this.currentValue);
        buffer.writeInt(this.threshold);
        buffer.writeString(this.detectorMode.name());
    }

    @Override
    public void receiveInitialData(@NotNull PacketBuffer buffer) {
        super.receiveInitialData(buffer);
        this.currentValue = buffer.readInt();
        this.threshold = buffer.readInt();
        this.detectorMode = DetectorModes.valueOf(buffer.readString(6));
    }
}
