package tekcays_addon.api.unification.material.materials;

import gregtech.api.fluids.fluidType.FluidTypes;
import gregtech.api.unification.material.Material;

import static gregtech.api.unification.material.info.MaterialIconSet.*;
import static tekcays_addon.api.unification.TKCYAMaterials.*;

public class TKCYANoFormulaMaterials {

    public static void init() {

        Ceramic = new Material.Builder(24000, "ceramic")
                .dust(1).ingot()
                .fluid()
                .fluidTemp(2500)
                .color(0xf6ad30).iconSet(DULL)
                .build();

        MicaPulp = new Material.Builder(24002, "mica_pulp")
                .dust(1)
                .color(0xf1cd91).iconSet(SAND)
                .build();

        Fuel = new Material.Builder(24003, "fuel")
                .fluid()
                .fluidTemp(298)
                .color(0xf6ad30)
                .build();

        HotFlueGas = new Material.Builder(24004, "hot_flue_gas")
                .fluid(FluidTypes.GAS)
                .fluidTemp(1800)
                .color(0x69605f)
                .build();

        FlueGas = new Material.Builder(24005, "flue_gas")
                .fluid(FluidTypes.GAS)
                .fluidTemp(298)
                .color(0x7a7372)
                .build();

        MelonOil = new Material.Builder(24006, "melon_oil")
                .fluid()
                .fluidTemp(298)
                .color(0xc68479)
                .build();

        PumpkinOil = new Material.Builder(24007, "pumpkin_oil")
                .fluid()
                .fluidTemp(298)
                .color(0xc6c079)
                .build();

        MixtureToFilter = new Material.Builder(24008, "mixture_to_filter")
                .fluid()
                .color(0xc6c079)
                .build();

    }

}
