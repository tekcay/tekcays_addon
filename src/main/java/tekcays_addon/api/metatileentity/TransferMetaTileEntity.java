package tekcays_addon.api.metatileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.capabilities.Capability;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IActiveOutputSide;
import gregtech.core.sound.GTSoundEvents;

public abstract class TransferMetaTileEntity extends WrenchAbleTieredMetaTileEntity implements IActiveOutputSide {

    protected final int ENERGY_BASE_CONSUMPTION = (int) (GTValues.V[getTier()] / 2);
    protected final int tier;
    protected TileEntity inputTe, outputTe;
    protected boolean isRunning;

    public TransferMetaTileEntity(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
        this.isRunning = false;
        this.tier = tier;
    }

    protected boolean getTileEntities() {
        inputTe = getWorld().getTileEntity(getPos().offset(getFrontFacing()));
        if (inputTe == null) return false;

        outputTe = getWorld().getTileEntity(getPos().offset(getOutputSide()));
        return outputTe != null;
    }

    @Override
    public boolean isValidFrontFacing(EnumFacing facing) {
        return facing != getOutputSide();
    }

    @Override
    public void update() {
        super.update();
        if (!getWorld().isRemote) {
            if (this.isBlockRedstonePowered() || energyContainer.getEnergyStored() < ENERGY_BASE_CONSUMPTION) {
                isRunning = false;
                return;
            }
            if (tryTransfer()) {
                energyContainer.removeEnergy(ENERGY_BASE_CONSUMPTION);
                isRunning = true;
            }
        }
    }

    protected abstract boolean tryTransfer();

    @Override
    @Nullable
    public <T> T getCapability(@NotNull Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE) {
            return side == getFrontFacing() ? GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE.cast(this) : null;
        }
        return super.getCapability(capability, side);
    }

    @Override
    public SoundEvent getSound() {
        return GTSoundEvents.MOTOR;
    }

    @Override
    public boolean isActive() {
        return this.isRunning;
    }

    @Override
    public boolean isAutoOutputItems() {
        return false;
    }

    @Override
    public boolean isAutoOutputFluids() {
        return true;
    }

    @Override
    public boolean isAllowInputFromOutputSideItems() {
        return false;
    }

    @Override
    public boolean isAllowInputFromOutputSideFluids() {
        return false;
    }
}
