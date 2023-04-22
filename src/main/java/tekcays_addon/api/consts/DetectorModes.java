package tekcays_addon.api.consts;

import gregtech.api.cover.CoverBehavior;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.function.Consumer;

public enum DetectorModes {

    EQUAL,
    HIGHER,
    LOWER;

    public static String showDetectorModeText(DetectorModes detectorModes) {
        return String.format("Detector mode: %s", detectorModes);
    }

    /**
     * Changes the provided {@link DetectorModes} and sends the description of the new set mode.
     * @param detectorMode the {@link DetectorModes} to change.
     * @param player the {@link EntityPlayer} in order to send the message
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
            default: return HIGHER;
        }
    }

    private static void setDetectorModeAndSendMessage(DetectorModes detectorMode, EntityPlayer player) {
        player.sendMessage(new TextComponentTranslation("tkcya.covers.detectors.mods." + detectorMode.name()));
    }

    /**
     *
     * @param detectorMode the current {@link DetectorModes}.
     * @param currentValue the measured value.
     * @param threshold the value to trigger.
     * @param turnRedstoneSignal a function to set the cover to the provided value. Typically, call {@link CoverBehavior#setRedstoneSignalOutput(int)}.
     */
    public static void updateRedstoneBehavior(DetectorModes detectorMode, int currentValue, int threshold, Consumer<Integer> turnRedstoneSignal) {

        switch (detectorMode) {
            case EQUAL:
                if (currentValue == threshold) turnRedstoneSignal.accept(12);
                else turnRedstoneSignal.accept(0);
                break;
            case LOWER:
                if (currentValue < threshold) turnRedstoneSignal.accept(12);
                else turnRedstoneSignal.accept(0);
                break;
            case HIGHER:
                if (currentValue > threshold) turnRedstoneSignal.accept(12);
                else turnRedstoneSignal.accept(0);
                break;
            default:
                turnRedstoneSignal.accept(0);
        }
    }
}
