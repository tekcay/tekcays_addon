package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.GTValues;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.ore.OrePrefix;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.api.unification.TKCYAMaterials;

import static gregtech.api.GTValues.LV;
import static gregtech.api.unification.material.Materials.*;

public class GalvanizingRecipeHandler {

    public static void init(){

        for (OrePrefix orePrefix : OrePrefix.values()) {

            if(!orePrefix.doGenerateItem(TKCYAMaterials.GalvanizedSteel)) continue;

            RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder()
                    .input(orePrefix, Steel)
                    .fluidInputs(Zinc.getFluid((int) (orePrefix.getMaterialAmount(Steel) * GTValues.L / GTValues.M * 9)))
                    .duration((int) orePrefix.getMaterialAmount(Steel))
                    .EUt((int) GTValues.V[LV])
                    .buildAndRegister();

            TKCYARecipeMaps.GALVANIZING.recipeBuilder()
                    .input(orePrefix, Steel)
                    .fluidInputs(Zinc.getFluid((int) (orePrefix.getMaterialAmount(Steel) * GTValues.L / GTValues.M * 9)))
                    .duration((int) orePrefix.getMaterialAmount(Steel) * 4)
                    .buildAndRegister();

        }
    }
}
