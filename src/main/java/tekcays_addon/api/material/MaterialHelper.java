package tekcays_addon.api.material;

import static gregtech.api.unification.ore.OrePrefix.dust;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.stack.MaterialStack;

public class MaterialHelper {

    public static Collection<Material> getAllMaterials() {
        return GregTechAPI.materialManager.getRegisteredMaterials();
    }

    public static List<ItemStack> getStacksFromMaterialComposition(@NotNull Material material) {
        return material.getMaterialComponents().stream()
                .map(materialStack -> OreDictUnifier.get(dust, materialStack.material, (int) materialStack.amount))
                .collect(Collectors.toList());
    }

    public static int getAmountComponentsSum(@NotNull Material material) {
        return Math.toIntExact((con.apply(material)));
    }

    public static int getInputAmountFromSubComposition(@NotNull Material material) {
        Material subMaterial = material.getMaterialComponents().get(0).material;
        return getAmountComponentsSum(subMaterial);
    }

    private static final Function<Material, Long> con = material -> material
            .getMaterialComponents()
            .stream()
            .mapToLong(materialStack -> materialStack.amount)
            .sum();

    public static boolean isAcidProof(@NotNull Material material) {
        return material.hasProperty(PropertyKey.FLUID_PIPE) &&
                material.getProperty(PropertyKey.FLUID_PIPE).isAcidProof();
    }

    /**
     * Totally unrealistic, but hey it's minecraft
     */
    private static int getFluidTemperature(@NotNull MaterialStack materialStack) {
        return materialStack.material.hasFluid() ?
                (int) (materialStack.material.getFluid().getTemperature() * materialStack.amount) : 0;
    }

    /**
     * Sets the melting point/fluid temperature via its chemical composition of a material.
     * <br>
     * Won't do anything if the material does not have a fluid.
     */
    public static void setWeightMp(@NotNull Material material) {
        if (!material.hasFluid()) return;
        material.getFluid().setTemperature(material.getMaterialComponents()
                .stream()
                .mapToInt(MaterialHelper::getFluidTemperature)
                .sum() / getAmountComponentsSum(material));
    }
}
