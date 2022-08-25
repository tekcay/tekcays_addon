package tekcays_addon.api.utils;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import tekcays_addon.api.unification.TKCYAMaterials;
import gregtech.api.unification.ore.OrePrefix;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;

import static gregtech.api.unification.ore.OrePrefix.gem;
import static gregtech.common.items.MetaItems.*;
import static tekcays_addon.api.unification.TKCYAMaterials.*;
import static tekcays_addon.api.unification.material.ore.TKCYAOrePrefix.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    //Lists/Arrays

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



    //////////////////
    // For the New Blast Furnace
    //////////////////

    private static final Map<Fluid, Integer> GAS_COST_MAP = Stream.of(new Object[][] {
            {Materials.Air.getFluid(), 1},
            {TKCYAMaterials.VeryHotAir.getFluid(), 10},
    }).collect(Collectors.toMap(data -> (Fluid) data[0], data -> (Integer) data[1]));

    private static final Map<ItemStack, Integer> ITEM_COST_MAP = Stream.of(new Object[][] {
            {OreDictUnifier.get(gem, Materials.Charcoal), 1},
            {OreDictUnifier.get(gem, Materials.Coal), 1},
            {OreDictUnifier.get(gem, Materials.Coke), 2},
    }).collect(Collectors.toMap(data -> (ItemStack) data[0], data -> (Integer) data[1]));




    /**
     * @return a {@code Map} with every gas ({@code Fluid}) accepted as fuel in {@code MetaTileEntityTKCYABlastFurnace} as the
     * {@code key} and the corresponding multiplier ({@code int}) and the {@code value}.
     * <br /><br />
     * The higher the multiplier, the higher the increasing temperature step.
     * <br /><br />
     * {@code .keySet()} return all the accepted fluids ({@code Fluid[]}).
     * <br /><br />
     * {@code .values()} return all the multipliers ({@code int[]}).
     */
    public static Map<Fluid, Integer> getGasCostMap() {
        return GAS_COST_MAP;
    }

    /**
     * @return a {@code Map} with every stack ({@code ItemStack}) accepted as fuel in {@code MetaTileEntityTKCYABlastFurnace} as the
     * {@code key} and the corresponding multiplier ({@code int}) and the {@code value}.
     * <br /><br />
     * The higher the multiplier, the higher the increasing temperature step.
     * <br /><br />
     * {@code .keySet()} return all the accepted items ({@code ItemStack[]}).
     * <br /><br />
     * {@code .values()} return all the multipliers ({@code int[]}).
     */
    public static Map<ItemStack, Integer> getItemCostMap() {
        return ITEM_COST_MAP;
    }

    public static final Material[] MOLD_MATERIALS = new Material[]{Steel, Tungsten, Carbon, Ceramic};
    public static final OrePrefix[] MOLDS = new OrePrefix[]{moldEmpty, moldIngot, moldPlate, moldStick, moldStickLong, moldGear,
            moldGearSmall, moldBolt, moldBall, moldCylinder, moldRing, moldRotor, moldCasing, moldBottle, moldBlock};

    public static final OrePrefix[] POLARIZING_PREFIXES = new OrePrefix[]{
        stick, stickLong, plate, ingot, plateDense, rotor, bolt, screw, wireFine, foil, dust, ring};

    public static final OrePrefix[] STEEL_TO_GALVANIZED_OREPREFIXES = new OrePrefix[]{
        ingot, plate, plateDouble, stick, stickLong, bolt, screw, ring, rotor, spring, springSmall, gear, gearSmall, frameGt, block};

   public static final Material[] ELECTRODE_MATERIALS = new Material[]{
           Gold, Copper, Platinum, Carbon, Steel, Iridium};

    public static final Material[] FILTER_MATERIALS = new Material[]{
            Steel, StainlessSteel};

}

