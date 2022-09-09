package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import tekcays_addon.api.unification.TKCYAMaterials;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockLargeMultiblockCasing;
import tekcays_addon.common.metatileentities.TKCYAMetaTileEntities;

import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.StainlessSteel;
import static gregtech.api.unification.ore.OrePrefix.plate;
import static gregtech.api.unification.ore.OrePrefix.stickLong;
import static gregtech.common.blocks.BlockSteamCasing.SteamCasingType.WOOD_WALL;
import static tekcays_addon.api.unification.TKCYAMaterials.*;
import static tekcays_addon.api.unification.TKCYAMaterials.GalvanizedSteel;
import static tekcays_addon.api.unification.TKCYAMaterials.HighDensityPolyethylene;

public class StorageOverhaul {

    public static void shapedRecipesDrums() {

        ModHandler.addShapedRecipe(true, "drum_wood", TKCYAMetaTileEntities.WOODEN_DRUM.getStackForm(),
                "rSs", "PRP", "PRP",
                'S', MetaItems.STICKY_RESIN.getStackForm(),
                'P', "plankWood", 'R', new UnificationEntry(stickLong, Materials.Iron));

        ModHandler.addShapedRecipe(true, "drum_bronze", TKCYAMetaTileEntities.BRONZE_DRUM.getStackForm(),
                " w ", "PRP", "PRP",
                'P', new UnificationEntry(plate, Materials.Bronze),
                'R', new UnificationEntry(OrePrefix.stickLong, Materials.Bronze));

    }

    public static void shapesRecipesTanksAndValves() {

        ModHandler.addShapedRecipe(true, "wood_tank", TKCYAMetaTileEntities.WOODEN_TANK.getStackForm(),
                " R ", "rCs", " R ",
                'R', new UnificationEntry(OrePrefix.ring, Materials.Lead),
                'C', MetaBlocks.STEAM_CASING.getItemVariant(WOOD_WALL));

        ModHandler.addShapedRecipe(true, "steel_tank", TKCYAMetaTileEntities.STEEL_TANK.getStackForm(),
                " R ", "hCw", " R ",
                'R', new UnificationEntry(OrePrefix.ring, Materials.Steel),
                'C', TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.STEEL_WALL));

