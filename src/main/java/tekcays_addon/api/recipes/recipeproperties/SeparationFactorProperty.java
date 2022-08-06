package tekcays_addon.api.recipes.recipeproperties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;

public class SeparationFactorProperty extends RecipeProperty<Integer> {
    public static final String KEY = "separation_factor";

    private static SeparationFactorProperty INSTANCE;

    private SeparationFactorProperty() {
        super(KEY, Integer.class);
    }

    public static SeparationFactorProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SeparationFactorProperty();
        }
        return INSTANCE;
    }


    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
    }




}
