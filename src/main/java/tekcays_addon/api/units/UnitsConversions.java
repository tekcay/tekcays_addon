package tekcays_addon.api.units;

import static tekcays_addon.api.number.IsBetweenUtils.*;
import static tekcays_addon.api.units.UnitFormat.formatValueWithUnit;
import static tekcays_addon.gtapi.consts.TKCYAValues.ATMOSPHERIC_PRESSURE;

public class UnitsConversions {

    public static String autoConvertPressureToString(int pressure) {
        if (isBetweenInclusive(ATMOSPHERIC_PRESSURE, ATMOSPHERIC_PRESSURE * 1000, pressure)) {
            return String.format("%d bar", pressure / ATMOSPHERIC_PRESSURE);
        } else if (pressure < ATMOSPHERIC_PRESSURE) {
            return String.format("%d mbar", pressure * 1000 / ATMOSPHERIC_PRESSURE);
        }
        return String.format("%d kbar", pressure / (1000 * ATMOSPHERIC_PRESSURE));
    }

    /**
     * Converts and formats to the closest size of order a numeric value with a unit.
     * 
     * @param value the raw value to convert.
     * @param unit  a standard unit without a metric prefix. See {@link MetricPrefix}.
     * @return a {@link String}
     *         <br>
     *         <br>
     *         {@code Use example:}
     *         <br>
     *         <br>
     *         {@code (0.04, "J")} -> "40.0 mJ"
     *         <br>
     *         {@code (12, "N")} -> "12.0 N"
     *         <br>
     *         {@code (120000, "bar")} -> "120.0 kbar"
     */
    public static String convertAndFormatToSizeOfOrder(double value, String unit) {
        return MetricPrefix.UNITS.entrySet().stream()
                .filter(entry -> !isBetweenEndExclusive(1, 1000, value))
                .filter(entry -> isBetweenEndExclusiveExponents(entry.getKey(), entry.getKey() + 3, value))
                .map(entry -> formatValueWithUnit(value / Math.pow(10, entry.getKey()), unit, entry.getValue()))
                .findAny()
                .orElse(formatValueWithUnit(value, unit));
    }
}
