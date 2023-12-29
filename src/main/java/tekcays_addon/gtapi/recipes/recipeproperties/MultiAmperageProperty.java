package tekcays_addon.gtapi.recipes.recipeproperties;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import tekcays_addon.api.recipeproperties.RecipePropertiesHelper;

public class MultiAmperageProperty extends RecipeProperty<Integer> implements RecipePropertiesHelper {

    public static final String KEY = "amperage";

    @Override
    public String getKey() {
        return KEY;
    }

    private static MultiAmperageProperty INSTANCE;

    private MultiAmperageProperty() {
        super(KEY, Integer.class);
    }

    public static MultiAmperageProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MultiAmperageProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(@NotNull Minecraft minecraft, int x, int y, int color, Object value) {
        Integer amperage = castValue(value);
        minecraft.fontRenderer.drawString(I18n.format("tkcya.recipe.amperage", amperage), x, y, color);
    }
}
