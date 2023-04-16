package tekcays_addon.gtapi.capability.containers;

public interface IRotationContainer {

    void setSpeed(int speed);
    void setTorque(int torque);
    void setPower(int power);
    void setRotationParams(int speed, int torque, int power);
    void setRotationParams(IRotationContainer rotationContainer);

    int getTorque();
    int getSpeed();
    int getPower();
    void getRotationParams(int speed, int torque, int power);
    void getRotationParams(IRotationContainer rotationContainer);

    int getMaxTorque();
    int getMaxSpeed();
    default int getMaxPower() {
        return getMaxSpeed() * getMaxTorque();
    }

    void changeSpeed(int amount);
    void changeTorque(int amount);
    void changePower(int amount);

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
