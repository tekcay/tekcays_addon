package tekcays_addon.loaders.recipe.removals;

import gregtech.api.items.metaitem.MetaItem;
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



    public static void removeMoldsAndUsage() {

        for (MetaItem.MetaValueItem mvi : MetaItems.SHAPE_MOLDS) {
            if (mvi.isItemEqual(MetaItems.SHAPE_EMPTY.getStackForm())) continue;
            ModHandler.removeRecipes(mvi.getStackForm());
        }

        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES,
                new ItemStack[] {
                        OreDictUnifier.get(OrePrefix.dust, Wood),
                        MetaItems.SHAPE_MOLD_PLATE.getStackForm()},
                new FluidStack[] {Glue.getFluid(50)});

        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.FORMING_PRESS_RECIPES,
                new ItemStack[] {
                        OreDictUnifier.get(OrePrefix.dust, Glass),
                        MetaItems.SHAPE_MOLD_BALL.getStackForm()},
                new FluidStack[] {});

        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.FORMING_PRESS_RECIPES,
                new ItemStack[] {
                        OreDictUnifier.get(OrePrefix.dust, Glass),
                        MetaItems.SHAPE_MOLD_BLOCK.getStackForm()},
                new FluidStack[] {});


    }




}
