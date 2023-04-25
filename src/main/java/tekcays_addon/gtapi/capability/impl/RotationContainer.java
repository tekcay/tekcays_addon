package tekcays_addon.gtapi.capability.impl;

import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;
import tekcays_addon.gtapi.capability.containers.IRotationContainer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static tekcays_addon.api.consts.NBTKeys.*;
import static tekcays_addon.gtapi.capability.TKCYATileCapabilities.CAPABILITY_ROTATIONAL_CONTAINER;

@Getter
@Setter
@ToString
public class RotationContainer extends MTETrait implements IRotationContainer {

    @Setter(AccessLevel.NONE)
    private int speed, torque, power;
    private int maxSpeed, maxTorque, maxPower;

    public RotationContainer(@Nonnull MetaTileEntity metaTileEntity, int maxSpeed, int maxTorque, int maxPower) {
        super(metaTileEntity);
        this.maxPower = maxPower;
        this.maxSpeed = maxSpeed;
        this.maxTorque = maxTorque;
        this.power = 0;
        this.speed = 0;
        this.torque = 0;
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

    @Override
    public void changeSpeed(int amount) {
        setSpeed(Math.max(this.speed + amount, 0));
    }

    @Override
    public void changeTorque(int amount) {
        this.torque = Math.max(this.torque + amount, 0);
    }

    @Override
    public void changePower(int amount) {
        this.power = Math.max(this.power + amount, 0);
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
        compound.setInteger(SPEED_KEY, this.speed);
        compound.setInteger(TORQUE_KEY, this.torque);
        compound.setInteger(POWER_KEY, this.power);
        return compound;
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagCompound compound) {
        this.speed = compound.getInteger(SPEED_KEY);
        this.torque = compound.getInteger(TORQUE_KEY);
        this.power = compound.getInteger(POWER_KEY);

    }

    @Override
    public void writeInitialData(@Nonnull PacketBuffer buffer) {
        super.writeInitialData(buffer);
        buffer.writeInt(this.speed);
        buffer.writeInt(this.torque);
        buffer.writeInt(this.power);
    }

    @Override
    public void receiveInitialData(@Nonnull PacketBuffer buffer) {
        super.receiveInitialData(buffer);
        this.speed = buffer.readInt();
        this.torque = buffer.readInt();
        this.power = buffer.readInt();
    }

    @Override
    public void setRotationParams(int speed, int torque, int power) {
        this.speed = speed;
        this.torque = torque;
        this.power = power;
        this.metaTileEntity.markDirty();
    }

    @Override
    public void setRotationParams(IRotationContainer rotationContainer) {
        this.speed = rotationContainer.getSpeed();
        this.torque = rotationContainer.getTorque();
        this.power = rotationContainer.getPower();
        this.metaTileEntity.markDirty();
    }

    @Override
    public void getRotationParams(int speed, int torque, int power) {
        speed = this.speed;
        torque = this.torque;
        power = this.power;
        this.metaTileEntity.markDirty();

    }

    @Override
    public void getRotationParams(IRotationContainer rotationContainer) {
        rotationContainer.setRotationParams(rotationContainer);
        this.metaTileEntity.markDirty();
    }

}
