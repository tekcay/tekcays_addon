package tekcays_addon.gtapi.capability.list;

import tekcays_addon.gtapi.capability.containers.IHeatContainer;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

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
        return heatContainerList.stream()
                .min(Comparator.comparing(IHeatContainer::getTemperature))
                .orElseThrow(NoSuchElementException::new)
                .getTemperature();
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
        heatContainerList.forEach(iHeatContainer -> iHeatContainer.setHeat(amount));
    }

    @Override
    public void setTemperature(int temperature) {
        heatContainerList.forEach(iHeatContainer -> iHeatContainer.setTemperature(temperature));
    }
}
