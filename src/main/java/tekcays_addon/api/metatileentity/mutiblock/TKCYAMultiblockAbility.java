package tekcays_addon.api.metatileentity.mutiblock;

import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import tekcays_addon.api.capability.IHeatContainer;
import tekcays_addon.api.capability.ILaserContainer;

@SuppressWarnings("InstantiationOfUtilityClass")
public class TKCYAMultiblockAbility {

    public static final MultiblockAbility<IHeatContainer> HEAT_CONTAINER = new MultiblockAbility<>("heat_container");
    public static final MultiblockAbility<ILaserContainer> LASER_CONTAINER = new MultiblockAbility<>("laser_container");

}
