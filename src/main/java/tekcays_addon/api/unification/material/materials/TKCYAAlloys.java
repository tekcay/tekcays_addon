package tekcays_addon.api.unification.material.materials;


import gregtech.api.fluids.fluidType.FluidTypes;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialFlags;

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
                .components(Iron, 25, Carbon, 1)
                .color(0xe7ada0).iconSet(DULL)
                .build();

        Monel = new Material.Builder(24203, "monel")
                .ingot(1).fluid()
                .fluidTemp(1573)
                .flags(EXT2_METAL, GENERATE_ROTOR, GENERATE_SMALL_GEAR, GENERATE_SPRING,
                        GENERATE_SPRING_SMALL, GENERATE_FRAME, GENERATE_GEAR, DISABLE_DECOMPOSITION, NO_SMELTING)
                .components(Nickel, 7, Copper, 3)
                .color(0xc1b8a8).iconSet(METALLIC)
                .build();

        Constantan = new Material.Builder(24204, "constantan")
                .ingot(1).fluid()
                .fluidTemp(1542)
                .flags(EXT2_METAL, DISABLE_DECOMPOSITION, NO_SMELTING)
                .cableProperties(128, 4, 4)
                .components(Copper, 1, Nickel, 1)
                .color(0xdfa478).iconSet(METALLIC)
                .build();

        SiliconCarbide = new Material.Builder(24205, "silicon_carbide")
                .ingot(2)
                .fluid()
                .fluidTemp(3003)
                .flags(EXT2_METAL, DISABLE_DECOMPOSITION, NO_SMELTING)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .color((Silicon.getMaterialRGB() + Oxygen.getMaterialRGB()) / 2)
                .iconSet(METALLIC)
                .components(Silicon, 1, Oxygen, 1)
                .build();

    }

}
