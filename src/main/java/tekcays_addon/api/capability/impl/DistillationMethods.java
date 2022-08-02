package tekcays_addon.api.capability.impl;

import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.utils.TKCYALog;

import java.util.List;
import java.util.Map;

import static tekcays_addon.api.utils.TKCYAValues.FLUID_BP;

public class DistillationMethods {


    /**
     * @return A sorted {@code Map<Integer, FluidStack} with the {@code Integer} being the boiling point of the corresponding {@code FluidStack}.
     */
    public static void setToDistillBP(List<FluidStack> distillate, Map<Integer, FluidStack> map) {
        for (FluidStack fs : distillate) {

            TKCYALog.logger.info("Fluid : " + fs.getFluid().getUnlocalizedName());
            TKCYALog.logger.info("bp : " + FLUID_BP.get(fs.getFluid()));

            map.put(FLUID_BP.get(fs.getFluid()), fs);
        }
    }



}
