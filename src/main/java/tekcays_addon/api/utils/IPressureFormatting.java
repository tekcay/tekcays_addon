package tekcays_addon.api.utils;

import static tekcays_addon.api.utils.TKCYAValues.ATMOSPHERIC_PRESSURE;

public interface IPressureFormatting {

    default boolean isVacuum(int pressure) {
        return pressure < ATMOSPHERIC_PRESSURE;
    }

    /**
     * A method to convert a pressure in {@code Pa} into {@code mbar}.
     * @param pressureInPa
     * @return a {@code String} containing the value of the pressure with its unit.
     */
    default String convertPressureToMbar(int pressureInPa) {
        //Returns the pressure in mbar
        if (pressureInPa < ATMOSPHERIC_PRESSURE) return String.format("%d mbar", (int) (1000.0 * pressureInPa / ATMOSPHERIC_PRESSURE));
        return "error";
    }

    /**
     * A method to convert a pressure in {@code Pa} into {@code bar} or {@code kbar}.
     * @param pressureInPa
     * @return a {@code String} containing the value of the pressure with its unit.
     */
    default String convertPressureToBar(int pressureInPa) {
        //Returns the pressure in kbar
        if (pressureInPa > ATMOSPHERIC_PRESSURE * 1000) {
            return String.format("%4.1f kbar", (pressureInPa * 1.0 / (ATMOSPHERIC_PRESSURE * Math.pow(10, 6))));
        }
        return String.format("%4.1f bar", (pressureInPa * 1.0 / ATMOSPHERIC_PRESSURE));
    }
}
