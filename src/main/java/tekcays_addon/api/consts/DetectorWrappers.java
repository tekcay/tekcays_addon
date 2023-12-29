package tekcays_addon.api.consts;

import static tekcays_addon.api.consts.MeasureText.*;
import static tekcays_addon.api.consts.UnitSymbol.*;
import static tekcays_addon.gtapi.capability.TKCYATileCapabilities.*;

import tekcays_addon.api.detectors.DetectorWrapper;

public class DetectorWrappers {

    public static final DetectorWrapper PRESSURE_DETECTOR_WRAPPER = DetectorWrapper.builder()
            .unit(BAR)
            .uiTitle("Pressure controller")
            .capability(CAPABILITY_PRESSURE_CONTAINER)
            .currentMeasureText(PRESSURE_MEASURE_TEXT)
            .build();

    public static final DetectorWrapper TEMPERATURE_DETECTOR_WRAPPER = DetectorWrapper.builder()
            .unit(KELVIN)
            .uiTitle("Temperature controller")
            .capability(CAPABILITY_HEAT_CONTAINER)
            .currentMeasureText(TEMPERATURE_MEASURE_TEXT)
            .build();

    public static final DetectorWrapper VACUUM_DETECTOR_WRAPPER = DetectorWrapper.builder()
            .unit(mBAR)
            .uiTitle("Vacuum controller")
            .capability(CAPABILITY_VACUUM_CONTAINER)
            .currentMeasureText(VACUUM_MEASURE_TEXT)
            .build();

    public static final DetectorWrapper SPEED_DETECTOR_WRAPPER = DetectorWrapper.builder()
            .unit(RPM)
            .uiTitle("Speed controller")
            .capability(CAPABILITY_ROTATIONAL_CONTAINER)
            .currentMeasureText(SPEED_MEASURE_TEXT)
            .build();

    public static final DetectorWrapper TORQUE_DETECTOR_WRAPPER = DetectorWrapper.builder()
            .unit(NM)
            .uiTitle("Torque controller")
            .capability(CAPABILITY_ROTATIONAL_CONTAINER)
            .currentMeasureText(TORQUE_MEASURE_TEXT)
            .build();

    public static final DetectorWrapper ROTATION_POWER_DETECTOR_WRAPPER = DetectorWrapper.builder()
            .unit(WATT)
            .uiTitle("Rotation power controller")
            .capability(CAPABILITY_ROTATIONAL_CONTAINER)
            .currentMeasureText(ROTATION_POWER_MEASURE_TEXT)
            .build();
}
