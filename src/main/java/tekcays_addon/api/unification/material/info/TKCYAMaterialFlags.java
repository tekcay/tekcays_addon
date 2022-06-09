package tekcays_addon.api.unification.material.info;

import gregtech.api.unification.material.info.MaterialFlag;
import gregtech.api.unification.material.properties.PropertyKey;

public class TKCYAMaterialFlags {

    public static final MaterialFlag MELTING = new MaterialFlag.Builder("melting")
            .requireProps(PropertyKey.DUST, PropertyKey.INGOT, PropertyKey.FLUID)
            .build();

}


