package tekcays_addon.api.fluid;

import gregtech.api.fluids.fluidType.FluidType;
import lombok.Getter;

import static gregtech.api.fluids.fluidType.FluidTypes.*;

@Getter
public enum FluidTypeName {

    LIQUID_NAME(LIQUID),
    GAS_NAME(GAS),
    ACID_NAME(ACID),
    PLASMA_NAME(PLASMA);

    private FluidType fluidType;

    FluidTypeName(FluidType fluidType) {
    }
}
