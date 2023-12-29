package tekcays_addon.loaders.recipe.handlers;

import static gregtech.api.unification.ore.OrePrefix.dust;
import static tekcays_addon.api.material.MaterialHelper.getAllMaterials;
import static tekcays_addon.gtapi.consts.TKCYAValues.STANDARD_UNIT;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;
import tekcays_addon.gtapi.consts.TKCYAValues;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;

public class TKCYAMeltingRecipeHandler {

    public static void init() {
        getAllMaterials().stream()
                .filter(material -> material.hasProperty(PropertyKey.DUST))
                .filter(Material::hasFluid)
                .filter(material -> material.getFluid().getTemperature() > TKCYAValues.ROOM_TEMPERATURE)
                .forEach(TKCYAMeltingRecipeHandler::registerMeltingRecipes);
    }

    public static void registerMeltingRecipes(Material material) {
        TKCYARecipeMaps.MELTER_RECIPES.recipeBuilder()
                .temperature(material.getFluid().getTemperature())
                .input(dust, material)
                .fluidOutputs(material.getFluid(STANDARD_UNIT))
                .duration((int) material.getMass())
                .buildAndRegister();
    }
}
