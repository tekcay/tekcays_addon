package tekcays_addon.common.items.behaviors;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.metaitem.stats.IItemDurabilityManager;
import gregtech.api.items.metaitem.stats.IItemMaxStackSizeProvider;
import gregtech.api.unification.material.Material;
import gregtech.common.items.behaviors.AbstractMaterialPartBehavior;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FilterBehavior extends AbstractMaterialPartBehavior implements IItemMaxStackSizeProvider {

    //TODO rework filter stats once material stats are also reworked
    @Override
    public int getPartMaxDurability(ItemStack itemStack) {
        Material material = getPartMaterial(itemStack);
        return 1000 * (int) material.getMass();
    }

    public int getFilterDurabilityPercent(ItemStack itemStack) {
        return 100 - 100 * getPartDamage(itemStack) / getPartMaxDurability(itemStack);
    }

    public void applyFilterDamage(ItemStack itemStack, int damageApplied) {
        int filterDurability = getPartMaxDurability(itemStack);
        int resultDamage = getPartDamage(itemStack) + damageApplied;
        if (resultDamage >= filterDurability) {
            itemStack.shrink(1);
        } else {
            setPartDamage(itemStack, resultDamage);
        }
    }

    @Override
    public int getMaxStackSize(ItemStack itemStack, int defaultValue) {
        return 1;
    }

    @Nullable
    public static FilterBehavior getInstanceFor(@Nonnull ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof MetaItem))
            return null;

        MetaItem<?>.MetaValueItem valueItem = ((MetaItem<?>) itemStack.getItem()).getItem(itemStack);
        if (valueItem == null)
            return null;

        IItemDurabilityManager durabilityManager = valueItem.getDurabilityManager();
        if (!(durabilityManager instanceof FilterBehavior))
            return null;

        return (FilterBehavior) durabilityManager;
    }
}
