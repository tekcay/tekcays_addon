package tekcays_addon.gtapi.unification.material.ore;

import gregtech.api.unification.material.properties.FluidPipeProperties;
import gregtech.api.unification.material.properties.ItemPipeProperties;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;

import java.util.*;

import static gregtech.api.unification.material.properties.PropertyKey.FLUID_PIPE;
import static gregtech.api.unification.material.properties.PropertyKey.ITEM_PIPE;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.api.unification.ore.OrePrefix.wireFine;
import static tekcays_addon.gtapi.unification.material.ore.TKCYAOrePrefix.curvedPlate;

public class OrePrefixValues {

    public static final Map<OrePrefix, Double> OREPREFIX_TO_UNIT = new HashMap<OrePrefix, Double>(){{
        put(pipeHugeRestrictive, 12.5);
        put(pipeHugeFluid, 12.0);
        put(pipeHugeItem, 12.0);
        put(block, 9.0);
        put(plateDense, 9.0);
        put(pipeLargeRestrictive, 6.5);
        put(pipeLargeFluid, 6.0);
        put(pipeLargeItem, 6.0);
        put(rotor, 4.25);
        put(gear, 4.0);
        put(toolHeadBuzzSaw, 4.0);
        put(pipeNormalRestrictive, 3.5);
        put(pipeNonupleFluid, 3.0);
        put(pipeNormalItem, 3.0);
        put(spring, 2.0);
        put(plateDouble, 2.0);
        put(wireGtOctal, 2.0);
        put(wireGtHex, 1.5);
        put(pipeSmallRestrictive, 1.5);
        put(pipeSmallFluid, 1.0);
        put(pipeSmallItem, 1.0);
        put(plate, 1.0);
        put(curvedPlate, 1.0);
        put(stickLong, 1.0);
        put(ingot, 1.0);
        put(dust, 1.0);
        put(wireGtQuadruple, 1.0);
        put(gearSmall, 1.0);
        put(pipeTinyFluid, 0.5);
        put(pipeTinyItem, 0.5);
        put(stick, 0.5);
        put(wireGtDouble, 0.5);
        put(wireGtSingle, 0.25);
        put(ring, 0.25);
        put(springSmall, 0.25);
        put(dustSmall, 0.25);
        put(dustTiny, 0.125);
        put(bolt, 0.125);
        put(wireFine, 0.125);
    }};

    public static final List<OrePrefix> FLUID_PIPES_ORES = new ArrayList<>();
    public static final List<OrePrefix> ITEM_PIPES_ORES = new ArrayList<>();
    public static final Map<PropertyKey<FluidPipeProperties>, List<OrePrefix>> FLUID_PIPE_PROPERTY_TO_ORE_PREFIX = new HashMap<>();
    public static final Map<PropertyKey<ItemPipeProperties>, List<OrePrefix>> ITEM_PIPE_PROPERTY_TO_ORE_PREFIX = new HashMap<>();

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
        FLUID_PIPE_PROPERTY_TO_ORE_PREFIX.put(FLUID_PIPE, FLUID_PIPES_ORES);
    }
    static {
        ITEM_PIPE_PROPERTY_TO_ORE_PREFIX.put(ITEM_PIPE, ITEM_PIPES_ORES);
    }
}
