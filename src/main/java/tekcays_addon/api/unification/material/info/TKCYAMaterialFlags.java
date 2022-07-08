package tekcays_addon.api.unification.material.info;

import gregtech.api.unification.material.info.MaterialFlag;

import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_LONG_ROD;
import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_PLATE;

public class TKCYAMaterialFlags {

    public static final MaterialFlag GENERATE_MOLDS = new MaterialFlag.Builder("mold")
            .requireFlags(GENERATE_PLATE)
            .build();

    public static final MaterialFlag GENERATE_ELECTRODE= new MaterialFlag.Builder("electrode")
            .requireFlags(GENERATE_LONG_ROD)
            .build();

}


