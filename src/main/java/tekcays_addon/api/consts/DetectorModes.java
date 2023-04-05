package tekcays_addon.api.consts;

public enum DetectorModes {

    EQUAL,
    HIGHER,
    LOWER;

    public static String showDetectorModeText(DetectorModes detectorModes) {
        return String.format("Detector mode: %s", detectorModes);
    }

}
