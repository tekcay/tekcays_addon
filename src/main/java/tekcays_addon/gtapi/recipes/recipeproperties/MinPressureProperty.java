package tekcays_addon.gtapi.recipes.recipeproperties;

import static tekcays_addon.gtapi.consts.TKCYAValues.MIN_PRESSURE_PROPERTY;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import tekcays_addon.api.recipeproperties.RecipePropertiesHelper;
import tekcays_addon.api.units.IPressureFormatting;

public class MinPressureProperty extends RecipeProperty<Integer>
                                 implements IPressureFormatting, RecipePropertiesHelper {

    @Override
    public String getKey() {
        return MIN_PRESSURE_PROPERTY;
    }

    private static MinPressureProperty INSTANCE;

    private MinPressureProperty() {
        super(MIN_PRESSURE_PROPERTY, Integer.class);
    }

    public static MinPressureProperty getInstance() {
        if (INSTANCE == null) INSTANCE = new MinPressureProperty();
        return INSTANCE;
    }

    @Override
    public void drawInfo(@NotNull Minecraft minecraft, int x, int y, int color, Object value) {
        Integer casted = castValue(value);
        if (isVacuum(casted)) minecraft.fontRenderer.drawString(I18n.format(
                "tkcya.recipe.min.pressure", convertPressureToMbar(casted, true)), x, y, color);
        else minecraft.fontRenderer.drawString(I18n.format(
                "tkcya.recipe.min.pressure", convertPressureToBar(casted, true)), x, y, color);
    }
}
