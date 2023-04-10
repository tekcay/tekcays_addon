package tekcays_addon.api.capability.machines;

import tekcays_addon.api.capability.containers.IHeatContainer;

public interface IHeatMachine {

    /**
     *
     * @return the heat container of this machine
     */
    IHeatContainer getHeatContainer();
}
