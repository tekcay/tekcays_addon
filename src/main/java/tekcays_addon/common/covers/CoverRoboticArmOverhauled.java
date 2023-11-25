package tekcays_addon.common.covers;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.cover.ICoverable;
import gregtech.common.covers.CoverRoboticArm;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.IItemHandler;
import tekcays_addon.api.covers.CoverMethods;
import tekcays_addon.gtapi.capability.TKCYATileCapabilities;
import tekcays_addon.gtapi.capability.containers.LogisticContainer;

public class CoverRoboticArmOverhauled extends CoverRoboticArm implements ItemLogisticCover {

    private final long energyPerOperation;
    private final long minEnergyNeeded;

    public CoverRoboticArmOverhauled(ICoverable coverable, EnumFacing attachedSide, int tier, int itemsPerSecond) {
        super(coverable, attachedSide, tier, itemsPerSecond);
        this.energyPerOperation = CoverMethods.getEnergyPerOperation(tier);
        this.minEnergyNeeded = CoverMethods.minEnergyNeeded(tier);
    }

    @Override
    public boolean canAttach() {
        return super.canAttach() && getLogisticContainer() != null;
    }

    @Override
    public IEnergyContainer getEnergyContainer() {
        return coverHolder.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, null);
    }

    @Override
    public long getEnergyPerOperation() {
        return this.energyPerOperation;
    }

    @Override
    public long getMinEnergyNeeded() {
        return this.minEnergyNeeded;
    }

    @Override
    public LogisticContainer getLogisticContainer() {
        return coverHolder.getCapability(TKCYATileCapabilities.CAPABILITY_ITEM_LOGISTIC, attachedSide);
    }

    @Override
    public int superDoTransferItemsAny(IItemHandler itemHandler, IItemHandler myItemHandler, int maxTransferAmount) {
        return super.doTransferItemsAny(itemHandler, myItemHandler, maxTransferAmount);
    }

    @Override
    protected int doTransferItemsAny(IItemHandler itemHandler, IItemHandler myItemHandler, int maxTransferAmount) {
        return overiddenDoTransferItemsAny(itemHandler, myItemHandler, maxTransferAmount);
    }
}
