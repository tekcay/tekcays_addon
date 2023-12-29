package tekcays_addon.gtapi.recipes.recipeproperties;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.recipeproperties.RecipeProperty;

public class NoEnergyTemperatureProperty extends RecipeProperty<Integer> {

    public static final String KEY = "temperature";

    private static NoEnergyTemperatureProperty INSTANCE;

    private NoEnergyTemperatureProperty() {
        super(KEY, Integer.class);
    }

    public static NoEnergyTemperatureProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NoEnergyTemperatureProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(@NotNull Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("tkcya.recipe.temperature", castValue(value)), x, y, color);
    }
}
