package tekcays_addon.api.unification;

import tekcays_addon.api.unification.material.materials.TKCYAFirstDegreeMaterials;
import tekcays_addon.api.unification.material.materials.TKCYASecondDegreeMaterials;

public class TKCYAMaterials {

    /*
     * First Degree Materials 3000-3019
     */

    public static void init() {
        // First Degree 3000-3019
        TKCYAFirstDegreeMaterials.init();

        // Second Degree 3020-3039
        TKCYASecondDegreeMaterials.init();

        // Flags
        TKCYAMaterialFlagAddition.init();
    }

    /*
    static {

        EXT2_METAL.add(MELTING);

    }

     */

}
