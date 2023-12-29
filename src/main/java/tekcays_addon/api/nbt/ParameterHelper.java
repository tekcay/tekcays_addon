package tekcays_addon.api.nbt;

import java.util.List;
import java.util.function.Consumer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tekcays_addon.api.utils.ListBuilder;

@Getter
@AllArgsConstructor
public class ParameterHelper<T> {

    private String name;
    private T value;
    private Consumer<T> function;

    public static <T> ParameterHelper<T> build(String name, T value, Consumer<T> function) {
        return new ParameterHelper<T>(name, value, function);
    }

    @SafeVarargs
    public static <T> List<ParameterHelper<? extends T>> buildList(ParameterHelper<? extends T>... parameterHelpers) {
        return ListBuilder.build(parameterHelpers);
    }
}
