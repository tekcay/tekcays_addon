package tekcays_addon.gtapi.recipes.recipeproperties;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import tekcays_addon.api.recipeproperties.RecipePropertiesHelper;

public class NoCoilTemperatureProperty extends RecipeProperty<Integer> implements RecipePropertiesHelper {

    public static final String KEY = "temperature";

    @Override
    public String getKey() {
        return KEY;
    }

    private static NoCoilTemperatureProperty INSTANCE;

    private NoCoilTemperatureProperty() {
        super(KEY, Integer.class);
    }

    public static NoCoilTemperatureProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NoCoilTemperatureProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(@NotNull Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("tkcya.recipe.temperature", castValue(value)), x, y, color);
    }
}
