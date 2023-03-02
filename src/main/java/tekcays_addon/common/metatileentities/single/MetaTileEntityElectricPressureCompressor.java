package tekcays_addon.common.metatileentities.single;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import tekcays_addon.api.capability.IPressureContainer;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.consts.DataIds;
import tekcays_addon.api.metatileentity.ElectricPressureCompressor;
import tekcays_addon.api.utils.IPressureVacuum;
import tekcays_addon.api.utils.TKCYALog;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.List;

import static gregtech.api.unification.material.Materials.Air;
import static tekcays_addon.api.consts.DataIds.AIR_FLUID_STACK;
import static tekcays_addon.api.consts.DataIds.PRESSURIZED_FLUID_STACK;
import static tekcays_addon.api.utils.TKCYAValues.MINIMUM_FLUID_STACK_AMOUNT;

public class MetaTileEntityElectricPressureCompressor extends ElectricPressureCompressor implements IPressureVacuum<IPressureContainer> {

    private final int ENERGY_BASE_CONSUMPTION = (int) (GTValues.V[getTier()] * 15/16);
    private IPressureContainer pressureContainer;
    private int fluidCapacity;
    private int tierMultiplier = (getTier() * getTier() + 1);
    private IFluidTank fluidTank;
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
            pressureContainer = getAdjacentIPressureContainer(getOutputSide());
            if (pressureContainer != null) {
                pressure = pressureContainer.getPressure();
                transferRate = getBaseTransferRate();
                TKCYALog.logger.info("transferRate = " + transferRate);
            } else {
                transferRate = 0;
                return;
            }

            FluidStack fluidTankContent = fluidTank.getFluid();
            if (fluidTankContent == null) return;
            Fluid fluid = fluidTankContent.getFluid();

            FluidStack pressureContainerFluid = pressureContainer.getFluidStack();

            if (pressureContainerFluid != null && !pressureContainerFluid.isFluidEqual(fluidTankContent)) return;
            if (pressureContainerFluid != null) {
                TKCYALog.logger.info("FluidStack is :" + pressureContainerFluid.getLocalizedName() + pressureContainerFluid.amount);
            }
            applyPressure(fluidTankContent, transferRate);
            //writeData(PRESSURIZED_FLUID_STACK);

            if (pressureContainer.getAirAmount() > MINIMUM_FLUID_STACK_AMOUNT) {
                applyVacuum(transferRate);
                //writeData(AIR_FLUID_STACK);
            }
            pressureContainer.setPressure();
            energyContainer.removeEnergy(ENERGY_BASE_CONSUMPTION);
        }
    }

    private void writeData(DataIds dataId) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag(dataId.getName(), getPressureContainer().setFluidStackNBT());
        writeCustomData(dataId.getId(), packetBuffer -> packetBuffer.writeCompoundTag(nbt));
        TKCYALog.logger.info("WroteData");
    }

    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE) {
            return side == getFrontFacing() ? GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE.cast(this) : null;
        }
        if (capability.equals(TKCYATileCapabilities.CAPABILITY_PRESSURE_CONTAINER)) {
            System.out.println("GOT THERE");
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
    public IPressureContainer getPressureContainer() {
        return pressureContainer;
    }


    @Override
    public int getBaseTransferRate() {
        return BASE_TRANSFER_RATE;
    }

}
