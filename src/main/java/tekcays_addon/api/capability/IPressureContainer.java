package tekcays_addon.api.capability;

import tekcays_addon.api.utils.TKCYAValues;

import static tekcays_addon.api.utils.TKCYAValues.ABSOLUTE_VACUUM;
import static tekcays_addon.api.utils.TKCYAValues.ATMOSPHERIC_PRESSURE;

public interface IPressureContainer {

    /**
     * @return the amount of pressure in the container
     */
    int getPressure();

    /**
     * Determines if the {@code PressureContainer} handles a pressure greater or lower than 1 bar.
     * @return
     */
    boolean canHandleVacuum();

    /**
     * Set the {@code pressure}
     * @param pressure
     */
    void setPressure(int pressure);


    /**
     * Change the pressure in the container by {@code ADDING} a given amount.
     * If the {@code PressureContainer} can handle vacuum
     * If the resulted pressure is lower than 1, pressure will be set to 1.
     * If the resulted pressure is higher than {@code maxPressure}, pressure will be set to {@code maxPressure}.
     * @param amount the amount to change by
     */
    default void changePressure(int amount) {
        setPressure(Math.max(getMinPressure(), Math.min(getPressure() + amount, getMaxPressure())));
    }

    int getVolume();

    void setVolume(int volume);


    /**
     * @return the maximum pressure this container can handle
     */
    int getMaxPressure();

    /**
     * @return the minimum pressure this container can handle
     */
    int getMinPressure();

    IPressureContainer EMPTY = new IPressureContainer() {

        @Override
        public int getMaxPressure() {
            return canHandleVacuum() ? ATMOSPHERIC_PRESSURE * 1000 : TKCYAValues.MAX_PRESSURE;
        }

        @Override
        public int getMinPressure() {
            return canHandleVacuum() ? ABSOLUTE_VACUUM : ATMOSPHERIC_PRESSURE;
        }

        @Override
        public int getPressure() {
            return 1;
        }

        @Override
        public boolean canHandleVacuum() {
            return false;
        }

        @Override
        public void setPressure(int pressure) {
        }

        @Override
        public int getVolume() {
            return 0;
        }

        @Override
        public void setVolume(int volume) {

        }

    };

}
