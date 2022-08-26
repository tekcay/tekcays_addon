package tekcays_addon.loaders.recipe.handlers;

import gregicality.science.api.GCYSValues;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.material.Material;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.api.recipes.TKCYARecipeMaps.PRESSURE_CRACKING;

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
        
    }

    private static void lightlyCrack(Material raw, Material hydroCracked, Material steamCracked) {

        PRESSURE_CRACKING.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(1))
                .fluidInputs(raw.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(2000))
                .fluidOutputs(hydroCracked.getFluid(1000))
                .pressure(GCYSValues.EARTH_PRESSURE * 10)
                .temperature(400)
                .duration(80).EUt(VA[MV]).buildAndRegister();

        PRESSURE_CRACKING.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(1))
                .fluidInputs(raw.getFluid(1000))
                .fluidInputs(Steam.getFluid(1000))
                .fluidOutputs(steamCracked.getFluid(1000))
                .pressure(GCYSValues.EARTH_PRESSURE * 10)
                .temperature(400)
                .duration(80).EUt(240).buildAndRegister();
    }

    private static void moderatelyCrack(Material raw, Material hydroCracked, Material steamCracked) {

        PRESSURE_CRACKING.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(2))
                .fluidInputs(raw.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(4000))
                .fluidOutputs(hydroCracked.getFluid(1000))
                .pressure(GCYSValues.EARTH_PRESSURE * 50)
                .temperature(500)
                .duration(120).EUt(180).buildAndRegister();

        PRESSURE_CRACKING.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(2))
                .fluidInputs(raw.getFluid(1000))
                .fluidInputs(Steam.getFluid(1000))
                .fluidOutputs(steamCracked.getFluid(1000))
                .pressure(GCYSValues.EARTH_PRESSURE * 50)
                .temperature(500)
                .duration(120).EUt(360).buildAndRegister();
    }

    private static void severelyCrack(Material raw, Material hydroCracked, Material steamCracked) {

        PRESSURE_CRACKING.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(2))
                .fluidInputs(raw.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(6000))
                .fluidOutputs(hydroCracked.getFluid(1000))
                .pressure(GCYSValues.EARTH_PRESSURE * 100)
                .temperature(600)
                .duration(160).EUt(240).buildAndRegister();

        PRESSURE_CRACKING.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(3))
                .fluidInputs(raw.getFluid(1000))
                .fluidInputs(Steam.getFluid(1000))
                .fluidOutputs(steamCracked.getFluid(1000))
                .pressure(GCYSValues.EARTH_PRESSURE * 100)
                .temperature(600)
                .duration(160).EUt(VA[HV]).buildAndRegister();
    }
    
}
