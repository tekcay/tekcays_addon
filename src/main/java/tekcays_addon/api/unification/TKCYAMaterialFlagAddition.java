package tekcays_addon.api.unification;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialFlags;
import tekcays_addon.api.unification.material.info.TKCYAMaterialFlags;
import tekcays_addon.common.TKCYAConfigHolder;

import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.api.utils.TKCYAValues.MOLD_MATERIALS;

public class TKCYAMaterialFlagAddition {




    public static void init() {

        // Foils
        Materials.Titanium.addFlags(MaterialFlags.GENERATE_FOIL);

        // For electrode
        Carbon.addFlags(MaterialFlags.GENERATE_LONG_ROD, MaterialFlags.NO_SMELTING, MaterialFlags.NO_SMASHING);

        //Molds
        if (TKCYAConfigHolder.meltingOverhaul.enableCastingOverhaul) {

            for (Material m : MOLD_MATERIALS) {
                m.addFlags(TKCYAMaterialFlags.GENERATE_MOLDS);
            }
        }
    }
}
