package tekcays_addon.api.units;

import java.util.LinkedHashMap;
import java.util.Map;

public class MetricPrefix {

    public static final Map<Integer, Character> UNITS = new LinkedHashMap<>();

    public static final char femto = 'f';
    public static final char pico = 'p';
    public static final char nano = 'n';
    public static final char micro = 'µ';
    public static final char milli = 'm';
    public static final char kilo = 'k';
    public static final char mega = 'M';
    public static final char giga = 'G';
    public static final char tera = 'T';
    public static final char peta = 'P';
    public static final char exa = 'E';

    static {
        UNITS.put(-15, femto);
        UNITS.put(-12, pico);
        UNITS.put(-9, nano);
        UNITS.put(-6, micro);
        UNITS.put(-3, milli);
        UNITS.put(3, kilo);
        UNITS.put(6, mega);
        UNITS.put(9, giga);
        UNITS.put(12, tera);
        UNITS.put(15, peta);
        UNITS.put(18, exa);
    }
}
