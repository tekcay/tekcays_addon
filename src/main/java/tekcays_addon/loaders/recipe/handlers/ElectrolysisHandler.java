package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.recipes.ingredients.GTRecipeItemInput;
import gregtech.api.recipes.ingredients.nbtmatch.NBTCondition;
import gregtech.api.recipes.ingredients.nbtmatch.NBTMatcher;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import tekcays_addon.common.items.TKCYAMetaItems;

import static gregicality.science.api.unification.materials.GCYSMaterials.*;
import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.api.recipes.TKCYARecipeMaps.ELECTROLYSIS;
import static tekcays_addon.loaders.DamageableItemsLoader.*;

public class ElectrolysisHandler {

    public static void init() {

        //H2O -> H2 + O2

        ELECTROLYSIS.recipeBuilder()
                .inputNBT(GTRecipeItemInput.getOrCreate(electrodePlatinum).setNonConsumable(), NBTMatcher.ANY, NBTCondition.ANY)
                .notConsumable(SulfuricAcid.getFluid(), 1000)
                .notConsumable(TKCYAMetaItems.GAS_COLLECTOR)
                .fluidInputs(DistilledWater.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(2000), Oxygen.getFluid(1000))
                .duration(100)
                .EUt(120)
                .buildAndRegister();

        ELECTROLYSIS.recipeBuilder()
                .inputNBT(GTRecipeItemInput.getOrCreate(electrodePlatinum).setNonConsumable(), NBTMatcher.ANY, NBTCondition.ANY)
                .notConsumable(OrePrefix.dust, Potash)
                .notConsumable(TKCYAMetaItems.GAS_COLLECTOR)
                .fluidInputs(DistilledWater.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(2000), Oxygen.getFluid(1000))
                .duration(100)
                .EUt(120)
                .buildAndRegister();

        ELECTROLYSIS.recipeBuilder()
                .inputNBT(GTRecipeItemInput.getOrCreate(electrodePlatinum).setNonConsumable(), NBTMatcher.ANY, NBTCondition.ANY)
                .notConsumable(OrePrefix.dust, SodiumHydroxide)
                .notConsumable(TKCYAMetaItems.GAS_COLLECTOR)
                .fluidInputs(DistilledWater.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(2000), Oxygen.getFluid(1000))
                .duration(100)
                .EUt(120)
                .buildAndRegister();


        ELECTROLYSIS.recipeBuilder()
                .inputNBT(GTRecipeItemInput.getOrCreate(electrodeGold).setNonConsumable(), NBTMatcher.ANY, NBTCondition.ANY)
                .fluidInputs(BlueVitriol.getFluid(1000), DistilledWater.getFluid(1000))
                .fluidOutputs(SulfuricAcid.getFluid(1000))
                .output(OrePrefix.dust, Copper)
                .chancedOutput(OrePrefix.dust, ChalcogenAnodeMud, 50, 0)
                .duration(100)
                .EUt(60)
                .buildAndRegister();

        ELECTROLYSIS.recipeBuilder()
                .inputNBT(GTRecipeItemInput.getOrCreate(electrodeGold).setNonConsumable(), NBTMatcher.ANY, NBTCondition.ANY)
                .notConsumable(TKCYAMetaItems.GAS_COLLECTOR)
                .fluidInputs(BlueVitriol.getFluid(1000), DistilledWater.getFluid(1000))
                .fluidOutputs(SulfuricAcid.getFluid(1000), Oxygen.getFluid(1000))
                .output(OrePrefix.dust, Copper)
                .chancedOutput(OrePrefix.dust, ChalcogenAnodeMud, 50, 0)
                .duration(100)
                .EUt(80)
                .buildAndRegister();

        doVitriols(BlueVitriol, Copper, 1);
        doVitriols(GrayVitriol, Manganese, 1);
        doVitriols(PinkVitriol, Magnesium, 1);
        doVitriols(GreenVitriol, Iron, 1);
        doVitriols(WhiteVitriol, Zinc, 1);
        doVitriols(ClayVitriol, Alumina, 5);
        doVitriols(CyanVitriol, Nickel, 1);
        doVitriols(RedVitriol, Cobalt, 1);

    }

    public static void doVitriols(Material vitriol, Material output, int amount) {

        ELECTROLYSIS.recipeBuilder()
                .inputNBT(GTRecipeItemInput.getOrCreate(electrodeSilver).setNonConsumable(), NBTMatcher.ANY, NBTCondition.ANY)
                .fluidInputs(vitriol.getFluid(1000), DistilledWater.getFluid(1000))
                .fluidOutputs(SulfuricAcid.getFluid(1000))
                .output(OrePrefix.dust, output, amount)
                .duration(100)
                .EUt(60)
                .buildAndRegister();

        ELECTROLYSIS.recipeBuilder()
                .inputNBT(GTRecipeItemInput.getOrCreate(electrodeSilver).setNonConsumable(), NBTMatcher.ANY, NBTCondition.ANY)
                .notConsumable(TKCYAMetaItems.GAS_COLLECTOR)
                .fluidInputs(vitriol.getFluid(1000), DistilledWater.getFluid(1000))
                .fluidOutputs(SulfuricAcid.getFluid(1000), Oxygen.getFluid(1000))
                .output(OrePrefix.dust, output, amount)
                .duration(100)
                .EUt(80)
                .buildAndRegister();

    }


}
