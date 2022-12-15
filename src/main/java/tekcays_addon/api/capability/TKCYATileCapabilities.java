package tekcays_addon.api.capability;

import gregtech.api.capability.SimpleCapabilityManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class TKCYATileCapabilities {

    @CapabilityInject(IHeatContainer.class)
    public static Capability<IHeatContainer> CAPABILITY_HEAT_CONTAINER = null;

    @CapabilityInject(IVacuumContainer.class)
    public static Capability<IVacuumContainer> CAPABILITY_VACUUM_CONTAINER = null;

    @CapabilityInject(IPressureContainer.class)
    public static Capability<IPressureContainer> CAPABILITY_PRESSURE_CONTAINER = null;


    public static void init() {
        SimpleCapabilityManager.registerCapabilityWithNoDefault(IHeatContainer.class);
        SimpleCapabilityManager.registerCapabilityWithNoDefault(IVacuumContainer.class);
        SimpleCapabilityManager.registerCapabilityWithNoDefault(IPressureContainer.class);
    }

}
