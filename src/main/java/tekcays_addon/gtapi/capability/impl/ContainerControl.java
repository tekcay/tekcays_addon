package tekcays_addon.gtapi.capability.impl;

import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;
import tekcays_addon.gtapi.capability.containers.IContainerControl;
import tekcays_addon.api.consts.DetectorModes;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static tekcays_addon.gtapi.capability.TKCYATileCapabilities.CAPABILITY_CONTAINER_CONTROL;
import static tekcays_addon.api.consts.NBTKeys.*;

@Getter
@Setter
public class ContainerControl extends MTETrait implements IContainerControl {

    private int threshold;
    private int currentValue;
    private DetectorModes detectorMode;

    public ContainerControl(MetaTileEntity metaTileEntity) {
        super(metaTileEntity);
        this.threshold = 0;
        this.currentValue = 0;
        this.detectorMode = DetectorModes.EQUAL;
    }

    @Nonnull
    @Override
    public String getName() {
        return "ContainerControl";
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability) {
        if (capability == CAPABILITY_CONTAINER_CONTROL) {
            return CAPABILITY_CONTAINER_CONTROL.cast(this);
        }
        return null;
    }

    @Nonnull
    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = super.serializeNBT();
        compound.setInteger(THRESHOLD_PRESSURE_KEY, this.threshold);
        compound.setInteger(PRESSURE_KEY, this.currentValue);
        compound.setString(DETECTOR_MODE_KEY, this.detectorMode.name());
        return compound;
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagCompound compound) {
        this.currentValue = compound.getInteger(PRESSURE_KEY);
        this.threshold = compound.getInteger(THRESHOLD_PRESSURE_KEY);
        this.detectorMode = DetectorModes.valueOf(compound.getString(DETECTOR_MODE_KEY));
    }

    @Override
    public void writeInitialData(@Nonnull PacketBuffer buffer) {
        super.writeInitialData(buffer);
        buffer.writeInt(this.currentValue);
        buffer.writeInt(this.threshold);
        buffer.writeString(this.detectorMode.name());
    }

    @Override
    public void receiveInitialData(@Nonnull PacketBuffer buffer) {
        super.receiveInitialData(buffer);
        this.currentValue = buffer.readInt();
        this.threshold = buffer.readInt();
        this.detectorMode = DetectorModes.valueOf(buffer.readString(6));
    }
}
