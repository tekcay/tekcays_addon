package tekcays_addon.api.capability.impl;

import gregtech.api.recipes.CountableIngredient;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import tekcays_addon.api.recipes.DistillationRecipes;
import tekcays_addon.api.utils.FluidWithProperties;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockPump;
import tekcays_addon.common.metatileentities.multi.MetaTileEntityBatchDistillationTower;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static tekcays_addon.common.metatileentities.multi.MetaTileEntityBatchDistillationTower.bp;
import static tekcays_addon.api.recipes.DistillationRecipes.TKCYA_DISTILLATION_RECIPES;
import static tekcays_addon.api.recipes.TKCYARecipeMaps.DISTILLATION;
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

    public static BlockPump.PumpType getPumpTypeFromIngredient(CountableIngredient countableIngredient) {

        for (BlockPump.PumpType pumpType : BlockPump.PumpType.values()) {
            if (CountableIngredient.from(TKCYAMetaBlocks.PUMP_MACHINE.getItemVariant(pumpType)).equals(countableIngredient)) return pumpType;
        }
        return null;
    }

    /**
     *
     * @param unlocalizedName of the {@code FluidStack} input of the sought {@code Recipe}, e.g. {@code water}.
     * @return the {@code Recipe}.
     */
    public static Recipe getRecipeFromFluidName(String unlocalizedName) {

        for (Recipe recipe : DISTILLATION.getRecipeList()) {
            //As there will always be one fluid input, .get(0) is enough
            if (recipe.getFluidInputs().get(0).getUnlocalizedName().equals(unlocalizedName)) return recipe;
        }
        return null;
    }

    public static boolean hasCircuit1(IItemHandlerModifiable iItemHandlerModifiable) {
        for (int i = 0;  i < iItemHandlerModifiable.getSlots(); i++) {
            ItemStack input = iItemHandlerModifiable.getStackInSlot(i);
            if (input.isItemEqual((IntCircuitIngredient.getIntegratedCircuit(1)))) return true;
        }
        return false;
    }


    public static int getSeparationFactor(int height) {
        return height;
    }

    public static void setBp(Map<Integer, FluidStack> map, int index) {
        bp = new ArrayList<>(map.keySet()).get(index);
    }





}
