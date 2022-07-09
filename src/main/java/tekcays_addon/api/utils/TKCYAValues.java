package tekcays_addon.api.utils;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;


import static gregtech.common.items.MetaItems.*;
import static tekcays_addon.api.unification.TKCYAMaterials.Ceramic;

import java.util.*;

public class TKCYAValues {

    //Mods

    public static final String GREGIFICATION_MODID = "gregification";
    public static final String GCYS_MODID = "gcys";
    public static final String GCYM_MODID = "gcym";

    //Lists/Arrays

    public static final MetaItem.MetaValueItem[] ELECTRIC_PUMPS = new MetaItem.MetaValueItem[]{
            ELECTRIC_PUMP_LV, ELECTRIC_PUMP_MV, ELECTRIC_PUMP_HV, ELECTRIC_PUMP_EV, ELECTRIC_PUMP_IV,
            ELECTRIC_PUMP_LuV, ELECTRIC_PUMP_ZPM};

    public static final Material[] MOLD_MATERIALS = new Material[]{Steel, Tungsten, Carbon, Ceramic};

    public static final OrePrefix[] POLARIZING_PREFIXES = new OrePrefix[]{
        stick, stickLong, plate, ingot, plateDense, rotor, bolt, screw, wireFine, foil, dust, ring};

    public static final OrePrefix[] STEEL_TO_GALVANIZED_OREPREFIXES = new OrePrefix[]{
        ingot, plate, plateDouble, stick, stickLong, bolt, screw, ring, rotor, spring, springSmall, gear, gearSmall, frameGt, block};

   public static final Material[] ELECTRODE_MATERIALS = new Material[]{
           Gold, Copper, Platinum, Carbon, Steel};

}

