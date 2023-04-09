package tekcays_addon.api.capability.impl;

import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;
import tekcays_addon.api.capability.IPressureControl;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.consts.DetectorModes;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static tekcays_addon.api.consts.NBTKeys.*;

public class PressureControl extends MTETrait implements IPressureControl {

    private long thresholdPressure;
    private long pressure;
    private DetectorModes detectorModes;

    public PressureControl(MetaTileEntity metaTileEntity) {
        super(metaTileEntity);
        this.thresholdPressure = 0;
        this.pressure = 0;
        this.detectorModes = DetectorModes.EQUAL;
    }

    @Override
    public long getThresholdPressure() {
        return thresholdPressure;
    }

    @Override
    public long getPressure() {
        return pressure;
    }

    @Override
    public DetectorModes getDetectorMode() {
        return detectorModes;
    }

    @Override
    public void setThresholdPressure(long thresholdPressure) {
        this.thresholdPressure = thresholdPressure;
    }

    @Override
    public void setPressure(long pressure) {
        this.pressure = pressure;
    }

    @Override
    public void setDetectorMode(DetectorModes detectorModes) {
        this.detectorModes = detectorModes;
    }

    @Override
    public String getName() {
        return "PressureControl";
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability) {
        if (capability == TKCYATileCapabilities.CAPABILITY_PRESSURE_CONTROL ) {
            return TKCYATileCapabilities.CAPABILITY_PRESSURE_CONTROL.cast(this);
        }
        return null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = super.serializeNBT();
        compound.setLong(THRESHOLD_PRESSURE_KEY, this.thresholdPressure);
        compound.setLong(PRESSURE_KEY, this.pressure);
        compound.setString(DETECTOR_MODE_KEY, this.detectorModes.name());
        return compound;
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagCompound compound) {
        this.pressure = compound.getLong(PRESSURE_KEY);
        this.thresholdPressure = compound.getLong(THRESHOLD_PRESSURE_KEY);
        this.detectorModes = DetectorModes.valueOf(compound.getString(DETECTOR_MODE_KEY));
    }

    @Override
    public void writeInitialData(PacketBuffer buffer) {
        super.writeInitialData(buffer);
        buffer.writeLong(this.pressure);
        buffer.writeLong(this.thresholdPressure);
        buffer.writeString(this.detectorModes.name());
    }

    @Override
    public void receiveInitialData(PacketBuffer buffer) {
        super.receiveInitialData(buffer);
        this.pressure = buffer.readLong();
        this.thresholdPressure = buffer.readLong();
        this.detectorModes = DetectorModes.valueOf(buffer.readString(6));
    }
}
