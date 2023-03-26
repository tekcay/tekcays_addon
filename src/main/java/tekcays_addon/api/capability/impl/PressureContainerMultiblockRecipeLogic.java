package tekcays_addon.api.capability.impl;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.recipes.Recipe;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.capability.IPressureContainer;
import tekcays_addon.api.capability.IPressureMachine;
import tekcays_addon.api.recipes.recipeproperties.MaxPressureProperty;
import tekcays_addon.api.recipes.recipeproperties.MinPressureProperty;
import tekcays_addon.api.recipes.recipeproperties.NoCoilTemperatureProperty;
import tekcays_addon.api.recipes.recipeproperties.PressurizedFluidStackProperty;

public class PressureContainerMultiblockRecipeLogic extends MultiblockRecipeLogic {

    private long recipeMinPressure = 0;
    private long recipeMaxPressure = 0;
    private int recipeTemperature = 0;
    private FluidStack recipeGas = null;


    public PressureContainerMultiblockRecipeLogic(RecipeMapMultiblockController tileEntity) {
        super(tileEntity);
    }

    protected IPressureContainer getPressureContainer() {
        return ((IPressureMachine) this.metaTileEntity).getPressureContainer();
    }

    @Override
    protected void setupRecipe(Recipe recipe) {
        super.setupRecipe(recipe);
        if (recipe.getRecipePropertyStorage() != null && recipe.hasProperty(MinPressureProperty.getInstance())) {
            this.recipeMinPressure = recipe.getProperty(MinPressureProperty.getInstance(), 0L);
        }

        if (recipe.getRecipePropertyStorage() != null && recipe.hasProperty(MaxPressureProperty.getInstance())) {
            this.recipeMaxPressure = recipe.getProperty(MaxPressureProperty.getInstance(), 0L);
        }

        if (recipe.getRecipePropertyStorage() != null && recipe.hasProperty(NoCoilTemperatureProperty.getInstance())) {
            this.recipeTemperature = recipe.getProperty(NoCoilTemperatureProperty.getInstance(), 0);
        }

        if (recipe.getRecipePropertyStorage() != null && recipe.hasProperty(PressurizedFluidStackProperty.getInstance())) {
            this.recipeGas = recipe.getProperty(PressurizedFluidStackProperty.getInstance(), null);
        }
    }

    public void invalidate() {
        previousRecipe = null;
        progressTime = 0;
        maxProgressTime = 0;
        recipeEUt = 0;
        fluidOutputs = null;
        itemOutputs = null;
        this.recipeTemperature = 0;
        this.recipeMinPressure = 0;
        this.recipeMaxPressure = 0;
        this.recipeGas = null;
        setActive(false); // this marks dirty for us
    }


}
