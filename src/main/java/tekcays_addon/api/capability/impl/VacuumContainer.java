package tekcays_addon.api.capability.impl;

import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.capability.IVacuumContainer;
import tekcays_addon.api.capability.TKCYATileCapabilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;

import static tekcays_addon.api.utils.TKCYAValues.ROOM_TEMPERATURE;

public class VacuumContainer extends MTETrait implements IVacuumContainer {

    private int minPressure;
    private int maxPressure;
    protected int pressure;
    private int volume;
    private FluidStack airFluidStack;
    boolean canHandleVacuum;

    /**
     * Default Vacuum container
     * {@link IVacuumContainer}
     */
    public VacuumContainer(MetaTileEntity metaTileEntity, boolean canHandleVacuum) {
        super(metaTileEntity);
        this.canHandleVacuum = canHandleVacuum;
    }

    /**
     * Default Vacuum container
     * {@link IVacuumContainer}
     */
    public VacuumContainer(MetaTileEntity metaTileEntity, boolean canHandleVacuum, int minPressure, int maxPressure) {
        super(metaTileEntity);
        this.canHandleVacuum = canHandleVacuum;
        this.minPressure = minPressure;
        this.maxPressure = maxPressure;
        this.pressure = 0;
        this.volume = 0;
        this.initializeAirFluidStack();
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
        return true;
    }

    @Override
    public void setPressure() {
        this.pressure = calculatePressure(getAirAmount(), ROOM_TEMPERATURE, getVolume());
        this.metaTileEntity.markDirty();
    }

    @Override
    public void setPressure(int temperature) {
        this.pressure = calculatePressure(getAirAmount(), temperature, getVolume());
        this.metaTileEntity.markDirty();
    }


    @Override
    public int getVolume() {
        return this.volume;
    }

    @Override
    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public FluidStack getAirFluidStack() {
        return this.airFluidStack;
    }

    @Override
    public void setAirFluidStack(FluidStack fluidStack) {
        this.airFluidStack = fluidStack;
    }

    @Override
    public boolean canLeakMore(int leak) {
        return false;
    }

    @Override
    public String getName() {
        return "VacuumContainer";
    }

    @Override
    public int getNetworkID() {
        return 6;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability) {
        if (capability == TKCYATileCapabilities.CAPABILITY_VACUUM_CONTAINER) {
            return TKCYATileCapabilities.CAPABILITY_VACUUM_CONTAINER.cast(this);
        }
        return null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("pressure", this.pressure);
        compound.setInteger("volume", this.volume);
        compound.setTag("airFluidStack", this.setFluidStackNBT());
        return compound;
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagCompound compound) {
        this.pressure = compound.getInteger("pressure");
        this.volume = compound.getInteger("volume");
        this.airFluidStack = FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("airFluidStack"));
    }

    @Override
    public void writeInitialData(PacketBuffer buffer) {
        super.writeInitialData(buffer);
        buffer.writeInt(this.pressure);
        buffer.writeInt(this.volume);
        buffer.writeCompoundTag(this.setFluidStackNBT());
    }

    @Override
    public void receiveInitialData(PacketBuffer buffer) {
        super.receiveInitialData(buffer);
        this.pressure = buffer.readInt();
        this.volume = buffer.readInt();
        try {
            this.airFluidStack = FluidStack.loadFluidStackFromNBT(buffer.readCompoundTag());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
