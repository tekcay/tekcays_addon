package tekcays_addon.common.items;

import gregtech.api.items.metaitem.StandardMetaItem;
import tekcays_addon.api.items.metaitem.TraysStats;
import tekcays_addon.common.items.behaviors.ElectrodeBehavior;

import static gregtech.api.unification.material.Materials.StainlessSteel;
import static gregtech.api.unification.material.Materials.Steel;
import static tekcays_addon.api.unification.TKCYAMaterials.GalvanizedSteel;
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
        ELECTRODE = addItem(4, "electrode").addComponents(new ElectrodeBehavior());

        //Bubble caps
        BUBBLE_CAP_STEEL = addItem(5, "bubble_cap.steel");
        BUBBLE_CAP_GALVANIZED_STEEL = addItem(6, "bubble_cap.galvanized_steel");
        BUBBLE_CAP_STAINLESS_STEEL = addItem(7, "bubble_cap.stainless_steel");

        MICRO_BUBBLE_CAP_STEEL = addItem(8, "micro_bubble_cap.steel");
        MICRO_BUBBLE_CAP_GALVANIZED_STEEL = addItem(9, "micro_bubble_cap.galvanized_steel");
        MICRO_BUBBLE_CAP_STAINLESS_STEEL = addItem(10, "micro_bubble_cap.stainless_steel");

        NANO_BUBBLE_CAP_STEEL = addItem(11, "nano_bubble_cap.steel");
        NANO_BUBBLE_CAP_GALVANIZED_STEEL = addItem(12, "nano_bubble_cap.galvanized_steel");
        NANO_BUBBLE_CAP_STAINLESS_STEEL = addItem(13, "nano_bubble_cap.stainless_steel");

        //Distillation trays
        DISTILLATION_TRAY_STEEL = addItem(14, "distillation_tray.steel").addComponents(new TraysStats(Steel, 10, 100));
        DISTILLATION_TRAY_GALVANIZED_STEEL = addItem(15, "distillation_tray.galvanized_steel").addComponents(new TraysStats(GalvanizedSteel, 8, 100));
        DISTILLATION_TRAY_STAINLESS_STEEL = addItem(16, "distillation_tray.stainless_steel").addComponents(new TraysStats(StainlessSteel, 6, 100));

        THIN_DISTILLATION_TRAY_STEEL = addItem(17, "thin_distillation_tray.steel").addComponents(new TraysStats(Steel, 5, 100));
        THIN_DISTILLATION_TRAY_GALVANIZED_STEEL = addItem(18, "thin_distillation_tray.galvanized_steel").addComponents(new TraysStats(GalvanizedSteel, 8, 100));
        THIN_DISTILLATION_TRAY_STAINLESS_STEEL = addItem(19, "thin_distillation_tray.stainless_steel").addComponents(new TraysStats(StainlessSteel, 6, 100));

        ADVANCED_DISTILLATION_TRAY_STEEL = addItem(20, "advanced_distillation_tray.steel").addComponents(new TraysStats(Steel, 5, 150));
        ADVANCED_DISTILLATION_TRAY_GALVANIZED_STEEL = addItem(21, "advanced_distillation_tray.galvanized_steel").addComponents(new TraysStats(GalvanizedSteel, 8, 150));
        ADVANCED_DISTILLATION_TRAY_STAINLESS_STEEL = addItem(22, "advanced_distillation_tray.stainless_steel").addComponents(new TraysStats(StainlessSteel, 6, 150));

        DENSE_DISTILLATION_TRAY_STEEL = addItem(23, "dense_distillation_tray.steel").addComponents(new TraysStats(Steel, 5, 300));
        DENSE_DISTILLATION_TRAY_GALVANIZED_STEEL = addItem(24, "dense_distillation_tray.galvanized_steel").addComponents(new TraysStats(GalvanizedSteel, 8, 300));
        DENSE_DISTILLATION_TRAY_STAINLESS_STEEL = addItem(25, "dense_distillation_tray.stainless_steel").addComponents(new TraysStats(StainlessSteel, 6, 300));

    }
}
