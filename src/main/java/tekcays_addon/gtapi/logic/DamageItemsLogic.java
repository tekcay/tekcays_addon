package tekcays_addon.gtapi.logic;

import java.util.HashSet;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

import gregtech.api.recipes.ingredients.GTRecipeInput;
import tekcays_addon.common.items.TKCYAMetaItems;
import tekcays_addon.common.items.behaviors.ElectrodeBehavior;

public class DamageItemsLogic {

    public static void getCurrentRecipeNonConsummables(HashSet<String> currentRecipeNonConsummIngredient,
                                                       List<GTRecipeInput> inputs) {
        currentRecipeNonConsummIngredient.clear();
        for (GTRecipeInput gtRecipeInput : inputs) {

            if (!gtRecipeInput.isNonConsumable()) continue;
            ItemStack[] stack = gtRecipeInput.getInputStacks();

            currentRecipeNonConsummIngredient.add(stack[0].getDisplayName());

            // if (!stack.isItemStack ..Damageable()) continue;
            // currentRecipeNonConsummIngredient.add(stack);
        }
    }

    /**
     * For MTE that only has one Item slot.
     * 
     * @param currentRecipeNonConsummIngredient
     * @param inputs
     */
    public static void getCurrentRecipeNonConsummables(HashSet<String> currentRecipeNonConsummIngredient,
                                                       ItemStack inputs) {
        currentRecipeNonConsummIngredient.clear();
        if (!inputs.isItemStackDamageable()) return;
        currentRecipeNonConsummIngredient.add(inputs.getDisplayName());
    }

    public static void getCurrentInventory(HashSet<String> nonConsummInInventory,
                                           IItemHandlerModifiable inputInventory) {
        nonConsummInInventory.clear();
        for (int i = 0; i < inputInventory.getSlots(); i++) {
            ItemStack stack = inputInventory.getStackInSlot(i);
            if (stack == null || stack.getDisplayName().startsWith("Air")) continue;
            nonConsummInInventory.add(stack.getDisplayName());
        }
    }

    public static void applyDamage(IItemHandlerModifiable inputInventory, int damageAmount) {
        for (int i = 0; i < inputInventory.getSlots(); i++) {

            if (inputInventory.isItemValid(i, TKCYAMetaItems.ELECTRODE.getStackForm())) {
                ItemStack electrodeStack = inputInventory.getStackInSlot(i);

                ElectrodeBehavior behavior = ElectrodeBehavior.getInstanceFor(electrodeStack);
                if (behavior == null) continue;
                behavior.applyElectrodeDamage(electrodeStack, damageAmount);
            }
        }
    }

    public static boolean doesInventoryContainDamageableItems(HashSet<String> nonConsummInInventory,
                                                              HashSet<String> currentRecipeNonConsummIngredient) {
        return nonConsummInInventory.containsAll(currentRecipeNonConsummIngredient);
    }
}
