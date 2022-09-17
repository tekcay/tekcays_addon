package tekcays_addon.api.recipes.recipeproperties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import javax.annotation.Nonnull;

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
    public void drawInfo(@Nonnull Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("gcys.recipe.temperature", castValue(value)), x, y, color);
    }
}
