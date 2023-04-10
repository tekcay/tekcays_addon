package tekcays_addon.api.capability;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Consumer;

@Getter
@AllArgsConstructor
public class ParameterHelper<T> {

    private String name;
    private T value;
    private Consumer<T> function;
}
