package tekcays_addon.common.covers;

import net.minecraft.util.ResourceLocation;
import tekcays_addon.TekCaysAddon;
import tekcays_addon.api.covers.CoverDetectorWrapper;
import tekcays_addon.gtapi.utils.TKCYALog;
import tekcays_addon.common.items.TKCYAMetaItems;

import static gregtech.common.covers.CoverBehaviors.registerBehavior;
import static tekcays_addon.api.consts.UnitSymbol.*;
import static tekcays_addon.gtapi.capability.TKCYATileCapabilities.*;
import static tekcays_addon.gtapi.render.TKCYATextures.*;

public class Covers {

    public static void init() {
        TKCYALog.logger.info("Registering cover behaviors...");

        //registerBehavior(new ResourceLocation(TekCaysAddon.MODID, "temperature_detector"), TKCYAMetaItems.COVER_TEMPERATURE_DETECTOR, CoverDetectorTemperature::new);

        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, "temperature_detector"), TKCYAMetaItems.COVER_TEMPERATURE_DETECTOR,
                (coverHolder, attachedSide) -> new CoverDetector(coverHolder, attachedSide, CoverDetectorWrapper.builder()
                        .unit(KELVIN)
                        .textures(DETECTOR_TEMPERATURE)
                        .capability(CAPABILITY_HEAT_CONTAINER)
                        .valueRetrievingFunction((holder, side) -> holder.getCapability(CAPABILITY_HEAT_CONTAINER, side).getTemperature())
                        .uiTitle("Temperature detector")
                        .currentMeasureText("Current temperature: %d %s")
                        .build())
        );

        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, "speed_detector"), TKCYAMetaItems.COVER_SPEED_DETECTOR,
                (coverHolder, attachedSide) -> new CoverDetector(coverHolder, attachedSide, CoverDetectorWrapper.builder()
                        .unit(RPM)
                        .textures(DETECTOR_SPEED)
                        .capability(CAPABILITY_ROTATIONAL_CONTAINER)
                        .valueRetrievingFunction((holder, side) -> holder.getCapability(CAPABILITY_ROTATIONAL_CONTAINER, side).getSpeed())
                        .uiTitle("Speed detector")
                        .currentMeasureText("Current speed: %d %s")
                        .build())
        );

        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, "torque_detector"), TKCYAMetaItems.COVER_TORQUE_DETECTOR,
                (coverHolder, attachedSide) -> new CoverDetector(coverHolder, attachedSide, CoverDetectorWrapper.builder()
                        .unit(NM)
                        .textures(DETECTOR_TORQUE)
                        .capability(CAPABILITY_ROTATIONAL_CONTAINER)
                        .valueRetrievingFunction((holder, side) -> holder.getCapability(CAPABILITY_ROTATIONAL_CONTAINER, side).getTorque())
                        .uiTitle("Torque detector")
                        .currentMeasureText("Current torque: %d %s")
                        .build())
        );

        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, "rotation_power_detector"), TKCYAMetaItems.COVER_ROTATION_POWER_DETECTOR,
                (coverHolder, attachedSide) -> new CoverDetector(coverHolder, attachedSide, CoverDetectorWrapper.builder()
                        .unit(WATT)
                        .textures(DETECTOR_ROTATION_POWER)
                        .capability(CAPABILITY_ROTATIONAL_CONTAINER)
                        .valueRetrievingFunction((holder, side) -> holder.getCapability(CAPABILITY_ROTATIONAL_CONTAINER, side).getPower())
                        .uiTitle("Rotation power detector")
                        .currentMeasureText("Current rotation power: %d %s")
                        .build())
        );

        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, "pressure_detector"), TKCYAMetaItems.COVER_PRESSURE_DETECTOR,
                (coverHolder, attachedSide) -> new CoverDetector(coverHolder, attachedSide, CoverDetectorWrapper.builder()
                        .unit(BAR)
                        .textures(DETECTOR_PRESSURE)
                        .capability(CAPABILITY_PRESSURE_CONTAINER)
                        .valueRetrievingFunction((holder, side) -> holder.getCapability(CAPABILITY_PRESSURE_CONTAINER, side).getPressure())
                        .uiTitle("Pressure detector")
                        .currentMeasureText("Current pressure: %d %s")
                        .build())
        );

        registerBehavior(new ResourceLocation(TekCaysAddon.MODID, "vacuum_detector"), TKCYAMetaItems.COVER_PRESSURE_DETECTOR,
                (coverHolder, attachedSide) -> new CoverDetector(coverHolder, attachedSide, CoverDetectorWrapper.builder()
                        .unit(mBAR)
                        .textures(DETECTOR_VACUUM)
                        .capability(CAPABILITY_PRESSURE_CONTAINER)
                        .valueRetrievingFunction((holder, side) -> holder.getCapability(CAPABILITY_PRESSURE_CONTAINER, side).getPressure())
                        .uiTitle("Pressure detector")
                        .currentMeasureText("Current pressure: %d %s")
                        .build())
        );


    }
}
