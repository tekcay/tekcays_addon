package tekcays_addon.gtapi.network;

import gregtech.api.network.IClientExecutor;
import gregtech.api.network.IPacket;
import lombok.NoArgsConstructor;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tekcays_addon.gtapi.worldgen.FluidDepositHandler;
import tekcays_addon.gtapi.worldgen.FluidDepositWorldEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static tekcays_addon.gtapi.consts.DepositValues.WEIGHT;

@NoArgsConstructor
public class PacketFluidDepositList implements IPacket, IClientExecutor {

    private List<FluidDepositWorldEntry> list;
    public PacketFluidDepositList(List<FluidDepositWorldEntry> list) {
        this.list = list;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeInt(list.size());
            list.forEach(fluidDepositWorldEntry -> {
                NBTTagCompound tag = fluidDepositWorldEntry.writeToNBT();
                tag.setInteger(WEIGHT, fluidDepositWorldEntry.getFluidDepositDefinition().getWeight());
                ByteBufUtils.writeTag(buf, tag);
            });
    }

    @Override
    public void decode(PacketBuffer buf) {
        this.list = new ArrayList<>();
        int size = buf.readVarInt();
        IntStream.range(0, size).forEach(i -> {
            NBTTagCompound tag = ByteBufUtils.readTag(buf);
            if (tag == null || tag.isEmpty()) return;
            FluidDepositWorldEntry entry = FluidDepositWorldEntry.readFromNBT(tag);
            this.list.add(entry);
        });
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void executeClient(NetHandlerPlayClient handler) {
        FluidDepositHandler.veinList.clear();
        list.forEach(fluidDepositWorldEntry -> FluidDepositHandler.veinList.add(fluidDepositWorldEntry.getFluidDepositDefinition()));
    }
}
