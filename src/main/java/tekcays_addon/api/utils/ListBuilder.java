package tekcays_addon.api.utils;

import java.util.Arrays;
import java.util.List;

public class ListBuilder {

    @SafeVarargs
    public static <T> List<T> build(T... elements) {
        return Arrays.asList(elements);
    }
}
