package tekcays_addon.api.recipes.recipeproperties;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import tekcays_addon.api.utils.IPressureFormatting;

import javax.annotation.Nonnull;

public class MinPressureProperty extends PressureProperty implements IPressureFormatting {

    public static final String KEY = "minPressure";
    @Override
    public String getKey() {
        return KEY;
    }
    private static MinPressureProperty INSTANCE;

    private MinPressureProperty() {
        super();
    }

    public static MinPressureProperty getInstance() {
        if (INSTANCE == null) INSTANCE = new MinPressureProperty();
        return INSTANCE;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int x, int y, int color, Object value) {
        Integer casted = castValue(value);
        if (isVacuum(casted)) minecraft.fontRenderer.drawString(I18n.format(
                "tkcya.recipe.min.pressure", convertPressureToMbar(casted)), x, y, color);
        else minecraft.fontRenderer.drawString(I18n.format(
                "tkcya.recipe.min.pressure", convertPressureToBar(casted)), x, y, color);
    }

}
