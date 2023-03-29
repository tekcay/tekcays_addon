package tekcays_addon.common.items;

import gregtech.api.items.metaitem.MetaItem;
import net.minecraft.item.ItemStack;
import tekcays_addon.common.items.behaviors.ElectrodeBehavior;

import static gregtech.api.unification.material.Materials.*;


public final class TKCYAMetaItems {

    private TKCYAMetaItems(){
        super();
    }

    public static MetaItem<?>.MetaValueItem MICA_SHEET;
    public static MetaItem<?>.MetaValueItem MICA_INSULATOR_SHEET;
    public static MetaItem<?>.MetaValueItem MICA_INSULATOR_FOIL;
    public static MetaItem<?>.MetaValueItem GAS_COLLECTOR;

    //Damageable items
    public static MetaItem<?>.MetaValueItem ELECTRODE;
    public static MetaItem<?>.MetaValueItem FILTER;

    //Mixtures
    public static MetaItem<?>.MetaValueItem DUST_MIXTURE;

    //Covers
    public static MetaItem<?>.MetaValueItem COVER_TEMPERATURE_DETECTOR;


    public static void init() {
        TKCYAMetaItem1 metaItem1 = new TKCYAMetaItem1((short) 0);
        metaItem1.setRegistryName("meta_item_1");

    }


}
