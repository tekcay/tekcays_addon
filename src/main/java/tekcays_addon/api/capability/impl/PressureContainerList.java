package tekcays_addon.api.capability.impl;

import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.capability.IPressureContainer;
import tekcays_addon.api.utils.TKCYALog;

import java.util.List;

import static gregtech.api.unification.material.Materials.Air;

public class PressureContainerList implements IPressureContainer {

    private final List<IPressureContainer> pressureContainerList;

    public PressureContainerList(List<IPressureContainer> pressureContainerList) {
        this.pressureContainerList = pressureContainerList;
    }

    private IPressureContainer getIPressureContainer() {
        return pressureContainerList.get(0);
    }

    @Override
    public int getMinPressure() {
        return getIPressureContainer().getMinPressure();
    }

    @Override
    public int getMaxPressure() {
        return getIPressureContainer().getMaxPressure();
    }


    @Override
    public int getVolume() {
        return getIPressureContainer().getVolume();
    }

    @Override
    public void setVolume(int volume) {
        getIPressureContainer().setVolume(volume);
    }

    @Override
    public FluidStack getAirFluidStack() {
        return getIPressureContainer().getAirFluidStack();
    }

    @Override
    public void setAirFluidStack(FluidStack fluidStack) {
        getIPressureContainer().setAirFluidStack(fluidStack);
    }

    @Override
    public boolean canLeakMore(int leak) {
        return false;
    }

    @Override
    public FluidStack getFluidStack() {
        return getIPressureContainer().getFluidStack();
    }

    @Override
    public void setPressurizedFluidName(String pressurizedFluidName) {
        getIPressureContainer().setPressurizedFluidName(pressurizedFluidName);
    }

    @Override
    public String getPressurizedFluidName() {
        return getIPressureContainer().getPressurizedFluidName();
    }

    @Override
    public void setPressurizedFluidAmount(int pressurizedFluidAmount) {
        getIPressureContainer().setPressurizedFluidAmount(pressurizedFluidAmount);
    }

    @Override
    public int getPressurizedFluidAmount() {
        return getIPressureContainer().getPressurizedFluidAmount();
    }

    @Override
    public void setFluidStack(FluidStack fluidStack) {
        TKCYALog.logger.info("PCList fstack : " + fluidStack.getLocalizedName());
        if (fluidStack.isFluidEqual(Air.getFluid(1))) throw new IllegalArgumentException("FluidStack is Air!");
        getIPressureContainer().setFluidStack(fluidStack);
    }

    @Override
    public int getPressure() {
        return getIPressureContainer().getPressure();
    }

    @Override
    public void setPressure() {
        getIPressureContainer().setPressure();
    }

    @Override
    public void setPressure(int temperature) {
        getIPressureContainer().setPressure(temperature);
    }

    @Override
    public boolean canHandleVacuum() {
        return getIPressureContainer().canHandleVacuum();
    }


}
