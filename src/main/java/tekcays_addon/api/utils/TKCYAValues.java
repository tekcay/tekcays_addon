package tekcays_addon.api.utils;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import tekcays_addon.api.unification.TKCYAMaterials;

import static gregtech.api.unification.ore.OrePrefix.gem;
import static gregtech.common.items.MetaItems.*;

import java.util.*;

public class TKCYAValues {

    //Mods

    public static final String GREGIFICATION_MODID = "gregification";
    public static final String GCYS_MODID = "gcys";
    public static final String GCYM_MODID = "gcym";

    //Lists

    public static final MetaItem.MetaValueItem[] ELECTRIC_PUMPS = new MetaItem.MetaValueItem[] {
            ELECTRIC_PUMP_LV, ELECTRIC_PUMP_MV, ELECTRIC_PUMP_HV, ELECTRIC_PUMP_EV, ELECTRIC_PUMP_IV,
            ELECTRIC_PUMP_LuV, ELECTRIC_PUMP_ZPM};

    //////////////////
    // For the New Blast Furnace
    //////////////////

    public static final Fluid[] ACCEPTED_INPUT_FLUIDS = {Materials.Air.getFluid(), TKCYAMaterials.HotFlueGas.getFluid()};
    public static final ItemStack[] ACCEPTED_INPUT_ITEMS = {OreDictUnifier.get(gem, Materials.Charcoal), OreDictUnifier.get(gem, Materials.Coal), OreDictUnifier.get(gem, Materials.Coke)};
    public static final int[] ACCEPTED_INPUT_FLUIDS_MULTIPLIER = {10, 1};
    public static final int[] ACCEPTED_INPUT_ITEMS_MULTIPLIER = {2, 2, 1};

    private static final Map<Fluid, Integer> GAS_COST_MAP = new TreeMap<>();
    private static final Map<ItemStack, Integer> ITEM_COST_MAP = new TreeMap<>();


    public static void setGasCostMap() {

        if (ACCEPTED_INPUT_FLUIDS.length != ACCEPTED_INPUT_FLUIDS_MULTIPLIER.length) return;

        for (int i = 0; i < ACCEPTED_INPUT_FLUIDS.length; i++) {
            GAS_COST_MAP.put(ACCEPTED_INPUT_FLUIDS[i], ACCEPTED_INPUT_FLUIDS_MULTIPLIER[i]);
        }
    }

    public static Map<Fluid, Integer> getGasCostMap() {
        return GAS_COST_MAP;
    }

    public static void setItemCostMap() {

        if (ACCEPTED_INPUT_ITEMS.length != ACCEPTED_INPUT_ITEMS_MULTIPLIER.length) return;

        for (int i = 0; i < ACCEPTED_INPUT_ITEMS.length; i++) {
            ITEM_COST_MAP.put(ACCEPTED_INPUT_ITEMS[i], ACCEPTED_INPUT_ITEMS_MULTIPLIER[i]);
        }
    }

    public static Map<ItemStack, Integer> getItemCostMap() {
        return ITEM_COST_MAP;
    }

}
