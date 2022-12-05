package tekcays_addon.api.capability.impl;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import tekcays_addon.api.capability.IPressureContainer;
import tekcays_addon.api.capability.IPressureMachine;

public class PressureContainerMultiblockRecipeLogic extends MultiblockRecipeLogic {

    private int recipePressure = 0;

    public PressureContainerMultiblockRecipeLogic(RecipeMapMultiblockController tileEntity) {
        super(tileEntity);
    }

    protected IPressureContainer getPressureContainer() {
        return ((IPressureMachine) this.metaTileEntity).getPressureContainer();
    }

}
