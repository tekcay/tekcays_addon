package tekcays_addon.api.metatileentity.multiblock;

import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import tekcays_addon.api.capability.IHeatContainer;
import tekcays_addon.api.capability.IPressureContainer;

@SuppressWarnings("InstantiationOfUtilityClass")
public class TKCYAMultiblockAbility {

    public static final MultiblockAbility<IHeatContainer> HEAT_CONTAINER = new MultiblockAbility<>("heat_container");
    public static final MultiblockAbility<IPressureContainer> PRESSURE_CONTAINER = new MultiblockAbility<>("pressure_container");

}
