package tekcays_addon.api.utils;

public class Numbers {

    public static int getValueInBetween(int min, int max, int number) {
        if (number > max) return max;
        return Math.max(number, min);
    }

    public static double getValueInBetween(double min, double max, double number) {
        if (number > max) return max;
        return Math.max(number, min);
    }

    public static long getValueInBetween(long min, long max, long number) {
        if (number > max) return max;
        return Math.max(number, min);
    }

    public static float getValueInBetween(float min, float max, float number) {
        if (number > max) return max;
        return Math.max(number, min);
    }

    public static int changeByKeepingPositive(int number, int amount) {
        return Math.max(number + amount, 0);
    }

    public static long changeByKeepingPositive(long number, long amount) {
        return Math.max(number + amount, 0);
    }

    public static float changeByKeepingPositive(float number, float amount) {
        return Math.max(number + amount, 0);
    }

    public static double changeByKeepingPositive(double number, double amount) {
        return Math.max(number + amount, 0);
    }

    /**
     * Returns a {@code float} between 0.0f and 1.0f.
     * 
     * @param number
     * @return a float
     */
    public static float getPercentageInFloat(float number) {
        if (number > 1.0f) return 1.0f;
        return Math.max(number, 0.0f);
    }
}
