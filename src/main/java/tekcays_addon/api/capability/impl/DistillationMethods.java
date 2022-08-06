package tekcays_addon.api.capability.impl;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.recipes.DistillationRecipes;
import tekcays_addon.api.utils.FluidWithProperties;
import tekcays_addon.common.blocks.blocks.BlockPump;

import java.util.List;
import java.util.Map;

import static tekcays_addon.api.recipes.DistillationRecipes.TKCYA_DISTILLATION_RECIPES;
import static tekcays_addon.api.utils.FluidWithProperties.FLUID_WITH_PROPERTIES;

public class DistillationMethods {

    /**
     * @return A sorted {@code Map<Integer, FluidStack>} with the {@code Integer} being the boiling point of the corresponding {@code FluidStack}.
     */

    public static void setToDistillBP(List<FluidStack> distillate, Map<Integer, FluidStack> map) {
        for (FluidStack fs : distillate) {
            for (FluidWithProperties fluidWithProperties : FLUID_WITH_PROPERTIES) {
                if (!fs.getLocalizedName().equals(fluidWithProperties.getName())) continue;
                map.put(fluidWithProperties.getBoilingPoint(), fs);
            }
        }
    }

    public static void setToDistillBP(FluidStack[] distillate, Map<Integer, FluidStack> map) {
        for (FluidStack fs : distillate) {
            for (FluidWithProperties fluidWithProperties : FLUID_WITH_PROPERTIES) {
                if (!fs.getLocalizedName().equals(fluidWithProperties.getName())) continue;
                map.put(fluidWithProperties.getBoilingPoint(), fs);
            }
        }
    }

    /**
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

    /**
     * @param unlocalizedName name of the pump machine, e.g. {@code pump_machine_lv}.
     * @return the corresponding {@code PumpType}.
     */

    public static BlockPump.PumpType getPumpTypeFromName(String unlocalizedName) {

        for (BlockPump.PumpType pumpType : BlockPump.PumpType.values()) {
            if (pumpType.getName().equals(unlocalizedName)) return pumpType;
        }
        return null;
    }

    /**
     *
     * @param unlocalizedName of the {@code FluidStack} input of the sought {@code Recipe}, e.g. {@code water}.
     * @param recipeMap the {@code RecipeMap} to look in.
     * @return the {@code Recipe}.
     */
    public static Recipe getRecipeFromFluidName(String unlocalizedName, RecipeMap recipeMap) {
        for (Recipe recipe : recipeMap.getRecipeList()) {
            //As there will always be one fluid input, .get(0) is enough
            if (recipe.getFluidInputs().get(0).getUnlocalizedName().equals(unlocalizedName)) return recipe;
        }
        return null;
    }





}
