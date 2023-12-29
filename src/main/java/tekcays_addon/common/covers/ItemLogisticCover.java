package tekcays_addon.common.covers;

import net.minecraftforge.items.IItemHandler;

public interface ItemLogisticCover extends LogisticsCover {

    int superDoTransferItemsAny(IItemHandler itemHandler, IItemHandler myItemHandler, int maxTransferAmount);

    default int overiddenDoTransferItemsAny(IItemHandler itemHandler, IItemHandler myItemHandler,
                                            int maxTransferAmount) {
        int transferedAmount = superDoTransferItemsAny(itemHandler, myItemHandler, maxTransferAmount);
        if (transferedAmount > 0) getEnergyContainer().removeEnergy(getEnergyPerOperation());
        return transferedAmount;
    }
}
