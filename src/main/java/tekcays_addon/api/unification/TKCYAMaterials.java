package tekcays_addon.api.unification;

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

        // Alloys 24201-24299
        TKCYAAlloys.init();

        // Second Degree 24300-24699
        TKCYAOrganicCompounds.init();

        // Second Degree 24700-24800
        TKCYAPolymers.init();

        // Flags
        TKCYAMaterialFlagAddition.init();
    }




    /**
     * No Formula
     */

    public static Material Ceramic;
    public static Material MicaPulp;
    public static Material Fuel;

    /**
     * Misc
     */

    public static Material HotAir;
    public static Material PreciousMetal;
    public static Material GoldAlloy;

    //FluorideChain

    public static Material PotassiumBifluoride;
    public static Material Fluorite;
    public static Material CalciumSulfonate;
    public static Material LithiumFluoride;
    public static Material SodiumFluoride;
    public static Material PotassiumFluoride;
    public static Material LithiumHydroxide;
    public static Material HydrogenFluoride;
    public static Material HydrogenChloride;
    public static Material HydrogenBromide;
    public static Material HydrogenIodide;


    /**
     * Alloys
     */

    public static Material GalvanizedSteel;
    public static Material Monel;

    /**
     * Alloys
     */

    public static Material HighDensityPolyethylene;
    public static Material Polypropylene;



}
