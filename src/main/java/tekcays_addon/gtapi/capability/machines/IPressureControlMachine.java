package tekcays_addon.gtapi.capability.machines;

import tekcays_addon.gtapi.capability.containers.IContainerDetector;

public interface IPressureControlMachine {

    /**
     * @return the pressureControl of this machine
     */
    IContainerDetector getPressureControl();
}
