package tekcays_addon.api.capability.impl;

import gregicality.science.api.GCYSValues;
import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;
import tekcays_addon.api.capability.IHeatContainer;
import tekcays_addon.api.capability.TKCYATileCapabilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HeatContainer extends MTETrait implements IHeatContainer {

    private final int minHeat;
    private final int maxHeat;
    private int maxTemperature;
    private int heat;
    private int temperature;

    /**
     * Default Heat container
     * {@link IHeatContainer}
     */
    public HeatContainer(MetaTileEntity metaTileEntity, int minHeat, int maxHeat) {
        super(metaTileEntity);
        this.minHeat = minHeat;
        this.maxHeat = maxHeat;
        this.heat = 0;
    }

    public HeatContainer(MetaTileEntity metaTileEntity, int minHeat, int maxHeat, int maxTemperature) {
        super(metaTileEntity);
        this.minHeat = minHeat;
        this.maxHeat = maxHeat;
        this.heat = 0;
        this.maxTemperature = maxTemperature;
        this.temperature = GCYSValues.EARTH_TEMPERATURE;
    }

    @Override
    public int getMaxPressure() {
        return this.maxHeat;
    }

    @Override
    public int getMinPressure() {
        return this.minHeat;
    }

    @Override
    public int getPressure() {
        return this.heat;
    }

    @Override
    public int getMaxTemperature() {
        return this.maxTemperature;
    }

    @Override
    public int getTemperature() {
        return this.temperature;
    }

    @Override
    public String getName() {
        return "HeatContainer";
    }

    @Override
    public void setPressure(int amount) {
        this.heat = amount;
        this.metaTileEntity.markDirty();
    }

    @Override
    public void setTemperature(int temperature) {
        this.temperature = temperature;
        this.metaTileEntity.markDirty();
    }

    @Override
    public int getNetworkID() {
        return 4;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability) {
        if (capability == TKCYATileCapabilities.CAPABILITY_HEAT_CONTAINER) {
            return TKCYATileCapabilities.CAPABILITY_HEAT_CONTAINER.cast(this);
        }
        return null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("heat", this.heat);
        compound.setInteger("temperature", this.temperature);
        return compound;
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagCompound compound) {
        this.heat = compound.getInteger("heat");
        this.temperature = compound.getInteger("temperature");
    }

    @Override
    public void writeInitialData(PacketBuffer buffer) {
        super.writeInitialData(buffer);
        buffer.writeInt(this.heat);
        buffer.writeInt(this.temperature);
    }

    @Override
    public void receiveInitialData(PacketBuffer buffer) {
        super.receiveInitialData(buffer);
        this.heat = buffer.readInt();
        this.temperature = buffer.readInt();
    }
}
