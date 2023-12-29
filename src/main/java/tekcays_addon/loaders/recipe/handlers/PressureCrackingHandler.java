package tekcays_addon.loaders.recipe.handlers;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.gtapi.consts.TKCYAValues.ATMOSPHERIC_PRESSURE;
import static tekcays_addon.gtapi.consts.TKCYAValues.MINIMUM_FLUID_STACK_AMOUNT;
import static tekcays_addon.gtapi.recipes.TKCYARecipeMaps.PRESSURE_CRACKING;
import static tekcays_addon.gtapi.unification.TKCYAMaterials.*;

import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.material.Material;

public class PressureCrackingHandler {

    public static void init() {
        moderatelyCrack(Ethane, HydroCrackedEthane, SteamCrackedEthane);
        moderatelyCrack(Ethylene, HydroCrackedEthylene, SteamCrackedEthylene);
        moderatelyCrack(Propene, HydroCrackedPropene, SteamCrackedPropene);
        moderatelyCrack(Propane, HydroCrackedPropane, SteamCrackedPropane);
        moderatelyCrack(Butane, HydroCrackedButane, SteamCrackedButane);
        moderatelyCrack(Butene, HydroCrackedButene, SteamCrackedButene);
        moderatelyCrack(Butadiene, HydroCrackedButadiene, SteamCrackedButadiene);

        lightlyCrack(HeavyFuel, LightlyHydroCrackedHeavyFuel, LightlySteamCrackedHeavyFuel);
        severelyCrack(HeavyFuel, SeverelyHydroCrackedHeavyFuel, SeverelySteamCrackedHeavyFuel);
        lightlyCrack(LightFuel, LightlyHydroCrackedLightFuel, LightlySteamCrackedLightFuel);
        severelyCrack(LightFuel, SeverelyHydroCrackedLightFuel, SeverelySteamCrackedLightFuel);
        lightlyCrack(Naphtha, LightlyHydroCrackedNaphtha, LightlySteamCrackedNaphtha);
        severelyCrack(Naphtha, SeverelyHydroCrackedNaphtha, SeverelySteamCrackedNaphtha);
        lightlyCrack(RefineryGas, LightlyHydroCrackedGas, LightlySteamCrackedGas);
        severelyCrack(RefineryGas, SeverelyHydroCrackedGas, SeverelySteamCrackedGas);

        // 2 CH4 + H2O -> 2 CO + 3 H2
        PRESSURE_CRACKING.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(1))
                .fluidInputs(Methane.getFluid(1000))
                .fluidOutputs(LightlySteamCrackedMethane.getFluid(1000))
                .intervalPressure(new Integer[] { ATMOSPHERIC_PRESSURE * 10, ATMOSPHERIC_PRESSURE * 12 })
                .intervalTemperature(new Integer[] { 400, 500 })
                .pressurizedFluidStack(Steam.getFluid(MINIMUM_FLUID_STACK_AMOUNT))
                .duration(120).EUt(120).buildAndRegister();

