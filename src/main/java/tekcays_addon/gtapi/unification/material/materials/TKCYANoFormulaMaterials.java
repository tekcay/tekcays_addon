package tekcays_addon.gtapi.unification.material.materials;

import gregtech.api.fluids.fluidType.FluidTypes;
import gregtech.api.unification.material.Material;

import static gregtech.api.unification.material.Materials.Methane;
import static gregtech.api.unification.material.Materials.Steam;
import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_PLATE;
import static gregtech.api.unification.material.info.MaterialIconSet.*;
import static gregtech.api.util.GTUtility.gregtechId;
import static tekcays_addon.gtapi.unification.TKCYAMaterials.*;

public class TKCYANoFormulaMaterials {

    public static int init(int id) {

        Ceramic = new Material.Builder(id++, gregtechId( "ceramic"))
                .dust(1).ingot()
                .ingot()
                .flags(GENERATE_PLATE)
                .fluid()
                .fluidTemp(2500)
                .color(0xf6ad30).iconSet(DULL)
                .build();

        MicaPulp = new Material.Builder(id++, gregtechId( "mica_pulp"))
                .dust(1)
                .color(0xf1cd91).iconSet(SAND)
                .build();

        Fuel = new Material.Builder(id++, gregtechId( "fuel"))
                .fluid()
                .fluidTemp(298)
                .color(0xf6ad30)
                .build();

        HotFlueGas = new Material.Builder(id++, gregtechId( "hot_flue_gas"))
                .fluid(FluidTypes.GAS)
                .fluidTemp(1800)
                .color(0x69605f)
                .build();

        FlueGas = new Material.Builder(id++, gregtechId( "flue_gas"))
                .fluid(FluidTypes.GAS)
                .fluidTemp(298)
                .color(0x7a7372)
                .build();

        MelonOil = new Material.Builder(id++, gregtechId( "melon_oil"))
                .fluid()
                .fluidTemp(298)
                .color(0xc68479)
                .build();

        PumpkinOil = new Material.Builder(id++, gregtechId( "pumpkin_oil"))
                .fluid()
                .fluidTemp(298)
                .color(0xc6c079)
                .build();

        MixtureToFilter = new Material.Builder(id++, gregtechId( "mixture_to_filter"))
                .fluid()
                .color(0xc6c079)
                .build();

        LightlySteamCrackedMethane = new Material.Builder(id++, gregtechId( "lightly_steam_cracked_methane"))
                .fluid()
                .fluidTemp(500)
                .color((2 * Methane.getMaterialRGB() + Steam.getMaterialRGB()) / 3)
                .build();

        ModeratelySteamCrackedMethane = new Material.Builder(id++, gregtechId( "moderately_steam_cracked_methane"))
                .fluid()
                .fluidTemp(600)
                .color((Methane.getMaterialRGB() + Steam.getMaterialRGB()) / 2)
                .build();

        SeverelySteamCrackedMethane = new Material.Builder(id++, gregtechId( "severely_steam_cracked_methane"))
                .fluid()
                .fluidTemp(700)
                .color((Methane.getMaterialRGB() + Steam.getMaterialRGB() * 2) / 3)
                .build();

        ChalcogenAnodeMud = new Material.Builder(id++, gregtechId( "chalcogen_anode_mud"))
                .dust()
                .color(0x8A3324)
                .iconSet(FINE)
                .build();

        return id;
    }

}
