package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialFlag;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import tekcays_addon.api.recipes.TKCYARecipeMaps;

import java.sql.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static gregtech.api.GTValues.L;
import static gregtech.api.unification.ore.OrePrefix.*;

public class TKCYAMeltingRecipeHandler {

    public static void init() {

            for (Material material : GregTechAPI.MATERIAL_REGISTRY) {
                if (material.hasProperty(PropertyKey.DUST)) continue;
                if (material.hasProperty(PropertyKey.FLUID)) continue;
                if (material.getFluid().getTemperature() > 300) continue;
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
