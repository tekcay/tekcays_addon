package tekcays_addon.api.recipes.recipeproperties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import tekcays_addon.api.utils.IPressureFormatting;

import javax.annotation.Nonnull;

import static tekcays_addon.api.utils.TKCYAValues.MIN_PRESSURE_PROPERTY;

public class MinPressureProperty extends RecipeProperty<Long> implements IPressureFormatting, RecipePropertiesHelper  {

    @Override
    public String getKey() {
        return MIN_PRESSURE_PROPERTY;
    }
    private static MinPressureProperty INSTANCE;

    private MinPressureProperty() {
        super(MIN_PRESSURE_PROPERTY, Long.class);
    }

    public static MinPressureProperty getInstance() {
        if (INSTANCE == null) INSTANCE = new MinPressureProperty();
        return INSTANCE;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int x, int y, int color, Object value) {
        Long casted = castValue(value);
        if (isVacuum(casted)) minecraft.fontRenderer.drawString(I18n.format(
                "tkcya.recipe.min.pressure", convertPressureToMbar(casted, true)), x, y, color);
        else minecraft.fontRenderer.drawString(I18n.format(
                "tkcya.recipe.min.pressure", convertPressureToBar(casted, true)), x, y, color);
    }

}
