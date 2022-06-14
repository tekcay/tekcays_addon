package tekcays_addon.common.items;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.metaitem.StandardMetaItem;

import static tekcays_addon.common.items.TKCYAMetaItems.*;

public class TKCYAMetaItem1 extends StandardMetaItem {

    public TKCYAMetaItem1() {
        super();
    }

    @Override
    public void registerSubItems() {
        // Credits: ID 0-10

        MICA_SHEET = addItem(0, "mica_sheet");
        MICA_INSULATOR_SHEET = addItem(1, "mica_insulator_sheet");
        MICA_INSULATOR_FOIL = addItem(2, "mica_insulator_foil");

    }
}
