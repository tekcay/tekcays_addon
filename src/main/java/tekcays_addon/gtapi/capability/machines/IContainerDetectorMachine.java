package tekcays_addon.gtapi.capability.machines;

import tekcays_addon.gtapi.capability.containers.IContainerDetector;
import tekcays_addon.gtapi.capability.impl.ContainerDetector;

public interface IContainerDetectorMachine {

    /**
     * @return the {@link ContainerDetector} of this machine.
     */
    IContainerDetector getContainerDetector();
}
