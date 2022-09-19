package tekcays_addon.api.capability.impl;

import gregtech.api.metatileentity.MetaTileEntity;

public class AtmosphericHeatContainer extends HeatContainer {

    /**
     * Atmopsheric heat container which always remains at atmospheric
     */
    public AtmosphericHeatContainer(MetaTileEntity metaTileEntity) {
        super(metaTileEntity, 0, 0);
    }

    @Override
    public void setHeat(int amount) {/**/}
}
