package tekcays_addon.gtapi.capability.impl;

import lombok.Getter;
import net.minecraftforge.common.capabilities.Capability;
import tekcays_addon.gtapi.capability.containers.IContainer;
import tekcays_addon.api.consts.CapabilityId;

@Getter
public class ContainerWhat<T, U extends T> implements IContainer<T, U> {

    private Capability<T> containerCapability;
    private U container;
    private CapabilityId capabilityId;

}
