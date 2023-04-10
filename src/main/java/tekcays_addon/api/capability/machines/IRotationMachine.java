package tekcays_addon.api.capability.machines;

import tekcays_addon.api.capability.containers.IRotationContainer;

public interface IRotationMachine {

    /**
     * @return the rotation container of this machine
     */
    IRotationContainer getRotationContainer();
}
