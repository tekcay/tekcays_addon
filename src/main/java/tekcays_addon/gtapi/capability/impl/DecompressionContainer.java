package tekcays_addon.gtapi.capability.impl;

import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.IFluidHandler;
import tekcays_addon.gtapi.capability.TKCYATileCapabilities;
import tekcays_addon.gtapi.capability.containers.IDecompression;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static tekcays_addon.api.consts.NBTKeys.*;

@Getter
public class DecompressionContainer extends MTETrait implements IDecompression {

    private IFluidHandler fluidHandler;
    private boolean isActive;
    private boolean isAbleToDecompress;

    /**
     * Create a new MTE trait.
     * @param metaTileEntity the MTE to reference, and add the trait to
     */
    public DecompressionContainer(MetaTileEntity metaTileEntity, @Nonnull IFluidHandler fluidHandler) {
        super(metaTileEntity);
        this.fluidHandler = fluidHandler;
        this.isActive = false;
        this.isAbleToDecompress = false;
    }



    @Nonnull
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

    @Nonnull
    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = super.serializeNBT();
        compound.setBoolean(IS_ACTIVE, this.isActive);
        compound.setBoolean(IS_ABLE_TO_DECOMPRESS, this.isAbleToDecompress);
        return compound;
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagCompound compound) {
        this.isActive = compound.getBoolean(IS_ACTIVE);
        this.isAbleToDecompress = compound.getBoolean(IS_ABLE_TO_DECOMPRESS);
    }

    @Override
    public void writeInitialData(@Nonnull PacketBuffer buffer) {
        super.writeInitialData(buffer);
        buffer.writeBoolean(this.isActive);
        buffer.writeBoolean(this.isAbleToDecompress);
    }

    @Override
    public void receiveInitialData(@Nonnull PacketBuffer buffer) {
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
