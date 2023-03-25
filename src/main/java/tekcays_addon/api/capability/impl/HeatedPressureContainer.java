package tekcays_addon.api.capability.impl;

import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.capability.IPressureContainer;

import javax.annotation.Nonnull;

import static gregtech.api.unification.material.Materials.Air;
import static tekcays_addon.api.utils.TKCYAValues.ATMOSPHERIC_PRESSURE;

public class HeatedPressureContainer extends PressureContainer {

    private int temperature;

    /**
     * Default Pressure container
     * {@link IPressureContainer}
     */
    public HeatedPressureContainer(MetaTileEntity metaTileEntity) {
        super(metaTileEntity);
    }

    /**
     * Pressure Container with pressure parameters
     * {@link IPressureContainer}
     */
    public HeatedPressureContainer(MetaTileEntity metaTileEntity, boolean canHandleVacuum, int minPressure, int maxPressure) {
        super(metaTileEntity, minPressure, maxPressure);
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    @Override
    public void setPressure(int temperature) {
        this.pressure = calculatePressure(getPressurizedFluidAmount(), getTemperature(), getVolume());
        this.metaTileEntity.markDirty();
    }

    @Override
    public String getName() {
        return "HeatedPressureContainer";
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = super.serializeNBT();
        nbt.setInteger("temperature", this.temperature);
        return nbt;
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagCompound compound) {
        super.deserializeNBT(compound);
        this.temperature = compound.getInteger("temperature");
    }

    @Override
    public void writeInitialData(PacketBuffer buffer) {
        super.writeInitialData(buffer);
        buffer.writeInt(this.temperature);
    }

    @Override
    public void receiveInitialData(PacketBuffer buffer) {
        super.receiveInitialData(buffer);
        this.temperature = buffer.readInt();
    }
}
