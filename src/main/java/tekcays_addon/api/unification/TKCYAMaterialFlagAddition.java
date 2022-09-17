package tekcays_addon.api.unification;


import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialFlags;
import tekcays_addon.api.utils.roasting.RoastableMaterial;


import static gregicality.science.api.unification.materials.GCYSMaterials.ZincOxide;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static tekcays_addon.api.unification.TKCYAMaterials.Ceramic;
import static tekcays_addon.api.unification.material.info.TKCYAMaterialFlags.GENERATE_MOLDS;
import static tekcays_addon.api.unification.material.info.TKCYAMaterialFlags.POLYMER;
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
        ZincOxide.addFlags(DISABLE_DECOMPOSITION);

        //For Roasting
        for (RoastableMaterial rm : ROASTABLE_MATERIALS) {
            Material m = rm.getMaterial();
            if (!m.hasFlag(DISABLE_DECOMPOSITION)) m.addFlags(DISABLE_DECOMPOSITION);
            if (!m.hasFlag(NO_SMELTING)) m.addFlags(NO_SMELTING);
        }

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
