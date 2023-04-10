package tekcays_addon.api.capability.list;

import tekcays_addon.api.capability.containers.IHeatContainer;

import java.util.List;

public class HeatContainerList implements IHeatContainer {

    private final List<IHeatContainer> heatContainerList;

    public HeatContainerList(List<IHeatContainer> heatContainerList) {
        this.heatContainerList = heatContainerList;
    }

    @Override
    public int getMinHeat() {
        return heatContainerList.stream()
                .mapToInt(IHeatContainer::getMinHeat)
                .sum();
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

    @Override
    public void changeHeat(int amount) {
        if (amount >= this.getHeat()) {
            setHeat(0);
            return;
        }
        int heatToChange = amount / heatContainerList.size();
        for (IHeatContainer heatContainer : heatContainerList) {
            heatContainer.changeHeat(heatToChange);
        }
    }

    @Override
    public int getHeat() {
        return heatContainerList.stream()
                .mapToInt(IHeatContainer::getHeat)
                .sum();
    }

    //Only needs to check one as they all should have the same temperature
    @Override
    public int getTemperature() {
        return heatContainerList.get(0).getTemperature();
    }

    @Override
    public void setHeat(int amount) {
        for (IHeatContainer heatContainer : heatContainerList) {
            heatContainer.setHeat(amount);
        }
    }

    @Override
    public void setTemperature(int temperature) {
        for (IHeatContainer iHeatContainer : heatContainerList) {
            iHeatContainer.setTemperature(temperature);
        }
    }

}
