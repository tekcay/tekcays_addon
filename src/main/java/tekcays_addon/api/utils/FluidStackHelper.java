package tekcays_addon.api.utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public interface FluidStackHelper {

    void setFluidStack(FluidStack fluidStack);

    FluidStack getFluidStack();

    /**
     * Adds the amount of a {@code FluidStack f2} to a {@code FluidStack f1} and returns the modified {@code f1}.
     * @param f1 the {@code FluidStack} to increase the amount.
     * @param f2 the {@code FluidStack} which amount will be added to {@code f1}.
     * @return the corresponding {@code FluidStack} with increased amount. If the two {@code FluidStack}s are not
     * made of the same {@code Fluid}, it will return {@code f1}.
     */
    default FluidStack addFluidStacks(FluidStack f1, FluidStack f2) {
        if (!f1.equals(f2)) return f1;
        return new FluidStack(f1.getFluid(), f1.amount + f2.amount);
    }

    /**
     * Adds the amount of a {@code FluidStack f2} to a {@code FluidStack f1} and returns the modified {@code f1}.
     * @param f1 the {@code FluidStack} to increase the amount.
     * @param toAdd the {@code amount} which will be added to {@code f1}.
     * @return the corresponding {@code FluidStack} with increased amount.
     */
    default FluidStack addFluidStacks(FluidStack f1, int toAdd) {
        return new FluidStack(f1.getFluid(), f1.amount + toAdd);
    }

    /**
     * Substracts the amount of a {@code FluidStack f2} to a {@code FluidStack f1} and returns the modified {@code f1}.
     * @param f1 the {@code FluidStack} to decrease the amount.
     * @param f2 the {@code FluidStack} which amount will be substracted to {@code f1}.
     * @return the corresponding {@code FluidStack} with decreased amount. If the two {@code FluidStack}s are not
     * made of the same {@code Fluid}, it will return {@code f1}.
     */
    default FluidStack substractFluidStacks(FluidStack f1, FluidStack f2) {
        if (!f1.containsFluid(f2)) return f1;
        return new FluidStack(f1.getFluid(), f1.amount - f2.amount);
    }

    /**
     * Substracts the amount of a {@code FluidStack f2} to a {@code FluidStack f1} and returns the modified {@code f1}.
     * @param f1 the {@code FluidStack} to decrease the amount.
     * @param toRemove the {@code amount} which will be substracted to {@code f1}.
     * @return the corresponding {@code FluidStack} with decreased amount. If the substraction gives 0 or less, it
     * will return {@code f1}.
     */
    default FluidStack substractFluidStacks(FluidStack f1, int toRemove) {
        if (f1.amount - toRemove <= 0) return f1;
        return new FluidStack(f1.getFluid(), f1.amount - toRemove);
    }

    default NBTTagCompound getFluidStackNBT(FluidStack fluidStack) {
        NBTTagCompound nbt = new NBTTagCompound();
        fluidStack.writeToNBT(nbt);
        return nbt;
    }

    default NBTTagCompound setFluidStackNBT() {
        return getFluidStack() == null ? new NBTTagCompound() : getFluidStackNBT(getFluidStack());
    }

    default void changeFluidStack(FluidStack fs, boolean doAdd) {
        if (doAdd) setFluidStack(addFluidStacks(getFluidStack(), fs));
        else setFluidStack(substractFluidStacks(getFluidStack(), fs));
    }

    default void changeFluidStack(int amount, boolean doAdd) {
        if (doAdd) setFluidStack(addFluidStacks(getFluidStack(), amount));
        else setFluidStack(substractFluidStacks(getFluidStack(), amount));
    }
}
