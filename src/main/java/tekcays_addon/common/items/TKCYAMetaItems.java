package tekcays_addon.common.items;

import gregtech.api.items.metaitem.MetaItem;


public final class TKCYAMetaItems {

    private TKCYAMetaItems(){
        super();
    }

    //public static final List<MetaItem<?>> ITEMS = MetaItem.getMetaItems();

    public static MetaItem<?>.MetaValueItem MICA_SHEET;
    public static MetaItem<?>.MetaValueItem MICA_INSULATOR_SHEET;
    public static MetaItem<?>.MetaValueItem MICA_INSULATOR_FOIL;
    public static MetaItem<?>.MetaValueItem GAS_COLLECTOR;

    public static MetaItem<?>.MetaValueItem ELECTRODE;


    public static void init() {
        TKCYAMetaItem1 metaItem1 = new TKCYAMetaItem1();
        metaItem1.setRegistryName("meta_item_1");
    }


}
