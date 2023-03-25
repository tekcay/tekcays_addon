package tekcays_addon.api.utils;

import gregtech.api.capability.impl.FluidTankList;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.capability.IPressureContainer;

public interface PressureContainerHandler {


    IPressureContainer getPressureContainer();
    int getBaseTransferRate();
    long getPressure();
    FluidTankList importFluidTanks();

/*
    default void applyPressure(FluidStack fluidStack, int transferRate) {
        int toTransfer = Math.min(fluidStack.amount, transferRate);
        int drainAmount = drainImportTank(fluidStack, toTransfer, false);

        if (drainAmount == toTransfer) {
            drainImportTank(fluidStack, toTransfer, true);
            ((IPressureContainer) getPressureContainer()).changeFluidStack(toTransfer, true);
        } else if (drainAmount != 0) {
            drainImportTank(fluidStack, drainAmount, true);
            ((IPressureContainer) getPressureContainer()).changeFluidStack(drainAmount, true);
        }
    }

 */

    default void applyPressure(FluidStack fluidStack, int transferRate) {
        int toTransfer = Math.min(fluidStack.amount, transferRate);

        //if (pressureContainer.getPressurizedFluidName().equals("")) {
        drainImportTank(fluidStack, transferRate, true);
        getPressureContainer().increasePressurizedFluidAmount(toTransfer);
        getPressureContainer().setPressurizedFluidName(fluidStack.getUnlocalizedName());
        //pressureContainer.setFluidStack(new FluidStack(fluidStack.getFluid(), toTransfer));
        //}

        /*
        int drainAmount = drainImportTank(fluidStack, toTransfer, false);

        if (drainAmount == toTransfer) {
            drainImportTank(fluidStack, toTransfer, true);
            pressureContainer.changeFluidStack(toTransfer, true);
        } else if (drainAmount != 0) {
            drainImportTank(fluidStack, drainAmount, true);
            pressureContainer.changeFluidStack(drainAmount, true);
        }

         */
    }


    default int drainImportTank(FluidStack fluidStack, int amount, boolean doDrain) {
        FluidStack fs = importFluidTanks().drain(new FluidStack(fluidStack.getFluid(), amount), doDrain);
        return fs == null ? fluidStack.amount : fs.amount;
    }

    default int getTransferRate() {
        double pressurePercentage = (double) getPressure() / getPressureContainer().getMaxPressure();
        return (int) (getBaseTransferRate() * pressurePercentage);
    }

}

