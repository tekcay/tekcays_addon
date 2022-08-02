package tekcays_addon.api.capability.impl;

import gregtech.api.capability.IEnergyContainer;

public class MultiblocksMethods {


    public static long getEnergy(IEnergyContainer energyContainer) {
        return energyContainer.getEnergyStored();
    }


    public static int temperatureEnergyCostElectricMelter(int temp) {
        return temp <= 300 ? 0 : (int) Math.exp(((double) temp - 100) / 100);  // (int) (1.5 * Math.pow(10, -10) * Math.pow(temp, 3.6) + 10)
    }


    public static int temperatureEnergyCostBatchDistillationTower(int temp) {
        return temp <= 300 ? 0 : temp - 300;  // (int) (1.5 * Math.pow(10, -10) * Math.pow(temp, 3.6) + 10)
    }

    public static boolean enoughEnergyToDrain(IEnergyContainer energyContainer, int cost) {
        long energy = getEnergy(energyContainer);
        return energy != 0 && energy >= cost;
    }

    public static void drainEnergy(IEnergyContainer energyContainer, int cost) {
         energyContainer.removeEnergy(cost);
    }


}
