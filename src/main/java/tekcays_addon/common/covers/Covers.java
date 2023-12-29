package tekcays_addon.common.covers;

import static gregtech.common.covers.CoverBehaviors.registerBehavior;
import static tekcays_addon.api.consts.DetectorWrappers.*;
import static tekcays_addon.common.items.TKCYAMetaItems.*;
import static tekcays_addon.gtapi.capability.TKCYATileCapabilities.*;
import static tekcays_addon.gtapi.consts.TKCYAValues.tkcyaId;
import static tekcays_addon.gtapi.render.TKCYATextures.*;

import java.util.Objects;

import net.minecraft.util.ResourceLocation;

import gregtech.api.GTValues;
import tekcays_addon.TekCaysAddon;
import tekcays_addon.api.covers.CoverMethods;
import tekcays_addon.api.detectors.CoverDetectorWrapper;
import tekcays_addon.common.items.TKCYAMetaItems;
import tekcays_addon.gtapi.utils.TKCYALog;

public class Covers {

    public static void init() {
        TKCYALog.logger.info("Registering cover behaviors...");
        TKCYALog.logger.info("Registering detector covers...");

        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, "temperature_detector"), COVER_TEMPERATURE_DETECTOR,
                (def, tile, side) -> new CoverDetector(def, tile, side,
                        new CoverDetectorWrapper(TEMPERATURE_DETECTOR_WRAPPER, DETECTOR_TEMPERATURE,
                                (holderTile, attachedSide) -> Objects
                                        .requireNonNull(
                                                holderTile.getCapability(CAPABILITY_HEAT_CONTAINER, attachedSide))
                                        .getTemperature())));

        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, "speed_detector"), COVER_SPEED_DETECTOR,
                (def, tile, side) -> new CoverDetector(def, tile, side,
                        new CoverDetectorWrapper(SPEED_DETECTOR_WRAPPER, DETECTOR_SPEED,
                                (holderTile, attachedSide) -> Objects
                                        .requireNonNull(
                                                holderTile.getCapability(CAPABILITY_ROTATIONAL_CONTAINER, attachedSide))
                                        .getSpeed())));

        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, "torque_detector"), COVER_TORQUE_DETECTOR,
                (def, tile, side) -> new CoverDetector(def, tile, side,
                        new CoverDetectorWrapper(TORQUE_DETECTOR_WRAPPER, DETECTOR_TORQUE,
                                (holderTile, attachedSide) -> Objects
                                        .requireNonNull(
                                                holderTile.getCapability(CAPABILITY_ROTATIONAL_CONTAINER, attachedSide))
                                        .getTorque())));

        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, "rotation_power_detector"),
                COVER_ROTATION_POWER_DETECTOR,
                (def, tile, side) -> new CoverDetector(def, tile, side,
                        new CoverDetectorWrapper(ROTATION_POWER_DETECTOR_WRAPPER, DETECTOR_ROTATION_POWER,
                                (holderTile, attachedSide) -> Objects
                                        .requireNonNull(
                                                holderTile.getCapability(CAPABILITY_ROTATIONAL_CONTAINER, attachedSide))
                                        .getPower())));

        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, "pressure_detector"), COVER_PRESSURE_DETECTOR,
                (def, tile, side) -> new CoverDetector(def, tile, side,
                        new CoverDetectorWrapper(PRESSURE_DETECTOR_WRAPPER, DETECTOR_PRESSURE,
                                (holderTile, attachedSide) -> Objects
                                        .requireNonNull(
                                                holderTile.getCapability(CAPABILITY_PRESSURE_CONTAINER, attachedSide))
                                        .getPressure())));

        TKCYALog.logger.info("Registering mold covers...");

        /*
         * 
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_INGOT_CERAMIC_WRAPPER.getPathln()),
         * COVER_MOLD_INGOT_CERAMIC,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_INGOT_CERAMIC_WRAPPER));
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_PLATE_CERAMIC_WRAPPER.getPathln()),
         * COVER_MOLD_PLATE_CERAMIC,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_PLATE_CERAMIC_WRAPPER));
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_STICK_CERAMIC_WRAPPER.getPathln()),
         * COVER_MOLD_STICK_CERAMIC,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_STICK_CERAMIC_WRAPPER));
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_STICK_LONG_CERAMIC_WRAPPER.getPathln()),
         * COVER_MOLD_STICK_LONG_CERAMIC,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide,
         * COVER_MOLD_STICK_LONG_CERAMIC_WRAPPER));
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_GEAR_CERAMIC_WRAPPER.getPathln()),
         * COVER_MOLD_GEAR_CERAMIC,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_GEAR_CERAMIC_WRAPPER));
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_GEAR_SMALL_CERAMIC_WRAPPER.getPathln()),
         * COVER_MOLD_GEAR_SMALL_CERAMIC,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide,
         * COVER_MOLD_GEAR_SMALL_CERAMIC_WRAPPER));
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_BOLT_CERAMIC_WRAPPER.getPathln()),
         * COVER_MOLD_BOLT_CERAMIC,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_BOLT_CERAMIC_WRAPPER));
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_RING_CERAMIC_WRAPPER.getPathln()),
         * COVER_MOLD_RING_CERAMIC,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_RING_CERAMIC_WRAPPER));
         * 
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_INGOT_STEEL_WRAPPER.getPathln()),
         * COVER_MOLD_INGOT_STEEL,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_INGOT_STEEL_WRAPPER));
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_PLATE_STEEL_WRAPPER.getPathln()),
         * COVER_MOLD_PLATE_STEEL,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_PLATE_STEEL_WRAPPER));
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_STICK_STEEL_WRAPPER.getPathln()),
         * COVER_MOLD_STICK_STEEL,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_STICK_STEEL_WRAPPER));
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_STICK_LONG_STEEL_WRAPPER.getPathln()),
         * COVER_MOLD_STICK_LONG_STEEL,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide,
         * COVER_MOLD_STICK_LONG_STEEL_WRAPPER));
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_GEAR_STEEL_WRAPPER.getPathln()),
         * COVER_MOLD_GEAR_STEEL,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_GEAR_STEEL_WRAPPER));
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_GEAR_SMALL_STEEL_WRAPPER.getPathln()),
         * COVER_MOLD_GEAR_SMALL_STEEL,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide,
         * COVER_MOLD_GEAR_SMALL_STEEL_WRAPPER));
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_BOLT_STEEL_WRAPPER.getPathln()),
         * COVER_MOLD_BOLT_STEEL,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_BOLT_STEEL_WRAPPER));
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_RING_STEEL_WRAPPER.getPathln()),
         * COVER_MOLD_RING_STEEL,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_RING_STEEL_WRAPPER));
         * 
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID,
         * COVER_MOLD_INGOT_TUNGSTEN_CARBIDE_WRAPPER.getPathln()), COVER_MOLD_INGOT_TUNGSTEN_CARBIDE,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide,
         * COVER_MOLD_INGOT_TUNGSTEN_CARBIDE_WRAPPER));
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID,
         * COVER_MOLD_PLATE_TUNGSTEN_CARBIDE_WRAPPER.getPathln()), COVER_MOLD_PLATE_TUNGSTEN_CARBIDE,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide,
         * COVER_MOLD_PLATE_TUNGSTEN_CARBIDE_WRAPPER));
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID,
         * COVER_MOLD_STICK_TUNGSTEN_CARBIDE_WRAPPER.getPathln()), COVER_MOLD_STICK_TUNGSTEN_CARBIDE,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide,
         * COVER_MOLD_STICK_TUNGSTEN_CARBIDE_WRAPPER));
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID,
         * COVER_MOLD_STICK_LONG_TUNGSTEN_CARBIDE_WRAPPER.getPathln()), COVER_MOLD_STICK_LONG_TUNGSTEN_CARBIDE,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide,
         * COVER_MOLD_STICK_LONG_TUNGSTEN_CARBIDE_WRAPPER));
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID,
         * COVER_MOLD_GEAR_TUNGSTEN_CARBIDE_WRAPPER.getPathln()), COVER_MOLD_GEAR_TUNGSTEN_CARBIDE,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide,
         * COVER_MOLD_GEAR_TUNGSTEN_CARBIDE_WRAPPER));
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID,
         * COVER_MOLD_GEAR_SMALL_TUNGSTEN_CARBIDE_WRAPPER.getPathln()), COVER_MOLD_GEAR_SMALL_TUNGSTEN_CARBIDE,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide,
         * COVER_MOLD_GEAR_SMALL_TUNGSTEN_CARBIDE_WRAPPER));
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID,
         * COVER_MOLD_BOLT_TUNGSTEN_CARBIDE_WRAPPER.getPathln()), COVER_MOLD_BOLT_TUNGSTEN_CARBIDE,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide,
         * COVER_MOLD_BOLT_TUNGSTEN_CARBIDE_WRAPPER));
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID,
         * COVER_MOLD_RING_TUNGSTEN_CARBIDE_WRAPPER.getPathln()), COVER_MOLD_RING_TUNGSTEN_CARBIDE,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide,
         * COVER_MOLD_RING_TUNGSTEN_CARBIDE_WRAPPER));
         * 
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_INGOT_CARBON_WRAPPER.getPathln()),
         * COVER_MOLD_INGOT_CARBON,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_INGOT_CARBON_WRAPPER));
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_PLATE_CARBON_WRAPPER.getPathln()),
         * COVER_MOLD_PLATE_CARBON,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_PLATE_CARBON_WRAPPER));
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_STICK_CARBON_WRAPPER.getPathln()),
         * COVER_MOLD_STICK_CARBON,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_STICK_CARBON_WRAPPER));
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_STICK_LONG_CARBON_WRAPPER.getPathln()),
         * COVER_MOLD_STICK_LONG_CARBON,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide,
         * COVER_MOLD_STICK_LONG_CARBON_WRAPPER));
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_GEAR_CARBON_WRAPPER.getPathln()),
         * COVER_MOLD_GEAR_CARBON,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_GEAR_CARBON_WRAPPER));
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_GEAR_SMALL_CARBON_WRAPPER.getPathln()),
         * COVER_MOLD_GEAR_SMALL_CARBON,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide,
         * COVER_MOLD_GEAR_SMALL_CARBON_WRAPPER));
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_BOLT_CARBON_WRAPPER.getPathln()),
         * COVER_MOLD_BOLT_CARBON,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_BOLT_CARBON_WRAPPER));
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_RING_CARBON_WRAPPER.getPathln()),
         * COVER_MOLD_RING_CARBON,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_RING_CARBON_WRAPPER));
         * 
         */

        TKCYALog.logger.info("Registering GTCEu logistic covers...");

        registerBehavior(tkcyaId("conveyor.lv"), TKCYAMetaItems.CONVEYOR_MODULE_LV,
                (def, tile, side) -> new CoverConveyorOverhauled(def, tile, side, GTValues.LV,
                        CoverMethods.getItemTransferRatePerSecond(1)));
        registerBehavior(tkcyaId("conveyor.mv"), TKCYAMetaItems.CONVEYOR_MODULE_MV,
                (def, tile, side) -> new CoverConveyorOverhauled(def, tile, side, GTValues.MV,
                        CoverMethods.getItemTransferRatePerSecond(2)));
        registerBehavior(tkcyaId("conveyor.hv"), TKCYAMetaItems.CONVEYOR_MODULE_HV,
                (def, tile, side) -> new CoverConveyorOverhauled(def, tile, side, GTValues.HV,
                        CoverMethods.getItemTransferRatePerSecond(3)));
        registerBehavior(tkcyaId("conveyor.ev"), TKCYAMetaItems.CONVEYOR_MODULE_EV,
                (def, tile, side) -> new CoverConveyorOverhauled(def, tile, side, GTValues.EV,
                        CoverMethods.getItemTransferRatePerSecond(4)));
        registerBehavior(tkcyaId("conveyor.iv"), TKCYAMetaItems.CONVEYOR_MODULE_IV,
                (def, tile, side) -> new CoverConveyorOverhauled(def, tile, side, GTValues.IV,
                        CoverMethods.getItemTransferRatePerSecond(5)));
        registerBehavior(tkcyaId("conveyor.luv"), TKCYAMetaItems.CONVEYOR_MODULE_LuV,
                (def, tile, side) -> new CoverConveyorOverhauled(def, tile, side, GTValues.LuV,
                        CoverMethods.getItemTransferRatePerSecond(6)));
        registerBehavior(tkcyaId("conveyor.zpm"), TKCYAMetaItems.CONVEYOR_MODULE_ZPM,
                (def, tile, side) -> new CoverConveyorOverhauled(def, tile, side, GTValues.ZPM,
                        CoverMethods.getItemTransferRatePerSecond(7)));
        registerBehavior(tkcyaId("conveyor.uv"), TKCYAMetaItems.CONVEYOR_MODULE_UV,
                (def, tile, side) -> new CoverConveyorOverhauled(def, tile, side, GTValues.UV,
                        CoverMethods.getItemTransferRatePerSecond(8)));

        registerBehavior(tkcyaId("robotic_arm.lv"), TKCYAMetaItems.ROBOT_ARM_LV,
                (def, tile, side) -> new CoverRoboticArmOverhauled(def, tile, side, GTValues.LV,
                        CoverMethods.getItemTransferRatePerSecond(1)));
        registerBehavior(tkcyaId("robotic_arm.mv"), TKCYAMetaItems.ROBOT_ARM_MV,
                (def, tile, side) -> new CoverRoboticArmOverhauled(def, tile, side, GTValues.MV,
                        CoverMethods.getItemTransferRatePerSecond(2)));
        registerBehavior(tkcyaId("robotic_arm.hv"), TKCYAMetaItems.ROBOT_ARM_HV,
                (def, tile, side) -> new CoverRoboticArmOverhauled(def, tile, side, GTValues.HV,
                        CoverMethods.getItemTransferRatePerSecond(3)));
        registerBehavior(tkcyaId("robotic_arm.ev"), TKCYAMetaItems.ROBOT_ARM_EV,
                (def, tile, side) -> new CoverRoboticArmOverhauled(def, tile, side, GTValues.EV,
                        CoverMethods.getItemTransferRatePerSecond(4)));
        registerBehavior(tkcyaId("robotic_arm.iv"), TKCYAMetaItems.ROBOT_ARM_IV,
                (def, tile, side) -> new CoverRoboticArmOverhauled(def, tile, side, GTValues.IV,
                        CoverMethods.getItemTransferRatePerSecond(5)));
        registerBehavior(tkcyaId("robotic_arm.luv"), TKCYAMetaItems.ROBOT_ARM_LuV,
                (def, tile, side) -> new CoverRoboticArmOverhauled(def, tile, side, GTValues.LuV,
                        CoverMethods.getItemTransferRatePerSecond(6)));
        registerBehavior(tkcyaId("robotic_arm.zpm"), TKCYAMetaItems.ROBOT_ARM_ZPM,
                (def, tile, side) -> new CoverRoboticArmOverhauled(def, tile, side, GTValues.ZPM,
                        CoverMethods.getItemTransferRatePerSecond(7)));
        registerBehavior(tkcyaId("robotic_arm.uv"), TKCYAMetaItems.ROBOT_ARM_UV,
                (def, tile, side) -> new CoverRoboticArmOverhauled(def, tile, side, GTValues.UV,
                        CoverMethods.getItemTransferRatePerSecond(8)));

        registerBehavior(tkcyaId("pump.lv"), TKCYAMetaItems.ELECTRIC_PUMP_LV,
                (def, tile, side) -> new CoverPumpOverhauled(def, tile, side, GTValues.LV,
                        CoverMethods.getFluidTransferRatePerSecond(1)));
        registerBehavior(tkcyaId("pump.mv"), TKCYAMetaItems.ELECTRIC_PUMP_MV,
                (def, tile, side) -> new CoverPumpOverhauled(def, tile, side, GTValues.MV,
                        CoverMethods.getFluidTransferRatePerSecond(2)));
        registerBehavior(tkcyaId("pump.hv"), TKCYAMetaItems.ELECTRIC_PUMP_HV,
                (def, tile, side) -> new CoverPumpOverhauled(def, tile, side, GTValues.HV,
                        CoverMethods.getFluidTransferRatePerSecond(3)));
        registerBehavior(tkcyaId("pump.ev"), TKCYAMetaItems.ELECTRIC_PUMP_EV,
                (def, tile, side) -> new CoverPumpOverhauled(def, tile, side, GTValues.EV,
                        CoverMethods.getFluidTransferRatePerSecond(4)));
        registerBehavior(tkcyaId("pump.iv"), TKCYAMetaItems.ELECTRIC_PUMP_IV,
                (def, tile, side) -> new CoverPumpOverhauled(def, tile, side, GTValues.IV,
                        CoverMethods.getFluidTransferRatePerSecond(5)));
        registerBehavior(tkcyaId("pump.luv"), TKCYAMetaItems.ELECTRIC_PUMP_LuV,
                (def, tile, side) -> new CoverPumpOverhauled(def, tile, side, GTValues.LuV,
                        CoverMethods.getFluidTransferRatePerSecond(6)));
        registerBehavior(tkcyaId("pump.zpm"), TKCYAMetaItems.ELECTRIC_PUMP_ZPM,
                (def, tile, side) -> new CoverPumpOverhauled(def, tile, side, GTValues.ZPM,
                        CoverMethods.getFluidTransferRatePerSecond(7)));
        registerBehavior(tkcyaId("pump.uv"), TKCYAMetaItems.ELECTRIC_PUMP_UV,
                (def, tile, side) -> new CoverPumpOverhauled(def, tile, side, GTValues.UV,
                        CoverMethods.getFluidTransferRatePerSecond(8)));

        registerBehavior(tkcyaId("fluid.regulator.lv"), TKCYAMetaItems.FLUID_REGULATOR_LV,
                (def, tile, side) -> new CoverFluidRegulatorOverhauled(def, tile, side, GTValues.LV,
                        CoverMethods.getFluidTransferRatePerSecond(1)));
        registerBehavior(tkcyaId("fluid.regulator.mv"), TKCYAMetaItems.FLUID_REGULATOR_MV,
                (def, tile, side) -> new CoverFluidRegulatorOverhauled(def, tile, side, GTValues.MV,
                        CoverMethods.getFluidTransferRatePerSecond(2)));
        registerBehavior(tkcyaId("fluid.regulator.hv"), TKCYAMetaItems.FLUID_REGULATOR_HV,
                (def, tile, side) -> new CoverFluidRegulatorOverhauled(def, tile, side, GTValues.HV,
                        CoverMethods.getFluidTransferRatePerSecond(3)));
        registerBehavior(tkcyaId("fluid.regulator.ev"), TKCYAMetaItems.FLUID_REGULATOR_EV,
                (def, tile, side) -> new CoverFluidRegulatorOverhauled(def, tile, side, GTValues.EV,
                        CoverMethods.getFluidTransferRatePerSecond(4)));
        registerBehavior(tkcyaId("fluid.regulator.iv"), TKCYAMetaItems.FLUID_REGULATOR_IV,
                (def, tile, side) -> new CoverFluidRegulatorOverhauled(def, tile, side, GTValues.IV,
                        CoverMethods.getFluidTransferRatePerSecond(5)));
        registerBehavior(tkcyaId("fluid.regulator.luv"), TKCYAMetaItems.FLUID_REGULATOR_LUV,
                (def, tile, side) -> new CoverFluidRegulatorOverhauled(def, tile, side, GTValues.LuV,
                        CoverMethods.getFluidTransferRatePerSecond(6)));
        registerBehavior(tkcyaId("fluid.regulator.zpm"), TKCYAMetaItems.FLUID_REGULATOR_ZPM,
                (def, tile, side) -> new CoverFluidRegulatorOverhauled(def, tile, side, GTValues.ZPM,
                        CoverMethods.getFluidTransferRatePerSecond(7)));
        registerBehavior(tkcyaId("fluid.regulator.uv"), TKCYAMetaItems.FLUID_REGULATOR_UV,
                (def, tile, side) -> new CoverFluidRegulatorOverhauled(def, tile, side, GTValues.UV,
                        CoverMethods.getFluidTransferRatePerSecond(8)));

        /*
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_BLOCK_STEEL_WRAPPER.getPathln()),
         * COVER_MOLD_BLOCK_STEEL,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_BLOCK_STEEL_WRAPPER));
         * 
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_BLOCK_CARBON_WRAPPER.getPathln()),
         * COVER_MOLD_BLOCK_CARBON,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_BLOCK_CARBON_WRAPPER));
         * 
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID,
         * COVER_MOLD_BLOCK_TUNGSTEN_CARBIDE_WRAPPER.getPathln()), COVER_MOLD_BLOCK_TUNGSTEN_CARBIDE,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide,
         * COVER_MOLD_BLOCK_TUNGSTEN_CARBIDE_WRAPPER));
         * 
         * registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_BLOCK_CERAMIC_WRAPPER.getPathln()),
         * COVER_MOLD_BLOCK_CERAMIC,
         * (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_BLOCK_CERAMIC_WRAPPER));
         * 
         * 
         * 
         */
    }
}
