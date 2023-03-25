package tekcays_addon.api.capability;


import static tekcays_addon.api.utils.TKCYAValues.*;

public interface IPressureContainer {

    /**
     * @return the amount of pressure in the container in {@code Pa}
     */
    long getPressure();

    /**
     * Set the {@code pressure} in {@code Pa}
     */
    void setPressure();

    /**
     * Set the {@code pressure} in {@code Pa} with a temperature input
     */
    void setPressure(int temperature);

    /**
     * @return the maximum pressure this container can handle in {@code Pa}
     */
    long getMaxPressure();

    /**
     * @return the minimum pressure this container can handle in {@code Pa}
     */
    long getMinPressure();

    boolean canHandleVacuum();

    /**
     * @return the {@code volume} of the {@code IPressureContainer} in m3
     */
    int getVolume();

    /**
     * Sets the {@code volume} of the {@code IPressureContainer} in m3
     */
    void setVolume(int volume);

    void setPressurizedFluidName(String pressurizedFluidName);

    String getPressurizedFluidName();

    void setPressurizedFluidAmount(int pressurizedFluidAmount);

    int getPressurizedFluidAmount();

    default void increasePressurizedFluidAmount(int amount) {
        setPressurizedFluidAmount(getPressurizedFluidAmount() + amount);
    }

    /**
     * Calculates the amount of substance which transcribes as an amount of a {@code FluidStack} following the ideal gas law.
     * @param pressure in {@code Pa}.
     * @param temperature in {@code K}.
     * @param volume in {@code m3}.
     * @return the corresponding {@code fluidAmount}.
     */
    default int calculateFluidAmount(int pressure, int temperature, int volume) {
        return (int) ((1.0 * pressure * volume * FLUID_MULTIPLIER_PRESSURE) / (1.0 * PERFECT_GAS_CONSTANT * temperature) ); // n = PV / RT
    }

    /**
     * Calculates the pressure following the ideal gas law.
     * @param fluidAmount the amount of substance, transcribed as the amount of a {@code FluidStack}.
     * @param temperature in {@code K}.
     * @param volume in {@code m3}.
     * @return the corresponding pressure in {@code Pa}
     */
    default int calculatePressure(int fluidAmount, int temperature, int volume) {
        return (int) ((1.0 * fluidAmount * PERFECT_GAS_CONSTANT * temperature) / (volume * FLUID_MULTIPLIER_PRESSURE)); // P = nRT / V
    }

    /**
     * @param amount
     * @return {@code false} if the {@code amount} is negative and would give a {@code fluidAmount} lower than 1 (MINIMUM_FLUID_STACK_AMOUNT).
     */
    default boolean canChangeFluidAmount(int amount) {
        return getPressurizedFluidAmount() + amount >= MINIMUM_FLUID_STACK_AMOUNT;
    }




}
