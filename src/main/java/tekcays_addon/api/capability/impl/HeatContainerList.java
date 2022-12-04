package tekcays_addon.api.capability.impl;

import tekcays_addon.api.capability.IHeatContainer;

import java.util.List;

public class HeatContainerList implements IHeatContainer {

    private final List<IHeatContainer> heatContainerList;

    public HeatContainerList(List<IHeatContainer> heatContainerList) {
        this.heatContainerList = heatContainerList;
    }

    @Override
    public int getMinHeat() {
        return 0;
    }

    @Override
    public int getMaxHeat() {
        return heatContainerList.stream()
                .mapToInt(IHeatContainer::getMaxHeat)
                .sum();
    }

    /**
     *
     * @return the lowest {@code maxTemperature} of all the {@code IHeatContainer}s contained in the {@code HeatContainerList}.
     */
    @Override
    public int getMaxTemperature() {
        int lowestTemp = Integer.MAX_VALUE;
        for (IHeatContainer heatContainer : heatContainerList) {
            lowestTemp = Math.min(heatContainer.getMaxTemperature(), lowestTemp);
        }
        return lowestTemp;
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
    public void changeHeat(int amount) {
        heatContainerList.get(0).changeHeat(amount);
    }

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
