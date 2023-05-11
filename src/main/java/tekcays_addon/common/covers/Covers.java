package tekcays_addon.common.covers;

import net.minecraft.util.ResourceLocation;
import tekcays_addon.TekCaysAddon;
import tekcays_addon.api.detectors.CoverDetectorWrapper;
import tekcays_addon.gtapi.utils.TKCYALog;

import static gregtech.common.covers.CoverBehaviors.registerBehavior;
import static tekcays_addon.api.consts.DetectorWrappers.*;
import static tekcays_addon.api.covers.molds.CoverMoldWrapper.*;
import static tekcays_addon.common.items.TKCYAMetaItems.*;
import static tekcays_addon.gtapi.capability.TKCYATileCapabilities.*;
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
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_BLOCK_CERAMIC_WRAPPER.getPathln()), COVER_MOLD_BLOCK_CERAMIC,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_BLOCK_CERAMIC_WRAPPER));
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
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_BLOCK_STEEL_WRAPPER.getPathln()), COVER_MOLD_BLOCK_STEEL,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_BLOCK_STEEL_WRAPPER));
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
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_BLOCK_TUNGSTEN_CARBIDE_WRAPPER.getPathln()), COVER_MOLD_BLOCK_TUNGSTEN_CARBIDE,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_BLOCK_TUNGSTEN_CARBIDE_WRAPPER));
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
        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, COVER_MOLD_BLOCK_CARBON_WRAPPER.getPathln()), COVER_MOLD_BLOCK_CARBON,
                (coverHolder, attachedSide) -> new CoverMold(coverHolder, attachedSide, COVER_MOLD_BLOCK_CARBON_WRAPPER));



    }
}
