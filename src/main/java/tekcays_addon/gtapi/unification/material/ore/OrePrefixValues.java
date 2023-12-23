package tekcays_addon.gtapi.unification.material.ore;

import gregtech.api.unification.material.properties.FluidPipeProperties;
import gregtech.api.unification.material.properties.ItemPipeProperties;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import net.minecraft.client.resources.I18n;
import tekcays_addon.common.TKCYAConfigHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static gregtech.api.unification.material.properties.PropertyKey.FLUID_PIPE;
import static gregtech.api.unification.material.properties.PropertyKey.ITEM_PIPE;
import static gregtech.api.unification.ore.OrePrefix.*;
import static tekcays_addon.gtapi.unification.material.ore.TKCYAOrePrefix.curvedPlate;

public class OrePrefixValues {

    public static final List<OrePrefix> FLUID_PIPES_ORES = new ArrayList<>();
    public static final List<OrePrefix> FLUID_PIPES_SINGLE = new ArrayList<>();
    public static final List<OrePrefix> FLUID_PIPES_MULTI = new ArrayList<>();
    public static final List<OrePrefix> ITEM_PIPES_ORES = new ArrayList<>();
    public static final List<OrePrefix> ITEM_PIPES_NORMAL = new ArrayList<>();
    public static final List<OrePrefix> ITEM_PIPES_RESTRICTIVE = new ArrayList<>();
    public static final Map<OrePrefix, OrePrefix> NORMAL_TO_RESTRICTIVE_PIPES = new HashMap<>();
    public static final List<OrePrefix> WIRES = new ArrayList<>();
    public static final Map<PropertyKey<FluidPipeProperties>, List<OrePrefix>> FLUID_PIPE_PROPERTY_TO_ORE_PREFIX = new HashMap<>();
    public static final Map<PropertyKey<ItemPipeProperties>, List<OrePrefix>> ITEM_PIPE_PROPERTY_TO_ORE_PREFIX = new HashMap<>();
    public static Map<OrePrefix,Double> ORE_PREFIX_TO_UNITS = new HashMap<>();
    public static void addUnitTooltip(UnificationEntry unificationEntry, List<String> tooltips) {
        OrePrefix orePrefix = unificationEntry.orePrefix;
        if (OrePrefixValues.ORE_PREFIX_TO_UNITS.containsKey(orePrefix)) {
            String unit = OrePrefixValues.getOrePrefixUnitAsText(orePrefix);
            if (orePrefix == ore) {
                tooltips.add(I18n.format("tkcya.oreprefix.ore.unit","1.000", unit));
            } else {
                tooltips.add(I18n.format("tkcya.oreprefix.unit", unit));
            }
        }
    }
    
    static {
            ORE_PREFIX_TO_UNITS.put(ore, (double) TKCYAConfigHolder.harderStuff.maxOutputPerOre);
            ORE_PREFIX_TO_UNITS.put(pipeNonupleFluid, 27.000);
            ORE_PREFIX_TO_UNITS.put(pipeQuadrupleFluid, 12.000);
            ORE_PREFIX_TO_UNITS.put(pipeHugeRestrictive, 12.500);
            ORE_PREFIX_TO_UNITS.put(pipeHugeFluid, 12.000);
            ORE_PREFIX_TO_UNITS.put(pipeHugeItem, 12.000);
            ORE_PREFIX_TO_UNITS.put(block, 9.000);
            ORE_PREFIX_TO_UNITS.put(plateDense, 9.000);
            ORE_PREFIX_TO_UNITS.put(pipeLargeRestrictive, 6.500);
            ORE_PREFIX_TO_UNITS.put(pipeLargeFluid, 6.000);
            ORE_PREFIX_TO_UNITS.put(pipeLargeItem, 6.000);
            ORE_PREFIX_TO_UNITS.put(rotor, 4.250);
            ORE_PREFIX_TO_UNITS.put(gear, 4.000);
            ORE_PREFIX_TO_UNITS.put(toolHeadBuzzSaw, 4.000);
            ORE_PREFIX_TO_UNITS.put(wireGtHex, 4.000);
            ORE_PREFIX_TO_UNITS.put(pipeNormalRestrictive, 3.500);
            ORE_PREFIX_TO_UNITS.put(pipeNormalFluid, 3.000);
            ORE_PREFIX_TO_UNITS.put(pipeNormalItem, 3.000);
            ORE_PREFIX_TO_UNITS.put(spring, 2.000);
            ORE_PREFIX_TO_UNITS.put(plateDouble, 2.000);
            ORE_PREFIX_TO_UNITS.put(wireGtOctal, 2.000);
            ORE_PREFIX_TO_UNITS.put(pipeSmallRestrictive, 1.500);
            ORE_PREFIX_TO_UNITS.put(pipeSmallFluid, 1.000);
            ORE_PREFIX_TO_UNITS.put(pipeSmallItem, 1.000);
            ORE_PREFIX_TO_UNITS.put(plate, 1.000);
            ORE_PREFIX_TO_UNITS.put(curvedPlate, 1.000);
            ORE_PREFIX_TO_UNITS.put(stickLong, 1.000);
            ORE_PREFIX_TO_UNITS.put(ingot, 1.000);
            ORE_PREFIX_TO_UNITS.put(ingotHot, 1.000);
            ORE_PREFIX_TO_UNITS.put(dust, 1.000);
            ORE_PREFIX_TO_UNITS.put(dustImpure, 1.000);
            ORE_PREFIX_TO_UNITS.put(dustPure, 1.000);
            ORE_PREFIX_TO_UNITS.put(wireGtQuadruple, 1.000);
            ORE_PREFIX_TO_UNITS.put(gearSmall, 1.000);
            ORE_PREFIX_TO_UNITS.put(pipeTinyFluid, 0.500);
            ORE_PREFIX_TO_UNITS.put(pipeTinyItem, 0.500);
            ORE_PREFIX_TO_UNITS.put(stick, 0.500);
            ORE_PREFIX_TO_UNITS.put(wireGtDouble, 0.500);
            ORE_PREFIX_TO_UNITS.put(wireGtSingle, 0.250);
            ORE_PREFIX_TO_UNITS.put(ring, 0.250);
            ORE_PREFIX_TO_UNITS.put(foil, 0.250);
            ORE_PREFIX_TO_UNITS.put(springSmall, 0.250);
            ORE_PREFIX_TO_UNITS.put(dustSmall, 0.250);
            ORE_PREFIX_TO_UNITS.put(bolt, 0.125);
            ORE_PREFIX_TO_UNITS.put(wireFine, 0.125);
            ORE_PREFIX_TO_UNITS.put(screw, 0.100);
            ORE_PREFIX_TO_UNITS.put(dustTiny, 0.025);
    }

