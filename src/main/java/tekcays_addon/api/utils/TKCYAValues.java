package tekcays_addon.api.utils;

import gregtech.api.GregTechAPI;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;
import gregtech.api.unification.ore.OrePrefix;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static tekcays_addon.api.unification.TKCYAMaterials.*;
import static tekcays_addon.api.unification.material.ore.TKCYAOrePrefix.*;

import java.util.ArrayList;
import java.util.List;

public class TKCYAValues {

    //Mods

    public static final String GREGIFICATION_MODID = "gregification";
    public static final String GCYS_MODID = "gcys";
    public static final String GCYM_MODID = "gcym";

    //Simple Values

    /**
     * Improves the yield by {20%}.
     */
    public static final float CLEANROOM_MULTIPLIER = 1.2f;


    //Heat

    public static final int MIN_HEAT = 0;
    public static final int MAX_HEAT = 10000;

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

    /**
     * How much in Pa the pressure will increase in the Steam Air Compressor.
     */
    public static final int STEAM_AIR_COMPRESSOR_PRESSURE_INCREASE = 1000;

    //Lists/Arrays
    /*
    public static final List<Material> ALL_MATERIALS = new ArrayList<Material>(){{
        addAll(GregTechAPI.MATERIAL_REGISTRY);
        a
    }}

     */

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


    public static final MetaItem.MetaValueItem[] ELECTRIC_PUMPS = new MetaItem.MetaValueItem[]{
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

