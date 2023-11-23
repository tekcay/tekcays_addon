package tekcays_addon.common.covers;

import gregtech.api.cover.ICoverable;
import gregtech.common.covers.CoverFluidRegulator;
import net.minecraft.util.EnumFacing;
import tekcays_addon.gtapi.capability.TKCYATileCapabilities;

public class CoverFluidRegulatorOverhauled extends CoverFluidRegulator {

    public CoverFluidRegulatorOverhauled(ICoverable coverHolder, EnumFacing attachedSide, int tier, int mbPerTick) {
        super(coverHolder, attachedSide, tier, mbPerTick);
    }

    @Override
    public boolean canAttach() {
        return super.canAttach() && coverHolder.getCapability(TKCYATileCapabilities.CAPABILITY_FLUID_LOGISTIC, attachedSide) != null;
    }
}
