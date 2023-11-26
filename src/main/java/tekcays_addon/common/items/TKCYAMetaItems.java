package tekcays_addon.common.items;

import gregtech.api.items.metaitem.MetaItem;

import java.util.ArrayList;
import java.util.List;


public final class TKCYAMetaItems {

    private TKCYAMetaItems(){
        super();
    }

    public static MetaItem<?>.MetaValueItem MICA_SHEET;
    public static MetaItem<?>.MetaValueItem MICA_INSULATOR_SHEET;
    public static MetaItem<?>.MetaValueItem MICA_INSULATOR_FOIL;
    public static MetaItem<?>.MetaValueItem GAS_COLLECTOR;

    //Damageable items
    public static MetaItem<?>.MetaValueItem ELECTRODE;
    public static MetaItem<?>.MetaValueItem FILTER;

    //Mixtures
    public static MetaItem<?>.MetaValueItem DUST_MIXTURE;

    //Covers
    ////Detectors
    public static MetaItem<?>.MetaValueItem COVER_TEMPERATURE_DETECTOR;
    public static MetaItem<?>.MetaValueItem COVER_SPEED_DETECTOR;
    public static MetaItem<?>.MetaValueItem COVER_TORQUE_DETECTOR;
    public static MetaItem<?>.MetaValueItem COVER_ROTATION_POWER_DETECTOR;
    public static MetaItem<?>.MetaValueItem COVER_PRESSURE_DETECTOR;
    public static MetaItem<?>.MetaValueItem COVER_VACUUM_DETECTOR;

    ////Molds
    public static List<MetaItem<?>.MetaValueItem> MOLDS = new ArrayList<>();

    public static MetaItem<?>.MetaValueItem EMPTY_MOLD_CERAMIC;
    public static MetaItem<?>.MetaValueItem EMPTY_MOLD_STEEL;
    public static MetaItem<?>.MetaValueItem EMPTY_MOLD_CARBON;
    public static MetaItem<?>.MetaValueItem EMPTY_MOLD_TUNGSTEN_CARBIDE;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_INGOT_CERAMIC;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_PLATE_CERAMIC;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_STICK_CERAMIC;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_STICK_LONG_CERAMIC;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_GEAR_CERAMIC;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_GEAR_SMALL_CERAMIC;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_BOLT_CERAMIC;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_RING_CERAMIC;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_BLOCK_CERAMIC;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_INGOT_STEEL;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_PLATE_STEEL;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_STICK_STEEL;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_STICK_LONG_STEEL;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_GEAR_STEEL;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_GEAR_SMALL_STEEL;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_BOLT_STEEL;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_RING_STEEL;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_BLOCK_STEEL;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_INGOT_TUNGSTEN_CARBIDE;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_PLATE_TUNGSTEN_CARBIDE;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_STICK_TUNGSTEN_CARBIDE;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_STICK_LONG_TUNGSTEN_CARBIDE;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_GEAR_TUNGSTEN_CARBIDE;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_GEAR_SMALL_TUNGSTEN_CARBIDE;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_BOLT_TUNGSTEN_CARBIDE;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_RING_TUNGSTEN_CARBIDE;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_BLOCK_TUNGSTEN_CARBIDE;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_INGOT_CARBON;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_PLATE_CARBON;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_STICK_CARBON;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_STICK_LONG_CARBON;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_GEAR_CARBON;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_GEAR_SMALL_CARBON;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_BOLT_CARBON;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_RING_CARBON;
    public static MetaItem<?>.MetaValueItem COVER_MOLD_BLOCK_CARBON;

    //ULV Components
    public static MetaItem<?>.MetaValueItem ELECTRIC_MOTOR_ULV;
    public static MetaItem<?>.MetaValueItem ELECTRIC_PUMP_ULV;
    public static MetaItem<?>.MetaValueItem PISTON_ULV;
    public static MetaItem<?>.MetaValueItem CONVEYOR_ULV;
    public static MetaItem<?>.MetaValueItem ROBOT_ARM_ULV;

