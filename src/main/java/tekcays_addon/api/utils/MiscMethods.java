package tekcays_addon.api.utils;

import gregtech.api.items.metaitem.MetaItem;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.unification.TKCYAMaterials;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import static tekcays_addon.api.utils.TKCYAValues.ELECTRIC_PUMPS;

public class MiscMethods {

    public static boolean isFuel(@Nullable FluidStack fluid) {
        if (fluid == null) return false;
        if (fluid.isFluidEqual(new FluidStack(TKCYAMaterials.Fuel.getFluid(), 1))) return true;

        return false;
    }

    public static boolean isSameFluid(@Nullable FluidStack fluidStack, Fluid fluid) {
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



}
