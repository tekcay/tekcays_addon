package tekcays_addon.api.capability.impl;

import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.capability.IPressureContainer;
import tekcays_addon.api.capability.TKCYATileCapabilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.io.IOException;

import static gregtech.api.unification.material.Materials.Air;
import static tekcays_addon.api.utils.TKCYAValues.*;

public class PressureContainer extends MTETrait implements IPressureContainer {

    private int minPressure;
    private int maxPressure;
    protected int pressure;
    private int volume;
    private FluidStack fluidStack;
    boolean canHandleVacuum;

    /**
     * Default Pressure container
     * {@link IPressureContainer}
     */
    public PressureContainer(MetaTileEntity metaTileEntity, boolean canHandleVacuum) {
        super(metaTileEntity);
        this.canHandleVacuum = canHandleVacuum;
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
    public void setPressure() {
        this.pressure = calculatePressure(getFluidAmount(), ROOM_TEMPERATURE, getVolume());
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
    public FluidStack getFluidStack() {
        return this.fluidStack;
    }

    //TODO ELSE ?
    @Override
    public void setFluidStack(FluidStack fluidStack) {
        if (!getFluidStack().isFluidEqual(fluidStack)) this.fluidStack = fluidStack;
        else this.fluidStack = getDefaultFluidStack();
    }

    public FluidStack getDefaultFluidStack() {
        return new FluidStack(Air.getFluid(), calculateFluidAmount(ATMOSPHERIC_PRESSURE, ROOM_TEMPERATURE, getVolume()));
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
        compound.setTag("fluidStack", this.setFluidStackNBT());
        return compound;
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagCompound compound) {
        this.pressure = compound.getInteger("pressure");
        this.fluidStack = FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("fluidStack"));
    }

    @Override
    public void writeInitialData(PacketBuffer buffer) {
        super.writeInitialData(buffer);
        buffer.writeInt(this.pressure);
        buffer.writeCompoundTag(this.setFluidStackNBT());
    }

    @Override
    public void receiveInitialData(PacketBuffer buffer) {
        super.receiveInitialData(buffer);
        this.pressure = buffer.readInt();
        try {
            this.fluidStack = FluidStack.loadFluidStackFromNBT(buffer.readCompoundTag());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
