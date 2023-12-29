package tekcays_addon.loaders;

import static gregtech.api.unification.material.Materials.*;

import net.minecraft.item.ItemStack;

import tekcays_addon.common.items.TKCYAMetaItems;
import tekcays_addon.common.items.behaviors.ElectrodeBehavior;
import tekcays_addon.common.items.behaviors.FilterBehavior;

public class DamageableItemsLoader {

    // Electrodes
    public static ItemStack electrodeCarbon = TKCYAMetaItems.ELECTRODE.getStackForm();
    public static ItemStack electrodeSteel = TKCYAMetaItems.ELECTRODE.getStackForm();
    public static ItemStack electrodePlatinum = TKCYAMetaItems.ELECTRODE.getStackForm();
    public static ItemStack electrodeZinc = TKCYAMetaItems.ELECTRODE.getStackForm();
    public static ItemStack electrodeAluminium = TKCYAMetaItems.ELECTRODE.getStackForm();
    public static ItemStack electrodeGold = TKCYAMetaItems.ELECTRODE.getStackForm();
    public static ItemStack electrodeSilver = TKCYAMetaItems.ELECTRODE.getStackForm();

    // Filters
    public static ItemStack filterSteel = TKCYAMetaItems.FILTER.getStackForm();
    public static ItemStack filterStainlessSteel = TKCYAMetaItems.FILTER.getStackForm();

    public static void initElectrodes() {
        ElectrodeBehavior.getInstanceFor(electrodeCarbon).setPartMaterial(electrodeCarbon, Carbon);
        ElectrodeBehavior.getInstanceFor(electrodeSteel).setPartMaterial(electrodeSteel, Steel);
        ElectrodeBehavior.getInstanceFor(electrodePlatinum).setPartMaterial(electrodePlatinum, Platinum);
        ElectrodeBehavior.getInstanceFor(electrodeZinc).setPartMaterial(electrodeZinc, Zinc);
        ElectrodeBehavior.getInstanceFor(electrodeAluminium).setPartMaterial(electrodeAluminium, Aluminium);
        ElectrodeBehavior.getInstanceFor(electrodeGold).setPartMaterial(electrodeGold, Gold);
        ElectrodeBehavior.getInstanceFor(electrodeSilver).setPartMaterial(electrodeSilver, Silver);
        //
    }

    public static void initFilters() {
        FilterBehavior.getInstanceFor(filterSteel).setPartMaterial(filterSteel, Steel);
        FilterBehavior.getInstanceFor(filterStainlessSteel).setPartMaterial(filterStainlessSteel, StainlessSteel);
    }
}
