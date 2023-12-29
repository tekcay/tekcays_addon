package tekcays_addon.loaders.recipe.handlers;

import static gregtech.api.GTValues.LV;
import static gregtech.api.GTValues.V;
import static gregtech.api.unification.material.Materials.*;

import gregtech.api.recipes.RecipeMaps;

public class BurningGasBoilerRecipeHandler {

    public static void init() {
        // GTRecipeHandler.removeAllRecipes(RecipeMaps.GAS_TURBINE_FUELS);

        // gas turbine fuels
        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(NaturalGas.getFluid(8))
                .duration(5)
                .EUt((int) V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(WoodGas.getFluid(8))
                .duration(6)
                .EUt((int) V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(SulfuricGas.getFluid(32))
                .duration(25)
                .EUt((int) V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(SulfuricNaphtha.getFluid(4))
                .duration(5)
                .EUt((int) V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(CoalGas.getFluid(1))
                .duration(3)
                .EUt((int) V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(Methane.getFluid(2))
                .duration(7)
                .EUt((int) V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(Ethylene.getFluid(1))
                .duration(4)
                .EUt((int) V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(RefineryGas.getFluid(1))
                .duration(5)
                .EUt((int) V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(Ethane.getFluid(4))
                .duration(21)
                .EUt((int) V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(Propene.getFluid(1))
                .duration(6)
                .EUt((int) V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(Butadiene.getFluid(16))
                .duration(102)
                .EUt((int) V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(Propane.getFluid(4))
                .duration(29)
                .EUt((int) V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(Butene.getFluid(1))
                .duration(8)
                .EUt((int) V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(Phenol.getFluid(1))
                .duration(9)
                .EUt((int) V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(Benzene.getFluid(1))
                .duration(11)
                .EUt((int) V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(Butane.getFluid(4))
                .duration(37)
                .EUt((int) V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(LPG.getFluid(1))
                .duration(10)
                .EUt((int) V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder() // TODO Too OP pls nerf
                .fluidInputs(Nitrobenzene.getFluid(1))
                .duration(30) // Was 40
                .EUt((int) V[LV])
                .buildAndRegister();
    }
}
