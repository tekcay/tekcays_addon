package tekcays_addon.gtapi.recipes.recipeproperties;

import static tekcays_addon.api.units.PressureFormatting.*;
import static tekcays_addon.gtapi.consts.TKCYAValues.MAX_PRESSURE_PROPERTY;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import tekcays_addon.api.recipeproperties.RecipePropertiesHelper;

public class MaxPressureProperty extends RecipeProperty<Integer>
                                 implements RecipePropertiesHelper {

    @Override
    public String getKey() {
        return MAX_PRESSURE_PROPERTY;
    }

    private static MaxPressureProperty INSTANCE;

    private MaxPressureProperty() {
        super(MAX_PRESSURE_PROPERTY, Integer.class);
    }

    public static MaxPressureProperty getInstance() {
        if (INSTANCE == null) INSTANCE = new MaxPressureProperty();
        return INSTANCE;
    }

    @Override
    public void drawInfo(@NotNull Minecraft minecraft, int x, int y, int color, Object value) {
        Integer casted = castValue(value);
        if (isVacuum(casted)) minecraft.fontRenderer.drawString(I18n.format(
                "tkcya.recipe.max.pressure", convertPressureToMillibar(casted, true)), x, y, color);
        else minecraft.fontRenderer.drawString(I18n.format(
                "tkcya.recipe.max.pressure", convertPressureToBar(casted, true)), x, y, color);
    }
}
