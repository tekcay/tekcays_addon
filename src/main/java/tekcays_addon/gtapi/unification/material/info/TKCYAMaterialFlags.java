package tekcays_addon.gtapi.unification.material.info;

import gregtech.api.unification.material.info.MaterialFlag;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.material.properties.PropertyKey;

import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_PLATE;

public class TKCYAMaterialFlags {


    public static final MaterialFlag GENERATE_CURVED_PLATE = new MaterialFlag.Builder("curvedPlate")
            .requireFlags(GENERATE_PLATE)
            .build();

    /**
     * Flag to prevent melting recipe generation and to add crystallisation recipes"
     */
    public static final MaterialFlag POLYMER = new MaterialFlag.Builder("polymer")
            .requireProps(PropertyKey.FLUID, PropertyKey.DUST)
            .build();

    public static final MaterialFlag GENERATE_FILTRATION = new MaterialFlag.Builder("generate_filtration")
            .requireProps(PropertyKey.FLUID)
            .build();
    public static final MaterialFlag GENERATE_SPIRAL_SEPARATION = new MaterialFlag.Builder("generate_spiral_separation")
            .requireProps(PropertyKey.DUST)
            .build();

    /**
     * For acids and other liquids used to treat minerals
     */
    public static final MaterialFlag BATH_FLUID = new MaterialFlag.Builder("bath_fluid")
            .requireProps(PropertyKey.FLUID)
            .build();

}


