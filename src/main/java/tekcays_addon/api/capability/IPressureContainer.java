package tekcays_addon.api.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.utils.IFluidStack;

import static tekcays_addon.api.utils.TKCYAValues.*;

public interface IPressureContainer extends IVacuumContainer {

    /**
     * @return all the {@code FluidStack}s pressurized in the {@code IPressureContainer}
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
        return getFluidStack() == null ? 0 : getFluidStack().amount;
    }

    default int getTotalFluidAmount() {
        return getFluidAmount() + getAirAmount();
    }

    default void changeFluidStack(FluidStack fs, boolean doAdd) {
        if (doAdd) setFluidStack(IFluidStack.addFluidStacks(getFluidStack(), fs));
        else setFluidStack(IFluidStack.substractFluidStacks(getFluidStack(), fs));
    }

    default void changeFluidStack(int amount, boolean doAdd) {
        if (doAdd) setFluidStack(IFluidStack.addFluidStacks(getFluidStack(), amount));
        else setFluidStack(IFluidStack.substractFluidStacks(getFluidStack(), amount));
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

    @Override
    default NBTTagCompound setFluidStackNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        getFluidStack().writeToNBT(nbt);
        getAirFluidStack().writeToNBT(nbt);
        return nbt;
    }


}
