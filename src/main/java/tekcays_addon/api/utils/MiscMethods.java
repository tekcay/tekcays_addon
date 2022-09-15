package tekcays_addon.api.utils;

import gregtech.api.GregTechAPI;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import tekcays_addon.api.unification.TKCYAMaterials;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static tekcays_addon.api.utils.TKCYAValues.ELECTRIC_PUMPS;

public class MiscMethods {

    public static boolean isFuel(@Nullable FluidStack fluid) {
        if (fluid == null) return false;
        if (fluid.isFluidEqual(new FluidStack(TKCYAMaterials.Fuel.getFluid(), 1))) return true;

        return false;
    }

    /**
     * Checks if a {@code FluidStack} is made of a certain {@code Fluid}.
     * This can avoid certain {@code NullPointer} exceptions.
     * <br /><br />
     * @param fluidStack the {@code FluidStack} that is checked.
     * @param fluid the {@code Fluid} to compare to.
     * @return true if condition is met.
     */
    public static boolean isSameFluid(FluidStack fluidStack, Fluid fluid) {
        if (fluidStack == null) return false;
        if (fluidStack.isFluidEqual(new FluidStack(fluid, 1))) return true;

        return false;
    }



    public static Map<Integer, Integer> getPumpPressureMap() {
        Map<Integer, Integer> map = new HashMap<>();
        int tier = 0;

        for (MetaItem.MetaValueItem pump : ELECTRIC_PUMPS) {
            tier++;
            map.put(tier * 10, tier);
        }
        return map;
    }

    public static Material getMaterialFromUnlocalizedName(String unlocalizedName) {
        for (Material m : GregTechAPI.MATERIAL_REGISTRY) {
            if (m.getUnlocalizedName().equals(unlocalizedName)) return m;
        }
        return null;
    }


    public static List<MaterialStack> getMaterialStacksFromString(String formula) {

        List<String> compounds = Arrays.asList(formula.split(","));
        List<MaterialStack> list = new ArrayList<>();

        for (int i = 0; i < compounds.size(); i += 2) {
            list.add(new MaterialStack(getMaterialFromUnlocalizedName(compounds.get(i)), Integer.parseInt(compounds.get(i + 1))));
        }

        return list;
    }

    public static NBTTagCompound writeNBTtoDustMixture(List<MaterialStack> list) {
        NBTTagCompound nbt = new NBTTagCompound();
        final StringBuilder composition = new StringBuilder();

        list.forEach(ms -> composition.append(ms.material.getUnlocalizedName())
                                      .append(",")
                                      .append(ms.amount)
                                      .append(","));

        nbt.setString("Composition", composition.toString());
        return nbt;
    }

    /**
     *
     * @param list
     * @return the sum of the amount of each {@code MaterialStack}.
     */
    public static int getMaterialStackSize(List<MaterialStack> list) {
        AtomicInteger stackSize = new AtomicInteger();
        list.forEach(ms -> stackSize.addAndGet((int) ms.amount));
        return stackSize.get();
    }


    /**
     *
     * @param list the {@code List<MaterialStack}s
     * @param prefix the {@code OrePrefix} to apply to get the output.
     * @return a {@code List<ItemStack}
     */
    public static List<ItemStack> getItemStacksFromMaterialStacks(List<MaterialStack> list, OrePrefix prefix) {
        List<ItemStack> output = new ArrayList<>();
        list.forEach(ms -> output.add(OreDictUnifier.get(prefix, ms.material, (int) ms.amount)));
        return output;
    }

    public static boolean canOutputItem(List<ItemStack> toOutput, IItemHandlerModifiable outputInventory) {
        if (toOutput.size() > outputInventory.getSlots()) return false;

        ItemStack stackLeft = toOutput.get(0);

        for (ItemStack stack : toOutput) {
            stackLeft = stack;
            for (int i = 0; i < outputInventory.getSlots(); i++) {
                stackLeft = outputInventory.insertItem(i, stackLeft, true);
                if (stack.isItemEqual(ItemStack.EMPTY)) break;
            }
        }
        return stackLeft.isItemEqual(ItemStack.EMPTY);
    }

    public static void doOutputItem(List<ItemStack> toOutput, IItemHandlerModifiable outputInventory) {

        for (ItemStack stack : toOutput) {
            ItemStack stackLeft = stack;
            for (int i = 0; i < outputInventory.getSlots(); i++) {
                stackLeft = outputInventory.insertItem(i, stackLeft, false);
                if (stackLeft.isItemEqual(ItemStack.EMPTY)) break;
            }
        }
    }


    /**
     *
     * @param stack
     * @param inputInventory
     * @return -1 if not found.
     */
    public static int hasAcceptedItemStackInSlot(ItemStack stack, IItemHandlerModifiable inputInventory) {
        for (int i = 0;  i < inputInventory.getSlots(); i++) {
            ItemStack input = inputInventory.getStackInSlot(i);
            if (input.isItemEqual(stack) && input.hasTagCompound()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Multiplies energyConsumption by 20 as the number of ticks to actualize the multi only once per second.
     * @param energyConsumption
     * @param energyContainer
     * @return
     */
    public static boolean hasEnoughEnergy(int energyConsumption, IEnergyContainer energyContainer) {
        return energyContainer.getEnergyStored() >= (long) energyConsumption * 20;
    }



}
