package tekcays_addon.gtapi.capability.containers;

import tekcays_addon.gtapi.capability.machines.Container;

public interface IHeatContainer extends Container {

    /**
     * @return the amount of heat in the container
     */
    int getHeat();

    /**
     * @return the temperature of the container
     */
    int getTemperature();

    /**
     * Set the amount of heat in the container
     *
     * @param amount the amount to set
     */
    void setHeat(int amount);

    /**
     * Set the temperature of the container
     *
     * @param temperature the amount to set
     */
    void setTemperature(int temperature);

    /**
     * Change the amount of heat in the container by {@code ADDING} a given amount.
     * If the resulted heat is lower than zero, heat will be set to 0.
     * If the resulted heat is higher than {@code maxHeat}, heat will be set to {@code maxHeat}.
     * @param amount the amount to change by
     */
    default void changeHeat(int amount) {
        setHeat(Math.max(0, Math.min(getHeat() + amount, getMaxHeat())));
    }

    /**
     * @return the minimum heat this container can handle
     */
    int getMinHeat();

    /**
     * @return the maximum heat this container can handle
     */
    int getMaxHeat();

    /**
     * @return the maximum temperature this container can handle
     */
    int getMaxTemperature();
}
