package tekcays_addon.api.mixtures;

import static tekcays_addon.api.material.MaterialHelper.getMaterialStacksFromString;
import static tekcays_addon.gtapi.unification.TKCYAMaterials.MixtureToFilter;
import static tekcays_addon.gtapi.utils.MiscMethods.getItemStacksFromMaterialStacks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandlerModifiable;

import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import tekcays_addon.common.items.TKCYAMetaItems;

public class MixtureUtil {

    public static final List<FluidStack> MIXTURE_TO_FILTER = new ArrayList<>();
    public static final List<ItemStack> DUST_MIXTURE_WITH_NBT = new ArrayList<>();

    public static List<ItemStack> setOutputStackPerSec(IItemHandlerModifiable inputInventory, int inputSlot,
                                                       OrePrefix prefix) {
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

    public static ItemStack getDustMixtureStackWithNBT(List<MaterialStack> materials) {
        NBTTagCompound nbt = writeNBTtoDustMixture(materials);
        ItemStack outputStack = TKCYAMetaItems.DUST_MIXTURE.getStackForm();
        outputStack.setTagCompound(nbt);
        DUST_MIXTURE_WITH_NBT.add(outputStack);
        return outputStack;
    }

    /**
     *
     *
     *
     * @param output
     * @param fluidOutputs
     * @return FluidStack output = getMixtureToFilterStack(new MaterialStack(Gold, 4),
     *         new MaterialStack(HydrochloricAcid, 4000));
     */
    public static FluidStack getMixtureToFilterStack(MaterialStack output, MaterialStack fluidOutputs) {
        FluidStack fs = new FluidStack(MixtureToFilter.getFluid(), 1000,
                writeNBTtoMixtureToFilter(output, fluidOutputs));
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
