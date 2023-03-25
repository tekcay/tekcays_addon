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


import static tekcays_addon.api.consts.NetworkIds.PRESSURE;
import static tekcays_addon.api.utils.TKCYAValues.*;

public class PressureContainer extends MTETrait implements IPressureContainer {

    protected int volume;
    protected long pressure;
    protected long minPressure;
    protected long maxPressure;
    protected int pressurizedFluidAmount;
    protected String pressurizedFluidName;

    /**
     * Default Pressure container
     * {@link IPressureContainer}
     */
    public PressureContainer(MetaTileEntity metaTileEntity) {
        super(metaTileEntity);
    }

    /**
     * Default Pressure container
     * {@link IPressureContainer}
     */
    public PressureContainer(MetaTileEntity metaTileEntity, long minPressure, long maxPressure) {
        super(metaTileEntity);
        this.minPressure = minPressure;
        this.maxPressure = maxPressure;
        this.pressurizedFluidName = "";
        this.pressurizedFluidAmount = 0;
    }
    @Override
    public long getMaxPressure() {
        return this.maxPressure;
    }

    @Override
    public long getMinPressure() {
        return this.minPressure;
    }

    @Override
    public long getPressure() {
        return this.pressure;
    }

    @Override
    public boolean canHandleVacuum() {
        return false;
    }

    @Override
    public int getVolume() {
        return this.volume;
    }
    @Override
    public String getPressurizedFluidName() {
        return this.pressurizedFluidName;
    }
    @Override
    public int getPressurizedFluidAmount() {
        return this.pressurizedFluidAmount;
    }

    @Override
    public void setVolume(int volume) {
        this.volume = volume;
        this.metaTileEntity.markDirty();
    }

    @Override
    public void setPressurizedFluidName(String pressurizedFluidName) {
        this.pressurizedFluidName = pressurizedFluidName;
        this.metaTileEntity.markDirty();
    }

    @Override
    public void setPressurizedFluidAmount(int pressurizedFluidAmount) {
        this.pressurizedFluidAmount = pressurizedFluidAmount;
        this.metaTileEntity.markDirty();
    }


    @Override
    public void setPressure() {
        this.pressure = calculatePressure(getPressurizedFluidAmount(), ROOM_TEMPERATURE, getVolume());
        this.metaTileEntity.markDirty();
    }

    @Override
    public void setPressure(int temperature) {
        setPressure();
    }

    @Override
    public String getName() {
        return "PressureContainer";
    }

    @Override
    public int getNetworkID() {
        return PRESSURE;
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
        //super.serializeNBT();
        NBTTagCompound compound = super.serializeNBT();
        //compound.setTag(PRESSURIZED_FLUID_STACK.getName(), this.setFluidStackNBT());
        compound.setLong("pressure", this.pressure);
        compound.setInteger("pressurizedFluid", this.pressurizedFluidAmount);
        compound.setString("pressurizedFluidName", this.pressurizedFluidName);
        return compound;
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagCompound compound) {
        //super.deserializeNBT(compound);
        this.pressure = compound.getLong("pressure");
        this.pressurizedFluidAmount = compound.getInteger("pressurizedFluid");
        this.pressurizedFluidName = compound.getString("pressurizedFluidName");
        //this.fluidStack = FluidStack.loadFluidStackFromNBT(compound.getCompoundTag(PRESSURIZED_FLUID_STACK.getName()));

    }

    @Override
    public void writeInitialData(PacketBuffer buffer) {
        super.writeInitialData(buffer);
        buffer.writeLong(this.pressure);
        buffer.writeInt(this.pressurizedFluidAmount);
        buffer.writeString(this.pressurizedFluidName);
        //buffer.writeCompoundTag(this.setFluidStackNBT());
    }

    @Override
    public void receiveInitialData(PacketBuffer buffer) {
        super.receiveInitialData(buffer);
        this.pressure = buffer.readLong();
        this.pressurizedFluidAmount = buffer.readInt();
        this.pressurizedFluidName = buffer.readString(40);

        /*
        try {
            this.fluidStack = FluidStack.loadFluidStackFromNBT(buffer.readCompoundTag());
            //TKCYALog.logger.info("pressurized fluidStack is {}, with amount of {}", this.fluidStack.amount, this.fluidStack.getLocalizedName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

         */
    }

    /*
    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == PRESSURIZED_FLUID_STACK.getId()) {
            try {
                this.fluidStack = FluidStack.loadFluidStackFromNBT(buf.readCompoundTag());
                TKCYALog.logger.info("InPressureContainer, is fluidStack null ? " + this.fluidStack == null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

     */
}
