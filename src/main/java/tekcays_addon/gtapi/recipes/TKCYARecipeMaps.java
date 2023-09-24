package tekcays_addon.gtapi.recipes;

import crafttweaker.annotations.ZenRegister;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.PrimitiveRecipeBuilder;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;
import gregtech.core.sound.GTSoundEvents;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenProperty;
import tekcays_addon.gtapi.recipes.builders.*;
import tekcays_addon.gtapi.recipes.machines.TopStaggeredFluidsRecipeMap;
import tekcays_addon.gtapi.recipes.machines.TopStaggeredRecipeMap;


@ZenExpansion("mods.tkcya.recipe.RecipeMaps")
@ZenRegister
public class TKCYARecipeMaps {


    @ZenProperty
    public static final RecipeMap<SimpleRecipeBuilder> CLUSTER_MILL_RECIPES = new RecipeMap<>(
            "cluster_mill", 1, 1, 0, 0, new SimpleRecipeBuilder(), false)
            .setSound(GTSoundEvents.MOTOR);

    @ZenProperty
    public static final RecipeMap<SimpleRecipeBuilder> ADVANCED_POLARIZER_RECIPES = new RecipeMap<>(
            "advanced_polarizer", 2, 1, 0, 0, new SimpleRecipeBuilder(), false)
            .setProgressBar(GuiTextures.PROGRESS_BAR_MAGNET, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.ARC);

    @ZenProperty
    public static final RecipeMap<NoEnergyTemperatureRecipeBuilder> MELTER_RECIPES = new RecipeMap<>(
            "primitive_melter", 1, 0, 0, 1, new NoEnergyTemperatureRecipeBuilder(), false)
            .setSound(GTSoundEvents.FURNACE);

    @ZenProperty
    public static final RecipeMap<PrimitiveRecipeBuilder> FERMENTATION_RECIPES = new RecipeMap<>(
            "fermentation", 1, 1, 1, 1, new PrimitiveRecipeBuilder(), false);

    @ZenProperty
    public static final RecipeMap<PrimitiveRecipeBuilder> ALLOYING_CRUCIBLE_RECIPES = new RecipeMap<>(
            "alloying_crucible", 0, 0, 8, 1, new PrimitiveRecipeBuilder(), false)
            .setSound(GTSoundEvents.CHEMICAL_REACTOR);
    @ZenProperty
    public static final RecipeMap<PrimitiveRecipeBuilder> CASTING_TABLE_RECIPES = new RecipeMap<>(
            "casting_table", 1, 1, 2, 1, new PrimitiveRecipeBuilder(), false)
            .setSound(GTSoundEvents.CHEMICAL_REACTOR);

    @ZenProperty
    public static final RecipeMap<SimpleRecipeBuilder> ELECTRIC_CASTING_RECIPES = new RecipeMap<>(
            "electric_casting_table", 2, 1, 2, 1, new SimpleRecipeBuilder(), false)
            .setSound(GTSoundEvents.MIXER);


    @ZenProperty
    public static final RecipeMap<TemperaturePressureIntervalRecipeBuilder> CONVERTING_RECIPES = new TopStaggeredRecipeMap<>(
            "converting", 2, 2, 3, 2, new TemperaturePressureIntervalRecipeBuilder(), false)
            .setSound(GTSoundEvents.COOLING);


    @ZenProperty
    public static final RecipeMap<NoEnergyTemperaturePressureIntervalRecipeBuilder> PRIMITIVE_CONVERTING_RECIPES = new TopStaggeredRecipeMap<>(
            "primitive_converting", 1, 0, 3, 2, new NoEnergyTemperaturePressureIntervalRecipeBuilder(), false)
            .setSound(GTSoundEvents.COOLING);

    @ZenProperty
    public static final RecipeMap<NoEnergyTemperaturePressureIntervalRecipeBuilder> BLASTING_RECIPES = new TopStaggeredRecipeMap<>(
            "blasting", 3, 0, 0, 2, new NoEnergyTemperaturePressureIntervalRecipeBuilder(), false)
            .setSound(GTSoundEvents.FURNACE);

    @ZenProperty
    public static final RecipeMap<NoEnergyTemperaturePressureIntervalRecipeBuilder> ADVANCED_BLAST_FURNACE_RECIPES = new TopStaggeredRecipeMap<>(
            "advanced_blasting", 4, 3, 3, 2, new NoEnergyTemperaturePressureIntervalRecipeBuilder(), false)
            .setSound(GTSoundEvents.FURNACE);

    @ZenProperty
    public static final RecipeMap<PrimitiveRecipeBuilder> PRIMITIVE_BATH = new RecipeMap<>(
            "primitive_bath", 1, 1, 1, 0, new PrimitiveRecipeBuilder(), false)
            .setSound(GTSoundEvents.CHEMICAL_REACTOR);

