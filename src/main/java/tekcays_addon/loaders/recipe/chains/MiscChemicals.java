package tekcays_addon.loaders.recipe.chains;


import tekcays_addon.api.unification.TKCYAMaterials;
import tekcays_addon.common.items.TKCYAMetaItems;

import static gregicality.science.api.unification.materials.GCYSMaterials.PotassiumHydroxide;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static tekcays_addon.api.unification.TKCYAMaterials.*;

public class MiscChemicals {

    public static void init() {

        ////ACIDS

        //2 KCl + H2SO4 -> 2 HCl + K2SO4
        CHEMICAL_RECIPES.recipeBuilder().duration(90).EUt(30)
                .input(dust, RockSalt, 2)
                .fluidInputs(SulfuricAcid.getFluid(1000))
                .output(dust, TKCYAMaterials.PotassiumSulfate)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder().duration(90).EUt(30)
                .notConsumable(TKCYAMetaItems.GAS_COLLECTOR.getStackForm())
                .input(dust, RockSalt, 2)
                .fluidInputs(SulfuricAcid.getFluid(1000))
                .output(dust, TKCYAMaterials.PotassiumSulfate)
                .fluidOutputs(HydrogenChloride.getFluid(2000))
                .buildAndRegister();

        //2 NaCl + H2SO4 -> 2 HCl + Na2SO4
        CHEMICAL_RECIPES.recipeBuilder().duration(90).EUt(30)
                .input(dust, Salt, 2)
                .fluidInputs(SulfuricAcid.getFluid(1000))
                .output(dust, TKCYAMaterials.PotassiumSulfate)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder().duration(90).EUt(30)
                .notConsumable(TKCYAMetaItems.GAS_COLLECTOR.getStackForm())
                .input(dust, Salt, 2)
                .fluidInputs(SulfuricAcid.getFluid(1000))
                .output(dust, TKCYAMaterials.PotassiumSulfate)
                .fluidOutputs(HydrogenChloride.getFluid(2000))
                .buildAndRegister();

        //HCl(g) + H2O -> HCl (l)
        MIXER_RECIPES.recipeBuilder().duration(90).EUt(30)
                .fluidInputs(HydrogenChloride.getFluid(1000))
                .fluidInputs(Water.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(1000))
                .buildAndRegister();

        //HF(g) + H2O -> HF (l)
        MIXER_RECIPES.recipeBuilder().duration(90).EUt(30)
                .fluidInputs(HydrogenFluoride.getFluid(1000))
                .fluidInputs(Water.getFluid(1000))
                .fluidOutputs(HydrofluoricAcid.getFluid(1000))
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
