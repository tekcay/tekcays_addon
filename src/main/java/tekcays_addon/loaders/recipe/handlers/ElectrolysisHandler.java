package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.GTValues;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.GTRecipeItemInput;
import gregtech.api.recipes.ingredients.nbtmatch.NBTCondition;
import gregtech.api.recipes.ingredients.nbtmatch.NBTMatcher;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import tekcays_addon.api.utils.TKCYAValues;

import static gregtech.api.GTValues.LV;
import static gregtech.api.unification.material.Materials.Steel;
import static gregtech.api.unification.material.Materials.Zinc;
import static tekcays_addon.api.recipes.TKCYARecipeMaps.ELECTROLYSIS;
import static tekcays_addon.api.unification.TKCYAMaterials.GalvanizedSteel;
import static tekcays_addon.loaders.DamageableItemsLoader.electrodePlatinum;
import static tekcays_addon.loaders.DamageableItemsLoader.electrodeZinc;

public class ElectrolysisHandler {

    public static void init() {

        //H2O -> H2 + O2


        ELECTROLYSIS.recipeBuilder()
                .inputNBT(GTRecipeItemInput.getOrCreate(electrodePlatinum).setNonConsumable(), NBTMatcher.ANY, NBTCondition.ANY)
                .notConsumable(Materials.SulfuricAcid.getFluid(), 1000)
                .fluidInputs(Materials.DistilledWater.getFluid(1000))
                .fluidOutputs(Materials.Hydrogen.getFluid(2000), Materials.Oxygen.getFluid(1000))
                .duration(100)
                .EUt(120)
                .buildAndRegister();

        ELECTROLYSIS.recipeBuilder()
                .inputNBT(GTRecipeItemInput.getOrCreate(electrodePlatinum).setNonConsumable(), NBTMatcher.ANY, NBTCondition.ANY)
                .notConsumable(OrePrefix.dust, Materials.Potash, 1)
                .fluidInputs(Materials.DistilledWater.getFluid(1000))
                .fluidOutputs(Materials.Hydrogen.getFluid(2000), Materials.Oxygen.getFluid(1000))
                .duration(100)
                .EUt(120)
                .buildAndRegister();

        ELECTROLYSIS.recipeBuilder()
                .inputNBT(GTRecipeItemInput.getOrCreate(electrodePlatinum).setNonConsumable(), NBTMatcher.ANY, NBTCondition.ANY)
                .notConsumable(OrePrefix.dust, Materials.SodiumHydroxide, 1)
                .fluidInputs(Materials.DistilledWater.getFluid(1000))
                .fluidOutputs(Materials.Hydrogen.getFluid(2000), Materials.Oxygen.getFluid(1000))
                .duration(100)
                .EUt(120)
                .buildAndRegister();

    }

    public static void galvanizing() {
        for (OrePrefix orePrefix : TKCYAValues.STEEL_TO_GALVANIZED_OREPREFIXES) {

            ELECTROLYSIS.recipeBuilder()
                    .input(orePrefix, Steel)
                    .inputNBT(GTRecipeItemInput.getOrCreate(electrodeZinc).setNonConsumable(), NBTMatcher.ANY, NBTCondition.ANY)
                    .fluidInputs(Zinc.getFluid((int) (orePrefix.getMaterialAmount(Steel) * GTValues.L / (GTValues.M * 9))))
                    .duration((int) (10 + Steel.getMass() * orePrefix.getMaterialAmount(Steel) / GTValues.M))
                    .output(orePrefix, GalvanizedSteel)
                    .EUt(120)
                    .buildAndRegister();
        }
    }

}
