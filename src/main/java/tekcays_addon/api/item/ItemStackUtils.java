package tekcays_addon.api.item;

import net.minecraft.item.ItemStack;

public class ItemStackUtils {

    public static boolean tryChangeItemStack(ItemStack itemStack, int amount) {
        if (itemStack.isEmpty() || amount == 0) return false;
        if (itemStack.getCount() < amount) return false;
        itemStack.setCount(itemStack.getCount() + amount);
        return true;
    }
}
