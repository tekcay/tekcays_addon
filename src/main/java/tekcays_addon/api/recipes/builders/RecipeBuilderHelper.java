package tekcays_addon.api.recipes.builders;

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

            if (recipeProperty instanceof PressurizedFluidStackProperty) {
                PressurizedFluidStackProperty pressurizedFluidStackProperty = (PressurizedFluidStackProperty) recipeProperty;
                if (recipePropertyStorage.hasRecipeProperty(pressurizedFluidStackProperty)) {
                    if (recipePropertyStorage.getRecipePropertyValue(pressurizedFluidStackProperty, null) == null) {
                        recipePropertyStorage.store(recipeProperty, null);
                    }
                } else recipePropertyStorage.store(recipeProperty, null);
                continue;
            }

            if (recipeProperty instanceof MinPressureProperty) {
                MinPressureProperty minPressureProperty = (MinPressureProperty) recipeProperty;
                if (recipePropertyStorage.hasRecipeProperty(minPressureProperty)) {
                    if (recipePropertyStorage.getRecipePropertyValue(minPressureProperty, 0L) <= 0) {
                        recipePropertyStorage.store(recipeProperty, 0L);
                    }
                } else recipePropertyStorage.store(recipeProperty, 0L);
                continue;
            }

            if (recipeProperty instanceof MaxPressureProperty) {
                MaxPressureProperty maxPressureProperty = (MaxPressureProperty) recipeProperty;
                if (recipePropertyStorage.hasRecipeProperty(maxPressureProperty)) {
                    if (recipePropertyStorage.getRecipePropertyValue(maxPressureProperty, 0L) <= 0) {
                        recipePropertyStorage.store(recipeProperty, 0L);
                    }
                } else recipePropertyStorage.store(recipeProperty, 0L);
                continue;
            }

            if (recipeProperty instanceof NoCoilTemperatureProperty) {
                NoCoilTemperatureProperty temperatureProperty = (NoCoilTemperatureProperty) recipeProperty;
                if (recipePropertyStorage.hasRecipeProperty(temperatureProperty)) {
                    if (recipePropertyStorage.getRecipePropertyValue(temperatureProperty, 0) <= 0) {
                        recipePropertyStorage.store(temperatureProperty, 0);
                    }
                } else recipePropertyStorage.store(temperatureProperty, 0);
            }
        }
    }


    default T validateValue(int value, RecipeProperty<?> recipeProperty) {
        T recipeBuilder =  getRecipeBuilder();
        if (value <= 0) {
            TKCYALog.logger.error(recipeProperty.getKey() + " cannot be less than or equal to 0", new IllegalArgumentException());
            setRecipeStatus(EnumValidationResult.INVALID);
        }
        getRecipeBuilder().applyProperty(recipeProperty, value);
        return recipeBuilder;
    }

    default T validateValue(long value, RecipeProperty<?> recipeProperty) {
        T recipeBuilder =  getRecipeBuilder();
        if (value <= 0) {
            TKCYALog.logger.error(recipeProperty.getKey() + " cannot be less than or equal to 0", new IllegalArgumentException());
            setRecipeStatus(EnumValidationResult.INVALID);
        }
        getRecipeBuilder().applyProperty(recipeProperty, value);
        return recipeBuilder;
    }

    default T validateValue(FluidStack fluidStack, RecipeProperty<?> recipeProperty) {
        T recipeBuilder =  getRecipeBuilder();
        if (fluidStack == null) {
            TKCYALog.logger.error(recipeProperty.getKey() + "fluidStack is null!", new IllegalArgumentException());
            setRecipeStatus(EnumValidationResult.INVALID);
        }
        getRecipeBuilder().applyProperty(recipeProperty, fluidStack);
        return recipeBuilder;
    }

    //RecipeProperties
    @Nonnull
    default T temperature (int temperature) {
        return validateValue(temperature, NoCoilTemperatureProperty.getInstance());
    }

    @Nonnull
    default T minPressure(long minPressure) {
        return validateValue(minPressure, MinPressureProperty.getInstance());
    }

    @Nonnull
    default T maxPressure(long maxPressure) {
        return validateValue(maxPressure, MaxPressureProperty.getInstance());
    }

    @Nonnull
    default T gas(FluidStack fluidStack) {
        return validateValue(fluidStack, PressurizedFluidStackProperty.getInstance());
    }

    //Getters for their values
    default int getTemperature() {
        return getRecipePropertyStorage()== null ? 0 :
                getRecipePropertyStorage().getRecipePropertyValue(NoCoilTemperatureProperty.getInstance(), 0);
    }

    default long getMinPressure() {
        return getRecipePropertyStorage() == null ? 0 :
                getRecipePropertyStorage().getRecipePropertyValue(MinPressureProperty.getInstance(), 0L);
    }

    default long getMaxPressure() {
        return getRecipePropertyStorage() == null ? 0 :
                getRecipePropertyStorage().getRecipePropertyValue(MaxPressureProperty.getInstance(), 0L);
    }

    default FluidStack getGas() {
        return getRecipePropertyStorage() == null ? null :
                getRecipePropertyStorage().getRecipePropertyValue(PressurizedFluidStackProperty.getInstance(), null);
    }


    default boolean applyPropertyHelper(@Nonnull String key, Object value) {
        for (RecipeProperty<?> recipeProperty : getRecipePropertiesInstance()) {
            if (recipeProperty instanceof NoCoilTemperatureProperty) {
                return applyTemperatureHelper(key, value);
            }
            if (recipeProperty instanceof MinPressureProperty) {
                return applyMinPressureHelper(key, value);
            }
            if (recipeProperty instanceof MaxPressureProperty) {
                return applyMaxPressureHelper(key, value);
            }
            if (recipeProperty instanceof PressurizedFluidStackProperty) {
                return applyGasHelper(key, value);
            }
        }
        return false;
    }

    default boolean applyTemperatureHelper(@Nonnull String key, Object value) {
        if (!key.equals(NoCoilTemperatureProperty.KEY)) return false;
        this.temperature(((Number) value).intValue());
        return true;
    }

    default boolean applyMinPressureHelper(@Nonnull String key, Object value) {
        if (!key.equals(MIN_PRESSURE_PROPERTY)) return false;
        this.minPressure(((Number) value).longValue());
        return true;
    }

    default boolean applyMaxPressureHelper(@Nonnull String key, Object value) {
        if (!key.equals(MAX_PRESSURE_PROPERTY)) return false;
        this.maxPressure(((Number) value).longValue());
        return true;
    }

    default boolean applyGasHelper(@Nonnull String key, Object value) {
        if (!key.equals(PRESSURIZED_FLUIDSTACK_PROPERTY)) return false;
        this.gas(((FluidStack) value));
        return true;
    }






}
