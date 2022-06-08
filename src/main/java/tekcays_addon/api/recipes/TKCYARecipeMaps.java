package tekcays_addon.api.recipes;

import crafttweaker.annotations.ZenRegister;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.recipes.RecipeMap;
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
            "primitive_melter", 1, 4, 0, 1, 0, 1, 1, 2, new ElectricMelterRecipeBuilder(), false)
            .setSound(GTSounds.FURNACE);


    /*
    @ZenProperty
    public static final ElectricMelterRecipeMap<ElectricMelterRecipeBuilder> ELECTRIC_MELTER_RECIPES =
            (ElectricMelterRecipeMap<ElectricMelterRecipeBuilder>) new ElectricMelterRecipeMap<>("electric_melter", new ElectricMelterRecipeBuilder())
            .setSound(GTSounds.FURNACE)
            .setSlotOverlay(false, false, true, GuiTextures.FURNACE_OVERLAY_1)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL);



    @ZenProperty
    public static final RecipeMap<PrimitiveMelterRecipeBuilder> PRIMITIVE_MELTER_RECIPES = new RecipeMap<>(
            "primitive_melter", 1, 2, 0, 1, 0, 0, 0, 0, new PrimitiveMelterRecipeBuilder(), false)
            .setSound(GTSounds.FURNACE)
            .onRecipeBuild(recipeBuilder -> {
                if (((PrimitiveMelterRecipeBuilder) recipeBuilder).getTemperature() != -1) {
                    ELECTRIC_MELTER_RECIPES.recipeBuilder().setTemp(((PrimitiveMelterRecipeBuilder) recipeBuilder).getTemperature())
                            .duration(recipeBuilder.getDuration() / 4)
                            .inputs(recipeBuilder.getInputs().get(0)) // We don't need charcoal.
                            .outputs(recipeBuilder.getOutputs())
                            .buildAndRegister();
                }
            });

     */

}
