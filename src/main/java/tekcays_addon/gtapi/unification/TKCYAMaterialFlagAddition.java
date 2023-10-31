package tekcays_addon.gtapi.unification;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialFlags;
import tekcays_addon.common.TKCYAConfigHolder;
import tekcays_addon.gtapi.utils.roasting.RoastableMaterial;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static tekcays_addon.gtapi.unification.material.info.TKCYAMaterialFlags.*;
import static tekcays_addon.gtapi.consts.TKCYAValues.*;

public class TKCYAMaterialFlagAddition {

    public static void init() {

        // Foils
        Materials.Titanium.addFlags(GENERATE_FOIL);

        //For Blasting
        BrownLimonite.addFlags(DISABLE_DECOMPOSITION, NO_SMELTING);
        YellowLimonite.addFlags(DISABLE_DECOMPOSITION, NO_SMELTING);
        BandedIron.addFlags(DISABLE_DECOMPOSITION, NO_SMELTING);
        Magnetite.addFlags(DISABLE_DECOMPOSITION, NO_SMELTING);
        Cassiterite.addFlags(DISABLE_DECOMPOSITION, NO_SMELTING);

        if (TKCYAConfigHolder.harderStuff.enableRoastingOverhaul) {

        }


        //For Zinc chain
        Sphalerite.addFlags(DISABLE_DECOMPOSITION, NO_SMELTING);

        //For Bauxite chain
        Bauxite.addFlags(DISABLE_DECOMPOSITION);

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
    }

    public static void polymersInit() {
        POLYMERS.forEach(material -> material.addFlags(POLYMER));
    }
}
