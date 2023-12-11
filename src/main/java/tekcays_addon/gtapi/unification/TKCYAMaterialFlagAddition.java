package tekcays_addon.gtapi.unification;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialFlags;
import tekcays_addon.common.TKCYAConfigHolder;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static tekcays_addon.gtapi.consts.TKCYAValues.DRUM_MATERIALS;
import static tekcays_addon.gtapi.consts.TKCYAValues.POLYMERS;
import static tekcays_addon.gtapi.unification.material.info.TKCYAMaterialFlags.*;

public class TKCYAMaterialFlagAddition {

    public static void init() {

        // Foils
        Materials.Titanium.addFlags(GENERATE_FOIL);

        if (TKCYAConfigHolder.harderStuff.enableRoastingOverhaul) {
            //To force cinnabar roasting
            Redstone.addFlags(DISABLE_DECOMPOSITION);
            Cinnabar.addFlags(DISABLE_DECOMPOSITION);
        }

        // For electrode
        Carbon.addFlags(MaterialFlags.GENERATE_LONG_ROD, MaterialFlags.NO_SMELTING, MaterialFlags.NO_SMASHING);


        //Molds Check with MOLD_MATERIALS
        //MOLD_MATERIALS.forEach(material -> material.addFlags(GENERATE_MOLDS));

        //Generate curved plate
        for (Material m : DRUM_MATERIALS) {
            if (m.hasFlag(GENERATE_ROTOR)) continue;
            if (m.hasFlags(GENERATE_BOLT_SCREW, GENERATE_PLATE)) m.addFlags(GENERATE_CURVED_PLATE);
        }

        Invar.addFlags(GENERATE_ROTOR);

        //Springs
        StainlessSteel.addFlags(GENERATE_SPRING);
        Titanium.addFlags(GENERATE_SPRING);

        //For molds
        Carbon.addFlags(GENERATE_PLATE);

        HydrochloricAcid.addFlags(BATH_FLUID);
        HydrofluoricAcid.addFlags(BATH_FLUID);
        SulfuricAcid.addFlags(BATH_FLUID);
    }


    public static void polymersInit() {
        POLYMERS.forEach(material -> {
            material.addFlags(POLYMER, GENERATE_LONG_ROD, GENERATE_PLATE, GENERATE_FOIL, NO_SMASHING);
            material.setFormula(String.format("(%s)n", material.getChemicalFormula()));
        });
    }
}
