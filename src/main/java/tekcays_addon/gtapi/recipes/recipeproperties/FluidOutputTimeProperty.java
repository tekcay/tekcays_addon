package tekcays_addon.gtapi.recipes.recipeproperties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import tekcays_addon.api.recipeproperties.FluidOutputTime;
import tekcays_addon.api.recipeproperties.RecipePropertiesHelper;
import tekcays_addon.api.units.ITimeFormatting;

import javax.annotation.Nonnull;

import static tekcays_addon.gtapi.consts.TKCYAValues.FLUID_OUTPUT_TIME;

public class FluidOutputTimeProperty extends RecipeProperty<FluidOutputTime[]> implements ITimeFormatting, RecipePropertiesHelper {

    public static final String KEY = FLUID_OUTPUT_TIME;
    private static FluidOutputTimeProperty INSTANCE;

    protected FluidOutputTimeProperty() {
        super(KEY, FluidOutputTime[].class);
    }

    public static FluidOutputTimeProperty getInstance() {
        if (INSTANCE == null) INSTANCE = new FluidOutputTimeProperty();
        return INSTANCE;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int x, int y, int color, Object value) {
        FluidOutputTime[] casted = castValue(value);
        for (int i = 0; i < casted.length; i++) {
            minecraft.fontRenderer.drawString(
                    I18n.format(String.valueOf(convertTime(casted[i].getOutputTime()))),103, 6 + i * 22, color);
        }
    }
}
