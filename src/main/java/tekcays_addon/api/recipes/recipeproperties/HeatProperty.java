package tekcays_addon.api.recipes.recipeproperties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;

import javax.annotation.Nonnull;

public class HeatProperty extends RecipeProperty<Double> {

    public static final String KEY = "heat";

    private static HeatProperty INSTANCE;

    private HeatProperty() {
        super(KEY, Double.class);
    }

    public static HeatProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HeatProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int x, int y, int color, Object value) {
    }
}
