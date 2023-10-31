package tekcays_addon.gtapi.unification;

import gregtech.api.unification.material.Material;
import tekcays_addon.common.TKCYAConfigHolder;
import tekcays_addon.gtapi.unification.material.info.TKCYAChemicalFormula;
import tekcays_addon.gtapi.unification.material.materials.*;

public class TKCYAMaterials {


    /*
     * First Degree Materials 3000-3019
     */

    public static void init() {

        int id = 24000;

        // NoFormula 24000-24100
        id = TKCYANoFormulaMaterials.init(id);

        // 24101-24200
        id = TKCYAMiscMaterials.init(id);

        // Alloys 24201-24299
        id = TKCYAAlloys.init(id);

        // Second Degree 24300-24699
        id = TKCYAOrganicCompounds.init(id);

        // Second Degree 24700-24800
        id = TKCYAPolymers.init(id);

        //24800-24900
        if (TKCYAConfigHolder.harderStuff.enableRoastingOverhaul) TKCYARoastMaterials.init();

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
    public static Material PotassiumBisulfate;
    public static Material ChloroauricAcid;

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

    //Misc
    public static Material PotassiumMetaBisulfite;
    public static Material PotassiumHydroxide;
    
    //Vitriols
    public static Material BlueVitriol;
    public static Material GrayVitriol;
    public static Material PinkVitriol;
    public static Material GreenVitriol;
    public static Material WhiteVitriol;
    public static Material ClayVitriol;
    public static Material CyanVitriol;
    public static Material RedVitriol;


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
    public static Material Alumina;

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

    /**
     * Roasted
     */
    public static Material RoastedTetrahedrite;
    public static Material RoastedCobaltite;
    public static Material RoastedGalena;
    public static Material SilverOxide;
    public static Material RoastedChalcopyrite;
    public static Material RoastedKesterite;
    public static Material RoastedStannite;
    public static Material RoastedArsenopyrite;
    public static Material RoastedBornite;


}
