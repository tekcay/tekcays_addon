package tekcays_addon.api.unification;

import gregtech.api.unification.material.Material;
import tekcays_addon.api.unification.material.info.TKCYAMaterialIconSet;

import static tekcays_addon.api.utils.TKCYAValues.GTCEu_POLYMERS;

public class TKCYAMaterialIconSetAddition {

    public static void polymersInit() {

        for (Material m : GTCEu_POLYMERS) {
            m.setMaterialIconSet(TKCYAMaterialIconSet.POLYMER);
        }

    }

}
