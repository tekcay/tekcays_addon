package tekcays_addon.api.capability.impl;

import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.unification.material.Materials;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.capability.containers.IVacuumContainer;
import tekcays_addon.api.capability.TKCYATileCapabilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static lombok.AccessLevel.NONE;
import static tekcays_addon.api.consts.DataIds.AIR_FLUID_STACK;
import static tekcays_addon.api.consts.NBTKeys.PRESSURE_KEY;
import static tekcays_addon.api.utils.TKCYAValues.ROOM_TEMPERATURE;

@Getter
@Setter
public class VacuumContainer extends MTETrait implements IVacuumContainer {

    @Setter(NONE)
    protected int minPressure;
    @Setter(NONE)
    protected int maxPressure;
    @Setter(NONE)
    protected int pressure;
    protected int volume;
    protected FluidStack airFluidStack;

    @Getter(NONE)
    @Setter(NONE)
    protected boolean canHandleVacuum;

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
    public boolean canHandleVacuum() {
        return true;
    }

    @Override
    public boolean canLeakMore(int leak) {
        return false;
    }

    @Nonnull
    @Override
    public String getName() {
        return "VacuumContainer";
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability) {
        if (capability == TKCYATileCapabilities.CAPABILITY_VACUUM_CONTAINER) {
            return TKCYATileCapabilities.CAPABILITY_VACUUM_CONTAINER.cast(this);
        }
        return null;
    }

    @Nonnull
    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setLong(PRESSURE_KEY, this.pressure);
        compound.setInteger("volume", this.volume);
        //compound.setTag(AIR_FLUID_STACK.getName(), this.setAirFluidStackNBT());
        compound.setInteger("airAmount", this.getAirAmount());
        return compound;
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagCompound compound) {
        super.deserializeNBT(compound);
        this.pressure = compound.getInteger(PRESSURE_KEY);
        this.volume = compound.getInteger("volume");
        this.airFluidStack = FluidStack.loadFluidStackFromNBT(compound.getCompoundTag(AIR_FLUID_STACK.getName()));
        this.airFluidStack = Materials.Air.getFluid(compound.getInteger("airAmount"));
    }

    @Override
    public void writeInitialData(@Nonnull PacketBuffer buffer) {
        super.writeInitialData(buffer);
        buffer.writeInt(this.pressure);
        buffer.writeInt(this.volume);
        //buffer.writeCompoundTag(this.setAirFluidStackNBT());
        buffer.writeInt(this.getAirAmount());
    }

    @Override
    public void receiveInitialData(@Nonnull PacketBuffer buffer) {
        super.receiveInitialData(buffer);
        this.pressure = buffer.readInt();
        this.volume = buffer.readInt();
        this.airFluidStack = Materials.Air.getFluid(buffer.readInt());
        /*
        try {
            this.airFluidStack = FluidStack.loadFluidStackFromNBT(buffer.readCompoundTag());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

         */
    }


    /*
    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == AIR_FLUID_STACK.getId()) {
            try {
                this.airFluidStack = FluidStack.loadFluidStackFromNBT(buf.readCompoundTag());
                TKCYALog.logger.info("InVacuumContainer, is airFluidStack null ? " + this.airFluidStack == null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

     */
}
