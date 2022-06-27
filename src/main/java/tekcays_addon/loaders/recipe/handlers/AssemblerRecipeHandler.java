package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;

import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static tekcays_addon.api.unification.TKCYAMaterials.GalvanizedSteel;

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

        //ULV Machine Casing
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, Materials.Potin, 8)
                .circuitMeta(8)
                .outputs(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.ULV))
                .duration(25)
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

}
