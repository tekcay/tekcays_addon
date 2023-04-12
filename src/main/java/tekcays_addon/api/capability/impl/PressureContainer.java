package tekcays_addon.api.capability.impl;

import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.capability.containers.IPressureContainer;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.utils.FluidStackHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


import static tekcays_addon.api.consts.DataIds.PRESSURIZED_FLUID_STACK;
import static tekcays_addon.api.consts.NBTKeys.PRESSURE_KEY;
import static tekcays_addon.api.utils.TKCYAValues.*;

@Getter
@Setter
public class PressureContainer extends MTETrait implements IPressureContainer, FluidStackHelper {

    @Setter(AccessLevel.NONE)
    protected int volume;

    @Setter(AccessLevel.NONE)
    protected int pressure;

    @Setter(AccessLevel.NONE)
    private FluidStack pressurizedFluidStack;

    protected int minPressure;
    protected int maxPressure;

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
    public PressureContainer(MetaTileEntity metaTileEntity, int minPressure, int maxPressure) {
        super(metaTileEntity);
        this.minPressure = minPressure;
        this.maxPressure = maxPressure;
    }

    @Override
    public int changePressurizedFluidStack(FluidStack fluidStack, int amount) {

        int changed = 0;

        FluidStack smallestFluidStackToTransfer = getSmallestFluidStackByAmount(fluidStack, amount);

        if (this.pressurizedFluidStack == null) {
            setPressurizedFluidStack(smallestFluidStackToTransfer);
            changed = smallestFluidStackToTransfer.amount;
        }

        if (fluidStack.isFluidEqual(this.pressurizedFluidStack)) {
            setPressurizedFluidStack(getChangedFluidStack(getPressurizedFluidStack(), smallestFluidStackToTransfer.amount));
            changed = smallestFluidStackToTransfer.amount;
        }

        return changed;
    }

    @Override
    public int getPressurizedFluidStackAmount() {
        return getNullableFluidStackAmount(this.pressurizedFluidStack);
    }

    @Override
    public String getPressurizedFluidStackLocalizedName() {
        return getNullableFluidStackLocalizedName(this.pressurizedFluidStack);
    }

    @Override
    public void setPressurizedFluidStack(FluidStack fluidStack) {
        this.pressurizedFluidStack = fluidStack;
        this.metaTileEntity.markDirty();
    }

    @Override
    public void setVolume(int volume) {
        this.volume = volume;
        this.metaTileEntity.markDirty();
    }

    @Override
    public void setPressure() {
        this.pressure = calculatePressure(getPressurizedFluidStackAmount(), ROOM_TEMPERATURE, getVolume());
        this.metaTileEntity.markDirty();
    }

    @Override
    public void setPressure(int temperature) {
        setPressure();
    }

    @Override
    public boolean canHandleVacuum() {
        return false;
    }

    @Nonnull
    @Override
    public String getName() {
        return "PressureContainer";
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability) {
        if (capability == TKCYATileCapabilities.CAPABILITY_PRESSURE_CONTAINER) {
            return TKCYATileCapabilities.CAPABILITY_PRESSURE_CONTAINER.cast(this);
        }
        return null;
    }


    @Nonnull
    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = super.serializeNBT();
        compound.setTag(PRESSURIZED_FLUID_STACK.getName(), this.setFluidStackNBT(this.pressurizedFluidStack));
        compound.setInteger(PRESSURE_KEY, this.pressure);
        return compound;
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagCompound compound) {
        this.pressure = compound.getInteger(PRESSURE_KEY);
        this.pressurizedFluidStack = FluidStack.loadFluidStackFromNBT(compound.getCompoundTag(PRESSURIZED_FLUID_STACK.getName()));

    }

    @Override
    public void writeInitialData(@Nonnull PacketBuffer buffer) {
        super.writeInitialData(buffer);
        buffer.writeInt(this.pressure);
    }

    @Override
    public void receiveInitialData(@Nonnull PacketBuffer buffer) {
        super.receiveInitialData(buffer);
        this.pressure = buffer.readInt();
    }


}
