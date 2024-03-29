package tekcays_addon.common.metatileentities.single.electric;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.IFluidTank;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import tekcays_addon.gtapi.capability.TKCYATileCapabilities;
import tekcays_addon.gtapi.capability.containers.IVacuumContainer;
import tekcays_addon.gtapi.metatileentity.ElectricPressureCompressor;
import tekcays_addon.gtapi.utils.IPressureVacuum;

public class MetaTileEntityElectricVacuumPump extends ElectricPressureCompressor
                                              implements IPressureVacuum<IVacuumContainer> {

    private int transferRate = 0;
    private final int ENERGY_BASE_CONSUMPTION = (int) (GTValues.V[getTier()] * 15 / 16);
    private IVacuumContainer vacuumContainer;
    private int fluidCapacity;
    private int tierMultiplier = (getTier() * getTier() + 1);
    private IFluidTank fluidTank;
    private int pressure;

    public MetaTileEntityElectricVacuumPump(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, true, tier);
        this.fluidCapacity = 1000 * (getTier() * getTier() + 1);
        initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityElectricVacuumPump(metaTileEntityId, getTier());
    }

    @Override
    protected int getCurrentTransferRate() {
        return this.transferRate;
    }

    @Override
    public void update() {
        super.update();
        if (!getWorld().isRemote) {
            vacuumContainer = getAdjacentIVacuumContainer(getFrontFacing());
            if (vacuumContainer != null) {
                pressure = vacuumContainer.getPressure();
                transferRate = getTransferRate();
            } else {
                transferRate = 0;
            }

            // Redstone stops heating
            if (this.isBlockRedstonePowered()) return;
            if (energyContainer.getEnergyStored() < ENERGY_BASE_CONSUMPTION) return;

            // TODO first make Air FluidStack amount to 1 and the added FluidStack at standard, then make it pressurized
            applyVacuum(transferRate);

            energyContainer.removeEnergy(ENERGY_BASE_CONSUMPTION);
        }
    }

    @Override
    @Nullable
    public <T> T getCapability(@NotNull Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE) {
            return side == getFrontFacing() ? GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE.cast(this) : null;
        }
        if (capability.equals(TKCYATileCapabilities.CAPABILITY_VACUUM_CONTAINER)) {
            return TKCYATileCapabilities.CAPABILITY_VACUUM_CONTAINER.cast(vacuumContainer);
        }
        return super.getCapability(capability, side);
    }

    @Override
    public MetaTileEntityElectricVacuumPump getMetaTileEntity() {
        return this;
    }

    @Override
    public int getPressure() {
        return pressure;
    }

    @Override
    public IVacuumContainer getPressureContainer() {
        return vacuumContainer;
    }

    @Override
    public int getBaseTransferRate() {
        return BASE_TRANSFER_RATE;
    }
}
