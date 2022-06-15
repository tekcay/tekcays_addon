package tekcays_addon.api.unification.material.materials;

import gregtech.api.unification.material.Material;

import static gregtech.api.unification.material.info.MaterialIconSet.SAND;
import static tekcays_addon.api.unification.TKCYAMaterials.*;
import static gregtech.api.unification.material.info.MaterialIconSet.DULL;

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

        Fuel = new Material.Builder(24000, "fuel")
                .fluid()
                .color(0xf6ad30)
                .build();
    }

}
