package tekcays_addon.api.unification.material.ore;

import gregtech.api.unification.ore.OrePrefix;
import tekcays_addon.api.unification.material.info.TKCYAMaterialIconType;

import static gregtech.api.GTValues.M;
import static gregtech.api.unification.ore.OrePrefix.Flags.ENABLE_UNIFICATION;
import static tekcays_addon.api.unification.material.info.TKCYAMaterialFlags.GENERATE_MOLDS;

public class TKCYAOrePrefix {

    // Molds
    public static final OrePrefix moldEmpty = new OrePrefix("moldEmpty", M * 4, null, TKCYAMaterialIconType.moldEmpty, ENABLE_UNIFICATION, mat -> mat.hasFlag(GENERATE_MOLDS));
    public static final OrePrefix moldIngot = new OrePrefix("moldIngot", M * 4, null, TKCYAMaterialIconType.moldIngot, ENABLE_UNIFICATION, mat -> mat.hasFlag(GENERATE_MOLDS));
    public static final OrePrefix moldPlate = new OrePrefix("moldPlate", M * 4, null, TKCYAMaterialIconType.moldPlate, ENABLE_UNIFICATION, mat -> mat.hasFlag(GENERATE_MOLDS));
    public static final OrePrefix moldStick = new OrePrefix("moldStick", M * 4, null, TKCYAMaterialIconType.moldStick, ENABLE_UNIFICATION, mat -> mat.hasFlag(GENERATE_MOLDS));
    public static final OrePrefix moldStickLong = new OrePrefix("moldStickLong", M * 4, null, TKCYAMaterialIconType.moldStickLong, ENABLE_UNIFICATION, mat -> mat.hasFlag(GENERATE_MOLDS));
    public static final OrePrefix moldGear = new OrePrefix("moldGear", M * 4, null, TKCYAMaterialIconType.moldGear, ENABLE_UNIFICATION, mat -> mat.hasFlag(GENERATE_MOLDS));
    public static final OrePrefix moldGearSmall = new OrePrefix("moldGearSmall", M * 4, null, TKCYAMaterialIconType.moldGearSmall, ENABLE_UNIFICATION, mat -> mat.hasFlag(GENERATE_MOLDS));
    public static final OrePrefix moldBolt = new OrePrefix("moldBolt", M * 4, null, TKCYAMaterialIconType.moldBolt, ENABLE_UNIFICATION, mat -> mat.hasFlag(GENERATE_MOLDS));
    public static final OrePrefix moldBall = new OrePrefix("moldBall", M * 4, null, TKCYAMaterialIconType.moldBall, ENABLE_UNIFICATION, mat -> mat.hasFlag(GENERATE_MOLDS));
    public static final OrePrefix moldCylinder = new OrePrefix("moldCylinder", M * 4, null, TKCYAMaterialIconType.moldCylinder, ENABLE_UNIFICATION, mat -> mat.hasFlag(GENERATE_MOLDS));
    public static final OrePrefix moldRing = new OrePrefix("moldRing", M * 4, null, TKCYAMaterialIconType.moldRing, ENABLE_UNIFICATION, mat -> mat.hasFlag(GENERATE_MOLDS));
    public static final OrePrefix moldRotor = new OrePrefix("moldRotor", M * 4, null, TKCYAMaterialIconType.moldRotor, ENABLE_UNIFICATION, mat -> mat.hasFlag(GENERATE_MOLDS));
    public static final OrePrefix moldCasing = new OrePrefix("moldCasing", M * 4, null, TKCYAMaterialIconType.moldCasing, ENABLE_UNIFICATION, mat -> mat.hasFlag(GENERATE_MOLDS));
}


