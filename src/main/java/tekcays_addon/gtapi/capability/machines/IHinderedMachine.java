package tekcays_addon.gtapi.capability.machines;

import tekcays_addon.gtapi.capability.containers.IHinderedContainer;

public interface IHinderedMachine {

    /**
     *
     * @return the hindered container of this machine
     */
    IHinderedContainer getHinderedContainer();
}
