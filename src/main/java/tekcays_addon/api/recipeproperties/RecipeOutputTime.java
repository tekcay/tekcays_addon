package tekcays_addon.api.recipeproperties;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class RecipeOutputTime {

    private int outputTime;
    private ItemStack outputItemStack;
    private FluidStack outputFluidStack;

    public RecipeOutputTime(int outputTime, FluidStack outputFluidStack) {
        this.outputTime = outputTime;
        this.outputFluidStack = outputFluidStack;
    }

    public RecipeOutputTime(int outputTime, ItemStack outputItemStack) {
        this.outputTime = outputTime;
        this.outputItemStack = outputItemStack;
    }

    public RecipeOutputTime(int outputTime, ItemStack outputItemStack, FluidStack outputFluidStack) {
        this.outputTime = outputTime;
        this.outputItemStack = outputItemStack;
        this.outputFluidStack = outputFluidStack;
    }



}
