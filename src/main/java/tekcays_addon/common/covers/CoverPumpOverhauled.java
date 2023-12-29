package tekcays_addon.common.covers;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.capability.IFluidHandler;

import org.jetbrains.annotations.NotNull;

import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.cover.CoverDefinition;
import gregtech.api.cover.CoverableView;
import gregtech.common.covers.CoverPump;
import tekcays_addon.api.covers.CoverMethods;
import tekcays_addon.gtapi.capability.TKCYATileCapabilities;
import tekcays_addon.gtapi.capability.containers.LogisticContainer;

public class CoverPumpOverhauled extends CoverPump implements FluidLogisticCover {

    private final long energyPerOperation;
    private final long minEnergyNeeded;

    public CoverPumpOverhauled(@NotNull CoverDefinition definition, @NotNull CoverableView coverableView,
                               @NotNull EnumFacing attachedSide, int tier, int mbPerTick) {
        super(definition, coverableView, attachedSide, tier, mbPerTick);
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
    public void update() {
        if (isThereEnoughEnergy()) {
            super.update();
        }
    }

    @Override
    protected int doTransferFluidsInternal(IFluidHandler myFluidHandler, IFluidHandler fluidHandler,
                                           int transferLimit) {
        return overridenDoTransferFluidsInternal(myFluidHandler, fluidHandler, transferLimit);
    }

    @Override
    public int superDoTransferFluidsInternal(IFluidHandler myFluidHandler, IFluidHandler fluidHandler,
                                             int transferLimit) {
        return super.doTransferFluidsInternal(myFluidHandler, fluidHandler, transferLimit);
    }
}
