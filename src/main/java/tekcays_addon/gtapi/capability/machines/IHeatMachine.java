package tekcays_addon.gtapi.capability.machines;

import org.jetbrains.annotations.Nullable;

import tekcays_addon.api.metatileentity.LogicType;
import tekcays_addon.gtapi.capability.containers.IHeatContainer;
import tekcays_addon.gtapi.capability.impl.HeatContainer;

public interface IHeatMachine {

    /**
     *
     * @return the {@link HeatContainer} of this machine.
     *         <br>
     *         Will return {@code null} if {@link LogicType#HEAT} was not passed as an argument in the MetaTileEntity
     *         constructor.
     */
    @Nullable
    IHeatContainer getHeatContainer();
}
