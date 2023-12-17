package tekcays_addon.gtapi.unification.material.materials;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.fluids.attribute.FluidAttributes;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialIconSet;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.DISABLE_DECOMPOSITION;
import static gregtech.api.unification.material.info.MaterialIconSet.*;
import static gregtech.api.util.GTUtility.gregtechId;
import static tekcays_addon.gtapi.unification.TKCYAMaterials.*;
import static tekcays_addon.gtapi.unification.material.info.TKCYAMaterialFlags.GENERATE_FILTRATION;
import static tekcays_addon.gtapi.unification.material.info.TKCYAMaterialFlags.GENERATE_SPIRAL_SEPARATION;

public class TKCYAMiscMaterials {

    public static int init(int id){

        HotAir = new Material.Builder(id++, gregtechId( "hot_air"))
                .gas()
                .flags(DISABLE_DECOMPOSITION)
                .components(Materials.Air, 1)
                .color(0xe08b41)
                .build();

        VeryHotAir = new Material.Builder(id++, gregtechId( "very_hot_air"))
                .gas()
                .liquid(new FluidBuilder().temperature(1800))
                .components(Materials.Air, 1)
                .color(0xf56342)
                .build();

        //GoldChain
        PreciousMetal = new Material.Builder(id++, gregtechId( "precious_metal"))
                .dust().ore()
                .liquid(new FluidBuilder().temperature(1337))
                .addOreByproducts(Materials.Cobalt, Materials.Copper, Materials.Iron)
                .flags(DISABLE_DECOMPOSITION)
                .components(Materials.Gold, 1)
                .iconSet(SHINY)
                .color(0xB99023)
                .build();
        PreciousMetal.setFormula("Au?");

        GoldAlloy = new Material.Builder(id++, gregtechId( "gold_alloy"))
                .ingot().liquid(new FluidBuilder().temperature(1000)) //TODO
                .flags(DISABLE_DECOMPOSITION)
                .components(Materials.Copper, 3, PreciousMetal, 1)
                .iconSet(SHINY)
                .color(0xB99023)
                .build();
        GoldAlloy.setFormula("Cu3Au?", true);

        GoldLeach = new Material.Builder(id++, gregtechId( "gold_leach"))
                .dust()
                .iconSet(SHINY)
                .color(0xB99023)
                .build();
        GoldLeach.setFormula("CuAu?", true);

        CopperLeach = new Material.Builder(id++, gregtechId( "copper_leach"))
                .dust()
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .components(Copper, 27, Lead, 1, Iron, 1, Nickel, 1, Silver, 1)
                .colorAverage()
                .build();
        CopperLeach.setFormula("Cu?", true);

        PotassiumBisulfate = new Material.Builder(id++, gregtechId( "potassium_bisulfate"))
                .dust()
                .iconSet(DULL)
                .components(Potassium, 1, Hydrogen, 1, Sulfur, 1, Oxygen, 4)
                .colorAverage()
                .build();
        PotassiumBisulfate.setFormula("KHSO4", true);

        ChloroauricAcid = new Material.Builder(id++, gregtechId( "chloroauric_acid"))
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID))
                .components(Hydrogen, 1, Gold, 1, Chlorine, 4)
                .colorAverage()
                .build();



        //FluorineChain
        PotassiumBifluoride = new Material.Builder(id++, gregtechId( "potassium_bifluoride"))
                .dust()
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID)
                        .temperature(512))
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(MaterialIconSet.DULL)
                .components(Potassium, 1, Hydrogen, 1, Fluorine, 2)
                .colorAverage()
                .build();

        Fluorite = new Material.Builder(id++, gregtechId( "fluorite"))
                .dust().ore()
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(MaterialIconSet.BRIGHT)
                .components(Calcium, 1, Fluorine, 2)
                .colorAverage()
                .build();

        CalciumSulfonate = new Material.Builder(id++, gregtechId( "calcium_sulfonate"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(MaterialIconSet.BRIGHT)
                .components(Calcium, 1, Sulfur, 1, Oxygen, 4)
                .colorAverage()
                .build();

        LithiumFluoride = new Material.Builder(id++, gregtechId( "lithium_fluoride"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(Lithium, 1, Fluorine, 1)
                .colorAverage()
                .build();

        SodiumFluoride = new Material.Builder(id++, gregtechId( "sodium_fluoride"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(Sodium, 1, Fluorine, 1)
                .colorAverage()
                .build();

        PotassiumFluoride = new Material.Builder(id++, gregtechId( "potassium_fluoride"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(Potassium, 1, Fluorine, 1)
                .colorAverage()
                .build();


        LithiumHydroxide = new Material.Builder(id++, gregtechId( "lithium_hydroxide"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(Lithium, 1, Oxygen, 1, Hydrogen, 1)
                .colorAverage()
                .build();

        HydrogenFluoride = new Material.Builder(id++, gregtechId( "hydrogen_fluoride"))
                .gas()
                .flags(DISABLE_DECOMPOSITION)
                .components(Hydrogen, 1, Fluorine, 1)
                .colorAverage()
                .build();

        HydrogenChloride = new Material.Builder(id++, gregtechId( "hydrogen_chloride"))
                .gas()
                .flags(DISABLE_DECOMPOSITION)
                .components(Hydrogen, 1, Chlorine, 1)
                .colorAverage()
                .build();

        HydrogenBromide = new Material.Builder(id++, gregtechId( "hydrogen_bromide"))
                .gas()
                .flags(DISABLE_DECOMPOSITION)
                .components(Hydrogen, 1, Bromine, 1)
                .colorAverage()
                .build();

        HydrogenIodide = new Material.Builder(id++, gregtechId( "hydrogen_iodide"))
                .gas()
                .flags(DISABLE_DECOMPOSITION)
                .components(Hydrogen, 1, Iodine, 1)
                .colorAverage()
                .build();




        //FREE IDs

        //Zinc chain

        IronSulfate = new Material.Builder(id++, gregtechId( "iron_sulfate"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(Iron, 1, Sulfur, 1, Oxygen, 4)
                .colorAverage()
                .build();

        ZincSulfate = new Material.Builder(id++, gregtechId( "zinc_sulfate"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(Zinc, 1, Sulfur, 1, Oxygen, 4)
                .colorAverage()
                .colorAverage()
                .build();

        ZincLeachingSolution = new Material.Builder(id++, gregtechId( "zinc_leaching_solution"))
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID))
                .color(Germanium.getMaterialRGB() + Oxygen.getMaterialRGB() + Zinc.getMaterialRGB() / 3)
                .build();
        ZincLeachingSolution.setFormula("Zn?");

        ZincLeachingResidue = new Material.Builder(id++, gregtechId( "zinc_leaching_residue"))
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID))
                .color(Germanium.getMaterialRGB() + Oxygen.getMaterialRGB() / 2)
                .build();
        ZincLeachingResidue.setFormula("Ge?");

        GermanicAcidSolution = new Material.Builder(id++, gregtechId( "germanic_acid_solution"))
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID))
                .color(Germanium.getMaterialRGB() + SulfuricAcid.getMaterialRGB() / 2)
                .build();
        GermanicAcidSolution.setFormula("H4GeO4", true);

        TannicAcid = new Material.Builder(id++, gregtechId( "tannic_acid"))
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID))
                .color(554242)
                .build();
        TannicAcid.setFormula("C76H52O46", true);

        GermaniumChloride = new Material.Builder(id++, gregtechId( "germanium_chloride"))
                .dust()
                .components(Germanium, 1, Chlorine, 4)
                .colorAverage()
                .build();

        GermaniumOxide = new Material.Builder(id++, gregtechId( "germanium_oxide"))
                .dust()
                .components(Germanium, 1, Oxygen, 2)
                .colorAverage()
                .build();

        PotassiumMetaBisulfite = new Material.Builder(id++, gregtechId( "potassium_metabisulfite"))
                .dust()
                .components(Potassium, 2, Sulfur, 2, Oxygen, 5)
                .colorAverage()
                .build();
        PotassiumMetaBisulfite.setFormula("K2S2O5", true);

        //More roastable ores
        Kesterite = new Material.Builder(id++, gregtechId( "kesterite"))
                .dust().ore()
                .addOreByproducts(Materials.Cobalt, Materials.Copper, Materials.Iron)
                .flags(DISABLE_DECOMPOSITION)
                .components(Copper, 2, Zinc, 1, Tin, 1, Sulfur, 4)
                .iconSet(DULL)
                .colorAverage()
                .build();

        Stannite = new Material.Builder(id++, gregtechId( "stannite"))
                .dust().ore()
                .addOreByproducts(Materials.Cobalt, Materials.Copper, Materials.Iron)
                .flags(DISABLE_DECOMPOSITION)
                .components(Copper, 2, Iron, 1, Tin, 1, Sulfur, 4)
                .iconSet(DULL)
                .colorAverage()
                .build();

        Arsenopyrite = new Material.Builder(id++, gregtechId( "arsenopyrite"))
                .dust().ore()
                .addOreByproducts(Materials.Cobalt, Materials.Copper, Materials.Iron)
                .flags(DISABLE_DECOMPOSITION)
                .components(Iron, 1, Arsenic, 1, Sulfur, 1)
                .iconSet(DULL)
                .colorAverage()
                .build();

        //Roasting outputs
        Cuprite = new Material.Builder(id++, gregtechId( "cuprite"))
                .dust().ore()
                .addOreByproducts(Materials.Cobalt, Materials.Copper, Materials.Iron)
                .flags(DISABLE_DECOMPOSITION)
                .components(Copper, 2, Oxygen, 1)
                .iconSet(DULL)
                .colorAverage()
                .build();

        //BauxiteChain
        PotassiumAluminate = new Material.Builder(id++, gregtechId( "potassium_aluminate"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(Potassium, 1, Aluminium, 1, Oxygen, 3)
                .iconSet(DULL)
                .colorAverage()
                .build();

        SodiumAluminate = new Material.Builder(id++, gregtechId( "sodium_aluminate"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(Sodium, 1, Aluminium, 1, Oxygen, 3)
                .iconSet(DULL)
                .colorAverage()
                .build();

        AluminiumFluoride = new Material.Builder(id++, gregtechId( "aluminium_fluoride"))
                .dust()
                .liquid(new FluidBuilder().temperature(1560))
                .color((Aluminium.getMaterialRGB() + Fluorine.getMaterialRGB() * 3)/ 4)
                .build();
        AluminiumFluoride.setFormula("AlF3", true);

        Cryolite = new Material.Builder(id++, gregtechId( "cryolite"))
                .liquid(new FluidBuilder().temperature(1285))
                .color((Aluminium.getMaterialRGB() + Fluorine.getMaterialRGB() * 3)/ 4)
                .build();
        Cryolite.setFormula("Na3AlF6", true);

        HexafluorosilicAcid = new Material.Builder(id++, gregtechId( "hexafluorosilic_acid"))
                .fluid()
                .color((Aluminium.getMaterialRGB() + Fluorine.getMaterialRGB() * 3)/ 4)
                .build();
        HexafluorosilicAcid.setFormula("H2SiF6", true);

        AluminiumHydroxide = new Material.Builder(id++, gregtechId( "aluminium_hydroxide"))
                .dust()
                .liquid(new FluidBuilder().temperature(573))
                .components(Aluminium, 1, Oxygen, 3, Hydrogen, 3)
                .flags(DISABLE_DECOMPOSITION)
                .color(Aluminium.getMaterialRGB() + (Oxygen.getMaterialRGB() * 3 + Hydrogen.getMaterialRGB() * 3)/ 7)
                .build();
        AluminiumHydroxide.setFormula("Al(OH)3", true);

        Alumina = new Material.Builder(id++, gregtechId( "alumina"))
                .dust()
                .liquid(new FluidBuilder().temperature(2345))
                .color(Aluminium.getMaterialRGB() * 2 + (Oxygen.getMaterialRGB())/ 5)
                .build();
        Alumina.setFormula("Al2O3", true);



        //Chromite Chain
        SodiumChromate = new Material.Builder(id++, gregtechId( "sodium_chromate"))
                .dust()
                .color(0xe6d62c)
                .build();
        SodiumChromate.setFormula("Na2CrO4", true);

        SodiumDichromate = new Material.Builder(id++, gregtechId( "sodium_dichromate"))
                .dust()
                .color(0xdb822e)
                .build();
        SodiumDichromate.setFormula("Na2Cr2O7", true);

        ChromiumOxide = new Material.Builder(id++, gregtechId( "chromium_oxide"))
                .dust()
                .color(0x69c765)
                .build();
        ChromiumOxide.setFormula("Cr2O3", true);

        SodiumCarbonate = new Material.Builder(id++, gregtechId( "sodium_carbonate"))
                .dust()
                .color(0xdeddd1)
                .build();
        SodiumCarbonate.setFormula("Na2CO3", true);


        PotassiumSulfate = new Material.Builder(id++, gregtechId( "potassium_sulfate"))
                .dust()
                .color(0xdeddd1)
                .build();
        PotassiumSulfate.setFormula("K2SO4", true);

        //Mo chain
        MolybdenumTrioxide = new Material.Builder(id++, gregtechId( "molybdenum_trioxide"))
                .dust()
                .liquid(new FluidBuilder().temperature(795 + 273))
                .color((Molybdenum.getMaterialRGB() + Oxygen.getMaterialRGB()) / 4)
                .build();
        MolybdenumTrioxide.setFormula("MoO3", true);

        MolybdenumFlue = new Material.Builder(id++, gregtechId( "molybdenum_flue"))
                .dust()
                .fluid()
                .color(Molybdenum.getMaterialRGB())
                .build();
        MolybdenumFlue.setFormula("Mo?", true);

        //Vitriols
        BlueVitriol = new Material.Builder(id++, gregtechId( "blue_vitriol"))
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID))
                .color(0x350092)
                .build();
        BlueVitriol.setFormula("H2SO4 + Cu?", true);

        GrayVitriol = new Material.Builder(id++, gregtechId( "gray_vitriol"))
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID))
                .color(0xbab6b6)
                .build();
        GrayVitriol.setFormula("H2SO4 + Mn?", true);

        PinkVitriol = new Material.Builder(id++, gregtechId( "pink_vitriol"))
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID))
                .color(0xf6b7c9)
                .build();
        PinkVitriol.setFormula("H2SO4 + Mg?", true);

        GreenVitriol = new Material.Builder(id++, gregtechId( "green_vitriol"))
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID))
                .color(0x5ba304)
                .build();
        GreenVitriol.setFormula("H2SO4 + Fe?", true);

        WhiteVitriol = new Material.Builder(id++, gregtechId( "white_vitriol"))
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID))
                .color(0xf7f7f7)
                .build();
        WhiteVitriol.setFormula("H2SO4 + Zn?", true);

        ClayVitriol = new Material.Builder(id++, gregtechId( "clay_vitriol"))
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID))
                .color(0xc49963)
                .build();
        ClayVitriol.setFormula("H2SO4 + Al2O3?", true);

        CyanVitriol = new Material.Builder(id++, gregtechId( "cyan_vitriol"))
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID))
                .color(0x09b6c6)
                .build();
        CyanVitriol.setFormula("H2SO4 + Ni?", true);

        RedVitriol = new Material.Builder(id++, gregtechId( "red_vitriol"))
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID))
                .color(0xd41501)
                .build();
        RedVitriol.setFormula("H2SO4 + Fe?", true);

        //Others
        PotassiumHydroxide = new Material.Builder(id++, gregtechId( "potassium_hydroxide"))
                .dust()
                .components(Potassium, 1, Oxygen, 1, Hydrogen, 1)
                .flags(DISABLE_DECOMPOSITION)
                .colorAverage()
                .build();

        TreatedScheelite = new Material.Builder(id++, gregtechId( "treated.scheelite"))
                .dust()
                .components(TungsticAcid, 7, CalciumChloride, 3)
                .flags(DISABLE_DECOMPOSITION, GENERATE_SPIRAL_SEPARATION)
                .color((Scheelite.getMaterialRGB()))
                .build();

        TreatedTungstate = new Material.Builder(id++, gregtechId( "treated.tungstate"))
                .dust()
                .components(TungsticAcid, 7, LithiumChloride, 4)
                .flags(DISABLE_DECOMPOSITION, GENERATE_SPIRAL_SEPARATION)
                .colorAverage()
                .build();

        AcidicScheeliteSolution = new Material.Builder(id++, gregtechId( "acidic.solution.scheelite"))
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID))
                .flags(DISABLE_DECOMPOSITION, GENERATE_FILTRATION)
                .components(TreatedScheelite, 1, HydrochloricAcid, 1)
                .iconSet(BRIGHT)
                .colorAverage()
                .build();

        AcidicTungstateSolution = new Material.Builder(id++, gregtechId( "acidic.solution.tungstate"))
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID))
                .flags(DISABLE_DECOMPOSITION, GENERATE_FILTRATION)
                .components(TreatedTungstate, 1, HydrochloricAcid, 1)
                .iconSet(BRIGHT)
                .colorAverage()
                .build();


        return id;
    }

}
