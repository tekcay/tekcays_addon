package tekcays_addon.gtapi.recipes.recipeproperties;

import gregtech.api.items.toolitem.ToolOreDict;
import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import tekcays_addon.api.recipeproperties.RecipePropertiesHelper;
import tekcays_addon.gtapi.consts.TKCYAValues;

import javax.annotation.Nonnull;

public class NoEnergyNoDurationProperty extends RecipeProperty<Boolean> implements RecipePropertiesHelper {

    public static final String KEY = TKCYAValues.TOOL_ORE_DICT_PROPERTY;

    @Override
    public String getKey() {
        return KEY;
    }

    private static NoEnergyNoDurationProperty INSTANCE;

    private NoEnergyNoDurationProperty() {
        super(KEY, Boolean.class);
    }

    public static NoEnergyNoDurationProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NoEnergyNoDurationProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int x, int y, int color, Object value) {

    }
}
