package tekcays_addon.api.recipes;

import crafttweaker.annotations.ZenRegister;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.PrimitiveRecipeBuilder;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;
import gregtech.api.sound.GTSounds;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenProperty;
import tekcays_addon.api.recipes.builders.*;



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

    @ZenProperty
    public static final RecipeMap<PrimitiveMelterRecipeBuilder> PRIMITIVE_MELTER_RECIPES = new RecipeMap<>(
            "primitive_melter", 1, 2, 0, 1, 0, 1, 1, 1, new PrimitiveMelterRecipeBuilder(), false)
            .setSound(GTSounds.FURNACE);

    @ZenProperty
    public static final RecipeMap<ElectricMelterRecipeBuilder> ELECTRIC_MELTER_RECIPES = new RecipeMap<>(
            "electric_melter", 1, 4, 0, 1, 0, 1, 1, 2, new ElectricMelterRecipeBuilder(), false)
            .setSound(GTSounds.FURNACE);

    @ZenProperty
    public static final RecipeMap<PrimitiveRecipeBuilder> ALLOYING_CRUCIBLE_RECIPES = new RecipeMap<>(
            "alloying_crucible", 0, 1, 0, 0, 0, 8, 1, 1, new PrimitiveRecipeBuilder(), false)
            .setSound(GTSounds.CHEMICAL_REACTOR);


}
