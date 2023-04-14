package tekcays_addon.api.capability.containers;

import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import tekcays_addon.api.consts.CapabilityId;

import javax.annotation.Nonnull;

public interface IContainer<T, U extends T> {

    Capability<T> getContainerCapability();
    U getContainer();
    CapabilityId getCapabilityId();

    @Nullable
    default T retrieveCapability(@Nonnull Capability<T> capability, @Nonnull EnumFacing recieverSide, @Nonnull EnumFacing emitterSide) {
        if (capability == getContainerCapability()) return recieverSide == emitterSide ? getContainerCapability().cast(getContainer()) : null;
        return null;
    }
}
