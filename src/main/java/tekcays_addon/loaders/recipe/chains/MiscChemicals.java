package tekcays_addon.loaders.recipe.chains;


import tekcays_addon.api.unification.TKCYAMaterials;

import static gregicality.science.api.unification.materials.GCYSMaterials.PotassiumHydroxide;
import static gregtech.api.recipes.RecipeMaps.CHEMICAL_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;

public class MiscChemicals {

    public static void init() {



        CHEMICAL_RECIPES.recipeBuilder().duration(90).EUt(30)
                .input(dust, PotassiumHydroxide, 2)
                .fluidInputs(SulfurDioxide.getFluid(2000))
                .output(dust, TKCYAMaterials.PotassiumMetaBisulfite)
                .fluidOutputs(DistilledWater.getFluid(1000))
                .buildAndRegister();

        /*
        //Diethyl ether
        // 2C2H5OH = (C2H5)2O + H2O (H2O lost to dehydrator) //TODO in GCYS
        CHEMICAL_DEHYDRATOR_RECIPES.recipeBuilder().duration(120).EUt(750)
                .fluidInputs(Ethanol.getFluid(2000))
                .notConsumable(SulfuricAcid.getFluid(0))
                .notConsumable(new IntCircuitIngredient(1))
                .fluidOutputs(Diethylether.getFluid(1000))
                .buildAndRegister();



        //Tannic Acid

        // 2 Wood + Na2SO4 + H2O2 + [NaOH + H2O] = 2C6H10O5 + Polyphenol Mix
        // This recipe is close enough
        CHEMICAL_RECIPES.recipeBuilder().duration(90).EUt(30)
                .input(dust, Wood, 2)
                .inputs(SodiumSulfite.getItemStack(6))
                .fluidInputs(HydrogenPeroxide.getFluid(1000))
                .fluidInputs(SodiumHydroxideSolution.getFluid(1000))
                .outputs(Cellulose.getItemStack(42))
                .fluidOutputs(PolyphenolMix.getFluid(1000))
                .buildAndRegister();


         */

    }

}
