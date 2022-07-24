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
                .ingot(3)
                .toolStats(8.0f, 4.0f, 768, 25)
                .fluidPipeProperties(2000, 100, true, true, true, false)
                .flags(EXT2_METAL, GENERATE_ROTOR, GENERATE_SMALL_GEAR, GENERATE_SPRING,
                        GENERATE_SPRING_SMALL, GENERATE_FRAME, GENERATE_GEAR,
                        NO_UNIFICATION, NO_SMELTING, DISABLE_DECOMPOSITION)
                .components(Steel, 9, Zinc, 1)
                .color(0xf5f8fa).iconSet(METALLIC)
                .build();

        PigIron = new Material.Builder(24202, "pig_iron")
                .ingot().fluid()
                .fluidTemp(1473)
                .flags(NO_UNIFICATION, NO_SMELTING, DISABLE_DECOMPOSITION)
                .components(Iron, 50, Carbon, 1)
                .color(0xe7ada0).iconSet(DULL)
                .build();

        Monel = new Material.Builder(24203, "monel")
                .ingot(1).fluid()
                .fluidTemp(1573)
                .flags(EXT2_METAL, GENERATE_ROTOR, GENERATE_SMALL_GEAR, GENERATE_SPRING,
                        GENERATE_SPRING_SMALL, GENERATE_FRAME, GENERATE_GEAR, DISABLE_DECOMPOSITION)
                .components(Nickel, 7, Copper, 3)
                .color(0xc1b8a8).iconSet(METALLIC)
                .build();

    }

}
