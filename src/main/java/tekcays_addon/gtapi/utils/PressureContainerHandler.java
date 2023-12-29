package tekcays_addon.gtapi.utils;

import net.minecraftforge.fluids.FluidStack;

import gregtech.api.capability.impl.FluidTankList;
import tekcays_addon.gtapi.capability.containers.IPressureContainer;

public interface PressureContainerHandler {

    IPressureContainer getPressureContainer();

    int getBaseTransferRate();

    int getPressure();

    FluidTankList importFluidTanks();

    default int drainImportTank(FluidStack fluidStack, int amount, boolean doDrain) {
        FluidStack fs = importFluidTanks().drain(new FluidStack(fluidStack.getFluid(), amount), doDrain);
        return fs == null ? fluidStack.amount : fs.amount;
    }

    default int getTransferRate() {
        double pressurePercentage = (double) getPressure() / getPressureContainer().getMaxPressure();
        return (int) (getBaseTransferRate() * pressurePercentage);
    }
}
