package tekcays_addon.gtapi.recipes.recipeproperties;

import static tekcays_addon.gtapi.consts.TKCYAValues.PRESSURIZED_FLUIDSTACK_PROPERTY;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import tekcays_addon.api.recipeproperties.RecipePropertiesHelper;

public class PressurizedFluidStackProperty extends RecipeProperty<FluidStack> implements RecipePropertiesHelper {

    @Override
    public String getKey() {
        return PRESSURIZED_FLUIDSTACK_PROPERTY;
    }

    private static PressurizedFluidStackProperty INSTANCE;

    protected PressurizedFluidStackProperty() {
        super(PRESSURIZED_FLUIDSTACK_PROPERTY, FluidStack.class);
    }

    public static PressurizedFluidStackProperty getInstance() {
        if (INSTANCE == null) INSTANCE = new PressurizedFluidStackProperty();
        return INSTANCE;
    }

    @Override
    public void drawInfo(@NotNull Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(
                I18n.format("tkcya.recipe.pressurized-fluid-stack", ((FluidStack) value).getLocalizedName()), x, y,
                color);
    }
}
