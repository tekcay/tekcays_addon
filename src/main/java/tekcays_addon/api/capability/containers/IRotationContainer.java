package tekcays_addon.api.capability.containers;


import net.minecraftforge.common.capabilities.Capability;
import tekcays_addon.api.capability.impl.RotationContainer;
import tekcays_addon.api.consts.CapabilityId;

public interface IRotationContainer extends IContainer<IRotationContainer, RotationContainer> {

    @Override
    Capability<IRotationContainer> getContainerCapability();
    @Override
    RotationContainer getContainer();
    @Override
    CapabilityId getCapabilityId();

    /**
     * Set the speed of rotation in the container
     *
     * @param speed the value of speed to set
     */
    void setSpeed(int speed);

    /**
     * Set the torque of rotation in the container
     *
     * @param torque the value of torque to set
     */
    void setTorque(int torque);

    /**
     * Set the power of rotation in the container
     */
    void setPower(int power);

    /**
     * @return speed of the rotation
     */
    int getTorque();

    /**
     * @return speed of the rotation
     */
    int getSpeed();

    /**
     * @return power of the rotation
     */
    int getPower();


    /**
     * @return power of the rotation
     */
    default int calculatePower() {
        return getSpeed() * getTorque();
    }

    /**
     * @return speed of the rotation
     */
    default int calculateTorque() {
        return getPower() / getSpeed();
    }

    /**
     * @return speed of the rotation
     */
    default int calculateSpeed() {
        return getPower() / getTorque();
    }

    /**
     * @return the minimum torque this container can handle
     */
    int getMaxTorque();

    /**
     * @return the maximum speed this container can handle
     */
    int getMaxSpeed();

    /**
     * @return the maximum rotation power this container can handle
     */
    default int getMaxPower() {
        return getMaxSpeed() * getMaxTorque();
    }

    /**
     * @return the maximum rotation power this container can handle
     */
    default int calculateMaxPower() {
        return getMaxSpeed() * getMaxTorque();
    }

    /**
     * @param Power the power of rotation to check
     * @return true if the rotation power is safe for the container, else false
     */
    default boolean isRotationSafe(int Power) {
        return Power <= getMaxPower();
    }
}
