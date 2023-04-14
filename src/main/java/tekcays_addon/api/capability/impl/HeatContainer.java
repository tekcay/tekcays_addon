package tekcays_addon.api.capability.impl;

import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;
import tekcays_addon.api.capability.NBTHelper;
import tekcays_addon.api.capability.ParameterHelper;
import tekcays_addon.api.capability.containers.IContainer;
import tekcays_addon.api.capability.containers.IHeatContainer;
import tekcays_addon.api.capability.TKCYATileCapabilities;
import tekcays_addon.api.consts.CapabilityId;
import tekcays_addon.api.utils.TKCYAValues;
import tekcays_addon.api.utils.capability.GetCapabilityHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static tekcays_addon.api.utils.capability.GetCapabilityHelper.getCapabilityOfContainer;

@Getter
@Setter
public class HeatContainer extends MTETrait implements IHeatContainer, NBTHelper {

    private final int minHeat;
    private final int maxHeat;
    private int maxTemperature;

    @Setter(AccessLevel.NONE)
    private int heat;
    @Setter(AccessLevel.NONE)
    private int temperature;

    /**
     * Default Heat container
     * {@link IHeatContainer}
     */
    public HeatContainer(MetaTileEntity metaTileEntity, int minHeat, int maxHeat) {
        super(metaTileEntity);
        this.minHeat = minHeat;
        this.maxHeat = maxHeat;
        this.heat = 0;
    }

    public HeatContainer(MetaTileEntity metaTileEntity, int minHeat, int maxHeat, int maxTemperature) {
        super(metaTileEntity);
        this.minHeat = minHeat;
        this.maxHeat = maxHeat;
        this.heat = 0;
        this.maxTemperature = maxTemperature;
        this.temperature = TKCYAValues.ROOM_TEMPERATURE;
    }

    @Nonnull
    @Override
    public String getName() {
        return "HeatContainer";
    }

    @Override
    public void setHeat(int amount) {
        this.heat = amount;
        this.metaTileEntity.markDirty();
    }

    @Override
    public void setTemperature(int temperature) {
        this.temperature = temperature;
        this.metaTileEntity.markDirty();
    }
    @Nullable
    public <T> T getCapability(Capability<T> capability) {
        getCapabilityOfContainer(capability, this);
        return null;
    }

    @Nonnull
    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = super.serializeNBT();
        return serializeNBTHelper(compound);
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagCompound compound) {
        deserializeNBTHelper(compound);
    }

    @Override
    public void writeInitialData(@Nonnull PacketBuffer buffer) {
        super.writeInitialData(buffer);
        writeInitialDataHelper(buffer);
    }

    @Override
    public void receiveInitialData(@Nonnull PacketBuffer buffer) {
        super.receiveInitialData(buffer);
        receiveInitialDataHelper(buffer);
    }

    @Override
    public List<ParameterHelper<Integer>> getAllIntValues() {
        List<ParameterHelper<Integer>> parametersValues = new ArrayList<>();
        parametersValues.add(new ParameterHelper<>("heat", heat, this::setHeat));
        parametersValues.add(new ParameterHelper<>("temperature", temperature, this::setTemperature));
        return parametersValues;
    }

    @Override
    public Capability<IHeatContainer> getContainerCapability() {
        return TKCYATileCapabilities.CAPABILITY_HEAT_CONTAINER;
    }

    @Override
    public HeatContainer getContainer() {
        return this;
    }

    @Override
    public CapabilityId getCapabilityId() {
        return CapabilityId.HEAT;
    }
}
