package tekcays_addon.loaders.recipe;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.RecipeMaps;
import tekcays_addon.loaders.recipe.chains.*;
import tekcays_addon.loaders.recipe.handlers.*;
import tekcays_addon.loaders.ItemsRemovalHandler;
import tekcays_addon.loaders.recipe.handlers.StorageOverhaul;
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
            FermentationHandler.init();
            GasCollectorRecipeHandler.init();
            GlassHandler.init();
            ShapedCraftingRecipes.molds();
            ShapedCraftingRecipes.gasCollector();
            TKCYAPartsRecipeHandler.processMolds();
            ItemsRemovalHandler.molds();
            RecipesRemovalHandler.removeMoldsAndUsage();
        }

        if (energyOverhaul.disableGasTurbinesOverhaul) {
            BurningGasBoilerRecipeHandler.init();
        }

        if (miscOverhaul.enableCoilOverhaul) {
            Coils.init();
        }

        if (miscOverhaul.enableGalvanizedSteel) {
            GalvanizedSteel.shapedRecipes();
            GalvanizedSteel.componentsAssemblerRecipes();
            GalvanizedSteel.componentsGTCEuRecipesRemoval();

            RecipesRemovalHandler.removeShapedTreatedWoodRecipe();
            BathRecipeHandler.treatingWoodInit();
            ShapedCraftingRecipes.ulvPotin();
        }

        if (miscOverhaul.disableComponentsShapesRecipes) {
            RecipesRemovalHandler.shapedComponentsRecipes();
        }

        if (meltingOverhaul.enableBlastingOverhaul) {
            ConverterHandler.init();
            BlastingRecipeHandler.init();
        }

        if (crackingOverhaul.enableCrackingOverhaul) {
            PressureCrackingHandler.init();
        }

        if (miscOverhaul.enableElectrolysisOverhaul) {
            TKCYAPartsRecipeHandler.initElectrode();
            ElectrolysisHandler.init();
            //TODO : creates an issue with GCYS removing recipe
            /**
             * {@link gregicality.science.loaders.recipe.oreprocessing.PlatinumGroupProcessing}
             */
            //GTRecipeHandler.removeAllRecipes(RecipeMaps.ELECTROLYZER_RECIPES);
        }

        if (storageOverhaul.enableDrumsOverhaul) {
            StorageOverhaul.shapedRecipesDrums();
            StorageOverhaul.drumsAssembler();
            StorageOverhaul.removeGTCEuDrumsRecipe();
        }

        if (storageOverhaul.enableMultiblockTanksOverhaul) {
            StorageOverhaul.shapesRecipesTanksAndValves();
            StorageOverhaul.wallsAssembler();
            StorageOverhaul.removeGTCEuTanksAndValvesRecipe();
        }

        TKCYAPartsRecipeHandler.initFilter();
        CasingsLoader.init();
        ChemicalChains.init();
        MineralChains.init();
        PolymerHandler.init();
        RoastingHandler.init();
        //MUST BE CALLED AFTER ANY HANDLER THAT GENERATES DUST_MIXTURE !
        SpiralSeparatorHandler.init();
        GTCEuRequireCleanRoomHandler.init();
    }


    public static void loadLatest() {

        if (miscOverhaul.enableGalvanizedSteel) {
            GalvanizedSteel.galvanizingSteelBath();
            GalvanizedSteel.galvanizingSteelElectrolysis();
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
            TKCYACastingTableRecipeHandler.misc();
        }

    }

}
