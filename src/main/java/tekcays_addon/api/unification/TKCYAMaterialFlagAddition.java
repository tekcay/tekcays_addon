package tekcays_addon.api.unification;

import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialFlags;
import tekcays_addon.api.unification.material.info.TKCYAMaterialFlags;
import tekcays_addon.common.TKCYAConfigHolder;

public class TKCYAMaterialFlagAddition {

    public static void init() {

        // Foils
        Materials.Titanium.addFlags(MaterialFlags.GENERATE_FOIL);



        //Molds
        if (TKCYAConfigHolder.meltingOverhaul.enableCastingOverhaul) {

            Materials.Tungsten.addFlags(TKCYAMaterialFlags.GENERATE_MOLDS);
            Materials.Carbon.addFlags(TKCYAMaterialFlags.GENERATE_MOLDS);
            TKCYAMaterials.Ceramic.addFlags(TKCYAMaterialFlags.GENERATE_MOLDS);
        }


    }
}
