package tekcays_addon.common.metatileentities.single;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import tekcays_addon.api.capability.IPressureContainer;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.metatileentity.ElectricPressureCompressor;
import tekcays_addon.api.utils.PressureContainerHandler;
import tekcays_addon.api.utils.TKCYALog;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MetaTileEntityElectricPressureCompressor extends ElectricPressureCompressor implements PressureContainerHandler {

    private final int ENERGY_BASE_CONSUMPTION = (int) (GTValues.V[getTier()] * 15/16);
    private IPressureContainer pressureContainer;
    private int fluidCapacity;
    private int tierMultiplier = (getTier() * getTier() + 1);
    private IFluidTank fluidTank;
    private long pressure;

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

        if (!getWorld().isRemote) {
            pressureContainer = getAdjacentPressureContainer();
            if (pressureContainer != null) {
                pressure = pressureContainer.getPressure();
                transferRate = getBaseTransferRate();
            } else {
                transferRate = 0;
                return;
            }
            
            if (getFluidTankContent() == null) return;

            if (!canInteractWithContainer()) return;

            applyPressure(getFluidTankContent(), transferRate);
            energyContainer.removeEnergy(ENERGY_BASE_CONSUMPTION);

        }
    }
    
    private FluidStack getFluidTankContent() {
        return fluidTank.getFluid();
    }
    
    private boolean canInteractWithContainer() {
        return pressureContainer.getPressurizedFluidAmount() == 0 || pressureContainer.getPressurizedFluidName().equals(getFluidTankContent().getUnlocalizedName());
    }

    private IPressureContainer getAdjacentPressureContainer() {
        TileEntity te = getWorld().getTileEntity(getPos().offset(getOutputSide()));
        if (te != null) {
            return te.getCapability(TKCYATileCapabilities.CAPABILITY_PRESSURE_CONTAINER, getOutputSide().getOpposite());
        }
        return null;
    }

    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE) {
            return side == getFrontFacing() ? GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE.cast(this) : null;
        }
        return super.getCapability(capability, side);
    }

    //Implementations

    @Override
    public IPressureContainer getPressureContainer() {
        return getAdjacentPressureContainer();
    }

    @Override
    public int getBaseTransferRate() {
        return BASE_TRANSFER_RATE;
    }

    @Override
    public long getPressure() {
        return pressureContainer.getPressure();
    }

    @Override
    public FluidTankList importFluidTanks() {
        return this.getImportFluids();
    }

}
