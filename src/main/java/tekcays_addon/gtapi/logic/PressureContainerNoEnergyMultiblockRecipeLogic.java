package tekcays_addon.gtapi.logic;

import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import tekcays_addon.api.metatileentity.LogicType;
import tekcays_addon.gtapi.capability.containers.IPressureContainer;
import tekcays_addon.gtapi.capability.machines.IPressureMachine;

public class PressureContainerNoEnergyMultiblockRecipeLogic extends ModulableLogic {

    public PressureContainerNoEnergyMultiblockRecipeLogic(RecipeMapMultiblockController tileEntity) {
        super(tileEntity, LogicType.NO_ENERGY, LogicType.NO_OVERCLOCK);
    }

    protected IPressureContainer getPressureContainer() {
        return ((IPressureMachine) this.metaTileEntity).getPressureContainer();
    }


}
