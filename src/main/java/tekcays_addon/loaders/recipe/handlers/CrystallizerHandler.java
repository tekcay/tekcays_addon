package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.GTValues;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.MetaItems;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.recipes.TKCYARecipeMaps;

import static tekcays_addon.api.utils.TKCYAValues.*;
import static tekcays_addon.loaders.recipe.handlers.TKCYACastingTableRecipeHandler.MOLD_PRODUCTION;

public class CrystallizerHandler {

    public static void boules() {

        TKCYARecipeMaps.CRYSTALLIZATION.recipeBuilder()
                .fluidInputs(Materials.Silicon.getFluid(32 * GTValues.L))
                .fluidInputs(Materials.GalliumArsenide.getFluid(GTValues.L/4))
                .output(MetaItems.SILICON_BOULE)
                .duration(450 * 20)
                .buildAndRegister();

    }

    public static void polymers() {

        for (OrePrefix orePrefix : MOLD_PRODUCTION.keySet()) {
            for (Material m : MOLD_MATERIALS) {
                for (Material polymer : POLYMERS) {

                    if (!orePrefix.doGenerateItem(polymer)) continue;

                    int fluidInputs = (int) (orePrefix.getMaterialAmount(polymer) * GTValues.L / GTValues.M);

                    TKCYARecipeMaps.CRYSTALLIZATION.recipeBuilder()
                            .fluidInputs(polymer.getFluid(fluidInputs))
                            .notConsumable(MOLD_PRODUCTION.get(orePrefix), m)
                            .output(orePrefix, polymer)
                            .duration(polymer.getFluid().getTemperature() * fluidInputs)
                            .buildAndRegister();
                }
            }
        }

    }



}
