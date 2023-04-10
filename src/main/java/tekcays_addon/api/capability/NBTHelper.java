package tekcays_addon.api.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;

import java.util.List;

public interface NBTHelper {

    List<ParameterHelper<Integer>> getAllIntValues();

    @Nonnull
    default NBTTagCompound serializeNBTHelper(@Nonnull NBTTagCompound compound) {
        getAllIntValues().forEach(integerParameterHelper ->
                compound.setInteger(integerParameterHelper.getName(),
                        integerParameterHelper.getValue()));
        return compound;
    }

    default void deserializeNBTHelper(@Nonnull NBTTagCompound compound) {
        getAllIntValues().forEach(integerParameterHelper -> integerParameterHelper
                .getFunction()
                .accept(compound.getInteger(integerParameterHelper.getName())));
    }

    default void writeInitialDataHelper(@Nonnull PacketBuffer buffer) {
        getAllIntValues().forEach(integerParameterHelper ->
                buffer.writeInt(integerParameterHelper.getValue()));
    }

    default void receiveInitialDataHelper(@Nonnull PacketBuffer buffer) {
        getAllIntValues().forEach(integerParameterHelper -> integerParameterHelper
                .getFunction()
                .accept(buffer.readInt()));
    }
}
