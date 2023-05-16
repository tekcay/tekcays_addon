package tekcays_addon.loaders.recipe;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.properties.PropertyKey;
import tekcays_addon.loaders.recipe.chains.*;
import tekcays_addon.loaders.recipe.handlers.*;
import tekcays_addon.loaders.ItemsRemovalHandler;
import tekcays_addon.loaders.recipe.handlers.StorageOverhaul;
import tekcays_addon.loaders.recipe.removals.RecipesRemovalHandler;

import static gregtech.api.unification.ore.OrePrefix.foil;
import static tekcays_addon.common.TKCYAConfigHolder.*;

public class TKCYARecipeLoader {

    public static void preload() {
    }
    public static void load() {

        TKCYAMetaTileEntityLoader.init();
        FuelRecipes.init();

        PressureHandler.init();
        ULVComponentsHandler.init();

        if (miscOverhaul.enableFoilOverhaul) foil.addProcessingHandler(PropertyKey.INGOT, TKCYAPartsRecipeHandler::processFoil);
        if (miscOverhaul.enableHarderRotors) HarderRotorsHandler.init();
        if (miscOverhaul.enableCoilOverhaul) Coils.init();
        if (miscOverhaul.disableComponentsShapesRecipes) RecipesRemovalHandler.shapedComponentsRecipesRemoval();
        if (miscOverhaul.enableMagneticOverhaul) TKCYAPartsRecipeHandler.initPolarizing();
        if (meltingOverhaul.enableMeltingOverhaul) TKCYAPartsRecipeHandler.removeExtractor();
        if (energyOverhaul.disableGasTurbinesOverhaul) BurningGasBoilerRecipeHandler.init();
        if (crackingOverhaul.enableCrackingOverhaul) {
            PressureCrackingHandler.init();
            DistillationHandler.init();
        }

        if (meltingOverhaul.enableAlloyingOverhaul) {
            TKCYAPartsRecipeHandler.removeAlloySmelter();
            GTRecipeHandler.removeAllRecipes(RecipeMaps.PRIMITIVE_BLAST_FURNACE_RECIPES);
        }

        if (meltingOverhaul.enableCastingOverhaul) {
            FermentationHandler.init();
            GasCollectorRecipeHandler.init();
            GlassHandler.init();
            ShapedCraftingRecipes.gasCollector();
            //TKCYAPartsRecipeHandler.processMolds();
            ItemsRemovalHandler.molds();
            RecipesRemovalHandler.removeMoldsAndUsage();
        }

        if (miscOverhaul.enableGalvanizedSteel) {
            GalvanizedSteel.shapedRecipes();
            GalvanizedSteel.componentsAssemblerRecipes();
            GalvanizedSteel.componentsGTCEuRecipesRemoval();

            RecipesRemovalHandler.removeShapedTreatedWoodRecipe();
            BathRecipeHandler.treatingWoodInit();
            ShapedCraftingRecipes.ulvPotin();
        }

        if (meltingOverhaul.enableBlastingOverhaul) {
            ConverterHandler.init();
            BlastingRecipeHandler.init();
        }

        if (miscOverhaul.enableElectrolysisOverhaul) {
            TKCYAPartsRecipeHandler.initElectrode();
            ElectrolysisHandler.init();
            GTRecipeHandler.removeAllRecipes(RecipeMaps.ELECTROLYZER_RECIPES);
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

        if (miscOverhaul.enableCokeOvenOverhaul) {
            CokeOvenRecipeHandler.init();
        }

        CasingsLoader.init();
        ChemicalChains.init();
        MineralChains.init();
        PolymerHandler.init();
        RoastingHandler.init();
        HeatHandler.init();
        //MUST BE CALLED AFTER ANY HANDLER THAT GENERATES DUST_MIXTURE !
        SpiralSeparatorHandler.init();
        GTCEuRequireCleanRoomHandler.init();
        //Must be called in the end
        FiltrationRecipeHandler.init();
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
            CastingRecipeHandler.init();
        }

    }

}
