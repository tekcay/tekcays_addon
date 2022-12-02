package tekcays_addon.api.capability.impl;

import tekcays_addon.api.capability.IHeatContainer;

import java.util.List;

public class HeatContainerList implements IHeatContainer {

    private final List<IHeatContainer> heatContainerList;

    public HeatContainerList(List<IHeatContainer> heatContainerList) {
        this.heatContainerList = heatContainerList;
    }


    @Override
    public boolean changeHeat(int amount, boolean simulate) {
        for (IHeatContainer heatContainer : heatContainerList) {
            int currentHeat = heatContainer.getHeat();
            int maxHeat = heatContainer.getMaxHeat();

            if (amount > 0) {
                int diff = maxHeat - currentHeat - amount;
                if (diff < 0) {
                    heatContainer.changeHeat(maxHeat - currentHeat, false);
                    amount = - diff;
                } else {
                    heatContainer.changeHeat(currentHeat + amount, false);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getMinHeat() {
        return 0;
    }

    @Override
    public int getMaxHeat() {
        return 20000;
    }

    @Override
    public int getMaxTemperature() {
        return 20000;
    }

    /*
    @Override
    public int getHeat() {
        return heatContainerList.stream()
                .mapToInt(IHeatContainer::getHeat)
                .sum();
    }
     */
    ////////////////////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //Works only for ONE heat container, code must be reworked if multiple heat containers per multiblocks must be handled
    ////////////////////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @Override
    public int getHeat() {
        return heatContainerList.get(0).getHeat();
    }

    @Override
    public int getTemperature() {
        return heatContainerList.get(0).getTemperature();
    }

    @Override
    public void setHeat(int amount) {
        heatContainerList.get(0).setHeat(amount);
    }

    @Override
    public void setTemperature(int temperature) {
        for (IHeatContainer iHeatContainer : heatContainerList) {
            iHeatContainer.setTemperature(temperature);
        }
    }

}
