package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.GTValues;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.ore.OrePrefix;
import tekcays_addon.common.items.TKCYAMetaItems;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.api.recipes.TKCYARecipeMaps.CONVERTING_RECIPES;
import static tekcays_addon.api.recipes.TKCYARecipeMaps.PRIMITIVE_CONVERTING_RECIPES;
import static tekcays_addon.api.unification.TKCYAMaterials.*;
import static tekcays_addon.api.utils.TKCYAValues.*;

public class ConverterHandler {

    public static void init() {

        //Primitive converting
        PRIMITIVE_CONVERTING_RECIPES.recipeBuilder()
                .fluidInputs(PigIron.getFluid(GTValues.L))
                .fluidOutputs(Iron.getFluid(GTValues.L))
                .intervalPressure(new Integer[]{ATMOSPHERIC_PRESSURE * 6, ATMOSPHERIC_PRESSURE * 10})
                .intervalTemperature(new Integer[]{PigIron.getFluid().getTemperature(), 2500})
                .pressurizedFluidStack(Air.getFluid(1))
                .duration(MINUTE).buildAndRegister();

        PRIMITIVE_CONVERTING_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Calcite)
                .fluidInputs(PigIron.getFluid(GTValues.L))
                .fluidOutputs(Steel.getFluid(GTValues.L))
                .intervalPressure(new Integer[]{ATMOSPHERIC_PRESSURE * 4, ATMOSPHERIC_PRESSURE * 5})
                .intervalTemperature(new Integer[]{PigIron.getFluid().getTemperature(), 2500})
                .pressurizedFluidStack(Air.getFluid(1))
                .duration(3 * MINUTE).buildAndRegister();


        //Converting electric
        //Iron with Air
        CONVERTING_RECIPES.recipeBuilder()
                .fluidInputs(PigIron.getFluid(GTValues.L))
                .fluidOutputs(Iron.getFluid(GTValues.L))
                .intervalPressure(new Integer[]{ATMOSPHERIC_PRESSURE * 6, ATMOSPHERIC_PRESSURE * 10})
                .intervalTemperature(new Integer[]{PigIron.getFluid().getTemperature(), 2500})
                .pressurizedFluidStack(Air.getFluid(1))
                .duration(3 * MINUTE).EUt(VA[LV]).buildAndRegister();

        //Iron with oxygen
        CONVERTING_RECIPES.recipeBuilder()
                .notConsumable(IntCircuitIngredient.getIntegratedCircuit(1))
                .fluidInputs(PigIron.getFluid(GTValues.L))
                .fluidOutputs(Iron.getFluid(GTValues.L))
                .intervalPressure(new Integer[]{ATMOSPHERIC_PRESSURE * 4, ATMOSPHERIC_PRESSURE * 6})
                .intervalTemperature(new Integer[]{PigIron.getFluid().getTemperature(), 2500})
                .pressurizedFluidStack(Oxygen.getFluid(1))
                .duration(MINUTE).EUt(VA[LV]).buildAndRegister();

        //Steel with Air
        CONVERTING_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Calcite)
                .fluidInputs(PigIron.getFluid(GTValues.L))
                .fluidOutputs(Steel.getFluid(GTValues.L))
                .intervalPressure(new Integer[]{ATMOSPHERIC_PRESSURE * 4, ATMOSPHERIC_PRESSURE * 5})
                .intervalTemperature(new Integer[]{PigIron.getFluid().getTemperature(), 2500})
                .pressurizedFluidStack(Air.getFluid(1))
                .duration(3 * MINUTE).EUt(VA[LV]).buildAndRegister();

        //Steel with oxygen
        CONVERTING_RECIPES.recipeBuilder()
                .notConsumable(IntCircuitIngredient.getIntegratedCircuit(1))
                .input(OrePrefix.dust, Calcite)
                .fluidInputs(PigIron.getFluid(GTValues.L))
                .fluidOutputs(Steel.getFluid(GTValues.L))
                .intervalPressure(new Integer[]{ATMOSPHERIC_PRESSURE * 3, ATMOSPHERIC_PRESSURE * 4})
                .intervalTemperature(new Integer[]{PigIron.getFluid().getTemperature(), 2500})
                .pressurizedFluidStack(Oxygen.getFluid(1))
                .duration(MINUTE).EUt(VA[LV]).buildAndRegister();

        //NiZnFe4 + 4 O2 -> NiZnFe4O8
        CONVERTING_RECIPES.recipeBuilder()
                .fluidInputs(FerriteMixture.getFluid(GTValues.L))
                .fluidOutputs(NickelZincFerrite.getFluid(GTValues.L))
                .intervalPressure(new Integer[]{ATMOSPHERIC_PRESSURE * 10, ATMOSPHERIC_PRESSURE * 15})
                .intervalTemperature(new Integer[]{NickelZincFerrite.getFluid().getTemperature(), Titanium.getFluid().getTemperature() + 500})
                .pressurizedFluidStack(Oxygen.getFluid(1))
                .duration(200).EUt(VA[MV]).buildAndRegister();

        //TiF3 + 3 H2 -> Ti + 3 HF
        CONVERTING_RECIPES.recipeBuilder()
                .fluidInputs(TitaniumTrifluoride.getFluid(GTValues.L * 4))
                .fluidOutputs(Titanium.getFluid(GTValues.L))
                .intervalPressure(new Integer[]{ATMOSPHERIC_PRESSURE * 10, ATMOSPHERIC_PRESSURE * 15})
                .intervalTemperature(new Integer[]{Titanium.getFluid().getTemperature(), Titanium.getFluid().getTemperature() + 500})
                .pressurizedFluidStack(Hydrogen.getFluid(1))
                .duration(200).EUt(VA[MV]).buildAndRegister();

        //TiF3 + 3 H2 -> Ti + 3 HF with HF recovery
        CONVERTING_RECIPES.recipeBuilder()
                .notConsumable(TKCYAMetaItems.GAS_COLLECTOR.getStackForm())
                .fluidOutputs(HydrogenFluoride.getFluid(GTValues.L * 3))
                .fluidInputs(TitaniumTrifluoride.getFluid(GTValues.L * 4))
                .fluidOutputs(Titanium.getFluid(GTValues.L))
                .intervalPressure(new Integer[]{ATMOSPHERIC_PRESSURE * 10, ATMOSPHERIC_PRESSURE * 15})
                .intervalTemperature(new Integer[]{Titanium.getFluid().getTemperature(), Titanium.getFluid().getTemperature() + 500})
                .pressurizedFluidStack(Hydrogen.getFluid(1))
                .duration(200).EUt(VA[MV]).buildAndRegister();

    }
}
