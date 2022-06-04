package tekcays_addon.api.metatileentity;

import tekcays_addon.api.capability.IParallelHatch;
import gregtech.api.metatileentity.ITieredMetaTileEntity;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;

@SuppressWarnings("InstantiationOfUtilityClass")
public class TKCYAMultiblockAbility {

    public static final MultiblockAbility<IParallelHatch> PARALLEL_HATCH = new MultiblockAbility<>("parallel_hatch");

    public static final MultiblockAbility<ITieredMetaTileEntity> TIERED_HATCH = new MultiblockAbility<>("tiered_hatch");
}
