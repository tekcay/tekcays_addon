package tekcays_addon.loaders.recipe.parts;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.stack.UnificationEntry;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;

import static gregtech.api.unification.ore.OrePrefix.plate;
import static tekcays_addon.gtapi.unification.material.ore.TKCYAOrePrefix.curvedPlate;

public class CurvedPlateHandler {

    public static void init(Material material) {
        shapedRecipe(material);
        process(material);
    }

    private static void shapedRecipe(Material material) {
        ModHandler.addShapedRecipe(material.getUnlocalizedName() + "_curved_plate", OreDictUnifier.get(curvedPlate, material),
                " h ", " P ", "   ", 'P', new UnificationEntry(plate, material));
    }

    private static void process(Material material) {
        TKCYARecipeMaps.ROLLING_RECIPES.recipeBuilder()
                .input(plate, material)
                .output(curvedPlate, material)
                .duration((int) material.getMass())
                .EUt(24)
                .buildAndRegister();
    }
}
