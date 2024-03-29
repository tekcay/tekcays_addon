package tekcays_addon.loaders.recipe.handlers;

import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static tekcays_addon.common.metatileentities.TKCYAMetaTileEntities.*;
import static tekcays_addon.gtapi.consts.TKCYAValues.MINUTE;
import static tekcays_addon.gtapi.consts.TKCYAValues.SECOND;
import static tekcays_addon.gtapi.recipes.TKCYARecipeMaps.*;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import gregtech.api.GTValues;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockBrick;
import tekcays_addon.gtapi.unification.TKCYAMaterials;

public class BlastingRecipeHandler {

    public static void init() {
        // Pig Iron

        //// From Limonite

        BLASTING_RECIPES.recipeBuilder()
                .minTemperature(600)
                .input(dust, Materials.YellowLimonite, 8)
                .output(dust, Materials.BandedIron, 5)
                .duration(MINUTE)
                .buildAndRegister();

        BLASTING_RECIPES.recipeBuilder()
                .minTemperature(600)
                .input(dust, Materials.BrownLimonite, 8)
                .output(dust, Materials.BandedIron, 5)
                .duration(MINUTE)
                .buildAndRegister();

        //// From BandedIron
        // Fe2O3 + 3 CO -> 2 Fe + 3 CO2
        BLASTING_RECIPES.recipeBuilder()
                .minTemperature(1800)
                .input(dust, Materials.BandedIron, 5)
                .input(dust, Materials.Coke, 2)
                .input(dust, Materials.Stone, 3)
                .fluidOutputs(TKCYAMaterials.PigIron.getFluid(GTValues.L))
                .duration(MINUTE)
                .buildAndRegister();

        ADVANCED_BLAST_FURNACE_RECIPES.recipeBuilder()
                .minTemperature(1800)
                .input(dust, Materials.BandedIron, 5)
                .input(dust, Materials.Coke, 2)
                .input(dust, Materials.Stone, 3)
                .fluidOutputs(TKCYAMaterials.PigIron.getFluid(GTValues.L))
                .duration(30 * SECOND)
                .buildAndRegister();

        //// From Magnetite
        // Fe3O4 + 4 CO -> 3 Fe + 4 CO2
        BLASTING_RECIPES.recipeBuilder()
                .minTemperature(1800)
                .input(dust, Materials.Magnetite, 7)
                .input(dust, Materials.Coke, 2)
                .fluidOutputs(TKCYAMaterials.PigIron.getFluid(GTValues.L * 3))
                .duration(2 * MINUTE)
                .buildAndRegister();

        ADVANCED_BLAST_FURNACE_RECIPES.recipeBuilder()
                .minTemperature(1800)
                .input(dust, Materials.Magnetite, 7)
                .input(dust, Materials.Coke, 2)
                .fluidOutputs(TKCYAMaterials.PigIron.getFluid(GTValues.L * 3))
                .duration(30 * SECOND)
                .buildAndRegister();

        // Tin

        //// From Cassiterite
        // SnO2 + 2 CO -> Sn + 2 CO2
        BLASTING_RECIPES.recipeBuilder()
                .minTemperature(1500)
                .input(dust, Materials.Cassiterite, 3)
                .input(dust, Materials.Coke, 2)
                .fluidOutputs(Materials.Tin.getFluid(GTValues.L))
                .duration(2 * MINUTE)
                .buildAndRegister();

        ADVANCED_BLAST_FURNACE_RECIPES.recipeBuilder()
                .minTemperature(1500)
                .input(dust, Materials.Cassiterite, 3)
                .input(dust, Materials.Coke, 2)
                .fluidOutputs(Materials.Tin.getFluid(GTValues.L))
                .duration(30 * SECOND)
                .buildAndRegister();        // Tin

        //// From Garnierite
        // NiO + CO -> Ni + CO2
        BLASTING_RECIPES.recipeBuilder()
                .minTemperature(1500)
                .input(dust, Garnierite, 2)
                .input(dust, Materials.Coke, 1)
                .fluidOutputs(Nickel.getFluid(GTValues.L))
                .duration(2 * MINUTE)
                .buildAndRegister();

        ADVANCED_BLAST_FURNACE_RECIPES.recipeBuilder()
                .minTemperature(1500)
                .input(dust, Garnierite, 2)
                .input(dust, Materials.Coke, 1)
                .fluidOutputs(Nickel.getFluid(GTValues.L))
                .duration(30 * SECOND)
                .buildAndRegister();

        // Bricks recipes
        ModHandler.addShapedRecipe("brick", TKCYAMetaBlocks.BLOCK_BRICK.getItemVariant(BlockBrick.BrickType.BRICK),
                "BBB", "BBB", "BBB",
                'B', new UnificationEntry(ingot, Brick));

        ModHandler.addShapedRecipe("reinforced_brick",
                TKCYAMetaBlocks.BLOCK_BRICK.getItemVariant(BlockBrick.BrickType.REINFORCED_BRICK),
                "CBC", "BCB", "CBC",
                'B', new UnificationEntry(ingot, Brick),
                'C', MetaItems.COKE_OVEN_BRICK);

        ModHandler.addShapedRecipe("fireclay_brick",
                TKCYAMetaBlocks.BLOCK_BRICK.getItemVariant(BlockBrick.BrickType.FIRECLAY_BRICK),
                "BBB", "BBB", "BBB",
                'B', MetaItems.FIRECLAY_BRICK);

        /*
         * ModHandler.addShapedRecipe("strong_brick",
         * TKCYAMetaBlocks.BLOCK_BRICK.getItemVariant(BlockBrick.BrickType.STRONG_BRICK),
         * "BBB", "BBB", "BBB",
         * 'B', MetaItems.FIRECLAY_BRICK);
         * 
         */

        // Fluid Hatch recipes
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
         * ModHandler.addShapedRecipe("strong_brick_export_fluid_hatch", BRICK_EXPORT_FLUID_HATCH[3].getStackForm(),
         * "BBB", "BBB", "BBB",
         * 'B', MetaItems.FIRECLAY_BRICK);
         */

        // Import Item Bus recipes
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
         * ModHandler.addShapedRecipe("strong_brick_import_item_bus", BRICK_IMPORT_ITEM_BUS[3].getStackForm(),
         * "BBB", "BCB", "BBB",
         * 'C', "chestWood",
         * 'B', MetaItems.FIRECLAY_BRICK);
         * 
         */

        // Export Item Bus recipes
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
         * ModHandler.addShapedRecipe("strong_brick_export_item_bus", BRICK_EXPORT_ITEM_BUS[3].getStackForm(),
         * "BBB", "BBB", "BCB",
         * 'C', "chestWood",
         * 'B', MetaItems.FIRECLAY_BRICK);
         * 
         */

        // Muffler recipes
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
         * ModHandler.addShapedRecipe("strong_brick_muffler", PRIMITIVE_MUFFLER[3].getStackForm(),
         * "BBB", "BCB", "BBB",
         * 'C', new ItemStack(Blocks.FURNACE),
         * 'B', MetaItems.FIRECLAY_BRICK);
         */

        // Controller recipes
        ModHandler.addShapedRecipe("brick_blast_furnace_controller", BLAST_FURNACE[0].getStackForm(),
                "   ", " B ", "   ",
                'B', TKCYAMetaBlocks.BLOCK_BRICK.getItemVariant(BlockBrick.BrickType.BRICK));

        ModHandler.addShapedRecipe("reinforced_brick_blast_furnace_controller", BLAST_FURNACE[1].getStackForm(),
                " w ", " B ", " h ",
                'B', TKCYAMetaBlocks.BLOCK_BRICK.getItemVariant(BlockBrick.BrickType.REINFORCED_BRICK));

        ModHandler.addShapedRecipe("fireclay_brick_blast_furnace_controller", BLAST_FURNACE[2].getStackForm(),
                " w ", " B ", " h ",
                'B', TKCYAMetaBlocks.BLOCK_BRICK.getItemVariant(BlockBrick.BrickType.FIRECLAY_BRICK));

        /*
         * ModHandler.addShapedRecipe("strong_brick_blast_furnace_controller", BLAST_FURNACE[3].getStackForm(),
         * " w ", " B ", " h ",
         * 'B', TKCYAMetaBlocks.BLOCK_BRICK.getItemVariant(BlockBrick.BrickType.FIRECLAY_BRICK));
         */

        // Primitive converter
        ModHandler.addShapedRecipe("primitive_converter_controller", PRIMITIVE_CONVERTER.getStackForm(),
                "   ", "wBh", "   ",
                'B', TKCYAMetaBlocks.BLOCK_BRICK.getItemVariant(BlockBrick.BrickType.REINFORCED_BRICK));

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(pipeNormalFluid, Potin)
                .inputs(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.ULV))
                .outputs(MetaTileEntities.HULL[0].getStackForm())
                .EUt(8)
                .duration(5 * SECOND)
                .buildAndRegister();
    }
}
