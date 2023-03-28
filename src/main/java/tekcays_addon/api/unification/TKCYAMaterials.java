package tekcays_addon.api.unification;

import gregtech.api.unification.material.Material;
import tekcays_addon.api.unification.material.info.TKCYAChemicalFormula;
import tekcays_addon.api.unification.material.materials.*;

import static gregtech.api.unification.material.info.MaterialIconSet.FINE;

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
        TKCYAMaterialFlagAddition.polymersInit();

        //Chemical Formula
        TKCYAChemicalFormula.modifyChemicalFormula();

    }




    /**
     * No Formula
     */

    public static Material Ceramic;
    public static Material MicaPulp;
    public static Material Fuel;
    public static Material HotFlueGas;
    public static Material FlueGas;
    public static Material MelonOil;
    public static Material PumpkinOil;
    public static Material MixtureToFilter;
    public static Material LightlySteamCrackedMethane;
    public static Material ModeratelySteamCrackedMethane;
    public static Material SeverelySteamCrackedMethane;
    public static Material ChalcogenAnodeMud;

    /**
     * Misc
     */

    public static Material HotAir;
    public static Material VeryHotAir;

    //GoldChain
    public static Material PreciousMetal;
    public static Material GoldAlloy;
    public static Material GoldLeach;
    public static Material CopperLeach;

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

    //ZincChain
    public static Material ZincSulfate;
    public static Material ZincLeachingSolution;
    public static Material ZincLeachingResidue;
    public static Material IronSulfate;
    public static Material TannicAcid;
    public static Material GermanicAcidSolution;
    public static Material GermaniumChloride;
    public static Material GermaniumOxide;
    public static Material ZincOxide;


    //Misc
    public static Material PotassiumMetaBisulfite;
    public static Material PotassiumHydroxide;


    //More roastable
    public static Material Kesterite;
    public static Material Stannite;
    public static Material Arsenopyrite;

    //Roasting outputs
    public static Material Cuprite;

    //BauxiteChain
    public static Material PotassiumAluminate;
    public static Material SodiumAluminate;
    public static Material AluminiumFluoride;
    public static Material Cryolite;
    public static Material HexafluorosilicAcid;
    public static Material AluminiumHydroxide;

    //ChromiteChain
    public static Material SodiumChromate;
    public static Material SodiumDichromate;
    public static Material ChromiumOxide;
    public static Material SodiumCarbonate;
    public static Material SodiumSulfate;
    public static Material PotassiumSulfate;

    //Molybdenum chain
    public static Material MolybdenumTrioxide;
    public static Material MolybdenumFlue;
    
    /**
     * Alloys
     */

    public static Material GalvanizedSteel;
    public static Material PigIron;
    public static Material Monel;
    public static Material Constantan;
    public static Material SiliconCarbide;

    /**
     * Polymers
     */

    public static Material HighDensityPolyethylene;
    public static Material Polypropylene;


}
