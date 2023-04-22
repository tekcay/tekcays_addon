package tekcays_addon.gtapi.capability.machines;

import tekcays_addon.gtapi.capability.containers.IPressureContainer;

public interface IPressureMachine {

    /**
     *
     * @return the pressure container of this machine
     */
    IPressureContainer getPressureContainer();
}
