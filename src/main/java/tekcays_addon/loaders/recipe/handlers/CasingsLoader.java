package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.ore.OrePrefix;
import tekcays_addon.gtapi.unification.TKCYAMaterials;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockLargeMultiblockCasing;

public class CasingsLoader {

    public static void init() {

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, TKCYAMaterials.Monel, 6)
                .input(OrePrefix.frameGt, TKCYAMaterials.Monel)
                .notConsumable(new IntCircuitIngredient(6))
                .outputs(TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.MONEL_CASING, 2))
                .duration(50).EUt(16).buildAndRegister();


    }

}
