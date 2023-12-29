package tekcays_addon.gtapi.recipes.recipeproperties;

import net.minecraft.client.Minecraft;

import org.jetbrains.annotations.NotNull;

import gregtech.api.items.toolitem.ToolOreDict;
import gregtech.api.recipes.recipeproperties.RecipeProperty;
import tekcays_addon.api.recipeproperties.RecipePropertiesHelper;
import tekcays_addon.gtapi.consts.TKCYAValues;

public class ToolProperty extends RecipeProperty<ToolOreDict> implements RecipePropertiesHelper {

    public static final String KEY = TKCYAValues.TOOL_ORE_DICT_PROPERTY;

    @Override
    public String getKey() {
        return KEY;
    }

    private static ToolProperty INSTANCE;

    private ToolProperty() {
        super(KEY, ToolOreDict.class);
    }

    public static ToolProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ToolProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(@NotNull Minecraft minecraft, int x, int y, int color, Object value) {}
}
