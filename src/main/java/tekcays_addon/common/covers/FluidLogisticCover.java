package tekcays_addon.common.covers;

import net.minecraftforge.fluids.capability.IFluidHandler;

public interface FluidLogisticCover extends LogisticsCover {

    int superDoTransferFluidsInternal(IFluidHandler myFluidHandler, IFluidHandler fluidHandler, int transferLimit);

    default int overridenDoTransferFluidsInternal(IFluidHandler myFluidHandler, IFluidHandler fluidHandler,
                                                  int transferLimit) {
        int transferedAmount = superDoTransferFluidsInternal(myFluidHandler, fluidHandler, transferLimit);
        if (transferedAmount > 0) getEnergyContainer().removeEnergy(getEnergyPerOperation());
        return transferedAmount;
    }
}
