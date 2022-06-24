package tekcays_addon.api.recipes.recipeproperties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import gregtech.api.unification.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.apache.commons.lang3.Validate;

import java.util.Map;
import java.util.TreeMap;

public class PressureProperty extends RecipeProperty<Integer> {
    public static final String KEY = "pressure";

    private static PressureProperty INSTANCE;

    private PressureProperty() {
        super(KEY, Integer.class);
    }

    public static PressureProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PressureProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("gregtech.recipe.pressure",
                value, getMinTierForPressure(castValue(value))), x, y, color);
    }

}
