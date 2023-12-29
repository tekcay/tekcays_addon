package tekcays_addon.loaders.recipe.removals;

import static net.minecraft.init.Blocks.FURNACE;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import gregtech.api.GTValues;
import gregtech.api.recipes.ModHandler;
import gregtech.common.metatileentities.MetaTileEntities;

public class FurnacesRemoval {

    public static void init() {
        ModHandler.removeRecipeByOutput(new ItemStack(Item.getItemFromBlock(FURNACE)));
        ModHandler.removeRecipeByOutput(MetaTileEntities.ELECTRIC_BLAST_FURNACE.getStackForm());
        ModHandler.removeRecipeByOutput(MetaTileEntities.STEAM_FURNACE_BRONZE.getStackForm());
        ModHandler.removeRecipeByOutput(MetaTileEntities.STEAM_FURNACE_STEEL.getStackForm());
        ModHandler.removeRecipeByOutput(MetaTileEntities.STEAM_OVEN.getStackForm());
        ModHandler.removeRecipeByOutput(MetaTileEntities.MULTI_FURNACE.getStackForm());

        for (int i = GTValues.LV; i < GTValues.UV; i++) {
            ModHandler.removeRecipeByOutput(MetaTileEntities.ELECTRIC_FURNACE[i].getStackForm());
        }
    }
}
