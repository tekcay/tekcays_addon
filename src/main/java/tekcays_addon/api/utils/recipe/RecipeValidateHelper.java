package tekcays_addon.api.utils.recipe;

import net.minecraftforge.fluids.FluidStack;


import java.util.function.Function;

import static tekcays_addon.api.utils.TKCYAValues.EMPTY_LONG_TWO_ARRAY;

public class RecipeValidateHelper {

    public static final Function<Integer, String> VALIDATE_INT_POSITIVE = value -> value <= 0 ? " cannot be less than or equal to 0" : null;
    public static final Function<Long, String> VALIDATE_LONG_POSITIVE = value -> value <= 0 ? " cannot be less than or equal to 0" : null;
    public static final Function<FluidStack, String> VALIDATE_FLUIDSTACK = value -> value == null ? "fluidStack is null!" : null;
    public static final Function<Long[], String> VALIDATE_ARRAY = value -> value.length != EMPTY_LONG_TWO_ARRAY.length ? "has more or less than expected arguments (2)!" : null;

}

