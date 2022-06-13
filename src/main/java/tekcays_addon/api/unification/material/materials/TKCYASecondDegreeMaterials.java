package tekcays_addon.api.unification.material.materials;

import gregtech.api.GTValues;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconSet;
import gregtech.api.unification.material.properties.BlastProperty;

import static gregtech.api.unification.material.info.MaterialIconSet.DULL;
import static gregtech.api.unification.material.info.MaterialIconSet.FINE;
import static tekcays_addon.api.unification.TKCYAMaterials.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;

public class TKCYASecondDegreeMaterials {

    public static void init() {

        Ceramic = new Material.Builder(24000, "ceramic")
                .dust(1).ingot()
                .color(0x782828).iconSet(DULL)
                .build();

    }
}
