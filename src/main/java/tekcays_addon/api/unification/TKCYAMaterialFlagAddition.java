package tekcays_addon.api.unification;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialFlags;
import tekcays_addon.api.utils.roasting.RoastableMaterial;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static tekcays_addon.api.unification.material.info.TKCYAMaterialFlags.*;
import static tekcays_addon.api.utils.TKCYAValues.*;
import static tekcays_addon.loaders.recipe.handlers.RoastingHandler.ROASTABLE_MATERIALS;

public class TKCYAMaterialFlagAddition {

    public static void init() {

        // Foils
        Materials.Titanium.addFlags(GENERATE_FOIL);

        //For Blasting
        BrownLimonite.addFlags(DISABLE_DECOMPOSITION, NO_SMELTING);
        YellowLimonite.addFlags(DISABLE_DECOMPOSITION, NO_SMELTING);
        Hematite.addFlags(DISABLE_DECOMPOSITION, NO_SMELTING);
        Magnetite.addFlags(DISABLE_DECOMPOSITION, NO_SMELTING);
        Cassiterite.addFlags(DISABLE_DECOMPOSITION, NO_SMELTING);


        //For Zinc chain
        Sphalerite.addFlags(DISABLE_DECOMPOSITION, NO_SMELTING);

        //For Bauxite chain
        Bauxite.addFlags(DISABLE_DECOMPOSITION);

        //For Roasting
        for (RoastableMaterial rm : ROASTABLE_MATERIALS) {
            Material m = rm.getMaterial();
            if (!m.hasFlag(DISABLE_DECOMPOSITION)) m.addFlags(DISABLE_DECOMPOSITION);
            if (!m.hasFlag(NO_SMELTING)) m.addFlags(NO_SMELTING);
        }

        // For electrode
        Carbon.addFlags(MaterialFlags.GENERATE_LONG_ROD, MaterialFlags.NO_SMELTING, MaterialFlags.NO_SMASHING);


        //Molds Check with MOLD_MATERIALS
        MOLD_MATERIALS.forEach(material -> material.addFlags(GENERATE_MOLDS));

        //Generate curved plate
        for (Material m : DRUM_MATERIALS) {
            if (m.hasFlag(GENERATE_ROTOR)) continue;
            if (m.hasFlags(GENERATE_BOLT_SCREW, GENERATE_PLATE)) m.addFlags(GENERATE_CURVED_PLATE);
        }

        Invar.addFlags(GENERATE_ROTOR);
    }

    public static void polymersInit() {
        POLYMERS.forEach(material -> material.addFlags(POLYMER));
    }
}