    @ZenProperty
    public static final RecipeMap<PrimitiveRecipeBuilder> CRYSTALLIZATION = new RecipeMap<>(
            "crystallization", 1, 1, 3, 0, new PrimitiveRecipeBuilder(), false);

    @ZenProperty
    public static final RecipeMap<SimpleRecipeBuilder> ELECTROLYSIS = new RecipeMap<>(
            "electrolysis", 5, 2, 3, 3, new SimpleRecipeBuilder(), false)
            .setSound(GTSoundEvents.ELECTROLYZER);

    @ZenProperty
    public static final RecipeMap<PrimitiveRecipeBuilder> FILTRATION = new RecipeMap<>(
            "filtration", 1, 1, 1, 1, new PrimitiveRecipeBuilder(), false);

    @ZenProperty
    public static final RecipeMap<PrimitiveRecipeBuilder> PRIMITIVE_FURNACE = new RecipeMap<>(
            "primitive_furnace", 2, 2, 0, 0, new PrimitiveRecipeBuilder(), false);

    @ZenProperty
    public static final RecipeMap<NoEnergyFluidTimeRecipeBuilder> BATCH_DISTILLATION = new TopStaggeredFluidsRecipeMap<>(
            "batch_distillation", 1, 0, 1, 5, new NoEnergyFluidTimeRecipeBuilder(), false);

    @ZenProperty
    public static final RecipeMap<PrimitiveRecipeBuilder> FLOW_DISTILLATION = new RecipeMap<>(
            "flow_distillation", 1, 0, 2, 5, new PrimitiveRecipeBuilder(), false);

    /**
     * When using a fluid for two different recipes, it's MANDATORY to use an {@code ntCircuitIngredient}.
     * <pre>
     * Example:
     * <pre>
     *  PRESSURE_CRACKING.recipeBuilder()
     *          .notConsumable(new IntCircuitIngredient(1))
     *          .fluidInputs(Ethane.getFluid(1000))
     *          .fluidOutputs(HydroCrackedEthane.getFluid(1000))
     *          .minPressure(ATMOSPHERIC_PRESSURE * 10)
     *          .maxPressure(ATMOSPHERIC_PRESSURE * 12)
     *          .temperature(400)
     *          .gas(Hydrogen)
     *          .duration(80).EUt(VA[MV]).buildAndRegister();
     * </pre>
     */
    @ZenProperty
    public static final RecipeMap<TemperaturePressureIntervalRecipeBuilder> PRESSURE_CRACKING = new TopStaggeredRecipeMap<>(
            "pressure_cracking", 2, 0, 1, 1, new TemperaturePressureIntervalRecipeBuilder(), false)
            .setSound(GTSoundEvents.COMPRESSOR)
            .setSlotOverlay(false,false, true, GuiTextures.CRYSTAL_OVERLAY);

    @ZenProperty
    public static final RecipeMap<HeatRecipeBuilder> HEATING = new RecipeMap<>(
            "heating", 2, 0, 2, 2, new HeatRecipeBuilder(), false)
            .setSound(GTSoundEvents.COMPRESSOR);



    @ZenProperty
    public static final RecipeMap<TemperaturePressureIntervalRecipeBuilder> ROASTING = new RecipeMap<>(
            "roasting", 2, 4, 1, 2, new TemperaturePressureIntervalRecipeBuilder(), false)
            .setSound(GTSoundEvents.FURNACE);


    @ZenProperty
    public static final RecipeMap<SimpleRecipeBuilder> SPIRAL_SEPARATION = new RecipeMap<>(
            "spiral_separation", 1, 8, 0, 0, new SimpleRecipeBuilder(), false)
            .setSound(GTSoundEvents.MOTOR);

    @ZenProperty
    public static final RecipeMap<PrimitiveRecipeBuilder> COKING = new RecipeMap<>(
            "coking", 2, 1, 0, 1, new PrimitiveRecipeBuilder(), false)
            .setSound(GTSoundEvents.COMBUSTION);

    @ZenProperty
    public static final RecipeMap<SimpleRecipeBuilder> STEAM_AUTOCLAVE = new RecipeMap<>(
            "steam_autoclave", 2, 1, 0, 0, new SimpleRecipeBuilder(), false)
            .setSound(GTSoundEvents.COMPRESSOR);

    @ZenProperty
    public static final RecipeMap<PrimitiveRecipeBuilder> DIESEL_GENERATOR = new RecipeMap<>(
            "diesel_generator", 0, 0, 1, 1, new PrimitiveRecipeBuilder(), false)
            .setSound(GTSoundEvents.MOTOR);

    @ZenProperty
    public static final RecipeMap<ToolRecipeBuilder> LOG_CUTING = new RecipeMap<>(
            "log_cutting", 3, 2, 0, 0, new ToolRecipeBuilder(), false);



}
