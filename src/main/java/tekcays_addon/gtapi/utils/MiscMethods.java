package tekcays_addon.gtapi.utils;

import gregtech.api.GregTechAPI;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTTransferUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import tekcays_addon.gtapi.unification.TKCYAMaterials;
import tekcays_addon.common.items.TKCYAMetaItems;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


import static gregtech.api.unification.material.Materials.Air;
import static tekcays_addon.gtapi.unification.TKCYAMaterials.MixtureToFilter;
import static tekcays_addon.gtapi.consts.TKCYAValues.*;

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
    
    /**
     *
     * @param m1
     * @param m2
     * @return the amount of {@code Material} m2 in {@code Material} m1.
     */
    public static int getAmountMaterial(Material m1, Material m2) {
        for (MaterialStack ms : m1.getMaterialComponents()) {
            if (ms.material.equals(m2)) return (int) ms.amount;
        }
        return 0;
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
     * @param list {@code List<MaterialStack>}
     * @return the sum of the amount of each {@code MaterialStack}.
     */
    public static int getMaterialStackListSize(List<MaterialStack> list) {
        AtomicInteger stackSize = new AtomicInteger();
        list.forEach(ms -> stackSize.addAndGet((int) ms.amount));
        return stackSize.get();
    }

    /**
     *
     * @param list {@code List<FluidStack>}
     * @return the sum of the amount of each {@code FluidStack}.
     */
    public static int getFluidStackListSize(List<FluidStack> list) {
        AtomicInteger stackSize = new AtomicInteger();
        list.forEach(stack -> stackSize.addAndGet(stack.amount));
        return stackSize.get();
    }

    /**
     *
     * @param list {@code List<ItemStack>}
     * @return the sum of the amount of each {@code ItemStack}.
     */
    public static int getItemStackListSize(List<ItemStack> list) {
        AtomicInteger stackSize = new AtomicInteger();
        list.forEach(stack -> stackSize.addAndGet((int) stack.getCount()));
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
        list.forEach(ms -> output.add(OreDictUnifier.get(prefix, ms.material, (int) (ms.amount))));
        return output;
    }

    public static List<FluidStack> getFluidStacksFromMaterialStacks(List<MaterialStack> list) {
        List<FluidStack> output = new ArrayList<>();
        list.forEach(ms -> output.add(ms.material.getFluid((int) ms.amount)));
        return output;
    }

    public static ItemStack getOutputItemStackFromNBT(OrePrefix prefix, NBTTagCompound nbt) {
        List<MaterialStack> list = getMaterialStacksFromString(nbt.getString("output"));
        return getItemStacksFromMaterialStacks(list, prefix).get(0);
    }

    public static FluidStack getOutputFluidStackFromNBT(NBTTagCompound nbt) {
        List<MaterialStack> list = getMaterialStacksFromString(nbt.getString("fluidOutputs"));
        return getFluidStacksFromMaterialStacks(list).get(0);
    }

    public static FluidStack getMixtureToFilterStack(MaterialStack output, MaterialStack fluidOutputs) {
        FluidStack fs = new FluidStack(MixtureToFilter.getFluid(), 1000, writeNBTtoMixtureToFilter(output, fluidOutputs));
        MIXTURE_TO_FILTER.add(fs);
        return fs;
    }

    public static NBTTagCompound writeNBTtoMixtureToFilter(MaterialStack output, MaterialStack fluidOutputs) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("output", output.material.getUnlocalizedName() + "," + output.amount);
        nbt.setString("fluidOutputs", fluidOutputs.material.getUnlocalizedName() + "," + fluidOutputs.amount);
        return nbt;
    }

    /**
     *
     * @param list the {@code List<MaterialStack}s
     * @param prefix the {@code OrePrefix} to apply to get the output.
     * @param applyMaterialAmount
     * @return a {@code List<ItemStack}
     */
    public static List<ItemStack> getItemStacksFromMaterialStacks(List<MaterialStack> list, OrePrefix prefix, boolean applyMaterialAmount) {
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


    /**
     *
     * @param list the {@code List<ItemStack>}
     * @param divider
     * @return the desired {@code List<ItemStack>}.
     * <pre>
     * If the division is not possible, returns the former {@code List<ItemStack>}.
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

    public static List<ItemStack> setOutputStackPerSec(IItemHandlerModifiable inputInventory, int inputSlot, OrePrefix prefix) {
        ItemStack input = inputInventory.getStackInSlot(inputSlot);
        NBTTagCompound nbt = input.getTagCompound();
        List<MaterialStack> list = getMaterialStacksFromString(nbt.getString("Composition"));
        return getItemStacksFromMaterialStacks(list, prefix);
    }


    public static List<ItemStack> setOutputStackPerSec(String composition, OrePrefix prefix) {
        List<MaterialStack> list = getMaterialStacksFromString(composition);
        return getItemStacksFromMaterialStacks(list, prefix);
    }

    public static List<ItemStack> setOutputStack(String composition, OrePrefix prefix) {
        List<MaterialStack> list = getMaterialStacksFromString(composition);
        return getItemStacksFromMaterialStacks(list, prefix);
    }

    public static List<ItemStack> setOutputStack(String composition, OrePrefix prefix, boolean applyMaterialAmount) {
        List<MaterialStack> list = getMaterialStacksFromString(composition);
        return getItemStacksFromMaterialStacks(list, prefix, applyMaterialAmount);
    }

    public static String getComposition(IItemHandlerModifiable inputInventory, int inputSlot) {
        ItemStack input = inputInventory.getStackInSlot(inputSlot);
        NBTTagCompound nbt = input.getTagCompound();
        return nbt.getString("Composition");
    }



    /**
     * Check if a multiblock can process by checking if there is enough energy and room in the output.
     * @param energyContainer
     * @param requiredVoltage
     * @param energyCost
     * @param outputStackPerSec
     * @param outputInventory
     * @return true.
     */
    public static boolean canDoWork(IEnergyContainer energyContainer, long requiredVoltage, int energyCost, List<ItemStack> outputStackPerSec, IItemHandlerModifiable outputInventory) {
        if (energyContainer.getInputVoltage() < requiredVoltage) return false;
        if (!hasEnoughEnergy(energyCost, energyContainer)) return false;
        return GTTransferUtils.addItemsToItemHandler(outputInventory, true, outputStackPerSec);
        //return canOutputItem(outputStackPerSec, outputInventory);
    }

    public static ItemStack getDustMixtureStackWithNBT(List<MaterialStack> materials) {
        NBTTagCompound nbt = writeNBTtoDustMixture(materials);
        ItemStack outputStack = TKCYAMetaItems.DUST_MIXTURE.getStackForm();
        outputStack.setTagCompound(nbt);
        DUST_MIXTURE_WITH_NBT.add(outputStack);
        return outputStack;
    }

}
