package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.GTValues;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;

import java.util.List;
import java.util.Map;

import static gregtech.api.GTValues.LV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.ore.OrePrefix.*;
import static tekcays_addon.api.utils.MiscMethods.*;

public class TKCYAPartsRecipeHandler {


    public static void processFoil() {

        List<Material> foilMaterials = getFoilMaterialsList();

        for (Material material : foilMaterials) {

            TKCYARecipeMaps.CLUSTER_MILL_RECIPES.recipeBuilder()
                    .input(plate, material)
                    .output(foil, material, 4)
                    .duration((int) material.getMass())
                    .EUt(24)
                    .buildAndRegister();
        }
    }


    public static void processPolarizing() {

        OrePrefix[] polarizingPrefixes = new OrePrefix[]{
                OrePrefix.stick, OrePrefix.stickLong, OrePrefix.plate, OrePrefix.ingot, OrePrefix.plateDense, OrePrefix.rotor,
                OrePrefix.bolt, OrePrefix.screw, OrePrefix.wireFine, OrePrefix.foil, OrePrefix.dust, OrePrefix.ring};

        Map<Material, Material> magneticMaterialsMap = getMagneticMaterialsMap();

        for (OrePrefix polarizingPrefix : polarizingPrefixes) {
            magneticMaterialsMap.forEach((m, magneticMaterial) -> {

                TKCYARecipeMaps.ADVANCED_POLARIZER_RECIPES.recipeBuilder() //polarizing
                        .input(polarizingPrefix, m)
                        .input(OrePrefix.dust, Materials.Magnetite)
                        .output(polarizingPrefix, magneticMaterial)
                        .duration((int) ((int) m.getMass() * polarizingPrefix.getMaterialAmount(m) / GTValues.M))
                        .EUt(8 * getVoltageMultiplier(m))
                        .buildAndRegister();
            });
        }
    }

    private static int getVoltageMultiplier(Material material) {
        return material.getBlastTemperature() >= 1200 ? VA[LV] : 2;
    }

}