    public static double getOrePrefixUnitAsDouble(OrePrefix orePrefix) {
        return OrePrefixValues.ORE_PREFIX_TO_UNITS.get(orePrefix);
    }

    /*
    public static int getOrePrefixUnitAsInt(OrePrefix orePrefix) {
        return (int) (getOrePrefixUnitAsDouble(orePrefix) * 100);
    }

     */

    public static String getOrePrefixUnitAsText(OrePrefix orePrefix) {
        return String.format("%.3f", getOrePrefixUnitAsDouble(orePrefix));
    }

    static {
        FLUID_PIPES_SINGLE.add(pipeHugeFluid);
        FLUID_PIPES_SINGLE.add(pipeLargeFluid);
        FLUID_PIPES_SINGLE.add(pipeNormalFluid);
        FLUID_PIPES_SINGLE.add(pipeSmallFluid);
        FLUID_PIPES_SINGLE.add(pipeTinyFluid);
    }

    static {
        FLUID_PIPES_MULTI.add(pipeNonupleFluid);
        FLUID_PIPES_MULTI.add(pipeQuadrupleFluid);
    }

    static {
        FLUID_PIPES_ORES.addAll(FLUID_PIPES_SINGLE);
        FLUID_PIPES_ORES.addAll(FLUID_PIPES_MULTI);
    }

    static {
        ITEM_PIPES_NORMAL.add(pipeHugeItem);
        ITEM_PIPES_NORMAL.add(pipeLargeItem);
        ITEM_PIPES_NORMAL.add(pipeNormalItem);
        ITEM_PIPES_NORMAL.add(pipeSmallItem);
        //ITEM_PIPES_ORES.add(pipeTinyItem); Removed from 2.9?
    }

    static {
       ITEM_PIPES_RESTRICTIVE.add(pipeHugeRestrictive);
       ITEM_PIPES_RESTRICTIVE.add(pipeLargeRestrictive);
       ITEM_PIPES_RESTRICTIVE.add(pipeNormalRestrictive);
       ITEM_PIPES_RESTRICTIVE.add(pipeSmallRestrictive);
    }

    static {
        NORMAL_TO_RESTRICTIVE_PIPES.put(pipeHugeItem, pipeHugeRestrictive);
        NORMAL_TO_RESTRICTIVE_PIPES.put(pipeLargeItem, pipeLargeRestrictive);
        NORMAL_TO_RESTRICTIVE_PIPES.put(pipeNormalItem, pipeNormalRestrictive);
        NORMAL_TO_RESTRICTIVE_PIPES.put(pipeSmallItem, pipeSmallRestrictive);
    }

    static {
        ITEM_PIPES_ORES.addAll(ITEM_PIPES_NORMAL);
        ITEM_PIPES_ORES.addAll(ITEM_PIPES_RESTRICTIVE);
    }
    static {
        WIRES.add(wireGtSingle);
        WIRES.add(wireGtDouble);
        WIRES.add(wireGtQuadruple);
        WIRES.add(wireGtOctal);
        WIRES.add(wireGtHex);
    }
    static {
        FLUID_PIPE_PROPERTY_TO_ORE_PREFIX.put(FLUID_PIPE, FLUID_PIPES_ORES);
    }
    static {
        ITEM_PIPE_PROPERTY_TO_ORE_PREFIX.put(ITEM_PIPE, ITEM_PIPES_ORES);
    }
}
