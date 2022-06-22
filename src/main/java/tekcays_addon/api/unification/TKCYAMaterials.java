package tekcays_addon.api.unification;

import gregicality.multiblocks.api.unification.properties.AlloyBlastProperty;
import gregtech.api.unification.material.Material;
import tekcays_addon.api.unification.material.materials.*;

public class TKCYAMaterials {


    /*
     * First Degree Materials 3000-3019
     */

    public static void init() {


        // NoFormula 24000-24100
        TKCYANoFormulaMaterials.init();

        // 24101-24200
        TKCYAMiscMaterials.init();

        // Alloys 3000-3019
        TKCYAAlloys.init();

        // Second Degree 3020-3039
        TKCYAOrganicCompounds.init();



        // Flags
        TKCYAMaterialFlagAddition.init();
    }


    public static Material HotAir;

    /**
     * No Formula
     */

    public static Material Ceramic;
    public static Material MicaPulp;
    public static Material Fuel;

    /**
     * Misc
     */



}
