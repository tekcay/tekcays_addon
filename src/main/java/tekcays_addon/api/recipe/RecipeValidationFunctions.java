package tekcays_addon.api.recipe;

import gregtech.api.GTValues;
import gregtech.api.items.toolitem.ToolOreDict;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.recipeproperties.FluidOutputTime;

import javax.annotation.Nullable;
import java.util.Arrays;
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
    public static final Function<Integer, String> VALIDATE_VOLTAGE = RecipeValidationFunctions::validateVoltage;
    public static final Function<FluidOutputTime[], String> VALIDATE_FLUID_OUTPUT_TIME = RecipeValidationFunctions::validateFluidOutputTimes;

    private static String validateVoltage(int value) {
        if(value < 0) {
            return  ("Voltage can not be lower than 0");
        }

        if(value > (GTValues.VN.length - 1)) {
            return String.format("Voltage can not higher than %d. Voltage follows all the GTTiers i.e. ULV = 0 etc.", (GTValues.VN.length - 1));
        }

        return null;
    }


    @Nullable
    public static String validateFluidOutputTimes(FluidOutputTime[] fluidOutputTimes) {
        if (fluidOutputTimes.length == 0) {
            return "There is no fluidOutputTime.";
        }

        if (Arrays.stream(fluidOutputTimes)
                .anyMatch(FluidOutputTime::isNotValid)) {
            return "Invalid fluidOutputTime";
        }

        return null;
    }
}

