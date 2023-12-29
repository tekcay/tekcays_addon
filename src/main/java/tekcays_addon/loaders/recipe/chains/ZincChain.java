package tekcays_addon.loaders.recipe.chains;

import static gregtech.api.recipes.RecipeMaps.CHEMICAL_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Zincite;
import static gregtech.api.unification.ore.OrePrefix.*;
import static tekcays_addon.gtapi.recipes.TKCYARecipeMaps.ELECTROLYSIS;
import static tekcays_addon.gtapi.recipes.TKCYARecipeMaps.FILTRATION;
import static tekcays_addon.gtapi.unification.TKCYAMaterials.*;
import static tekcays_addon.loaders.DamageableItemsLoader.electrodeAluminium;
import static tekcays_addon.loaders.DamageableItemsLoader.filterStainlessSteel;

import gregtech.api.recipes.ingredients.GTRecipeItemInput;
import gregtech.api.recipes.ingredients.nbtmatch.NBTCondition;
import gregtech.api.recipes.ingredients.nbtmatch.NBTMatcher;
import tekcays_addon.common.items.TKCYAMetaItems;

public class ZincChain {

    public static void init() {
        // ZnO + 2H2SO4 = ZnSO4 + ZincLeachingResidue [Contains: (H2O)(H2SO4)]
        CHEMICAL_RECIPES.recipeBuilder().duration(40).EUt(480)
                .input(dust, Zincite)
                .fluidInputs(SulfuricAcid.getFluid(2000))
                .fluidOutputs(ZincLeachingSolution.getFluid(1000))
                .buildAndRegister();

        // ZnO + 2H2SO4 = ZnSO4 + ZincLeachingResidue [Contains: (H2O)(H2SO4)]
        FILTRATION.recipeBuilder().duration(200)
                .fluidInputs(ZincLeachingSolution.getFluid(1000))
                .inputNBT(GTRecipeItemInput.getOrCreate(filterStainlessSteel).setNonConsumable(), NBTMatcher.ANY,
                        NBTCondition.ANY)
                .output(dustSmall, Zinc)
                .fluidOutputs(ZincLeachingResidue.getFluid(1000))
                .buildAndRegister();

        // ZnSO4 + H2O -> Zn + 1/2 O2 + H2SO4
        ELECTROLYSIS.recipeBuilder().duration(200)
                .fluidInputs(Water.getFluid(1000))
                .notConsumable(TKCYAMetaItems.GAS_COLLECTOR)
                .inputNBT(GTRecipeItemInput.getOrCreate(electrodeAluminium).setNonConsumable(), NBTMatcher.ANY,
                        NBTCondition.ANY)
                .input(dust, ZincSulfate)
                .output(dust, Zinc)
                .fluidOutputs(SulfuricAcid.getFluid(1000))
                .EUt(100)
                .buildAndRegister();

        ELECTROLYSIS.recipeBuilder().duration(200)
                .fluidInputs(Water.getFluid(1000))
                .inputNBT(GTRecipeItemInput.getOrCreate(electrodeAluminium).setNonConsumable(), NBTMatcher.ANY,
                        NBTCondition.ANY)
                .input(dust, ZincSulfate)
                .output(dust, Zinc)
                .fluidOutputs(Oxygen.getFluid(500), SulfuricAcid.getFluid(1000))
                .EUt(120)
                .buildAndRegister();

        // ZincLeachingResidue [Contains: (H2O)(H2SO4)] -> FeSO4 + 0.5H4GeO4
        CHEMICAL_RECIPES.recipeBuilder().duration(100).EUt(480)
                .fluidInputs(ZincLeachingResidue.getFluid(1000))
                .notConsumable(TannicAcid.getFluid(1))
                .output(dust, IronSulfate, 6)
                .fluidOutputs(GermanicAcidSolution.getFluid(500))
                .buildAndRegister();
    }
}
