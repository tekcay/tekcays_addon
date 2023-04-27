package tekcays_addon.api.metatileentity;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

import static gregtech.api.capability.GregtechDataCodes.UPDATE_OUTPUT_FACING;
import static net.minecraft.util.EnumFacing.UP;
import static tekcays_addon.api.consts.NBTKeys.OUTPUT_SIDE;

public abstract class WrenchAbleTieredMetaTileEntity extends TieredMetaTileEntity {

    protected final int tier;
    protected EnumFacing outputSide;

    public WrenchAbleTieredMetaTileEntity(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
        this.tier = tier;
    }

    public EnumFacing getOutputSide() {
        return outputSide == null ? getFrontFacing().getOpposite() : outputSide;
    }

    public void setOutputSide(EnumFacing outputSide) {
        this.outputSide = outputSide;
        if (!getWorld().isRemote) {
            notifyBlockUpdate();
            writeCustomData(UPDATE_OUTPUT_FACING, buf -> buf.writeByte(outputSide.getIndex()));
            markDirty();
        }
    }

    @Override
    public void setFrontFacing(EnumFacing frontFacing) {
        super.setFrontFacing(frontFacing);
        if (this.outputSide == null) {
            //set initial output facing as opposite to front
            setOutputSide(frontFacing.getOpposite());
        }
    }

    @Override
    public boolean isValidFrontFacing(EnumFacing facing) {
        return facing != EnumFacing.DOWN && facing != UP;
    }

    @Override
    public boolean onWrenchClick(EntityPlayer playerIn, EnumHand hand, EnumFacing wrenchSide, CuboidRayTraceResult hitResult) {
        if (wrenchSide == null) return false;
        if (playerIn.isSneaking()) {
            if (wrenchSide == getFrontFacing() || !isValidFrontFacing(wrenchSide) || !hasFrontFacing()) return false;
            setFrontFacing(wrenchSide);
        } else {
            if (wrenchSide == getOutputSide() || wrenchSide == getFrontFacing()) return false;
            setOutputSide(wrenchSide);
        }
        return true;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger(OUTPUT_SIDE, getOutputSide().getIndex());
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.outputSide = EnumFacing.VALUES[data.getInteger(OUTPUT_SIDE)];
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeByte(getOutputSide().getIndex());
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.outputSide = EnumFacing.VALUES[buf.readByte()];
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == UPDATE_OUTPUT_FACING) {
            this.outputSide = EnumFacing.VALUES[buf.readByte()];
            scheduleRenderUpdate();
        }
    }
}
