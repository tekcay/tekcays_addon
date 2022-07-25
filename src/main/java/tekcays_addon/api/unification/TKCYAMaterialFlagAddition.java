package tekcays_addon.api.unification;


import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialFlags;


import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static tekcays_addon.api.unification.TKCYAMaterials.Ceramic;
import static tekcays_addon.api.unification.material.info.TKCYAMaterialFlags.GENERATE_MOLDS;


public class TKCYAMaterialFlagAddition {




    public static void init() {

        // Foils
        Materials.Titanium.addFlags(GENERATE_FOIL);

        //For Blasting
        BrownLimonite.addFlags(DISABLE_DECOMPOSITION, NO_SMELTING);
        YellowLimonite.addFlags(DISABLE_DECOMPOSITION, NO_SMELTING);
        BandedIron.addFlags(DISABLE_DECOMPOSITION, NO_SMELTING);
        Magnetite.addFlags(DISABLE_DECOMPOSITION, NO_SMELTING);

        // For electrode
        Carbon.addFlags(MaterialFlags.GENERATE_LONG_ROD, MaterialFlags.NO_SMELTING, MaterialFlags.NO_SMASHING);


        //Molds Check with MOLD_MATERIALS
        Steel.addFlags(GENERATE_MOLDS);
        Ceramic.addFlags(GENERATE_MOLDS);
        Tungsten.addFlags(GENERATE_MOLDS);
        Carbon.addFlags(GENERATE_MOLDS);



    }
}
