package tekcays_addon.gtapi.recipes.recipeproperties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import javax.annotation.Nonnull;

import static tekcays_addon.gtapi.consts.TKCYAValues.MAX_TEMPERATURE_PROPERTY;

public class MaxTemperatureProperty extends RecipeProperty<Integer> {

    private static MaxTemperatureProperty INSTANCE;

    private MaxTemperatureProperty() {
        super(MAX_TEMPERATURE_PROPERTY, Integer.class);
    }

    public static MaxTemperatureProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MaxTemperatureProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("tkcya.recipe.temperature", castValue(value)), x, y, color);
    }
}
