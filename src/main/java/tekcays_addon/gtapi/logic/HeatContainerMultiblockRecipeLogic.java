package tekcays_addon.gtapi.logic;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import tekcays_addon.gtapi.capability.containers.IHeatContainer;
import tekcays_addon.gtapi.capability.machines.IHeatMachine;


public class HeatContainerMultiblockRecipeLogic extends MultiblockRecipeLogic {

    private int recipeHeat = 0;

    public HeatContainerMultiblockRecipeLogic(RecipeMapMultiblockController tileEntity) {
        super(tileEntity);
    }

    protected IHeatContainer getHeatContainer() {
        return ((IHeatMachine) this.metaTileEntity).getHeatContainer();
    }

}
