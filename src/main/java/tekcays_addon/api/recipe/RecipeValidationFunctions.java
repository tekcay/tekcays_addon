package tekcays_addon.api.recipe;

import gregtech.api.items.toolitem.ToolOreDict;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.gtapi.recipes.recipeproperties.ToolProperty;

import java.util.function.Function;

import static tekcays_addon.gtapi.consts.TKCYAValues.EMPTY_INT_TWO_ARRAY;
import static tekcays_addon.gtapi.consts.TKCYAValues.EMPTY_LONG_TWO_ARRAY;

public class RecipeValidationFunctions {

    public static final Function<Integer, String> VALIDATE_INT_POSITIVE = value -> value <= 0 ? " cannot be less than or equal to 0" : null;
    public static final Function<Long, String> VALIDATE_LONG_POSITIVE = value -> value <= 0 ? " cannot be less than or equal to 0" : null;
    public static final Function<FluidStack, String> VALIDATE_FLUIDSTACK = value -> value == null ? "fluidStack is null!" : null;
    public static final Function<Integer[], String> VALIDATE_INT_ARRAY = value -> value.length != EMPTY_INT_TWO_ARRAY.length ? "has more or less than expected arguments (2)!" : null;
    public static final Function<Long[], String> VALIDATE_LONG_ARRAY = value -> value.length != EMPTY_LONG_TWO_ARRAY.length ? "has more or less than expected arguments (2)!" : null;
    public static final Function<ToolOreDict, String> VALIDATE_TOOL_ORE = value -> value == null ? "Tool oreDict is null" : null;


}

