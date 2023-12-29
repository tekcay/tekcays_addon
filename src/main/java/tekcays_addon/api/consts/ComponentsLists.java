package tekcays_addon.api.consts;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import gregtech.common.items.MetaItems;

public class ComponentsLists {

    public static List<ItemStack> LV_COMPONENTS_TO_REMOVE_RECIPES = new ArrayList<>();
    public static List<ItemStack> MV_COMPONENTS_TO_REMOVE_RECIPES = new ArrayList<>();
    public static List<ItemStack> HV_COMPONENTS_TO_REMOVE_RECIPES = new ArrayList<>();
    public static List<ItemStack> EV_COMPONENTS_TO_REMOVE_RECIPES = new ArrayList<>();
    public static List<ItemStack> IV_COMPONENTS_TO_REMOVE_RECIPES = new ArrayList<>();
    public static List<ItemStack> LV_TO_IV_COMPONENTS_TO_REMOVE_RECIPES = new ArrayList<>();
    public static final List<ItemStack> UV_Logistics = new ArrayList<>();
    public static final List<ItemStack> LV_TO_IV_PUMPS = new ArrayList<>();
    public static final List<ItemStack> LV_TO_IV_FLUID_REGULATORS = new ArrayList<>();
    public static final List<ItemStack> LV_TO_IV_CONVEYOR_MODULES = new ArrayList<>();
    public static final List<ItemStack> LV_TO_IV_ROBOT_ARMS = new ArrayList<>();
    public static final List<ItemStack> LV_TO_IV_LOGISTIC_COMPONENTS = new ArrayList<>();
    public static final List<ItemStack> UV_LOGISTIC_COMPONENTS = new ArrayList<>();
    public static final List<ItemStack> LUV_TO_UV_LOGISTIC_COMPONENTS = new ArrayList<>();
    public static final List<ItemStack> ALL_LOGISTIC_COMPONENTS = new ArrayList<>();
    public static final List<ItemStack> LV_TO_IV_MOTORS = new ArrayList<>();

    static {
        LV_COMPONENTS_TO_REMOVE_RECIPES.add(MetaItems.ELECTRIC_MOTOR_LV.getStackForm());
        LV_COMPONENTS_TO_REMOVE_RECIPES.add(MetaItems.ELECTRIC_PISTON_LV.getStackForm());
        LV_COMPONENTS_TO_REMOVE_RECIPES.add(MetaItems.ELECTRIC_PUMP_LV.getStackForm());
        LV_COMPONENTS_TO_REMOVE_RECIPES.add(MetaItems.ROBOT_ARM_LV.getStackForm());
        LV_COMPONENTS_TO_REMOVE_RECIPES.add(MetaItems.FLUID_REGULATOR_LV.getStackForm());
        LV_COMPONENTS_TO_REMOVE_RECIPES.add(MetaItems.CONVEYOR_MODULE_LV.getStackForm());
    }

    static {
        MV_COMPONENTS_TO_REMOVE_RECIPES.add(MetaItems.ELECTRIC_MOTOR_MV.getStackForm());
        MV_COMPONENTS_TO_REMOVE_RECIPES.add(MetaItems.ELECTRIC_PISTON_MV.getStackForm());
        MV_COMPONENTS_TO_REMOVE_RECIPES.add(MetaItems.ELECTRIC_PUMP_MV.getStackForm());
        MV_COMPONENTS_TO_REMOVE_RECIPES.add(MetaItems.ROBOT_ARM_MV.getStackForm());
        MV_COMPONENTS_TO_REMOVE_RECIPES.add(MetaItems.FLUID_REGULATOR_MV.getStackForm());
        MV_COMPONENTS_TO_REMOVE_RECIPES.add(MetaItems.CONVEYOR_MODULE_MV.getStackForm());
    }

    static {
        HV_COMPONENTS_TO_REMOVE_RECIPES.add(MetaItems.ELECTRIC_MOTOR_HV.getStackForm());
        HV_COMPONENTS_TO_REMOVE_RECIPES.add(MetaItems.ELECTRIC_PISTON_HV.getStackForm());
        HV_COMPONENTS_TO_REMOVE_RECIPES.add(MetaItems.ELECTRIC_PUMP_HV.getStackForm());
        HV_COMPONENTS_TO_REMOVE_RECIPES.add(MetaItems.ROBOT_ARM_HV.getStackForm());
        HV_COMPONENTS_TO_REMOVE_RECIPES.add(MetaItems.FLUID_REGULATOR_HV.getStackForm());
        HV_COMPONENTS_TO_REMOVE_RECIPES.add(MetaItems.CONVEYOR_MODULE_HV.getStackForm());
    }

