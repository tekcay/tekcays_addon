package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;
import net.minecraft.item.ItemStack;
import tekcays_addon.api.recipes.TKCYARecipeMaps;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static gregtech.api.GTValues.L;
import static gregtech.api.GTValues.M;

public class TKCYAMeltingRecipeHandler {

    public static void init() {
        for (Map.Entry<ItemStack, ItemMaterialInfo> entry : OreDictUnifier.getAllItemInfos()) {
            ItemStack itemStack = entry.getKey();
            ItemMaterialInfo materialInfo = entry.getValue();
            ArrayList<MaterialStack> materialStacks = new ArrayList<>(materialInfo.getMaterials());
            registerMeltingRecipes(itemStack, materialStacks, null);
        }
    }

    public static void registerMeltingRecipes(ItemStack input, List<MaterialStack> components, @Nullable OrePrefix prefix) {

        // Gather the valid Materials for use in recycling recipes.
        // - Filter out Materials that cannot create a Dust
        // - Filter out Materials that do not equate to at least 1 Nugget worth of Material.
        // - Sort Materials on a descending material amount
        List<MaterialStack> materials = components.stream()
                .filter(stack -> stack.material.hasProperty(PropertyKey.DUST))
                .filter(stack -> stack.amount >= M / 9)
                .sorted(Comparator.comparingLong(ms -> -ms.amount))
                .collect(Collectors.toList());

        // Exit if no Materials matching the above requirements exist.
        if (materials.isEmpty()) return;

        if (prefix != null) {
            registerMelterRecipes(input, components, prefix);
        }
    }



    private static void registerMelterRecipes(ItemStack input, List<MaterialStack> materials, @Nullable OrePrefix prefix) {

        // Handle simple materials separately
        if (prefix != null && prefix.secondaryMaterials.isEmpty()) {
            MaterialStack ms = OreDictUnifier.getMaterial(input);
            if (ms == null || ms.material == null) {
                return;
            }
            Material m = ms.material;
            if (m.hasProperty(PropertyKey.INGOT) && m.getProperty(PropertyKey.INGOT).getMacerateInto() != m) {
                m = m.getProperty(PropertyKey.INGOT).getMacerateInto();
            }
            if (!m.hasProperty(PropertyKey.FLUID)) return;

            TKCYARecipeMaps.PRIMITIVE_MELTER_RECIPES.recipeBuilder()
                    .inputs(input.copy())
                    .fluidOutputs(m.getFluid((int) (ms.amount * L / M)))
                    .duration((int) Math.max(1, ms.amount * ms.material.getMass() / M))
                    .buildAndRegister();

            TKCYARecipeMaps.ELECTRIC_MELTER_RECIPES.recipeBuilder()
                    .setTemp(m.getFluid().getTemperature())
                    .inputs(input.copy())
                    .fluidOutputs(m.getFluid((int) (ms.amount * L / M)))
                    .duration((int) Math.max(1, ms.amount * ms.material.getMass() / M))
                    .buildAndRegister();

            return;
        }

        // Find the first Material which can create a Fluid.
        // If no Material in the list can create a Fluid, return.
        MaterialStack fluidMs = materials.stream().filter(ms -> ms.material.hasProperty(PropertyKey.FLUID)).findFirst().orElse(null);
        if (fluidMs == null) return;

        // Find the next MaterialStack, which will be the Item output.
        // This can sometimes be before the Fluid output in the list, so we have to
        // assume it can be anywhere in the list.
        MaterialStack itemMs = materials.stream().filter(ms -> !ms.material.equals(fluidMs.material)).findFirst().orElse(null);

        // Calculate the duration based off of those two possible outputs.
        // - Sum the two Material amounts together (if both exist)
        // - Divide the sum by M
        long duration = fluidMs.amount * fluidMs.material.getMass();
        if (itemMs != null) duration += (itemMs.amount * itemMs.material.getMass());
        duration = Math.max(1L, duration / M);

        // Build the final Recipe.
        RecipeBuilder<?> primitiveMelterBuilder = TKCYARecipeMaps.PRIMITIVE_MELTER_RECIPES.recipeBuilder()
                .inputs(input.copy())
                .fluidOutputs(fluidMs.material.getFluid((int) (fluidMs.amount * L / M)))
                .duration((int) duration);

        RecipeBuilder<?> electricMelterRecipeBuilder = TKCYARecipeMaps.ELECTRIC_MELTER_RECIPES.recipeBuilder()
                .setTemp(fluidMs.material.getFluid().getTemperature())
                .inputs(input.copy())
                .fluidOutputs(fluidMs.material.getFluid((int) (fluidMs.amount * L / M)))
                .duration((int) duration);

        // Null check the Item before adding it to the Builder.
        // - Try to output an Ingot, otherwise output a Dust.
        if (itemMs != null) {
            OrePrefix outputPrefix = itemMs.material.hasProperty(PropertyKey.INGOT) ? OrePrefix.ingot : OrePrefix.dust;
            primitiveMelterBuilder.output(outputPrefix, itemMs.material, (int) (itemMs.amount / M));
            electricMelterRecipeBuilder.output(outputPrefix, itemMs.material, (int) (itemMs.amount / M));
        }

        primitiveMelterBuilder.buildAndRegister();
        electricMelterRecipeBuilder.buildAndRegister();
    }

}
