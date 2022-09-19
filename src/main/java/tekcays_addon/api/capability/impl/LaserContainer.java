package tekcays_addon.api.capability.impl;

import gregicality.science.api.GCYSValues;
import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;
import tekcays_addon.api.capability.ILaserContainer;
import tekcays_addon.api.capability.TKCYATileCapabilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LaserContainer extends MTETrait implements ILaserContainer {

    private final double minLaser;
    private final double maxLaser;
    private final double volume;

    private double particles;

    /**
     * Default pressure container
     * {@link ILaserContainer}
     *
     * @param volume the volume of the container, must be nonzero
     */
    public LaserContainer(MetaTileEntity metaTileEntity, double minLaser, double maxLaser, double volume) {
        super(metaTileEntity);
        this.minLaser = minLaser;
        this.maxLaser = maxLaser;
        this.volume = volume;
        this.particles = volume * GCYSValues.EARTH_PRESSURE;
    }

    @Override
    public double getMaxLaser() {
        return this.maxLaser;
    }

    @Override
    public double getParticles() {
        return this.particles;
    }

    @Override
    public double getVolume() {
        return this.volume;
    }

    @Override
    public void setParticles(double amount) {
        this.particles = amount;
        this.metaTileEntity.markDirty();
    }

    @Override
    public double getMinLaser() {
        return this.minLaser;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setDouble("particles", this.particles);
        return compound;
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagCompound compound) {
        this.particles = compound.getDouble("particles");
    }

    @Override
    public void writeInitialData(PacketBuffer buffer) {
        super.writeInitialData(buffer);
        buffer.writeDouble(this.particles);
    }

    @Override
    public void receiveInitialData(PacketBuffer buffer) {
        super.receiveInitialData(buffer);
        this.particles = buffer.readDouble();
    }

    @Override
    public String getName() {
        return "LaserContainer";
    }

    @Override
    public int getNetworkID() {
        return 5;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability) {
        if (capability == TKCYATileCapabilities.CAPABILITY_LASER_CONTAINER) {
            return TKCYATileCapabilities.CAPABILITY_LASER_CONTAINER.cast(this);
        }
        return null;
    }
}
