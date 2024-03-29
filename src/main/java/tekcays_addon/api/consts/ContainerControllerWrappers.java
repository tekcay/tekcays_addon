package tekcays_addon.api.consts;

import static tekcays_addon.api.consts.DetectorWrappers.*;

import gregtech.client.renderer.texture.Textures;
import tekcays_addon.api.detectors.ControllerDetectorWrapper;
import tekcays_addon.gtapi.render.TKCYATextures;

public class ContainerControllerWrappers {

    public static final ControllerDetectorWrapper CONTROL_PRESSURE_DETECTOR = new ControllerDetectorWrapper(
            PRESSURE_DETECTOR_WRAPPER, TKCYATextures.DETECTOR_PRESSURE);
    public static final ControllerDetectorWrapper CONTROL_TEMPERATURE_DETECTOR = new ControllerDetectorWrapper(
            TEMPERATURE_DETECTOR_WRAPPER, TKCYATextures.DETECTOR_TEMPERATURE);
    public static final ControllerDetectorWrapper CONTROL_SPEED_DETECTOR = new ControllerDetectorWrapper(
            SPEED_DETECTOR_WRAPPER, TKCYATextures.DETECTOR_SPEED);
    public static final ControllerDetectorWrapper CONTROL_TORQUE_DETECTOR = new ControllerDetectorWrapper(
            TORQUE_DETECTOR_WRAPPER, Textures.CONVERTER_FE_IN);
    public static final ControllerDetectorWrapper CONTROL_ROTATION_POWER_DETECTOR = new ControllerDetectorWrapper(
            ROTATION_POWER_DETECTOR_WRAPPER, TKCYATextures.DETECTOR_ROTATION_POWER);
    public static final ControllerDetectorWrapper CONTROL_VACUUM_DETECTOR = new ControllerDetectorWrapper(
            VACUUM_DETECTOR_WRAPPER, Textures.CONVERTER_FE_IN);
}
