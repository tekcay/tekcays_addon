package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.Material;
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
import static gregtech.api.unification.ore.OrePrefix.plate;
import static gregtech.api.unification.ore.OrePrefix.stickLong;
import static gregtech.common.blocks.BlockSteamCasing.SteamCasingType.WOOD_WALL;
import static tekcays_addon.api.unification.TKCYAMaterials.GalvanizedSteel;
import static tekcays_addon.api.unification.material.ore.TKCYAOrePrefix.curvedPlate;
import static tekcays_addon.api.utils.TKCYAValues.DRUM_MATERIALS;
import static tekcays_addon.common.metatileentities.TKCYAMetaTileEntities.DRUMS;

public class StorageOverhaul {

    public static void shapedRecipesDrums() {

        ModHandler.addShapedRecipe(true, "drum_wood", DRUMS[0].getStackForm(),
                "rSs", "PRP", "PRP",
                'S', MetaItems.STICKY_RESIN.getStackForm(),
                'P', "plankWood", 'R', new UnificationEntry(stickLong, Materials.Iron));

        ModHandler.addShapedRecipe(true, "drum_bronze", DRUMS[1].getStackForm(),
                " w ", "PRP", "PRP",
                'P', new UnificationEntry(curvedPlate, Materials.Bronze),
                'R', new UnificationEntry(OrePrefix.stickLong, Materials.Bronze));
    }

    public static void shapesRecipesTanksAndValves() {

        //Tanks
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

        //Valves
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

        //Walls
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
        //Starts at i = 1 so it skips Treated Wood
        for (int i = 1; i < DRUM_MATERIALS.size(); i++) {
            Material m = DRUM_MATERIALS.get(i);
            ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(stickLong, m, 2).input(curvedPlate, m, 4).outputs(DRUMS[i].getStackForm()).duration(200).circuitMeta(2).buildAndRegister();
        }
    }

    public static void wallsAssembler() {
        ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(plate, Steel, 4).outputs(TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.STEEL_WALL)).duration(200).circuitMeta(8).buildAndRegister();
        ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(plate, StainlessSteel, 4).outputs(TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.STAINLESS_STEEL_WALL)).duration(200).circuitMeta(8).buildAndRegister();
        ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(plate, GalvanizedSteel, 4).outputs(TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.GALVANIZED_STEEL_WALL)).duration(200).circuitMeta(8).buildAndRegister();
    }

    public static void removeGTCEuDrumsRecipe(){
        ModHandler.removeRecipeByOutput(MetaTileEntities.WOODEN_DRUM.getStackForm());
        ModHandler.removeRecipeByOutput(MetaTileEntities.BRONZE_DRUM.getStackForm());
    }

    public static void removeGTCEuTanksAndValvesRecipe() {
        ModHandler.removeRecipeByOutput(MetaTileEntities.WOODEN_TANK.getStackForm());
        ModHandler.removeRecipeByOutput(MetaTileEntities.STEEL_TANK.getStackForm());
        ModHandler.removeRecipeByOutput(MetaTileEntities.STEEL_TANK_VALVE.getStackForm());
    }

}
