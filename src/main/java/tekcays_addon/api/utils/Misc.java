package tekcays_addon.api.utils;

public class Misc {

    public static boolean isBetweenInclusive(long start, long end, long value) {
        return start <= value && value <= end;
    }
}
