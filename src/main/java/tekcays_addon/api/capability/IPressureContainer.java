package tekcays_addon.api.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import static gregtech.api.unification.material.Materials.Air;
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
        return getFluidStack().amount;
    }

    default void changeFluidAmount(int amount) {
        setFluidStack(new FluidStack(getFluidStack().getFluid(), getFluidAmount() + amount));
    }

    /**
     * @param amount
     * @return {@code false} if the {@code amount} is negative and would give a {@code fluidAmount} lower than 1 (MINIMUM_FLUID_STACK_AMOUNT).
     */
    default boolean canChangeFluidAmount(int amount) {
        return getFluidAmount() + amount <= MINIMUM_FLUID_STACK_AMOUNT;
    }

    default void leaksContainer(int amount) {
        if (!canChangeFluidAmount(amount)) return;
        changeFluidAmount(amount);
    }

    default NBTTagCompound setFluidStackNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        return getFluidStack().writeToNBT(nbt);
    }

    default boolean isEmpty() {
        return getFluidAmount() == MINIMUM_FLUID_STACK_AMOUNT;
    }

    default boolean canLeakMore(int leak) {
        return getFluidAmount() - leak <= MINIMUM_FLUID_STACK_AMOUNT;
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

    /**
     * Gets the {@code FluidStack} with the amount at standard conditions (298 K, 1013 hPa).
     * @return the {@code FluidStack} with changed amount.
     */
    default FluidStack getFluidStackStandard(FluidStack fs, int temperature) {
        return new FluidStack(fs.getFluid(), calculateFluidAmount(ATMOSPHERIC_PRESSURE, temperature, getVolume()));
    }

    /**
     * Gets a {@code FluidStack} of Air at standard conditions (298 K, 1013 hPa).
     * @return {@code FluidStack} of Air.
     */
    default FluidStack getDefaultFluidStack() {
        return getFluidStackStandard(Air.getFluid(MINIMUM_FLUID_STACK_AMOUNT), ROOM_TEMPERATURE);
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
