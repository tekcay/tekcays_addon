package tekcays_addon.loaders.recipe.removals;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.item.ItemStack;

public class RecipesRemovalHandler {

    public static void steelRemovalsInit() {
        // Assembler recipe
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES,
            new ItemStack[] {
                    OreDictUnifier.get(OrePrefix.plate, Materials.Steel, 8),
                    IntCircuitIngredient.getIntegratedCircuit(8)},
            null);

        //Shaped recipe

        ModHandler.removeRecipes(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.LV));


    }


}
