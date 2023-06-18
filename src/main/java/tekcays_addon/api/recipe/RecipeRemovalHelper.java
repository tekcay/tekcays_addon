package tekcays_addon.api.recipe;

import gregtech.api.recipes.ModHandler;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

import static tekcays_addon.gtapi.consts.TKCYAValues.GT_ID;
import static tekcays_addon.gtapi.consts.TKCYAValues.MC_ID;

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
}