    static {
        EV_COMPONENTS_TO_REMOVE_RECIPES.add(MetaItems.ELECTRIC_MOTOR_EV.getStackForm());
        EV_COMPONENTS_TO_REMOVE_RECIPES.add(MetaItems.ELECTRIC_PISTON_EV.getStackForm());
        EV_COMPONENTS_TO_REMOVE_RECIPES.add(MetaItems.ELECTRIC_PUMP_EV.getStackForm());
        EV_COMPONENTS_TO_REMOVE_RECIPES.add(MetaItems.ROBOT_ARM_EV.getStackForm());
        EV_COMPONENTS_TO_REMOVE_RECIPES.add(MetaItems.FLUID_REGULATOR_EV.getStackForm());
        EV_COMPONENTS_TO_REMOVE_RECIPES.add(MetaItems.CONVEYOR_MODULE_EV.getStackForm());
    }

    static {
        IV_COMPONENTS_TO_REMOVE_RECIPES.add(MetaItems.ELECTRIC_MOTOR_IV.getStackForm());
        IV_COMPONENTS_TO_REMOVE_RECIPES.add(MetaItems.ELECTRIC_PISTON_IV.getStackForm());
        IV_COMPONENTS_TO_REMOVE_RECIPES.add(MetaItems.ELECTRIC_PUMP_IV.getStackForm());
        IV_COMPONENTS_TO_REMOVE_RECIPES.add(MetaItems.ROBOT_ARM_IV.getStackForm());
        IV_COMPONENTS_TO_REMOVE_RECIPES.add(MetaItems.FLUID_REGULATOR_IV.getStackForm());
        IV_COMPONENTS_TO_REMOVE_RECIPES.add(MetaItems.CONVEYOR_MODULE_IV.getStackForm());
    }

    static {
        LV_TO_IV_COMPONENTS_TO_REMOVE_RECIPES.addAll(LV_COMPONENTS_TO_REMOVE_RECIPES);
        LV_TO_IV_COMPONENTS_TO_REMOVE_RECIPES.addAll(MV_COMPONENTS_TO_REMOVE_RECIPES);
        LV_TO_IV_COMPONENTS_TO_REMOVE_RECIPES.addAll(HV_COMPONENTS_TO_REMOVE_RECIPES);
        LV_TO_IV_COMPONENTS_TO_REMOVE_RECIPES.addAll(EV_COMPONENTS_TO_REMOVE_RECIPES);
        LV_TO_IV_COMPONENTS_TO_REMOVE_RECIPES.addAll(IV_COMPONENTS_TO_REMOVE_RECIPES);
    }

    static {
        UV_Logistics.add(MetaItems.ELECTRIC_PUMP_UV.getStackForm());
        UV_Logistics.add(MetaItems.FLUID_REGULATOR_UV.getStackForm());
        UV_Logistics.add(MetaItems.CONVEYOR_MODULE_UV.getStackForm());
        UV_Logistics.add(MetaItems.ROBOT_ARM_UV.getStackForm());
    }

    static {
        LV_TO_IV_PUMPS.add(MetaItems.ELECTRIC_PUMP_LV.getStackForm());
        LV_TO_IV_PUMPS.add(MetaItems.ELECTRIC_PUMP_MV.getStackForm());
        LV_TO_IV_PUMPS.add(MetaItems.ELECTRIC_PUMP_HV.getStackForm());
        LV_TO_IV_PUMPS.add(MetaItems.ELECTRIC_PUMP_EV.getStackForm());
        LV_TO_IV_PUMPS.add(MetaItems.ELECTRIC_PUMP_IV.getStackForm());
    }

    static {
        LV_TO_IV_CONVEYOR_MODULES.add(MetaItems.CONVEYOR_MODULE_LV.getStackForm());
        LV_TO_IV_CONVEYOR_MODULES.add(MetaItems.CONVEYOR_MODULE_MV.getStackForm());
        LV_TO_IV_CONVEYOR_MODULES.add(MetaItems.CONVEYOR_MODULE_HV.getStackForm());
        LV_TO_IV_CONVEYOR_MODULES.add(MetaItems.CONVEYOR_MODULE_EV.getStackForm());
        LV_TO_IV_CONVEYOR_MODULES.add(MetaItems.CONVEYOR_MODULE_IV.getStackForm());
    }

