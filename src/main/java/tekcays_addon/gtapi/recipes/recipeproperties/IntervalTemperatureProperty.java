package tekcays_addon.gtapi.recipes.recipeproperties;

import static tekcays_addon.gtapi.consts.TKCYAValues.INTERVAL_TEMPERATURE_PROPERTY;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import tekcays_addon.api.recipeproperties.RecipePropertiesHelper;

;

public class IntervalTemperatureProperty extends RecipeProperty<Integer[]> implements RecipePropertiesHelper {

    @Override
    public String getKey() {
        return INTERVAL_TEMPERATURE_PROPERTY;
    }

    private static IntervalTemperatureProperty INSTANCE;

    private IntervalTemperatureProperty() {
        super(INTERVAL_TEMPERATURE_PROPERTY, Integer[].class);
    }

    public static IntervalTemperatureProperty getInstance() {
        if (INSTANCE == null) INSTANCE = new IntervalTemperatureProperty();
        return INSTANCE;
    }

    // TODO refacto
    @Override
    public void drawInfo(@NotNull Minecraft minecraft, int x, int y, int color, Object value) {
        Integer[] casted = castValue(value);
        minecraft.fontRenderer.drawString(I18n.format(
                "tkcya.recipe.interval.temperature", casted[0], casted[1]), x, y, color);
    }
}
