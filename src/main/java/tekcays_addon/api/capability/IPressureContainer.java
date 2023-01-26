package tekcays_addon.api.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import static tekcays_addon.api.utils.TKCYAValues.*;

public interface IPressureContainer extends IVacuumContainer {

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
     * Set the {@code pressure} in {@code Pa}
     */
    void setPressure();

    /**
     * Set the {@code pressure} in {@code Pa} with a temperature input
     */
    void setPressure(int temperature);

    /**
     * Gets the amount of the {@code FluidStack} in the {@code IPressureContainer}
     * @return 0 if the {@code FluidStack} is {@code null}
     */
    default int getFluidAmount() {
        return getFluidStack() == null ? 0 : getFluidStack().amount;
    }

    default int getTotalFluidAmount() {
        return getFluidAmount() + getAirAmount();
    }

    default void changeFluidStack(FluidStack fs, boolean doAdd) {
        if (doAdd) setFluidStack(addFluidStacks(getFluidStack(), fs));
        else setFluidStack(substractFluidStacks(getFluidStack(), fs));
    }

    default void changeFluidStack(int amount, boolean doAdd) {
        if (doAdd) setFluidStack(addFluidStacks(getFluidStack(), amount));
        else setFluidStack(substractFluidStacks(getFluidStack(), amount));
    }


    /**
     * @param amount
     * @return {@code false} if the {@code amount} is negative and would give a {@code fluidAmount} lower than 1 (MINIMUM_FLUID_STACK_AMOUNT).
     */
    default boolean canChangeFluidAmount(int amount) {
        return getFluidAmount() + amount >= MINIMUM_FLUID_STACK_AMOUNT;
    }

    @Override
    default void leaksContainer(int amount) {
        if (!canChangeFluidAmount(amount / 2)) return;
        changeAirFluidStack(amount / 2, true);
        changeFluidStack(amount / 2, false);
    }

    default NBTTagCompound setFluidStackNBT() {
        return getFluidStack() == null ? new NBTTagCompound() : getFluidStackNBT(getFluidStack());
    }

}
