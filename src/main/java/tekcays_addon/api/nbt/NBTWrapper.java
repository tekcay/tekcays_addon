package tekcays_addon.api.nbt;

import lombok.Getter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import tekcays_addon.api.utils.ListBuilder;
import tekcays_addon.gtapi.utils.TKCYALog;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Getter
public class NBTWrapper {

    private List<ParameterHelper> parameterHelperList;
    private NBTType nbtType;

    public NBTWrapper(NBTType nbtType) {
        this.parameterHelperList = new ArrayList<>();
        this.nbtType = nbtType;
    }

    public NBTWrapper(List<ParameterHelper> parameterHelperList) {
        this.parameterHelperList = parameterHelperList;
    }

    public NBTWrapper(ParameterHelper... parameterHelpers) {
        this.parameterHelperList = ListBuilder.build(parameterHelpers);
    }

    public <T> NBTWrapper add(String name, T value, Consumer<T> function) {
        this.parameterHelperList.add(ParameterHelper.build(name, value, function));
        return this;
    }

    public static void deserializeNBTHelper(@Nonnull NBTTagCompound compound, NBTWrapper... wrappers) {
        Arrays.asList(wrappers).forEach(wrapper -> wrapper.deDerializeNBT(compound));
    }

    public static void serializeNBTHelper(@Nonnull NBTTagCompound compound, NBTWrapper... wrappers) {
        Arrays.asList(wrappers).forEach(wrapper -> wrapper.serializeNBT(compound));
        });
    }

    public static void writeInitialDataHelper(@Nonnull PacketBuffer buffer, NBTWrapper... wrappers) {
        Arrays.asList(wrappers).forEach(wrapper -> wrapper.writeInitialData(buffer));
    }

    public static void receiveInitialDataHelper(@Nonnull PacketBuffer buffer, NBTWrapper... wrappers) {
        Arrays.asList(wrappers).forEach(wrapper -> wrapper.receiveInitialData(buffer));
    }

    @SuppressWarnings("unchecked")
    private void serializeNBT(@Nonnull NBTTagCompound compound) {
        List<ParameterHelper> parameterHelpers = this.getParameterHelperList();
        switch (this.getNbtType()){
            case LONG:
                parameterHelpers.forEach(parameterHelper ->
                        compound.setLong(parameterHelper.getName(), (long) parameterHelper.getValue()));
                break;
            case BOOLEAN:
                parameterHelpers.forEach(parameterHelper ->
                        compound.setBoolean(parameterHelper.getName(), (boolean) parameterHelper.getValue()));
                break;
            case INTEGER:
                parameterHelpers.forEach(parameterHelper ->
                        compound.setInteger(parameterHelper.getName(), (int) parameterHelper.getValue()));
                break;
            default:
        }
    }

    @SuppressWarnings("unchecked")
    private void deDerializeNBT(@Nonnull NBTTagCompound compound) {
        List<ParameterHelper> parameterHelpers = this.getParameterHelperList();
        switch (this.getNbtType()){
            case LONG:
                parameterHelpers.forEach(parameterHelper ->
                        ((ParameterHelper<Long>) parameterHelper).getFunction().accept(compound.getLong(parameterHelper.getName())));
                break;
            case BOOLEAN:
                parameterHelpers.forEach(parameterHelper ->
                        ((ParameterHelper<Boolean>) parameterHelper).getFunction().accept(compound.getBoolean(parameterHelper.getName())));
                break;
            case INTEGER:
                parameterHelpers.forEach(parameterHelper ->
                        ((ParameterHelper<Integer>) parameterHelper).getFunction().accept(compound.getInteger(parameterHelper.getName())));
                break;
            default:
        }
    }

    
    private void writeInitialData(@Nonnull PacketBuffer buffer) {
        List<ParameterHelper> parameterHelpers = this.getParameterHelperList();
        switch (this.getNbtType()){
            case LONG:
                parameterHelpers.forEach(parameterHelper ->
                    buffer.writeLong((long) parameterHelper.getValue()));
                break;
            case BOOLEAN:
                parameterHelpers.forEach(parameterHelper -> buffer.writeBoolean((boolean) parameterHelper.getValue()));
                break;
            case INTEGER:
                parameterHelpers.forEach(parameterHelper -> buffer.writeInt((int) parameterHelper.getValue()));
                break;
            default:
        }
    }

    @SuppressWarnings("unchecked")
    private void receiveInitialData(@Nonnull PacketBuffer buffer) {
        List<ParameterHelper> parameterHelpers = this.getParameterHelperList();
        switch (this.getNbtType()){
            case LONG:
                parameterHelpers.forEach(parameterHelper ->
                        ((ParameterHelper<Long>) parameterHelper).getFunction().accept(buffer.readLong()));
                break;
            case BOOLEAN:
                parameterHelpers.forEach(parameterHelper ->
                        ((ParameterHelper<Boolean>) parameterHelper).getFunction().accept(buffer.readBoolean()));
                break;
            case INTEGER:
                parameterHelpers.forEach(parameterHelper ->
                        ((ParameterHelper<Integer>) parameterHelper).getFunction().accept(buffer.readInt()));
                break;
            default:
        }
    }



}
