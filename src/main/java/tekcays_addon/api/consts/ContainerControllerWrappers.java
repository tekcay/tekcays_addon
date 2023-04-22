package tekcays_addon.api.consts;

import gregtech.client.renderer.texture.Textures;
import tekcays_addon.api.detectors.ControllerDetectorWrapper;

import static tekcays_addon.api.consts.DetectorWrappers.*;

public class ContainerControllerWrappers {

    public static final ControllerDetectorWrapper CONTROL_PRESSURE_DETECTOR = new ControllerDetectorWrapper(PRESSURE_DETECTOR, Textures.CONVERTER_FE_IN);
    public static final ControllerDetectorWrapper CONTROL_TEMPERATURE_DETECTOR = new ControllerDetectorWrapper(TEMPERATURE_DETECTOR, Textures.CONVERTER_FE_IN);
    public static final ControllerDetectorWrapper CONTROL_SPEED_DETECTOR = new ControllerDetectorWrapper(SPEED_DETECTOR, Textures.CONVERTER_FE_IN);
    public static final ControllerDetectorWrapper CONTROL_TORQUE_DETECTOR = new ControllerDetectorWrapper(TORQUE_DETECTOR, Textures.CONVERTER_FE_IN);
    public static final ControllerDetectorWrapper CONTROL_ROTATION_POWER_DETECTOR = new ControllerDetectorWrapper(ROTATION_POWER_DETECTOR, Textures.CONVERTER_FE_IN);
    public static final ControllerDetectorWrapper CONTROL_VACUUM_DETECTOR = new ControllerDetectorWrapper(VACUUM_DETECTOR, Textures.CONVERTER_FE_IN);
}
