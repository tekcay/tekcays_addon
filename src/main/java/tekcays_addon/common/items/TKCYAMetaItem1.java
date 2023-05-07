package tekcays_addon.common.items;

import gregtech.api.items.metaitem.StandardMetaItem;
import tekcays_addon.common.items.behaviors.ElectrodeBehavior;
import tekcays_addon.common.items.behaviors.FilterBehavior;
import tekcays_addon.gtapi.consts.TKCYAValues;
import tekcays_addon.gtapi.utils.TKCYALog;

import java.util.concurrent.atomic.AtomicInteger;

import static tekcays_addon.common.items.TKCYAMetaItems.*;
import static tekcays_addon.gtapi.consts.TKCYAValues.MOLDS;
import static tekcays_addon.gtapi.consts.TKCYAValues.MOLDS_OREPREFIX_TO_MOLDS_MATERIAL;

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
        COVER_TEMPERATURE_DETECTOR = addItem(8, "cover/detector/cover.temperature_detector");
        COVER_PRESSURE_DETECTOR = addItem(9, "cover/detector/cover.pressure_detector");
        COVER_VACUUM_DETECTOR = addItem(10, "cover/detector/cover.vacuum_detector");
        COVER_SPEED_DETECTOR = addItem(11, "cover/detector/cover.speed_detector");
        COVER_TORQUE_DETECTOR = addItem(12, "cover/detector/cover.torque_detector");
        COVER_ROTATION_POWER_DETECTOR = addItem(13, "cover/detector/cover.rotation_power_detector");


        //ULV Components
        ELECTRIC_MOTOR_ULV = addItem(200, "electric_motor_ulv");
        ELECTRIC_PUMP_ULV = addItem(201, "electric_pump_ulv");
        PISTON_ULV = addItem(202, "piston_ulv");
        CONVEYOR_ULV = addItem(203, "conveyor_ulv");
        ROBOT_ARM_ULV = addItem(204, "robot_arm_ulv");

        MOLD_INGOT_CERAMIC = addItem(72, "cover/mold/cover_mold_ingot_ceramic");
        MOLD_PLATE_CERAMIC = addItem(73, "cover/mold/cover_mold_plate_ceramic");
        MOLD_STICK_CERAMIC = addItem(74, "cover/mold/cover_mold_stick_ceramic");
        MOLD_STICK_LONG_CERAMIC = addItem(75, "cover/mold/cover_mold_stick_long_ceramic");
        MOLD_GEAR_CERAMIC = addItem(76, "cover/mold/cover_mold_gear_ceramic");
        MOLD_GEAR_SMALL_CERAMIC = addItem(77, "cover/mold/cover_mold_gear_small_ceramic");
        MOLD_BOLT_CERAMIC = addItem(78, "cover/mold/cover_mold_bolt_ceramic");
        MOLD_RING_CERAMIC = addItem(79, "cover/mold/cover_mold_ring_ceramic");
        MOLD_BLOCK_CERAMIC = addItem(80, "cover/mold/cover_mold_block_ceramic");
        MOLD_INGOT_STEEL = addItem(82, "cover/mold/cover_mold_ingot_steel");
        MOLD_PLATE_STEEL = addItem(83, "cover/mold/cover_mold_plate_steel");
        MOLD_STICK_STEEL = addItem(84, "cover/mold/cover_mold_stick_steel");
        MOLD_STICK_LONG_STEEL = addItem(85, "cover/mold/cover_mold_stick_long_steel");
        MOLD_GEAR_STEEL = addItem(86, "cover/mold/cover_mold_gear_steel");
        MOLD_GEAR_SMALL_STEEL = addItem(87, "cover/mold/cover_mold_gear_small_steel");
        MOLD_BOLT_STEEL = addItem(88, "cover/mold/cover_mold_bolt_steel");
        MOLD_RING_STEEL = addItem(89, "cover/mold/cover_mold_ring_steel");
        MOLD_BLOCK_STEEL = addItem(90, "cover/mold/cover_mold_block_steel");
        MOLD_INGOT_TUNGSTEN_CARBIDE = addItem(92, "cover/mold/cover_mold_ingot_tungsten_carbide");
        MOLD_PLATE_TUNGSTEN_CARBIDE = addItem(93, "cover/mold/cover_mold_plate_tungsten_carbide");
        MOLD_STICK_TUNGSTEN_CARBIDE = addItem(94, "cover/mold/cover_mold_stick_tungsten_carbide");
        MOLD_STICK_LONG_TUNGSTEN_CARBIDE = addItem(95, "cover/mold/cover_mold_stick_long_tungsten_carbide");
        MOLD_GEAR_TUNGSTEN_CARBIDE = addItem(96, "cover/mold/cover_mold_gear_tungsten_carbide");
        MOLD_GEAR_SMALL_TUNGSTEN_CARBIDE = addItem(97, "cover/mold/cover_mold_gear_small_tungsten_carbide");
        MOLD_BOLT_TUNGSTEN_CARBIDE = addItem(98, "cover/mold/cover_mold_bolt_tungsten_carbide");
        MOLD_RING_TUNGSTEN_CARBIDE = addItem(99, "cover/mold/cover_mold_ring_tungsten_carbide");
        MOLD_BLOCK_TUNGSTEN_CARBIDE = addItem(100, "cover/mold/cover_mold_block_tungsten_carbide");
        MOLD_INGOT_CARBON = addItem(102, "cover/mold/cover_mold_ingot_carbon");
        MOLD_PLATE_CARBON = addItem(103, "cover/mold/cover_mold_plate_carbon");
        MOLD_STICK_CARBON = addItem(104, "cover/mold/cover_mold_stick_carbon");
        MOLD_STICK_LONG_CARBON = addItem(105, "cover/mold/cover_mold_stick_long_carbon");
        MOLD_GEAR_CARBON = addItem(106, "cover/mold/cover_mold_gear_carbon");
        MOLD_GEAR_SMALL_CARBON = addItem(107, "cover/mold/cover_mold_gear_small_carbon");
        MOLD_BOLT_CARBON = addItem(108, "cover/mold/cover_mold_bolt_carbon");
        MOLD_RING_CARBON = addItem(109, "cover/mold/cover_mold_ring_carbon");
        MOLD_BLOCK_CARBON = addItem(110, "cover/mold/cover_mold_block_carbon");


    }
}
