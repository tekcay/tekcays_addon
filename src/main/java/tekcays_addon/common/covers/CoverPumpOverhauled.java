package tekcays_addon.common.covers;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.cover.ICoverable;
import gregtech.api.util.GTTransferUtils;
import gregtech.common.covers.CoverPump;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.capability.IFluidHandler;
import tekcays_addon.gtapi.capability.TKCYATileCapabilities;

public class CoverPumpOverhauled extends CoverPump {

    private IEnergyContainer energyContainer;
    private final long energyPerOperation;
    private final long minEnergyNeeded;

    public CoverPumpOverhauled(ICoverable coverHolder, EnumFacing attachedSide, int tier, int mbPerTick) {
        super(coverHolder, attachedSide, tier, mbPerTick);
        this.energyPerOperation = GTValues.V[tier - 1];
        this.minEnergyNeeded = this.energyPerOperation * 4;
    }

    @Override
    public boolean canAttach() {
        return super.canAttach() && coverHolder.getCapability(TKCYATileCapabilities.CAPABILITY_FLUID_LOGISTIC, attachedSide) != null;
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
    protected int doTransferFluidsInternal(IFluidHandler myFluidHandler, IFluidHandler fluidHandler, int transferLimit) {
        int transferedFluidAmount = 0;
        if (pumpMode == PumpMode.IMPORT) {
            transferedFluidAmount = GTTransferUtils.transferFluids(fluidHandler, myFluidHandler, transferLimit, fluidFilter::testFluidStack);
        } else if (pumpMode == PumpMode.EXPORT) {
            transferedFluidAmount = GTTransferUtils.transferFluids(myFluidHandler, fluidHandler, transferLimit, fluidFilter::testFluidStack);
        }
        if (transferedFluidAmount > 0) this.energyContainer.removeEnergy(this.energyPerOperation);
        return 0;
    }

}
