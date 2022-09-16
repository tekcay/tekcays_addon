package tekcays_addon.api.utils;

import gregtech.api.GregTechAPI;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.unification.TKCYAMaterials;

import javax.annotation.Nullable;
import java.util.*;

import static tekcays_addon.api.unification.TKCYAMaterials.MixtureToFilter;
import static tekcays_addon.api.utils.TKCYAValues.ELECTRIC_PUMPS;
import static tekcays_addon.api.utils.TKCYAValues.MIXTURE_TO_FILTER;

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



}
