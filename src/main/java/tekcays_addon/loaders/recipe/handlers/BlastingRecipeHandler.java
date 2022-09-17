package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.GTValues;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.items.MetaItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import tekcays_addon.api.unification.TKCYAMaterials;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockBrick;

import static gregtech.api.unification.material.Materials.Brick;
import static gregtech.api.unification.ore.OrePrefix.*;
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
                .EUt(8)
                .buildAndRegister();

        RecipeMaps.FURNACE_RECIPES.recipeBuilder()
                .input(dust, Materials.BrownLimonite, 8)
                .output(dust, Materials.Hematite, 5)
                .duration(100)
                .EUt(8)
                .buildAndRegister();

        ////From Hematite
        //Fe2O3 + 3 CO -> 2 Fe + 3 CO2
        BLASTING_RECIPES.recipeBuilder()
                .temperature(2 * MINUTE)
                .input(dust, Materials.Hematite, 5)
                .input(dust, Materials.Coke, 2)
                .input(dust, Materials.Stone, 3)
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

        ModHandler.addShapedRecipe("reinforced_brick", TKCYAMetaBlocks.BLOCK_BRICK.getItemVariant(BlockBrick.BrickType.REINFORCED_BRICK),
                "CBC", "BCB", "CBC",
                'B', new UnificationEntry(ingot, Brick),
                'C', MetaItems.COKE_OVEN_BRICK);

        ModHandler.addShapedRecipe("fireclay_brick", TKCYAMetaBlocks.BLOCK_BRICK.getItemVariant(BlockBrick.BrickType.FIRECLAY_BRICK),
                "BBB", "BBB", "BBB",
                'B', MetaItems.FIRECLAY_BRICK);

        /*
        ModHandler.addShapedRecipe("strong_brick", TKCYAMetaBlocks.BLOCK_BRICK.getItemVariant(BlockBrick.BrickType.STRONG_BRICK),
                "BBB", "BBB", "BBB",
                'B', MetaItems.FIRECLAY_BRICK);

         */

        //Fluid Hatch recipes
        ModHandler.addShapedRecipe("brick_export_fluid_hatch", BRICK_EXPORT_FLUID_HATCH[0].getStackForm(),
                "BBB", "B B", "BBB",
                'B', new UnificationEntry(ingot, Brick));

        ModHandler.addShapedRecipe("reinforced_brick_export_fluid_hatch", BRICK_EXPORT_FLUID_HATCH[1].getStackForm(),
                "CBC", "B B", "CBC",
                'B', new UnificationEntry(ingot, Brick),
                'C', MetaItems.COKE_OVEN_BRICK);

        ModHandler.addShapedRecipe("fireclay_brick_export_fluid_hatch", BRICK_EXPORT_FLUID_HATCH[2].getStackForm(),
                "BBB", "B B", "BBB",
                'B', MetaItems.FIRECLAY_BRICK);

        /*
        ModHandler.addShapedRecipe("strong_brick_export_fluid_hatch", BRICK_EXPORT_FLUID_HATCH[3].getStackForm(),
                "BBB", "BBB", "BBB",
                'B', MetaItems.FIRECLAY_BRICK);
         */

        //Import Item Bus recipes
        ModHandler.addShapedRecipe("brick_import_item_bus", BRICK_IMPORT_ITEM_BUS[0].getStackForm(),
                "BBB", "BCB", "BBB",
                'C', "chestWood",
                'B', new UnificationEntry(ingot, Brick));

        ModHandler.addShapedRecipe("reinforced_brick_import_item_bus", BRICK_IMPORT_ITEM_BUS[1].getStackForm(),
                "OBO", "BCB", "OBO",
                'C', "chestWood",
                'B', new UnificationEntry(ingot, Brick),
                'O', MetaItems.COKE_OVEN_BRICK);

        ModHandler.addShapedRecipe("fireclay_brick_import_item_bus", BRICK_IMPORT_ITEM_BUS[2].getStackForm(),
                "BBB", "BCB", "BBB",
                'C', "chestWood",
                'B', MetaItems.FIRECLAY_BRICK);

        /*
        ModHandler.addShapedRecipe("strong_brick_import_item_bus", BRICK_IMPORT_ITEM_BUS[3].getStackForm(),
                "BBB", "BCB", "BBB",
                'C', "chestWood",
                'B', MetaItems.FIRECLAY_BRICK);

         */

        //Export Item Bus recipes
        ModHandler.addShapedRecipe("brick_export_item_bus", BRICK_EXPORT_ITEM_BUS[0].getStackForm(),
                "BBB", "BBB", "BCB",
                'C', "chestWood",
                'B', new UnificationEntry(ingot, Brick));

        ModHandler.addShapedRecipe("reinforced_brick_export_item_bus", BRICK_EXPORT_ITEM_BUS[1].getStackForm(),
                "OBO", "BBB", "OCO",
                'C', "chestWood",
                'B', new UnificationEntry(ingot, Brick),
                'O', MetaItems.COKE_OVEN_BRICK);

        ModHandler.addShapedRecipe("fireclay_brick_export_item_bus", BRICK_EXPORT_ITEM_BUS[2].getStackForm(),
                "BBB", "BBB", "BCB",
                'C', "chestWood",
                'B', MetaItems.FIRECLAY_BRICK);

        /*
        ModHandler.addShapedRecipe("strong_brick_export_item_bus", BRICK_EXPORT_ITEM_BUS[3].getStackForm(),
                "BBB", "BBB", "BCB",
                'C', "chestWood",
                'B', MetaItems.FIRECLAY_BRICK);

         */

        //Muffler recipes
        ModHandler.addShapedRecipe("brick_muffler", PRIMITIVE_MUFFLER[0].getStackForm(),
                "BBB", "BCB", "BBB",
                'C', new ItemStack(Blocks.FURNACE),
                'B', new UnificationEntry(ingot, Brick));

        ModHandler.addShapedRecipe("reinforced_brick_muffler", PRIMITIVE_MUFFLER[1].getStackForm(),
                "OBO", "BCB", "OBO",
                'C', new ItemStack(Blocks.FURNACE),
                'B', new UnificationEntry(ingot, Brick),
                'O', MetaItems.COKE_OVEN_BRICK);

        ModHandler.addShapedRecipe("fireclay_brick_muffler", PRIMITIVE_MUFFLER[2].getStackForm(),
                "BBB", "BCB", "BBB",
                'C', new ItemStack(Blocks.FURNACE),
                'B', MetaItems.FIRECLAY_BRICK);

        /*
        ModHandler.addShapedRecipe("strong_brick_muffler", PRIMITIVE_MUFFLER[3].getStackForm(),
                "BBB", "BCB", "BBB",
                'C', new ItemStack(Blocks.FURNACE),
                'B', MetaItems.FIRECLAY_BRICK);
         */



        //Controller recipes
        ModHandler.addShapedRecipe("brick_blast_furnace", BLAST_FURNACE[0].getStackForm(),
                "   ", " B ", "   ",
                'B', TKCYAMetaBlocks.BLOCK_BRICK.getItemVariant(BlockBrick.BrickType.BRICK));

        ModHandler.addShapedRecipe("reinforced_brick_blast_furnace", BLAST_FURNACE[1].getStackForm(),
                " w ", " B ", " h ",
                'B', TKCYAMetaBlocks.BLOCK_BRICK.getItemVariant(BlockBrick.BrickType.REINFORCED_BRICK));

        ModHandler.addShapedRecipe("fireclay_brick_blast_furnace", BLAST_FURNACE[2].getStackForm(),
                " w ", " B ", " h ",
                'B', TKCYAMetaBlocks.BLOCK_BRICK.getItemVariant(BlockBrick.BrickType.FIRECLAY_BRICK));

        /*
        ModHandler.addShapedRecipe("strong_brick_blast_furnace", BLAST_FURNACE[3].getStackForm(),
                " w ", "B ", " h ",
                'B', TKCYAMetaBlocks.BLOCK_BRICK.getItemVariant(BlockBrick.BrickType.FIRECLAY_BRICK));

         */

    }

}
