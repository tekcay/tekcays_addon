package tekcays_addon.common.covers;

import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.cover.CoverDefinition;
import gregtech.api.cover.CoverableView;
import gregtech.common.covers.CoverRoboticArm;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import tekcays_addon.api.covers.CoverMethods;
import tekcays_addon.gtapi.capability.TKCYATileCapabilities;
import tekcays_addon.gtapi.capability.containers.LogisticContainer;

public class CoverRoboticArmOverhauled extends CoverRoboticArm implements ItemLogisticCover {

    private final long energyPerOperation;
    private final long minEnergyNeeded;

    public CoverRoboticArmOverhauled(@NotNull CoverDefinition definition, @NotNull CoverableView coverableView, @NotNull EnumFacing attachedSide, int tier, int itemsPerSecond) {
        super(definition, coverableView, attachedSide, tier, itemsPerSecond);
        this.energyPerOperation = CoverMethods.getEnergyPerOperation(tier);
        this.minEnergyNeeded = CoverMethods.minEnergyNeeded(tier);
    }


    @Override
    public boolean canAttach(@NotNull CoverableView coverable, @NotNull EnumFacing side) {
        return super.canAttach(coverable, side) && getLogisticContainer() != null;
    }

    @Override
    public IEnergyContainer getEnergyContainer() {
        return getCoverableView().getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, null);
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
        return getCoverableView().getCapability(TKCYATileCapabilities.CAPABILITY_ITEM_LOGISTIC, getAttachedSide());
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
