package tekcays_addon.api.material;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.core.unification.material.internal.MaterialRegistryManager;
import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static gregtech.api.unification.ore.OrePrefix.dust;

public class MaterialHelper {

    public static Collection<Material> getAllMaterials() {
        return MaterialRegistryManager.getInstance().getRegisteredMaterials();
    }

    public static List<ItemStack> getOutput(Material material) {
        return material.getMaterialComponents().stream()
                .map(materialStack -> OreDictUnifier.get(dust, materialStack.material, (int) materialStack.amount))
                .collect(Collectors.toList());
    }

    public static int getInputAmountFromComposition(Material material) {
        return (int) (material.getMaterialComponents()
                .stream()
                .mapToLong(materialStack -> materialStack.amount)
                .sum());
    }

}
