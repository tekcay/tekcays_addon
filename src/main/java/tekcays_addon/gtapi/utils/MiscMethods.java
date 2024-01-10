package tekcays_addon.gtapi.utils;

import static gregtech.api.unification.material.Materials.Air;
import static tekcays_addon.api.material.MaterialHelper.getMaterialStacksFromString;
import static tekcays_addon.gtapi.consts.TKCYAValues.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.IItemHandlerModifiable;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.util.GTTransferUtils;

public class MiscMethods {

    public static Map<Integer, Integer> getPumpPressureMap() {
        Map<Integer, Integer> map = new HashMap<>();
        int tier = 0;

        for (MetaItem.MetaValueItem pump : ELECTRIC_PUMPS) {
            tier++;
            map.put(tier * 10, tier);
        }
        return map;
    }

    /**
     *
     * @param list {@code List<ItemStack>}
     * @return the sum of the amount of each {@code ItemStack}.
     */
    public static int getItemStackListSize(List<ItemStack> list) {
        AtomicInteger stackSize = new AtomicInteger();
        list.forEach(stack -> stackSize.addAndGet(stack.getCount()));
        return stackSize.get();
    }

    /**
     *
     * @param list   the {@code List<MaterialStack}s
     * @param prefix the {@code OrePrefix} to apply to get the output.
     * @return a {@code List<ItemStack}
     */
    public static List<ItemStack> getItemStacksFromMaterialStacks(List<MaterialStack> list, OrePrefix prefix) {
        List<ItemStack> output = new ArrayList<>();
        list.forEach(ms -> output.add(OreDictUnifier.get(prefix, ms.material, (int) (ms.amount))));
        return output;
    }

    public static ItemStack getOutputItemStackFromNBT(OrePrefix prefix, NBTTagCompound nbt) {
        List<MaterialStack> list = getMaterialStacksFromString(nbt.getString("output"));
        return getItemStacksFromMaterialStacks(list, prefix).get(0);
    }

    /**
     *
     * @param list                the {@code List<MaterialStack}s
     * @param prefix              the {@code OrePrefix} to apply to get the output.
     * @param applyMaterialAmount
     * @return a {@code List<ItemStack}
     */
    public static List<ItemStack> getItemStacksFromMaterialStacks(List<MaterialStack> list, OrePrefix prefix,
                                                                  boolean applyMaterialAmount) {
        List<ItemStack> output = new ArrayList<>();
        if (applyMaterialAmount) {
            long materialAmount = OrePrefix.dust.getMaterialAmount(Air) / prefix.getMaterialAmount(Air);
            list.forEach(ms -> output.add(OreDictUnifier.get(prefix, ms.material, (int) (ms.amount * materialAmount))));
        } else {
            list.forEach(ms -> output.add(OreDictUnifier.get(prefix, ms.material, (int) (ms.amount))));
        }
        return output;
    }

    /**
     *
     * @param stack
     * @param inputInventory
     * @return -1 if not found.
     */
    public static int hasAcceptedItemStackInSlot(ItemStack stack, IItemHandlerModifiable inputInventory) {
        for (int i = 0; i < inputInventory.getSlots(); i++) {
            ItemStack input = inputInventory.getStackInSlot(i);
            if (input.isItemEqual(stack) && input.hasTagCompound()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Multiplies energyConsumption by 20 as the number of ticks to actualize the multi only once per second.
     */
    public static boolean hasEnoughEnergy(int energyConsumption, IEnergyContainer energyContainer) {
        return energyContainer.getEnergyStored() >= (long) energyConsumption * 20;
    }

    /**
     *
     * @param list    the {@code List<ItemStack>}
     * @param divider
     * @return the desired {@code List<ItemStack>}.
     * 
     *         <pre>
     *         If the division is not possible, returns the former {@code List<ItemStack>}.
     */
    public static List<ItemStack> divideOutputStack(List<ItemStack> list, int divider) {
        List<ItemStack> dividedOutputStack = new ArrayList<>();
        boolean success = true;

        for (ItemStack stack : list) {
            if (stack.getCount() % divider != 0) return list;
            dividedOutputStack.add(new ItemStack(stack.getItem(), stack.getCount() / divider));
        }
        return dividedOutputStack;
    }

    /**
     * Check if a multiblock can process by checking if there is enough energy and room in the output.
     * 
     * @param energyContainer
     * @param requiredVoltage
     * @param energyCost
     * @param outputStackPerSec
     * @param outputInventory
     * @return true.
     */
    public static boolean canDoWork(IEnergyContainer energyContainer, long requiredVoltage, int energyCost,
                                    List<ItemStack> outputStackPerSec, IItemHandlerModifiable outputInventory) {
        if (energyContainer.getInputVoltage() < requiredVoltage) return false;
        if (!hasEnoughEnergy(energyCost, energyContainer)) return false;
        return GTTransferUtils.addItemsToItemHandler(outputInventory, true, outputStackPerSec);
        // return canOutputItem(outputStackPerSec, outputInventory);
    }
}
