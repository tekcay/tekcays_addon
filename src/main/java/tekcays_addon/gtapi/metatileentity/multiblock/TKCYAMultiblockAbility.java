package tekcays_addon.gtapi.metatileentity.multiblock;

import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import tekcays_addon.gtapi.capability.containers.*;

@SuppressWarnings("InstantiationOfUtilityClass")
public class TKCYAMultiblockAbility {

    public static final MultiblockAbility<IHeatContainer> HEAT_CONTAINER = new MultiblockAbility<>("heat_container");
    public static final MultiblockAbility<IVacuumContainer> VACUUM_CONTAINER = new MultiblockAbility<>("vacuum_container");
    public static final MultiblockAbility<IPressureContainer> PRESSURE_CONTAINER = new MultiblockAbility<>("pressure_container");
    public static final MultiblockAbility<IContainerDetector> CONTAINER_CONTROL = new MultiblockAbility<>("pressure_control");
    public static final MultiblockAbility<IRotationContainer> ROTATION_CONTAINER = new MultiblockAbility<>("rotation_container");
    public static final MultiblockAbility<IDecompression> DECOMPRESSOR_CONTAINER = new MultiblockAbility<>("decompressor_container");
    public static final MultiblockAbility<IMoldCoverable> MOLD_COVERABLE_CONTAINER = new MultiblockAbility<>("mold_coverable_container");

}
