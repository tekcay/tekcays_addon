package tekcays_addon.api.logic;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.recipes.Recipe;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.capability.containers.IPressureContainer;
import tekcays_addon.api.capability.machines.IPressureMachine;
import tekcays_addon.api.recipes.recipeproperties.MaxPressureProperty;
import tekcays_addon.api.recipes.recipeproperties.MinPressureProperty;
import tekcays_addon.api.recipes.recipeproperties.NoCoilTemperatureProperty;
import tekcays_addon.api.recipes.recipeproperties.PressurizedFluidStackProperty;

public class PressureContainerMultiblockRecipeLogic extends MultiblockRecipeLogic {

    private int recipeMinPressure = 0;
    private int recipeMaxPressure = 0;
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
            this.recipeMinPressure = recipe.getProperty(MinPressureProperty.getInstance(), 0);
        }

        if (recipe.getRecipePropertyStorage() != null && recipe.hasProperty(MaxPressureProperty.getInstance())) {
            this.recipeMaxPressure = recipe.getProperty(MaxPressureProperty.getInstance(), 0);
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
