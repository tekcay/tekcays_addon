package tekcays_addon.common.items;

import gregtech.api.items.metaitem.StandardMetaItem;
import gregtech.common.items.behaviors.TurbineRotorBehavior;
import tekcays_addon.common.items.behaviors.ElectrodeBehavior;
import tekcays_addon.common.items.behaviors.FilterBehavior;

import static gregtech.common.items.MetaItems.TURBINE_ROTOR;
import static tekcays_addon.common.items.TKCYAMetaItems.*;

public class TKCYAMetaItem1 extends StandardMetaItem {

    public TKCYAMetaItem1() {
        super();
    }

    @Override
    public void registerSubItems() {

        MICA_SHEET = addItem(0, "mica.sheet");
        MICA_INSULATOR_SHEET = addItem(1, "mica_insulator.sheet");
        MICA_INSULATOR_FOIL = addItem(2, "mica_insulator.foil");
        GAS_COLLECTOR = addItem(3, "gas_collector");

        //Damageable items
        ELECTRODE = addItem(4, "electrode").addComponents(new ElectrodeBehavior());
        FILTER = addItem(5, "filter").addComponents(new FilterBehavior());

    }
}
