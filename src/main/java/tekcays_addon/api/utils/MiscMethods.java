package tekcays_addon.api.utils;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconSet;
import gregtech.api.unification.ore.OrePrefix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tekcays_addon.api.utils.RegistriesList.MAGNETIC_MATERIALS;

public class MiscMethods {


    public static List<Material> getMagneticMaterialsRegistryList() {

        List<Material> magneticMaterials = new ArrayList<>();

        for (Material m : GregTechAPI.MATERIAL_REGISTRY) {

            if (m.getMaterialIconSet() != MaterialIconSet.MAGNETIC) continue;
            magneticMaterials.add(m);

        }
        return magneticMaterials;
    }


    public static Map<Material, Material> getMagneticMaterialsRegistryMap() {

        Map<Material, Material> magneticMaterialsMap = new HashMap<>();

        for (Material magneticMaterial : MAGNETIC_MATERIALS.list) {
            for (Material m : GregTechAPI.MATERIAL_REGISTRY) {

                if (!magneticMaterial.getUnlocalizedName().startsWith(m.getUnlocalizedName())) continue;
                if (magneticMaterial == m) continue;
                if (magneticMaterial.getChemicalFormula() != m.getChemicalFormula()) continue; // To avoid particular cases like Neodymium oxide
                TKCYALog.logger.info("NonmagneticMaterial = " + m.getUnlocalizedName());
                magneticMaterialsMap.put(m, magneticMaterial);
            }
        }
        return magneticMaterialsMap;
    }


    public static List<Material> getFoilMaterialsList() {

        List<Material> foilMaterials = new ArrayList<>();

        for (Material material : GregTechAPI.MATERIAL_REGISTRY) {
            if (!OrePrefix.plate.doGenerateItem(material)) continue;
            if (!OrePrefix.foil.doGenerateItem(material)) continue;
            foilMaterials.add(material);
        }
    return foilMaterials;
    }



    public static List<OrePrefix> getMaterialOrePrefixesList(Material material) {

        List<OrePrefix> materialOrePrefixesList = new ArrayList<>();

        for (OrePrefix prefix : OrePrefix.values()) {
            if (!prefix.doGenerateItem(material)) continue;
            materialOrePrefixesList.add(prefix);
        }
    return materialOrePrefixesList;
    }

}


