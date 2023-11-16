package tekcays_addon.gtapi.unification.material.materials;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialFlags;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialIconSet.DULL;
import static gregtech.api.unification.material.info.MaterialIconSet.METALLIC;
import static gregtech.api.util.GTUtility.gregtechId;
import static tekcays_addon.gtapi.unification.TKCYAMaterials.*;

public class TKCYAAlloys {

    public static int init(int id) {

        GalvanizedSteel = new Material.Builder(id++, gregtechId("galvanized_steel"))
                .ingot(3)
                .fluidPipeProperties(2000, 100, true, true, true, false)
                .flags(EXT2_METAL, GENERATE_ROTOR, GENERATE_SMALL_GEAR, GENERATE_SPRING,
                        GENERATE_SPRING_SMALL, GENERATE_FRAME, GENERATE_GEAR, GENERATE_DOUBLE_PLATE, GENERATE_DENSE,
                        NO_UNIFICATION, NO_SMELTING, DISABLE_DECOMPOSITION)
                .components(Steel, 9, Zinc, 1)
                .color(0xf5f8fa).iconSet(METALLIC)
                .build();

        PigIron = new Material.Builder(id++, gregtechId("pig_iron"))
                .ingot().fluid()
                .fluidTemp(1473)
                .flags(NO_UNIFICATION, NO_SMELTING, DISABLE_DECOMPOSITION)
                .components(Iron, 25, Carbon, 1)
                .color(0xe7ada0).iconSet(DULL)
                .build();

        Monel = new Material.Builder(id++, gregtechId("monel"))
                .ingot(1).fluid()
                .fluidTemp(1573)
                .flags(EXT2_METAL, GENERATE_ROTOR, GENERATE_SMALL_GEAR, GENERATE_SPRING,
                        GENERATE_SPRING_SMALL, GENERATE_FRAME, GENERATE_GEAR, DISABLE_DECOMPOSITION, NO_SMELTING)
                .components(Nickel, 7, Copper, 3)
                .color(0xc1b8a8).iconSet(METALLIC)
                .build();

        Constantan = new Material.Builder(id++, gregtechId("constantan"))
                .ingot(1).fluid()
                .fluidTemp(1542)
                .flags(EXT2_METAL, DISABLE_DECOMPOSITION, NO_SMELTING)
                .cableProperties(128, 4, 4)
                .components(Copper, 1, Nickel, 1)
                .color(0xdfa478).iconSet(METALLIC)
                .build();

        SiliconCarbide = new Material.Builder(id++, gregtechId("silicon_carbide"))
                .ingot(2)
                .fluid()
                .fluidTemp(3003)
                .flags(EXT2_METAL, DISABLE_DECOMPOSITION, NO_SMELTING)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .color((Silicon.getMaterialRGB() + Carbon.getMaterialRGB()) / 2)
                .iconSet(METALLIC)
                .components(Silicon, 1, Carbon, 1)
                .build();

        BT6 = new Material.Builder(id++, gregtechId("bt_6"))
                .ingot(2)
                .fluid()
                .fluidTemp(3003)
                .flags(EXT2_METAL, DISABLE_DECOMPOSITION, NO_SMELTING)
                .components(Cobalt, 8, Chrome, 3, Steel, 3, Vanadium, 1, Tungsten, 4)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .colorAverage()
                .iconSet(METALLIC)
                .build();

        HastelloyC276 = new Material.Builder(id++, gregtechId("hastelloy_c_276"))
                .ingot(2)
                .fluid()
                .fluidTemp(3003)
                .flags(EXT2_METAL, DISABLE_DECOMPOSITION, NO_SMELTING)
                .components(Cobalt, 1, Chrome, 7, Copper,  1, Molybdenum, 8, Nickel, 32, Tungsten, 1)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .colorAverage()
                .iconSet(METALLIC)
                .build();

        HastelloyN = new Material.Builder(id++, gregtechId("hastelloy_n"))
                .ingot(2)
                .fluid()
                .fluidTemp(3003)
                .flags(EXT2_METAL, DISABLE_DECOMPOSITION, NO_SMELTING)
                .components(Chrome, 8, Molybdenum, 16, Nickel, 60, Titanium, 8, Yttrium, 8)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .colorAverage()
                .iconSet(METALLIC)
                .build();

        Inconel600 = new Material.Builder(id++, gregtechId("inconel_600"))
                .ingot(2)
                .fluid()
                .fluidTemp(3003)
                .flags(EXT2_METAL, DISABLE_DECOMPOSITION, NO_SMELTING)
                .components(Chrome, 1, Steel, 4, Nickel, 5)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .colorAverage()
                .iconSet(METALLIC)
                .build();

        Inconel690 = new Material.Builder(id++, gregtechId("inconel_690"))
                .ingot(2)
                .fluid()
                .fluidTemp(3003)
                .flags(EXT2_METAL, DISABLE_DECOMPOSITION, NO_SMELTING)
                .components(Chrome, 4, Steel, 1, Nickel, 8)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .colorAverage()
                .iconSet(METALLIC)
                .build();

        EglinSteel = new Material.Builder(id++, gregtechId("eglin_steel"))
                .ingot(2)
                .fluid()
                .fluidTemp(3003)
                .flags(EXT2_METAL, DISABLE_DECOMPOSITION, NO_SMELTING)
                .components(Carbon, 13, Chrome, 30, Copper, 8, Iron, 1530, Manganese, 15, Molybdenum, 6, Nickel, 87, Vanadium, 1, Tungsten, 4)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .colorAverage()
                .iconSet(METALLIC)
                .build();

        AF1410 = new Material.Builder(id++, gregtechId("af_1410"))
                .ingot(2)
                .fluid()
                .fluidTemp(3003)
                .flags(EXT2_METAL, DISABLE_DECOMPOSITION, NO_SMELTING)
                .components(Carbon, 1, Cobalt, 24, Chrome, 4, Iron, 136, Molybdenum, 1, Nickel, 17)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .colorAverage()
                .iconSet(METALLIC)
                .build();

        Aermet100 = new Material.Builder(id++, gregtechId("aermet_100"))
                .ingot(2)
                .fluid()
                .fluidTemp(3003)
                .flags(EXT2_METAL, DISABLE_DECOMPOSITION, NO_SMELTING)
                .components(Carbon, 2, Cobalt, 18, Chrome, 5, Iron, 107, Molybdenum, 1, Nickel, 16)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .colorAverage()
                .iconSet(METALLIC)
                .build();

        HY180 = new Material.Builder(id++, gregtechId("hy_180"))
                .ingot(2)
                .fluid()
                .fluidTemp(3003)
                .flags(EXT2_METAL, DISABLE_DECOMPOSITION, NO_SMELTING)
                .components(Carbon, 6, Cobalt, 78, Chrome, 21, Iron, 783, Manganese, 1, Molybdenum, 6, Nickel, 16, Silicon)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .colorAverage()
                .iconSet(METALLIC)
                .build();

        HP9420 = new Material.Builder(id++, gregtechId("hp_4920"))
                .ingot(2)
                .fluid()
                .fluidTemp(3003)
                .flags(EXT2_METAL, DISABLE_DECOMPOSITION, NO_SMELTING)
                .components(Carbon, 12, Cobalt, 60, Chrome, 10, Iron, 79, Manganese, 3, Molybdenum, 8, Nickel, 121, Vanadium, 1)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .colorAverage()
                .iconSet(METALLIC)
                .build();

        StelliteStarJ = new Material.Builder(id++, gregtechId("stellite_star_j"))
                .ingot(2)
                .fluid()
                .fluidTemp(3003)
                .flags(EXT2_METAL, DISABLE_DECOMPOSITION, NO_SMELTING)
                .components(Carbon, 6, Cobalt, 22, Chrome, 18, Iron, 1, Nickel, 1, Tungsten, 3)
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .colorAverage()
                .iconSet(METALLIC)
                .build();

        return id;
    }

}
