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
import tekcays_addon.gtapi.capability.containers.IRotationContainer;
import tekcays_addon.api.nbt.NBTWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static tekcays_addon.gtapi.capability.TKCYATileCapabilities.CAPABILITY_ROTATIONAL_CONTAINER;
import static tekcays_addon.api.nbt.NBTWrapper.*;

@Getter
@Setter
public class RotationContainer extends MTETrait implements IRotationContainer {

    @Setter(AccessLevel.NONE)
    private int speed, torque, power;

    private int maxSpeed, maxTorque, maxPower;

    @Setter(AccessLevel.NONE)
    private NBTWrapper wrapper = new NBTWrapper(NBTType.INTEGER);

    public RotationContainer(@Nonnull MetaTileEntity metaTileEntity, int maxPower, int maxSpeed, int maxTorque) {
        super(metaTileEntity);
        this.maxPower = maxPower;
        this.maxSpeed = maxSpeed;
        this.maxTorque = maxTorque;
        this.power = 0;
        this.speed = 0;
        this.torque = 0;
        this.wrapper.add("speed", speed, this::setSpeed)
                    .add("torque", torque, this::setTorque)
                    .add("power", power, this::setPower)
                    .add("maxSpeed", maxSpeed, this::setMaxSpeed)
                    .add("maxTorque", maxTorque, this::setMaxTorque)
                    .add("maxPower", maxPower, this::setMaxPower);
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
        this.metaTileEntity.markDirty();
    }

    @Override
    public void setTorque(int torque) {
        this.torque = torque;
        this.metaTileEntity.markDirty();
    }

    @Override
    public void setPower(int power) {
        this.power = power;
        this.metaTileEntity.markDirty();
    }

    @Nonnull
    @Override
    public String getName() {
        return "RotationContainer";
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability) {
        if (capability == CAPABILITY_ROTATIONAL_CONTAINER) {
            return CAPABILITY_ROTATIONAL_CONTAINER.cast(this);
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

    @Override
    public void setRotationParams(int speed, int torque, int power) {
        this.speed = speed;
        this.torque = torque;
        this.power = power;
        this.metaTileEntity.markDirty();
    }

    @Override
    public void getRotationParams(int speed, int torque, int power) {
        speed = this.speed;
        torque = this.torque;
        power = this.power;
        this.metaTileEntity.markDirty();

    }
}
