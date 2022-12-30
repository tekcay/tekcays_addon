package tekcays_addon.api.utils;

import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import tekcays_addon.api.capability.IPressureContainer;
import tekcays_addon.api.capability.IVacuumContainer;
import tekcays_addon.api.capability.TKCYATileCapabilities;


import static codechicken.lib.util.ClientUtils.getWorld;
import static gregtech.api.unification.material.Materials.Air;
import static tekcays_addon.api.utils.TKCYAValues.MINIMUM_FLUID_STACK_AMOUNT;

public interface IPressureVacuum {

    MetaTileEntity getMetaTileEntity();
    int getPressure();
    IVacuumContainer getPressureContainer();
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

    default void applyPressure(IFluidTank fluidTank, Fluid fluid, int transferRate) {
        int toTransfer = Math.min(fluidTank.getFluidAmount(), transferRate);
        int drainAmount = drainImportTank(fluid, toTransfer, false);

        if (drainAmount == toTransfer) {
            drainImportTank(fluid, toTransfer, true);
            ((IPressureContainer) getPressureContainer()).changeFluidStack(toTransfer, true);
        } else if (drainAmount != 0) {
            drainImportTank(fluid, drainAmount, true);
            ((IPressureContainer) getPressureContainer()).changeFluidStack(drainAmount, true);
        }
    }

    default IPressureContainer getAdjacentIPressureContainer(EnumFacing side) {
        TileEntity te = getWorld().getTileEntity(getMetaTileEntity().getPos().offset(side));
        if (te == null) TKCYALog.logger.info("te == null");
        if (te == null) return null;

        IPressureContainer container = te.getCapability(TKCYATileCapabilities.CAPABILITY_PRESSURE_CONTAINER, side.getOpposite());
        if (container == null) TKCYALog.logger.info("container == null");
        if (container == null) return null;
        //if (!side.equals(getMetaTileEntity().getFrontFacing())) return null;
        return container;
    }

    default IVacuumContainer getAdjacentIVacuumContainer(EnumFacing side) {
        TileEntity te = getWorld().getTileEntity(getMetaTileEntity().getPos().offset(side));
        if (te == null) return null;

        IVacuumContainer container = te.getCapability(TKCYATileCapabilities.CAPABILITY_VACUUM_CONTAINER, side.getOpposite());
        if (container == null) return null;
        if (!side.equals(getMetaTileEntity().getFrontFacing())) return null;
        return container;
    }

    default int fillExportTank(int amount, boolean doFill) {
        return getMetaTileEntity().getExportFluids().fill(Air.getFluid(amount), doFill);
    }

    default int drainImportTank(Fluid fluid, int amount, boolean doDrain) {
        FluidStack fs = getMetaTileEntity().getImportFluids().drain(new FluidStack(fluid, amount), doDrain);
        return fs == null ? amount : fs.amount;
    }

    default int getTransferRate() {
        double pressurePercentage = (double) getPressure() / getPressureContainer().getMaxPressure();
        return (int) (getBaseTransferRate() * pressurePercentage);
    }

}
