package tekcays_addon.api.capability.containers;

import tekcays_addon.api.consts.DetectorModes;

public interface IPressureControl {

    long getThresholdPressure();
    long getPressure();
    DetectorModes getDetectorMode();

    void setThresholdPressure(long thresholdPressure);
    void setPressure(long pressure);
    void setDetectorMode(DetectorModes detectorModes);


}
