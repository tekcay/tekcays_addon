package tekcays_addon.gtapi.unification.material.materials;

import static gregtech.api.unification.material.Materials.Methane;
import static gregtech.api.unification.material.Materials.Steam;
import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_PLATE;
import static gregtech.api.unification.material.info.MaterialIconSet.*;
import static gregtech.api.util.GTUtility.gregtechId;
import static tekcays_addon.gtapi.unification.TKCYAMaterials.*;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.material.Material;

public class TKCYANoFormulaMaterials {

    public static int init(int id) {
        Ceramic = new Material.Builder(id++, gregtechId("ceramic"))
                .dust(1).ingot()
                .ingot()
                .flags(GENERATE_PLATE)
                .liquid(new FluidBuilder().temperature(2500))
                .color(0xf6ad30).iconSet(DULL)
                .build();

        MicaPulp = new Material.Builder(id++, gregtechId("mica_pulp"))
                .dust(1)
                .color(0xf1cd91).iconSet(SAND)
                .build();

        Fuel = new Material.Builder(id++, gregtechId("fuel"))
                .fluid()
                .color(0xf6ad30)
                .build();

        MelonOil = new Material.Builder(id++, gregtechId("melon_oil"))
                .fluid()
                .color(0xc68479)
                .build();

        PumpkinOil = new Material.Builder(id++, gregtechId("pumpkin_oil"))
                .fluid()
                .color(0xc6c079)
                .build();

        MixtureToFilter = new Material.Builder(id++, gregtechId("mixture_to_filter"))
                .fluid()
                .color(0xc6c079)
                .build();

        LightlySteamCrackedMethane = new Material.Builder(id++, gregtechId("lightly_steam_cracked_methane"))
                .liquid(new FluidBuilder().temperature(500))
                .color((2 * Methane.getMaterialRGB() + Steam.getMaterialRGB()) / 3)
                .build();

        ModeratelySteamCrackedMethane = new Material.Builder(id++, gregtechId("moderately_steam_cracked_methane"))
                .liquid(new FluidBuilder().temperature(600))
                .color((Methane.getMaterialRGB() + Steam.getMaterialRGB()) / 2)
                .build();

        SeverelySteamCrackedMethane = new Material.Builder(id++, gregtechId("severely_steam_cracked_methane"))
                .liquid(new FluidBuilder().temperature(700))
                .color((Methane.getMaterialRGB() + Steam.getMaterialRGB() * 2) / 3)
                .build();

        ChalcogenAnodeMud = new Material.Builder(id++, gregtechId("chalcogen_anode_mud"))
                .dust()
                .color(0x8A3324)
                .iconSet(FINE)
                .build();

        return id;
    }
}
