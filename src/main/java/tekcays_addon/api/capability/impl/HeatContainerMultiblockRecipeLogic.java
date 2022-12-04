package tekcays_addon.api.capability.impl;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.recipes.Recipe;
import gregtech.common.ConfigHolder;
import net.minecraft.nbt.NBTTagCompound;
import tekcays_addon.api.capability.IHeatContainer;
import tekcays_addon.api.capability.IHeatMachine;
import tekcays_addon.api.recipes.recipeproperties.HeatProperty;

import javax.annotation.Nonnull;

public class HeatContainerMultiblockRecipeLogic extends MultiblockRecipeLogic {

    private int recipeHeat = 0;

    public HeatContainerMultiblockRecipeLogic(RecipeMapMultiblockController tileEntity) {
        super(tileEntity);
    }

    protected IHeatContainer getHeatContainer() {
        return ((IHeatMachine) this.metaTileEntity).getHeatContainer();
    }

}