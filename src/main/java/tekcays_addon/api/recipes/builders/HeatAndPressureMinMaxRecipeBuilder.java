package tekcays_addon.api.recipes.builders;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.recipes.recipeproperties.RecipeProperty;
import gregtech.api.recipes.recipeproperties.RecipePropertyStorage;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import gregtech.api.util.ValidationResult;
import org.apache.commons.lang3.builder.ToStringBuilder;
import tekcays_addon.api.recipes.recipeproperties.*;
import tekcays_addon.api.utils.TKCYALog;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class HeatAndPressureMinMaxRecipeBuilder extends RecipeBuilder<HeatAndPressureMinMaxRecipeBuilder>
        implements RecipeBuilderHelper<HeatAndPressureMinMaxRecipeBuilder> {

    @SuppressWarnings("unused")
    public HeatAndPressureMinMaxRecipeBuilder() {

    }

    @SuppressWarnings("unused")
    public HeatAndPressureMinMaxRecipeBuilder(Recipe recipe, RecipeMap<HeatAndPressureMinMaxRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public static final List<RecipeProperty> recipePropertiesInstances = new ArrayList<RecipeProperty>() {{
        add(NoCoilTemperatureProperty.getInstance());
        add(MinPressureProperty.getInstance());
        add(MaxPressureProperty.getInstance());
        add(GasProperty.getInstance());
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
        buildHelper();
        return super.build();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("Temperature", getTemperature())
                .append("MinPressure", getMinPressure())
                .append("MaxPressure", getMaxPressure())
                .append("Gas:", getGas())
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
    public List<RecipeProperty> getRecipePropertyInstance() {
        return recipePropertiesInstances;
    }

    @Override
    public void setRecipeStatus(EnumValidationResult enumValidationResult) {
        recipeStatus = enumValidationResult;
    }


}
