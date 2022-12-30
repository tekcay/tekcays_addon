package tekcays_addon.common.metatileentities.single;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import tekcays_addon.api.capability.IPressureContainer;
import tekcays_addon.api.capability.IVacuumContainer;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.metatileentity.ElectricPressureCompressor;
import tekcays_addon.api.utils.IPressureVacuum;
import tekcays_addon.api.utils.TKCYALog;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static tekcays_addon.api.utils.TKCYAValues.ATMOSPHERIC_PRESSURE;
import static tekcays_addon.api.utils.TKCYAValues.MINIMUM_FLUID_STACK_AMOUNT;


public class MetaTileEntityElectricPressureCompressor extends ElectricPressureCompressor implements IPressureVacuum {

    //private int transferRate = 0;
    private final int BASE_TRANSFER_RATE;
    private final int ENERGY_BASE_CONSUMPTION = (int) (GTValues.V[getTier()] * 15/16);
    private IPressureContainer pressureContainer;
    private int fluidCapacity;
    private int tierMultiplier = (getTier() * getTier() + 1);
    private IFluidTank fluidTank;
    private int pressure;

    public MetaTileEntityElectricPressureCompressor(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, false, tier);
        this.BASE_TRANSFER_RATE = 100 * tierMultiplier;
        this.fluidCapacity = 1000 * (getTier() * getTier() + 1);
        initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityElectricPressureCompressor(metaTileEntityId, getTier());
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();
        this.importFluids = this.createImportFluidHandler();
        fluidTank = this.importFluids.getTankAt(0);
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
        TKCYALog.logger.info("getOutputSide() = " + getOutputSide().getName());
        if (!getWorld().isRemote) {
            pressureContainer = getAdjacentIPressureContainer(getOutputSide());
            if (pressureContainer != null) {
                TKCYALog.logger.info("pressureContainer is not null");
                pressure = pressureContainer.getPressure();
                TKCYALog.logger.info("pressure = " + pressure);
                transferRate = getBaseTransferRate();
                TKCYALog.logger.info("transferRate = " + transferRate);
            } else {
                transferRate = 0;
                return;
            }

            FluidStack fluidTankContent = fluidTank.getFluid();
            if (fluidTankContent == null) return;
            Fluid fluid = fluidTankContent.getFluid();

            applyPressure(fluidTank, fluid, transferRate);
            if (pressureContainer.getAirAmount() > MINIMUM_FLUID_STACK_AMOUNT) applyVacuum(transferRate);
            pressureContainer.setPressure();
            energyContainer.removeEnergy(ENERGY_BASE_CONSUMPTION);
        }
    }

    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE) {
            return side == getFrontFacing() ? GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE.cast(this) : null;
        }
        if (capability.equals(TKCYATileCapabilities.CAPABILITY_PRESSURE_CONTAINER)) {
            return TKCYATileCapabilities.CAPABILITY_PRESSURE_CONTAINER.cast(pressureContainer);
        }
        return super.getCapability(capability, side);
    }

    //Implementations

    @Override
    public MetaTileEntityElectricPressureCompressor getMetaTileEntity() {
        return this;
    }

    @Override
    public int getPressure() {
        return pressure;
    }

    @Override
    public IVacuumContainer getPressureContainer() {
        return pressureContainer;
    }

    @Override
    public int getBaseTransferRate() {
        return BASE_TRANSFER_RATE;
    }

}
