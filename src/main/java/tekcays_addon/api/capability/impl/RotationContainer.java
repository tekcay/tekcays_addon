package tekcays_addon.api.capability.impl;

import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.capability.containers.IRotationContainer;
import tekcays_addon.api.capability.NBTHelper;
import tekcays_addon.api.capability.ParameterHelper;
import tekcays_addon.api.consts.CapabilityId;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static tekcays_addon.api.capability.TKCYATileCapabilities.CAPABILITY_ROTATIONAL_CONTAINER;
import static tekcays_addon.api.utils.capability.GetCapabilityHelper.getCapabilityOfContainer;

@Getter
@Setter
public class RotationContainer extends MTETrait implements IRotationContainer, NBTHelper {

    private int speed, torque, power;
    private int maxSpeed, maxTorque, maxPower;

    public RotationContainer(@Nonnull MetaTileEntity metaTileEntity, int maxPower, int maxSpeed, int maxTorque) {
        super(metaTileEntity);
        this.maxPower = maxPower;
        this.maxSpeed = maxSpeed;
        this.maxTorque = maxTorque;
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
        return serializeNBTHelper(compound);
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagCompound compound) {
        deserializeNBTHelper(compound);
    }

    @Override
    public void writeInitialData(@Nonnull PacketBuffer buffer) {
        super.writeInitialData(buffer);
        writeInitialDataHelper(buffer);
    }

    @Override
    public void receiveInitialData(@Nonnull PacketBuffer buffer) {
        super.receiveInitialData(buffer);
        receiveInitialDataHelper(buffer);
    }
    
    @Override
    public List<ParameterHelper<Integer>> getAllIntValues() {
        List<ParameterHelper<Integer>> parametersValues = new ArrayList<>();
        parametersValues.add(new ParameterHelper<>("speed", speed, this::setSpeed));
        parametersValues.add(new ParameterHelper<>("torque", torque, this::setTorque));
        parametersValues.add(new ParameterHelper<>("power", power, this::setPower));
        parametersValues.add(new ParameterHelper<>("maxSpeed", maxSpeed, this::setMaxSpeed));
        parametersValues.add(new ParameterHelper<>("maxTorque", maxTorque, this::setMaxTorque));
        parametersValues.add(new ParameterHelper<>("maxPower", maxPower, this::setMaxPower));
        return parametersValues;
    }
}
