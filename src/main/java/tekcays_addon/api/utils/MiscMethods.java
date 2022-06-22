package tekcays_addon.api.utils;

import gregtech.api.unification.material.Materials;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.unification.TKCYAMaterials;

import javax.annotation.Nullable;

public class MiscMethods {

    public static boolean isFuel(@Nullable FluidStack fluid) {
        if (fluid == null) return false;
        if (fluid.isFluidEqual(new FluidStack(TKCYAMaterials.Fuel.getFluid(), 1))) return true;

        return false;
    }

    public static boolean isAir(@Nullable FluidStack fluid) {
        if (fluid == null) return false;
        if (fluid.isFluidEqual(new FluidStack(Materials.Air.getFluid(), 1))) return true;

        return false;
    }



}
