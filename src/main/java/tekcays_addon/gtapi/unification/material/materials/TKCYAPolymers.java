package tekcays_addon.gtapi.unification.material.materials;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.util.GTUtility.gregtechId;
import static tekcays_addon.gtapi.unification.TKCYAMaterials.*;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.stack.MaterialStack;
import tekcays_addon.gtapi.unification.material.info.TKCYAMaterialFlags;

public class TKCYAPolymers {

    public static int init(int id) {
        HighDensityPolyethylene = new Material.Builder(id++, gregtechId("high_density_polyethylene"))
                .ingot().fluid()
                .fluidPipeProperties(410, 50, true)
                .flags(TKCYAMaterialFlags.POLYMER)
                .components(new MaterialStack(Ethylene, 1))
                // .components(Carbon, 2, Hydrogen, 2)
                .color(0xebf0ef)
                .build();

        Polypropylene = new Material.Builder(id++, gregtechId("polypropylene"))
                .ingot().fluid()
                .fluidPipeProperties(420, 50, true, true, false, false)
                .flags(TKCYAMaterialFlags.POLYMER)
                .components(new MaterialStack(Propene, 1))
                .color(0xe1e6e5)
                .build();

        return id;
    }
}
