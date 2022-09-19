package tekcays_addon.api.unification.material.materials;

import gregtech.api.fluids.fluidType.FluidTypes;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.material.info.MaterialIconSet;
import tekcays_addon.api.unification.TKCYAMaterials;
import tekcays_addon.api.unification.material.info.TKCYAMaterialFlags;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialIconSet.*;
import static tekcays_addon.api.unification.TKCYAMaterials.*;

public class TKCYAMiscMaterials {

    public static void init(){

        HotAir = new Material.Builder(24101, "hot_air")
                .fluid(FluidTypes.GAS, true)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .components(Materials.Air, 1)
                .color(0xe08b41)
                .build();

        VeryHotAir = new Material.Builder(24102, "very_hot_air")
                .fluid(FluidTypes.GAS, true)
                .fluidTemp(1800)
                .components(Materials.Air, 1)
                .color(0xf56342)
                .build();

        //GoldChain
        PreciousMetal = new Material.Builder(24104, "precious_metal")
                .dust().ore().fluid()
                .fluidTemp(1337)
                .addOreByproducts(Materials.Cobalt, Materials.Copper, Materials.Iron)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .components(Materials.Gold, 1)
                .iconSet(SHINY)
                .color(0xB99023)
                .build();
        PreciousMetal.setFormula("Au?");

        GoldAlloy = new Material.Builder(24105, "gold_alloy")
                .ingot().fluid()
                .fluidTemp(1000) //TODO
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .components(Materials.Copper, 3, PreciousMetal, 1)
                .iconSet(SHINY)
                .color(0xB99023)
                .build();
        GoldAlloy.setFormula("Cu3Au?", true);

        GoldLeach = new Material.Builder(24106, "gold_leach")
                .dust()
                .iconSet(SHINY)
                .color(0xB99023)
                .build();
        GoldLeach.setFormula("CuAu?", true);

        CopperLeach = new Material.Builder(24107, "copper_leach")
                .dust()
                .iconSet(SHINY)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .components(Copper, 27, Lead, 1, Iron, 1, Nickel, 1, Silver, 1)
                .color(0xB99023)
                .build();
        CopperLeach.setFormula("Cu?", true);



        //FluorineChain
        PotassiumBifluoride = new Material.Builder(24114, "potassium_bifluoride")
                .ingot().fluid(FluidTypes.ACID)
                .fluidTemp(512)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .color(0xFF6400).iconSet(MaterialIconSet.DULL)
                .components(Potassium, 1, Hydrogen, 1, Fluorine, 2)
                .build();

        Fluorite = new Material.Builder(24115, "fluorite")
                .dust().ore()
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .color(0x009933).iconSet(MaterialIconSet.BRIGHT)
                .components(Calcium, 1, Fluorine, 2)
                .build();

        CalciumSulfonate = new Material.Builder(24116, "calcium_sulfonate")
                .dust()
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .color(0xFF6400).iconSet(MaterialIconSet.BRIGHT)
                .components(Calcium, 1, Sulfur, 1, Oxygen, 4)
                .build();

        LithiumFluoride = new Material.Builder(24117, "lithium_fluoride")
                .dust()
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .color(0x757575).iconSet(MaterialIconSet.DULL)
                .components(Lithium, 1, Fluorine, 1)
                .build();

        SodiumFluoride = new Material.Builder(24118, "sodium_fluoride")
                .dust()
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .color(Sodium.getMaterialRGB() + Fluorine.getMaterialRGB() / 2).iconSet(MaterialIconSet.DULL)
                .components(Sodium, 1, Fluorine, 1)
                .build();

        PotassiumFluoride = new Material.Builder(24119, "potassium_fluoride")
                .dust()
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .color(0xFDFDFD).iconSet(MaterialIconSet.DULL)
                .components(Potassium, 1, Fluorine, 1)
                .build();


        LithiumHydroxide = new Material.Builder(24120, "lithium_hydroxide")
                .dust()
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .color(Lithium.getMaterialRGB() + Oxygen.getMaterialRGB() + Hydrogen.getMaterialRGB() / 3).iconSet(MaterialIconSet.DULL)
                .components(Lithium, 1, Oxygen, 1, Hydrogen, 1)
                .build();

        HydrogenFluoride = new Material.Builder(24121, "hydrogen_fluoride")
                .fluid(FluidTypes.GAS)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .color(Hydrogen.getMaterialRGB() + Fluorine.getMaterialRGB() / 2)
                .components(Hydrogen, 1, Fluorine, 1)
                .build();

        HydrogenChloride = new Material.Builder(24122, "hydrogen_chloride")
                .fluid(FluidTypes.GAS)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .color(Hydrogen.getMaterialRGB() + Chlorine.getMaterialRGB() / 2)
                .components(Hydrogen, 1, Chlorine, 1)
                .build();

        HydrogenBromide = new Material.Builder(24123, "hydrogen_bromide")
                .fluid(FluidTypes.GAS)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .color(Hydrogen.getMaterialRGB() + Bromine.getMaterialRGB() / 2)
                .components(Hydrogen, 1, Bromine, 1)
                .build();

        HydrogenIodide = new Material.Builder(24124, "hydrogen_iodide")
                .fluid(FluidTypes.GAS)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .color(Hydrogen.getMaterialRGB() + Iodine.getMaterialRGB() / 2)
                .components(Hydrogen, 1, Iodine, 1)
                .build();


        //FREE IDs

        //Zinc chain

        IronSulfate = new Material.Builder(24130, "iron_sulfate")
                .dust()
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .color(Iron.getMaterialRGB() + Sulfur.getMaterialRGB() + Oxygen.getMaterialRGB() / 3)
                .components(Iron, 1, Sulfur, 1, Oxygen, 4)
                .build();

        ZincSulfate = new Material.Builder(24131, "zinc_sulfate")
                .dust()
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .color(Zinc.getMaterialRGB() + Sulfur.getMaterialRGB() + Oxygen.getMaterialRGB() / 3)
                .components(Zinc, 1, Sulfur, 1, Oxygen, 4)
                .build();

        ZincLeachingSolution = new Material.Builder(24132, "zinc_leaching_solution")
                .fluid(FluidTypes.ACID)
                .color(Germanium.getMaterialRGB() + Oxygen.getMaterialRGB() + Zinc.getMaterialRGB() / 3)
                .build();
        ZincLeachingSolution.setFormula("Zn?");

        ZincLeachingResidue = new Material.Builder(24133, "zinc_leaching_residue")
                .fluid(FluidTypes.ACID)
                .color(Germanium.getMaterialRGB() + Oxygen.getMaterialRGB() / 2)
                .build();
        ZincLeachingResidue.setFormula("Ge?");

        GermanicAcidSolution = new Material.Builder(24134, "germanic_acid_solution")
                .fluid(FluidTypes.ACID)
                .color(Germanium.getMaterialRGB() + SulfuricAcid.getMaterialRGB() / 2)
                .build();
        GermanicAcidSolution.setFormula("H4GeO4", true);

        TannicAcid = new Material.Builder(24135, "tannic_acid")
                .fluid(FluidTypes.ACID)
                .color(554242)
                .build();
        TannicAcid.setFormula("C76H52O46", true);

        GermaniumChloride = new Material.Builder(24136, "germanium_chloride")
                .dust()
                .color(Germanium.getMaterialRGB() + Chlorine.getMaterialRGB() * 4 / 5)
                .build();
        GermaniumChloride.setFormula("GeCl4", true);

        GermaniumOxide = new Material.Builder(24137, "germanium_oxide")
                .dust()
                .color(Germanium.getMaterialRGB() + Oxygen.getMaterialRGB() * 2 / 3)
                .build();
        GermaniumOxide.setFormula("GeO2", true);

        PotassiumMetaBisulfite = new Material.Builder(24138, "potassium_metabisulfite")
                .dust()
                .color(Potassium.getMaterialRGB() * 2 + Sulfur.getMaterialRGB() * 2 + Obsidian.getMaterialRGB() * 5 / 9)
                .build();
        PotassiumMetaBisulfite.setFormula("K2S2O5", true);

        //More roastable ores
        Kesterite = new Material.Builder(24139, "kesterite")
                .dust().ore()
                .addOreByproducts(Materials.Cobalt, Materials.Copper, Materials.Iron)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .components(Copper, 2, Zinc, 1, Tin, 1, Sulfur, 4)
                .iconSet(DULL)
                .color(0x577b5b)
                .build();

        Stannite = new Material.Builder(24140, "stannite")
                .dust().ore()
                .addOreByproducts(Materials.Cobalt, Materials.Copper, Materials.Iron)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .components(Copper, 2, Iron, 1, Tin, 1, Sulfur, 4)
                .iconSet(DULL)
                .color(0x91a95e)
                .build();

        Arsenopyrite = new Material.Builder(24141, "arsenopyrite")
                .dust().ore()
                .addOreByproducts(Materials.Cobalt, Materials.Copper, Materials.Iron)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .components(Iron, 1, Arsenic, 1, Sulfur, 1)
                .iconSet(DULL)
                .color(0x91a95e)
                .build();

        //Roasting outputs
        Cuprite = new Material.Builder(24142, "cuprite")
                .dust().ore()
                .addOreByproducts(Materials.Cobalt, Materials.Copper, Materials.Iron)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .components(Copper, 2, Oxygen, 1)
                .iconSet(DULL)
                .color(Copper.getMaterialRGB() * 2 + Oxygen.getMaterialRGB() / 3)
                .build();




    }

}
