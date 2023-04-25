package tekcays_addon.gtapi.capability.containers;

import tekcays_addon.api.consts.DetectorModes;

public interface IContainerDetector {

    int getThreshold();
    int getCurrentValue();
    DetectorModes getDetectorMode();

    void setThreshold(int threshold);
    void setCurrentValue(int value);
    void setDetectorMode(DetectorModes detectorModes);


}
