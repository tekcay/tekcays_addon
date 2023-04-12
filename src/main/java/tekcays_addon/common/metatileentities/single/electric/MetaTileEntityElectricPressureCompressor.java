package tekcays_addon.common.metatileentities.single.electric;

import gregtech.api.GTValues;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.NotifiableFluidTank;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.capability.containers.IPressureContainer;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.metatileentity.ElectricPressureCompressor;
import tekcays_addon.api.utils.AdjacentCapabilityHelper;
import tekcays_addon.api.utils.PressureContainerHandler;

public class MetaTileEntityElectricPressureCompressor extends ElectricPressureCompressor implements PressureContainerHandler, AdjacentCapabilityHelper<IPressureContainer> {

    private final int ENERGY_BASE_CONSUMPTION = (int) (GTValues.V[getTier()] * 15/16);
    private IPressureContainer pressureContainer;
    private int fluidCapacity;
    private int tierMultiplier = (getTier() * getTier() + 1);
    private int pressure;

    public MetaTileEntityElectricPressureCompressor(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, false, tier);
        this.fluidCapacity = 1000 * (getTier() * getTier() + 1);
        initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityElectricPressureCompressor(metaTileEntityId, getTier());
    }

    @Override
    public FluidTankList createImportFluidHandler() {
        this.fluidTank = new NotifiableFluidTank(fluidCapacity, this, false);
        return new FluidTankList(false, fluidTank);
    }

    @Override
    protected int getCurrentTransferRate() {
        return this.transferRate;
    }

    @Override
    public void update() {
        super.update();
        //Redstone stops fluid transfer
        if (this.isBlockRedstonePowered()) return;
        if (energyContainer.getEnergyStored() < ENERGY_BASE_CONSUMPTION) return;

        if (!getWorld().isRemote) {
            pressureContainer = getAdjacentCapabilityContainer(this);
            if (pressureContainer != null) {
                pressure = pressureContainer.getPressure();
                transferRate = getBaseTransferRate();
            } else {
                transferRate = 0;
                return;
            }
            
            if (getFluidTankContent() == null) return;

            int toDrain = pressureContainer.changePressurizedFluidStack(getFluidTankContent(), transferRate);

            if (toDrain > 0) {
                fluidTank.drain(toDrain, true);
                energyContainer.removeEnergy(ENERGY_BASE_CONSUMPTION);
            }
        }
    }
    
    private FluidStack getFluidTankContent() {
        return fluidTank.getFluid();
    }

    //Implementations

    @Override
    public IPressureContainer getPressureContainer() {
        return getAdjacentCapabilityContainer(this);
    }

    @Override
    public int getBaseTransferRate() {
        return BASE_TRANSFER_RATE;
    }

    @Override
    public int getPressure() {
        return pressureContainer.getPressure();
    }

    @Override
    public FluidTankList importFluidTanks() {
        return this.getImportFluids();
    }

    @Override
    public Capability<IPressureContainer> getCapability() {
        return TKCYATileCapabilities.CAPABILITY_PRESSURE_CONTAINER;
    }

    @Override
    public EnumFacing getOutputSide() {
        return super.getOutputSide();
    }
}
