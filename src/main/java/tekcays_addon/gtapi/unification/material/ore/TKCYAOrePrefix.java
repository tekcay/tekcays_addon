package tekcays_addon.gtapi.unification.material.ore;

import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.ore.OrePrefix;
import tekcays_addon.gtapi.unification.material.info.TKCYAMaterialIconType;

import static gregtech.api.GTValues.M;
import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_ROTOR;
import static gregtech.api.unification.ore.OrePrefix.Flags.ENABLE_UNIFICATION;
import static gregtech.api.unification.ore.OrePrefix.Flags.SELF_REFERENCING;
import static tekcays_addon.gtapi.unification.material.info.TKCYAMaterialFlags.*;

public class TKCYAOrePrefix {

    // Molds
    public static final OrePrefix moldEmpty = new OrePrefix("moldEmpty", M * 4, null, TKCYAMaterialIconType.moldEmpty, ENABLE_UNIFICATION, material -> material.hasFlag(GENERATE_MOLDS));
    public static final OrePrefix moldIngot = new OrePrefix("moldIngot", M * 4, null, TKCYAMaterialIconType.moldIngot, ENABLE_UNIFICATION, null);
    public static final OrePrefix moldPlate = new OrePrefix("moldPlate", M * 4, null, TKCYAMaterialIconType.moldPlate, ENABLE_UNIFICATION, null);
    public static final OrePrefix moldStick = new OrePrefix("moldStick", M * 4, null, TKCYAMaterialIconType.moldStick, ENABLE_UNIFICATION, null);
    public static final OrePrefix moldStickLong = new OrePrefix("moldStickLong", M * 4, null, TKCYAMaterialIconType.moldStickLong, ENABLE_UNIFICATION, null);
    public static final OrePrefix moldGear = new OrePrefix("moldGear", M * 4, null, TKCYAMaterialIconType.moldGear, ENABLE_UNIFICATION, null);
    public static final OrePrefix moldGearSmall = new OrePrefix("moldGearSmall", M * 4, null, TKCYAMaterialIconType.moldGearSmall, ENABLE_UNIFICATION, null);
    public static final OrePrefix moldBolt = new OrePrefix("moldBolt", M * 4, null, TKCYAMaterialIconType.moldBolt, ENABLE_UNIFICATION, null);
    public static final OrePrefix moldRing = new OrePrefix("moldRing", M * 4, null, TKCYAMaterialIconType.moldRing, ENABLE_UNIFICATION, null);
    public static final OrePrefix moldBlock = new OrePrefix("moldBlock", M * 4, null, TKCYAMaterialIconType.moldBlock, ENABLE_UNIFICATION, null);


    // Others

    public static final OrePrefix bottleGlass = new OrePrefix("bottleGlass", -1, MarkerMaterials.Color.Colorless, null, SELF_REFERENCING, null);
    public static final OrePrefix curvedPlate = new OrePrefix("curvedPlate", M, null, TKCYAMaterialIconType.curvedPlate, ENABLE_UNIFICATION, mat -> mat.hasFlag(GENERATE_ROTOR) || mat.hasFlag(GENERATE_CURVED_PLATE));

}


