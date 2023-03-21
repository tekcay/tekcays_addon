package tekcays_addon.api.capability.impl;

import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.capability.IPressureContainer;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.utils.TKCYALog;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.io.IOException;

import static tekcays_addon.api.consts.DataIds.PRESSURIZED_FLUID_STACK;
import static tekcays_addon.api.utils.TKCYAValues.*;

public class PressureContainer extends VacuumContainer implements IPressureContainer {

    private int minPressure;
    private int maxPressure;
    private FluidStack fluidStack;
    private int pressurizedFluidAmount;
    private String pressurizedFluidName;

    /**
     * Default Pressure container
     * {@link IPressureContainer}
     */
    public PressureContainer(MetaTileEntity metaTileEntity) {
        super(metaTileEntity, false);
    }

    /**
     * Default Pressure container
     * {@link IPressureContainer}
     */
    public PressureContainer(MetaTileEntity metaTileEntity, int minPressure, int maxPressure) {
        super(metaTileEntity, false);
        this.minPressure = minPressure;
        this.maxPressure = maxPressure;
        this.pressurizedFluidName = "";
        this.pressurizedFluidAmount = 0;
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
        return false;
    }

    @Override
    public boolean canLeakMore(int leak) {
        return false;
    }

    @Override
    public FluidStack getFluidStack() {
        return this.fluidStack;
    }

    @Override
    public void setPressurizedFluidName(String pressurizedFluidName) {
        this.pressurizedFluidName = pressurizedFluidName;
    }

    @Override
    public String getPressurizedFluidName() {
        return this.pressurizedFluidName;
    }

    @Override
    public void setPressurizedFluidAmount(int pressurizedFluidAmount) {
        this.pressurizedFluidAmount = pressurizedFluidAmount;
    }

    @Override
    public int getPressurizedFluidAmount() {
        return this.pressurizedFluidAmount;
    }

    @Override
    public void setFluidStack(FluidStack fluidStack) {
        this.fluidStack = fluidStack;
    }

    @Override
    public void setPressure() {
        this.pressure = calculatePressure(getTotalFluidAmount(), ROOM_TEMPERATURE, getVolume());
        this.metaTileEntity.markDirty();
    }

    @Override
    public void setPressure(int temperature) {
        this.pressure = calculatePressure(getTotalFluidAmount(), temperature, getVolume());
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
        super.serializeNBT();
        NBTTagCompound compound = super.serializeNBT();
        //compound.setTag(PRESSURIZED_FLUID_STACK.getName(), this.setFluidStackNBT());
        compound.setInteger("pressurizedFluid", this.pressurizedFluidAmount);
        compound.setString("pressurizedFluidName", this.pressurizedFluidName);
        return compound;
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagCompound compound) {
        super.deserializeNBT(compound);
        this.pressurizedFluidAmount = compound.getInteger("pressurizedFluid");
        this.pressurizedFluidName = compound.getString("pressurizedFluidName");
        //this.fluidStack = FluidStack.loadFluidStackFromNBT(compound.getCompoundTag(PRESSURIZED_FLUID_STACK.getName()));

    }

    @Override
    public void writeInitialData(PacketBuffer buffer) {
        super.writeInitialData(buffer);
        buffer.writeInt(this.pressurizedFluidAmount);
        buffer.writeString(this.pressurizedFluidName);
        //buffer.writeCompoundTag(this.setFluidStackNBT());
    }

    @Override
    public void receiveInitialData(PacketBuffer buffer) {
        super.receiveInitialData(buffer);
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
