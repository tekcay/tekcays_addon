package tekcays_addon.common.items;

import gregtech.api.items.metaitem.StandardMetaItem;
import tekcays_addon.common.items.behaviors.ElectrodeBehavior;
import tekcays_addon.common.items.behaviors.FilterBehavior;
import static tekcays_addon.common.items.TKCYAMetaItems.*;

public class TKCYAMetaItem1 extends StandardMetaItem {

    public TKCYAMetaItem1(short i) {
        super(i);
    }

    @Override
    public void registerSubItems() {

        MICA_SHEET = addItem(1, "mica.sheet");
        MICA_INSULATOR_SHEET = addItem(2, "mica_insulator.sheet");
        MICA_INSULATOR_FOIL = addItem(3, "mica_insulator.foil");
        GAS_COLLECTOR = addItem(4, "gas_collector");

        //Damageable items
        ELECTRODE = addItem(5, "electrode").addComponents(new ElectrodeBehavior());
        FILTER = addItem(6, "filter").addComponents(new FilterBehavior());

        //Mixtures
        DUST_MIXTURE = addItem(7, "dust_mixture");

        //Covers
        COVER_TEMPERATURE_DETECTOR = addItem(8, "cover.temperature_detector");
        COVER_PRESSURE_DETECTOR = addItem(9, "cover.pressure_detector");
        COVER_VACUUM_DETECTOR = addItem(10, "cover.vacuum_detector");
        COVER_SPEED_DETECTOR = addItem(11, "cover.speed_detector");
        COVER_TORQUE_DETECTOR = addItem(12, "cover.torque_detector");
        COVER_ROTATION_POWER_DETECTOR = addItem(13, "cover.rotation_power_detector");

        //ULV Components
        ELECTRIC_MOTOR_ULV = addItem(20, "electric_motor_ulv");
        ELECTRIC_PUMP_ULV = addItem(21, "electric_pump_ulv");
        PISTON_ULV = addItem(22, "piston_ulv");
        CONVEYOR_ULV = addItem(23, "conveyor_ulv");
        ROBOT_ARM_ULV = addItem(24, "robot_arm_ulv");

    }
}
