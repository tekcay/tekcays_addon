package tekcays_addon.gtapi.logic;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.recipes.Recipe;

public class NoOverclockLogic extends MultiblockRecipeLogic {

    public NoOverclockLogic(RecipeMapMultiblockController tileEntity) {
        super(tileEntity);
    }

    //No Overclock
    @Override
    protected int[] calculateOverclock(Recipe recipe) {
        return new int[]{0, recipe.getDuration()};
    }

}
