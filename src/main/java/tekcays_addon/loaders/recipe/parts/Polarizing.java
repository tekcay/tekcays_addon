package tekcays_addon.loaders.recipe.parts;

import gregtech.api.GTValues;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.IngotProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.item.ItemStack;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;

import static gregtech.api.GTValues.LV;
import static gregtech.api.GTValues.VA;
import static tekcays_addon.gtapi.consts.TKCYAValues.POLARIZING_PREFIXES;

public class Polarizing {

    public static void init() {
            POLARIZING_PREFIXES.forEach(orePrefix -> orePrefix.addProcessingHandler(PropertyKey.INGOT, Polarizing::processPolarizing));
    }

    private static void processPolarizing(OrePrefix polarizingPrefix, Material material, IngotProperty property) {

        Material magneticMaterial = property.getMagneticMaterial();

        if (magneticMaterial != null && polarizingPrefix.doGenerateItem(magneticMaterial)) {
            ItemStack magneticStack = OreDictUnifier.get(polarizingPrefix, magneticMaterial);

            TKCYARecipeMaps.ADVANCED_POLARIZER_RECIPES.recipeBuilder() //polarizing
                    .input(polarizingPrefix, material)
                    .input(OrePrefix.dust, Materials.Magnetite)
                    .outputs(magneticStack)
                    .duration((int) ((int) material.getMass() * polarizingPrefix.getMaterialAmount(material) / GTValues.M))
                    .EUt(8 * getVoltageMultiplier(material))
                    .buildAndRegister();
        }
    }

    private static int getVoltageMultiplier(Material material) {
        return material.getBlastTemperature() >= 1200 ? VA[LV] : 2;
    }
}
