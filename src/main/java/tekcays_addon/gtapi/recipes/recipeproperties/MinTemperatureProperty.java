package tekcays_addon.gtapi.recipes.recipeproperties;

import static tekcays_addon.gtapi.consts.TKCYAValues.MIN_TEMPERATURE_PROPERTY;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.recipeproperties.RecipeProperty;

public class MinTemperatureProperty extends RecipeProperty<Integer> {

    private static MinTemperatureProperty INSTANCE;

    private MinTemperatureProperty() {
        super(MIN_TEMPERATURE_PROPERTY, Integer.class);
    }

    public static MinTemperatureProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MinTemperatureProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(@NotNull Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("tkcya.recipe.temperature", castValue(value)), x, y, color);
    }
}
