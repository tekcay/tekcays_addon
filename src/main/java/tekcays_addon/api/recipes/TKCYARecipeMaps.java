package tekcays_addon.api.recipes;

import crafttweaker.annotations.ZenRegister;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;
import gregtech.api.sound.GTSounds;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenProperty;

@ZenExpansion("mods.tkcya.recipe.RecipeMaps")
@ZenRegister
public class TKCYARecipeMaps {


    @ZenProperty
    public static final RecipeMap<SimpleRecipeBuilder> CLUSTER_MILL_RECIPES = new RecipeMap<>(
            "cluster_mill", 1, 1, 1, 1, 0, 0, 0, 0, new SimpleRecipeBuilder(), false)
            .setSound(GTSounds.MOTOR);

    @ZenProperty
    public static final RecipeMap<SimpleRecipeBuilder> ADVANCED_POLARIZER_RECIPES = new RecipeMap<>(
            "advanced_polarizer", 1, 2, 1, 1, 0, 0, 0, 0, new SimpleRecipeBuilder(), false)
            .setProgressBar(GuiTextures.PROGRESS_BAR_MAGNET, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSounds.ARC);

}
