package tekcays_addon.api.capability.containers;

import tekcays_addon.api.utils.TKCYAValues;

import static tekcays_addon.api.utils.TKCYAValues.ROOM_TEMPERATURE;

public interface IHeatContainer {

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


    IHeatContainer EMPTY = new IHeatContainer() {

        @Override
        public int getMinHeat() {
            return TKCYAValues.MIN_HEAT;
        }

        @Override
        public int getMaxHeat() {
            return TKCYAValues.MAX_HEAT;
        }

        @Override
        public int getHeat() {
            return 0;
        }

        @Override
        public int getMaxTemperature() {
            return TKCYAValues.MAX_HEAT;
        }
        @Override
        public int getTemperature() {
            return ROOM_TEMPERATURE;
        }


        @Override
        public void setHeat(int amount) {
        }

        @Override
        public void setTemperature(int temperature) {
        }

    };
}
