package tekcays_addon.api.capability;

import gregtech.api.unification.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.utils.IFluidStack;
import tekcays_addon.api.utils.IPressureFormatting;

import static gregtech.api.unification.material.Materials.Air;
import static tekcays_addon.api.utils.TKCYAValues.*;
import static tekcays_addon.api.utils.TKCYAValues.ROOM_TEMPERATURE;

public interface IVacuumContainer extends IPressureFormatting {

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

    /**
     * @return the {@code FluidStack} of Air pressurized in the {@code IPressureContainer}
     */
    FluidStack getAirFluidStack();

    /**
     * Sets the {@code FluidStack} of Air pressurized in the {@code IPressureContainer}
     * @param fluidStack
     */
    void setAirFluidStack(FluidStack fluidStack);

    /**
     * Gets the amount of the {@code FluidStack} in the {@code IPressureContainer}
     * @return the amount of Air {@code FluidStack}.
     */
    default int getAirAmount() {
        return getAirFluidStack().amount;
    }

    default void changeAirFluidStack(int amount, boolean doAdd) {
        if (doAdd) setAirFluidStack(IFluidStack.addFluidStacks(getAirFluidStack(), amount));
        else setAirFluidStack(IFluidStack.substractFluidStacks(getAirFluidStack(), amount));
    }

    default void leaksContainer(int amount) {
        changeAirFluidStack(amount, true);
    }

    default NBTTagCompound setFluidStackNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        getAirFluidStack().writeToNBT(nbt);
        return nbt;
    }

    boolean canLeakMore(int leak);

    default void initializeAirFluidStack() {
        setAirFluidStack(getAirFluidStackStandard());
    }

    /**
     * Calculates the amount of substance which transcribes as an amount of a {@code FluidStack} following the ideal gas law.
     * @param pressure in {@code Pa}.
     * @param temperature in {@code K}.
     * @param volume in {@code m3}.
     * @return the corresponding {@code fluidAmount}.
     */
    default int calculateFluidAmount(int pressure, int temperature, int volume) {
        return (int) ((pressure * volume) / (PERFECT_GAS_CONSTANT * temperature) ); // n = PV / RT
    }

    /**
     * Calculates the pressure following the ideal gas law.
     * @param fluidAmount the amount of substance, transcribed as the amount of a {@code FluidStack}.
     * @param temperature in {@code K}.
     * @param volume in {@code m3}.
     * @return the corresponding pressure in {@code Pa}
     */
    default int calculatePressure(int fluidAmount, int temperature, int volume) {
        return (int) ((fluidAmount * PERFECT_GAS_CONSTANT * temperature) / volume); // P = nRT / V
    }

    /**
     * Gets the {@code FluidStack} with the amount at standard conditions (1013 hPa).
     * @return the {@code FluidStack} with changed amount.
     */
    default FluidStack getFluidStackStandard(FluidStack fs, int temperature) {
        return new FluidStack(fs.getFluid(), calculateFluidAmount(ATMOSPHERIC_PRESSURE, temperature, getVolume()));
    }

    /**
     * Gets the {@code FluidStack} with the amount at standard conditions (1013 hPa).
     * @param material
     * @param temperature in {@code K}.
     * @return
     */
    default FluidStack getFluidStackStandard(Material material, int temperature) {
        return material.getFluid(calculateFluidAmount(ATMOSPHERIC_PRESSURE, temperature, getVolume()));
    }

    /**
     * Gets the {@code FluidStack} with the amount at standard conditions (298 K, 1013 hPa).
     * @param material
     * @return
     */
    default FluidStack getFluidStackStandard(Material material) {
        return material.getFluid(calculateFluidAmount(ATMOSPHERIC_PRESSURE, ROOM_TEMPERATURE, getVolume()));
    }

    /**
     * Gets a {@code FluidStack} of Air at standard conditions (298 K, 1013 hPa).
     * @return {@code FluidStack} of Air.
     */
    default FluidStack getAirFluidStackStandard() {
        return getFluidStackStandard(Air.getFluid(MINIMUM_FLUID_STACK_AMOUNT), ROOM_TEMPERATURE);
    }



    IVacuumContainer EMPTY = new IVacuumContainer() {

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
            return ATMOSPHERIC_PRESSURE;
        }

        @Override
        public void setPressure() {
        }

        @Override
        public void setPressure(int temperature) {

        }

        @Override
        public int getVolume() {
            return 1;
        }

        @Override
        public void setVolume(int volume) {

        }

        @Override
        public FluidStack getAirFluidStack() {
            return Air.getFluid(calculateFluidAmount(ATMOSPHERIC_PRESSURE, ROOM_TEMPERATURE, 1));
        }

        @Override
        public void setAirFluidStack(FluidStack fluidStack) {

        }

        @Override
        public boolean canLeakMore(int leak) {
            return false;
        }

    };


}