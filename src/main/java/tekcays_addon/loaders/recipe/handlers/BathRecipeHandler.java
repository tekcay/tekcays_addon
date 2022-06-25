package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.GTValues;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.ore.OrePrefix;
import tekcays_addon.api.recipes.TKCYARecipeMaps;

import static gregtech.api.GTValues.LV;
import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.api.unification.TKCYAMaterials.GalvanizedSteel;

public class BathRecipeHandler {

    public static void galvanizingSteelInit(){

        for (OrePrefix orePrefix : OrePrefix.values()) {

            if (orePrefix == OrePrefix.dust
                    || orePrefix == OrePrefix.dustSmall
                    || orePrefix == OrePrefix.dustTiny
                    || orePrefix == OrePrefix.nugget
                    || !orePrefix.doGenerateItem(Steel)
                    || !orePrefix.doGenerateItem(GalvanizedSteel)) continue;


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
