package tekcays_addon.gtapi.capability.impl;

import net.minecraftforge.common.capabilities.Capability;

import lombok.Getter;
import tekcays_addon.api.consts.CapabilityId;
import tekcays_addon.gtapi.capability.containers.IContainer;

@Getter
public class ContainerWhat<T, U extends T> implements IContainer<T, U> {

    private Capability<T> containerCapability;
    private U container;
    private CapabilityId capabilityId;
}
