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
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.metatileentity.ElectricPressureCompressor;
import tekcays_addon.api.utils.IPressureVacuum;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class MetaTileEntityElectricPressureCompressor extends ElectricPressureCompressor implements IPressureVacuum {

    private int transferRate = 0;
    private final int BASE_TRANSFER_RATE;
    private final int ENERGY_BASE_CONSUMPTION = (int) (GTValues.V[getTier()] * 15/16);
    private IPressureContainer pressureContainer;
    private int fluidCapacity;
    private int tierMultiplier = (getTier() * getTier() + 1);
    private IFluidTank fluidTank;

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
        this.exportFluids = this.createExportFluidHandler();
        fluidTank = this.exportFluids.getTankAt(0);
    }


    private int getTankFluidAmount(FluidTankList tank) {
        return tank.getTankAt(0).getFluidAmount();
    }


    private void setTransferRate(int pressure) {
        double pressurePercentage = (double) pressure / pressureContainer.getMaxPressure();
        transferRate = (int) (BASE_TRANSFER_RATE * pressurePercentage);
    }

    @Override
    public void update() {
        super.update();
        if (!getWorld().isRemote) {
            pressureContainer = getAdjacentIPressureContainer(getFrontFacing(), this);
            if (pressureContainer != null) {
                int pressure = pressureContainer.getPressure();
                setTransferRate(pressure);
            } else {
                transferRate = 0;
            }

            //Redstone stops heating
            if (this.isBlockRedstonePowered()) return;
            if (energyContainer.getEnergyStored() < ENERGY_BASE_CONSUMPTION) return;


            //TODO first make Air FluidStack amount to 1 and the added FluidStack at standard, then make it pressurized

            FluidStack fluidTankContent = fluidTank.getFluid();
            if (fluidTankContent == null) return;
            Fluid fluid = fluidTankContent.getFluid();
            applyPressure(fluidTank, fluid, pressureContainer, transferRate);

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

    @Override
    public MetaTileEntityElectricPressureCompressor getMetaTileEntity() {
        return this;
    }

}
