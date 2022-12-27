package tekcays_addon.api.recipes.recipeproperties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import gregtech.api.unification.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import javax.annotation.Nonnull;

public class GasProperty extends RecipeProperty<Material> implements RecipePropertiesHelper {

    public static final String KEY = "fluidStack";


    @Override
    public String getKey() {
        return KEY;
    }

    private static GasProperty INSTANCE;
    protected GasProperty() {
        super(KEY, Material.class);
    }

    public static GasProperty getInstance() {
        if (INSTANCE == null) INSTANCE = new GasProperty();
        return INSTANCE;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format(
                "tkcya.recipe.gas", ((Material) value).getLocalizedName()), x, y, color);
    }
}
