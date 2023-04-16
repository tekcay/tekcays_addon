package tekcays_addon.gtapi.capability.impl;

import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;
import tekcays_addon.gtapi.capability.containers.IHeatContainer;
import tekcays_addon.gtapi.capability.TKCYATileCapabilities;
import tekcays_addon.gtapi.utils.TKCYAValues;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static tekcays_addon.api.consts.NBTKeys.*;

@Getter
@Setter
public class HeatContainer extends MTETrait implements IHeatContainer {

    private final int minHeat;
    private final int maxHeat;
    private int maxTemperature;

    @Setter(AccessLevel.NONE)
    private int heat;
    @Setter(AccessLevel.NONE)
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
        this.temperature = TKCYAValues.ROOM_TEMPERATURE;
    }

    @Nonnull
    @Override
    public String getName() {
        return "HeatContainer";
    }

    @Override
    public void setHeat(int amount) {
        this.heat = amount;
        this.metaTileEntity.markDirty();
    }

    @Override
    public void setTemperature(int temperature) {
        this.temperature = temperature;
        this.metaTileEntity.markDirty();
    }
    @Nullable
    public <T> T getCapability(Capability<T> capability) {
        if (capability == TKCYATileCapabilities.CAPABILITY_HEAT_CONTAINER) {
            return TKCYATileCapabilities.CAPABILITY_HEAT_CONTAINER.cast(this);
        }
        return null;
    }

    @Nonnull
    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = super.serializeNBT();
        compound.setInteger(HEAT_KEY, this.heat);
        compound.setInteger(TEMPERATURE_KEY, this.temperature);
        return compound;
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagCompound compound) {
        this.heat = compound.getInteger(HEAT_KEY);
        this.temperature = compound.getInteger(TEMPERATURE_KEY);
    }

    @Override
    public void writeInitialData(@Nonnull PacketBuffer buffer) {
        super.writeInitialData(buffer);
        buffer.writeInt(this.heat);
        buffer.writeInt(this.temperature);
    }

    @Override
    public void receiveInitialData(@Nonnull PacketBuffer buffer) {
        super.receiveInitialData(buffer);
        this.heat = buffer.readInt();
        this.temperature = buffer.readInt();
    }

}
