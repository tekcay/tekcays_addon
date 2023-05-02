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
import tekcays_addon.gtapi.worldgen.FluidDepositWorldEntry;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import static tekcays_addon.gtapi.consts.DepositValues.*;
import static tekcays_addon.gtapi.worldgen.FluidDepositHandler.veinList;

@NoArgsConstructor
public class PacketFluidDepositToDimension implements IPacket, IClientExecutor {

    private Map<FluidDepositWorldEntry, Integer> map;
    public PacketFluidDepositToDimension(Map<FluidDepositWorldEntry, Integer> map) {
        this.map = map;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeInt(map.size());
            map.forEach((fluidDepositWorldEntry, dimID) -> {
                NBTTagCompound tag = fluidDepositWorldEntry.writeToNBT();
                tag.setInteger(WEIGHT, fluidDepositWorldEntry.getFluidDepositDefinition().getWeight());
                tag.setInteger(DIM_ID, dimID);
                ByteBufUtils.writeTag(buf, tag);
            });
    }

    @Override
    public void decode(PacketBuffer buf) {
        this.map = new HashMap<>();
        int size = buf.readVarInt();
        IntStream.range(0, size).forEach(i -> {
            NBTTagCompound tag = ByteBufUtils.readTag(buf);
            if (tag == null || tag.isEmpty()) return;
            FluidDepositWorldEntry entry = FluidDepositWorldEntry.readFromNBT(tag);
            int dimId = tag.getInteger(DIM_ID);
            this.map.put(entry, dimId);
        });
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void executeClient(NetHandlerPlayClient handler) {
        veinList.clear();
        map.forEach((fluidDepositWorldEntry, dimId) -> veinList.add(fluidDepositWorldEntry.getFluidDepositDefinition()));
    }
}
