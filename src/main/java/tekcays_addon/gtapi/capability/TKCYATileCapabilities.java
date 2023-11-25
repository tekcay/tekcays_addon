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

    @CapabilityInject(IContainerDetector.class)
    public static Capability<IContainerDetector> CAPABILITY_CONTAINER_DETECTOR = null;

    @CapabilityInject(IRotationContainer.class)
    public static Capability<IRotationContainer> CAPABILITY_ROTATIONAL_CONTAINER = null;

    @CapabilityInject(ISteamConsumer.class)
    public static Capability<ISteamConsumer> CAPABILITY_STEAM_CONSUMER_CONTAINER = null;

    @CapabilityInject(IDecompression.class)
    public static Capability<IDecompression> CAPABILITY_DECOMPRESSION_CONTAINER = null;

    @CapabilityInject(IMoldCoverable.class)
    public static Capability<IMoldCoverable> CAPABILITY_MOLD_COVERABLE = null;

    @CapabilityInject(IHinderedContainer.class)
    public static Capability<IHinderedContainer> CAPABILITY_HINDERED_CONTAINER = null;

    @CapabilityInject(LogisticContainer.class)
    public static Capability<LogisticContainer> CAPABILITY_FLUID_LOGISTIC = null;

    @CapabilityInject(LogisticContainer.class)
    public static Capability<LogisticContainer> CAPABILITY_ITEM_LOGISTIC = null;


    public static void init() {
        SimpleCapabilityManager.registerCapabilityWithNoDefault(IHeatContainer.class);
        SimpleCapabilityManager.registerCapabilityWithNoDefault(IVacuumContainer.class);
        SimpleCapabilityManager.registerCapabilityWithNoDefault(IPressureContainer.class);
        SimpleCapabilityManager.registerCapabilityWithNoDefault(IContainerDetector.class);
        SimpleCapabilityManager.registerCapabilityWithNoDefault(IRotationContainer.class);
        SimpleCapabilityManager.registerCapabilityWithNoDefault(ISteamConsumer.class);
        SimpleCapabilityManager.registerCapabilityWithNoDefault(IDecompression.class);
        SimpleCapabilityManager.registerCapabilityWithNoDefault(IMoldCoverable.class);
        SimpleCapabilityManager.registerCapabilityWithNoDefault(IHinderedContainer.class);
        SimpleCapabilityManager.registerCapabilityWithNoDefault(LogisticContainer.class);
    }

}
