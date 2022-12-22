package tekcays_addon.api.capability.impl;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import tekcays_addon.api.capability.IHeatContainer;
import tekcays_addon.api.capability.IHeatMachine;
import tekcays_addon.api.capability.IPressureContainer;
import tekcays_addon.api.capability.IPressureMachine;

public class HeatedPressureContainerMultiblockRecipeLogic extends MultiblockRecipeLogic {

    private int recipeHeat = 0;
    private int recipePressure = 0;

    public HeatedPressureContainerMultiblockRecipeLogic(RecipeMapMultiblockController tileEntity) {
        super(tileEntity);
    }

    protected IHeatContainer getHeatContainer() {
        return ((IHeatMachine) this.metaTileEntity).getHeatContainer();
    }

    protected IPressureContainer getPressureContainer() {
        return ((IPressureMachine) this.metaTileEntity).getPressureContainer();
    }

}
