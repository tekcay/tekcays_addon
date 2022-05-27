package tekcays_addon.multiblocks.api.unification;

import gregtech.api.unification.material.Material;

public class TKCYAMaterials {

    /*
     * First Degree Materials 3000-3019
     */
    public static Material Stellite100;
    public static Material WatertightSteel;
    public static Material MaragingSteel300;
    public static Material HastelloyC276;
    public static Material HastelloyX;
    public static Material Trinaquadalloy;
    public static Material Zeron100;
    public static Material TitaniumCarbide;
    public static Material TantalumCarbide;
    public static Material MolybdenumDisilicide;

    /*
     * Second Degree Materials 3020-3039
     */
    public static Material HSLASteel;
    public static Material TitaniumTungstenCarbide;
    public static Material IncoloyMA956;

    /*
     * High Degree Materials 3040-3059
     */


    /*
     * Gregicality: 3000-19999
     */
    public static void init() {
        // First Degree 3000-3019
        TKCYAFirstDegreeMaterials.init();

        // Second Degree 3020-3039
        TKCYASecondDegreeMaterials.init();

        // Flags
        TKCYAMaterialFlagAddition.init();
    }
}
