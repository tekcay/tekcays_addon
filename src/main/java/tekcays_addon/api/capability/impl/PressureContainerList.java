package tekcays_addon.api.capability.impl;

import tekcays_addon.api.capability.IPressureContainer;

import java.util.List;

public class PressureContainerList implements IPressureContainer {

    private final List<IPressureContainer> pressureContainerList;

    public PressureContainerList(List<IPressureContainer> pressureContainerList) {
        this.pressureContainerList = pressureContainerList;
    }

    @Override
    public int getMinPressure() {
        return pressureContainerList.get(0).getMinPressure();
    }

    @Override
    public int getMaxPressure() {
        return pressureContainerList.get(0).getMaxPressure();
    }


    @Override
    public void changePressure(int amount) {
        this.setPressure(Math.max(this.getMinPressure(), Math.min(this.getPressure() + amount, this.getMaxPressure())));
    }

    @Override
    public int getPressure() {
        return pressureContainerList.get(0).getPressure();
    }

    @Override
    public boolean canHandleVacuum() {
        return pressureContainerList.get(0).canHandleVacuum();
    }

    @Override
    public void setPressure(int amount) {
        pressureContainerList.get(0).setPressure(amount);
    }


}
