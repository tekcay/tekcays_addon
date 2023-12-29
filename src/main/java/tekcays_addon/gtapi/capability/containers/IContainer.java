package tekcays_addon.gtapi.capability.containers;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import org.jetbrains.annotations.NotNull;

import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import tekcays_addon.api.consts.CapabilityId;

public interface IContainer<T, U extends T> {

    Capability<T> getContainerCapability();

    U getContainer();

    CapabilityId getCapabilityId();

    @Nullable
    default T retrieveCapability(@NotNull Capability<T> capability, @NotNull EnumFacing recieverSide,
                                 @NotNull EnumFacing emitterSide) {
        if (capability == getContainerCapability())
            return recieverSide == emitterSide ? getContainerCapability().cast(getContainer()) : null;
        return null;
    }
}
