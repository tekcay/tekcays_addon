package tekcays_addon.api.covers;

import gregtech.api.GTValues;

public class CoverMethods {

    public static int getFluidTransferRatePerTick(int tier) {
        return 1280 * tier * 4 / 20;
    }

    public static int getFluidTransferRatePerSecond(int tier) {
        return getFluidTransferRatePerTick(tier) * 20;
    }

    public static int getItemTransferRatePerSecond(int tier) {
        return (int) (GTValues.V[tier - 1] / 2);
    }

    public static long getEnergyPerOperation(int tier) {
        return GTValues.V[tier - 1];
    }

    public static long minEnergyNeeded(int tier) {
        return GTValues.V[tier];
    }

}
