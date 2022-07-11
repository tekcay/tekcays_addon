package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import tekcays_addon.common.metatileentities.TKCYAMetaTileEntities;

import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.StainlessSteel;
import static gregtech.api.unification.ore.OrePrefix.plate;
import static gregtech.api.unification.ore.OrePrefix.stickLong;
import static gregtech.common.metatileentities.MetaTileEntities.*;
import static gregtech.common.metatileentities.MetaTileEntities.STAINLESS_STEEL_DRUM;
import static tekcays_addon.api.unification.TKCYAMaterials.*;

public class AssemblerRecipeHandler {

    public static void galvanizedSteel() {

        //LV Machine Casing
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, GalvanizedSteel, 8)
                .circuitMeta(8)
                .outputs(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.LV))
                .duration(50)
                .EUt(16)
                .buildAndRegister();

        // LV Motor
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.stick, GalvanizedSteel, 2)
                .input(OrePrefix.stick, Materials.SteelMagnetic)
                .input(OrePrefix.cableGtSingle, Materials.Tin, 2)
                .input(OrePrefix.wireGtSingle, Materials.Copper, 4)
                .output(MetaItems.ELECTRIC_MOTOR_LV)
                .duration(100)
                .EUt(30)
                .buildAndRegister();

        //LV Piston
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, GalvanizedSteel, 3)
                .input(OrePrefix.stick, GalvanizedSteel, 2)
                .input(OrePrefix.cableGtSingle, Materials.Tin, 2)
                .input(OrePrefix.gearSmall, GalvanizedSteel)
                .input(MetaItems.ELECTRIC_MOTOR_LV)
                .output(MetaItems.ELECTRIC_PISTON_LV)
                .duration(100)
                .EUt(30)
                .buildAndRegister();

        //LV Robot Arm
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.stick, GalvanizedSteel, 2)
                .input(OrePrefix.cableGtSingle, Materials.Tin, 3)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.LV)
                .input(MetaItems.ELECTRIC_PISTON_LV)
                .output(MetaItems.ROBOT_ARM_LV)
                .duration(100)
                .EUt(30)
                .buildAndRegister();

        /*
        //LV Power Unit //TO DO
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.stick, GalvanizedSteel, 2)
                .input(OrePrefix.cableGtSingle, Materials.Tin, 3)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.LV)
                .input(MetaItems.ELECTRIC_PISTON_LV)
                .output(MetaItems.POWER_UNI)
                .duration(100) //TODO
                .EUt(30)
                .buildAndRegister();

         */
    }

    public static void drums() {

        ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(stickLong, Bronze, 2).input(plate, Bronze, 4).outputs(TKCYAMetaTileEntities.WOODEN_DRUM.getStackForm()).duration(200).circuitMeta(2).buildAndRegister();
        ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(stickLong, Steel, 2).input(plate, Steel, 4).outputs(TKCYAMetaTileEntities.STEEL_DRUM.getStackForm()).duration(200).circuitMeta(2).buildAndRegister();
        ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(stickLong, Aluminium, 2).input(plate, Aluminium, 4).outputs(TKCYAMetaTileEntities.ALUMINIUM_DRUM.getStackForm()).duration(200).circuitMeta(2).buildAndRegister();
        ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(stickLong, StainlessSteel, 2).input(plate, StainlessSteel, 4).outputs(TKCYAMetaTileEntities.STAINLESS_STEEL_DRUM.getStackForm()).duration(200).circuitMeta(2).buildAndRegister();
        ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(stickLong, GalvanizedSteel, 2).input(plate, GalvanizedSteel, 4).outputs(TKCYAMetaTileEntities.GALVANIZED_STEEL_DRUM.getStackForm()).duration(200).circuitMeta(2).buildAndRegister();
        ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(stickLong, Polytetrafluoroethylene, 2).input(plate, Polytetrafluoroethylene, 4).outputs(TKCYAMetaTileEntities.POLYTETRAFLUOROETHYLENE_DRUM.getStackForm()).duration(200).circuitMeta(2).buildAndRegister();
        ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(stickLong, Polypropylene, 2).input(plate, Polypropylene, 4).outputs(TKCYAMetaTileEntities.POLYPROPYLENE_DRUM.getStackForm()).duration(200).circuitMeta(2).buildAndRegister();
        ASSEMBLER_RECIPES.recipeBuilder().EUt(16).input(stickLong, HighDensityPolyethylene, 2).input(plate, HighDensityPolyethylene, 4).outputs(TKCYAMetaTileEntities.HIGH_DENSITY_POLYETHYLENE_DRUM.getStackForm()).duration(200).circuitMeta(2).buildAndRegister();

    }

}
