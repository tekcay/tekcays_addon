package tekcays_addon.gtapi.logic;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.recipes.Recipe;
import tekcays_addon.gtapi.recipes.builders.MultiAmperageRecipeBuilder;
import tekcays_addon.gtapi.recipes.recipeproperties.MultiAmperageProperty;
import tekcays_addon.gtapi.recipes.recipeproperties.VoltageProperty;

import javax.annotation.Nonnull;

/**
 * A {@link MultiblockRecipeLogic} that works only with recipes built with {@link MultiAmperageRecipeBuilder}.
 * Recipes will drain energy with a specific {@code voltage} and {@code amperage}.
 * <br>
 * <br>
 * <b>!!IMPORTANT!!</b>: overclock is <b>disabled!</b>
 *  <br>
 *  <br>
 */
public class MultiAmperageLogic extends MultiblockRecipeLogic {

    public MultiAmperageLogic(RecipeMapMultiblockController tileEntity) {
        super(tileEntity);
    }

    /**
     * Overclock is disabled.
     * @param recipe the recipe to run
     * @return
     */
    @Nonnull
    @Override
    protected int[] calculateOverclock(Recipe recipe) {
        return new int[]{0, recipe.getDuration()};
    }

    public long getAmperage() {
        RecipeMapMultiblockController controller = (RecipeMapMultiblockController) metaTileEntity;
        return controller.getEnergyContainer().getInputAmperage();
    }

    public long getVoltage() {
        RecipeMapMultiblockController controller = (RecipeMapMultiblockController) metaTileEntity;
        return controller.getEnergyContainer().getInputVoltage();
    }

    public int getRecipeAmperage(Recipe recipe) {
        return recipe.getProperty(MultiAmperageProperty.getInstance(), 0);
    }

    public int getRecipeVoltage(Recipe recipe) {
        return recipe.getProperty(VoltageProperty.getInstance(), 0);
    }

    public boolean isAmperageValid(Recipe recipe) {
        return getAmperage() >= getRecipeAmperage(recipe);
    }

    public boolean isVoltageValid(Recipe recipe) {
        return getVoltage() >= getRecipeVoltage(recipe);
    }

    public int getEUt(Recipe recipe) {
        return getRecipeVoltage(recipe) * getRecipeAmperage(recipe);
    }

    @Override
    public void update() {
        if (recipeEUt == 0) return;

        if (drawEnergy(recipeEUt, true)) {
            setActive(true);
            drawEnergy(recipeEUt, false);
        } else setActive(false);
    }


    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe) {
        recipeEUt = getEUt(recipe);
        return isAmperageValid(recipe) && isVoltageValid(recipe);
    }

}
