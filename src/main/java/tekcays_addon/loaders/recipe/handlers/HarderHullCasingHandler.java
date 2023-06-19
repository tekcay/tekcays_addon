package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import gregtech.common.items.ToolItems;
import gregtech.common.metatileentities.MetaTileEntities;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;
import tekcays_addon.gtapi.unification.TKCYAMaterials;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.gtapi.consts.TKCYAValues.SECOND;

public class HarderHullCasingHandler {

    public static void init() {
        recipeRemoval();
        recipeAddition();
    }

    private static void recipeAddition() {
        primitiveFrame();
        machineCasingAddition();
        hullAddition();
    }

    private static void primitiveFrame() {
        TKCYARecipeMaps.BLOCK_MAKING.recipeBuilder()
                .input(OrePrefix.stick, Steel, 12)
                .input(ToolItems.HARD_HAMMER.get())
                .output(OrePrefix.frameGt, Steel)
                .duration(5 * SECOND)
                .buildAndRegister();

        TKCYARecipeMaps.BLOCK_MAKING.recipeBuilder()
                .input(OrePrefix.stick, TKCYAMaterials.GalvanizedSteel, 12)
                .input(ToolItems.HARD_HAMMER.get())
                .output(OrePrefix.frameGt, TKCYAMaterials.GalvanizedSteel)
                .duration(5 * SECOND)
                .buildAndRegister();
    }

    private static void machineCasingAddition() {
        TKCYARecipeMaps.BLOCK_MAKING.recipeBuilder()
                .inputs(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.ULV))
                .input(OrePrefix.cableGtSingle, RedAlloy, 8)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.ULV)
                .input(ToolItems.WIRE_CUTTER.get())
                .input(MetaItems.VOLTAGE_COIL_ULV)
                .fluidInputs(Materials.Tin.getFluid(288))
                .outputs(MetaTileEntities.HULL[ULV].getStackForm())
                .duration(5 * SECOND)
                .buildAndRegister();

        TKCYARecipeMaps.BLOCK_MAKING.recipeBuilder()
                .inputs(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.LV))
                .input(OrePrefix.cableGtSingle, Tin, 8)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.LV)
                .input(MetaItems.VOLTAGE_COIL_LV)
                .input(ToolItems.WIRE_CUTTER.get())
                .fluidInputs(Materials.Tin.getFluid(288))
                .outputs(MetaTileEntities.HULL[LV].getStackForm())
                .duration(5 * SECOND)
                .buildAndRegister();

        TKCYARecipeMaps.BLOCK_MAKING.recipeBuilder()
                .inputs(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.MV))
                .input(OrePrefix.cableGtSingle, Copper, 8)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.MV)
                .input(MetaItems.VOLTAGE_COIL_MV)
                .input(ToolItems.WIRE_CUTTER.get())
                .fluidInputs(Materials.Tin.getFluid(288))
                .outputs(MetaTileEntities.HULL[MV].getStackForm())
                .duration(5 * SECOND)
                .buildAndRegister();

    }

    private static void hullAddition() {
        TKCYARecipeMaps.BLOCK_MAKING.recipeBuilder()
                .input(OrePrefix.plate, Steel, 6)
                .input(OrePrefix.frameGt, Steel)
                .input(ToolItems.HARD_HAMMER.get())
                .outputs(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.ULV))
                .duration(5 * SECOND)
                .buildAndRegister();

        TKCYARecipeMaps.BLOCK_MAKING.recipeBuilder()
                .input(OrePrefix.plate, TKCYAMaterials.GalvanizedSteel, 6)
                .input(OrePrefix.frameGt, TKCYAMaterials.GalvanizedSteel)
                .input(ToolItems.HARD_HAMMER.get())
                .outputs(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.ULV))
                .duration(5 * SECOND)
                .buildAndRegister();

    }

    private static void recipeRemoval() {
        hullRemoval();
        machineCasingRemoval();
    }

    private static void frameRemoval() {
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES, OreDictUnifier.get(OrePrefix.stick, Steel, 4), IntCircuitIngredient.getIntegratedCircuit(4));
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES, OreDictUnifier.get(OrePrefix.stick, TKCYAMaterials.GalvanizedSteel, 4), IntCircuitIngredient.getIntegratedCircuit(4));

        ModHandler.removeRecipeByOutput(OreDictUnifier.get(OrePrefix.frameGt, Steel));
        ModHandler.removeRecipeByOutput(OreDictUnifier.get(OrePrefix.frameGt, TKCYAMaterials.GalvanizedSteel));
    }

    private static void hullRemoval() {
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES, OreDictUnifier.get(OrePrefix.plate, WroughtIron, 8), IntCircuitIngredient.getIntegratedCircuit(8));
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES, OreDictUnifier.get(OrePrefix.plate, Steel, 8), IntCircuitIngredient.getIntegratedCircuit(8));
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES, OreDictUnifier.get(OrePrefix.plate, Aluminium, 8), IntCircuitIngredient.getIntegratedCircuit(8));


        ModHandler.removeRecipeByOutput(MetaTileEntities.HULL[ULV].getStackForm());
        ModHandler.removeRecipeByOutput(MetaTileEntities.HULL[LV].getStackForm());
        ModHandler.removeRecipeByOutput(MetaTileEntities.HULL[MV].getStackForm());
    }

    private static void machineCasingRemoval() {
        ModHandler.removeRecipeByOutput(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.ULV));
        ModHandler.removeRecipeByOutput(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.LV));
        ModHandler.removeRecipeByOutput(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.MV));
    }
}
