package tekcays_addon.common.covers;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.cover.ICoverable;
import gregtech.common.covers.CoverConveyor;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.IItemHandler;
import tekcays_addon.gtapi.capability.TKCYATileCapabilities;
import tekcays_addon.gtapi.capability.containers.Logistic;

public class CoverConveyorOverhauled extends CoverConveyor implements Logistic {

    private IEnergyContainer energyContainer;
    private final long energyPerOperation;
    private final long minEnergyNeeded;


    public CoverConveyorOverhauled(ICoverable coverable, EnumFacing attachedSide, int tier, int itemsPerSecond) {
        super(coverable, attachedSide, tier, itemsPerSecond);
        this.energyPerOperation = GTValues.V[tier - 1];
        this.minEnergyNeeded = this.energyPerOperation * 4;
    }

    @Override
    public boolean canAttach() {
        return super.canAttach() && coverHolder.getCapability(TKCYATileCapabilities.CAPABILITY_ITEM_LOGISTIC, attachedSide) != null;
    }

    public IEnergyContainer getEnergyContainer() {
        return this.energyContainer = coverHolder.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, null);
    }

    @Override
    public void update() {
        this.energyContainer = getEnergyContainer();
        if (this.energyContainer != null && this.energyContainer.getEnergyStored() >= this.minEnergyNeeded) {
            super.update();
        }
    }

    @Override
    protected int doTransferItemsAny(IItemHandler itemHandler, IItemHandler myItemHandler, int maxTransferAmount) {
        int transferedItems = super.doTransferItemsAny(itemHandler, myItemHandler, maxTransferAmount);
        if (transferedItems > 0) this.energyContainer.removeEnergy(this.energyPerOperation);
        return transferedItems;
    }
}
