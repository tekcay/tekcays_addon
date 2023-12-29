package tekcays_addon.gtapi.recipes.recipeproperties;

import static tekcays_addon.gtapi.consts.TKCYAValues.INTERVAL_PRESSURE_PROPERTY;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import tekcays_addon.api.recipeproperties.RecipePropertiesHelper;
import tekcays_addon.api.units.IPressureFormatting;

public class IntervalPressureProperty extends RecipeProperty<Integer[]>
                                      implements IPressureFormatting, RecipePropertiesHelper {

    @Override
    public String getKey() {
        return INTERVAL_PRESSURE_PROPERTY;
    }

    private static IntervalPressureProperty INSTANCE;

    private IntervalPressureProperty() {
        super(INTERVAL_PRESSURE_PROPERTY, Integer[].class);
    }

    public static IntervalPressureProperty getInstance() {
        if (INSTANCE == null) INSTANCE = new IntervalPressureProperty();
        return INSTANCE;
    }

    // TODO refacto
    @Override
    public void drawInfo(@NotNull Minecraft minecraft, int x, int y, int color, Object value) {
        Integer[] casted = castValue(value);
        String minPressure;
        String maxPressure;

        if (isVacuum(casted[0])) {
            minPressure = convertPressureToMbar(casted[0], false);
        } else minPressure = convertPressureToBar(casted[0], false);

        if (isVacuum(casted[1])) {
            maxPressure = convertPressureToMbar(casted[1], true);
        } else maxPressure = convertPressureToBar(casted[1], true);

        minecraft.fontRenderer.drawString(I18n.format(
                "tkcya.recipe.interval.pressure", minPressure, maxPressure), x, y, color);
    }
}
