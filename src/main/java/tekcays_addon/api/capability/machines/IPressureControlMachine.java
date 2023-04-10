package tekcays_addon.api.capability.machines;

import tekcays_addon.api.capability.containers.IPressureControl;

public interface IPressureControlMachine {

    /**
     * @return the pressureControl of this machine
     */
    IPressureControl getPressureControl();
}
