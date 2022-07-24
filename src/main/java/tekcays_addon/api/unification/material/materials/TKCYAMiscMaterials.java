package tekcays_addon.api.unification.material.materials;

import gregtech.api.fluids.fluidType.FluidTypes;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.material.info.MaterialIconSet;

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



        //FluorineChain
        PotassiumBifluoride = new Material.Builder(24114, "potassium_bifluoride")
                .ingot().fluid(FluidTypes.ACID)
                .fluidTemp(512)
                .color(0xFF6400).iconSet(MaterialIconSet.DULL)
                .components(Potassium, 1, Hydrogen, 1, Fluorine, 2)
                .build();

        Fluorite = new Material.Builder(24115, "fluorite")
                .dust().ore()
                .color(0x009933).iconSet(MaterialIconSet.BRIGHT)
                .components(Calcium, 1, Fluorine, 2)
                .build();

        CalciumSulfonate = new Material.Builder(24116, "calcium_sulfonate")
                .dust()
                .color(0xFF6400).iconSet(MaterialIconSet.BRIGHT)
                .components(Calcium, 1, Sulfur, 1, Oxygen, 4)
                .build();

        LithiumFluoride = new Material.Builder(24117, "lithium_fluoride")
                .dust()
                .color(0x757575).iconSet(MaterialIconSet.DULL)
                .components(Lithium, 1, Fluorine, 1)
                .build();

        SodiumFluoride = new Material.Builder(24118, "sodium_fluoride")
                .dust()
                .color(Sodium.getMaterialRGB() + Fluorine.getMaterialRGB() / 2).iconSet(MaterialIconSet.DULL)
                .components(Sodium, 1, Fluorine, 1)
                .build();

        PotassiumFluoride = new Material.Builder(24119, "potassium_fluoride")
                .dust()
                .color(0xFDFDFD).iconSet(MaterialIconSet.DULL)
                .components(Potassium, 1, Fluorine, 1)
                .build();


        LithiumHydroxide = new Material.Builder(24120, "lithium_hydroxide")
                .dust()
                .color(Lithium.getMaterialRGB() + Oxygen.getMaterialRGB() + Hydrogen.getMaterialRGB() / 3).iconSet(MaterialIconSet.DULL)
                .components(Lithium, 1, Oxygen, 1, Hydrogen, 1)
                .build();

        HydrogenFluoride = new Material.Builder(24121, "hydrogen_fluoride")
                .fluid(FluidTypes.ACID)
                .color(Hydrogen.getMaterialRGB() + Fluorine.getMaterialRGB() / 2)
                .components(Hydrogen, 1, Fluorine, 1)
                .build();

        HydrogenChloride = new Material.Builder(24122, "hydrogen_chloride")
                .fluid(FluidTypes.ACID)
                .color(Hydrogen.getMaterialRGB() + Chlorine.getMaterialRGB() / 2)
                .components(Hydrogen, 1, Chlorine, 1)
                .build();

        HydrogenBromide = new Material.Builder(24123, "hydrogen_bromide")
                .fluid(FluidTypes.ACID)
                .color(Hydrogen.getMaterialRGB() + Bromine.getMaterialRGB() / 2)
                .components(Hydrogen, 1, Bromine, 1)
                .build();

        HydrogenIodide = new Material.Builder(24124, "hydrogen_iodide")
                .fluid(FluidTypes.ACID)
                .color(Hydrogen.getMaterialRGB() + Iodine.getMaterialRGB() / 2)
                .components(Hydrogen, 1, Iodine, 1)
                .build();

    }

}
