package tekcays_addon.gtapi.unification.material.materials;

import gregtech.api.fluids.fluidType.FluidTypes;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialIconSet;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.DISABLE_DECOMPOSITION;
import static gregtech.api.unification.material.info.MaterialIconSet.*;
import static gregtech.api.util.GTUtility.gregtechId;
import static tekcays_addon.gtapi.unification.TKCYAMaterials.*;

public class TKCYAMiscMaterials {

    public static int init(int id){

        HotAir = new Material.Builder(id++, gregtechId( "hot_air"))
                .fluid(FluidTypes.GAS, true)
                .flags(DISABLE_DECOMPOSITION)
                .components(Materials.Air, 1)
                .color(0xe08b41)
                .build();

        VeryHotAir = new Material.Builder(id++, gregtechId( "very_hot_air"))
                .fluid(FluidTypes.GAS, true)
                .fluidTemp(1800)
                .components(Materials.Air, 1)
                .color(0xf56342)
                .build();

        //GoldChain
        PreciousMetal = new Material.Builder(id++, gregtechId( "precious_metal"))
                .dust().ore().fluid()
                .fluidTemp(1337)
                .addOreByproducts(Materials.Cobalt, Materials.Copper, Materials.Iron)
                .flags(DISABLE_DECOMPOSITION)
                .components(Materials.Gold, 1)
                .iconSet(SHINY)
                .color(0xB99023)
                .build();
        PreciousMetal.setFormula("Au?");

        GoldAlloy = new Material.Builder(id++, gregtechId( "gold_alloy"))
                .ingot().fluid()
                .fluidTemp(1000) //TODO
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
                .color(0xB99023)
                .build();
        CopperLeach.setFormula("Cu?", true);

        PotassiumBisulfate = new Material.Builder(id++, gregtechId( "potassium_bisulfate"))
                .dust()
                .iconSet(DULL)
                .color(0xB99022)
                .build();
        PotassiumBisulfate.setFormula("KHSO4", true);

        ChloroauricAcid = new Material.Builder(id++, gregtechId( "chloroauric_acid"))
                .fluid(FluidTypes.ACID)
                .color(0xfcb13b)
                .build();
        ChloroauricAcid.setFormula("HAuCl4", true);



        //FluorineChain
        PotassiumBifluoride = new Material.Builder(id++, gregtechId( "potassium_bifluoride"))
                .ingot().fluid(FluidTypes.ACID)
                .fluidTemp(512)
                .flags(DISABLE_DECOMPOSITION)
                .color(0xFF6400).iconSet(MaterialIconSet.DULL)
                .components(Potassium, 1, Hydrogen, 1, Fluorine, 2)
                .build();

        Fluorite = new Material.Builder(id++, gregtechId( "fluorite"))
                .dust().ore()
                .flags(DISABLE_DECOMPOSITION)
                .color(0x009933).iconSet(MaterialIconSet.BRIGHT)
                .components(Calcium, 1, Fluorine, 2)
                .build();

        CalciumSulfonate = new Material.Builder(id++, gregtechId( "calcium_sulfonate"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .color(0xFF6400).iconSet(MaterialIconSet.BRIGHT)
                .components(Calcium, 1, Sulfur, 1, Oxygen, 4)
                .build();

        LithiumFluoride = new Material.Builder(id++, gregtechId( "lithium_fluoride"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .color(0x757575).iconSet(MaterialIconSet.DULL)
                .components(Lithium, 1, Fluorine, 1)
                .build();

        SodiumFluoride = new Material.Builder(id++, gregtechId( "sodium_fluoride"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .color((Sodium.getMaterialRGB() + Fluorine.getMaterialRGB()) / 2).iconSet(MaterialIconSet.DULL)
                .components(Sodium, 1, Fluorine, 1)
                .build();

        PotassiumFluoride = new Material.Builder(id++, gregtechId( "potassium_fluoride"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .color(0xFDFDFD).iconSet(MaterialIconSet.DULL)
                .components(Potassium, 1, Fluorine, 1)
                .build();


        LithiumHydroxide = new Material.Builder(id++, gregtechId( "lithium_hydroxide"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .color((Lithium.getMaterialRGB() + Oxygen.getMaterialRGB() + Hydrogen.getMaterialRGB()) / 3).iconSet(MaterialIconSet.DULL)
                .components(Lithium, 1, Oxygen, 1, Hydrogen, 1)
                .build();

        HydrogenFluoride = new Material.Builder(id++, gregtechId( "hydrogen_fluoride"))
                .fluid(FluidTypes.GAS)
                .flags(DISABLE_DECOMPOSITION)
                .color((Hydrogen.getMaterialRGB() + Fluorine.getMaterialRGB()) / 2)
                .components(Hydrogen, 1, Fluorine, 1)
                .build();

        HydrogenChloride = new Material.Builder(id++, gregtechId( "hydrogen_chloride"))
                .fluid(FluidTypes.GAS)
                .flags(DISABLE_DECOMPOSITION)
                .color((Hydrogen.getMaterialRGB() + Chlorine.getMaterialRGB()) / 2)
                .components(Hydrogen, 1, Chlorine, 1)
                .build();

        HydrogenBromide = new Material.Builder(id++, gregtechId( "hydrogen_bromide"))
                .fluid(FluidTypes.GAS)
                .flags(DISABLE_DECOMPOSITION)
                .color((Hydrogen.getMaterialRGB() + Bromine.getMaterialRGB()) / 2)
                .components(Hydrogen, 1, Bromine, 1)
                .build();

        HydrogenIodide = new Material.Builder(id++, gregtechId( "hydrogen_iodide"))
                .fluid(FluidTypes.GAS)
                .flags(DISABLE_DECOMPOSITION)
                .color((Hydrogen.getMaterialRGB() + Iodine.getMaterialRGB()) / 2)
                .components(Hydrogen, 1, Iodine, 1)
                .build();




        //FREE IDs

        //Zinc chain

        IronSulfate = new Material.Builder(id++, gregtechId( "iron_sulfate"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .color(Iron.getMaterialRGB() + Sulfur.getMaterialRGB() + Oxygen.getMaterialRGB() / 3)
                .components(Iron, 1, Sulfur, 1, Oxygen, 4)
                .build();

        ZincSulfate = new Material.Builder(id++, gregtechId( "zinc_sulfate"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .color(Zinc.getMaterialRGB() + Sulfur.getMaterialRGB() + Oxygen.getMaterialRGB() / 3)
                .components(Zinc, 1, Sulfur, 1, Oxygen, 4)
                .build();

        ZincLeachingSolution = new Material.Builder(id++, gregtechId( "zinc_leaching_solution"))
                .fluid(FluidTypes.ACID)
                .color(Germanium.getMaterialRGB() + Oxygen.getMaterialRGB() + Zinc.getMaterialRGB() / 3)
                .build();
        ZincLeachingSolution.setFormula("Zn?");

        ZincLeachingResidue = new Material.Builder(id++, gregtechId( "zinc_leaching_residue"))
                .fluid(FluidTypes.ACID)
                .color(Germanium.getMaterialRGB() + Oxygen.getMaterialRGB() / 2)
                .build();
        ZincLeachingResidue.setFormula("Ge?");

        GermanicAcidSolution = new Material.Builder(id++, gregtechId( "germanic_acid_solution"))
                .fluid(FluidTypes.ACID)
                .color(Germanium.getMaterialRGB() + SulfuricAcid.getMaterialRGB() / 2)
                .build();
        GermanicAcidSolution.setFormula("H4GeO4", true);

        TannicAcid = new Material.Builder(id++, gregtechId( "tannic_acid"))
                .fluid(FluidTypes.ACID)
                .color(554242)
                .build();
        TannicAcid.setFormula("C76H52O46", true);

        GermaniumChloride = new Material.Builder(id++, gregtechId( "germanium_chloride"))
                .dust()
                .color(Germanium.getMaterialRGB() + Chlorine.getMaterialRGB() * 4 / 5)
                .build();
        GermaniumChloride.setFormula("GeCl4", true);

        GermaniumOxide = new Material.Builder(id++, gregtechId( "germanium_oxide"))
                .dust()
                .color(Germanium.getMaterialRGB() + Oxygen.getMaterialRGB() * 2 / 3)
                .build();
        GermaniumOxide.setFormula("GeO2", true);

        PotassiumMetaBisulfite = new Material.Builder(id++, gregtechId( "potassium_metabisulfite"))
                .dust()
                .color(Potassium.getMaterialRGB() * 2 + Sulfur.getMaterialRGB() * 2 + Obsidian.getMaterialRGB() * 5 / 9)
                .build();
        PotassiumMetaBisulfite.setFormula("K2S2O5", true);

        //More roastable ores
        Kesterite = new Material.Builder(id++, gregtechId( "kesterite"))
                .dust().ore()
                .addOreByproducts(Materials.Cobalt, Materials.Copper, Materials.Iron)
                .flags(DISABLE_DECOMPOSITION)
                .components(Copper, 2, Zinc, 1, Tin, 1, Sulfur, 4)
                .iconSet(DULL)
                .color(0x577b5b)
                .build();

        Stannite = new Material.Builder(id++, gregtechId( "stannite"))
                .dust().ore()
                .addOreByproducts(Materials.Cobalt, Materials.Copper, Materials.Iron)
                .flags(DISABLE_DECOMPOSITION)
                .components(Copper, 2, Iron, 1, Tin, 1, Sulfur, 4)
                .iconSet(DULL)
                .color(0x91a95e)
                .build();

        Arsenopyrite = new Material.Builder(id++, gregtechId( "arsenopyrite"))
                .dust().ore()
                .addOreByproducts(Materials.Cobalt, Materials.Copper, Materials.Iron)
                .flags(DISABLE_DECOMPOSITION)
                .components(Iron, 1, Arsenic, 1, Sulfur, 1)
                .iconSet(DULL)
                .color(0x91a95e)
                .build();

        //Roasting outputs
        Cuprite = new Material.Builder(id++, gregtechId( "cuprite"))
                .dust().ore()
                .addOreByproducts(Materials.Cobalt, Materials.Copper, Materials.Iron)
                .flags(DISABLE_DECOMPOSITION)
                .components(Copper, 2, Oxygen, 1)
                .iconSet(DULL)
                .color(Copper.getMaterialRGB() * 2 + Oxygen.getMaterialRGB() / 3)
                .build();

        //BauxiteChain
        PotassiumAluminate = new Material.Builder(id++, gregtechId( "potassium_aluminate"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(Potassium, 1, Aluminium, 1, Oxygen, 3)
                .iconSet(DULL)
                .color(Copper.getMaterialRGB() * 2 + Oxygen.getMaterialRGB() / 3)
                .build();

        SodiumAluminate = new Material.Builder(id++, gregtechId( "sodium_aluminate"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(Sodium, 1, Aluminium, 1, Oxygen, 3)
                .iconSet(DULL)
                .color(Copper.getMaterialRGB() * 2 + Oxygen.getMaterialRGB() / 3)
                .build();

        AluminiumFluoride = new Material.Builder(id++, gregtechId( "aluminium_fluoride"))
                .dust()
                .fluidTemp(1560)
                .color((Aluminium.getMaterialRGB() + Fluorine.getMaterialRGB() * 3)/ 4)
                .build();
        AluminiumFluoride.setFormula("AlF3", true);

        Cryolite = new Material.Builder(id++, gregtechId( "cryolite"))
                .fluidTemp(1285)
                .color((Aluminium.getMaterialRGB() + Fluorine.getMaterialRGB() * 3)/ 4)
                .build();
        Cryolite.setFormula("Na3AlF6", true);

        HexafluorosilicAcid = new Material.Builder(id++, gregtechId( "hexafluorosilic_acid"))
                .fluidTemp(298)
                .color((Aluminium.getMaterialRGB() + Fluorine.getMaterialRGB() * 3)/ 4)
                .build();
        HexafluorosilicAcid.setFormula("H2SiF6", true);

        AluminiumHydroxide = new Material.Builder(id++, gregtechId( "aluminium_hydroxide"))
                .dust()
                .fluidTemp(573)
                .components(Aluminium, 1, Oxygen, 3, Hydrogen, 3)
                .flags(DISABLE_DECOMPOSITION)
                .color(Aluminium.getMaterialRGB() + (Oxygen.getMaterialRGB() * 3 + Hydrogen.getMaterialRGB() * 3)/ 7)
                .build();
        AluminiumHydroxide.setFormula("Al(OH)3", true);

        Alumina = new Material.Builder(id++, gregtechId( "alumina"))
                .dust()
                .fluidTemp(2345)
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
                .fluidTemp(795 + 273)
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
                .fluid(FluidTypes.ACID)
                .color(0x350092)
                .build();
        BlueVitriol.setFormula("H2SO4 + Cu?", true);

        GrayVitriol = new Material.Builder(id++, gregtechId( "gray_vitriol"))
                .fluid(FluidTypes.ACID)
                .color(0xbab6b6)
                .build();
        GrayVitriol.setFormula("H2SO4 + Mn?", true);

        PinkVitriol = new Material.Builder(id++, gregtechId( "pink_vitriol"))
                .fluid(FluidTypes.ACID)
                .color(0xf6b7c9)
                .build();
        PinkVitriol.setFormula("H2SO4 + Mg?", true);

        GreenVitriol = new Material.Builder(id++, gregtechId( "green_vitriol"))
                .fluid(FluidTypes.ACID)
                .color(0x5ba304)
                .build();
        GreenVitriol.setFormula("H2SO4 + Fe?", true);

        WhiteVitriol = new Material.Builder(id++, gregtechId( "white_vitriol"))
                .fluid(FluidTypes.ACID)
                .color(0xf7f7f7)
                .build();
        WhiteVitriol.setFormula("H2SO4 + Zn?", true);

        ClayVitriol = new Material.Builder(id++, gregtechId( "clay_vitriol"))
                .fluid(FluidTypes.ACID)
                .color(0xc49963)
                .build();
        ClayVitriol.setFormula("H2SO4 + Al2O3?", true);

        CyanVitriol = new Material.Builder(id++, gregtechId( "cyan_vitriol"))
                .fluid(FluidTypes.ACID)
                .color(0x09b6c6)
                .build();
        CyanVitriol.setFormula("H2SO4 + Ni?", true);

        RedVitriol = new Material.Builder(id++, gregtechId( "red_vitriol"))
                .fluid(FluidTypes.ACID)
                .color(0xd41501)
                .build();
        RedVitriol.setFormula("H2SO4 + Fe?", true);

        //Others
        PotassiumHydroxide = new Material.Builder(id++, gregtechId( "potassium_hydroxide"))
                .dust()
                .components(Potassium, 1, Oxygen, 1, Hydrogen, 1)
                .flags(DISABLE_DECOMPOSITION)
                .color((Potassium.getMaterialRGB() + Hydrogen.getMaterialRGB() + Oxygen.getMaterialRGB()) / 3)
                .build();
        PotassiumHydroxide.setFormula("KOH", true);


        return id;
    }

}
