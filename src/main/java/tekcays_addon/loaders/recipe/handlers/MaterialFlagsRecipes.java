package tekcays_addon.loaders.recipe.handlers;

import static gregtech.api.unification.ore.OrePrefix.dust;
import static tekcays_addon.api.material.MaterialHelper.*;
import static tekcays_addon.gtapi.unification.material.info.TKCYAMaterialFlags.BATH_FLUID;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.stack.MaterialStack;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;
import tekcays_addon.gtapi.unification.material.info.TKCYAMaterialFlags;

public class MaterialFlagsRecipes {

    public static void init() {
        for (Material material : getAllMaterials()) {
            if (material.hasFlag(TKCYAMaterialFlags.GENERATE_FILTRATION)) {
                registerFiltrationRecipes(material);
            }

            if (material.hasFlag(TKCYAMaterialFlags.GENERATE_SPIRAL_SEPARATION)) {
                registerSpiralSeparationRecipes(material);
            }
        }
    }

    private static void registerFiltrationRecipes(Material material) {
        MaterialStack[] outputMaterialStacks = separateMaterialsForFiltration(material);

        MaterialStack bathFluid = outputMaterialStacks[0];
        MaterialStack dustInput = outputMaterialStacks[1];

        TKCYARecipeMaps.FILTRATION.recipeBuilder()
                .output(dust, dustInput.material, (int) dustInput.amount)
                .fluidInputs(material.getFluid(1000))
                .fluidOutputs(bathFluid.material.getFluid((int) (bathFluid.amount * 1000)))
                .duration(100)
                .buildAndRegister();
    }

    private static void registerSpiralSeparationRecipes(Material material) {
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, material, getAmountComponentsSum(material))
                .outputs(getStacksFromMaterialComposition(material))
                .duration(100)
                .EUt(20)
                .buildAndRegister();
    }

    /**
     * Helps the generation of the filtration recipes.
     * <br>
     * <br>
     * {@code array[0]} -> the materialStack having the {@link TKCYAMaterialFlags#BATH_FLUID} and will be used as the
     * {@code fluidOutputs}.
     * <br>
     * <br>
     * {@code array[1]} -> the materialStack that will be used as the {@code outputs}.
     * <br>
     * 
     * @param material the material to decompose
     * @return a sorted array of {@link MaterialStack} containing the components {@link Material} of the provided
     *         {@code material}.
     */
    private static MaterialStack[] separateMaterialsForFiltration(Material material) {
        MaterialStack[] materialStacks = new MaterialStack[2];

        MaterialStack materialStack1 = material.getMaterialComponents().get(0);
        MaterialStack materialStack2 = material.getMaterialComponents().get(1);

        if (materialStack1.material.hasFlag(BATH_FLUID)) {
            materialStacks[0] = materialStack1;
            materialStacks[1] = materialStack2;
        } else {
            materialStacks[0] = materialStack2;
            materialStacks[1] = materialStack1;
        }

        return materialStacks;
    }
}
