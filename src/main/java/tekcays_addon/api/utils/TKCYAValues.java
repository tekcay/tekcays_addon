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

    private static final Map<Fluid, Integer> GAS_COST_MAP = new TreeMap<>() {{
        put(Materials.Air.getFluid(), 10);
        put(TKCYAMaterials.HotFlueGas.getFluid(), 1);

    }};
    private static final Map<ItemStack, Integer> ITEM_COST_MAP = new TreeMap<>() {{
        put(OreDictUnifier.get(gem, Materials.Charcoal), 2);
        put(OreDictUnifier.get(gem, Materials.Coal), 2);
        put(OreDictUnifier.get(gem, Materials.Coke), 1);

    }};


    public static Map<Fluid, Integer> getGasCostMap() {
        return GAS_COST_MAP;
    }

    public static Map<ItemStack, Integer> getItemCostMap() {
        return ITEM_COST_MAP;
    }

}
