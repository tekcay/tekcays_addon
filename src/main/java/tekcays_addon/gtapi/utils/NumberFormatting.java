package tekcays_addon.gtapi.utils;

import static java.lang.String.format;

public class NumberFormatting {

    public static String tryToConvertToKNumber(int number) {
        return number > 1000 ? format("%d %s", number/1000, "k") : Integer.toString(number);
    }
}
