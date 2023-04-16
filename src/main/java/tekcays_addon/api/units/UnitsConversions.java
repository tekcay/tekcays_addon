package tekcays_addon.api.units;

import static gregtech.api.util.GTUtility.isBetweenInclusive;
import static tekcays_addon.gtapi.utils.TKCYAValues.ATMOSPHERIC_PRESSURE;
import static tekcays_addon.gtapi.utils.TKCYAValues.STEAM_TO_WATER;

public class UnitsConversions {

    public static String autoConvertPressureToString(int pressure) {
        if (isBetweenInclusive(ATMOSPHERIC_PRESSURE, ATMOSPHERIC_PRESSURE * 1000, pressure)) {
            return String.format("%d bar", pressure / ATMOSPHERIC_PRESSURE);
        } else if (pressure < ATMOSPHERIC_PRESSURE) {
            return String.format("%d mbar", pressure * 1000 / ATMOSPHERIC_PRESSURE);
        }
        return String.format("%d kbar", pressure / (1000 * ATMOSPHERIC_PRESSURE));
    }

    public static int convertSteamToWater(int steamAmount) {
        return steamAmount / STEAM_TO_WATER;
    }

}
