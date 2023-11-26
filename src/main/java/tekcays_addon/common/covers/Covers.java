package tekcays_addon.common.covers;

import gregtech.api.GTValues;
import net.minecraft.util.ResourceLocation;
import tekcays_addon.TekCaysAddon;
import tekcays_addon.api.covers.CoverMethods;
import tekcays_addon.api.detectors.CoverDetectorWrapper;
import tekcays_addon.common.items.TKCYAMetaItems;
import tekcays_addon.gtapi.utils.TKCYALog;


import static gregtech.common.covers.CoverBehaviors.registerBehavior;
import static tekcays_addon.api.consts.DetectorWrappers.*;
import static tekcays_addon.api.covers.molds.CoverMoldWrapper.*;
import static tekcays_addon.common.items.TKCYAMetaItems.*;
import static tekcays_addon.gtapi.capability.TKCYATileCapabilities.*;
import static tekcays_addon.gtapi.consts.TKCYAValues.tkcyaId;
import static tekcays_addon.gtapi.render.TKCYATextures.*;

public class Covers {

    public static void init() {
        TKCYALog.logger.info("Registering cover behaviors...");
        TKCYALog.logger.info("Registering detector covers...");

        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, "temperature_detector"), COVER_TEMPERATURE_DETECTOR,
                (coverHolder, attachedSide) -> new CoverDetector(coverHolder, attachedSide,
                        new CoverDetectorWrapper(TEMPERATURE_DETECTOR_WRAPPER, DETECTOR_TEMPERATURE, (holder, side) -> holder.getCapability(CAPABILITY_HEAT_CONTAINER, side).getTemperature())));

        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, "speed_detector"), COVER_SPEED_DETECTOR,
                (coverHolder, attachedSide) -> new CoverDetector(coverHolder, attachedSide,
                        new CoverDetectorWrapper(SPEED_DETECTOR_WRAPPER, DETECTOR_SPEED, (holder, side) -> holder.getCapability(CAPABILITY_ROTATIONAL_CONTAINER, side).getSpeed())));

        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, "torque_detector"), COVER_TORQUE_DETECTOR,
                (coverHolder, attachedSide) -> new CoverDetector(coverHolder, attachedSide,
                        new CoverDetectorWrapper(TORQUE_DETECTOR_WRAPPER, DETECTOR_TORQUE, (holder, side) -> holder.getCapability(CAPABILITY_ROTATIONAL_CONTAINER, side).getTorque())));

        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, "rotation_power_detector"), COVER_ROTATION_POWER_DETECTOR,
                (coverHolder, attachedSide) -> new CoverDetector(coverHolder, attachedSide,
                        new CoverDetectorWrapper(ROTATION_POWER_DETECTOR_WRAPPER, DETECTOR_ROTATION_POWER, (holder, side) -> holder.getCapability(CAPABILITY_ROTATIONAL_CONTAINER, side).getPower())));

        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, "pressure_detector"), COVER_PRESSURE_DETECTOR,
                (coverHolder, attachedSide) -> new CoverDetector(coverHolder, attachedSide,
                        new CoverDetectorWrapper(PRESSURE_DETECTOR_WRAPPER, DETECTOR_PRESSURE, (holder, side) -> holder.getCapability(CAPABILITY_PRESSURE_CONTAINER, side).getPressure())));

        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, "vacuum_detector"), COVER_VACUUM_DETECTOR,
                (coverHolder, attachedSide) -> new CoverDetector(coverHolder, attachedSide,
                        new CoverDetectorWrapper(VACUUM_DETECTOR_WRAPPER, DETECTOR_VACUUM, (holder, side) -> holder.getCapability(CAPABILITY_PRESSURE_CONTAINER, side).getPressure())));

        TKCYALog.logger.info("Registering mold covers...");

        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_INGOT_CERAMIC_WRAPPER.getPathln()), COVER_MOLD_INGOT_CERAMIC,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_INGOT_CERAMIC_WRAPPER));
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_PLATE_CERAMIC_WRAPPER.getPathln()), COVER_MOLD_PLATE_CERAMIC,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_PLATE_CERAMIC_WRAPPER));
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_STICK_CERAMIC_WRAPPER.getPathln()), COVER_MOLD_STICK_CERAMIC,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_STICK_CERAMIC_WRAPPER));
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_STICK_LONG_CERAMIC_WRAPPER.getPathln()), COVER_MOLD_STICK_LONG_CERAMIC,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_STICK_LONG_CERAMIC_WRAPPER));
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_GEAR_CERAMIC_WRAPPER.getPathln()), COVER_MOLD_GEAR_CERAMIC,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_GEAR_CERAMIC_WRAPPER));
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_GEAR_SMALL_CERAMIC_WRAPPER.getPathln()), COVER_MOLD_GEAR_SMALL_CERAMIC,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_GEAR_SMALL_CERAMIC_WRAPPER));
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_BOLT_CERAMIC_WRAPPER.getPathln()), COVER_MOLD_BOLT_CERAMIC,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_BOLT_CERAMIC_WRAPPER));
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_RING_CERAMIC_WRAPPER.getPathln()), COVER_MOLD_RING_CERAMIC,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_RING_CERAMIC_WRAPPER));

        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_INGOT_STEEL_WRAPPER.getPathln()), COVER_MOLD_INGOT_STEEL,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_INGOT_STEEL_WRAPPER));
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_PLATE_STEEL_WRAPPER.getPathln()), COVER_MOLD_PLATE_STEEL,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_PLATE_STEEL_WRAPPER));
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_STICK_STEEL_WRAPPER.getPathln()), COVER_MOLD_STICK_STEEL,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_STICK_STEEL_WRAPPER));
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_STICK_LONG_STEEL_WRAPPER.getPathln()), COVER_MOLD_STICK_LONG_STEEL,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_STICK_LONG_STEEL_WRAPPER));
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_GEAR_STEEL_WRAPPER.getPathln()), COVER_MOLD_GEAR_STEEL,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_GEAR_STEEL_WRAPPER));
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_GEAR_SMALL_STEEL_WRAPPER.getPathln()), COVER_MOLD_GEAR_SMALL_STEEL,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_GEAR_SMALL_STEEL_WRAPPER));
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_BOLT_STEEL_WRAPPER.getPathln()), COVER_MOLD_BOLT_STEEL,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_BOLT_STEEL_WRAPPER));
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_RING_STEEL_WRAPPER.getPathln()), COVER_MOLD_RING_STEEL,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_RING_STEEL_WRAPPER));

        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_INGOT_TUNGSTEN_CARBIDE_WRAPPER.getPathln()), COVER_MOLD_INGOT_TUNGSTEN_CARBIDE,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_INGOT_TUNGSTEN_CARBIDE_WRAPPER));
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_PLATE_TUNGSTEN_CARBIDE_WRAPPER.getPathln()), COVER_MOLD_PLATE_TUNGSTEN_CARBIDE,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_PLATE_TUNGSTEN_CARBIDE_WRAPPER));
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_STICK_TUNGSTEN_CARBIDE_WRAPPER.getPathln()), COVER_MOLD_STICK_TUNGSTEN_CARBIDE,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_STICK_TUNGSTEN_CARBIDE_WRAPPER));
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_STICK_LONG_TUNGSTEN_CARBIDE_WRAPPER.getPathln()), COVER_MOLD_STICK_LONG_TUNGSTEN_CARBIDE,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_STICK_LONG_TUNGSTEN_CARBIDE_WRAPPER));
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_GEAR_TUNGSTEN_CARBIDE_WRAPPER.getPathln()), COVER_MOLD_GEAR_TUNGSTEN_CARBIDE,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_GEAR_TUNGSTEN_CARBIDE_WRAPPER));
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_GEAR_SMALL_TUNGSTEN_CARBIDE_WRAPPER.getPathln()), COVER_MOLD_GEAR_SMALL_TUNGSTEN_CARBIDE,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_GEAR_SMALL_TUNGSTEN_CARBIDE_WRAPPER));
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_BOLT_TUNGSTEN_CARBIDE_WRAPPER.getPathln()), COVER_MOLD_BOLT_TUNGSTEN_CARBIDE,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_BOLT_TUNGSTEN_CARBIDE_WRAPPER));
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_RING_TUNGSTEN_CARBIDE_WRAPPER.getPathln()), COVER_MOLD_RING_TUNGSTEN_CARBIDE,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_RING_TUNGSTEN_CARBIDE_WRAPPER));

        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_INGOT_CARBON_WRAPPER.getPathln()), COVER_MOLD_INGOT_CARBON,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_INGOT_CARBON_WRAPPER));
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_PLATE_CARBON_WRAPPER.getPathln()), COVER_MOLD_PLATE_CARBON,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_PLATE_CARBON_WRAPPER));
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_STICK_CARBON_WRAPPER.getPathln()), COVER_MOLD_STICK_CARBON,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_STICK_CARBON_WRAPPER));
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_STICK_LONG_CARBON_WRAPPER.getPathln()), COVER_MOLD_STICK_LONG_CARBON,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_STICK_LONG_CARBON_WRAPPER));
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_GEAR_CARBON_WRAPPER.getPathln()), COVER_MOLD_GEAR_CARBON,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_GEAR_CARBON_WRAPPER));
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_GEAR_SMALL_CARBON_WRAPPER.getPathln()), COVER_MOLD_GEAR_SMALL_CARBON,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_GEAR_SMALL_CARBON_WRAPPER));
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_BOLT_CARBON_WRAPPER.getPathln()), COVER_MOLD_BOLT_CARBON,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_BOLT_CARBON_WRAPPER));
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_RING_CARBON_WRAPPER.getPathln()), COVER_MOLD_RING_CARBON,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_RING_CARBON_WRAPPER));

        TKCYALog.logger.info("Registering GTCEu logistic covers...");

        registerBehavior(tkcyaId("conveyor.lv"), TKCYAMetaItems.CONVEYOR_MODULE_LV, (tile, side) -> new CoverConveyorOverhauled(tile, side, GTValues.LV, CoverMethods.getItemTransferRatePerSecond(1)));
        registerBehavior(tkcyaId("conveyor.mv"), TKCYAMetaItems.CONVEYOR_MODULE_MV, (tile, side) -> new CoverConveyorOverhauled(tile, side, GTValues.MV, CoverMethods.getItemTransferRatePerSecond(2)));
        registerBehavior(tkcyaId("conveyor.hv"), TKCYAMetaItems.CONVEYOR_MODULE_HV, (tile, side) -> new CoverConveyorOverhauled(tile, side, GTValues.HV, CoverMethods.getItemTransferRatePerSecond(3)));
        registerBehavior(tkcyaId("conveyor.ev"), TKCYAMetaItems.CONVEYOR_MODULE_EV, (tile, side) -> new CoverConveyorOverhauled(tile, side, GTValues.EV, CoverMethods.getItemTransferRatePerSecond(4)));
        registerBehavior(tkcyaId("conveyor.iv"), TKCYAMetaItems.CONVEYOR_MODULE_IV, (tile, side) -> new CoverConveyorOverhauled(tile, side, GTValues.IV, CoverMethods.getItemTransferRatePerSecond(5)));
        registerBehavior(tkcyaId("conveyor.luv"), TKCYAMetaItems.CONVEYOR_MODULE_LuV, (tile, side) -> new CoverConveyorOverhauled(tile, side, GTValues.LuV, CoverMethods.getItemTransferRatePerSecond(6)));
        registerBehavior(tkcyaId("conveyor.zpm"), TKCYAMetaItems.CONVEYOR_MODULE_ZPM, (tile, side) -> new CoverConveyorOverhauled(tile, side, GTValues.ZPM, CoverMethods.getItemTransferRatePerSecond(7)));
        registerBehavior(tkcyaId("conveyor.uv"), TKCYAMetaItems.CONVEYOR_MODULE_UV, (tile, side) -> new CoverConveyorOverhauled(tile, side, GTValues.UV, CoverMethods.getItemTransferRatePerSecond(8)));

        registerBehavior(tkcyaId("robotic_arm.lv"), TKCYAMetaItems.ROBOT_ARM_LV, (tile, side) -> new CoverRoboticArmOverhauled(tile, side, GTValues.LV, CoverMethods.getItemTransferRatePerSecond(1)));
        registerBehavior(tkcyaId("robotic_arm.mv"), TKCYAMetaItems.ROBOT_ARM_MV, (tile, side) -> new CoverRoboticArmOverhauled(tile, side, GTValues.MV, CoverMethods.getItemTransferRatePerSecond(2)));
        registerBehavior(tkcyaId("robotic_arm.hv"), TKCYAMetaItems.ROBOT_ARM_HV, (tile, side) -> new CoverRoboticArmOverhauled(tile, side, GTValues.HV, CoverMethods.getItemTransferRatePerSecond(3)));
        registerBehavior(tkcyaId("robotic_arm.ev"), TKCYAMetaItems.ROBOT_ARM_EV, (tile, side) -> new CoverRoboticArmOverhauled(tile, side, GTValues.EV, CoverMethods.getItemTransferRatePerSecond(4)));
        registerBehavior(tkcyaId("robotic_arm.iv"), TKCYAMetaItems.ROBOT_ARM_IV, (tile, side) -> new CoverRoboticArmOverhauled(tile, side, GTValues.IV, CoverMethods.getItemTransferRatePerSecond(5)));
        registerBehavior(tkcyaId("robotic_arm.luv"), TKCYAMetaItems.ROBOT_ARM_LuV, (tile, side) -> new CoverRoboticArmOverhauled(tile, side, GTValues.LuV, CoverMethods.getItemTransferRatePerSecond(6)));
        registerBehavior(tkcyaId("robotic_arm.zpm"), TKCYAMetaItems.ROBOT_ARM_ZPM, (tile, side) -> new CoverRoboticArmOverhauled(tile, side, GTValues.ZPM, CoverMethods.getItemTransferRatePerSecond(7)));
        registerBehavior(tkcyaId("robotic_arm.uv"), TKCYAMetaItems.ROBOT_ARM_UV, (tile, side) -> new CoverRoboticArmOverhauled(tile, side, GTValues.UV, CoverMethods.getItemTransferRatePerSecond(8)));

        registerBehavior(tkcyaId("pump.lv"), TKCYAMetaItems.ELECTRIC_PUMP_LV, (tile, side) -> new CoverPumpOverhauled(tile, side, GTValues.LV, CoverMethods.getFluidTransferRatePerSecond(1)));
        registerBehavior(tkcyaId("pump.mv"), TKCYAMetaItems.ELECTRIC_PUMP_MV, (tile, side) -> new CoverPumpOverhauled(tile, side, GTValues.MV, CoverMethods.getFluidTransferRatePerSecond(2)));
        registerBehavior(tkcyaId("pump.hv"), TKCYAMetaItems.ELECTRIC_PUMP_HV, (tile, side) -> new CoverPumpOverhauled(tile, side, GTValues.HV, CoverMethods.getFluidTransferRatePerSecond(3)));
        registerBehavior(tkcyaId("pump.ev"), TKCYAMetaItems.ELECTRIC_PUMP_EV, (tile, side) -> new CoverPumpOverhauled(tile, side, GTValues.EV, CoverMethods.getFluidTransferRatePerSecond(4)));
        registerBehavior(tkcyaId("pump.iv"), TKCYAMetaItems.ELECTRIC_PUMP_IV, (tile, side) -> new CoverPumpOverhauled(tile, side, GTValues.IV, CoverMethods.getFluidTransferRatePerSecond(5)));
        registerBehavior(tkcyaId("pump.luv"), TKCYAMetaItems.ELECTRIC_PUMP_LuV, (tile, side) -> new CoverPumpOverhauled(tile, side, GTValues.LuV, CoverMethods.getFluidTransferRatePerSecond(6)));
        registerBehavior(tkcyaId("pump.zpm"), TKCYAMetaItems.ELECTRIC_PUMP_ZPM, (tile, side) -> new CoverPumpOverhauled(tile, side, GTValues.ZPM, CoverMethods.getFluidTransferRatePerSecond(7)));
        registerBehavior(tkcyaId("pump.uv"), TKCYAMetaItems.ELECTRIC_PUMP_UV, (tile, side) -> new CoverPumpOverhauled(tile, side, GTValues.UV, CoverMethods.getFluidTransferRatePerSecond(8)));

        registerBehavior(tkcyaId("fluid.regulator.lv"), TKCYAMetaItems.FLUID_REGULATOR_LV, (tile, side) -> new CoverFluidRegulatorOverhauled(tile, side, GTValues.LV, CoverMethods.getFluidTransferRatePerSecond(1)));
        registerBehavior(tkcyaId("fluid.regulator.mv"), TKCYAMetaItems.FLUID_REGULATOR_MV, (tile, side) -> new CoverFluidRegulatorOverhauled(tile, side, GTValues.MV, CoverMethods.getFluidTransferRatePerSecond(2)));
        registerBehavior(tkcyaId("fluid.regulator.hv"), TKCYAMetaItems.FLUID_REGULATOR_HV, (tile, side) -> new CoverFluidRegulatorOverhauled(tile, side, GTValues.HV, CoverMethods.getFluidTransferRatePerSecond(3)));
        registerBehavior(tkcyaId("fluid.regulator.ev"), TKCYAMetaItems.FLUID_REGULATOR_EV, (tile, side) -> new CoverFluidRegulatorOverhauled(tile, side, GTValues.EV, CoverMethods.getFluidTransferRatePerSecond(4)));
        registerBehavior(tkcyaId("fluid.regulator.iv"), TKCYAMetaItems.FLUID_REGULATOR_IV, (tile, side) -> new CoverFluidRegulatorOverhauled(tile, side, GTValues.IV, CoverMethods.getFluidTransferRatePerSecond(5)));
        registerBehavior(tkcyaId("fluid.regulator.luv"), TKCYAMetaItems.FLUID_REGULATOR_LUV, (tile, side) -> new CoverFluidRegulatorOverhauled(tile, side, GTValues.LuV, CoverMethods.getFluidTransferRatePerSecond(6)));
        registerBehavior(tkcyaId("fluid.regulator.zpm"), TKCYAMetaItems.FLUID_REGULATOR_ZPM, (tile, side) -> new CoverFluidRegulatorOverhauled(tile, side, GTValues.ZPM, CoverMethods.getFluidTransferRatePerSecond(7)));
        registerBehavior(tkcyaId("fluid.regulator.uv"), TKCYAMetaItems.FLUID_REGULATOR_UV, (tile, side) -> new CoverFluidRegulatorOverhauled(tile, side, GTValues.UV, CoverMethods.getFluidTransferRatePerSecond(8)));




        /*
                registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_BLOCK_STEEL_WRAPPER.getPathln()), COVER_MOLD_BLOCK_STEEL,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_BLOCK_STEEL_WRAPPER));

                        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_BLOCK_CARBON_WRAPPER.getPathln()), COVER_MOLD_BLOCK_CARBON,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_BLOCK_CARBON_WRAPPER));

                        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_BLOCK_TUNGSTEN_CARBIDE_WRAPPER.getPathln()), COVER_MOLD_BLOCK_TUNGSTEN_CARBIDE,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_BLOCK_TUNGSTEN_CARBIDE_WRAPPER));

                        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_BLOCK_CERAMIC_WRAPPER.getPathln()), COVER_MOLD_BLOCK_CERAMIC,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_BLOCK_CERAMIC_WRAPPER));



         */


    }
}
