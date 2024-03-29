package tekcays_addon.common.items;

import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.common.items.TKCYAMetaItems.*;
import static tekcays_addon.gtapi.unification.TKCYAMaterials.Ceramic;
import static tekcays_addon.gtapi.unification.material.ore.TKCYAOrePrefix.*;

import java.util.List;

import net.minecraft.client.resources.I18n;

import gregtech.api.items.metaitem.StandardMetaItem;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.common.items.behaviors.TooltipBehavior;
import tekcays_addon.api.covers.CoverMethods;
import tekcays_addon.common.items.behaviors.ElectrodeBehavior;
import tekcays_addon.common.items.behaviors.FilterBehavior;

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

        // Damageable items
        ELECTRODE = addItem(5, "electrode").addComponents(new ElectrodeBehavior());
        FILTER = addItem(6, "filter").addComponents(new FilterBehavior());

        // Mixtures
        DUST_MIXTURE = addItem(7, "dust_mixture");

        // Covers
        COVER_TEMPERATURE_DETECTOR = addItem(8, "cover/detector/cover.temperature_detector");
        COVER_PRESSURE_DETECTOR = addItem(9, "cover/detector/cover.pressure_detector");
        COVER_VACUUM_DETECTOR = addItem(10, "cover/detector/cover.vacuum_detector");
        COVER_SPEED_DETECTOR = addItem(11, "cover/detector/cover.speed_detector");
        COVER_TORQUE_DETECTOR = addItem(12, "cover/detector/cover.torque_detector");
        COVER_ROTATION_POWER_DETECTOR = addItem(13, "cover/detector/cover.rotation_power_detector");

        // ULV Components
        ELECTRIC_MOTOR_ULV = addItem(200, "electric_motor_ulv");
        ELECTRIC_PUMP_ULV = addItem(201, "electric_pump_ulv");
        PISTON_ULV = addItem(202, "piston_ulv");
        CONVEYOR_ULV = addItem(203, "conveyor_ulv");
        ROBOT_ARM_ULV = addItem(204, "robot_arm_ulv");

        EMPTY_MOLD_CERAMIC = addItem(68, "mold.empty_ceramic").setUnificationData(moldEmpty, MarkerMaterials.Empty)
                .setUnificationData(moldEmpty, Ceramic);
        EMPTY_MOLD_STEEL = addItem(69, "mold.empty_steel").setUnificationData(moldEmpty, MarkerMaterials.Empty)
                .setUnificationData(moldEmpty, Steel);
        EMPTY_MOLD_TUNGSTEN_CARBIDE = addItem(70, "mold.empty_tungsten_carbide")
                .setUnificationData(moldEmpty, MarkerMaterials.Empty).setUnificationData(moldEmpty, TungstenCarbide);
        EMPTY_MOLD_CARBON = addItem(71, "mold.empty_carbon").setUnificationData(moldEmpty, MarkerMaterials.Empty)
                .setUnificationData(moldEmpty, Carbon);

        COVER_MOLD_INGOT_CERAMIC = addItem(72, "cover.mold.ingot_ceramic")
                .setUnificationData(moldIngot, MarkerMaterials.Empty).setUnificationData(moldIngot, Ceramic);
        COVER_MOLD_PLATE_CERAMIC = addItem(73, "cover.mold.plate_ceramic")
                .setUnificationData(moldPlate, MarkerMaterials.Empty).setUnificationData(moldPlate, Ceramic);
        COVER_MOLD_STICK_CERAMIC = addItem(74, "cover.mold.stick_ceramic")
                .setUnificationData(moldStick, MarkerMaterials.Empty).setUnificationData(moldStick, Ceramic);
        COVER_MOLD_STICK_LONG_CERAMIC = addItem(75, "cover.mold.stick_long_ceramic")
                .setUnificationData(moldStickLong, MarkerMaterials.Empty).setUnificationData(moldStickLong, Ceramic);
        COVER_MOLD_GEAR_CERAMIC = addItem(76, "cover.mold.gear_ceramic")
                .setUnificationData(moldGear, MarkerMaterials.Empty).setUnificationData(moldGear, Ceramic);
        COVER_MOLD_GEAR_SMALL_CERAMIC = addItem(77, "cover.mold.gear_small_ceramic")
                .setUnificationData(moldGearSmall, MarkerMaterials.Empty).setUnificationData(moldGearSmall, Ceramic);
        COVER_MOLD_BOLT_CERAMIC = addItem(78, "cover.mold.bolt_ceramic")
                .setUnificationData(moldBolt, MarkerMaterials.Empty).setUnificationData(moldBolt, Ceramic);
        COVER_MOLD_RING_CERAMIC = addItem(79, "cover.mold.ring_ceramic")
                .setUnificationData(moldRing, MarkerMaterials.Empty).setUnificationData(moldRing, Ceramic);

        COVER_MOLD_INGOT_STEEL = addItem(82, "cover.mold.ingot_steel")
                .setUnificationData(moldIngot, MarkerMaterials.Empty).setUnificationData(moldIngot, Steel);
        COVER_MOLD_PLATE_STEEL = addItem(83, "cover.mold.plate_steel")
                .setUnificationData(moldPlate, MarkerMaterials.Empty).setUnificationData(moldPlate, Steel);
        COVER_MOLD_STICK_STEEL = addItem(84, "cover.mold.stick_steel")
                .setUnificationData(moldStick, MarkerMaterials.Empty).setUnificationData(moldStick, Steel);
        COVER_MOLD_STICK_LONG_STEEL = addItem(85, "cover.mold.stick_long_steel")
                .setUnificationData(moldStickLong, MarkerMaterials.Empty).setUnificationData(moldStickLong, Steel);
        COVER_MOLD_GEAR_STEEL = addItem(86, "cover.mold.gear_steel").setUnificationData(moldGear, MarkerMaterials.Empty)
                .setUnificationData(moldGear, Steel);
        COVER_MOLD_GEAR_SMALL_STEEL = addItem(87, "cover.mold.gear_small_steel")
                .setUnificationData(moldGearSmall, MarkerMaterials.Empty).setUnificationData(moldGearSmall, Steel);
        COVER_MOLD_BOLT_STEEL = addItem(88, "cover.mold.bolt_steel").setUnificationData(moldBolt, MarkerMaterials.Empty)
                .setUnificationData(moldBolt, Steel);
        COVER_MOLD_RING_STEEL = addItem(89, "cover.mold.ring_steel").setUnificationData(moldRing, MarkerMaterials.Empty)
                .setUnificationData(moldRing, Steel);

        COVER_MOLD_INGOT_TUNGSTEN_CARBIDE = addItem(92, "cover.mold.ingot_tungsten_carbide")
                .setUnificationData(moldIngot, MarkerMaterials.Empty).setUnificationData(moldIngot, TungstenCarbide);
        COVER_MOLD_PLATE_TUNGSTEN_CARBIDE = addItem(93, "cover.mold.plate_tungsten_carbide")
                .setUnificationData(moldPlate, MarkerMaterials.Empty).setUnificationData(moldPlate, TungstenCarbide);
        COVER_MOLD_STICK_TUNGSTEN_CARBIDE = addItem(94, "cover.mold.stick_tungsten_carbide")
                .setUnificationData(moldStick, MarkerMaterials.Empty).setUnificationData(moldStick, TungstenCarbide);
        COVER_MOLD_STICK_LONG_TUNGSTEN_CARBIDE = addItem(95, "cover.mold.stick_long_tungsten_carbide")
                .setUnificationData(moldStickLong, MarkerMaterials.Empty)
                .setUnificationData(moldStickLong, TungstenCarbide);
        COVER_MOLD_GEAR_TUNGSTEN_CARBIDE = addItem(96, "cover.mold.gear_tungsten_carbide")
                .setUnificationData(moldGear, MarkerMaterials.Empty).setUnificationData(moldGear, TungstenCarbide);
        COVER_MOLD_GEAR_SMALL_TUNGSTEN_CARBIDE = addItem(97, "cover.mold.gear_small_tungsten_carbide")
                .setUnificationData(moldGearSmall, MarkerMaterials.Empty)
                .setUnificationData(moldGearSmall, TungstenCarbide);
        COVER_MOLD_BOLT_TUNGSTEN_CARBIDE = addItem(98, "cover.mold.bolt_tungsten_carbide")
                .setUnificationData(moldBolt, MarkerMaterials.Empty).setUnificationData(moldBolt, TungstenCarbide);
        COVER_MOLD_RING_TUNGSTEN_CARBIDE = addItem(99, "cover.mold.ring_tungsten_carbide")
                .setUnificationData(moldRing, MarkerMaterials.Empty).setUnificationData(moldRing, TungstenCarbide);

        COVER_MOLD_INGOT_CARBON = addItem(102, "cover.mold.ingot_carbon")
                .setUnificationData(moldIngot, MarkerMaterials.Empty).setUnificationData(moldIngot, Carbon);
        COVER_MOLD_PLATE_CARBON = addItem(103, "cover.mold.plate_carbon")
                .setUnificationData(moldPlate, MarkerMaterials.Empty).setUnificationData(moldPlate, Carbon);
        COVER_MOLD_STICK_CARBON = addItem(104, "cover.mold.stick_carbon")
                .setUnificationData(moldStick, MarkerMaterials.Empty).setUnificationData(moldStick, Carbon);
        COVER_MOLD_STICK_LONG_CARBON = addItem(105, "cover.mold.stick_long_carbon")
                .setUnificationData(moldStickLong, MarkerMaterials.Empty).setUnificationData(moldStickLong, Carbon);
        COVER_MOLD_GEAR_CARBON = addItem(106, "cover.mold.gear_carbon")
                .setUnificationData(moldGear, MarkerMaterials.Empty).setUnificationData(moldGear, Carbon);
        COVER_MOLD_GEAR_SMALL_CARBON = addItem(107, "cover.mold.gear_small_carbon")
                .setUnificationData(moldGearSmall, MarkerMaterials.Empty).setUnificationData(moldGearSmall, Carbon);
        COVER_MOLD_BOLT_CARBON = addItem(108, "cover.mold.bolt_carbon")
                .setUnificationData(moldBolt, MarkerMaterials.Empty).setUnificationData(moldBolt, Carbon);
        COVER_MOLD_RING_CARBON = addItem(109, "cover.mold.ring_carbon")
                .setUnificationData(moldRing, MarkerMaterials.Empty).setUnificationData(moldRing, Carbon);

        // COVER_MOLD_BLOCK_CERAMIC = addItem(80, "cover.mold.block_ceramic").setUnificationData(moldBlock,
        // MarkerMaterials.Empty).setUnificationData(moldBlock, Ceramic);
        // COVER_MOLD_BLOCK_STEEL = addItem(90, "cover.mold.block_steel").setUnificationData(moldBlock,
        // MarkerMaterials.Empty).setUnificationData(moldBlock, Steel);
        // COVER_MOLD_BLOCK_CARBON = addItem(110, "cover.mold.block_carbon").setUnificationData(moldBlock,
        // MarkerMaterials.Empty).setUnificationData(moldBlock, Carbon);
        // COVER_MOLD_BLOCK_TUNGSTEN_CARBIDE = addItem(100,
        // "cover.mold.block_tungsten_carbide").setUnificationData(moldBlock,
        // MarkerMaterials.Empty).setUnificationData(moldBlock, TungstenCarbide);

        // FromGTCEu
        // Pumps: ID 141-155

        ELECTRIC_PUMP_LV = addItem(142, "electric.pump.lv").addComponents(generatePumpTooltips(1))
                .setUnificationData(lvComponents, MarkerMaterials.Empty);

        ELECTRIC_PUMP_MV = addItem(143, "electric.pump.mv").addComponents(generatePumpTooltips(2))
                .setUnificationData(mvComponents, MarkerMaterials.Empty);

        ELECTRIC_PUMP_HV = addItem(144, "electric.pump.hv").addComponents(generatePumpTooltips(3))
                .setUnificationData(hvComponents, MarkerMaterials.Empty);

        ELECTRIC_PUMP_EV = addItem(145, "electric.pump.ev").addComponents(generatePumpTooltips(4))
                .setUnificationData(evComponents, MarkerMaterials.Empty);

        ELECTRIC_PUMP_IV = addItem(146, "electric.pump.iv").addComponents(generatePumpTooltips(5))
                .setUnificationData(ivComponents, MarkerMaterials.Empty);

        ELECTRIC_PUMP_LuV = addItem(147, "electric.pump.luv").addComponents(generatePumpTooltips(6))
                .setUnificationData(luvComponents, MarkerMaterials.Empty);

        ELECTRIC_PUMP_ZPM = addItem(148, "electric.pump.zpm").addComponents(generatePumpTooltips(7))
                .setUnificationData(zpmComponents, MarkerMaterials.Empty);

        ELECTRIC_PUMP_UV = addItem(149, "electric.pump.uv").addComponents(generatePumpTooltips(8))
                .setUnificationData(uvComponents, MarkerMaterials.Empty);

        // Conveyors: ID 156-170
        CONVEYOR_MODULE_LV = addItem(157, "conveyor.module.lv").addComponents(generateConveyorTooltips(1))
                .setUnificationData(lvComponents, MarkerMaterials.Empty);

        CONVEYOR_MODULE_MV = addItem(158, "conveyor.module.mv").addComponents(generateConveyorTooltips(2))
                .setUnificationData(mvComponents, MarkerMaterials.Empty);

        CONVEYOR_MODULE_HV = addItem(159, "conveyor.module.hv").addComponents(generateConveyorTooltips(3))
                .setUnificationData(hvComponents, MarkerMaterials.Empty);

        CONVEYOR_MODULE_EV = addItem(160, "conveyor.module.ev").addComponents(generateConveyorTooltips(5))
                .setUnificationData(evComponents, MarkerMaterials.Empty);

        CONVEYOR_MODULE_IV = addItem(161, "conveyor.module.iv").addComponents(generateConveyorTooltips(6))
                .setUnificationData(ivComponents, MarkerMaterials.Empty);

        CONVEYOR_MODULE_LuV = addItem(162, "conveyor.module.luv").addComponents(generateConveyorTooltips(7))
                .setUnificationData(luvComponents, MarkerMaterials.Empty);

        CONVEYOR_MODULE_ZPM = addItem(163, "conveyor.module.zpm").addComponents(generateConveyorTooltips(8))
                .setUnificationData(zpmComponents, MarkerMaterials.Empty);

        CONVEYOR_MODULE_UV = addItem(164, "conveyor.module.uv").addComponents(generateConveyorTooltips(9))
                .setUnificationData(uvComponents, MarkerMaterials.Empty);

        // Robot Arms: ID 186-200
        ROBOT_ARM_LV = addItem(187, "robot.arm.lv").addComponents(generateRoboticArmTooltips(1))
                .setUnificationData(lvComponents, MarkerMaterials.Empty);

        ROBOT_ARM_MV = addItem(188, "robot.arm.mv").addComponents(generateRoboticArmTooltips(2))
                .setUnificationData(mvComponents, MarkerMaterials.Empty);

        ROBOT_ARM_HV = addItem(189, "robot.arm.hv").addComponents(generateRoboticArmTooltips(3))
                .setUnificationData(hvComponents, MarkerMaterials.Empty);

        ROBOT_ARM_EV = addItem(190, "robot.arm.ev").addComponents(generateRoboticArmTooltips(4))
                .setUnificationData(evComponents, MarkerMaterials.Empty);

        ROBOT_ARM_IV = addItem(191, "robot.arm.iv").addComponents(generateRoboticArmTooltips(5))
                .setUnificationData(ivComponents, MarkerMaterials.Empty);

        ROBOT_ARM_LuV = addItem(192, "robot.arm.luv").addComponents(generateRoboticArmTooltips(6))
                .setUnificationData(luvComponents, MarkerMaterials.Empty);

        ROBOT_ARM_ZPM = addItem(193, "robot.arm.zpm").addComponents(generateRoboticArmTooltips(7))
                .setUnificationData(zpmComponents, MarkerMaterials.Empty);

        ROBOT_ARM_UV = addItem(194, "robot.arm.uv").addComponents(generateRoboticArmTooltips(8))
                .setUnificationData(uvComponents, MarkerMaterials.Empty);

        // Fluid Regulators: ID 246-260
        FLUID_REGULATOR_LV = addItem(247, "fluid.regulator.lv").addComponents(generateRegulatorTooltips(1))
                .setUnificationData(lvComponents, MarkerMaterials.Empty);

        FLUID_REGULATOR_MV = addItem(248, "fluid.regulator.mv").addComponents(generateRegulatorTooltips(2))
                .setUnificationData(mvComponents, MarkerMaterials.Empty);

        FLUID_REGULATOR_HV = addItem(249, "fluid.regulator.hv").addComponents(generateRegulatorTooltips(3))
                .setUnificationData(hvComponents, MarkerMaterials.Empty);

        FLUID_REGULATOR_EV = addItem(250, "fluid.regulator.ev").addComponents(generateRegulatorTooltips(4))
                .setUnificationData(evComponents, MarkerMaterials.Empty);

        FLUID_REGULATOR_IV = addItem(251, "fluid.regulator.iv").addComponents(generateRegulatorTooltips(5))
                .setUnificationData(ivComponents, MarkerMaterials.Empty);;
        FLUID_REGULATOR_LUV = addItem(252, "fluid.regulator.luv").addComponents(generateRegulatorTooltips(6))
                .setUnificationData(luvComponents, MarkerMaterials.Empty);

        FLUID_REGULATOR_ZPM = addItem(253, "fluid.regulator.zpm").addComponents(generateRegulatorTooltips(7))
                .setUnificationData(zpmComponents, MarkerMaterials.Empty);

        FLUID_REGULATOR_UV = addItem(254, "fluid.regulator.uv").addComponents(generateRegulatorTooltips(8))
                .setUnificationData(uvComponents, MarkerMaterials.Empty);
    }

    private static TooltipBehavior generatePumpTooltips(int tier) {
        return new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.electric.pump.tooltip"));
            lines.add(I18n.format("gregtech.universal.tooltip.fluid_transfer_rate",
                    CoverMethods.getFluidTransferRatePerTick(tier)));
            addPlacementAndConsumptionTooltips(lines, tier);
        });
    }

    private static void addPlacementAndConsumptionTooltips(List<String> lines, int tier) {
        lines.add(I18n.format("tkcya.electric.pump.placement"));
        lines.add(I18n.format("tkcya.electric.pump.consumption", CoverMethods.getEnergyPerOperation(tier)));
    }

    private static TooltipBehavior generateConveyorTooltips(int tier) {
        return new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.conveyor.module.tooltip"));
            lines.add(I18n.format("gregtech.universal.tooltip.item_transfer_rate",
                    CoverMethods.getItemTransferRatePerSecond(tier)));
            addPlacementAndConsumptionTooltips(lines, tier);
        });
    }

    private static TooltipBehavior generateRoboticArmTooltips(int tier) {
        return new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.robot.arm.tooltip"));
            lines.add(I18n.format("gregtech.universal.tooltip.item_transfer_rate",
                    CoverMethods.getItemTransferRatePerSecond(tier)));
            addPlacementAndConsumptionTooltips(lines, tier);
        });
    }

    private static TooltipBehavior generateRegulatorTooltips(int tier) {
        return new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.fluid.regulator.tooltip"));
            lines.add(I18n.format("gregtech.universal.tooltip.fluid_transfer_rate",
                    CoverMethods.getFluidTransferRatePerTick(tier)));
            addPlacementAndConsumptionTooltips(lines, tier);
        });
    }
}