        // CH4 + H2O -> CO + 3 H2
        PRESSURE_CRACKING.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(2))
                .fluidInputs(Methane.getFluid(1000))
                .fluidOutputs(ModeratelySteamCrackedMethane.getFluid(1000))
                .intervalPressure(new Integer[] { ATMOSPHERIC_PRESSURE * 50, ATMOSPHERIC_PRESSURE * 55 })
                .intervalTemperature(new Integer[] { 500, 600 })
                .pressurizedFluidStack(Steam.getFluid(MINIMUM_FLUID_STACK_AMOUNT))
                .duration(120).EUt(180).buildAndRegister();

        // CH4 + 2 H2O -> CO2 + 4 H2
        PRESSURE_CRACKING.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(3))
                .fluidInputs(Methane.getFluid(1000))
                .fluidOutputs(SeverelySteamCrackedMethane.getFluid(1000))
                .intervalPressure(new Integer[] { ATMOSPHERIC_PRESSURE * 100, ATMOSPHERIC_PRESSURE * 120 })
                .intervalTemperature(new Integer[] { 700, 800 })
                .pressurizedFluidStack(Steam.getFluid(MINIMUM_FLUID_STACK_AMOUNT))
                .duration(120).EUt(240).buildAndRegister();
    }

    private static void lightlyCrack(Material raw, Material hydroCracked, Material steamCracked) {
        PRESSURE_CRACKING.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(1))
                .fluidInputs(raw.getFluid(1000))
                .fluidOutputs(hydroCracked.getFluid(1000))
                .intervalPressure(new Integer[] { ATMOSPHERIC_PRESSURE * 10, ATMOSPHERIC_PRESSURE * 12 })
                .intervalTemperature(new Integer[] { 400, 500 })
                .pressurizedFluidStack(Hydrogen.getFluid(MINIMUM_FLUID_STACK_AMOUNT))
                .duration(80).EUt(VA[MV]).buildAndRegister();

        PRESSURE_CRACKING.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(2))
                .fluidInputs(raw.getFluid(1000))
                .fluidOutputs(steamCracked.getFluid(1000))
                .intervalPressure(new Integer[] { ATMOSPHERIC_PRESSURE * 10, ATMOSPHERIC_PRESSURE * 12 })
                .intervalTemperature(new Integer[] { 400, 500 })
                .pressurizedFluidStack(Steam.getFluid(MINIMUM_FLUID_STACK_AMOUNT))
                .duration(80).EUt(240).buildAndRegister();
    }

    private static void moderatelyCrack(Material raw, Material hydroCracked, Material steamCracked) {
        PRESSURE_CRACKING.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(3))
                .fluidInputs(raw.getFluid(1000))
                .fluidOutputs(hydroCracked.getFluid(1000))
                .intervalPressure(new Integer[] { ATMOSPHERIC_PRESSURE * 50, ATMOSPHERIC_PRESSURE * 55 })
                .intervalTemperature(new Integer[] { 500, 600 })
                .pressurizedFluidStack(Hydrogen.getFluid(MINIMUM_FLUID_STACK_AMOUNT))
                .duration(120).EUt(180).buildAndRegister();

        PRESSURE_CRACKING.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(4))
                .fluidInputs(raw.getFluid(1000))
                .fluidOutputs(steamCracked.getFluid(1000))
                .intervalPressure(new Integer[] { ATMOSPHERIC_PRESSURE * 50, ATMOSPHERIC_PRESSURE * 55 })
                .intervalTemperature(new Integer[] { 500, 600 })
                .pressurizedFluidStack(Steam.getFluid(MINIMUM_FLUID_STACK_AMOUNT))
                .duration(120).EUt(360).buildAndRegister();
    }

    private static void severelyCrack(Material raw, Material hydroCracked, Material steamCracked) {
        PRESSURE_CRACKING.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(5))
                .fluidInputs(raw.getFluid(1000))
                .fluidOutputs(hydroCracked.getFluid(1000))
                .intervalPressure(new Integer[] { ATMOSPHERIC_PRESSURE * 100, ATMOSPHERIC_PRESSURE * 120 })
                .intervalTemperature(new Integer[] { 700, 800 })
                .pressurizedFluidStack(Hydrogen.getFluid(MINIMUM_FLUID_STACK_AMOUNT))
                .duration(160).EUt(240).buildAndRegister();

        PRESSURE_CRACKING.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(6))
                .fluidInputs(raw.getFluid(1000))
                .fluidOutputs(steamCracked.getFluid(1000))
                .intervalPressure(new Integer[] { ATMOSPHERIC_PRESSURE * 100, ATMOSPHERIC_PRESSURE * 120 })
                .intervalTemperature(new Integer[] { 700, 800 })
                .pressurizedFluidStack(Steam.getFluid(MINIMUM_FLUID_STACK_AMOUNT))
                .duration(160).EUt(VA[HV]).buildAndRegister();
    }
}
