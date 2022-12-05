package tekcays_addon.api.capability;

import gregicality.science.api.GCYSValues;
import tekcays_addon.api.utils.TKCYAValues;

public interface IHeatContainer {

    /**
     * @return the amount of heat in the container
     */
    int getPressure();

    /**
     * @return the temperature of the container
     */
    int getTemperature();

    /**
     * Set the amount of heat in the container
     *
     * @param amount the amount to set
     */
    void setPressure(int amount);

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
        setPressure(Math.max(0, Math.min(getPressure() + amount, getMaxPressure())));
    }

    /*
    default boolean changeHeat(int amount, boolean simulate) {
        if (simulate) return isHeatSafe(getHeat() + amount);
        setHeat(getHeat() + amount);
        return isHeatSafe();
    }

     */


    /**
     * @return the minimum heat this container can handle
     */
    int getMinPressure();

    /**
     * @return the maximum heat this container can handle
     */
    int getMaxPressure();

    /**
     * @return the maximum temperature this container can handle
     */
    int getMaxTemperature();

    /**
     * @return true if the Heat is safe for the container, else false
     */
    default boolean isHeatSafe() {
        return isHeatSafe(getPressure());
    }

    /**
     * @param Heat the Heat to check
     * @return true if the Heat is safe for the container, else false
     */
    default boolean isHeatSafe(int Heat) {
        return Heat <= getMaxPressure() && Heat >= getMinPressure();
    }

    /**
     * @return if the Heat is around atmospheric levels
     */
    default boolean isNormalHeat() {
        return getPressure() == 0;
    }


    IHeatContainer EMPTY = new IHeatContainer() {

        @Override
        public int getMinPressure() {
            return TKCYAValues.MIN_HEAT;
        }

        @Override
        public int getMaxPressure() {
            return TKCYAValues.MAX_HEAT;
        }

        @Override
        public int getPressure() {
            return 0;
        }

        @Override
        public int getMaxTemperature() {
            return TKCYAValues.MAX_HEAT;
        }
        @Override
        public int getTemperature() {
            return GCYSValues.EARTH_TEMPERATURE;
        }


        @Override
        public void setPressure(int amount) {
        }

        @Override
        public void setTemperature(int temperature) {
        }

    };
}
