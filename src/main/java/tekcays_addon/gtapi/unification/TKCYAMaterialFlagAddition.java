package tekcays_addon.gtapi.unification;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static tekcays_addon.gtapi.consts.TKCYAValues.DRUM_MATERIALS;
import static tekcays_addon.gtapi.consts.TKCYAValues.POLYMERS;
import static tekcays_addon.gtapi.unification.material.info.TKCYAMaterialFlags.*;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialFlags;
import tekcays_addon.common.TKCYAConfigHolder;
import tekcays_addon.gtapi.consts.FlammableFluidMaterials;
import tekcays_addon.gtapi.consts.ToxicFluidMaterials;

public class TKCYAMaterialFlagAddition {

    public static void init() {
        // Foils
        Materials.Titanium.addFlags(GENERATE_FOIL);

        if (TKCYAConfigHolder.harderStuff.enableRoastingOverhaul) {
            // To force cinnabar roasting
            Redstone.addFlags(DISABLE_DECOMPOSITION);
            Cinnabar.addFlags(DISABLE_DECOMPOSITION);
        }

        // For electrode
        Carbon.addFlags(MaterialFlags.GENERATE_LONG_ROD, MaterialFlags.NO_SMELTING, MaterialFlags.NO_SMASHING);

        // Molds Check with MOLD_MATERIALS
        // MOLD_MATERIALS.forEach(material -> material.addFlags(GENERATE_MOLDS));

        // Generate curved plate
        for (Material m : DRUM_MATERIALS) {
            if (m.hasFlag(GENERATE_ROTOR)) continue;
            if (m.hasFlags(GENERATE_BOLT_SCREW, GENERATE_PLATE)) m.addFlags(GENERATE_CURVED_PLATE);
        }

        Invar.addFlags(GENERATE_ROTOR);

        // Springs
        StainlessSteel.addFlags(GENERATE_SPRING);
        Titanium.addFlags(GENERATE_SPRING);

        // For molds
        Carbon.addFlags(GENERATE_PLATE);

        HydrochloricAcid.addFlags(BATH_FLUID);
        HydrofluoricAcid.addFlags(BATH_FLUID);
        SulfuricAcid.addFlags(BATH_FLUID);

        // For rotors
        Chrome.addFlags(GENERATE_ROUND);
        Polyethylene.addFlags(GENERATE_ROUND);
        Iron.addFlags(GENERATE_ROUND);
        Tin.addFlags(GENERATE_ROUND);
        Titanium.addFlags(GENERATE_ROUND);
        Darmstadtium.addFlags(GENERATE_ROUND);
        Bronze.addFlags(GENERATE_ROUND);
        Lead.addFlags(GENERATE_ROUND);
        Steel.addFlags(GENERATE_ROUND);
        Invar.addFlags(GENERATE_ROUND);
        StainlessSteel.addFlags(GENERATE_ROUND);
        TungstenSteel.addFlags(GENERATE_ROUND);
        NaquadahAlloy.addFlags(GENERATE_ROUND);
        RhodiumPlatedPalladium.addFlags(GENERATE_ROUND);

        // For fluid properties
        // DangerFluids.init();
        FlammableFluidMaterials.addFlag();
        ToxicFluidMaterials.addFlag();
    }

    public static void polymersInit() {
        POLYMERS.forEach(material -> {
            material.addFlags(POLYMER, GENERATE_LONG_ROD, GENERATE_PLATE, GENERATE_FOIL, NO_SMASHING);
            material.setFormula(String.format("(%s)n", material.getChemicalFormula()));
        });
    }
}
