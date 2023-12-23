package tekcays_addon.gtapi.unification.material.info;

import gregtech.api.unification.material.info.MaterialFlag;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.material.properties.PropertyKey;

import java.util.ArrayList;
import java.util.List;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;

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

    public static final List<MaterialFlag> ALL_PARTS = new ArrayList<>();

    static {
        ALL_PARTS.addAll(EXT2_METAL);
        ALL_PARTS.add(GENERATE_ROTOR);
        ALL_PARTS.add(GENERATE_DENSE);
        ALL_PARTS.add(GENERATE_DOUBLE_PLATE);
        ALL_PARTS.add(GENERATE_FRAME);
        ALL_PARTS.add(GENERATE_SPRING);
        ALL_PARTS.add(GENERATE_SPRING_SMALL);
        ALL_PARTS.add(GENERATE_FOIL);
        ALL_PARTS.add(GENERATE_FINE_WIRE);
        ALL_PARTS.add(GENERATE_GEAR);
        ALL_PARTS.add(GENERATE_SMALL_GEAR);
        ALL_PARTS.add(GENERATE_CURVED_PLATE);
        ALL_PARTS.add(GENERATE_ROUND);
    }


}


