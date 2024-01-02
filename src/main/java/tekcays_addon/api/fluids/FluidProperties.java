package tekcays_addon.api.fluids;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FluidProperties {

    TOXIC("toxic"),
    FLAMMABLE("flammable");

    private final String name;
}
