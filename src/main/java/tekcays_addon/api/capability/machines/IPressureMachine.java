package tekcays_addon.api.capability.machines;

import tekcays_addon.api.capability.containers.IPressureContainer;

public interface IPressureMachine {

    /**
     *
     * @return the pressure container of this machine
     */
    IPressureContainer getPressureContainer();
}
