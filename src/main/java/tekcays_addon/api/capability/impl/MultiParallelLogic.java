package tekcays_addon.api.capability.impl;

import gregtech.api.metatileentity.multiblock.ParallelLogicType;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.recipeproperties.RecipePropertyStorage;
import tekcays_addon.api.metatileentity.mutiblock.RecipeMapMultiblockNoEnergyController;

import javax.annotation.Nonnull;

import static gregtech.api.recipes.logic.OverclockingLogic.standardOverclockingLogic;


/**
 * General Recipe Handler for non-energy consumming Multiblocks.
 * Will do up to the passed value of items in one process.
 * Not recommended to use this Handler if you do not
 * need multi-recipe logic for your Multi.
 */
public class MultiParallelLogic extends MultiblockNoEnergyRecipeLogic {

    public MultiParallelLogic(RecipeMapMultiblockNoEnergyController tileEntity) {
        super(tileEntity);
    }

    @Override
    public ParallelLogicType getParallelLogicType() {
        return ParallelLogicType.APPEND_ITEMS;
    }

    @Override
    public void applyParallelBonus(@Nonnull RecipeBuilder<?> builder) {
        // the builder automatically multiplies by -1, so nothing extra is needed here
        builder.duration(builder.getDuration() / builder.getParallel());
    }

}
