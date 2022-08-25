package tekcays_addon.api.unification;


import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialFlags;


import static gregicality.science.api.unification.materials.GCYSMaterials.ZincOxide;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static tekcays_addon.api.unification.TKCYAMaterials.Ceramic;
import static tekcays_addon.api.unification.material.info.TKCYAMaterialFlags.GENERATE_MOLDS;
import static tekcays_addon.api.unification.material.info.TKCYAMaterialFlags.POLYMER;

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
        ZincOxide.addFlags(DISABLE_DECOMPOSITION);

        // For electrode
        Carbon.addFlags(MaterialFlags.GENERATE_LONG_ROD, MaterialFlags.NO_SMELTING, MaterialFlags.NO_SMASHING);


        //Molds Check with MOLD_MATERIALS
        Steel.addFlags(GENERATE_MOLDS);
        Ceramic.addFlags(GENERATE_MOLDS);
        Tungsten.addFlags(GENERATE_MOLDS);
        Carbon.addFlags(GENERATE_MOLDS);

    }

    public static void polymersInit() {

        Polybenzimidazole.addFlags(POLYMER);
        Polycaprolactam.addFlags(POLYMER);
        Polydimethylsiloxane.addFlags(POLYMER);
        PolyphenyleneSulfide.addFlags(POLYMER);
        Polytetrafluoroethylene.addFlags(POLYMER);
        PolyvinylAcetate.addFlags(POLYMER);
        PolyvinylButyral.addFlags(POLYMER);
        PolyvinylChloride.addFlags(POLYMER);
        Polyethylene.addFlags(POLYMER);

    }
}
