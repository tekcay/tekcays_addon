package tekcays_addon.api.utils.roasting;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;

import static gregtech.api.unification.material.Materials.Sulfur;

public class RoastingRecipeHandlerMethods {

    /**
     *
     * @param material
     * @param prefix the {@code OrePrefix} you want your {@code ItemStack} to be made of.
     * @return a {@code Collection<ItemStack>} which can be used directly as an {@code inputs}
     * or an {@code outputs} in a {@code RecipeBuilder}.
     */
    public static Collection<ItemStack> getOutputStack(Material material, OrePrefix prefix) {
        Collection<ItemStack> output = new ArrayList<>();
        for (MaterialStack ms : material.getMaterialComponents()) {
            output.add(OreDictUnifier.get(prefix, ms.material, (int) (ms.amount)));
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

    public static int getAmountSulfur(Material material) {
        for (MaterialStack ms : material.getMaterialComponents()) {
            if (ms.material.equals(Sulfur)) return (int) ms.amount;
        }
        return 0;
    }

}
