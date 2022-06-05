package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.GTValues;
import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.IngotProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import net.minecraft.item.ItemStack;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.common.TKCYAConfigHolder;

import static gregtech.api.GTValues.LV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.BENDER_RECIPES;
import static gregtech.api.recipes.RecipeMaps.POLARIZER_RECIPES;
import static gregtech.api.unification.ore.OrePrefix.plate;

public class TKCYAPolarizingRecipeHandler {

        private static final OrePrefix[] POLARIZING_PREFIXES = new OrePrefix[]{
            OrePrefix.stick, OrePrefix.stickLong, OrePrefix.plate, OrePrefix.ingot, OrePrefix.plateDense, OrePrefix.rotor,
            OrePrefix.bolt, OrePrefix.screw, OrePrefix.wireFine, OrePrefix.foil, OrePrefix.dust, OrePrefix.ring};

    public static void register() {
        for (OrePrefix orePrefix : POLARIZING_PREFIXES) {
            orePrefix.addProcessingHandler(PropertyKey.INGOT, TKCYAPolarizingRecipeHandler::processPolarizing);
        }
    }

    public static void processPolarizing(OrePrefix polarizingPrefix, Material material, IngotProperty property) {
        Material magneticMaterial = property.getMagneticMaterial();

        if (TKCYAConfigHolder.magneticOverhaul.enableMagneticOverhaul) {

            if (magneticMaterial != null && polarizingPrefix.doGenerateItem(magneticMaterial)) {
                ItemStack magneticStack = OreDictUnifier.get(polarizingPrefix, magneticMaterial);

                GTRecipeHandler.removeRecipesByInputs(POLARIZER_RECIPES, OreDictUnifier.get(polarizingPrefix, material));

                TKCYARecipeMaps.ADVANCED_POLARIZER_RECIPES.recipeBuilder() //polarizing
                        .input(polarizingPrefix, material)
                        .input(OrePrefix.dust, Materials.Magnetite)
                        .outputs(magneticStack)
                        .duration((int) ((int) material.getMass() * polarizingPrefix.getMaterialAmount(material) / GTValues.M))
                        .EUt(8 * getVoltageMultiplier(material))
                        .buildAndRegister();
            }
        }
    }

    private static int getVoltageMultiplier(Material material) {
        return material.getBlastTemperature() >= 1200 ? VA[LV] : 2;
    }

}
