package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;

import static gregtech.api.GTValues.L;
import static gregtech.api.unification.ore.OrePrefix.*;
import static tekcays_addon.gtapi.unification.material.info.TKCYAMaterialFlags.POLYMER;

public class TKCYAMeltingRecipeHandler {

    public static void init() {

        for (Material material : GregTechAPI.MATERIAL_REGISTRY) {
            if (!material.hasProperty(PropertyKey.DUST)) continue;
            if (!material.hasProperty(PropertyKey.FLUID)) continue;
            if (material.getFluid().getTemperature() <= 300) continue;
            if (material.hasFlag(POLYMER)) continue;
            registerMeltingRecipes(material);

        }
    }

    public static void registerMeltingRecipes(Material material) {

    TKCYARecipeMaps.MELTER_RECIPES.recipeBuilder()
            .temperature(material.getFluid().getTemperature())
            .input(dust, material)
            .fluidOutputs(material.getFluid(L))
            .duration((int) material.getMass())
            .buildAndRegister();

    }


}
