package tekcays_addon.gtapi.capability.machines;

import tekcays_addon.api.metatileentity.LogicType;
import tekcays_addon.gtapi.capability.containers.IPressureContainer;
import tekcays_addon.gtapi.capability.impl.PressureContainer;

import javax.annotation.Nullable;

public interface IPressureMachine {

    /**
     *
     * @return the {@link PressureContainer} of this machine.
     * <br>
     * Will return {@code null} if {@link LogicType#PRESSURE} was not passed as an argument in the MetaTileEntity constructor.
     */
    @Nullable
    IPressureContainer getPressureContainer();
}
