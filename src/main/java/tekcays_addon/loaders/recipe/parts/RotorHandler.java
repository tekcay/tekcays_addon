package tekcays_addon.loaders.recipe.parts;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;

import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.unification.ore.OrePrefix.*;
import static tekcays_addon.gtapi.unification.material.ore.TKCYAOrePrefix.blade;

public class RotorHandler {

    public static void init(Material material) {

            ASSEMBLER_RECIPES.recipeBuilder()
                    .input(blade, material, 4)
                    .input(round, material)
                    .input(screw, material, 4)
                    .output(rotor, material)
                    .duration((int) material.getMass())
                    .EUt(24)
                    .buildAndRegister();

            TKCYARecipeMaps.NEW_ASSEMBLING.recipeBuilder()
                    .input(blade, material, 4)
                    .input(round, material)
                    .output(rotor, material)
                    .fluidInputs(Materials.SolderingAlloy.getFluid(72))
                    .duration((int) material.getMass())
                    .EUt(24)
                    .buildAndRegister();
    }
}
