package tekcays_addon.api.recipes.recipeproperties;

import gregicality.science.api.utils.NumberFormattingUtil;
import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import javax.annotation.Nonnull;

public class NoEnergyPressureProperty extends RecipeProperty<Double> {

    public static final String KEY = "pressure";

    private static NoEnergyPressureProperty INSTANCE;

    private NoEnergyPressureProperty() {
        super(KEY, Double.class);
    }

    public static NoEnergyPressureProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NoEnergyPressureProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int x, int y, int color, Object value) {
        Double casted = castValue(value);
        minecraft.fontRenderer.drawString(I18n.format(casted > 1 ? "gcys.recipe.pressure" : "gcys.recipe.vacuum",
                NumberFormattingUtil.formatDoubleToCompactString(casted)), x, y, color);
    }
}
