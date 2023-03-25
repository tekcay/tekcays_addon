package tekcays_addon.api.recipes.recipeproperties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;

public class PressurizedFluidStackProperty extends RecipeProperty<FluidStack> implements RecipePropertiesHelper {

    public static final String KEY = "fluidStack";


    @Override
    public String getKey() {
        return KEY;
    }

    private static PressurizedFluidStackProperty INSTANCE;
    protected PressurizedFluidStackProperty() {
        super(KEY, FluidStack.class);
    }

    public static PressurizedFluidStackProperty getInstance() {
        if (INSTANCE == null) INSTANCE = new PressurizedFluidStackProperty();
        return INSTANCE;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("tkcya.recipe.pressurized-fluid-stack", ((FluidStack) value).getLocalizedName()), x, y, color);
    }
}
