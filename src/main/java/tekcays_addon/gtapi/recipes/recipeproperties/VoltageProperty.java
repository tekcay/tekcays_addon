package tekcays_addon.gtapi.recipes.recipeproperties;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import org.jetbrains.annotations.NotNull;

import gregtech.api.GTValues;
import gregtech.api.recipes.recipeproperties.RecipeProperty;
import tekcays_addon.api.recipeproperties.RecipePropertiesHelper;

public class VoltageProperty extends RecipeProperty<Integer> implements RecipePropertiesHelper {

    public static final String KEY = "voltageTier";

    @Override
    public String getKey() {
        return KEY;
    }

    private static VoltageProperty INSTANCE;

    private VoltageProperty() {
        super(KEY, Integer.class);
    }

    public static VoltageProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new VoltageProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(@NotNull Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("tkcya.recipe.voltage", GTValues.VN[castValue(value)]), x, y,
                color);
    }
}
