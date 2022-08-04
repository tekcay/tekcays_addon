package tekcays_addon.loaders.recipe.handlers;

import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.api.recipes.DistillationRecipes.TKCYA_DISTILLATION_RECIPES;
import static tekcays_addon.api.recipes.DistillationRecipes.WOOD_VINEGAR;
import static tekcays_addon.api.recipes.TKCYARecipeMaps.DISTILLATION;
import static tekcays_addon.api.utils.FluidWithProperties.*;

public class DistillationHandler {

    public static void initJEI() {

        DISTILLATION.recipeBuilder()
                .fluidInputs(WoodVinegar.getFluid(1000))
                .fluidOutputs(AceticAcid.getFluid(100))
                .fluidOutputs(Water.getFluid(500))
                .fluidOutputs(Ethanol.getFluid(10))
                .fluidOutputs(Methanol.getFluid(300))
                .fluidOutputs(Acetone.getFluid(50))
                .fluidOutputs(MethylAcetate.getFluid(10))
                .duration(40)
                .buildAndRegister();

    }

    public static void fluidWithPropertiesInit() {
        FLUID_WITH_PROPERTIES.add(ACETIC_ACID);
        FLUID_WITH_PROPERTIES.add(ACETONE);
        FLUID_WITH_PROPERTIES.add(ETHANOL);
        FLUID_WITH_PROPERTIES.add(METHANOL);
        FLUID_WITH_PROPERTIES.add(METHYL_ACETATE);
        FLUID_WITH_PROPERTIES.add(WATER);
    }

    public static void initBackEnd() {
        TKCYA_DISTILLATION_RECIPES.add(WOOD_VINEGAR);
    }

}
