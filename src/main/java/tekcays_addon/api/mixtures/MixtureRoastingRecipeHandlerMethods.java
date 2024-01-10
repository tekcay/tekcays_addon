package tekcays_addon.api.mixtures;

import static gregtech.api.unification.material.Materials.Sulfur;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static tekcays_addon.api.mixtures.MixtureUtil.*;
import static tekcays_addon.common.items.TKCYAMetaItems.DUST_MIXTURE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;

public class MixtureRoastingRecipeHandlerMethods {

    /**
     *
     * @param material
     * @param prefix   the {@code OrePrefix} you want your {@code ItemStack} to be made of.
     * @return a {@code Collection<ItemStack>} which can be used directly as an {@code inputs}
     *         or an {@code outputs} in a {@code RecipeBuilder}.
     */
    public static Collection<ItemStack> getOutputStack(Material material, OrePrefix prefix) {
        Collection<ItemStack> output = new ArrayList<>();
        for (MaterialStack ms : material.getMaterialComponents()) {
            output.add(OreDictUnifier.get(prefix, ms.material, (int) (ms.amount)));
        }
        return output;
    }

    public static Collection<ItemStack> getOutputStack(Material material, OrePrefix prefix, boolean removeSulfur) {
        Collection<ItemStack> output = new ArrayList<>();
        for (MaterialStack ms : material.getMaterialComponents()) {
            if (removeSulfur && ms.material.equals(Sulfur)) continue;
            output.add(OreDictUnifier.get(prefix, ms.material, (int) (ms.amount)));
        }
        return output;
    }

    public static List<MaterialStack> getOutputMaterialStack(Material material, boolean removeSulfur) {
        List<MaterialStack> output = new ArrayList<>();
        for (MaterialStack ms : material.getMaterialComponents()) {
            if (removeSulfur && ms.material.equals(Sulfur)) continue;
            output.add(ms);
        }
        return output;
    }

    /**
     *
     * @param material
     * @return the number of elements that compose the {@code Material}.
     */
    public static int getComponentsNumber(Material material) {
        int componentsNumber = 0;
        for (MaterialStack ms : material.getMaterialComponents()) {
            componentsNumber += ms.amount;
        }
        return componentsNumber;
    }

    /**
     *
     * @param material
     * @return the amount of Sulfur atoms in the material.
     */
    public static int getAmountSulfur(Material material) {
        for (MaterialStack ms : material.getMaterialComponents()) {
            if (ms.material.equals(Sulfur)) return (int) ms.amount;
        }
        return 0;
    }

    /**
     * Mainly for the Roasting Oven materials
     * 
     * @param material
     * @return the desired {@code ItemStack} with a {@code NBTTagCompound} containing the composition,
     * 
     *         <pre>
     *         see {@link writeNBTtoDustMixture(List<MaterialStack>)}.
     * 
     *         <pre>
     *         If the input {@code Material} minus the Sulfur contains only 1 component,
     * 
     *         <pre>
     *         it will return the pure dust of this component as an {@code ItemStack}
     * 
     *         <pre>
     *         with no {@code NBTTagCompound}.
     */
    public static ItemStack getDustMixtureStackWithNBT(Material material) {
        List<MaterialStack> outputs = getOutputMaterialStack(material, true);
        if (outputs.size() == 1) {
            MaterialStack ms = outputs.get(0);
            return OreDictUnifier.get(dust, ms.material, (int) ms.amount);
        }
        NBTTagCompound nbt = writeNBTtoDustMixture(outputs);
        ItemStack outputStack = DUST_MIXTURE.getStackForm();
        outputStack.setTagCompound(nbt);
        DUST_MIXTURE_WITH_NBT.add(outputStack);
        return outputStack;
    }

    public static ItemStack getDustMixtureStackWithNBT(List<MaterialStack> materialStacks) {
        NBTTagCompound nbt = writeNBTtoDustMixture(materialStacks);
        ItemStack outputStack = DUST_MIXTURE.getStackForm();
        outputStack.setTagCompound(nbt);
        DUST_MIXTURE_WITH_NBT.add(outputStack);
        return outputStack;
    }

    /**
     *
     * @param material
     * @param outputPrefix to apply material quantity
     * @return
     */
    public static ItemStack getDustMixtureStackWithNBT(Material material, OrePrefix outputPrefix) {
        List<MaterialStack> outputs = getOutputMaterialStack(material, true);
        if (outputs.size() == 1) {
            MaterialStack ms = outputs.get(0);
            Material m = ms.material;
            return OreDictUnifier.get(dust, m,
                    (int) (ms.amount * dust.getMaterialAmount(m) / outputPrefix.getMaterialAmount(m)));
        }
        NBTTagCompound nbt = writeNBTtoDustMixture(outputs);
        ItemStack outputStack = DUST_MIXTURE.getStackForm();
        outputStack.setTagCompound(nbt);
        DUST_MIXTURE_WITH_NBT.add(outputStack);
        return outputStack;
    }
}
