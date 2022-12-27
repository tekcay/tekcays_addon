package tekcays_addon.api.recipes.recipeproperties;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import tekcays_addon.api.utils.IPressureFormatting;

import javax.annotation.Nonnull;

public class MaxPressureProperty extends PressureProperty implements IPressureFormatting {

    public static final String KEY = "maxPressure";
    @Override
    public String getKey() {
        return KEY;
    }
    private static MaxPressureProperty INSTANCE;

    private MaxPressureProperty() {
        super();
    }

    public static MaxPressureProperty getInstance() {
        if (INSTANCE == null) INSTANCE = new MaxPressureProperty();
        return INSTANCE;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int x, int y, int color, Object value) {
        Integer casted = castValue(value);
        if (isVacuum(casted)) minecraft.fontRenderer.drawString(I18n.format(
                "tkcya.recipe.max.pressure", convertPressureToMbar(casted)), x, y, color);
        else minecraft.fontRenderer.drawString(I18n.format(
                "tkcya.recipe.max.pressure", convertPressureToBar(casted)), x, y, color);
    }

}
