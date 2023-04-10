package tekcays_addon.api.capability.containers;


public interface IRotationContainer {

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
     * Change the amount of speed in the container by a given amount
     *
     * @param amount   the amount to change by
     * @param simulate whether to actually change the value or not
     * @return true if the change is safe, else false
     */
    default boolean changeSpeed(int amount, boolean simulate) {
        if (simulate) return isRotationSafe((getSpeed() + amount) * getTorque());
        setSpeed(getSpeed() + amount);
        return true;
    }

    /**
     * @return the minimum torque this container can handle
     */
    int getMinTorque();

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
