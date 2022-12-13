package tekcays_addon.api.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import static tekcays_addon.api.utils.TKCYAValues.*;

public interface IPressureContainer {

    /**
     * @return the amount of pressure in the container
     */
    int getPressure();

    /**
     * Set the {@code pressure}
     */
    void setPressure();

    /**
     * @return the maximum pressure this container can handle
     */
    int getMaxPressure();

    /**
     * @return the minimum pressure this container can handle
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

    /**
     * @return the {@code FluidStack} pressurized in the {@code IPressureContainer}
     */
    FluidStack getFluidStack();

    /**
     * Sets the {@code FluidStack} pressurized in the {@code IPressureContainer}
     * @param fluidStack
     */
    void setFluidStack(FluidStack fluidStack);

    /**
     * Gets the amount of the {@code FluidStack} in the {@code IPressureContainer}
     * @return 0 if the {@code FluidStack} is {@code null}
     */
    default int getFluidAmount() {
        int fluidAmount = 0;
        try {
            fluidAmount = getFluidStack().amount;
        } catch (NullPointerException ignored) {}
        return fluidAmount;
    }

    default NBTTagCompound setFluidStackNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        return getFluidStack().writeToNBT(nbt);
    }

    default boolean isEmpty() {
        return getFluidAmount() == 0;
    }

    /**
     * Calculates the amount of substance which transcribes as an amount of a {@code FluidStack} following the ideal gas law.
     * @param pressure in {@code bar}, conversion in {@code Pa} is made in situ.
     * @param temperature in {@code K}.
     * @param volume in {@code m3}.
     * @return the corresponding {@code fluidAmount}.
     */
    default int calculateFluidAmount(int pressure, int temperature, int volume) {
        return (int) (PERFECT_GAS_CONSTANT * temperature) / (pressure * 100000 * volume); // n = RT / PV
    }

    /**
     * Calculates the pressure following the ideal gas law.
     * @param fluidAmount the amount of substance, transcribed as the amount of a {@code FluidStack}.
     * @param temperature in {@code K}.
     * @param volume in {@code m3}.
     * @return the corresponding {@code fluidAmount}.
     */
    default int calculatePressure(int fluidAmount, int temperature, int volume) {
        return (int) (PERFECT_GAS_CONSTANT * temperature) / (BAR_TO_PA_MULTIPLIER * volume); // P = nRT / V
    }



    IPressureContainer EMPTY = new IPressureContainer() {

        @Override
        public int getMaxPressure() {
            return 0;
        }

        @Override
        public int getMinPressure() {
            return 0;
        }

        @Override
        public boolean canHandleVacuum() {
            return false;
        }

        @Override
        public int getPressure() {
            return 1;
        }


        @Override
        public void setPressure() {
        }

        @Override
        public int getVolume() {
            return 0;
        }

        @Override
        public void setVolume(int volume) {

        }

        @Override
        public FluidStack getFluidStack() {
            return null;
        }

        @Override
        public void setFluidStack(FluidStack fluidStack) {

        }

        @Override
        public int getFluidAmount() {
            return 0;
        }


    };

}
