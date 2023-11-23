package tekcays_addon.common.covers;

import gregtech.api.cover.ICoverable;
import gregtech.common.covers.CoverRoboticArm;
import net.minecraft.util.EnumFacing;
import tekcays_addon.gtapi.capability.TKCYATileCapabilities;

public class CoverRoboticArmOverhauled extends CoverRoboticArm {

    public CoverRoboticArmOverhauled(ICoverable coverable, EnumFacing attachedSide, int tier, int itemsPerSecond) {
        super(coverable, attachedSide, tier, itemsPerSecond);
    }

    @Override
    public boolean canAttach() {
        return super.canAttach() && coverHolder.getCapability(TKCYATileCapabilities.CAPABILITY_ITEM_LOGISTIC, attachedSide) != null;
    }
}
