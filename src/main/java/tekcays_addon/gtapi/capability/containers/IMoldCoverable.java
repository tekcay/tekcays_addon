package tekcays_addon.gtapi.capability.containers;

import gregtech.api.unification.material.Materials;
import tekcays_addon.api.covers.molds.CoverMoldWrapper;

public interface IMoldCoverable {

    CoverMoldWrapper getCoverMoldWrapper();

    boolean canMoldCoverBePlaced();

    default int getNeededFluidAmount() {
        return (int) getCoverMoldWrapper().getOutputPrefix().getMaterialAmount(Materials.Air);
    }
}
