package tekcays_addon.api.capability.impl;

import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static tekcays_addon.api.utils.TKCYAValues.FLUID_BP;

public class DistillationMethods {

    public static int bp, toFill;


    /**
     * @return A sorted {@code Map<Integer, FluidStack} with the {@code Integer} being the boiling point of the corresponding {@code FluidStack}.
     */
    public static void setToDistillBP(List<FluidStack> distillate, Map<Integer, FluidStack> map) {
        for (FluidStack fs : distillate) {
            map.put(FLUID_BP.get(fs.getFluid()), fs);
        }
    }

    public static void setBp(Map<Integer, FluidStack> map) {
        bp = new ArrayList<>(map.keySet()).get(0);
    }

    public static void setToFill(Map<Integer, FluidStack> map) {
        toFill = map.get(bp).amount;
    }



}
