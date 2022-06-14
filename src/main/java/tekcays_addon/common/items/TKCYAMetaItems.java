package tekcays_addon.common.items;

import gregtech.api.items.metaitem.MetaItem;

import java.util.List;

public final class TKCYAMetaItems {

    private TKCYAMetaItems(){
        super();
    }

    public static final List<MetaItem<?>> ITEMS = MetaItem.getMetaItems();

    public static MetaItem<?>.MetaValueItem MICA_SHEET;
    public static MetaItem<?>.MetaValueItem MICA_INSULATOR_SHEET;
    public static MetaItem<?>.MetaValueItem MICA_INSULATOR_FOIL;


    public static void init() {
        TKCYAMetaItem1 metaItem1 = new TKCYAMetaItem1();
        metaItem1.setRegistryName("meta_item_1");


}
