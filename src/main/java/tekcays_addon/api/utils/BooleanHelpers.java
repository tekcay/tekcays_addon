package tekcays_addon.api.utils;

import java.util.Arrays;
import java.util.List;

public class BooleanHelpers {

    @SafeVarargs
    public static <T> boolean anyMatch(T objectToCompareWith, T... objects) {
        return anyMatch(objectToCompareWith, Arrays.asList(objects));
    }

    public static <T> boolean anyMatch(T objectToCompareWith, List<T> objects) {
        return objects.stream().anyMatch(object -> object.equals(objectToCompareWith));
    }

}
