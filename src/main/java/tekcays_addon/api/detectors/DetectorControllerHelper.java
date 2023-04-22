package tekcays_addon.api.detectors;

import gregtech.api.cover.CoverBehavior;
import net.minecraft.util.EnumFacing;
import tekcays_addon.api.consts.DetectorModes;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface DetectorControllerHelper {

    EnumFacing getFrontFacing();
    //Function<ContainerStructuring, Integer> getCurrentValue();

    /**
     *
     * @param detectorMode the current {@link DetectorModes}.
     * @param currentValue the measured value.
     * @param threshold the value to trigger.
     * @param turnRedstoneSignal a function to set the cover to the provided value. Typically, call {@link CoverBehavior#setRedstoneSignalOutput(int)}.
     */
    default void updateRedstoneBehavior(DetectorModes detectorMode, int currentValue, int threshold, Consumer<Integer> turnRedstoneSignal) {

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

    default void updateRedstoneBehavior(DetectorModes detectorMode, int currentValue, int threshold, BiConsumer<EnumFacing, Integer> turnRedstoneSignal) {

        switch (detectorMode) {
            case EQUAL:
                if (currentValue == threshold) turnRedstoneSignal.accept(getFrontFacing(), 12);
                else turnRedstoneSignal.accept(getFrontFacing(), 0);
                break;
            case LOWER:
                if (currentValue < threshold) turnRedstoneSignal.accept(getFrontFacing(), 12);
                else turnRedstoneSignal.accept(getFrontFacing(), 0);
                break;
            case HIGHER:
                if (currentValue > threshold) turnRedstoneSignal.accept(getFrontFacing(), 12);
                else turnRedstoneSignal.accept(getFrontFacing(), 0);
                break;
            default:
                turnRedstoneSignal.accept(getFrontFacing(), 0);
        }
    }
}
