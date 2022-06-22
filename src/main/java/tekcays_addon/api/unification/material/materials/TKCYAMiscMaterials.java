package tekcays_addon.api.unification.material.materials;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;

import static gregtech.api.unification.material.info.MaterialIconSet.DULL;
import static tekcays_addon.api.unification.TKCYAMaterials.*;

public class TKCYAMiscMaterials {

    public static void init({

        HotAir = new Material.Builder(24101, "hot_air")
                .fluid()
                .components(Materials.Air)
                .color(0xf6ad30).iconSet(DULL)
                .build();

    }

}
