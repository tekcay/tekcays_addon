package tekcays_addon.api.recipe;

import static tekcays_addon.gtapi.consts.TKCYAValues.*;

import java.util.Map;
import java.util.function.Function;

import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import gregtech.api.items.toolitem.ToolOreDict;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.recipes.recipeproperties.RecipeProperty;
import gregtech.api.recipes.recipeproperties.RecipePropertyStorage;
import gregtech.api.util.EnumValidationResult;
import tekcays_addon.gtapi.recipes.recipeproperties.*;
import tekcays_addon.gtapi.utils.TKCYALog;

public interface RecipeBuilderHelper<T extends RecipeBuilder<T>> {

    IRecipePropertyStorage getRecipePropertyStorage();

    T getRecipeBuilder();

    void setRecipeStatus(EnumValidationResult enumValidationResult);

    default void buildHelper() {
        IRecipePropertyStorage recipePropertyStorage = getRecipePropertyStorage();
        if (recipePropertyStorage == null) recipePropertyStorage = new RecipePropertyStorage();
        for (Map.Entry<RecipeProperty<?>, Object> entry : getRecipePropertyStorage().getRecipeProperties()) {

            RecipeProperty<?> recipeProperty = entry.getKey();

            switch (recipeProperty.getKey()) {

                case MIN_TEMPERATURE_PROPERTY:
                    store((MinTemperatureProperty) recipeProperty, recipePropertyStorage, 0);
                    break;

                case MAX_TEMPERATURE_PROPERTY:
                    store((MaxTemperatureProperty) recipeProperty, recipePropertyStorage, 0);
                    break;

                case MIN_PRESSURE_PROPERTY:
                    store((MinPressureProperty) recipeProperty, recipePropertyStorage, 0L);
                    break;

                case MAX_PRESSURE_PROPERTY:
                    store((MaxPressureProperty) recipeProperty, recipePropertyStorage, 0L);
                    break;

                case INTERVAL_PRESSURE_PROPERTY:
                    store((IntervalPressureProperty) recipeProperty, recipePropertyStorage, EMPTY_LONG_TWO_ARRAY);
                    break;

                case INTERVAL_TEMPERATURE_PROPERTY:
                    store((IntervalTemperatureProperty) recipeProperty, recipePropertyStorage, EMPTY_INT_TWO_ARRAY);
                    break;

                case PRESSURIZED_FLUIDSTACK_PROPERTY:
                    store((PressurizedFluidStackProperty) recipeProperty, recipePropertyStorage, null);
                    break;

                case TOOL_ORE_DICT_PROPERTY:
                    store((ToolProperty) recipeProperty, recipePropertyStorage, ToolOreDict.toolWrench);
                    break;

                case AMPERAGE:
                    store((MultiAmperageProperty) recipeProperty, recipePropertyStorage, 0);
                    break;

                case VOLTAGE:
                    store((VoltageProperty) recipeProperty, recipePropertyStorage, 0);
                    break;

                default:
                    break;
            }
        }
    }

    default <U extends RecipeProperty<?>> void store(U recipeProperty, IRecipePropertyStorage recipePropertyStorage,
                                                     Object defaultValue) {
        recipePropertyStorage.store(recipeProperty, defaultValue);
    }

    default <U> T validate(RecipeProperty<?> recipeProperty, U value, @NotNull Function<U, String> validate) {
        T recipeBuilder = getRecipeBuilder();
        String errorMessage = validate.apply(value);

        if (errorMessage != null) {
            TKCYALog.logger.error(recipeProperty.getKey() + " cannot be less than or equal to 0",
                    new IllegalArgumentException());
            setRecipeStatus(EnumValidationResult.INVALID);
        }
        getRecipeBuilder().applyProperty(recipeProperty, value);
        return recipeBuilder;
    }

    // RecipeProperties

