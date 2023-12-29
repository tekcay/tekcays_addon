package tekcays_addon.api.consts;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;

public enum DetectorModes {

    EQUAL,
    HIGHER,
    LOWER;

    public static String showDetectorModeText(DetectorModes detectorModes) {
        return String.format("Detector mode: %s", detectorModes);
    }

    /**
     * Changes the provided {@link DetectorModes} and sends the description of the new set mode.
     * 
     * @param detectorMode the {@link DetectorModes} to change.
     * @param player       the {@link EntityPlayer} in order to send the message
     */
    public static DetectorModes changeDetectModeAndSendMessage(DetectorModes detectorMode, EntityPlayer player) {
        switch (detectorMode) {
            case EQUAL:
                setDetectorModeAndSendMessage(LOWER, player);
                return LOWER;
            case LOWER:
                setDetectorModeAndSendMessage(HIGHER, player);
                return HIGHER;
            case HIGHER:
                setDetectorModeAndSendMessage(EQUAL, player);
                return EQUAL;
            default:
                return HIGHER;
        }
    }

    private static void setDetectorModeAndSendMessage(DetectorModes detectorMode, EntityPlayer player) {
        player.sendMessage(new TextComponentTranslation("tkcya.covers.detectors.mods." + detectorMode.name()));
    }
}
