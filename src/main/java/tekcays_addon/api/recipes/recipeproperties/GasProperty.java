package tekcays_addon.api.recipes.recipeproperties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.FluidStack;

public class FluidStackProperty extends RecipeProperty<FluidStack> implements RecipePropertiesHelper {

    public static final String KEY = "fluidStack";

    @Override
    public void drawInfo(Minecraft minecraft, int i, int i1, int i2, Object o) {

    }

    @Override
    public String getKey() {
        return KEY;
    }

    private static FluidStackProperty INSTANCE;
    protected PressureProperty() {
        super(KEY, Integer.class);
    }
}
