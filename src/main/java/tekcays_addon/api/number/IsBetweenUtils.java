package tekcays_addon.api.number;

import org.jetbrains.annotations.NotNull;

public class IsBetweenUtils {

    public static boolean isBetweenInclusive(long start, long end, long value) {
        return start <= value && value <= end;
    }

    public static boolean isBetweenInclusive(int start, int end, int value) {
        return start <= value && value <= end;
    }

    public static boolean isBetweenInclusive(double start, double end, double value) {
        return start <= value && value <= end;
    }

    public static boolean isBetweenExclusive(long start, long end, long value) {
        return start < value && value < end;
    }

    public static boolean isBetweenExclusive(int start, int end, int value) {
        return start < value && value < end;
    }

    public static boolean isBetweenExclusive(double start, double end, double value) {
        return start < value && value < end;
    }

    public static boolean isBetweenEndExclusive(long start, long end, long value) {
        return start <= value && value < end;
    }

    public static boolean isBetweenEndExclusive(int start, int end, int value) {
        return start <= value && value < end;
    }

    public static boolean isBetweenEndExclusive(double start, double end, double value) {
        return start <= value && value < end;
    }

    public static boolean isBetweenStartExclusive(long start, long end, long value) {
        return start < value && value <= end;
    }

    public static boolean isBetweenStartExclusive(int start, int end, int value) {
        return start < value && value <= end;
    }

    public static boolean isBetweenStartExclusive(double start, double end, double value) {
        return start < value && value <= end;
    }

    /**
     * Checks whether a value is included between 10^({@code start}) and 10^({@code  end}).
     */
    public static boolean isBetweenInclusiveExponents(double start, double end, @NotNull Number value) {
        return Math.pow(10, start) <= value.doubleValue() && value.doubleValue() <= Math.pow(10, end);
    }

    /**
     * Checks whether a value is included between 10^({@code start}) and 10^({@code  end}).
     */
    public static boolean isBetweenExclusiveExponents(double start, double end, @NotNull Number value) {
        return Math.pow(10, start) < value.doubleValue() && value.doubleValue() < Math.pow(10, end);
    }

    /**
     * Checks whether a value is included between 10^({@code start}) and 10^({@code  end}).
     */
    public static boolean isBetweenEndExclusiveExponents(double start, double end, @NotNull Number value) {
        return Math.pow(10, start) <= value.doubleValue() && value.doubleValue() < Math.pow(10, end);
    }

    /**
     * Check if a value is included between 10^({@code start}) and 10^({@code start} + 3).
     * <br>
     * If true, returns the quotient of the {@code value} divided by 10^({@code start}).
     * <br>
     * If false, returns the {@code value}.
     * <br>
     * See {@link IsBetweenUtils#isBetweenInclusiveExponents(double, double, Number)}.
     */
    public static double setToOrderOfSize(int start, double value) {
        return isBetweenInclusiveExponents(start, start + 3, value) ? value / Math.pow(10, start) : value;
    }
}
