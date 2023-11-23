package tekcays_addon.common.covers;

import gregtech.api.cover.ICoverable;
import gregtech.common.covers.CoverConveyor;
import net.minecraft.util.EnumFacing;
import tekcays_addon.gtapi.capability.TKCYATileCapabilities;
import tekcays_addon.gtapi.capability.containers.Logistic;

public class CoverConveyorOverhauled extends CoverConveyor implements Logistic {


    public CoverConveyorOverhauled(ICoverable coverable, EnumFacing attachedSide, int tier, int itemsPerSecond) {
        super(coverable, attachedSide, tier, itemsPerSecond);
    }

    @Override
    public boolean canAttach() {
        return super.canAttach() && coverHolder.getCapability(TKCYATileCapabilities.CAPABILITY_ITEM_LOGISTIC, attachedSide) != null;
    }
}
