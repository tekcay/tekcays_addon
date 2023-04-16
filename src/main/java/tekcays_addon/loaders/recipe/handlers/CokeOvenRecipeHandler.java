package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.recipes.ModHandler;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.api.unification.ore.OrePrefix.block;
import static tekcays_addon.gtapi.recipes.TKCYARecipeMaps.COKING;
import static tekcays_addon.gtapi.utils.TKCYAValues.MINUTE;
import static tekcays_addon.common.metatileentities.TKCYAMetaTileEntities.COKE_OVEN;

public class CokeOvenRecipeHandler {
    
    public static void init() {
        COKING.recipeBuilder().input(log, Wood).output(gem, Charcoal).fluidOutputs(Creosote.getFluid(250)).duration(5 * MINUTE).buildAndRegister();
        COKING.recipeBuilder().input(gem, Coal).output(gem, Coke).fluidOutputs(Creosote.getFluid(500)).duration(4 * MINUTE).buildAndRegister();
        COKING.recipeBuilder().input(block, Coal).output(block, Coke).fluidOutputs(Creosote.getFluid(4500)).duration(45 * MINUTE).buildAndRegister();

        //Controller recipes
        ModHandler.addShapedRecipe("coke_oven_controller", COKE_OVEN.getStackForm(),
                " h ", " B ", "   ",
                'B', MetaBlocks.METAL_CASING.getItemVariant(BlockMetalCasing.MetalCasingType.COKE_BRICKS));
    }
}
