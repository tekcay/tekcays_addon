package tekcays_addon.api.utils.recipe;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.recipes.recipeproperties.RecipeProperty;
import gregtech.api.recipes.recipeproperties.RecipePropertyStorage;
import gregtech.api.util.EnumValidationResult;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.recipes.recipeproperties.*;
import tekcays_addon.api.utils.TKCYALog;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Function;

import static tekcays_addon.api.utils.recipe.RecipeValidateHelper.*;
import static tekcays_addon.api.utils.TKCYAValues.*;

public interface RecipeBuilderHelper<T extends RecipeBuilder<T>> {

    IRecipePropertyStorage getRecipePropertyStorage();
    T getRecipeBuilder();
    List<RecipeProperty<?>> getRecipePropertiesInstance();
    void setRecipeStatus(EnumValidationResult enumValidationResult);

    default void buildHelper() {
        IRecipePropertyStorage recipePropertyStorage = getRecipePropertyStorage();
        if (recipePropertyStorage == null) recipePropertyStorage = new RecipePropertyStorage();
        for (RecipeProperty<?> recipeProperty : getRecipePropertiesInstance()) {

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
                    store((MaxTemperatureProperty) recipeProperty, recipePropertyStorage, 0L);
                    break;

                case PRESSURIZED_FLUIDSTACK_PROPERTY:
                    store((PressurizedFluidStackProperty) recipeProperty, recipePropertyStorage, null);
                    break;

                default:
                    break;
            }
        }
    }

    default <U extends RecipeProperty<?>> void store(U recipeProperty, IRecipePropertyStorage recipePropertyStorage, Object defaultValue) {
        recipePropertyStorage.store(recipeProperty, defaultValue);
    }


    default <U> T validate(RecipeProperty<?> recipeProperty, U value, Function<U, String> validate) {
        T recipeBuilder = getRecipeBuilder();
        String errorMessage = validate.apply(value);

        if (errorMessage != null) {
            TKCYALog.logger.error(recipeProperty.getKey() + " cannot be less than or equal to 0", new IllegalArgumentException());
            setRecipeStatus(EnumValidationResult.INVALID);
        }
        getRecipeBuilder().applyProperty(recipeProperty, value);
        return recipeBuilder;
    }

    //RecipeProperties

    @Nonnull
    default T intervalPressure(Long[] pressures) {
        return validate(IntervalPressureProperty.getInstance(), pressures, VALIDATE_ARRAY);
    }
    @Nonnull
    default T minTemperature (int temperature) {
        return validate(MinTemperatureProperty.getInstance(), temperature, VALIDATE_INT_POSITIVE);
    }

    @Nonnull
    default T maxTemperature (int temperature) {
        return validate(MaxTemperatureProperty.getInstance(), temperature, VALIDATE_INT_POSITIVE);
    }

    @Nonnull
    default T minPressure(long minPressure) {
        return validate(MinPressureProperty.getInstance(), minPressure, VALIDATE_LONG_POSITIVE);
    }

    @Nonnull
    default T maxPressure(long maxPressure) {
        return validate(MaxPressureProperty.getInstance(), maxPressure, VALIDATE_LONG_POSITIVE);
    }

    @Nonnull
    default T gas(FluidStack fluidStack) {
        return validate(PressurizedFluidStackProperty.getInstance(), fluidStack, VALIDATE_FLUIDSTACK);
    }

    //Getters for their values
    default Long[] getIntervalPressure() {
        return getRecipePropertyStorage() == null ? EMPTY_LONG_TWO_ARRAY :
                getRecipePropertyStorage().getRecipePropertyValue(IntervalPressureProperty.getInstance(), EMPTY_LONG_TWO_ARRAY);
    }

    default int getMinTemperature() {
        return getRecipePropertyStorage()== null ? 0 :
                getRecipePropertyStorage().getRecipePropertyValue(MinTemperatureProperty.getInstance(), 0);
    }

    default int getMaxTemperature() {
        return getRecipePropertyStorage()== null ? 0 :
                getRecipePropertyStorage().getRecipePropertyValue(MaxTemperatureProperty.getInstance(), 0);
    }

    default long getMinPressure() {
        return getRecipePropertyStorage() == null ? 0L :
                getRecipePropertyStorage().getRecipePropertyValue(MinPressureProperty.getInstance(), 0L);
    }

    default long getMaxPressure() {
        return getRecipePropertyStorage() == null ? 0L :
                getRecipePropertyStorage().getRecipePropertyValue(MaxPressureProperty.getInstance(), 0L);
    }


    default FluidStack getGas() {
        return getRecipePropertyStorage() == null ? null :
                getRecipePropertyStorage().getRecipePropertyValue(PressurizedFluidStackProperty.getInstance(), null);
    }


    default boolean applyPropertyHelper(@Nonnull String key, Object value) {

        switch (key) {
            case INTERVAL_PRESSURE_PROPERTY:
                this.intervalPressure(((Long[]) value).clone());
                return true;

            case MIN_TEMPERATURE_PROPERTY:
                this.minTemperature(((Number) value).intValue());
                return true;

            case MAX_TEMPERATURE_PROPERTY:
                this.maxTemperature(((Number) value).intValue());
                return true;

            case MIN_PRESSURE_PROPERTY:
                this.minPressure(((Number) value).longValue());
                return true;

            case MAX_PRESSURE_PROPERTY:
                this.maxPressure(((Number) value).longValue());
                return true;

            case PRESSURIZED_FLUIDSTACK_PROPERTY:
                this.gas(((FluidStack) value));
                return true;

            default:
                break;
        }
        return false;
    }


}