    static {
        LV_TO_IV_FLUID_REGULATORS.add(MetaItems.FLUID_REGULATOR_LV.getStackForm());
        LV_TO_IV_FLUID_REGULATORS.add(MetaItems.FLUID_REGULATOR_MV.getStackForm());
        LV_TO_IV_FLUID_REGULATORS.add(MetaItems.FLUID_REGULATOR_HV.getStackForm());
        LV_TO_IV_FLUID_REGULATORS.add(MetaItems.FLUID_REGULATOR_EV.getStackForm());
        LV_TO_IV_FLUID_REGULATORS.add(MetaItems.FLUID_REGULATOR_IV.getStackForm());
    }

    static {
        LV_TO_IV_ROBOT_ARMS.add(MetaItems.ROBOT_ARM_LV.getStackForm());
        LV_TO_IV_ROBOT_ARMS.add(MetaItems.ROBOT_ARM_MV.getStackForm());
        LV_TO_IV_ROBOT_ARMS.add(MetaItems.ROBOT_ARM_HV.getStackForm());
        LV_TO_IV_ROBOT_ARMS.add(MetaItems.ROBOT_ARM_EV.getStackForm());
        LV_TO_IV_ROBOT_ARMS.add(MetaItems.ROBOT_ARM_IV.getStackForm());
    }

    static {
        LV_TO_IV_LOGISTIC_COMPONENTS.addAll(LV_TO_IV_PUMPS);
        LV_TO_IV_LOGISTIC_COMPONENTS.addAll(LV_TO_IV_FLUID_REGULATORS);
        LV_TO_IV_LOGISTIC_COMPONENTS.addAll(LV_TO_IV_CONVEYOR_MODULES);
        LV_TO_IV_LOGISTIC_COMPONENTS.addAll(LV_TO_IV_ROBOT_ARMS);
    }

    static {
        UV_LOGISTIC_COMPONENTS.add(MetaItems.CONVEYOR_MODULE_UV.getStackForm());
        UV_LOGISTIC_COMPONENTS.add(MetaItems.ROBOT_ARM_UV.getStackForm());
        UV_LOGISTIC_COMPONENTS.add(MetaItems.ELECTRIC_PUMP_UV.getStackForm());
        UV_LOGISTIC_COMPONENTS.add(MetaItems.FLUID_REGULATOR_UV.getStackForm());
    }

    static {
        LUV_TO_UV_LOGISTIC_COMPONENTS.add(MetaItems.CONVEYOR_MODULE_LuV.getStackForm());
        LUV_TO_UV_LOGISTIC_COMPONENTS.add(MetaItems.ROBOT_ARM_LuV.getStackForm());
        LUV_TO_UV_LOGISTIC_COMPONENTS.add(MetaItems.ELECTRIC_PUMP_LuV.getStackForm());
        LUV_TO_UV_LOGISTIC_COMPONENTS.add(MetaItems.FLUID_REGULATOR_LUV.getStackForm());

        LUV_TO_UV_LOGISTIC_COMPONENTS.add(MetaItems.CONVEYOR_MODULE_ZPM.getStackForm());
        LUV_TO_UV_LOGISTIC_COMPONENTS.add(MetaItems.ROBOT_ARM_ZPM.getStackForm());
        LUV_TO_UV_LOGISTIC_COMPONENTS.add(MetaItems.ELECTRIC_PUMP_ZPM.getStackForm());
        LUV_TO_UV_LOGISTIC_COMPONENTS.add(MetaItems.FLUID_REGULATOR_ZPM.getStackForm());

        LUV_TO_UV_LOGISTIC_COMPONENTS.addAll(UV_LOGISTIC_COMPONENTS);
    }

    static {
        ALL_LOGISTIC_COMPONENTS.addAll(LV_TO_IV_LOGISTIC_COMPONENTS);
        ALL_LOGISTIC_COMPONENTS.addAll(LUV_TO_UV_LOGISTIC_COMPONENTS);
    }

    static {
        LV_TO_IV_MOTORS.add(MetaItems.ELECTRIC_MOTOR_LV.getStackForm());
        LV_TO_IV_MOTORS.add(MetaItems.ELECTRIC_MOTOR_MV.getStackForm());
        LV_TO_IV_MOTORS.add(MetaItems.ELECTRIC_MOTOR_HV.getStackForm());
        LV_TO_IV_MOTORS.add(MetaItems.ELECTRIC_MOTOR_EV.getStackForm());
        LV_TO_IV_MOTORS.add(MetaItems.ELECTRIC_MOTOR_IV.getStackForm());
    }
}
