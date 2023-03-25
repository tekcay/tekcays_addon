package tekcays_addon.api.capability.impl;

import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.capability.IPressureContainer;
import tekcays_addon.api.utils.FluidStackHelper;

import java.util.List;

public class PressureContainerList implements IPressureContainer, FluidStackHelper {

    private final List<IPressureContainer> pressureContainerList;

    public PressureContainerList(List<IPressureContainer> pressureContainerList) {
        this.pressureContainerList = pressureContainerList;
    }

    private IPressureContainer getIPressureContainer() {
        return pressureContainerList.get(0);
    }

    @Override
    public long getMinPressure() {
        return getIPressureContainer().getMinPressure();
    }

    @Override
    public long getMaxPressure() {
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
    public FluidStack getPressurizedFluidStack() {
        return getIPressureContainer().getPressurizedFluidStack();
    }

    @Override
    public int getPressurizedFluidStackAmount() {
        return getNullableFluidStackAmount(getPressurizedFluidStack());
    }

    @Override
    public String getPressurizedFluidStackLocalizedName() {
        return getNullableFluidStackLocalizedName(getPressurizedFluidStack());
    }

    @Override
    public void setPressurizedFluidStack(FluidStack fluidStack) {
        getIPressureContainer().setPressurizedFluidStack(fluidStack);
    }

    @Override
    public int changePressurizedFluidStack(FluidStack fluidStack, int amount) {
        return getIPressureContainer().changePressurizedFluidStack(fluidStack, amount);
    }

    @Override
    public long getPressure() {
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
