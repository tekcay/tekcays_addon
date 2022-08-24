package tekcays_addon.loaders;

import net.minecraft.item.ItemStack;
import tekcays_addon.common.items.TKCYAMetaItems;
import tekcays_addon.common.items.behaviors.ElectrodeBehavior;
import tekcays_addon.common.items.behaviors.FilterBehavior;

import static gregtech.api.unification.material.Materials.*;

public class DamageableItemsLoader {

    //Electrodes
    public static ItemStack electrodeCarbon = TKCYAMetaItems.ELECTRODE.getStackForm();
    public static ItemStack electrodeSteel = TKCYAMetaItems.ELECTRODE.getStackForm();
    public static ItemStack electrodePlatinum = TKCYAMetaItems.ELECTRODE.getStackForm();
    public static ItemStack electrodeZinc = TKCYAMetaItems.ELECTRODE.getStackForm();

    //Filters
    public static ItemStack filterSteel = TKCYAMetaItems.FILTER.getStackForm();
    public static ItemStack filterStainlessSteel = TKCYAMetaItems.FILTER.getStackForm();


    public static void initElectrodes() {

        ElectrodeBehavior.getInstanceFor(electrodeCarbon).setPartMaterial(electrodeCarbon, Carbon);
        ElectrodeBehavior.getInstanceFor(electrodeSteel).setPartMaterial(electrodeSteel, Steel);
        ElectrodeBehavior.getInstanceFor(electrodePlatinum).setPartMaterial(electrodePlatinum, Platinum);
        ElectrodeBehavior.getInstanceFor(electrodeZinc).setPartMaterial(electrodeZinc, Zinc);
    }

    public static void initFilters() {

        FilterBehavior.getInstanceFor(filterSteel).setPartMaterial(filterSteel, Steel);
        FilterBehavior.getInstanceFor(filterStainlessSteel).setPartMaterial(filterStainlessSteel, StainlessSteel);

    }
}
