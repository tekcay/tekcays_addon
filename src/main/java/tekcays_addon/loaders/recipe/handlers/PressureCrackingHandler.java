package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.material.Material;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.api.unification.TKCYAMaterials.*;
import static tekcays_addon.api.recipes.TKCYARecipeMaps.PRESSURE_CRACKING;
import static tekcays_addon.api.utils.TKCYAValues.ATMOSPHERIC_PRESSURE;

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

        PRESSURE_CRACKING.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(1))
                .fluidInputs(Methane.getFluid(1000))
                .fluidOutputs(LightlySteamCrackedMethane.getFluid(1000))
                .minPressure(ATMOSPHERIC_PRESSURE * 10)
                .maxPressure(ATMOSPHERIC_PRESSURE * 12)
                .temperature(500)
                .gas(Steam)
                .duration(120).EUt(120).buildAndRegister();

        PRESSURE_CRACKING.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(2))
                .fluidInputs(Methane.getFluid(1000))
                .fluidOutputs(ModeratelySteamCrackedMethane.getFluid(1000))
                .minPressure(ATMOSPHERIC_PRESSURE * 50)
                .maxPressure(ATMOSPHERIC_PRESSURE * 55)
                .temperature(600)
                .gas(Steam)
                .duration(120).EUt(180).buildAndRegister();

        PRESSURE_CRACKING.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(3))
                .fluidInputs(Methane.getFluid(1000))
                .fluidOutputs(SeverelySteamCrackedMethane.getFluid(1000))
                .minPressure(ATMOSPHERIC_PRESSURE * 10)
                .maxPressure(ATMOSPHERIC_PRESSURE * 12)
                .temperature(700)
                .gas(Steam)
                .duration(120).EUt(240).buildAndRegister();

    }

    private static void lightlyCrack(Material raw, Material hydroCracked, Material steamCracked) {

        PRESSURE_CRACKING.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(1))
                .fluidInputs(raw.getFluid(1000))
                .fluidOutputs(hydroCracked.getFluid(1000))
                .minPressure(ATMOSPHERIC_PRESSURE * 10)
                .maxPressure(ATMOSPHERIC_PRESSURE * 12)
                .temperature(400)
                .gas(Hydrogen)
                .duration(80).EUt(VA[MV]).buildAndRegister();

        PRESSURE_CRACKING.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(2))
                .fluidInputs(raw.getFluid(1000))
                .fluidOutputs(steamCracked.getFluid(1000))
                .minPressure(ATMOSPHERIC_PRESSURE * 10)
                .maxPressure(ATMOSPHERIC_PRESSURE * 12)
                .temperature(400)
                .gas(Steam)
                .duration(80).EUt(240).buildAndRegister();
    }

    private static void moderatelyCrack(Material raw, Material hydroCracked, Material steamCracked) {

        PRESSURE_CRACKING.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(3))
                .fluidInputs(raw.getFluid(1000))
                .fluidOutputs(hydroCracked.getFluid(1000))
                .minPressure(ATMOSPHERIC_PRESSURE * 50)
                .maxPressure(ATMOSPHERIC_PRESSURE * 55)
                .temperature(500)
                .gas(Hydrogen)
                .duration(120).EUt(180).buildAndRegister();

        PRESSURE_CRACKING.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(4))
                .fluidInputs(raw.getFluid(1000))
                .fluidOutputs(steamCracked.getFluid(1000))
                .minPressure(ATMOSPHERIC_PRESSURE * 50)
                .maxPressure(ATMOSPHERIC_PRESSURE * 55)
                .temperature(500)
                .gas(Steam)
                .duration(120).EUt(360).buildAndRegister();
    }

    private static void severelyCrack(Material raw, Material hydroCracked, Material steamCracked) {

        PRESSURE_CRACKING.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(5))
                .fluidInputs(raw.getFluid(1000))
                .fluidOutputs(hydroCracked.getFluid(1000))
                .minPressure(ATMOSPHERIC_PRESSURE * 100)
                .maxPressure(ATMOSPHERIC_PRESSURE * 120)
                .temperature(600)
                .gas(Hydrogen)
                .duration(160).EUt(240).buildAndRegister();

        PRESSURE_CRACKING.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(6))
                .fluidInputs(raw.getFluid(1000))
                .fluidOutputs(steamCracked.getFluid(1000))
                .minPressure(ATMOSPHERIC_PRESSURE * 100)
                .maxPressure(ATMOSPHERIC_PRESSURE * 120)
                .temperature(600)
                .gas(Steam)
                .duration(160).EUt(VA[HV]).buildAndRegister();
    }
    
}
