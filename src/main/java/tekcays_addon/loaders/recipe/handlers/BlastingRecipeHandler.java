package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.GTValues;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.items.MetaItems;
import tekcays_addon.api.unification.TKCYAMaterials;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockBrick;

import static gregtech.api.unification.material.Materials.Brick;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static tekcays_addon.api.recipes.TKCYARecipeMaps.BLASTING_RECIPES;
import static tekcays_addon.api.utils.TKCYAValues.MINUTE;
import static tekcays_addon.common.metatileentities.TKCYAMetaTileEntities.*;

public class BlastingRecipeHandler {

    public static void init() {
        //Pig Iron

        ////From Limonite

        RecipeMaps.FURNACE_RECIPES.recipeBuilder()
                .input(dust, Materials.YellowLimonite, 8)
                .output(dust, Materials.Hematite, 5)
                .duration(100)
                .buildAndRegister();

        RecipeMaps.FURNACE_RECIPES.recipeBuilder()
                .input(dust, Materials.BrownLimonite, 8)
                .output(dust, Materials.Hematite, 5)
                .duration(100)
                .buildAndRegister();

        ////From Hematite
        //Fe2O3 + 3 CO -> 2 Fe + 3 CO2
        BLASTING_RECIPES.recipeBuilder()
                .temperature(2 * MINUTE)
                .input(dust, Materials.Hematite, 5)
                .input(dust, Materials.Coke, 2)
                .fluidOutputs(TKCYAMaterials.PigIron.getFluid(GTValues.L))
                .duration(200)
                .buildAndRegister();

        ////From Magnetite
        //Fe3O4 + 4 CO -> 3 Fe + 4 CO2
        BLASTING_RECIPES.recipeBuilder()
                .temperature(1800)
                .input(dust, Materials.Magnetite, 7)
                .input(dust, Materials.Coke, 2)
                .fluidOutputs(TKCYAMaterials.PigIron.getFluid(GTValues.L * 3))
                .duration(2 * MINUTE)
                .buildAndRegister();

        //Tin

        ////From Cassiterite
        //SnO2 + 2 CO -> Sn + 2 CO2
        BLASTING_RECIPES.recipeBuilder()
                .temperature(1500)
                .input(dust, Materials.Cassiterite, 3)
                .input(dust, Materials.Coke, 2)
                .fluidOutputs(Materials.Tin.getFluid(GTValues.L))
                .duration(2 * MINUTE)
                .buildAndRegister();


        //Bricks recipes

        ModHandler.addShapedRecipe("brick", TKCYAMetaBlocks.BLOCK_BRICK.getItemVariant(BlockBrick.BrickType.BRICK),
                "BBB", "BBB", "BBB",
                'B', new UnificationEntry(ingot, Brick));

        ModHandler.addShapedRecipe("brick", TKCYAMetaBlocks.BLOCK_BRICK.getItemVariant(BlockBrick.BrickType.REINFORCED_BRICK),
                "CBC", "BCB", "CBC",
                'B', new UnificationEntry(ingot, Brick),
                'C', MetaItems.COKE_OVEN_BRICK);

        ModHandler.addShapedRecipe("brick", TKCYAMetaBlocks.BLOCK_BRICK.getItemVariant(BlockBrick.BrickType.FIRECLAY_BRICK),
                "BBB", "BBB", "BBB",
                'B', MetaItems.FIRECLAY_BRICK);

        //Controller recipes

        ModHandler.addShapedRecipe("brick", BRICK_BLAST_FURNACE.getStackForm(),
                "  ", "B ", "  ",
                'B', TKCYAMetaBlocks.BLOCK_BRICK.getItemVariant(BlockBrick.BrickType.BRICK));

        ModHandler.addShapedRecipe("brick", REINFORCED_BRICK_BLAST_FURNACE.getStackForm(),
                " w ", "B ", " h ",
                'B', TKCYAMetaBlocks.BLOCK_BRICK.getItemVariant(BlockBrick.BrickType.REINFORCED_BRICK));

        ModHandler.addShapedRecipe("brick", FIRECLAY_BRICK_BLAST_FURNACE.getStackForm(),
                " w ", "B ", " h ",
                'B', TKCYAMetaBlocks.BLOCK_BRICK.getItemVariant(BlockBrick.BrickType.FIRECLAY_BRICK));

    }

}
