package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.MetaBlocks;
import tekcays_addon.api.unification.TKCYAMaterials;

import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;

public class AssemblerRecipeHandler {

    public static void galvanizedSteel() {

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, TKCYAMaterials.GalvanizedSteel)
                .circuitMeta(8)
                .outputs(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.LV))
                .duration(50)
                .EUt(16)
                .buildAndRegister();
    }

}
