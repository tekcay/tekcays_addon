package tekcays_addon.gtapi.recipes.recipeproperties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import tekcays_addon.api.recipeproperties.RecipePropertiesHelper;

import javax.annotation.Nonnull;

public class MultiAmperageProperty extends RecipeProperty<Long> implements RecipePropertiesHelper {

    public static final String KEY = "amperage";

    @Override
    public String getKey() {
        return KEY;
    }

    private static MultiAmperageProperty INSTANCE;

    private MultiAmperageProperty() {
        super(KEY, Long.class);
    }

    public static MultiAmperageProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MultiAmperageProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("tkcya.recipe.amperage", castValue(value)), x, y, color);
    }


}
