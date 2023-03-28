package tekcays_addon.loaders.recipe.chains;

import gregtech.api.GTValues;
import gregtech.api.recipes.ingredients.GTRecipeItemInput;
import gregtech.api.recipes.ingredients.nbtmatch.NBTCondition;
import gregtech.api.recipes.ingredients.nbtmatch.NBTMatcher;
import gregtech.api.unification.stack.MaterialStack;
import tekcays_addon.common.items.TKCYAMetaItems;

import java.util.ArrayList;
import java.util.List;

import static gregtech.api.recipes.RecipeMaps.CHEMICAL_BATH_RECIPES;
import static gregtech.api.recipes.RecipeMaps.CHEMICAL_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static tekcays_addon.api.recipes.TKCYARecipeMaps.*;
import static tekcays_addon.api.unification.TKCYAMaterials.*;
import static tekcays_addon.api.utils.TKCYAValues.SECOND;
import static tekcays_addon.api.utils.roasting.RoastingRecipeHandlerMethods.getDustMixtureStackWithNBT;
import static tekcays_addon.loaders.DamageableItemsLoader.electrodeCarbon;

public class BauxiteChain {

    public static void init() {

        //STEP 1
        List<MaterialStack> output_1 = new ArrayList<MaterialStack>() {{
           add(new MaterialStack(PotassiumAluminate, 18));
           add(new MaterialStack(Ilmenite, 2));
           add(new MaterialStack(Rutile, 1));
        }};

        List<MaterialStack> output_2 = new ArrayList<MaterialStack>() {{
            add(new MaterialStack(SodiumAluminate, 18));
            add(new MaterialStack(Ilmenite, 2));
            add(new MaterialStack(Rutile, 1));
        }};

        // Bauxite + 1.5 KOH -> 2 KAlO2 + (2 FeTiO3 + TiO2) tinyDust
        STEAM_AUTOCLAVE.recipeBuilder()
                .input(dust, Bauxite)
                .input(dustSmall, PotassiumHydroxide, 6)
                .outputs(getDustMixtureStackWithNBT(output_1))
                .duration(30 * SECOND)
                .EUt(15)
                .buildAndRegister();

        // Bauxite + 1.5 NaOH -> 2 NaAlO2 + (2 FeTiO3 + TiO2) tinyDust
        STEAM_AUTOCLAVE.recipeBuilder()
                .input(dust, Bauxite)
                .input(dustSmall, SodiumHydroxide, 6)
                .outputs(getDustMixtureStackWithNBT(output_2))
                .duration(75 * SECOND)
                .EUt(30)
                .buildAndRegister();


        //STEP 2

        List<MaterialStack> output_3 = new ArrayList<MaterialStack>() {{
            add(new MaterialStack(AluminiumHydroxide, 63));
            add(new MaterialStack(PotassiumHydroxide, 27));
        }};

        List<MaterialStack> output_4 = new ArrayList<MaterialStack>() {{
            add(new MaterialStack(AluminiumHydroxide, 63));
            add(new MaterialStack(SodiumHydroxide, 27));
        }};

        // 4 KAlO2 + 6 H2O -> 7 Al(OH)3 + 3 KOH
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(dust, PotassiumAluminate, 4)
                .fluidInputs(DistilledWater.getFluid(6000))
                .outputs(getDustMixtureStackWithNBT(output_3))
                .duration(75 * SECOND)
                .EUt(30)
                .buildAndRegister();

        // 4 NaAlO2 + 6 H2O -> 7 Al(OH)3 + 3 NaOH
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(dust, SodiumAluminate, 4)
                .fluidInputs(DistilledWater.getFluid(6000))
                .outputs(getDustMixtureStackWithNBT(output_4))
                .duration(75 * SECOND)
                .EUt(30)
                .buildAndRegister();

        //STEP 3
        // 2 Al(OH)3 -> Al2O3 (l) + 3 H2O
        MELTER_RECIPES.recipeBuilder()
                .input(dust, AluminiumHydroxide)
                .fluidOutputs(Alumina.getFluid(GTValues.L / 2))
                .duration(75 * SECOND)
                .temperature(Alumina.getFluid().getTemperature())
                .EUt(30)
                .buildAndRegister();

        //STEP 4
        // 10 Al2O3 + 3 C + AlF3 + Cryolite -> 4 Al + 9 CO2 + F2
        ELECTROLYSIS.recipeBuilder()
                .input(dust, Carbon, 3)
                .notConsumable(TKCYAMetaItems.GAS_COLLECTOR.getStackForm())
                .inputNBT(GTRecipeItemInput.getOrCreate(electrodeCarbon).setNonConsumable(), NBTMatcher.ANY, NBTCondition.ANY)
                .output(dust, Aluminium, 4)
                .fluidInputs(Alumina.getFluid(GTValues.L * 10))
                .fluidInputs(AluminiumFluoride.getFluid(4))
                .fluidInputs(Cryolite.getFluid(2))
                .fluidOutputs(CarbonDioxide.getFluid(9000))
                .duration(30 * SECOND)
                .EUt(100)
                .buildAndRegister();

        ELECTROLYSIS.recipeBuilder()
                .input(dust, Carbon, 3)
                .inputNBT(GTRecipeItemInput.getOrCreate(electrodeCarbon).setNonConsumable(), NBTMatcher.ANY, NBTCondition.ANY)
                .output(dust, Aluminium, 4)
                .fluidInputs(Alumina.getFluid(GTValues.L * 10))
                .fluidInputs(AluminiumFluoride.getFluid(4))
                .fluidInputs(Cryolite.getFluid(2))
                .duration(30 * SECOND)
                .EUt(100)
                .buildAndRegister();

        //HexafluorosilicAcid
        // 6 HF + SiO2 -> H2SiF6 + 2 H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, SiliconDioxide)
                .fluidInputs(HydrofluoricAcid.getFluid(6000))
                .fluidOutputs(HexafluorosilicAcid.getFluid(1000))
                .fluidOutputs(Water.getFluid(2000))
                .duration(100)
                .EUt(30)
                .buildAndRegister();

        //AluminiumFluoride
        // 2 Al + 3 F2 -> 2 AlF3
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Aluminium, 2)
                .fluidOutputs(Alumina.getFluid(GTValues.L / 2))
                .duration(100)
                .EUt(30)
                .buildAndRegister();

        //5 Al2O3 + 9 H2SiF6 -> 8 AlF3 + 3 H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Alumina, 5)
                .fluidInputs(HexafluorosilicAcid.getFluid(9000))
                .fluidOutputs(AluminiumFluoride.getFluid(8000))
                .fluidOutputs(Water.getFluid(3000))
                .duration(100)
                .EUt(30)
                .buildAndRegister();

        //Cryolite
        // 18 NaOH + 5 Al2O3 + 24 HF -> 20 Cryolite + 27 H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, SodiumHydroxide, 18)
                .input(dust, Alumina, 5)
                .fluidInputs(HydrofluoricAcid.getFluid(24000))
                .fluidOutputs(Water.getFluid(27000))
                .fluidOutputs(Cryolite.getFluid(20000))
                .duration(100)
                .EUt(30)
                .buildAndRegister();

    }
}
