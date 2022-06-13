package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.GregTechAPI;
import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;
import tekcays_addon.api.recipes.TKCYARecipeMaps;

import static gregtech.api.GTValues.L;
import static gregtech.api.recipes.RecipeMaps.EXTRACTOR_RECIPES;
import static gregtech.api.unification.ore.OrePrefix.*;

public class TKCYAMeltingRecipeHandler {

    public static void init() {

        for (Material material : GregTechAPI.MATERIAL_REGISTRY) {
            if (!material.hasProperty(PropertyKey.DUST)) continue;
            if (!material.hasProperty(PropertyKey.FLUID)) continue;
            if (material.getFluid().getTemperature() <= 300) continue;
            registerMeltingRecipes(material);

        }
    }

    public static void registerMeltingRecipes(Material material) {

    TKCYARecipeMaps.PRIMITIVE_MELTER_RECIPES.recipeBuilder()
            .input(dust, material)
            .fluidOutputs(material.getFluid(L))
            .duration((int) material.getMass())
            .buildAndRegister();

    TKCYARecipeMaps.ELECTRIC_MELTER_RECIPES.recipeBuilder()
            .setTemp(material.getFluid().getTemperature())
            .input(dust, material)
            .fluidOutputs(material.getFluid(L))
            .duration((int) material.getMass())
            .buildAndRegister();

    }


}