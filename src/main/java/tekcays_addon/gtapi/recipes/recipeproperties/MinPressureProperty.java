package tekcays_addon.gtapi.recipes.recipeproperties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import tekcays_addon.api.recipeproperties.RecipePropertiesHelper;
import tekcays_addon.api.units.IPressureFormatting;

import javax.annotation.Nonnull;

import static tekcays_addon.gtapi.consts.TKCYAValues.MIN_PRESSURE_PROPERTY;

public class MinPressureProperty extends RecipeProperty<Integer> implements IPressureFormatting, RecipePropertiesHelper {

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
    public void drawInfo(@Nonnull Minecraft minecraft, int x, int y, int color, Object value) {
        Integer casted = castValue(value);
        if (isVacuum(casted)) minecraft.fontRenderer.drawString(I18n.format(
                "tkcya.recipe.min.pressure", convertPressureToMbar(casted, true)), x, y, color);
        else minecraft.fontRenderer.drawString(I18n.format(
                "tkcya.recipe.min.pressure", convertPressureToBar(casted, true)), x, y, color);
    }

}
