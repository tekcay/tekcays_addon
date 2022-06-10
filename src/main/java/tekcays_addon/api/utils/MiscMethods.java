package tekcays_addon.api.utils;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_FOIL;
import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_PLATE;

public class MiscMethods {

    public static List<Material> getMagneticMaterialsList() {

        List<Material> magneticMaterials = new ArrayList<>();

        for (Material m : GregTechAPI.MATERIAL_REGISTRY) {

            if (m.getMaterialIconSet() != MaterialIconSet.MAGNETIC) continue;
            magneticMaterials.add(m);

        }

        return magneticMaterials;

    }

    public static Map<Material, Material> getMagneticMaterialsMap() {

        Map<Material, Material> magneticMaterialsMap = new HashMap<>();

        for (Material magneticMaterial : getMagneticMaterialsList()) {
            for (Material m : GregTechAPI.MATERIAL_REGISTRY) {

                if (magneticMaterial.getUnlocalizedName().startsWith(m.getUnlocalizedName()) &&
                    magneticMaterial.getMaterialComponents() == m.getMaterialComponents()) continue;

                magneticMaterialsMap.put(m, magneticMaterial);
                }
            }
        return magneticMaterialsMap;
    }

    public static List<Material> getFoilMaterialsList() {

        List<Material> foilMaterials = new ArrayList<>();

        for (Material material : GregTechAPI.MATERIAL_REGISTRY) {
            if (!material.hasFlag(GENERATE_FOIL)) continue;
            if (!material.hasFlag(GENERATE_PLATE)) continue;
            foilMaterials.add(material);
        }
    return foilMaterials;
    }

}


