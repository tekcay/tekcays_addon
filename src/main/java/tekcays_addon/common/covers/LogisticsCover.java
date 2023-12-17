package tekcays_addon.common.covers;

import gregtech.api.capability.IEnergyContainer;
import tekcays_addon.gtapi.capability.containers.LogisticContainer;

public interface LogisticsCover {

    IEnergyContainer getEnergyContainer();
    long getEnergyPerOperation();
    long getMinEnergyNeeded();
    LogisticContainer getLogisticContainer();
    default boolean checkEnergy() {
        return getEnergyContainer() != null && getEnergyContainer().getEnergyStored() >= getMinEnergyNeeded();
    }


}
