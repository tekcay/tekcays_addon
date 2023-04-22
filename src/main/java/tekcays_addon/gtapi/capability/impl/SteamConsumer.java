package tekcays_addon.gtapi.capability.impl;

import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import lombok.Getter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;
import tekcays_addon.gtapi.capability.TKCYATileCapabilities;
import tekcays_addon.gtapi.capability.containers.ISteamConsumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static tekcays_addon.api.consts.NBTKeys.*;

@Getter
public class SteamConsumer extends MTETrait implements ISteamConsumer {

    private int steamConsumption;
    private int waterOutputRate;

    /**
     * Create a new MTE trait.
     * @param metaTileEntity the MTE to reference, and add the trait to
     */
    public SteamConsumer(@Nonnull MetaTileEntity metaTileEntity) {
        super(metaTileEntity);
        this.steamConsumption = 0;
        this.waterOutputRate = 0;
    }

    public SteamConsumer(@Nonnull MetaTileEntity metaTileEntity, int steamConsumption, int waterOutputRate) {
        super(metaTileEntity);
        this.steamConsumption = steamConsumption;
        this.waterOutputRate = waterOutputRate;
    }

    @Override
    public void setSteamConsumption(int steamConsumption) {
        this.steamConsumption = steamConsumption;
        this.metaTileEntity.markDirty();
    }

    @Override
    public void setWaterOutputRate(int waterOutputRate) {
        this.waterOutputRate = waterOutputRate;
        this.metaTileEntity.markDirty();
    }

    @Override
    public void changeSteamConsumption(int amount) {
        this.steamConsumption = Math.max(this.steamConsumption + amount, 0);
    }

    @Override
    public void changeWaterOutputRate(int amount) {
        this.waterOutputRate = Math.max(this.waterOutputRate + amount, 0);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Steam Consumer";
    }

    @Nullable
    public <T> T getCapability(Capability<T> capability) {
        if (capability == TKCYATileCapabilities.CAPABILITY_STEAM_CONSUMER_CONTAINER) {
            return TKCYATileCapabilities.CAPABILITY_STEAM_CONSUMER_CONTAINER.cast(this);
        }
        return null;
    }

    @Nonnull
    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = super.serializeNBT();
        compound.setInteger(STEAM_CONSUMPTION_KEY, this.steamConsumption);
        compound.setInteger(WATER_OUTPUT_RATE_KEY, this.waterOutputRate);
        return compound;
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagCompound compound) {
        this.steamConsumption = compound.getInteger(STEAM_CONSUMPTION_KEY);
        this.waterOutputRate = compound.getInteger(WATER_OUTPUT_RATE_KEY);
    }

    @Override
    public void writeInitialData(@Nonnull PacketBuffer buffer) {
        super.writeInitialData(buffer);
        buffer.writeInt(this.steamConsumption);
        buffer.writeInt(this.waterOutputRate);
    }

    @Override
    public void receiveInitialData(@Nonnull PacketBuffer buffer) {
        super.receiveInitialData(buffer);
        this.steamConsumption = buffer.readInt();
        this.waterOutputRate = buffer.readInt();
    }

}
