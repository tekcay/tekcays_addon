package tekcays_addon.api.recipe;

import static tekcays_addon.gtapi.consts.TKCYAValues.GT_ID;
import static tekcays_addon.gtapi.consts.TKCYAValues.MC_ID;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.ingredients.GTRecipeInput;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;

public class RecipeRemovalHelper {

    public static final String PLANK_SAW = "_planks_saw";
    public static final String SLAB_SAW = "_slab_saw";
    public static final String MC_SLAB = "_wooden_slab";
    public static final String MC_PLANK = "_planks";

    public static void removeGtRecipeTool(String recipeName) {
        ModHandler.removeRecipeByName(new ResourceLocation(GT_ID, recipeName));
    }

    public static void removeGtRecipeTool(String recipeName, String suffix) {
        ModHandler.removeRecipeByName(new ResourceLocation(GT_ID, suffix + recipeName));
    }

    public static void removeMcRecipe(String recipeName) {
        ModHandler.removeRecipeByName(new ResourceLocation(MC_ID, recipeName));
    }

    public static void removeMcRecipe(String recipeName, String suffix) {
        ModHandler.removeRecipeByName(new ResourceLocation(MC_ID, suffix + recipeName));
    }

    public static void removeRecipeByFluidInput(RecipeMap<?> recipeMap, Material material) {
        for (Recipe recipe : recipeMap.getRecipeList()) {
            for (GTRecipeInput gtRecipeInput : recipe.getFluidInputs()) {
                if (gtRecipeInput.getInputFluidStack().getFluid() == material.getFluid()) {
                    recipeMap.removeRecipe(recipe);
                }
            }
        }
    }

    public static void removeRecipeByFluidStackInput(RecipeMap<?> recipeMap, FluidStack fluidStack) {
        for (Recipe recipe : recipeMap.getRecipeList()) {
            for (GTRecipeInput gtRecipeInput : recipe.getFluidInputs()) {
                if (gtRecipeInput.getInputFluidStack() == fluidStack) {
                    recipeMap.removeRecipe(recipe);
                }
            }
        }
    }

    public static void removeRecipeByInput(RecipeMap<?> recipeMap, OrePrefix orePrefix, Material material) {
        for (Recipe recipe : recipeMap.getRecipeList()) {
            for (GTRecipeInput gtRecipeInput : recipe.getInputs()) {
                for (ItemStack stack : gtRecipeInput.getInputStacks()) {
                    int count = stack.getCount();
                    if (stack.isItemEqual(OreDictUnifier.get(orePrefix, material, count))) {
                        recipeMap.removeRecipe(recipe);
                    }
                }
            }
        }
    }

    public static void removeRecipeByOutput(RecipeMap<?> recipeMap, OrePrefix orePrefix, Material material) {
        for (Recipe recipe : recipeMap.getRecipeList()) {
            if (recipe.getOutputs().contains(OreDictUnifier.get(orePrefix, material))) {
                recipeMap.removeRecipe(recipe);
            }
        }
    }
}
