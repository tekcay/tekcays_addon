package tekcays_addon.gtapi.metatileentity.multiblock;

import gregtech.api.capability.IMufflerHatch;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
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
    public static final MultiblockAbility<IItemHandlerModifiable> BRICK_IMPORT_ITEMS = new MultiblockAbility<>("brick__import_items");
    public static final MultiblockAbility<IItemHandlerModifiable> BRICK_EXPORT_ITEMS = new MultiblockAbility<>("brick__export_items");
    public static final MultiblockAbility<IFluidTank> BRICK_EXPORT_FLUIDS = new MultiblockAbility<>("brick_export_fluids");
    public static final MultiblockAbility<IFluidTank> BRICK_IMPORT_FLUIDS = new MultiblockAbility<>("brick_import_fluids");
    public static final MultiblockAbility<IMufflerHatch> BRICK_MUFFLER_HATCH = new MultiblockAbility<>("brick_muffler_hatch");
    public static final MultiblockAbility<IItemHandler> CRATE_VALVE = new MultiblockAbility<>("crate_valve");

}
