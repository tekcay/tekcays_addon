package tekcays_addon.api.unification.material.materials;


import gregtech.api.unification.material.Material;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialIconSet.DULL;
import static gregtech.api.unification.material.info.MaterialIconSet.METALLIC;
import static tekcays_addon.api.unification.TKCYAMaterials.*;

public class TKCYAAlloys {

    public static void init() {

        GalvanizedSteel = new Material.Builder(24201, "galvanized_steel")
                .ingot()
                .flags(GENERATE_FOIL, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_PLATE, GENERATE_FINE_WIRE, GENERATE_ROTOR, GENERATE_SMALL_GEAR, NO_UNIFICATION, NO_SMELTING, DISABLE_DECOMPOSITION)
                .components(Iron, 9, Zinc, 1)
                .color(0xf5f8fa).iconSet(METALLIC)
                .build();

        PigIron = new Material.Builder(24202, "pig_iron")
                .ingot().fluid()
                .fluidTemp(1473)
                .flags(NO_UNIFICATION, NO_SMELTING, DISABLE_DECOMPOSITION)
                .components(Iron, 50, Carbon, 1)
                .color(0xe7ada0).iconSet(DULL)
                .build();

    }

}
