package tekcays_addon.loaders.recipe;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import tekcays_addon.loaders.recipe.chains.*;
import tekcays_addon.loaders.recipe.handlers.*;
import tekcays_addon.loaders.recipe.removals.RecipesRemovalHandler;

import static tekcays_addon.common.TKCYAConfigHolder.*;

public class TKCYARecipeLoader {

    public static void preload() {
    }
    public static void load() {

        TKCYAMetaTileEntityLoader.init();

        if (miscOverhaul.enableFoilOverhaul) {
            TKCYAPartsRecipeHandler.initFoil();
        }

        if (meltingOverhaul.enableMeltingOverhaul) {
            TKCYAPartsRecipeHandler.removeExtractor();
        }

        if (meltingOverhaul.enableAlloyingOverhaul) {
            TKCYAPartsRecipeHandler.removeAlloySmelter();
            GTRecipeHandler.removeAllRecipes(RecipeMaps.PRIMITIVE_BLAST_FURNACE_RECIPES);
        }

        if (miscOverhaul.enableMagneticOverhaul) {
            TKCYAPartsRecipeHandler.initPolarizing();
        }

        if (meltingOverhaul.enableCastingOverhaul) {
            GasCollectorRecipeHandler.init();
            ShapedCraftingRecipes.molds();
            ShapedCraftingRecipes.gasCollector();
            TKCYAPartsRecipeHandler.processMolds();
        }

        if (energyOverhaul.disableGasTurbinesOverhaul) {
            BurningGasBoilerRecipeHandler.init();
        }

        if (miscOverhaul.enableCoilOverhaul) {
            Coils.init();
        }

        if (miscOverhaul.enableGalvanizedSteel) {
            RecipesRemovalHandler.steelRemovalsInit();
            RecipesRemovalHandler.removeShapedTreatedWoodRecipe();
            BathRecipeHandler.treatingWoodInit();
            ShapedCraftingRecipes.galvanizedSteel();
            AssemblerRecipeHandler.galvanizedSteel();
        }

        if (miscOverhaul.disableComponentsShapesRecipes) {
            RecipesRemovalHandler.shapedComponentsRecipes();
        }

        if (miscOverhaul.enableElectrolysisOverhaul) {
            TKCYAPartsRecipeHandler.initElectrode();
            ElectrolysisHandler.init();
            GTRecipeHandler.removeAllRecipes(RecipeMaps.ELECTROLYZER_RECIPES);
        }

        if (storageOverhaul.enableDrumsOverhaul) {
            RecipesRemovalHandler.removeDrums();
            ShapedCraftingRecipes.drums();
            AssemblerRecipeHandler.drums();
        }

        if (storageOverhaul.enableMultiblockTanksOverhaul) {
            RecipesRemovalHandler.removeTanksAndValves();
            ShapedCraftingRecipes.tanksAndValves();
        }

        CasingsLoader.init();
        ChemicalChains.init();
        MineralChains.init();

    }


    public static void loadLatest() {

        if (miscOverhaul.enableGalvanizedSteel) {
            BathRecipeHandler.galvanizingSteelInit();
        }

        if (meltingOverhaul.enableMeltingOverhaul) {
            TKCYAMeltingRecipeHandler.init();
            if (meltingOverhaul.enableBouleCrystallization) {
                CrystallizerHandler.boules();
            }
        }

        if (meltingOverhaul.enableAlloyingOverhaul) {
            TKCYAAlloyingCrucibleRecipeHandler.init();
        }

        if (meltingOverhaul.enableCastingOverhaul) {
            TKCYACastingTableRecipeHandler.init();
        }

    }

}
