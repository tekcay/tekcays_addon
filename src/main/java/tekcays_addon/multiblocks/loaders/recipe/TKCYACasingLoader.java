package tekcays_addon.multiblocks.loaders.recipe;

import tekcays_addon.multiblocks.api.unification.TKCYAMaterials;
import tekcays_addon.multiblocks.common.block.TKCYAMetaBlocks;
import tekcays_addon.multiblocks.common.block.blocks.BlockLargeMultiblockCasing;
import tekcays_addon.multiblocks.common.block.blocks.BlockUniqueCasing;
import gregtech.api.GTValues;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.items.MetaItems;

public class TKCYACasingLoader {

    public static void init() {
        // Multiblock Casings
        ModHandler.addShapedRecipe(true, "casing_large_macerator", TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.MACERATOR_CASING, 2), "PhP", "PFP", "PwP", 'P', new UnificationEntry(OrePrefix.plate, TKCYAMaterials.Zeron100), 'F', new UnificationEntry(OrePrefix.frameGt, Materials.Titanium));
        ModHandler.addShapedRecipe(true, "casing_high_temperature", TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.HIGH_TEMPERATURE_CASING, 2), "DhD", "PFP", "DwD", 'P', new UnificationEntry(OrePrefix.plate, TKCYAMaterials.TitaniumCarbide), 'D', new UnificationEntry(OrePrefix.plate, TKCYAMaterials.HSLASteel), 'F', new UnificationEntry(OrePrefix.frameGt, Materials.TungstenCarbide));
        ModHandler.addShapedRecipe(true, "casing_large_assembler", TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.ASSEMBLING_CASING, 2), "PhP", "PFP", "PwP", 'P', new UnificationEntry(OrePrefix.plate, TKCYAMaterials.Stellite100), 'F', new UnificationEntry(OrePrefix.frameGt, Materials.Tungsten));
        ModHandler.addShapedRecipe(true, "casing_stress_proof", TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.STRESS_PROOF_CASING, 2), "PhP", "PFP", "PwP", 'P', new UnificationEntry(OrePrefix.plate, TKCYAMaterials.MaragingSteel300), 'F', new UnificationEntry(OrePrefix.frameGt, Materials.StainlessSteel));
        ModHandler.addShapedRecipe(true, "casing_corrosion_proof", TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.CORROSION_PROOF_CASING, 2), "PhP", "PFP", "PwP", 'P', new UnificationEntry(OrePrefix.plate, Materials.CobaltBrass), 'F', new UnificationEntry(OrePrefix.frameGt, TKCYAMaterials.HSLASteel));
        ModHandler.addShapedRecipe(true, "casing_vibration_safe", TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.VIBRATION_SAFE_CASING, 2), "PhP", "PFP", "PwP", 'P', new UnificationEntry(OrePrefix.plate, TKCYAMaterials.IncoloyMA956), 'F', new UnificationEntry(OrePrefix.frameGt, TKCYAMaterials.IncoloyMA956));
        ModHandler.addShapedRecipe(true, "casing_watertight", TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.WATERTIGHT_CASING, 2), "PhP", "PFP", "PwP", 'P', new UnificationEntry(OrePrefix.plate, TKCYAMaterials.WatertightSteel), 'F', new UnificationEntry(OrePrefix.frameGt, TKCYAMaterials.WatertightSteel));
        ModHandler.addShapedRecipe(true, "casing_large_cutter", TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.CUTTER_CASING, 2), "PhP", "PFP", "PwP", 'P', new UnificationEntry(OrePrefix.plate, TKCYAMaterials.HastelloyC276), 'F', new UnificationEntry(OrePrefix.frameGt, TKCYAMaterials.HastelloyC276));
        ModHandler.addShapedRecipe(true, "casing_nonconducting", TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.NONCONDUCTING_CASING, 2), "PhP", "PFP", "PwP", 'P', new UnificationEntry(OrePrefix.plate, TKCYAMaterials.HSLASteel), 'F', new UnificationEntry(OrePrefix.frameGt, TKCYAMaterials.HSLASteel));
        ModHandler.addShapedRecipe(true, "casing_large_mixer", TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.MIXER_CASING, 2), "PhP", "PFP", "PwP", 'P', new UnificationEntry(OrePrefix.plate, TKCYAMaterials.HastelloyX), 'F', new UnificationEntry(OrePrefix.frameGt, TKCYAMaterials.MaragingSteel300));
        ModHandler.addShapedRecipe(true, "casing_large_engraver", TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.ENGRAVER_CASING, 2), "PhP", "PFP", "PwP", 'P', new UnificationEntry(OrePrefix.plate, TKCYAMaterials.TitaniumTungstenCarbide), 'F', new UnificationEntry(OrePrefix.frameGt, Materials.Titanium));
        ModHandler.addShapedRecipe(true, "casing_atomic", TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.ATOMIC_CASING, 2), "PhP", "PFP", "PwP", 'P', new UnificationEntry(OrePrefix.plateDouble, TKCYAMaterials.Trinaquadalloy), 'F', new UnificationEntry(OrePrefix.frameGt, Materials.NaquadahAlloy));
        ModHandler.addShapedRecipe(true, "casing_steam", TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.STEAM_CASING, 2), "PhP", "PFP", "PwP", 'P', new UnificationEntry(OrePrefix.plate, Materials.Brass), 'F', new UnificationEntry(OrePrefix.frameGt, Materials.Brass));

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, TKCYAMaterials.Zeron100, 6)
                .input(OrePrefix.frameGt, Materials.Titanium)
                .notConsumable(new IntCircuitIngredient(6))
                .outputs(TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.MACERATOR_CASING, 2))
                .duration(50).EUt(16).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, TKCYAMaterials.HSLASteel, 4)
                .input(OrePrefix.plate, TKCYAMaterials.TitaniumCarbide, 2)
                .input(OrePrefix.frameGt, Materials.TungstenCarbide)
                .notConsumable(new IntCircuitIngredient(6))
                .outputs(TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.HIGH_TEMPERATURE_CASING, 2))
                .duration(50).EUt(16).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, TKCYAMaterials.Stellite100, 6)
                .input(OrePrefix.frameGt, Materials.Tungsten)
                .notConsumable(new IntCircuitIngredient(6))
                .outputs(TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.ASSEMBLING_CASING, 2))
                .duration(50).EUt(16).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, TKCYAMaterials.MaragingSteel300, 6)
                .input(OrePrefix.frameGt, Materials.StainlessSteel)
                .notConsumable(new IntCircuitIngredient(6))
                .outputs(TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.STRESS_PROOF_CASING, 2))
                .duration(50).EUt(16).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, Materials.CobaltBrass, 6)
                .input(OrePrefix.frameGt, TKCYAMaterials.HSLASteel)
                .notConsumable(new IntCircuitIngredient(6))
                .outputs(TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.CORROSION_PROOF_CASING, 2))
                .duration(50).EUt(16).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, TKCYAMaterials.IncoloyMA956, 6)
                .input(OrePrefix.frameGt, TKCYAMaterials.IncoloyMA956)
                .notConsumable(new IntCircuitIngredient(6))
                .outputs(TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.VIBRATION_SAFE_CASING, 2))
                .duration(50).EUt(16).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, TKCYAMaterials.WatertightSteel, 6)
                .input(OrePrefix.frameGt, TKCYAMaterials.WatertightSteel)
                .notConsumable(new IntCircuitIngredient(6))
                .outputs(TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.WATERTIGHT_CASING, 2))
                .duration(50).EUt(16).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, TKCYAMaterials.HastelloyC276, 6)
                .input(OrePrefix.frameGt, TKCYAMaterials.HastelloyC276)
                .notConsumable(new IntCircuitIngredient(6))
                .outputs(TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.CUTTER_CASING, 2))
                .duration(50).EUt(16).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, TKCYAMaterials.HSLASteel, 6)
                .input(OrePrefix.frameGt, TKCYAMaterials.HSLASteel)
                .notConsumable(new IntCircuitIngredient(6))
                .outputs(TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.NONCONDUCTING_CASING, 2))
                .duration(50).EUt(16).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, TKCYAMaterials.HastelloyX, 6)
                .input(OrePrefix.frameGt, TKCYAMaterials.MaragingSteel300)
                .notConsumable(new IntCircuitIngredient(6))
                .outputs(TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.MIXER_CASING, 2))
                .duration(50).EUt(16).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, TKCYAMaterials.TitaniumTungstenCarbide, 6)
                .input(OrePrefix.frameGt, Materials.Titanium)
                .notConsumable(new IntCircuitIngredient(6))
                .outputs(TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.ENGRAVER_CASING, 2))
                .duration(50).EUt(16).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plateDouble, TKCYAMaterials.Trinaquadalloy, 6)
                .input(OrePrefix.frameGt, Materials.NaquadahAlloy)
                .notConsumable(new IntCircuitIngredient(6))
                .outputs(TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.ATOMIC_CASING, 2))
                .duration(50).EUt(16).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, Materials.Brass, 6)
                .input(OrePrefix.frameGt, Materials.Brass)
                .notConsumable(new IntCircuitIngredient(6))
                .outputs(TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.STEAM_CASING, 2))
                .duration(50).EUt(16).buildAndRegister();

        // Unique Casings
        ModHandler.addShapedRecipe(true, "casing_crushing_wheels", TKCYAMetaBlocks.UNIQUE_CASING.getItemVariant(BlockUniqueCasing.UniqueCasingType.CRUSHING_WHEELS, 2), "SSS", "GCG", "GMG", 'S', new UnificationEntry(OrePrefix.gearSmall, Materials.TungstenCarbide), 'G', new UnificationEntry(OrePrefix.gear, Materials.Ultimet), 'C', TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.MACERATOR_CASING), 'M', MetaItems.ELECTRIC_MOTOR_IV.getStackForm());
        ModHandler.addShapedRecipe(true, "casing_slicing_blades", TKCYAMetaBlocks.UNIQUE_CASING.getItemVariant(BlockUniqueCasing.UniqueCasingType.SLICING_BLADES, 2), "SSS", "GCG", "GMG", 'S', new UnificationEntry(OrePrefix.plate, Materials.TungstenCarbide), 'G', new UnificationEntry(OrePrefix.gear, Materials.Ultimet), 'C', TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.CUTTER_CASING), 'M', MetaItems.ELECTRIC_MOTOR_IV.getStackForm());
        ModHandler.addShapedRecipe(true, "casing_electrolytic_cell", TKCYAMetaBlocks.UNIQUE_CASING.getItemVariant(BlockUniqueCasing.UniqueCasingType.ELECTROLYTIC_CELL, 2), "WWW", "WCW", "KAK", 'W', new UnificationEntry(OrePrefix.wireGtDouble, Materials.Platinum), 'C', TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.NONCONDUCTING_CASING), 'K', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.IV), 'A', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Tungsten));
        ModHandler.addShapedRecipe(true, "casing_heat_vent", TKCYAMetaBlocks.UNIQUE_CASING.getItemVariant(BlockUniqueCasing.UniqueCasingType.HEAT_VENT, 2), "PDP", "RLR", "PDP", 'P', new UnificationEntry(OrePrefix.plate, TKCYAMaterials.TantalumCarbide), 'D', new UnificationEntry(OrePrefix.plateDouble, TKCYAMaterials.MolybdenumDisilicide), 'R', new UnificationEntry(OrePrefix.rotor, Materials.Titanium), 'L', new UnificationEntry(OrePrefix.stickLong, TKCYAMaterials.MolybdenumDisilicide));

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.gearSmall, Materials.TungstenCarbide, 3)
                .input(OrePrefix.gear, Materials.Ultimet, 4)
                .inputs(MetaItems.ELECTRIC_MOTOR_IV.getStackForm())
                .inputs(TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.MACERATOR_CASING))
                .outputs(TKCYAMetaBlocks.UNIQUE_CASING.getItemVariant(BlockUniqueCasing.UniqueCasingType.CRUSHING_WHEELS, 2))
                .duration(50).EUt(16).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, Materials.TungstenCarbide, 3)
                .input(OrePrefix.gear, Materials.Ultimet, 4)
                .inputs(MetaItems.ELECTRIC_MOTOR_IV.getStackForm())
                .inputs(TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.CUTTER_CASING))
                .outputs(TKCYAMetaBlocks.UNIQUE_CASING.getItemVariant(BlockUniqueCasing.UniqueCasingType.SLICING_BLADES, 2))
                .duration(50).EUt(16).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.wireGtDouble, Materials.Platinum, 5)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.IV, 2)
                .input(OrePrefix.cableGtSingle, Materials.Tungsten)
                .inputs(TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.NONCONDUCTING_CASING))
                .outputs(TKCYAMetaBlocks.UNIQUE_CASING.getItemVariant(BlockUniqueCasing.UniqueCasingType.ELECTROLYTIC_CELL, 2))
                .duration(50).EUt(16).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, TKCYAMaterials.TantalumCarbide, 4)
                .input(OrePrefix.rotor, Materials.Titanium, 2)
                .input(OrePrefix.plateDouble, TKCYAMaterials.MolybdenumDisilicide, 2)
                .input(OrePrefix.stickLong, TKCYAMaterials.MolybdenumDisilicide)
                .outputs(TKCYAMetaBlocks.UNIQUE_CASING.getItemVariant(BlockUniqueCasing.UniqueCasingType.HEAT_VENT, 2))
                .duration(50).EUt(16).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.ring, TKCYAMaterials.MolybdenumDisilicide, 32)
                .input(OrePrefix.foil, Materials.Graphene, 16)
                .fluidInputs(TKCYAMaterials.HSLASteel.getFluid(GTValues.L))
                .outputs(TKCYAMetaBlocks.UNIQUE_CASING.getItemVariant(BlockUniqueCasing.UniqueCasingType.MOLYBDENUM_DISILICIDE_COIL))
                .duration(500).EUt(GTValues.VA[GTValues.EV]).buildAndRegister();
    }
}
