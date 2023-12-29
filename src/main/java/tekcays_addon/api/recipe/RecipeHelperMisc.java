package tekcays_addon.api.recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.ingredients.GTRecipeInput;
import gregtech.api.recipes.ingredients.GTRecipeItemInput;
import tekcays_addon.gtapi.utils.TKCYALog;

public class RecipeHelperMisc {

    /*
     * public static List<Recipe> getRecipesContainingInputStack(RecipeMap<?> recipeMap, ItemStack itemStack) {
     * return recipeMap.getRecipeList().stream()
     * .filter(recipe -> doesContainStack(recipe.getInputs(), itemStack))
     * .collect(Collectors.toList());
     * }
     * 
     */

    public static List<Recipe> getRecipesContainingInputStack(RecipeMap<?> recipeMap, List<ItemStack> itemStacks) {
        List<Recipe> recipes = new ArrayList<>();
        itemStacks.forEach(itemStack -> recipes.addAll(getRecipesContainingInputStack(recipeMap, itemStack)));
        return recipes;
    }

    private static boolean doesContainStack(List<GTRecipeInput> gtRecipeInputs, ItemStack itemStack) {
        return gtRecipeInputs.stream()
                .map(gtRecipeInput -> (GTRecipeItemInput) gtRecipeInput)
                .anyMatch(gtRecipeItemInput -> gtRecipeItemInput.acceptsStack(itemStack));
    }

    public static List<Recipe> getRecipesContainingInputStack(RecipeMap<?> recipeMap, ItemStack itemStack) {
        return recipeMap.getRecipeList().stream()
                .filter(recipe -> matchRecipe(recipe, itemStack))
                .collect(Collectors.toList());
    }

    public static List<Recipe> getRecipesNotContainingInputStack(RecipeMap<?> recipeMap, ItemStack itemStack) {
        return recipeMap.getRecipeList().stream()
                .filter(recipe -> !matchRecipe(recipe, itemStack))
                .collect(Collectors.toList());
    }

    public static List<Recipe> getRecipesNotContainingGTRecipeInput(RecipeMap<?> recipeMap,
                                                                    List<GTRecipeInput> gtRecipeItemInputList) {
        return recipeMap.getRecipeList().stream()
                .filter(recipe -> !matchRecipes(recipe, gtRecipeItemInputList))
                .collect(Collectors.toList());
    }

    public static void transferRecipes(@NotNull RecipeMap<?> originRecipeMap,
                                       @NotNull RecipeMap<?> destinationRecipeMap, List<GTRecipeInput> gtRecipeInputs) {
        getRecipesNotContainingGTRecipeInput(originRecipeMap, gtRecipeInputs)
                .forEach(recipe -> generateAndAddRecipe(destinationRecipeMap, recipe));
    }

    private static boolean matchRecipes(Recipe recipe, List<GTRecipeInput> gtRecipeItemInputList) {
        TKCYALog.logger.info("came here");
        return recipe.getInputs().stream()
                .distinct()
                .anyMatch(gtRecipeItemInputList::contains);
    }

    private static boolean matchRecipe(Recipe recipe, List<ItemStack> itemStacks) {
        return recipe.getInputs().stream()
                .map(GTRecipeInput::getInputStacks)
                .map(Arrays::asList)
                .flatMap(Collection::stream)
                .distinct()
                .anyMatch(stack -> itemStacks.stream().anyMatch(itemStack -> itemStack.isItemEqual(stack)));
    }

    private static boolean matchRecipe(Recipe recipe, ItemStack itemStack) {
        return recipe.getInputs().stream()
                .map(GTRecipeInput::getInputStacks)
                .map(Arrays::asList)
                .flatMap(Collection::stream)
                .anyMatch(stack -> stack.isItemEqual(itemStack));
    }

    private static void generateAndAddRecipe(RecipeMap<?> recipeMap, Recipe foundRecipe) {
        recipeMap.recipeBuilder()
                .inputs(foundRecipe.getInputs().get(0))
                .inputs(foundRecipe.getInputs().get(1))
                .EUt(foundRecipe.getEUt())
                .duration(foundRecipe.getDuration())
                .outputs(foundRecipe.getOutputs())
                .buildAndRegister();
        TKCYALog.logger.info("did generate");
    }
}
