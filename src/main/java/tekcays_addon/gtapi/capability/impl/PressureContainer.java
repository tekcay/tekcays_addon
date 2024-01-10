package tekcays_addon.gtapi.capability.impl;

import static tekcays_addon.api.consts.DataIds.PRESSURIZED_FLUID_STACK;
import static tekcays_addon.api.consts.NBTKeys.PRESSURE_KEY;
import static tekcays_addon.api.fluids.FluidStackHelper.*;
import static tekcays_addon.api.units.PressureFormatting.*;
import static tekcays_addon.gtapi.consts.TKCYAValues.*;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import tekcays_addon.gtapi.capability.TKCYATileCapabilities;
import tekcays_addon.gtapi.capability.containers.IPressureContainer;

@Getter
@Setter
public class PressureContainer extends MTETrait implements IPressureContainer {

    @Setter(AccessLevel.NONE)
    protected int volume;

    @Setter(AccessLevel.NONE)
    protected int pressure;

    @Setter(AccessLevel.NONE)
    private FluidStack pressurizedFluidStack;

    protected int minPressure;
    protected int maxPressure;

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

    public PressureContainer(MetaTileEntity metaTileEntity, int maxPressure) {
        super(metaTileEntity);
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
            setPressurizedFluidStack(
                    getAmountChangedFluidStack(getPressurizedFluidStack(), smallestFluidStackToTransfer.amount));
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

    @Nullable
    @Override
    public void setPressurizedFluidStack(FluidStack fluidStack) {
        this.pressurizedFluidStack = fluidStack;
        this.metaTileEntity.markDirty();
    }

    @Override
    public void setVolumeContainer(int volume) {
        this.volume = volume;
        this.metaTileEntity.markDirty();
    }

    @Override
    public void setPressure() {
        this.pressure = calculatePressure(getPressurizedFluidStackAmount(), ROOM_TEMPERATURE, getContainerVolume());
        this.metaTileEntity.markDirty();
    }

    @Override
    public void setPressure(int temperature) {
        setPressure();
    }

    @Override
    public void addTooltip(List<String> tooltip, int leakingRate) {
        tooltip.add(
                I18n.format("tkcya.general.pressure.tooltip.pressure", convertPressureToBar(getMaxPressure(), true)));
        tooltip.add(I18n.format("tkcya.general.pressure.tooltip.pressure.leak", Math.abs(leakingRate)));
    }

    @Override
    public boolean canHandleVacuum() {
        return false;
    }

    @Override
    public int getContainerVolume() {
        return 0;
    }

    @NotNull
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

    @NotNull
    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = super.serializeNBT();
        compound.setTag(PRESSURIZED_FLUID_STACK.name(), setFluidStackNBT(this.pressurizedFluidStack));
        compound.setInteger(PRESSURE_KEY, this.pressure);
        return compound;
    }

    @Override
    public void deserializeNBT(@NotNull NBTTagCompound compound) {
        this.pressure = compound.getInteger(PRESSURE_KEY);
        this.pressurizedFluidStack = FluidStack
                .loadFluidStackFromNBT(compound.getCompoundTag(PRESSURIZED_FLUID_STACK.name()));
    }

    @Override
    public void writeInitialData(@NotNull PacketBuffer buffer) {
        super.writeInitialSyncData(buffer);
        buffer.writeInt(this.pressure);
    }

    @Override
    public void receiveInitialData(@NotNull PacketBuffer buffer) {
        super.receiveInitialData(buffer);
        this.pressure = buffer.readInt();
    }

    /**
     * Reset the pressure to default i.e. 1 bar and reset the {@code pressurizedFluidStack} to {@code null};
     */
    @Override
    public void resetContainer() {
        this.setPressure(ATMOSPHERIC_PRESSURE);
        this.setPressurizedFluidStack(null);
    }
}
