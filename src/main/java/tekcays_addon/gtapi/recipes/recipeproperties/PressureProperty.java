package tekcays_addon.gtapi.recipes.recipeproperties;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import tekcays_addon.api.recipeproperties.RecipePropertiesHelper;
import tekcays_addon.api.units.IPressureFormatting;

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
    public void drawInfo(@NotNull Minecraft minecraft, int x, int y, int color, Object value) {
        Integer casted = castValue(value);
        if (isVacuum(casted)) minecraft.fontRenderer.drawString(I18n.format(
                "tkcya.recipe.vacuum", convertPressureToMbar(casted, true)), x, y, color);
        else minecraft.fontRenderer.drawString(I18n.format(
                "tkcya.recipe.pressure", convertPressureToBar(casted, true)), x, y, color);
    }
}
