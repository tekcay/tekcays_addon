package tekcays_addon.api.material;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.core.unification.material.internal.MaterialRegistryManager;
import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static gregtech.api.unification.ore.OrePrefix.dust;

public class MaterialHelper {

    public static Collection<Material> getAllMaterials() {
        return MaterialRegistryManager.getInstance().getRegisteredMaterials();
    }

    public static List<ItemStack> getStacksFromMaterialComposition(Material material) {
        return material.getMaterialComponents().stream()
                .map(materialStack -> OreDictUnifier.get(dust, materialStack.material, (int) materialStack.amount))
                .collect(Collectors.toList());
    }

    public static int getAmountComponentsSum(Material material) {
        return Math.toIntExact((con.apply(material)));
    }

    public static int getInputAmountFromSubComposition(Material material) {
        Material subMaterial = material.getMaterialComponents().get(0).material;
        return getAmountComponentsSum(subMaterial);
    }

    private static final Function<Material, Long> con = material -> material
            .getMaterialComponents()
            .stream()
            .mapToLong(materialStack -> materialStack.amount)
            .sum();




}