package tekcays_addon.api.units;

public class UnitFormat {

    public static String formatValueWithUnit(double value, String unit) {
        return String.format("%3.1f %s", value, unit);
    }

    public static String formatValueWithUnit(int value, String unit) {
        return String.format("%d %s", value, unit);
    }

    public static String formatValueWithUnit(int value, String unit, char metricPrefix) {
        return String.format("%d %c%s", value, metricPrefix, unit);
    }

    public static String formatValueWithUnit(double value, String unit, char metricPrefix) {
        return String.format("%3.1f %c%s", value, metricPrefix, unit);
    }
}
