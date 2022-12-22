package tekcays_addon.api.capability.impl;

import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.capability.IPressureContainer;

import java.util.List;

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
    public void setFluidStack(FluidStack fluidStack) {
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
    public boolean canHandleVacuum() {
        return getIPressureContainer().canHandleVacuum();
    }


}
