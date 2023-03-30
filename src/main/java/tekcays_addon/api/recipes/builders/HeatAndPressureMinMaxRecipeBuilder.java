package tekcays_addon.api.recipes.builders;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.recipes.recipeproperties.RecipeProperty;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.ValidationResult;
import org.apache.commons.lang3.builder.ToStringBuilder;
import tekcays_addon.api.recipes.recipeproperties.*;
import tekcays_addon.api.utils.recipe.RecipeBuilderHelper;
import tekcays_addon.api.utils.recipe.RecipePropertyGetters;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;


public class HeatAndPressureMinMaxRecipeBuilder extends RecipeBuilder<HeatAndPressureMinMaxRecipeBuilder>
        implements RecipeBuilderHelper<HeatAndPressureMinMaxRecipeBuilder> {

    public HeatAndPressureMinMaxRecipeBuilder() {

    }

    public HeatAndPressureMinMaxRecipeBuilder(Recipe recipe, RecipeMap<HeatAndPressureMinMaxRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public static final List<RecipeProperty<?>> recipePropertiesInstances = new ArrayList<RecipeProperty<?>>() {{
        add(IntervalTemperatureProperty.getInstance());
        add(IntervalPressureProperty.getInstance());
        add(PressurizedFluidStackProperty.getInstance());
    }};

    public HeatAndPressureMinMaxRecipeBuilder(HeatAndPressureMinMaxRecipeBuilder builder) {
        super(builder);
    }

    @Override
    public HeatAndPressureMinMaxRecipeBuilder copy() {
        return new HeatAndPressureMinMaxRecipeBuilder(this);
    }

    @Override
    public boolean applyProperty(@Nonnull String key, Object value) {
        applyPropertyHelper(key, value);
        return super.applyProperty(key, value);
    }
    @Override
    public ValidationResult<Recipe> build() {
        //buildHelper();
        return super.build();
    }
    @Override
    public String toString() {
        RecipePropertyGetters recipePropertyGetters = new RecipePropertyGetters(getRecipePropertyStorage());
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("Temperature: ", recipePropertyGetters.getIntervalTemperature())
                .append("Pressure: ", recipePropertyGetters.getIntervalPressure())
                .append("Gas: ", recipePropertyGetters.getPressurizedFluidStack())
                .toString();
    }

    @Override
    public IRecipePropertyStorage getRecipePropertyStorage() {
        return this.recipePropertyStorage;
    }

    @Override
    public HeatAndPressureMinMaxRecipeBuilder getRecipeBuilder() {
        return this;
    }

    @Override
    public List<RecipeProperty<?>> getRecipePropertiesInstance() {
        return recipePropertiesInstances;
    }

    @Override
    public void setRecipeStatus(EnumValidationResult enumValidationResult) {
        recipeStatus = enumValidationResult;
    }

}
