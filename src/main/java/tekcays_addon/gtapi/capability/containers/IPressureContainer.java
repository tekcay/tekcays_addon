package tekcays_addon.gtapi.capability.containers;


import net.minecraftforge.fluids.FluidStack;

import java.util.List;

import static tekcays_addon.gtapi.consts.TKCYAValues.*;

public interface IPressureContainer {

    /**
     * Changes the amount of the {@link IPressureContainer} {@code pressurizedFluidStack} by adding a provided
     * {@code amount}.
     * <br>
     * If the addition results in a negative value, the amount of {@code pressurizedFluidStack} is set to 0 and the
     * rest is returned.
     * <br>
     * If the {@code pressurizedFluidStack} is {@code null}, it returns the provided amount.
     * @param amount
     * @return the amount that has been transfered.
     */
    int changePressurizedFluidStack(FluidStack fluidStack, int amount);

    /**
     * @return the amount of pressure in the container in {@code Pa}
     */
    int getPressure();

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
    int getMaxPressure();

    /**
     * @return the minimum pressure this container can handle in {@code Pa}
     */
    int getMinPressure();

    boolean canHandleVacuum();

    /**
     * @return the {@code volume} of the {@code IPressureContainer} in m3
     */
    int getVolume();

    /**
     * Sets the {@code volume} of the {@code IPressureContainer} in m3
     */
    void setVolume(int volume);

    FluidStack getPressurizedFluidStack();

    int getPressurizedFluidStackAmount();

    String getPressurizedFluidStackLocalizedName();

    void setPressurizedFluidStack(FluidStack fluidStack);

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

    void addTooltip(List<String> tooltip, int leakingRate);

}
