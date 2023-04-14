package tekcays_addon.api.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;

import java.util.List;

public interface NBTHelper {

    List<ParameterHelper<Integer>> getAllIntValues();

    @Nonnull
    default NBTTagCompound serializeNBTHelper() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        getAllIntValues().forEach(integerParameterHelper ->
                nbtTagCompound.setInteger(integerParameterHelper.getName(),
                        integerParameterHelper.getValue()));
        return nbtTagCompound;
    }

    @Nonnull
    default NBTTagCompound serializeNBTHelper(NBTTagCompound nbtTagCompound) {
        getAllIntValues().forEach(integerParameterHelper ->
                nbtTagCompound.setInteger(integerParameterHelper.getName(),
                        integerParameterHelper.getValue()));
        return nbtTagCompound;
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
