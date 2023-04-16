package tekcays_addon.gtapi.capability.containers;

import tekcays_addon.api.consts.DetectorModes;

public interface IPressureControl {

    int getThresholdPressure();
    int getPressure();
    DetectorModes getDetectorMode();

    void setThresholdPressure(int thresholdPressure);
    void setPressure(int pressure);
    void setDetectorMode(DetectorModes detectorModes);


}