    @NotNull
    default T intervalPressure(Integer[] pressures) {
        return validate(IntervalPressureProperty.getInstance(), pressures,
                RecipeValidationFunctions.VALIDATE_INT_ARRAY);
    }

    @NotNull
    default T intervalTemperature(Integer[] temperatures) {
        return validate(IntervalTemperatureProperty.getInstance(), temperatures,
                RecipeValidationFunctions.VALIDATE_INT_ARRAY);
    }

    @NotNull
    default T minTemperature(int temperature) {
        return validate(MinTemperatureProperty.getInstance(), temperature,
                RecipeValidationFunctions.VALIDATE_INT_POSITIVE);
    }

    @NotNull
    default T maxTemperature(int temperature) {
        return validate(MaxTemperatureProperty.getInstance(), temperature,
                RecipeValidationFunctions.VALIDATE_INT_POSITIVE);
    }

    @NotNull
    default T minPressure(int minPressure) {
        return validate(MinPressureProperty.getInstance(), minPressure,
                RecipeValidationFunctions.VALIDATE_INT_POSITIVE);
    }

    @NotNull
    default T maxPressure(int maxPressure) {
        return validate(MaxPressureProperty.getInstance(), maxPressure,
                RecipeValidationFunctions.VALIDATE_INT_POSITIVE);
    }

    @NotNull
    default T pressurizedFluidStack(FluidStack fluidStack) {
        return validate(PressurizedFluidStackProperty.getInstance(), fluidStack,
                RecipeValidationFunctions.VALIDATE_FLUIDSTACK);
    }

    @NotNull
    default T toolOreDict(ToolOreDict toolOreDict) {
        return validate(ToolProperty.getInstance(), toolOreDict, RecipeValidationFunctions.VALIDATE_TOOL_ORE);
    }

    /**
     * @param amperage corresponds at least to the sum of the amperage of all the input energy hatches.
     *                 <br>
     *                 e.g. an {@code amperage} of 3 needs two energy hatches of 2 A, regardless of the voltage.
     *                 <br>
     */
    @NotNull
    default T amperage(int amperage) {
        return validate(MultiAmperageProperty.getInstance(), amperage, RecipeValidationFunctions.VALIDATE_INT_POSITIVE);
    }

    /**
     * @param voltage corresponds to the voltage tier of the necessary energy hatch(es).
     *                <br>
     *                0 corresponds to ULV, 1 corresponds to LV etc.
     *                <br>
     */
    @NotNull
    default T voltageTier(int voltage) {
        return validate(VoltageProperty.getInstance(), voltage, RecipeValidationFunctions.VALIDATE_VOLTAGE);
    }

    default void applyPropertyHelper(@NotNull String key, Object value) {
        switch (key) {
            case INTERVAL_PRESSURE_PROPERTY:
                this.intervalPressure(((Integer[]) value).clone());
                return;

            case INTERVAL_TEMPERATURE_PROPERTY:
                this.intervalTemperature(((Integer[]) value).clone());
                return;

            case MIN_TEMPERATURE_PROPERTY:
                this.minTemperature(((Number) value).intValue());
                return;

            case MAX_TEMPERATURE_PROPERTY:
                this.maxTemperature(((Number) value).intValue());
                return;

            case MIN_PRESSURE_PROPERTY:
                this.minPressure(((Number) value).intValue());
                return;

            case MAX_PRESSURE_PROPERTY:
                this.maxPressure(((Number) value).intValue());
                return;

            case PRESSURIZED_FLUIDSTACK_PROPERTY:
                this.pressurizedFluidStack(((FluidStack) value));
                return;

            case TOOL_ORE_DICT_PROPERTY:
                this.toolOreDict(((ToolOreDict) value));
                return;

            case AMPERAGE:
                this.amperage(((Number) value).intValue());
                return;

            case VOLTAGE:
                this.voltageTier(((Number) value).intValue());
                return;

            default:
                break;
        }
    }
}
