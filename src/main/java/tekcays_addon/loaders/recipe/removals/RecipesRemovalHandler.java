package tekcays_addon.loaders.recipe.removals;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.recipes.*;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.MetaItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.loaders.recipe.handlers.TKCYAPartsRecipeHandler;

import java.util.List;

import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.common.TKCYAConfigHolder.harderStuff;
import static tekcays_addon.common.TKCYAConfigHolder.meltingOverhaul;

public class RecipesRemovalHandler {

    public static void recipeMapRecipesRemoval(RecipeMap<?> recipeMap, List<ItemStack> itemStacks) {
        for (Recipe recipe : recipeMap.getRecipeList()) {
            itemStacks.forEach(itemStack -> removeRecipe(recipe, itemStack, recipeMap));
        }
    }
    public static void recipeMapRecipesRemoval(RecipeMap<?> recipeMap, ItemStack itemStack) {
        recipeMap.getRecipeList().forEach(recipe -> removeRecipe(recipe, itemStack, recipeMap));
    }

    private static void removeRecipe(Recipe recipe, ItemStack itemStack, RecipeMap<?> recipeMap) {
        if (recipe.getAllItemOutputs()
                .stream()
                .anyMatch(output -> output.isItemEqual(itemStack))) {
            recipeMap.removeRecipe(recipe);
        }
    }

    public static void init() {
        if(harderStuff.disableTinCircuitRecipes) TinCircuitRemoval.init();
        if (meltingOverhaul.enableMeltingOverhaul) TKCYAPartsRecipeHandler.removeExtractor();
        if (harderStuff.disableFurnacesRecipes) FurnacesRemoval.init();
    }

    public static void removeShapedTreatedWoodRecipe(){
        ModHandler.removeRecipeByOutput(OreDictUnifier.get(OrePrefix.plank, TreatedWood));

    }

    public static void removeMoldsAndUsage() {

        for (MetaItem<?>.MetaValueItem mvi : MetaItems.SHAPE_MOLDS) {
            if (mvi.isItemEqual(MetaItems.SHAPE_EMPTY.getStackForm())) continue;
            ModHandler.removeRecipeByOutput(mvi.getStackForm());
        }

        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES,
                new ItemStack[] {
                        OreDictUnifier.get(OrePrefix.dust, Wood),
                        MetaItems.SHAPE_MOLD_PLATE.getStackForm()},
                new FluidStack[] {Glue.getFluid(50)});

        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.FORMING_PRESS_RECIPES,
                new ItemStack[] {
                        OreDictUnifier.get(OrePrefix.dust, Glass),
                        MetaItems.SHAPE_MOLD_BALL.getStackForm()},
                new FluidStack[] {});

        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.FORMING_PRESS_RECIPES,
                new ItemStack[] {
                        OreDictUnifier.get(OrePrefix.dust, Glass),
                        MetaItems.SHAPE_MOLD_BLOCK.getStackForm()},
                new FluidStack[] {});
    }

}
