package tekcays_addon.api.capability.impl;

import gregicality.science.api.GCYSValues;
import gregtech.api.metatileentity.MetaTileEntity;

public class AtmosphericLaserContainer extends LaserContainer {

    /**
     * Atmopsheric pressure container which always remains at atmospheric
     *
     * @param volume         the volume of the container, must be nonzero
     */
    public AtmosphericLaserContainer(MetaTileEntity metaTileEntity, double volume) {
        super(metaTileEntity, GCYSValues.EARTH_PRESSURE * 0.9, GCYSValues.EARTH_PRESSURE * 1.1, volume);
    }

    @Override
    public void setParticles(double amount) {/**/}
}
