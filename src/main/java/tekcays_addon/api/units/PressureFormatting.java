package tekcays_addon.api.units;

import static tekcays_addon.gtapi.consts.TKCYAValues.ATMOSPHERIC_PRESSURE;

public class PressureFormatting {

    public static boolean isVacuum(int pressureInPa) {
        return pressureInPa < ATMOSPHERIC_PRESSURE;
    }

    /**
     * A method to convert a pressure in {@code Pa} into {@code mbar}.
     * 
     * @return a {@code String} containing the value of the pressure with its unit.
     */
    public static String convertPressureToMillibar(int pressureInPa, boolean addsUnit) {
        if (pressureInPa < ATMOSPHERIC_PRESSURE) {
            return String.format((addsUnit ? "%d mbar" : "%d"), (int) (1000.0 * pressureInPa / ATMOSPHERIC_PRESSURE));
        } else return "error";
    }

    /**
     * A method to convert a pressure in {@code Pa} into {@code bar} or {@code kbar}.
     * 
     * @return a {@code String} containing the value of the pressure with its unit.
     */
    public static String convertPressureToBar(int pressureInPa, boolean addsUnit) {
        // Returns the pressure in kbar
        if (pressureInPa > ATMOSPHERIC_PRESSURE * 1000) {
            return String.format((addsUnit ? "%4.1f kbar" : "%4.1f"),
                    (pressureInPa * 1.0 / (ATMOSPHERIC_PRESSURE * Math.pow(10, 6))));
        }
        return String.format((addsUnit ? "%4.1f bar" : "%4.1f"), (pressureInPa * 1.0 / ATMOSPHERIC_PRESSURE));
    }
}
