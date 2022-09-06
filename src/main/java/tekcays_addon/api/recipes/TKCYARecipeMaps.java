package tekcays_addon.api.recipes;

import crafttweaker.annotations.ZenRegister;
import gregicality.science.api.recipes.builders.TemperaturePressureRecipeBuilder;
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
    public static final RecipeMap<TKCYATemperatureRecipeBuilder> MELTER_RECIPES = new RecipeMap<>(
            "primitive_melter", 1, 1, 0, 0, 0, 0, 1, 1, new TKCYATemperatureRecipeBuilder(), false)
            .setSound(GTSounds.FURNACE);

    @ZenProperty
    public static final RecipeMap<PrimitiveRecipeBuilder> FERMENTATION_RECIPES = new RecipeMap<>(
            "fermentation", 1, 1, 0, 1, 0, 1, 1, 1, new PrimitiveRecipeBuilder(), false);

    @ZenProperty
    public static final RecipeMap<PrimitiveRecipeBuilder> ALLOYING_CRUCIBLE_RECIPES = new RecipeMap<>(
            "alloying_crucible", 0, 0, 0, 0, 1, 8, 1, 1, new PrimitiveRecipeBuilder(), false)
            .setSound(GTSounds.CHEMICAL_REACTOR);
    @ZenProperty
    public static final RecipeMap<PrimitiveRecipeBuilder> CASTING_TABLE_RECIPES = new RecipeMap<>(
            "casting_table", 1, 1, 1, 1, 1, 2, 0, 1, new PrimitiveRecipeBuilder(), false)
            .setSound(GTSounds.CHEMICAL_REACTOR);

    @ZenProperty
    public static final RecipeMap<SimpleRecipeBuilder> ELECTRIC_CASTING_RECIPES = new RecipeMap<>(
            "electric_casting_table", 1, 2, 1, 1, 1, 2, 0, 1, new SimpleRecipeBuilder(), false)
            .setSound(GTSounds.MIXER);

    @ZenProperty
    public static final RecipeMap<TemperaturePressureRecipeBuilder> CONVERTING_RECIPES = new RecipeMap<>(
            "converting", 0, 2, 0, 2, 1, 4, 1, 2, new TemperaturePressureRecipeBuilder(), false)
            .setSound(GTSounds.COOLING);

    @ZenProperty
    public static final RecipeMap<TKCYATemperatureRecipeBuilder> BLASTING_RECIPES = new RecipeMap<>(
            "blasting", 1, 1, 0, 0, 0, 1, 1, 2, new TKCYATemperatureRecipeBuilder(), false)
            .setSound(GTSounds.FURNACE);

    @ZenProperty
    public static final RecipeMap<PrimitiveRecipeBuilder> PRIMITIVE_BATH = new RecipeMap<>(
            "primitive_bath", 1, 1, 1, 1, 1, 1, 0, 0, new PrimitiveRecipeBuilder(), false)
            .setSound(GTSounds.CHEMICAL_REACTOR);

    @ZenProperty
    public static final RecipeMap<PrimitiveRecipeBuilder> CRYSTALLIZATION = new RecipeMap<>(
            "crystallization", 0, 1, 1, 1, 1, 3, 0, 0, new PrimitiveRecipeBuilder(), false);


    @ZenProperty
    public static final RecipeMap<SimpleRecipeBuilder> ELECTROLYSIS = new RecipeMap<>(
            "electrolysis", 0, 4, 0, 1, 1, 3, 0, 3, new SimpleRecipeBuilder(), false)
            .setSound(GTSounds.ELECTROLYZER);

    @ZenProperty
    public static final RecipeMap<PrimitiveRecipeBuilder> FILTRATION = new RecipeMap<>(
            "filtration", 1, 1, 1, 1, 1, 1, 1, 1, new PrimitiveRecipeBuilder(), false);

    @ZenProperty
    public static final RecipeMap<TemperaturePressureRecipeBuilder> PRESSURE_CRACKING = new RecipeMap<>(
            "pressure_cracking", 1, 2, 0, 0, 1, 2, 1, 2, new TemperaturePressureRecipeBuilder(), false)
            .setSound(GTSounds.COMPRESSOR);

    @ZenProperty
    public static final RecipeMap<TemperaturePressureRecipeBuilder> ROASTING = new RecipeMap<>(
            "roasting", 1, 2, 0, 4, 1, 1, 0, 2, new TemperaturePressureRecipeBuilder(), false)
            .setSound(GTSounds.FURNACE);


}
