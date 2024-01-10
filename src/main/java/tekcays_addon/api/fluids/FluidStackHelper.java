package tekcays_addon.api.fluids;

import static tekcays_addon.api.material.MaterialHelper.getMaterialStacksFromString;
import static tekcays_addon.gtapi.consts.TKCYAValues.NO_FLUID;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.unification.stack.MaterialStack;
import tekcays_addon.gtapi.unification.TKCYAMaterials;

public class FluidStackHelper {

    /**
     * Adds the amount of a FluidStack {@code f2} to a FluidStack {@code f1} and returns the modified {@code f1}.
     * 
     * @param f1 the {@code FluidStack} to increase the amount.
     * @param f2 the {@code FluidStack} which amount will be added to {@code f1}.
     * @return the corresponding {@code FluidStack} with increased amount. If the two {@code FluidStack}s are not
     *         made of the same {@code Fluid}, it will return {@code f1}.
     */
    @NotNull
    public static FluidStack getFluidStackSumFusion(@NotNull FluidStack f1, @NotNull FluidStack f2) {
        if (!f1.equals(f2)) return f1;
        return new FluidStack(f1.getFluid(), f1.amount + f2.amount);
    }

    /**
     * Subtracts the amount of a {@code FluidStack f2} to a {@code FluidStack f1} and returns the modified {@code f1}.
     *
     * @param f1 the {@code FluidStack} to decrease the amount.
     * @param f2 the {@code FluidStack} which amount will be subtracted to {@code f1}.
     * @return the corresponding {@code FluidStack} with decreased amount. If the two {@code FluidStack}s are not
     *         made of the same {@code Fluid}, it will return {@code f1}.
     */
    @NotNull
    public static FluidStack getFluidStacksDifferenceFusion(@NotNull FluidStack f1, @NotNull FluidStack f2) {
        if (!f1.containsFluid(f2)) return f1;
        return new FluidStack(f1.getFluid(), f1.amount - f2.amount);
    }

    /**
     * Adds the amount of a {@code FluidStack f2} to a {@code FluidStack f1} and returns the modified {@code f1}.
     * 
     * @param f1    the {@code FluidStack} to increase the amount.
     * @param toAdd the {@code amount} which will be added to {@code f1}.
     * @return the corresponding {@code FluidStack} with increased amount.
     */
    @NotNull
    private static FluidStack addFluidStacks(@NotNull FluidStack f1, int toAdd) {
        return new FluidStack(f1.getFluid(), f1.amount + toAdd);
    }

    /**
     * Subtracts the amount of a {@code FluidStack f2} to a {@code FluidStack f1} and returns the modified {@code f1}.
     * 
     * @param f1       the {@code FluidStack} to decrease the amount.
     * @param toRemove the {@code amount} which will be subtracted to {@code f1}.
     * @return the corresponding {@code FluidStack} with decreased amount. If the subtraction gives 0 or less, it
     *         will return {@code f1}.
     */
    public static FluidStack getDecreasedAmountFluidStack(FluidStack f1, int toRemove) {
        if (f1.amount - toRemove <= 0) return f1;
        return new FluidStack(f1.getFluid(), f1.amount - toRemove);
    }

    /**
     * Increments or decrements the amount of a provided {@link FluidStack} and returns the resulted FluidStack.
     * <br>
     * If the sum of the provided FluidStack {@code f1} amount and the provided {@code amount} is not greater than 0,
     * returns the unmodified provided FluidStack {@code f1}.
     */
    @Nullable
    public static FluidStack getAmountChangedFluidStack(@Nullable FluidStack f1, int amount) {
        if (f1 == null) return null;

        if (f1.amount + amount > 0) {
            return addFluidStacks(f1, amount);
        }
        return new FluidStack(f1.getFluid(), 0);
    }

    public static NBTTagCompound getFluidStackNBT(FluidStack fluidStack) {
        NBTTagCompound nbt = new NBTTagCompound();
        fluidStack.writeToNBT(nbt);
        return nbt;
    }

    public static NBTTagCompound setFluidStackNBT(@Nullable FluidStack fluidStack) {
        return fluidStack == null ? new NBTTagCompound() : getFluidStackNBT(fluidStack);
    }

    public static int getNullableFluidStackAmount(@Nullable FluidStack fluidStack) {
        return fluidStack == null ? 0 : fluidStack.amount;
    }

    public static String getNullableFluidStackLocalizedName(@Nullable FluidStack fluidStack) {
        return fluidStack == null ? NO_FLUID : fluidStack.getLocalizedName();
    }

    @Nullable
    public static Fluid getNullableFluidFromFluidStack(@Nullable FluidStack fluidStack) {
        return fluidStack == null ? null : fluidStack.getFluid();
    }

    public static FluidStack getSmallestFluidStackByAmount(FluidStack fluidStack, int amount) {
        if (fluidStack.amount <= amount) return fluidStack;
        return new FluidStack(fluidStack.getFluid(), amount);
    }

    public static boolean isFuel(@Nullable FluidStack fluid) {
        if (fluid == null) return false;
        return fluid.isFluidEqual(new FluidStack(TKCYAMaterials.Fuel.getFluid(), 1));
    }

    /**
     * Returns the sum of the amount of each {@code FluidStack}.
     */
    public static int getFluidStackListAmount(List<FluidStack> list) {
        AtomicInteger stackSize = new AtomicInteger();
        list.forEach(stack -> stackSize.addAndGet(stack.amount));
        return stackSize.get();
    }

    public static List<FluidStack> getFluidStacksFromMaterialStacks(List<MaterialStack> list) {
        List<FluidStack> output = new ArrayList<>();
        list.forEach(ms -> output.add(ms.material.getFluid((int) ms.amount)));
        return output;
    }

    public static FluidStack getOutputFluidStackFromNBT(NBTTagCompound nbt) {
        List<MaterialStack> list = getMaterialStacksFromString(nbt.getString("fluidOutputs"));
        return getFluidStacksFromMaterialStacks(list).get(0);
    }
}
