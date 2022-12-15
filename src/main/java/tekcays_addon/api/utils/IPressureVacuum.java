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

public interface IPressureVacuum {

    default void applyVacuum(IVacuumContainer pressureContainer, int transferRate) {
        int toTransfer = Math.min(pressureContainer.getAirAmount(), transferRate);
        int fillAmount = fillExportTank(toTransfer, false);

        if (fillAmount == toTransfer) {
            fillExportTank(toTransfer, true);
            pressureContainer.changeAirFluidStack(transferRate, false);
        } else if (fillAmount != 0) {
            fillExportTank(fillAmount, true);
            pressureContainer.changeAirFluidStack(fillAmount, false);
        }
    }

    default void applyPressure(IFluidTank fluidTank, Fluid fluid, IPressureContainer pressureContainer, int transferRate) {
        int toTransfer = Math.min(fluidTank.getFluidAmount(), transferRate);
        int drainAmount = drainImportTank(fluid, toTransfer, false);

        if (drainAmount == toTransfer) {
            drainImportTank(fluid, toTransfer, true);
            pressureContainer.changeFluidStack(toTransfer, true);
        } else if (drainAmount != 0) {
            drainImportTank(fluid, drainAmount, true);
            pressureContainer.changeFluidStack(drainAmount, true);
        }
    }

    default IPressureContainer getAdjacentIPressureContainer(EnumFacing side, MetaTileEntity currentMTE) {
        TileEntity te = getWorld().getTileEntity(currentMTE.getPos().offset(side));
        if (te == null) return null;

        IPressureContainer container = te.getCapability(TKCYATileCapabilities.CAPABILITY_PRESSURE_CONTAINER, side.getOpposite());
        if (container == null) return null;
        if (!side.equals(currentMTE.getFrontFacing())) return null;
        return container;
    }

    default IVacuumContainer getAdjacentIVacuumContainer(EnumFacing side, MetaTileEntity currentMTE) {
        TileEntity te = getWorld().getTileEntity(currentMTE.getPos().offset(side));
        if (te == null) return null;

        IVacuumContainer container = te.getCapability(TKCYATileCapabilities.CAPABILITY_VACUUM_CONTAINER, side.getOpposite());
        if (container == null) return null;
        if (!side.equals(currentMTE.getFrontFacing())) return null;
        return container;
    }

    default int fillExportTank(int amount, boolean doFill, MetaTileEntity currentMTE) {
        return currentMTE.getExportFluids().fill(Air.getFluid(amount), doFill);
    }


    default int drainImportTank(Fluid fluid, int amount, boolean doDrain, MetaTileEntity currentMTE) {
        return currentMTE.getImportFluids().drain(new FluidStack(fluid, amount), doDrain).amount;
    }

}
