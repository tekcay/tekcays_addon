package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.GTValues;
import tekcays_addon.common.items.TKCYAMetaItems;

import static gregtech.api.GTValues.MV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.api.recipes.TKCYARecipeMaps.CONVERTING_RECIPES;
import static tekcays_addon.api.recipes.TKCYARecipeMaps.PRIMITIVE_CONVERTING_RECIPES;
import static tekcays_addon.api.unification.TKCYAMaterials.*;
import static tekcays_addon.api.utils.TKCYAValues.ATMOSPHERIC_PRESSURE;

public class ConverterHandler {

    public static void init() {

        //Primitive converting
        PRIMITIVE_CONVERTING_RECIPES.recipeBuilder()
                .fluidInputs(PigIron.getFluid(GTValues.L))
                .fluidInputs(Air.getFluid(8000))
                .fluidOutputs(Iron.getFluid(GTValues.L))
                .pressure(ATMOSPHERIC_PRESSURE)
                .duration(200).buildAndRegister();

        PRIMITIVE_CONVERTING_RECIPES.recipeBuilder()
                .fluidInputs(Iron.getFluid(GTValues.L))
                .fluidInputs(Air.getFluid(8000))
                .fluidOutputs(Steel.getFluid(GTValues.L))
                .pressure(ATMOSPHERIC_PRESSURE * 2)
                .duration(200).buildAndRegister();

        //Steel with Air
        CONVERTING_RECIPES.recipeBuilder()
                .fluidInputs(PigIron.getFluid(GTValues.L))
                .fluidInputs(Air.getFluid(4000))
                .fluidOutputs(Steel.getFluid(GTValues.L))
                .pressure(ATMOSPHERIC_PRESSURE * 5)
                .temperature(Steel.getFluid().getTemperature())
                .duration(200).EUt(VA[MV]).buildAndRegister();

        //Steel with oxygen
        CONVERTING_RECIPES.recipeBuilder()
                .fluidInputs(PigIron.getFluid(GTValues.L))
                .fluidInputs(Oxygen.getFluid(2000))
                .fluidOutputs(Steel.getFluid(GTValues.L))
                .pressure(ATMOSPHERIC_PRESSURE * 5)
                .temperature(Steel.getFluid().getTemperature())
                .duration(200).EUt(VA[MV]).buildAndRegister();

        //Steel with oxygen recycling
        CONVERTING_RECIPES.recipeBuilder()
                .notConsumable(TKCYAMetaItems.GAS_COLLECTOR.getStackForm())
                .fluidInputs(PigIron.getFluid(GTValues.L))
                .fluidInputs(Oxygen.getFluid(2000))
                .fluidOutputs(Steel.getFluid(GTValues.L))
                .fluidOutputs(Oxygen.getFluid(5000))
                .pressure(ATMOSPHERIC_PRESSURE * 5)
                .temperature(Steel.getFluid().getTemperature())
                .duration(200).EUt(VA[MV]).buildAndRegister();

        //NiZnFe4 + 4 O2 -> NiZnFe4O8
        CONVERTING_RECIPES.recipeBuilder()
                .fluidInputs(FerriteMixture.getFluid(GTValues.L))
                .fluidInputs(Oxygen.getFluid(8000))
                .fluidOutputs(NickelZincFerrite.getFluid(GTValues.L))
                .pressure(ATMOSPHERIC_PRESSURE * 10)
                .temperature(NickelZincFerrite.getFluid().getTemperature())
                .duration(200).EUt(VA[MV]).buildAndRegister();

        //TiF3 + 3 H2 -> Ti + 3 HF
        CONVERTING_RECIPES.recipeBuilder()
                .fluidInputs(TitaniumTrifluoride.getFluid(GTValues.L * 4))
                .fluidInputs(Hydrogen.getFluid(3000))
                .fluidOutputs(Titanium.getFluid(GTValues.L))
                .pressure(ATMOSPHERIC_PRESSURE * 10)
                .temperature(Titanium.getFluid().getTemperature())
                .duration(200).EUt(VA[MV]).buildAndRegister();

        //TiF3 + 3 H2 -> Ti + 3 HF with HF recovery
        CONVERTING_RECIPES.recipeBuilder()
                .notConsumable(TKCYAMetaItems.GAS_COLLECTOR.getStackForm())
                .fluidInputs(TitaniumTrifluoride.getFluid(GTValues.L * 4))
                .fluidInputs(Hydrogen.getFluid(3000))
                .fluidOutputs(Titanium.getFluid(GTValues.L))
                .fluidOutputs(HydrogenFluoride.getFluid(GTValues.L * 3))
                .pressure(ATMOSPHERIC_PRESSURE * 10)
                .temperature(Titanium.getFluid().getTemperature())
                .duration(200).EUt(VA[MV]).buildAndRegister();

    }
}
