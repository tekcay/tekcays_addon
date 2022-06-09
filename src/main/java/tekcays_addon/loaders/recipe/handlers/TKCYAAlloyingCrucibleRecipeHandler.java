package tekcays_addon.loaders.recipe.handlers;


import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialFlag;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.stack.MaterialStack;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.recipes.TKCYARecipeMaps;

import static gregtech.api.GTValues.L;
import static gregtech.api.unification.material.Materials.Carbon;
import static gregtech.api.unification.material.Materials.EXT2_METAL;
import static gregtech.api.unification.material.info.MaterialFlags.DISABLE_DECOMPOSITION;

import java.util.ArrayList;
import java.util.Collection;

public class TKCYAAlloyingCrucibleRecipeHandler {

    public static void init() {

        for (Material material : GregTechAPI.MATERIAL_REGISTRY) {
            if (material.getMaterialComponents().size() < 2) continue;
            if (!material.hasProperty(PropertyKey.FLUID)) continue;
            if (material.hasFlags(DISABLE_DECOMPOSITION)) continue; // To remove polymers & chemicals
            if (material.getFluid().getTemperature() <= 300) continue;
            register(material);
        }
    }



    public static void register(Material material) {

        Collection<FluidStack> f = new ArrayList<>();

        for (MaterialStack ms : material.getMaterialComponents()) {
            if (!ms.material.hasProperty(PropertyKey.FLUID)) break;
            if (ms.material.getFluid().isGaseous()) break; // Will make special recipes for those
            f.add(ms.material.getFluid((int) ms.amount * L));
        }

        if (f.size() != material.getMaterialComponents().size()) return; //Means that some materials were removed

        TKCYARecipeMaps.ALLOYING_CRUCIBLE_RECIPES.recipeBuilder()
                .fluidInputs(f)
                .fluidOutputs(material.getFluid(L))
                .duration((int) material.getMass())
                .buildAndRegister();

    }
}
