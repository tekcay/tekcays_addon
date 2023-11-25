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
import tekcays_addon.gtapi.capability.containers.LogisticContainer;

public class CoverPumpOverhauled extends CoverPump implements FluidLogisticCover {

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
        return super.canAttach() && getLogisticContainer() != null;
    }

    @Override
    public IEnergyContainer getEnergyContainer() {
        return this.energyContainer = coverHolder.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, null);
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
    public void update() {
        if (checkEnergy()) {
            super.update();
        }
    }

    @Override
    protected int doTransferFluidsInternal(IFluidHandler myFluidHandler, IFluidHandler fluidHandler, int transferLimit) {
        return overridenDoTransferFluidsInternal(myFluidHandler, fluidHandler, transferLimit);
    }

    @Override
    public int superDoTransferFluidsInternal(IFluidHandler myFluidHandler, IFluidHandler fluidHandler, int transferLimit) {
        return super.doTransferFluidsInternal(myFluidHandler, fluidHandler, transferLimit);
    }
}
