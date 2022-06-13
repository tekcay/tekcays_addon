package tekcays_addon.api.unification.material.info;

import gregtech.api.unification.material.info.MaterialFlag;
import gregtech.api.unification.material.properties.PropertyKey;

public class TKCYAMaterialFlags {

    public static final MaterialFlag GENERATE_MOLDS = new MaterialFlag.Builder("mold")
            .requireProps(PropertyKey.INGOT)
            .build();

}


