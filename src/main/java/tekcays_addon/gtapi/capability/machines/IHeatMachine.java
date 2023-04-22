package tekcays_addon.gtapi.capability.machines;

import tekcays_addon.gtapi.capability.containers.IHeatContainer;

public interface IHeatMachine {

    /**
     *
     * @return the heat container of this machine
     */
    IHeatContainer getHeatContainer();
}
