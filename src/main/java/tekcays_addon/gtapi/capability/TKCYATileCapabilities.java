package tekcays_addon.gtapi.capability;

import gregtech.api.capability.SimpleCapabilityManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import tekcays_addon.gtapi.capability.containers.*;

public class TKCYATileCapabilities {

    @CapabilityInject(IHeatContainer.class)
    public static Capability<IHeatContainer> CAPABILITY_HEAT_CONTAINER = null;

    @CapabilityInject(IVacuumContainer.class)
    public static Capability<IVacuumContainer> CAPABILITY_VACUUM_CONTAINER = null;

    @CapabilityInject(IPressureContainer.class)
    public static Capability<IPressureContainer> CAPABILITY_PRESSURE_CONTAINER = null;

    @CapabilityInject(IPressureControl.class)
    public static Capability<IPressureControl> CAPABILITY_PRESSURE_CONTROL = null;

    @CapabilityInject(IRotationContainer.class)
    public static Capability<IRotationContainer> CAPABILITY_ROTATIONAL_CONTAINER = null;


    public static void init() {
        SimpleCapabilityManager.registerCapabilityWithNoDefault(IHeatContainer.class);
        SimpleCapabilityManager.registerCapabilityWithNoDefault(IVacuumContainer.class);
        SimpleCapabilityManager.registerCapabilityWithNoDefault(IPressureContainer.class);
        SimpleCapabilityManager.registerCapabilityWithNoDefault(IPressureControl.class);
        SimpleCapabilityManager.registerCapabilityWithNoDefault(IRotationContainer.class);
    }

}
