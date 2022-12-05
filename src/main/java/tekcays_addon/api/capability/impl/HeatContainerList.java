package tekcays_addon.api.capability.impl;

import tekcays_addon.api.capability.IHeatContainer;

import java.util.List;

public class HeatContainerList implements IHeatContainer {

    private final List<IHeatContainer> heatContainerList;

    public HeatContainerList(List<IHeatContainer> heatContainerList) {
        this.heatContainerList = heatContainerList;
    }

    @Override
    public int getMinPressure() {
        return heatContainerList.stream()
                .mapToInt(IHeatContainer::getMinPressure)
                .sum();
    }

    @Override
    public int getMaxPressure() {
        return heatContainerList.stream()
                .mapToInt(IHeatContainer::getMaxPressure)
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

    @Override
    public void changeHeat(int amount) {
        if (amount >= this.getPressure()) {
            setPressure(0);
            return;
        }
        int heatToChange = amount / heatContainerList.size();
        for (IHeatContainer heatContainer : heatContainerList) {
            heatContainer.changeHeat(heatToChange);
        }
    }

    @Override
    public int getPressure() {
        return heatContainerList.stream()
                .mapToInt(IHeatContainer::getPressure)
                .sum();
    }

    //Only needs to check one as they all should have the same temperature
    @Override
    public int getTemperature() {
        return heatContainerList.get(0).getTemperature();
    }

    @Override
    public void setPressure(int amount) {
        for (IHeatContainer heatContainer : heatContainerList) {
            heatContainer.setPressure(amount);
        }
    }

    @Override
    public void setTemperature(int temperature) {
        for (IHeatContainer iHeatContainer : heatContainerList) {
            iHeatContainer.setTemperature(temperature);
        }
    }

}