        ModHandler.addShapedRecipe(true, "stainless_steel_tank", TKCYAMetaTileEntities.STAINLESS_STEEL_TANK.getStackForm(),
                " R ", "hCw", " R ",
                'R', new UnificationEntry(OrePrefix.ring, Materials.StainlessSteel),
                'C', TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.STAINLESS_STEEL_WALL));

        ModHandler.addShapedRecipe(true, "galvanized_steel_tank", TKCYAMetaTileEntities.GALVANIZED_STEEL_TANK.getStackForm(),
                " R ", "hCw", " R ",
                'R', new UnificationEntry(OrePrefix.ring, TKCYAMaterials.GalvanizedSteel),
                'C', TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.GALVANIZED_STEEL_WALL));



        ModHandler.addShapedRecipe(true, "tank_valve.steel", TKCYAMetaTileEntities.STEEL_TANK_VALVE.getStackForm(),
                " R ", "hCw", " O ",
                'O', new UnificationEntry(OrePrefix.rotor, Materials.Steel),
                'R', new UnificationEntry(OrePrefix.ring, Materials.Steel), 'C', TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.STEEL_WALL));

        ModHandler.addShapedRecipe(true, "tank_valve.stainless_steel",
                TKCYAMetaTileEntities.STAINLESS_STEEL_TANK_VALVE.getStackForm(),
                " R ", "hCw", " O ",
                'O', new UnificationEntry(OrePrefix.rotor, Materials.StainlessSteel),
                'R', new UnificationEntry(OrePrefix.ring, Materials.StainlessSteel),
                'C', TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.STAINLESS_STEEL_WALL));

        ModHandler.addShapedRecipe(true, "tank_valve.galvanized_steel",
                TKCYAMetaTileEntities.GALVANIZED_STEEL_TANK_VALVE.getStackForm(),
                " R ", "hCw", " O ",
                'O', new UnificationEntry(OrePrefix.rotor, TKCYAMaterials.GalvanizedSteel),
                'R', new UnificationEntry(OrePrefix.ring, TKCYAMaterials.GalvanizedSteel),
                'C', TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.GALVANIZED_STEEL_WALL));


        ModHandler.addShapedRecipe(true, "wall.steel",
                TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.STEEL_WALL),
                "hdw", "PP ", "PP ",
                'P', new UnificationEntry(plate, Materials.Steel));

        ModHandler.addShapedRecipe(true, "wall.stainless_steel",
                TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.STAINLESS_STEEL_WALL),
                "hdw", "PP ", "PP ",
                'P', new UnificationEntry(plate, Materials.StainlessSteel));

        ModHandler.addShapedRecipe(true, "wall.galvanized_steel",
                TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.GALVANIZED_STEEL_WALL),
                "hdw", "PP ", "PP ",
                'P', new UnificationEntry(plate, TKCYAMaterials.GalvanizedSteel));

    }

    public static void drumsAssembler() {

        ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(stickLong, Bronze, 2).input(plate, Bronze, 4).outputs(TKCYAMetaTileEntities.WOODEN_DRUM.getStackForm()).duration(200).circuitMeta(2).buildAndRegister();
        ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(stickLong, Steel, 2).input(plate, Steel, 4).outputs(TKCYAMetaTileEntities.STEEL_DRUM.getStackForm()).duration(200).circuitMeta(2).buildAndRegister();
        ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(stickLong, Aluminium, 2).input(plate, Aluminium, 4).outputs(TKCYAMetaTileEntities.ALUMINIUM_DRUM.getStackForm()).duration(200).circuitMeta(2).buildAndRegister();
        ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(stickLong, StainlessSteel, 2).input(plate, StainlessSteel, 4).outputs(TKCYAMetaTileEntities.STAINLESS_STEEL_DRUM.getStackForm()).duration(200).circuitMeta(2).buildAndRegister();
        ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(stickLong, GalvanizedSteel, 2).input(plate, GalvanizedSteel, 4).outputs(TKCYAMetaTileEntities.GALVANIZED_STEEL_DRUM.getStackForm()).duration(200).circuitMeta(2).buildAndRegister();
        ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(stickLong, Polytetrafluoroethylene, 2).input(plate, Polytetrafluoroethylene, 4).outputs(TKCYAMetaTileEntities.POLYTETRAFLUOROETHYLENE_DRUM.getStackForm()).duration(200).circuitMeta(2).buildAndRegister();
        ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(stickLong, Polypropylene, 2).input(plate, Polypropylene, 4).outputs(TKCYAMetaTileEntities.POLYPROPYLENE_DRUM.getStackForm()).duration(200).circuitMeta(2).buildAndRegister();
        ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(stickLong, HighDensityPolyethylene, 2).input(plate, HighDensityPolyethylene, 4).outputs(TKCYAMetaTileEntities.HIGH_DENSITY_POLYETHYLENE_DRUM.getStackForm()).duration(200).circuitMeta(2).buildAndRegister();

    }

    public static void wallsAssembler() {
        ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(plate, Steel, 4).outputs(TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.STEEL_WALL)).duration(200).circuitMeta(8).buildAndRegister();
        ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(plate, StainlessSteel, 4).outputs(TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.STAINLESS_STEEL_WALL)).duration(200).circuitMeta(8).buildAndRegister();
        ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(plate, GalvanizedSteel, 4).outputs(TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.GALVANIZED_STEEL_WALL)).duration(200).circuitMeta(8).buildAndRegister();

    }

    public static void removeGTCEuDrumsRecipe(){
        ModHandler.removeRecipes(MetaTileEntities.WOODEN_DRUM.getStackForm());
        ModHandler.removeRecipes(MetaTileEntities.BRONZE_DRUM.getStackForm());
    }

    public static void removeGTCEuTanksAndValvesRecipe() {
        ModHandler.removeRecipes(MetaTileEntities.WOODEN_TANK.getStackForm());
        ModHandler.removeRecipes(MetaTileEntities.STEEL_TANK.getStackForm());

        ModHandler.removeRecipes(MetaTileEntities.STEEL_TANK_VALVE.getStackForm());
    }

}
