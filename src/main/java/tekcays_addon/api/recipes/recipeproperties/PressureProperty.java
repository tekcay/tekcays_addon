package tekcays_addon.api.recipes.recipeproperties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import tekcays_addon.api.utils.IPressureFormatting;

import javax.annotation.Nonnull;

public class PressureProperty extends RecipeProperty<Integer> implements IPressureFormatting, RecipePropertiesHelper {

    public static final String KEY = "pressure";
    private static PressureProperty INSTANCE;

    protected PressureProperty() {
        super(KEY, Integer.class);
    }

    public static PressureProperty getInstance() {
        if (INSTANCE == null) INSTANCE = new PressureProperty();
        return INSTANCE;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int x, int y, int color, Object value) {
        Integer casted = castValue(value);
         if (isVacuum(casted)) minecraft.fontRenderer.drawString(I18n.format(
                 "tkcya.recipe.vacuum", convertPressureToMbar(casted)), x, y, color);
         else minecraft.fontRenderer.drawString(I18n.format(
                 "tkcya.recipe.pressure", convertPressureToBar(casted)), x, y, color);
    }
}
