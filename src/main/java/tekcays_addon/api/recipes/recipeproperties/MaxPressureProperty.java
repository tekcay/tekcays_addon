package tekcays_addon.api.recipes.recipeproperties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import tekcays_addon.api.utils.IPressureFormatting;

import javax.annotation.Nonnull;

import static tekcays_addon.api.utils.TKCYAValues.MAX_PRESSURE_PROPERTY;

public class MaxPressureProperty extends RecipeProperty<Long> implements IPressureFormatting, RecipePropertiesHelper  {

    @Override
    public String getKey() {
        return MAX_PRESSURE_PROPERTY;
    }
    private static MaxPressureProperty INSTANCE;

    private MaxPressureProperty() {
        super(MAX_PRESSURE_PROPERTY, Long.class);
    }

    public static MaxPressureProperty getInstance() {
        if (INSTANCE == null) INSTANCE = new MaxPressureProperty();
        return INSTANCE;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int x, int y, int color, Object value) {
        Long casted = castValue(value);
        if (isVacuum(casted)) minecraft.fontRenderer.drawString(I18n.format(
                "tkcya.recipe.max.pressure", convertPressureToMbar(casted)), x, y, color);
        else minecraft.fontRenderer.drawString(I18n.format(
                "tkcya.recipe.max.pressure", convertPressureToBar(casted)), x, y, color);
    }

}
