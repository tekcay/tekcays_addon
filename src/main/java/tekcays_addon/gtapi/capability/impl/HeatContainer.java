package tekcays_addon.gtapi.capability.impl;

import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;
import tekcays_addon.api.nbt.NBTType;
import tekcays_addon.gtapi.capability.containers.IHeatContainer;
import tekcays_addon.gtapi.capability.TKCYATileCapabilities;
import tekcays_addon.api.nbt.NBTWrapper;
import tekcays_addon.gtapi.utils.TKCYAValues;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static tekcays_addon.api.nbt.NBTWrapper.*;

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

    @Setter(AccessLevel.NONE)
    private NBTWrapper wrapper = new NBTWrapper(NBTType.INTEGER);

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
        this.wrapper.add("heat", heat, this::setHeat)
                    .add("temperature", temperature, this::setTemperature);
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
        serializeNBTHelper(compound, wrapper);
        return compound;
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagCompound compound) {
        deserializeNBTHelper(compound, wrapper);
    }

    @Override
    public void writeInitialData(@Nonnull PacketBuffer buffer) {
        super.writeInitialData(buffer);
        writeInitialDataHelper(buffer, wrapper);
    }

    @Override
    public void receiveInitialData(@Nonnull PacketBuffer buffer) {
        super.receiveInitialData(buffer);
        receiveInitialDataHelper(buffer, wrapper);
    }

}
