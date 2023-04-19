package tekcays_addon.loaders.recipe.handlers;

import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;
import static gregtech.api.unification.material.Materials.*;

public class FuelRecipes {
    
    public static void init() {

        
        TKCYARecipeMaps.DIESEL_GENERATOR.recipeBuilder()
                .fluidInputs(Naphtha.getFluid(1))
                .fluidOutputs(CarbonDioxide.getFluid(1))
                .duration(10)
                .buildAndRegister();

        TKCYARecipeMaps.DIESEL_GENERATOR.recipeBuilder()
                .fluidInputs(SulfuricLightFuel.getFluid(4))
                .fluidOutputs(CarbonDioxide.getFluid(1))
                .duration(5)
                .buildAndRegister();

        TKCYARecipeMaps.DIESEL_GENERATOR.recipeBuilder()
                .fluidInputs(Methanol.getFluid(4))
                .fluidOutputs(CarbonDioxide.getFluid(1))
                .duration(8)
                .buildAndRegister();

        TKCYARecipeMaps.DIESEL_GENERATOR.recipeBuilder()
                .fluidInputs(Ethanol.getFluid(1))
                .fluidOutputs(CarbonDioxide.getFluid(1))
                .duration(6)
                .buildAndRegister();

        TKCYARecipeMaps.DIESEL_GENERATOR.recipeBuilder()
                .fluidInputs(Octane.getFluid(2))
                .fluidOutputs(CarbonDioxide.getFluid(1))
                .duration(5)
                .buildAndRegister();

        TKCYARecipeMaps.DIESEL_GENERATOR.recipeBuilder()
                .fluidInputs(BioDiesel.getFluid(1))
                .fluidOutputs(CarbonDioxide.getFluid(1))
                .duration(8)
                .buildAndRegister();

        TKCYARecipeMaps.DIESEL_GENERATOR.recipeBuilder()
                .fluidInputs(LightFuel.getFluid(1))
                .fluidOutputs(CarbonDioxide.getFluid(1))
                .duration(10)
                .buildAndRegister();

        TKCYARecipeMaps.DIESEL_GENERATOR.recipeBuilder()
                .fluidInputs(Diesel.getFluid(1))
                .fluidOutputs(CarbonDioxide.getFluid(1))
                .duration(15)
                .buildAndRegister();

        TKCYARecipeMaps.DIESEL_GENERATOR.recipeBuilder()
                .fluidInputs(CetaneBoostedDiesel.getFluid(2))
                .fluidOutputs(CarbonDioxide.getFluid(1))
                .duration(45)
                .buildAndRegister();

        TKCYARecipeMaps.DIESEL_GENERATOR.recipeBuilder()
                .fluidInputs(RocketFuel.getFluid(16))
                .fluidOutputs(CarbonDioxide.getFluid(1))
                .duration(125)
                .buildAndRegister();

        TKCYARecipeMaps.DIESEL_GENERATOR.recipeBuilder()
                .fluidInputs(Gasoline.getFluid(1))
                .fluidOutputs(CarbonDioxide.getFluid(1))
                .duration(50)
                .buildAndRegister();

        TKCYARecipeMaps.DIESEL_GENERATOR.recipeBuilder()
                .fluidInputs(HighOctaneGasoline.getFluid(1))
                .fluidOutputs(CarbonDioxide.getFluid(1))
                .duration(100)
                .buildAndRegister();

        TKCYARecipeMaps.DIESEL_GENERATOR.recipeBuilder()
                .fluidInputs(Toluene.getFluid(1))
                .fluidOutputs(CarbonDioxide.getFluid(1))
                .duration(10)
                .buildAndRegister();

        TKCYARecipeMaps.DIESEL_GENERATOR.recipeBuilder()
                .fluidInputs(OilLight.getFluid(32))
                .fluidOutputs(CarbonDioxide.getFluid(1))
                .duration(5)
                .buildAndRegister();

        TKCYARecipeMaps.DIESEL_GENERATOR.recipeBuilder()
                .fluidInputs(RawOil.getFluid(64))
                .fluidOutputs(CarbonDioxide.getFluid(1))
                .duration(15)
                .buildAndRegister();

    }
}
