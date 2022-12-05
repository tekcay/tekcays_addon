package tekcays_addon.api.capability;

import tekcays_addon.api.utils.TKCYAValues;

public interface IPressureContainer {

    /**
     * @return the amount of pressure in the container
     */
    int getPressure();

    /**
     * Set the {@code pressure}
     * @param pressure
     */
    void setPressure(int pressure);


    /**
     * Change the pressure in the container by {@code ADDING} a given amount.
     * If the resulted heat is lower than 1, heat will be set to 1.
     * If the resulted pressure is higher than {@code maxPressure}, heat will be set to {@code maxPressure}.
     * @param amount the amount to change by
     */
    default void changePressure(int amount) {
        setPressure(Math.max(1, Math.min(getPressure() + amount, getMaxPressure())));
    }


    /**
     * @return the maximum pressure this container can handle
     */
    int getMaxPressure();



    IPressureContainer EMPTY = new IPressureContainer() {

        @Override
        public int getMaxPressure() {
            return TKCYAValues.MAX_PRESSURE;
        }

        @Override
        public int getPressure() {
            return 1;
        }

        @Override
        public void setPressure(int pressure) {
        }


    };
}
