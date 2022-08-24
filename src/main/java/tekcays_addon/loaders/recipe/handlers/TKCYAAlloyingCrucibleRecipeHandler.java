package tekcays_addon.loaders.recipe.handlers;


import gregtech.api.GregTechAPI;
import gregtech.api.recipes.ingredients.GTRecipeFluidInput;
import gregtech.api.recipes.ingredients.GTRecipeInput;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.stack.MaterialStack;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.api.unification.TKCYAMaterials;
import tekcays_addon.api.utils.TKCYALog;

import static gregtech.api.GTValues.L;
import static gregtech.api.unification.material.Materials.Carbon;
import static gregtech.api.unification.material.info.MaterialFlags.DISABLE_DECOMPOSITION;

import java.util.ArrayList;
import java.util.Collection;

public class TKCYAAlloyingCrucibleRecipeHandler {

    public static void init() {

        for (Material material : GregTechAPI.MATERIAL_REGISTRY) {
            if (!material.hasProperty(PropertyKey.FLUID)) continue;
            if (material.getFluid().getTemperature() <= 300) continue;
            register(material);
        }
    }



    public static void register(Material material) {

        Collection<GTRecipeInput> f = new ArrayList<>();
        boolean containsCarbon = false;
        int outputMultiplier = 0;

        for (MaterialStack ms : material.getMaterialComponents()) {
            if (!ms.material.hasProperty(PropertyKey.FLUID)) continue;
            if (ms.material.getFluid().isGaseous()) continue; // Will make special recipes for those
            if (ms.material.getFluid().getTemperature() < 300) continue; //To remove polymers, chemicals...
            outputMultiplier += ms.amount;
            if (ms.material == Carbon) {
                containsCarbon = true;
            } else {
                f.add(GTRecipeFluidInput.getOrCreate(ms.material.getFluid(), ((int) ms.amount * L)));
            }
        }

        if (f.size() < 2) return; //To remove element materials
        if (f.size() != material.getMaterialComponents().size() && !containsCarbon) return; //Means that some materials were removed

        if (!containsCarbon) {
            TKCYARecipeMaps.ALLOYING_CRUCIBLE_RECIPES.recipeBuilder()
                    .fluidInputs(f)
                    .fluidOutputs(material.getFluid(L * outputMultiplier))
                    .duration((int) material.getMass())
                    .buildAndRegister();
        }
    }
}
