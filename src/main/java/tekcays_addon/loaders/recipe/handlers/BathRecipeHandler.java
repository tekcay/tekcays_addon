package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.GTValues;
import gregtech.api.items.metaitem.MetaOreDictItem;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.OreDictExprFilter;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.api.utils.TKCYALog;
import tekcays_addon.api.utils.TKCYAValues;

import static gregtech.api.GTValues.LV;
import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.api.unification.TKCYAMaterials.GalvanizedSteel;

public class BathRecipeHandler {

    public static void galvanizingSteelInit(){

        for (OrePrefix orePrefix : TKCYAValues.STEEL_TO_GALVANIZED_OREPREFIXES) {

            RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder()
                    .input(orePrefix, Steel)
                    .fluidInputs(Zinc.getFluid((int) (orePrefix.getMaterialAmount(Steel) * GTValues.L / (GTValues.M * 9))))
                    .duration((int) (10 + Steel.getMass() * orePrefix.getMaterialAmount(Steel) / GTValues.M))
                    .output(orePrefix, GalvanizedSteel)
                    .EUt((int) GTValues.V[LV])
                    .buildAndRegister();

            TKCYARecipeMaps.PRIMITIVE_BATH.recipeBuilder()
                    .input(orePrefix, Steel)
                    .fluidInputs(Zinc.getFluid((int) (orePrefix.getMaterialAmount(Steel) * GTValues.L / (GTValues.M * 9))))
                    .output(orePrefix, GalvanizedSteel)
                    .duration((int) (10 + 4 * Steel.getMass() * orePrefix.getMaterialAmount(Steel) / GTValues.M))
                    .buildAndRegister();

        }
    }

    public static void treatingWoodInit() {

            TKCYARecipeMaps.PRIMITIVE_BATH.recipeBuilder()
                    .input(OrePrefix.plank, Wood)
                    .fluidInputs(Creosote.getFluid(100))
                    .output(OrePrefix.plank, TreatedWood)
                    .duration(200)
                    .buildAndRegister();
    }
}
