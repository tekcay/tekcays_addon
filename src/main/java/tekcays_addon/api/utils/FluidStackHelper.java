package tekcays_addon.api.utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

import static tekcays_addon.api.utils.TKCYAValues.NO_FLUID;

public interface FluidStackHelper {

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
     * Subtracts the amount of a {@code FluidStack f2} to a {@code FluidStack f1} and returns the modified {@code f1}.
     * @param f1 the {@code FluidStack} to decrease the amount.
     * @param f2 the {@code FluidStack} which amount will be substracted to {@code f1}.
     * @return the corresponding {@code FluidStack} with decreased amount. If the two {@code FluidStack}s are not
     * made of the same {@code Fluid}, it will return {@code f1}.
     */
    default FluidStack subtractFluidStacks(FluidStack f1, FluidStack f2) {
        if (!f1.containsFluid(f2)) return f1;
        return new FluidStack(f1.getFluid(), f1.amount - f2.amount);
    }

    /**
     * Subtracts the amount of a {@code FluidStack f2} to a {@code FluidStack f1} and returns the modified {@code f1}.
     * @param f1 the {@code FluidStack} to decrease the amount.
     * @param toRemove the {@code amount} which will be substracted to {@code f1}.
     * @return the corresponding {@code FluidStack} with decreased amount. If the substraction gives 0 or less, it
     * will return {@code f1}.
     */
    default FluidStack subtractFluidStacks(FluidStack f1, int toRemove) {
        if (f1.amount - toRemove <= 0) return f1;
        return new FluidStack(f1.getFluid(), f1.amount - toRemove);
    }

    /**
     * Changes the amount of a {@link FluidStack} by adding a provided {@code amount}.
     * @param f1 the {@link } FluidStack} to modify the amount.
     * @param amount the {@code amount} which will be added to {@code f1}.
     * @return how much the amount of the {@link FluidStack} has changed.
     */
    @Nullable
    default int changeFluidStack(@Nullable FluidStack f1, int amount) {
        int changed = 0;

        if (f1 == null) return changed;

        if (f1.amount + amount > 0) {
            TKCYALog.logger.info("f1 = {} L, amount = {} L", f1.amount, amount);
            f1 = addFluidStacks(f1, amount);
            TKCYALog.logger.info("post addFluidStacks, f1 = {}, {} L", f1.getLocalizedName(), f1.amount);
            return amount;
        } else {
            changed = f1.amount;
            f1 = new FluidStack(f1.getFluid(), 0);
            return changed;
        }
    }

    @Nullable
    default FluidStack getChangedFluidStack(@Nullable FluidStack f1, int amount) {
        int changed = 0;

        if (f1 == null) return null;

        if (f1.amount + amount > 0) {
            return addFluidStacks(f1, amount);
        }

        return new FluidStack(f1.getFluid(), 0);

    }

    default NBTTagCompound getFluidStackNBT(FluidStack fluidStack) {
        NBTTagCompound nbt = new NBTTagCompound();
        fluidStack.writeToNBT(nbt);
        return nbt;
    }

    default NBTTagCompound setFluidStackNBT(FluidStack fluidStack) {
        return fluidStack == null ? new NBTTagCompound() : getFluidStackNBT(fluidStack);
    }

    default int getNullableFluidStackAmount(@Nullable FluidStack fluidStack) {
        return fluidStack == null ? 0 : fluidStack.amount;
    }

    default String getNullableFluidStackLocalizedName(@Nullable FluidStack fluidStack) {
        return fluidStack == null ? NO_FLUID : fluidStack.getLocalizedName();
    }

    default FluidStack getSmallestFluidStackByAmount(FluidStack fluidStack, int amount) {
        if (fluidStack.amount <= amount) return fluidStack;
        return new FluidStack(fluidStack.getFluid(), amount);
    }
}
