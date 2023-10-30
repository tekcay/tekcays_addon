package tekcays_addon.loaders.recipe.removals;

import gregtech.api.recipes.ModHandler;
import gregtech.common.items.MetaItems;

public class ShapedComponentsRemoval {

    public static void init() {
        ModHandler.removeRecipeByOutput(MetaItems.ELECTRIC_MOTOR_MV.getStackForm());
        ModHandler.removeRecipeByOutput(MetaItems.ELECTRIC_PISTON_MV.getStackForm());
        ModHandler.removeRecipeByOutput(MetaItems.ELECTRIC_PUMP_MV.getStackForm());
        ModHandler.removeRecipeByOutput(MetaItems.ROBOT_ARM_MV.getStackForm());
        ModHandler.removeRecipeByOutput(MetaItems.FLUID_REGULATOR_MV.getStackForm());
        ModHandler.removeRecipeByOutput(MetaItems.CONVEYOR_MODULE_MV.getStackForm());

        ModHandler.removeRecipeByOutput(MetaItems.ELECTRIC_MOTOR_HV.getStackForm());
        ModHandler.removeRecipeByOutput(MetaItems.ELECTRIC_PISTON_HV.getStackForm());
        ModHandler.removeRecipeByOutput(MetaItems.ELECTRIC_PUMP_HV.getStackForm());
        ModHandler.removeRecipeByOutput(MetaItems.ROBOT_ARM_HV.getStackForm());
        ModHandler.removeRecipeByOutput(MetaItems.FLUID_REGULATOR_HV.getStackForm());
        ModHandler.removeRecipeByOutput(MetaItems.CONVEYOR_MODULE_HV.getStackForm());

        ModHandler.removeRecipeByOutput(MetaItems.ELECTRIC_MOTOR_EV.getStackForm());
        ModHandler.removeRecipeByOutput(MetaItems.ELECTRIC_PISTON_EV.getStackForm());
        ModHandler.removeRecipeByOutput(MetaItems.ELECTRIC_PUMP_EV.getStackForm());
        ModHandler.removeRecipeByOutput(MetaItems.ROBOT_ARM_EV.getStackForm());
        ModHandler.removeRecipeByOutput(MetaItems.FLUID_REGULATOR_EV.getStackForm());
        ModHandler.removeRecipeByOutput(MetaItems.CONVEYOR_MODULE_EV.getStackForm());

        ModHandler.removeRecipeByOutput(MetaItems.ELECTRIC_MOTOR_IV.getStackForm());
        ModHandler.removeRecipeByOutput(MetaItems.ELECTRIC_PISTON_IV.getStackForm());
        ModHandler.removeRecipeByOutput(MetaItems.ELECTRIC_PUMP_IV.getStackForm());
        ModHandler.removeRecipeByOutput(MetaItems.ROBOT_ARM_IV.getStackForm());
        ModHandler.removeRecipeByOutput(MetaItems.FLUID_REGULATOR_IV.getStackForm());
        ModHandler.removeRecipeByOutput(MetaItems.CONVEYOR_MODULE_IV.getStackForm());
    }
}
