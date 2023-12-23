package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.GTValues;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Material;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static tekcays_addon.gtapi.consts.TKCYAValues.ATMOSPHERIC_PRESSURE;
import static tekcays_addon.gtapi.unification.TKCYAMaterials.*;

public class RoastingHandler {

    public static void init() {
        registerCinnabar();
        registerRecipeChalcopyrite();

        registerRecipe(Chalcocite, 1, CupricOxide, 2, 2000, 3, 800);
        registerRecipe(Pyrite, 2, BandedIron, 1, 2000, 3, 720);
        registerRecipe(Realgar, 1, Arsenopyrite, 2, 2000, 3, 520);
        registerRecipe(Tetrahedrite, 1, RoastedTetrahedrite, 1, 2000, 4, 500);
        registerRecipe(Stibnite, 1, AntimonyTrioxide, 1, 1500, 4, 500);
        registerRecipe(Cobaltite, 1, RoastedCobaltite, 1, 1000, 4, 500);
        registerRecipe(Sphalerite, 1, Zincite, 1, 1000, 4, 500);
        registerRecipe(Pentlandite, 1, Garnierite, 1, 2000, 4, 500);
        registerRecipe(Galena, 1, RoastedGalena, 1, 1000, 4, 500);
        registerRecipe(Kesterite, 1, RoastedKesterite, 1, 4000, 4, 800);
        registerRecipe(Stannite, 1, RoastedStannite, 1, 4000, 4, 800);
        registerRecipe(Arsenopyrite, 1, RoastedArsenopyrite, 1, 4000, 4, 800);
        registerRecipe(Bornite, 1, RoastedBornite, 1, 4000, 4, 800);
        registerRecipe(Molybdenite, 1, MolybdenumTrioxide, 1, 2000, 4, 800);

    }
    
    private static void registerRecipe(Material material1, int amount1, Material material2, int amount2, int sulfurDioxideAmount, int pressureInBar, int temperature) {
        TKCYARecipeMaps.ROASTING.recipeBuilder()
                .input(dust, material1, amount1)
                .output(dust, material2, amount2)
                .fluidInputs(Air.getFluid(1))
                .fluidOutputs(SulfurDioxide.getFluid(sulfurDioxideAmount))
                .minPressure(ATMOSPHERIC_PRESSURE * pressureInBar)
                .minTemperature(temperature)
                .duration(100)
                .EUt(50)
                .buildAndRegister();

        TKCYARecipeMaps.ROASTING.recipeBuilder()
                .input(dust, material1, amount1)
                .output(dust, material2, amount2)
                .fluidInputs(Oxygen.getFluid(1))
                .fluidOutputs(SulfurDioxide.getFluid(sulfurDioxideAmount))
                .minPressure((int) (ATMOSPHERIC_PRESSURE * pressureInBar * 0.75))
                .minTemperature(temperature)
                .duration(100)
                .EUt(50)
                .buildAndRegister();
    }

    private static void registerCinnabar() {
        TKCYARecipeMaps.ROASTING.recipeBuilder()
                .input(dust, Cinnabar)
                .fluidInputs(Air.getFluid(1))
                .fluidOutputs(SulfurDioxide.getFluid(2000))
                .fluidOutputs(Mercury.getFluid(GTValues.L))
                .minPressure(ATMOSPHERIC_PRESSURE * 3)
                .minTemperature(600)
                .duration(100)
                .EUt(50)
                .buildAndRegister();

        TKCYARecipeMaps.ROASTING.recipeBuilder()
                .input(dust, Cinnabar)
                .fluidInputs(Oxygen.getFluid(1))
                .fluidOutputs(SulfurDioxide.getFluid(2000))
                .fluidOutputs(Mercury.getFluid(GTValues.L))
                .minPressure((int) (ATMOSPHERIC_PRESSURE * 3 * 0.75))
                .minTemperature(600)
                .duration(100)
                .EUt(50)
                .buildAndRegister();
    }

    private static void registerRecipeChalcopyrite() {
        TKCYARecipeMaps.ROASTING.recipeBuilder()
                .input(dust, Chalcopyrite)
                .input(dust, SiliconDioxide)
                .output(dust, RoastedChalcopyrite)
                .fluidInputs(Air.getFluid(1))
                .fluidOutputs(SulfurDioxide.getFluid(2000))
                .minPressure(ATMOSPHERIC_PRESSURE * 4)
                .minTemperature(600)
                .duration(100)
                .EUt(50)
                .buildAndRegister();

        TKCYARecipeMaps.ROASTING.recipeBuilder()
                .input(dust, Chalcopyrite)
                .input(dust, SiliconDioxide)
                .output(dust, RoastedChalcopyrite)
                .fluidInputs(Air.getFluid(1))
                .fluidOutputs(SulfurDioxide.getFluid(2000))
                .minPressure((int) (ATMOSPHERIC_PRESSURE * 4 * 0.75))
                .minTemperature(600)
                .duration(100)
                .EUt(50)
                .buildAndRegister();
    }
}
