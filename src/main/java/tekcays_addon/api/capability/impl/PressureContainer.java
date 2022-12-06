package tekcays_addon.api.capability.impl;

import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;
import tekcays_addon.api.capability.IPressureContainer;
import tekcays_addon.api.capability.TKCYATileCapabilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PressureContainer extends MTETrait implements IPressureContainer {

    private int minPressure;
    private int maxPressure;
    private int pressure;
    private int volume;
    private int pu;
    boolean canHandleVacuum;

    /**
     * Default Pressure container
     * {@link IPressureContainer}
     */
    public PressureContainer(MetaTileEntity metaTileEntity, boolean canHandleVacuum) {
        super(metaTileEntity);
        this.canHandleVacuum = canHandleVacuum;
        this.pu = 0;
    }

    /**
     * Default Pressure container
     * {@link IPressureContainer}
     */
    public PressureContainer(MetaTileEntity metaTileEntity, boolean canHandleVacuum, int minPressure, int maxPressure) {
        super(metaTileEntity);
        this.canHandleVacuum = canHandleVacuum;
        this.minPressure = minPressure;
        this.maxPressure = maxPressure;
        this.pressure = 0;
        this.volume = 0;
        this.pu = 0;
    }


    @Override
    public int getMaxPressure() {
        return this.maxPressure;
    }

    @Override
    public int getMinPressure() {
        return this.minPressure;
    }

    @Override
    public int getPressure() {
        return this.pressure;
    }

    @Override
    public boolean canHandleVacuum() {
        return this.canHandleVacuum;
    }

    @Override
    public int getPU() {
        return this.pu;
    }

    @Override
    public void setPU(int pu) {
        this.pu = pu;
    }

    @Override
    public void setPressure(int amount) {
        this.pressure = amount;
        this.metaTileEntity.markDirty();
    }

    @Override
    public int getVolume() {
        return this.volume;
    }

    @Override
    public void setVolume(int volume) {
        this.volume = volume;
        this.metaTileEntity.markDirty();
    }

    @Override
    public String getName() {
        return "PressureContainer";
    }

    @Override
    public int getNetworkID() {
        return 5;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability) {
        if (capability == TKCYATileCapabilities.CAPABILITY_PRESSURE_CONTAINER) {
            return TKCYATileCapabilities.CAPABILITY_PRESSURE_CONTAINER.cast(this);
        }
        return null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("pressure", this.pressure);
        return compound;
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagCompound compound) {
        this.pressure = compound.getInteger("pressure");
    }

    @Override
    public void writeInitialData(PacketBuffer buffer) {
        super.writeInitialData(buffer);
        buffer.writeInt(this.pressure);
    }

    @Override
    public void receiveInitialData(PacketBuffer buffer) {
        super.receiveInitialData(buffer);
        this.pressure = buffer.readInt();
    }
}
