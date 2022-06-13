package tekcays_addon.api.unification.material.materials;

import gregtech.api.unification.material.Material;

import static gregtech.api.unification.material.info.MaterialIconSet.DULL;
import static tekcays_addon.api.unification.TKCYAMaterials.*;

public class TKCYAFirstDegreeMaterials {

    public static void init() {

        Ceramic = new Material.Builder(24000, "ceramic")
                .dust(1).ingot()
                .color(0xf6ad30).iconSet(DULL)
                .build();

    }
}
