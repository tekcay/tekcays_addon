package tekcays_addon.common.items;

import gregtech.api.items.metaitem.MetaItem;


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
    public static MetaItem<?>.MetaValueItem COVER_TEMPERATURE_DETECTOR;
    public static MetaItem<?>.MetaValueItem COVER_SPEED_DETECTOR;
    public static MetaItem<?>.MetaValueItem COVER_TORQUE_DETECTOR;
    public static MetaItem<?>.MetaValueItem COVER_ROTATION_POWER_DETECTOR;
    public static MetaItem<?>.MetaValueItem COVER_PRESSURE_DETECTOR;
    public static MetaItem<?>.MetaValueItem COVER_VACUUM_DETECTOR;

    //ULV Components
    public static MetaItem<?>.MetaValueItem ELECTRIC_MOTOR_ULV;
    public static MetaItem<?>.MetaValueItem ELECTRIC_PUMP_ULV;
    public static MetaItem<?>.MetaValueItem PISTON_ULV;
    public static MetaItem<?>.MetaValueItem CONVEYOR_ULV;
    public static MetaItem<?>.MetaValueItem ROBOT_ARM_ULV;

    //Prospectors
    public static MetaItem<?>.MetaValueItem TKCYA_PROSPECTOR_LV;
    public static MetaItem<?>.MetaValueItem TKCYA_PROSPECTOR_HV;
    public static MetaItem<?>.MetaValueItem TKCYA_PROSPECTOR_LUV;


    public static void init() {
        TKCYAMetaItem1 metaItem1 = new TKCYAMetaItem1((short) 0);
        metaItem1.setRegistryName("tkcya");

    }


}
