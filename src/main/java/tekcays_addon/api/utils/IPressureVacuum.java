package tekcays_addon.api.utils;

import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.capability.containers.IPressureContainer;
import tekcays_addon.api.capability.containers.IVacuumContainer;
import tekcays_addon.api.capability.TKCYATileCapabilities;


import static codechicken.lib.util.ClientUtils.getWorld;
import static gregtech.api.unification.material.Materials.Air;
import static tekcays_addon.api.utils.TKCYAValues.MINIMUM_FLUID_STACK_AMOUNT;

public interface IPressureVacuum<T extends IVacuumContainer> {

    MetaTileEntity getMetaTileEntity();
    int getPressure();
    T getPressureContainer();
    int getBaseTransferRate();

    default void applyVacuum(int transferRate) {
        int toTransfer = Math.min(getPressureContainer().getAirAmount() - MINIMUM_FLUID_STACK_AMOUNT, transferRate);
        int fillAmount = fillExportTank(toTransfer, false);

        if (fillAmount == toTransfer) {
            fillExportTank(toTransfer, true);
            getPressureContainer().changeAirFluidStack(transferRate, false);
        } else if (fillAmount != 0) {
            fillExportTank(fillAmount, true);
            getPressureContainer().changeAirFluidStack(fillAmount, false);
        }
    }

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


    default IPressureContainer getAdjacentIPressureContainer(EnumFacing outputSide) {
        BlockPos blockPos = getMetaTileEntity().getPos().offset(outputSide);
        TileEntity te;
        IPressureContainer container;
        try {
            te = getWorld().getTileEntity(blockPos);
            container = te.getCapability(TKCYATileCapabilities.CAPABILITY_PRESSURE_CONTAINER, outputSide.getOpposite());
        } catch (NullPointerException e) {
            return null;
        }
        return container;
    }

    default IVacuumContainer getAdjacentIVacuumContainer(EnumFacing outputSide) {
        BlockPos blockPos = getMetaTileEntity().getPos().offset(outputSide);
        TileEntity te;
        IVacuumContainer container;
        try {
            te = getWorld().getTileEntity(blockPos);
            container = te.getCapability(TKCYATileCapabilities.CAPABILITY_VACUUM_CONTAINER, outputSide.getOpposite());
        } catch (NullPointerException e) {
            return null;
        }

        return container;
    }

    default int fillExportTank(int amount, boolean doFill) {
        return getMetaTileEntity().getExportFluids().fill(Air.getFluid(amount), doFill);
    }

    default int drainImportTank(FluidStack fluidStack, int amount, boolean doDrain) {
        FluidStack fs = getMetaTileEntity().getImportFluids().drain(new FluidStack(fluidStack.getFluid(), amount), doDrain);
        return fs == null ? fluidStack.amount : fs.amount;
    }

    default int getTransferRate() {
        double pressurePercentage = (double) getPressure() / getPressureContainer().getMaxPressure();
        return (int) (getBaseTransferRate() * pressurePercentage);
    }

}
