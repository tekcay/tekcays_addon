package tekcays_addon.gtapi.recipes.recipeproperties;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import tekcays_addon.api.recipeproperties.RecipePropertiesHelper;

public class HeatProperty extends RecipeProperty<Integer> implements RecipePropertiesHelper {

    public static final String KEY = "heat";

    @Override
    public String getKey() {
        return KEY;
    }

    private static HeatProperty INSTANCE;

    private HeatProperty() {
        super(KEY, Integer.class);
    }

    public static HeatProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HeatProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(@NotNull Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("tkcya.recipe.heat", castValue(value)), x, y, color);
    }
}
