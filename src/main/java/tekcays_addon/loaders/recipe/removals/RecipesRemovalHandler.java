package tekcays_addon.loaders.recipe.removals;

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
import gregtech.common.metatileentities.MetaTileEntities;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import static gregtech.api.unification.material.Materials.*;

public class RecipesRemovalHandler {

    public static void steelRemovalsInit() {
        // Assembler recipes

        // LV Machine Casing
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES,
            new ItemStack[] {
                    OreDictUnifier.get(OrePrefix.plate, Steel, 8),
                    IntCircuitIngredient.getIntegratedCircuit(8)},
            new FluidStack[] {});

        //LV Motor
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES,
                new ItemStack[] {
                        OreDictUnifier.get(OrePrefix.stick, Steel, 2),
                        OreDictUnifier.get(OrePrefix.stick, Materials.SteelMagnetic),
                        OreDictUnifier.get(OrePrefix.wireGtSingle, Materials.Copper, 4),
                        OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Tin, 2)},
                new FluidStack[] {});

        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES,
                new ItemStack[] {
                        OreDictUnifier.get(OrePrefix.stick, Iron, 2),
                        OreDictUnifier.get(OrePrefix.stick, Materials.IronMagnetic),
                        OreDictUnifier.get(OrePrefix.wireGtSingle, Materials.Copper, 4),
                        OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Tin, 2)},
                new FluidStack[] {});

        //LV Piston
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES,
                new ItemStack[] {
                        OreDictUnifier.get(OrePrefix.stick, Steel, 2),
                        OreDictUnifier.get(OrePrefix.plate, Steel, 3),
                        OreDictUnifier.get(OrePrefix.gearSmall, Steel),
                        OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Tin, 2),
                        MetaItems.ELECTRIC_MOTOR_LV.getStackForm()},
                new FluidStack[] {});
                
        //LV Robot Arm
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES,
                new ItemStack[] {
                        OreDictUnifier.get(OrePrefix.stick, Steel, 2),
                        OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.LV),
                        MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        MetaItems.ELECTRIC_PISTON_LV.getStackForm(),
                        OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Tin, 3)},
                new FluidStack[] {});



        //Shaped recipes

        //LV Machine Casing
        ModHandler.removeRecipes(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.LV));

        //ULV Machine Casing
        ModHandler.removeRecipes(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.ULV));

        //ULV Machine Hull
        ModHandler.removeRecipes(MetaTileEntities.HULL[0].getStackForm());


        //LV Motor
        ModHandler.removeRecipes(MetaItems.ELECTRIC_MOTOR_LV.getStackForm());
        //LV Piston
        ModHandler.removeRecipes(MetaItems.ELECTRIC_PISTON_LV.getStackForm());
        //LV Robot Arm
        ModHandler.removeRecipes(MetaItems.ROBOT_ARM_LV.getStackForm());
    }
    
    public static void shapedComponentsRecipes() {
        ModHandler.removeRecipes(MetaItems.ELECTRIC_MOTOR_MV.getStackForm());
        ModHandler.removeRecipes(MetaItems.ELECTRIC_PISTON_MV.getStackForm());
        ModHandler.removeRecipes(MetaItems.ELECTRIC_PUMP_MV.getStackForm());
        ModHandler.removeRecipes(MetaItems.ROBOT_ARM_MV.getStackForm());
        ModHandler.removeRecipes(MetaItems.FLUID_REGULATOR_MV.getStackForm());
        ModHandler.removeRecipes(MetaItems.CONVEYOR_MODULE_MV.getStackForm());

        ModHandler.removeRecipes(MetaItems.ELECTRIC_MOTOR_HV.getStackForm());
        ModHandler.removeRecipes(MetaItems.ELECTRIC_PISTON_HV.getStackForm());
        ModHandler.removeRecipes(MetaItems.ELECTRIC_PUMP_HV.getStackForm());
        ModHandler.removeRecipes(MetaItems.ROBOT_ARM_HV.getStackForm());
        ModHandler.removeRecipes(MetaItems.FLUID_REGULATOR_HV.getStackForm());
        ModHandler.removeRecipes(MetaItems.CONVEYOR_MODULE_HV.getStackForm());

        ModHandler.removeRecipes(MetaItems.ELECTRIC_MOTOR_EV.getStackForm());
        ModHandler.removeRecipes(MetaItems.ELECTRIC_PISTON_EV.getStackForm());
        ModHandler.removeRecipes(MetaItems.ELECTRIC_PUMP_EV.getStackForm());
        ModHandler.removeRecipes(MetaItems.ROBOT_ARM_EV.getStackForm());
        ModHandler.removeRecipes(MetaItems.FLUID_REGULATOR_EV.getStackForm());
        ModHandler.removeRecipes(MetaItems.CONVEYOR_MODULE_EV.getStackForm());

        ModHandler.removeRecipes(MetaItems.ELECTRIC_MOTOR_IV.getStackForm());
        ModHandler.removeRecipes(MetaItems.ELECTRIC_PISTON_IV.getStackForm());
        ModHandler.removeRecipes(MetaItems.ELECTRIC_PUMP_IV.getStackForm());
        ModHandler.removeRecipes(MetaItems.ROBOT_ARM_IV.getStackForm());
        ModHandler.removeRecipes(MetaItems.FLUID_REGULATOR_IV.getStackForm());
        ModHandler.removeRecipes(MetaItems.CONVEYOR_MODULE_IV.getStackForm());
        
    }

    public static void removeShapedTreatedWoodRecipe(){

        ModHandler.removeRecipes(OreDictUnifier.get(OrePrefix.plank, TreatedWood));
    }


}
