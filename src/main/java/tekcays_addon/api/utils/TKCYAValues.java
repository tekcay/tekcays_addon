package tekcays_addon.api.utils;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraftforge.fml.common.Loader;
import tekcays_addon.TekCaysAddon;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static tekcays_addon.api.unification.TKCYAMaterials.*;
import static tekcays_addon.api.unification.material.ore.TKCYAOrePrefix.*;

import java.nio.file.Path;
import java.util.*;

public class TKCYAValues {

    //Mods

    //Recipe properties keys
    public static final String INTERVAL_PRESSURE_PROPERTY = "interval_pressure_property";
    public static final String INTERVAL_TEMPERATURE_PROPERTY = "interval_temperatue_property";
    public static final String MIN_PRESSURE_PROPERTY = "minPressure";
    public static final String MAX_PRESSURE_PROPERTY = "maxPressure";
    public static final String MIN_TEMPERATURE_PROPERTY = "minTemperature";
    public static final String MAX_TEMPERATURE_PROPERTY = "maxTemperature";
    public static final String PRESSURIZED_FLUIDSTACK_PROPERTY = "pressurizedFluidStack";
    //Simple Values

    public static final Long[] EMPTY_LONG_TWO_ARRAY = new Long[]{0L, 0L};
    public static final Integer[] EMPTY_INT_TWO_ARRAY = new Integer[]{0, 0};

    /**
     * Improves the yield by {20%}.
     */
    public static final float CLEANROOM_MULTIPLIER = 1.2f;


    //// ENERGY_TYPE_RATIOS


    public static final float EU_TO_HU = 0.50F;


    //Heat
    public static final int ROOM_TEMPERATURE = 298;
    public static final int MIN_HEAT = 0;
    public static final int MAX_HEAT = 10000;

    //Pressure, in bar
    /**
     * Minimum amount of a {@code FluidStack} for it to not be null.
     */
    public static final int MINIMUM_FLUID_STACK_AMOUNT = 1;
    public static final float PERFECT_GAS_CONSTANT = 8.314f;
    public static final float FLUID_MULTIPLIER_PRESSURE = 100.0f;
    public static final String NO_FLUID = "";
    public static final int ABSOLUTE_VACUUM = 0;
    public static final long ATMOSPHERIC_PRESSURE = 101300;
    public static final float OXYGEN_IN_AIR_RATIO = 0.21f;
    public static final long MAX_PRESSURE = ATMOSPHERIC_PRESSURE * 2000;
    public static final float EU_TO_PU = 0.40F;
    public static final float EU_TO_VU = 0.30F;
    /**
     * One second in {@code tick}s.
     */
    public static final int SECOND = 20;
    /**
     * One minute in {@code tick}s.
     */
    public static final int MINUTE = 1200;
    /**
     * One hour in {@code tick}s.
     */
    public static final int HOUR = 72000;

    public static final int SLOT_INTERVAL = 19;

    /**
     * How much in Pa the pressure will increase in the Steam Air Compressor.
     */
    public static final int STEAM_AIR_COMPRESSOR_PRESSURE_INCREASE = 1000;

    //PATHS
    public static final Path TKCYA_CONFIG_PATH = Loader.instance().getConfigDir().toPath().resolve(TekCaysAddon.MODID);

    //Lists/Arrays

    public static final List<String> DIMENSIONS = new ArrayList<String>(){{
        add("overworld");
        add("nether");
        add("end");
    }};

    public static final List<EnumFacing> HORIZONTALS = new ArrayList<EnumFacing>(){{
       add(EnumFacing.SOUTH);
       add(EnumFacing.NORTH);
       add(EnumFacing.EAST);
       add(EnumFacing.WEST);
    }};

    public static final List<EnumFacing> VERTICALS = new ArrayList<EnumFacing>(){{
        add(EnumFacing.UP);
        add(EnumFacing.DOWN);
    }};

    public static final List<Material> DRUM_MATERIALS = new ArrayList<Material>(){{
        add(TreatedWood);
        add(Bronze);
        add(Steel);
        add(GalvanizedSteel);
        add(Aluminium);
        add(StainlessSteel);
        add(Titanium);
        add(TungstenSteel);
        add(Polytetrafluoroethylene);
        add(Polypropylene);
        add(HighDensityPolyethylene);
    }};


    public static final MetaItem<?>.MetaValueItem[] ELECTRIC_PUMPS = new MetaItem.MetaValueItem[]{
            ELECTRIC_PUMP_LV, ELECTRIC_PUMP_MV, ELECTRIC_PUMP_HV, ELECTRIC_PUMP_EV, ELECTRIC_PUMP_IV,
            ELECTRIC_PUMP_LuV, ELECTRIC_PUMP_ZPM};

    public static final List<Material> GTCEu_POLYMERS = new ArrayList<Material>(){{
        add(Polybenzimidazole);
        add(Polycaprolactam);
        add(Polydimethylsiloxane);
        add(PolyphenyleneSulfide);
        add(Polytetrafluoroethylene);
        add(PolyvinylAcetate);
        add(PolyvinylButyral);
        add(PolyvinylChloride);
        add(Polyethylene);
    }};

    public static final List<Material> POLYMERS = new ArrayList<Material>(){{
            add(HighDensityPolyethylene);
            add(Polypropylene);
            addAll(GTCEu_POLYMERS);
        }};

    public static final List<FluidStack> MIXTURE_TO_FILTER = new ArrayList<>();
    public static final List<ItemStack> DUST_MIXTURE_WITH_NBT = new ArrayList<>();

    public static final List<Material> MOLD_MATERIALS = new ArrayList<Material>(){{
        add(Steel);
        add(Ceramic);
        add(TungstenCarbide);
        add(Carbon);
    }};

    public static final OrePrefix[] MOLDS = new OrePrefix[]{moldEmpty, moldIngot, moldPlate, moldStick, moldStickLong, moldGear,
            moldGearSmall, moldBolt, moldBall, moldCylinder, moldRing, moldRotor, moldCasing, moldBottle, moldBlock};

    public static final OrePrefix[] POLARIZING_PREFIXES = new OrePrefix[]{
        stick, stickLong, plate, ingot, plateDense, rotor, bolt, screw, wireFine, foil, dust, ring};

    public static final OrePrefix[] STEEL_TO_GALVANIZED_OREPREFIXES = new OrePrefix[]{
        ingot, plate, plateDouble, stick, stickLong, bolt, screw, ring, rotor, spring, springSmall, gear, gearSmall, frameGt, block};

   public static final Material[] ELECTRODE_MATERIALS = new Material[]{
           Gold, Copper, Platinum, Carbon, Steel, Iridium, Aluminium};

    public static final Material[] FILTER_MATERIALS = new Material[]{
            Steel, StainlessSteel};

}

