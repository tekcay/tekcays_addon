package tekcays_addon.api.utils;

import static gregtech.api.util.GTUtility.isBetweenInclusive;
import static tekcays_addon.api.utils.TKCYAValues.ATMOSPHERIC_PRESSURE;

public class UnitsConversions {

    public static String autoConvertPressureToString(long pressure) {
        if (isBetweenInclusive(ATMOSPHERIC_PRESSURE, ATMOSPHERIC_PRESSURE * 1000, pressure)) {
            return String.format("%d bar", pressure / ATMOSPHERIC_PRESSURE);
        } else if (pressure < ATMOSPHERIC_PRESSURE) {
            return String.format("%d mbar", pressure * 1000 / ATMOSPHERIC_PRESSURE);
        }
        return String.format("%d kbar", pressure / (1000 * ATMOSPHERIC_PRESSURE));
    }

}
