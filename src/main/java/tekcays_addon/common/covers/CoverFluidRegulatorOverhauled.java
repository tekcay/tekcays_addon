package tekcays_addon.common.covers;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.cover.ICoverable;
import gregtech.common.covers.CoverFluidRegulator;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.capability.IFluidHandler;
import tekcays_addon.api.covers.CoverMethods;
import tekcays_addon.gtapi.capability.TKCYATileCapabilities;
import tekcays_addon.gtapi.capability.containers.LogisticContainer;

public class CoverFluidRegulatorOverhauled extends CoverFluidRegulator implements LogisticsCover {

    private final long energyPerOperation;
    private final long minEnergyNeeded;

    public CoverFluidRegulatorOverhauled(ICoverable coverHolder, EnumFacing attachedSide, int tier, int mbPerTick) {
        super(coverHolder, attachedSide, tier, mbPerTick);
        this.energyPerOperation = CoverMethods.getEnergyPerOperation(tier);
        this.minEnergyNeeded = CoverMethods.minEnergyNeeded(tier);
    }

    @Override
    public boolean canAttach() {
        return super.canAttach() && getLogisticContainer() != null;
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
        return coverHolder.getCapability(TKCYATileCapabilities.CAPABILITY_FLUID_LOGISTIC, attachedSide);
    }

    @Override
    public IEnergyContainer getEnergyContainer() {
        return coverHolder.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, null);
    }

    @Override
    public void update() {
        if (checkEnergy()) {
            super.update();
        }
    }

    @Override
    protected int doTransferFluidsInternal(IFluidHandler myFluidHandler, IFluidHandler fluidHandler, int transferLimit) {
        int doTransferFluidsInternalReturn = super.doTransferFluidsInternal(myFluidHandler, fluidHandler, transferLimit);
        if (doTransferFluidsInternalReturn > 0) getEnergyContainer().removeEnergy(getEnergyPerOperation());
        return doTransferFluidsInternalReturn;
    }
}
