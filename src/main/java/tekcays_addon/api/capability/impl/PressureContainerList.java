package tekcays_addon.api.capability.impl;

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
    public void changePressure(int amount) {
        this.setPressure(Math.max(this.getMinPressure(), Math.min(this.getPressure() + amount, this.getMaxPressure())));
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
    public int getPressure() {
        return getIPressureContainer().getPressure();
    }

    @Override
    public boolean canHandleVacuum() {
        return getIPressureContainer().canHandleVacuum();
    }

    @Override
    public int getPU() {
        return getIPressureContainer().getPU();
    }

    @Override
    public void setPU(int amount) {
        getIPressureContainer().setPU(amount);
    }

    @Override
    public void changePU(int amount) {
       getIPressureContainer().changePU(amount);
    }

    @Override
    public void setPressure(int amount) {
        getIPressureContainer().setPressure(amount);
    }


}
