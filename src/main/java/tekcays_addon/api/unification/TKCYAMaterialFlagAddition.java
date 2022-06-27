package tekcays_addon.api.unification;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;

import tekcays_addon.api.unification.material.info.TKCYAMaterialFlags;
import tekcays_addon.common.TKCYAConfigHolder;

import java.util.ArrayList;
import java.util.List;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static tekcays_addon.api.unification.TKCYAMaterials.Ceramic;

public class TKCYAMaterialFlagAddition {


    public static final Material[] MOLD_MATERIALS = new Material[]{Steel, Tungsten, Carbon, Ceramic};

    public static void init() {

        // Foils
        Materials.Titanium.addFlags(GENERATE_FOIL);

        BrownLimonite.addFlags(DISABLE_DECOMPOSITION, NO_SMELTING);
        YellowLimonite.addFlags(DISABLE_DECOMPOSITION, NO_SMELTING);
        BandedIron.addFlags(DISABLE_DECOMPOSITION, NO_SMELTING);
        Magnetite.addFlags(DISABLE_DECOMPOSITION, NO_SMELTING);

        //Molds
        if (TKCYAConfigHolder.meltingOverhaul.enableCastingOverhaul) {

            for (Material m : MOLD_MATERIALS) {
                m.addFlags(TKCYAMaterialFlags.GENERATE_MOLDS);
            }
        }


    }
}
