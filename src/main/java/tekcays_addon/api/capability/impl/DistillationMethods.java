package tekcays_addon.api.capability.impl;

import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.recipes.DistillationRecipes;
import tekcays_addon.api.utils.FluidWithProperties;

import java.util.Map;

import static tekcays_addon.api.recipes.DistillationRecipes.TKCYA_DISTILLATION_RECIPES;
import static tekcays_addon.api.utils.FluidWithProperties.FLUID_WITH_PROPERTIES;

public class DistillationMethods {

    /**
     * @return A sorted {@code Map<Integer, FluidStack>} with the {@code Integer} being the boiling point of the corresponding {@code FluidStack}.
     */
    public static void setToDistillBP(FluidStack[] distillate, Map<Integer, FluidStack> map) {
        map.clear();
        for (FluidStack fs : distillate) {
            for (FluidWithProperties fluidWithProperties : FLUID_WITH_PROPERTIES) {
                if (!fs.getLocalizedName().equals(fluidWithProperties.getName())) continue;
                map.put(fluidWithProperties.getBoilingPoint(), fs);
            }
        }
    }

    /**
     *
     * @param fluid the {@code LocalizedName} of the {@code Fluid} that was distilled.
     * @return the previous {@code DistillationRecipes}.
     */

    public static DistillationRecipes getDistillationRecipe(String fluid) {
        for (DistillationRecipes distillationRecipes : TKCYA_DISTILLATION_RECIPES) {
            if (!distillationRecipes.getFluidStackInput().getLocalizedName().equals(fluid)) continue;
            return distillationRecipes;
        }
        return null;
    }





}
