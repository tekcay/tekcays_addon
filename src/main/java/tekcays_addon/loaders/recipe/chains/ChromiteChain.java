package tekcays_addon.loaders.recipe.chains;

import gregtech.api.GTValues;
import tekcays_addon.common.items.TKCYAMetaItems;

import static gregtech.api.GTValues.MV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.CHEMICAL_RECIPES;
import static gregtech.api.recipes.RecipeMaps.LARGE_CHEMICAL_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static tekcays_addon.api.recipes.TKCYARecipeMaps.ADVANCED_BLAST_FURNACE_RECIPES;
import static tekcays_addon.api.unification.TKCYAMaterials.*;

public class ChromiteChain {

    public static void init() {

        //STEP 1: 2 FeCr2O4 + 2 Na2CO3 + O2 -> 2 Na2CrO4 + Fe2O3 + 2 CO2
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Chromite, 14)
                .input(dust, SodiumCarbonate, 12)
                .fluidInputs(Oxygen.getFluid(7000))
                .output(dust, SodiumChromate, 8)
                .output(dust, BandedIron, 5)
                .duration(80)
                .EUt(VA[MV])
                .buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(TKCYAMetaItems.GAS_COLLECTOR.getStackForm())
                .input(dust, Chromite, 14)
                .input(dust, SodiumCarbonate, 12)
                .fluidInputs(Oxygen.getFluid(7000))
                .output(dust, SodiumChromate, 8)
                .output(dust, BandedIron, 5)
                .fluidOutputs(CarbonDioxide.getFluid(12000))
                .duration(80)
                .EUt(VA[MV])
                .buildAndRegister();

        //STEP 2: 2 Na2CrO4 + 2 H2SO4 -> Na2Cr2O7 + Na2SO4 + H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, SodiumChromate, 14)
                .fluidInputs(SulfuricAcid.getFluid(7000))
                .fluidOutputs(Water.getFluid(3000))
                .output(dust, SodiumDichromate, 11)
                .output(dust, SodiumSulfate, 7)
                .duration(80)
                .EUt(VA[MV])
                .buildAndRegister();

        //STEP 3: 2 Na2CrO4 + C -> Cr2O3 + 2 Na2CO3 + CO
        ADVANCED_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(dust, SodiumDichromate, 11)
                .input(dust, Carbon, 2)
                .output(dust, ChromiumOxide, 8)
                .output(dust, SodiumCarbonate, 6)
                .temperature(2000)
                .duration(80)
                .buildAndRegister();

        //STEP 4: Cr2O3 + 2 Al -> Al2O3 + 2 Cr
        ADVANCED_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(dust, ChromiumOxide, 5)
                .input(dust, Aluminium, 2)
                .output(dust, Alumina, 5)
                .fluidOutputs(Chrome.getFluid(GTValues.L * 2))
                .duration(80)
                .temperature(Chrome.getFluid().getTemperature())
                .buildAndRegister();
    }

}
