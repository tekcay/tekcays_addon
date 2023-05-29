package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.recipes.ModHandler;
import net.minecraft.init.Items;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockDirt;
import tekcays_addon.common.metatileentities.TKCYAMetaTileEntities;

import static tekcays_addon.gtapi.recipes.TKCYARecipeMaps.PRIMITIVE_FURNACE;

public class PrimitiveFurnaceHandler {

    public static void init() {

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

        ModHandler.addShapelessRecipe("neutralized_dirt", TKCYAMetaBlocks.BLOCK_DIRT.getItemVariant(BlockDirt.DirtType.DIRT),
                "dirt");

        ModHandler.addShapelessRecipe("primitive_furnace", TKCYAMetaTileEntities.PRIMITIVE_FURNACE.getStackForm(),
                "dirt", "dirt", "dirt", "dirt");
    }

}
