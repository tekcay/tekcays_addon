package tekcays_addon.gtapi.unification.material.ore;

import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import tekcays_addon.gtapi.unification.material.info.TKCYAMaterialIconType;

import static gregtech.api.GTValues.M;
import static gregtech.api.unification.material.Materials.Wood;
import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_ROTOR;
import static gregtech.api.unification.ore.OrePrefix.Flags.ENABLE_UNIFICATION;
import static gregtech.api.unification.ore.OrePrefix.Flags.SELF_REFERENCING;
import static tekcays_addon.gtapi.unification.material.info.TKCYAMaterialFlags.*;

public class TKCYAOrePrefix {

    // Molds
    public static final OrePrefix moldEmpty = new OrePrefix("moldEmpty", -1, MarkerMaterials.Empty, TKCYAMaterialIconType.moldEmpty, SELF_REFERENCING, null);
    public static final OrePrefix moldIngot = new OrePrefix("moldIngot", -1, MarkerMaterials.Empty, TKCYAMaterialIconType.moldIngot, SELF_REFERENCING, null);
    public static final OrePrefix moldPlate = new OrePrefix("moldPlate", -1, MarkerMaterials.Empty, TKCYAMaterialIconType.moldPlate, SELF_REFERENCING, null);
    public static final OrePrefix moldStick = new OrePrefix("moldStick", -1, MarkerMaterials.Empty, TKCYAMaterialIconType.moldStick, SELF_REFERENCING, null);
    public static final OrePrefix moldStickLong = new OrePrefix("moldStickLong", -1, MarkerMaterials.Empty, TKCYAMaterialIconType.moldStickLong, SELF_REFERENCING, null);
    public static final OrePrefix moldGear = new OrePrefix("moldGear", -1, MarkerMaterials.Empty, TKCYAMaterialIconType.moldGear, SELF_REFERENCING, null);
    public static final OrePrefix moldGearSmall = new OrePrefix("moldGearSmall", -1, MarkerMaterials.Empty, TKCYAMaterialIconType.moldGearSmall, SELF_REFERENCING, null);
    public static final OrePrefix moldBolt = new OrePrefix("moldBolt", -1, MarkerMaterials.Empty, TKCYAMaterialIconType.moldBolt, SELF_REFERENCING, null);
    public static final OrePrefix moldRing = new OrePrefix("moldRing", -1, MarkerMaterials.Empty, TKCYAMaterialIconType.moldRing, SELF_REFERENCING, null);
    public static final OrePrefix moldBlock = new OrePrefix("moldBlock", -1, MarkerMaterials.Empty, TKCYAMaterialIconType.moldBlock, SELF_REFERENCING, null);


    // Others

    public static final OrePrefix bottleGlass = new OrePrefix("bottleGlass", -1, MarkerMaterials.Empty, null, SELF_REFERENCING, null);
    public static final OrePrefix curvedPlate = new OrePrefix("curvedPlate", M, null, TKCYAMaterialIconType.curvedPlate, ENABLE_UNIFICATION, mat -> mat.hasFlag(GENERATE_ROTOR) || mat.hasFlag(GENERATE_CURVED_PLATE));
    public static final OrePrefix cutWood = new OrePrefix("cutWood", 1, Wood,  null, SELF_REFERENCING, null);

}


