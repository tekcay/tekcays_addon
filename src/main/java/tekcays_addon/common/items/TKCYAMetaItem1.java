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

        COVER_MOLD_INGOT_CERAMIC = addItem(72, "cover.mold.ingot_ceramic");
        COVER_MOLD_PLATE_CERAMIC = addItem(73, "cover.mold.plate_ceramic");
        COVER_MOLD_STICK_CERAMIC = addItem(74, "cover.mold.stick_ceramic");
        COVER_MOLD_STICK_LONG_CERAMIC = addItem(75, "cover.mold.stick_long_ceramic");
        COVER_MOLD_GEAR_CERAMIC = addItem(76, "cover.mold.gear_ceramic");
        COVER_MOLD_GEAR_SMALL_CERAMIC = addItem(77, "cover.mold.gear_small_ceramic");
        COVER_MOLD_BOLT_CERAMIC = addItem(78, "cover.mold.bolt_ceramic");
        COVER_MOLD_RING_CERAMIC = addItem(79, "cover.mold.ring_ceramic");
        COVER_MOLD_BLOCK_CERAMIC = addItem(80, "cover.mold.block_ceramic");
        COVER_MOLD_INGOT_STEEL = addItem(82, "cover.mold.ingot_steel");
        COVER_MOLD_PLATE_STEEL = addItem(83, "cover.mold.plate_steel");
        COVER_MOLD_STICK_STEEL = addItem(84, "cover.mold.stick_steel");
        COVER_MOLD_STICK_LONG_STEEL = addItem(85, "cover.mold.stick_long_steel");
        COVER_MOLD_GEAR_STEEL = addItem(86, "cover.mold.gear_steel");
        COVER_MOLD_GEAR_SMALL_STEEL = addItem(87, "cover.mold.gear_small_steel");
        COVER_MOLD_BOLT_STEEL = addItem(88, "cover.mold.bolt_steel");
        COVER_MOLD_RING_STEEL = addItem(89, "cover.mold.ring_steel");
        COVER_MOLD_BLOCK_STEEL = addItem(90, "cover.mold.block_steel");
        COVER_MOLD_INGOT_TUNGSTEN_CARBIDE = addItem(92, "cover.mold.ingot_tungsten_carbide");
        COVER_MOLD_PLATE_TUNGSTEN_CARBIDE = addItem(93, "cover.mold.plate_tungsten_carbide");
        COVER_MOLD_STICK_TUNGSTEN_CARBIDE = addItem(94, "cover.mold.stick_tungsten_carbide");
        COVER_MOLD_STICK_LONG_TUNGSTEN_CARBIDE = addItem(95, "cover.mold.stick_long_tungsten_carbide");
        COVER_MOLD_GEAR_TUNGSTEN_CARBIDE = addItem(96, "cover.mold.gear_tungsten_carbide");
        COVER_MOLD_GEAR_SMALL_TUNGSTEN_CARBIDE = addItem(97, "cover.mold.gear_small_tungsten_carbide");
        COVER_MOLD_BOLT_TUNGSTEN_CARBIDE = addItem(98, "cover.mold.bolt_tungsten_carbide");
        COVER_MOLD_RING_TUNGSTEN_CARBIDE = addItem(99, "cover.mold.ring_tungsten_carbide");
        COVER_MOLD_BLOCK_TUNGSTEN_CARBIDE = addItem(100, "cover.mold.block_tungsten_carbide");
        COVER_MOLD_INGOT_CARBON = addItem(102, "cover.mold.ingot_carbon");
        COVER_MOLD_PLATE_CARBON = addItem(103, "cover.mold.plate_carbon");
        COVER_MOLD_STICK_CARBON = addItem(104, "cover.mold.stick_carbon");
        COVER_MOLD_STICK_LONG_CARBON = addItem(105, "cover.mold.stick_long_carbon");
        COVER_MOLD_GEAR_CARBON = addItem(106, "cover.mold.gear_carbon");
        COVER_MOLD_GEAR_SMALL_CARBON = addItem(107, "cover.mold.gear_small_carbon");
        COVER_MOLD_BOLT_CARBON = addItem(108, "cover.mold.bolt_carbon");
        COVER_MOLD_RING_CARBON = addItem(109, "cover.mold.ring_carbon");
        COVER_MOLD_BLOCK_CARBON = addItem(110, "cover.mold.block_carbon");






    }
}
