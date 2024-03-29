package tekcays_addon.loaders.recipe.handlers;

import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.unification.ore.StoneTypes.*;
import static tekcays_addon.gtapi.recipes.TKCYARecipeMaps.PRIMITIVE_FURNACE;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import gregtech.api.recipes.ModHandler;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockDirt;
import tekcays_addon.common.metatileentities.TKCYAMetaTileEntities;

public class PrimitiveFurnaceHandler {

    public static void init() {
        additions();
        removals();
    }

    private static void additions() {
        PRIMITIVE_FURNACE.recipeBuilder()
                .input(Items.BEEF)
                .output(Items.COOKED_BEEF)
                .duration(160)
                .buildAndRegister();

        PRIMITIVE_FURNACE.recipeBuilder()
                .input(Items.PORKCHOP)
                .output(Items.COOKED_PORKCHOP)
                .duration(160)
                .buildAndRegister();

        PRIMITIVE_FURNACE.recipeBuilder()
                .input(Items.FISH)
                .output(Items.COOKED_FISH)
                .duration(160)
                .buildAndRegister();

        ModHandler.addShapelessRecipe("neutralized_dirt",
                TKCYAMetaBlocks.BLOCK_DIRT.getItemVariant(BlockDirt.DirtType.DIRT),
                "dirt");

        ModHandler.addShapelessRecipe("primitive_furnace", TKCYAMetaTileEntities.PRIMITIVE_FURNACE.getStackForm(),
                "dirt", "dirt", "dirt", "dirt");
    }

    private static void removals() {
        ModHandler.removeRecipeByOutput(new ItemStack(Blocks.FURNACE));

        ASSEMBLER_RECIPES.getRecipeList()
                .stream()
                .filter(recipe -> recipe.getAllItemOutputs().get(0).isItemEqual(new ItemStack(Blocks.FURNACE)))
                .findFirst()
                .ifPresent(ASSEMBLER_RECIPES::removeRecipe);
    }
}
