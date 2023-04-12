package tekcays_addon.api.capability.impl;

import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;
import tekcays_addon.api.capability.containers.IPressureControl;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.consts.DetectorModes;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static tekcays_addon.api.consts.NBTKeys.*;

@Getter
@Setter
public class PressureControl extends MTETrait implements IPressureControl {

    private int thresholdPressure;
    private int pressure;
    private DetectorModes detectorMode;

    public PressureControl(MetaTileEntity metaTileEntity) {
        super(metaTileEntity);
        this.thresholdPressure = 0;
        this.pressure = 0;
        this.detectorMode = DetectorModes.EQUAL;
    }

    @Nonnull
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

    @Nonnull
    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = super.serializeNBT();
        compound.setInteger(THRESHOLD_PRESSURE_KEY, this.thresholdPressure);
        compound.setInteger(PRESSURE_KEY, this.pressure);
        compound.setString(DETECTOR_MODE_KEY, this.detectorMode.name());
        return compound;
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagCompound compound) {
        this.pressure = compound.getInteger(PRESSURE_KEY);
        this.thresholdPressure = compound.getInteger(THRESHOLD_PRESSURE_KEY);
        this.detectorMode = DetectorModes.valueOf(compound.getString(DETECTOR_MODE_KEY));
    }

    @Override
    public void writeInitialData(@Nonnull PacketBuffer buffer) {
        super.writeInitialData(buffer);
        buffer.writeInt(this.pressure);
        buffer.writeInt(this.thresholdPressure);
        buffer.writeString(this.detectorMode.name());
    }

    @Override
    public void receiveInitialData(@Nonnull PacketBuffer buffer) {
        super.receiveInitialData(buffer);
        this.pressure = buffer.readInt();
        this.thresholdPressure = buffer.readInt();
        this.detectorMode = DetectorModes.valueOf(buffer.readString(6));
    }
}
