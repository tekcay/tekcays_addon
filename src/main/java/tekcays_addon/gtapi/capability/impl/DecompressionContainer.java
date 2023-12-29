package tekcays_addon.gtapi.capability.impl;

import static tekcays_addon.api.consts.NBTKeys.*;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.IFluidHandler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import lombok.Getter;
import tekcays_addon.gtapi.capability.TKCYATileCapabilities;
import tekcays_addon.gtapi.capability.containers.IDecompression;

@Getter
public class DecompressionContainer extends MTETrait implements IDecompression {

    private IFluidHandler fluidHandler;
    private boolean isActive;
    private boolean isAbleToDecompress;

    /**
     * Create a new MTE trait.
     * 
     * @param metaTileEntity the MTE to reference, and add the trait to
     */
    public DecompressionContainer(MetaTileEntity metaTileEntity, @NotNull IFluidHandler fluidHandler) {
        super(metaTileEntity);
        this.fluidHandler = fluidHandler;
        this.isActive = false;
        this.isAbleToDecompress = false;
    }

    @NotNull
    @Override
    public String getName() {
        return "Decompression";
    }

    @Nullable
    public <T> T getCapability(Capability<T> capability) {
        if (capability == TKCYATileCapabilities.CAPABILITY_DECOMPRESSION_CONTAINER) {
            return TKCYATileCapabilities.CAPABILITY_DECOMPRESSION_CONTAINER.cast(this);
        }
        return null;
    }

    @NotNull
    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = super.serializeNBT();
        compound.setBoolean(IS_ACTIVE, this.isActive);
        compound.setBoolean(IS_ABLE_TO_DECOMPRESS, this.isAbleToDecompress);
        return compound;
    }

    @Override
    public void deserializeNBT(@NotNull NBTTagCompound compound) {
        this.isActive = compound.getBoolean(IS_ACTIVE);
        this.isAbleToDecompress = compound.getBoolean(IS_ABLE_TO_DECOMPRESS);
    }

    @Override
    public void writeInitialData(@NotNull PacketBuffer buffer) {
        super.writeInitialData(buffer);
        buffer.writeBoolean(this.isActive);
        buffer.writeBoolean(this.isAbleToDecompress);
    }

    @Override
    public void receiveInitialData(@NotNull PacketBuffer buffer) {
        super.receiveInitialData(buffer);
        this.isActive = buffer.readBoolean();
        this.isAbleToDecompress = buffer.readBoolean();
    }

    @Override
    public void setActivity(boolean willBeActive) {
        this.isActive = willBeActive;
        this.metaTileEntity.markDirty();
    }

    @Override
    public void setCompressAbility(boolean willBeAbleToCompress) {
        this.isAbleToDecompress = willBeAbleToCompress;
        this.metaTileEntity.markDirty();
    }
}
