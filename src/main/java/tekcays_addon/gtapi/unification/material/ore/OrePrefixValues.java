package tekcays_addon.gtapi.unification.material.ore;

import gregtech.api.unification.material.properties.FluidPipeProperties;
import gregtech.api.unification.material.properties.ItemPipeProperties;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;

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
    public static final List<OrePrefix> ITEM_PIPES_ORES = new ArrayList<>();
    public static final List<OrePrefix> WIRES = new ArrayList<>();
    public static final Map<PropertyKey<FluidPipeProperties>, List<OrePrefix>> FLUID_PIPE_PROPERTY_TO_ORE_PREFIX = new HashMap<>();
    public static final Map<PropertyKey<ItemPipeProperties>, List<OrePrefix>> ITEM_PIPE_PROPERTY_TO_ORE_PREFIX = new HashMap<>();
    public static Map<OrePrefix,Double> ORE_PREFIX_TO_UNITS = new HashMap<>();

    public static void init() {
        ORE_PREFIX_TO_UNITS = new HashMap<OrePrefix, Double>(){{
            put(pipeNonupleFluid, 27.000);
            put(pipeQuadrupleFluid, 12.000);
            put(pipeHugeRestrictive, 12.500);
            put(pipeHugeFluid, 12.000);
            put(pipeHugeItem, 12.000);
            put(block, 9.000);
            put(plateDense, 9.000);
            put(pipeLargeRestrictive, 6.500);
            put(pipeLargeFluid, 6.000);
            put(pipeLargeItem, 6.000);
            put(rotor, 4.250);
            put(gear, 4.000);
            put(toolHeadBuzzSaw, 4.000);
            put(wireGtHex, 4.000);
            put(pipeNormalRestrictive, 3.500);
            put(pipeNormalFluid, 3.000);
            put(pipeNormalItem, 3.000);
            put(spring, 2.000);
            put(plateDouble, 2.000);
            put(wireGtOctal, 2.000);
            put(pipeSmallRestrictive, 1.500);
            put(pipeSmallFluid, 1.000);
            put(pipeSmallItem, 1.000);
            put(plate, 1.000);
            put(curvedPlate, 1.000);
            put(stickLong, 1.000);
            put(ingot, 1.000);
            put(dust, 1.000);
            put(dustImpure, 1.000);
            put(dustPure, 1.000);
            put(wireGtQuadruple, 1.000);
            put(gearSmall, 1.000);
            put(pipeTinyFluid, 0.500);
            put(pipeTinyItem, 0.500);
            put(stick, 0.500);
            put(wireGtDouble, 0.500);
            put(wireGtSingle, 0.250);
            put(ring, 0.250);
            put(springSmall, 0.250);
            put(dustSmall, 0.250);
            put(bolt, 0.125);
            put(wireFine, 0.125);
            put(screw, 0.100);
            put(dustTiny, 0.025);
        }};
    }

    public static String getOrePrefixUnit(OrePrefix orePrefix) {
        Double units = OrePrefixValues.ORE_PREFIX_TO_UNITS.get(orePrefix);
        return String.format("%.3f", units);
    }

    static {
        FLUID_PIPES_ORES.add(pipeHugeFluid);
        FLUID_PIPES_ORES.add(pipeLargeFluid);
        FLUID_PIPES_ORES.add(pipeNormalFluid);
        FLUID_PIPES_ORES.add(pipeSmallFluid);
        FLUID_PIPES_ORES.add(pipeTinyFluid);
        FLUID_PIPES_ORES.add(pipeNonupleFluid);
        FLUID_PIPES_ORES.add(pipeQuadrupleFluid);
    }
    static {
        FLUID_PIPES_ORES.add(pipeHugeItem);
        FLUID_PIPES_ORES.add(pipeLargeItem);
        FLUID_PIPES_ORES.add(pipeNormalItem);
        FLUID_PIPES_ORES.add(pipeSmallItem);
        FLUID_PIPES_ORES.add(pipeTinyItem);
        FLUID_PIPES_ORES.add(pipeHugeRestrictive);
        FLUID_PIPES_ORES.add(pipeLargeRestrictive);
        FLUID_PIPES_ORES.add(pipeSmallRestrictive);
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