    //
    public static MetaItem<?>.MetaValueItem CONVEYOR_MODULE_LV;
    public static MetaItem<?>.MetaValueItem CONVEYOR_MODULE_MV;
    public static MetaItem<?>.MetaValueItem CONVEYOR_MODULE_HV;
    public static MetaItem<?>.MetaValueItem CONVEYOR_MODULE_EV;
    public static MetaItem<?>.MetaValueItem CONVEYOR_MODULE_IV;
    public static MetaItem<?>.MetaValueItem CONVEYOR_MODULE_LuV;
    public static MetaItem<?>.MetaValueItem CONVEYOR_MODULE_ZPM;
    public static MetaItem<?>.MetaValueItem CONVEYOR_MODULE_UV;
    public static MetaItem<?>.MetaValueItem CONVEYOR_MODULE_UHV;
    public static MetaItem<?>.MetaValueItem CONVEYOR_MODULE_UEV;
    public static MetaItem<?>.MetaValueItem CONVEYOR_MODULE_UIV;
    public static MetaItem<?>.MetaValueItem CONVEYOR_MODULE_UXV;
    public static MetaItem<?>.MetaValueItem CONVEYOR_MODULE_OpV;

    public static MetaItem<?>.MetaValueItem ROBOT_ARM_LV;
    public static MetaItem<?>.MetaValueItem ROBOT_ARM_MV;
    public static MetaItem<?>.MetaValueItem ROBOT_ARM_HV;
    public static MetaItem<?>.MetaValueItem ROBOT_ARM_EV;
    public static MetaItem<?>.MetaValueItem ROBOT_ARM_IV;
    public static MetaItem<?>.MetaValueItem ROBOT_ARM_LuV;
    public static MetaItem<?>.MetaValueItem ROBOT_ARM_ZPM;
    public static MetaItem<?>.MetaValueItem ROBOT_ARM_UV;
    public static MetaItem<?>.MetaValueItem ROBOT_ARM_UHV;
    public static MetaItem<?>.MetaValueItem ROBOT_ARM_UEV;
    public static MetaItem<?>.MetaValueItem ROBOT_ARM_UIV;
    public static MetaItem<?>.MetaValueItem ROBOT_ARM_UXV;
    public static MetaItem<?>.MetaValueItem ROBOT_ARM_OpV;

    public static MetaItem<?>.MetaValueItem ELECTRIC_PUMP_LV;
    public static MetaItem<?>.MetaValueItem ELECTRIC_PUMP_MV;
    public static MetaItem<?>.MetaValueItem ELECTRIC_PUMP_HV;
    public static MetaItem<?>.MetaValueItem ELECTRIC_PUMP_EV;
    public static MetaItem<?>.MetaValueItem ELECTRIC_PUMP_IV;
    public static MetaItem<?>.MetaValueItem ELECTRIC_PUMP_LuV;
    public static MetaItem<?>.MetaValueItem ELECTRIC_PUMP_ZPM;
    public static MetaItem<?>.MetaValueItem ELECTRIC_PUMP_UV;
    public static MetaItem<?>.MetaValueItem ELECTRIC_PUMP_UHV;
    public static MetaItem<?>.MetaValueItem ELECTRIC_PUMP_UEV;
    public static MetaItem<?>.MetaValueItem ELECTRIC_PUMP_UIV;
    public static MetaItem<?>.MetaValueItem ELECTRIC_PUMP_UXV;
    public static MetaItem<?>.MetaValueItem ELECTRIC_PUMP_OpV;

    public static MetaItem<?>.MetaValueItem FLUID_REGULATOR_LV;
    public static MetaItem<?>.MetaValueItem FLUID_REGULATOR_MV;
    public static MetaItem<?>.MetaValueItem FLUID_REGULATOR_HV;
    public static MetaItem<?>.MetaValueItem FLUID_REGULATOR_EV;
    public static MetaItem<?>.MetaValueItem FLUID_REGULATOR_IV;
    public static MetaItem<?>.MetaValueItem FLUID_REGULATOR_LUV;
    public static MetaItem<?>.MetaValueItem FLUID_REGULATOR_ZPM;
    public static MetaItem<?>.MetaValueItem FLUID_REGULATOR_UV;


    public static void init() {
        TKCYAMetaItem1 metaItem1 = new TKCYAMetaItem1((short) 0);
        metaItem1.setRegistryName("tkcya");

    }


}
