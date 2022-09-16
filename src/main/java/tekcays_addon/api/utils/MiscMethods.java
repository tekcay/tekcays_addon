package tekcays_addon.api.utils;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.recipes.ingredients.GTRecipeInput;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.stack.MaterialStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.unification.TKCYAMaterials;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tekcays_addon.api.utils.TKCYAValues.ELECTRIC_PUMPS;

public class MiscMethods {

    public static boolean isFuel(@Nullable FluidStack fluid) {
        if (fluid == null) return false;
        if (fluid.isFluidEqual(new FluidStack(TKCYAMaterials.Fuel.getFluid(), 1))) return true;

        return false;
    }

    /**
     * Checks if a {@code FluidStack} is made of a certain {@code Fluid}.
     * This can avoid certain {@code NullPointer} exceptions.
     * <br /><br />
     * @param fluidStack the {@code FluidStack} that is checked.
     * @param fluid the {@code Fluid} to compare to.
     * @return true if condition is met.
     */
    public static boolean isSameFluid(FluidStack fluidStack, Fluid fluid) {
        if (fluidStack == null) return false;
        if (fluidStack.isFluidEqual(new FluidStack(fluid, 1))) return true;

        return false;
    }



    public static Map<Integer, Integer> getPumpPressureMap() {
        Map<Integer, Integer> map = new HashMap<>();
        int tier = 0;

        for (MetaItem.MetaValueItem pump : ELECTRIC_PUMPS) {
            tier++;
            map.put(tier * 10, tier);
        }
        return map;
    }

    /**
     *
     * @param m1
     * @param m2
     * @return the amount of {@code Material} m2 in {@code Material} m1.
     */
    public static int getAmountMaterial(Material m1, Material m2) {
        for (MaterialStack ms : m1.getMaterialComponents()) {
            if (ms.material.equals(m2)) return (int) ms.amount;
        }
        return 0;
    }




}
