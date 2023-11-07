package tekcays_addon.gtapi.capability.machines;

import tekcays_addon.api.metatileentity.LogicType;
import tekcays_addon.gtapi.capability.containers.IContainerDetector;
import tekcays_addon.gtapi.capability.impl.ContainerDetector;

import javax.annotation.Nullable;

public interface IContainerDetectorMachine {

    /**
     * @return the {@link ContainerDetector} of this machine.
     <br>
     * Will return {@code null} if {@link LogicType#DETECTOR} was not passed as an argument in the MetaTileEntity constructor.
     */
    @Nullable
    IContainerDetector getContainerDetector();
}
