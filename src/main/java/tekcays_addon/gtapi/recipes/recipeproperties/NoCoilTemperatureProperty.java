package tekcays_addon.gtapi.recipes.recipeproperties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import tekcays_addon.api.recipeproperties.RecipePropertiesHelper;

import javax.annotation.Nonnull;

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
    public void drawInfo(@Nonnull Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("tkcya.recipe.temperature", castValue(value)), x, y, color);
    }
}
