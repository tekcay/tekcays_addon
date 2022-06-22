package tekcays_addon.loaders.recipe.handlers;


import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.stack.MaterialStack;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.recipes.TKCYARecipeMaps;

import static gregtech.api.GTValues.L;
import static gregtech.api.unification.material.Materials.Carbon;
import static gregtech.api.unification.material.info.MaterialFlags.DISABLE_DECOMPOSITION;
import static gregtech.api.unification.ore.OrePrefix.dust;

import java.util.ArrayList;
import java.util.Collection;

public class TKCYAAlloyingCrucibleRecipeHandler {

    public static void init() {

        for (Material material : GregTechAPI.MATERIAL_REGISTRY) {
            //if (material.getMaterialComponents().size() == 1) continue; //To remove element materials //TODO seems like it does not work
            if (material.hasFlags(DISABLE_DECOMPOSITION)) continue; //To remove polymers & chemicals
            if (!material.hasProperty(PropertyKey.FLUID)) continue;
            if (material.getFluid().getTemperature() <= 300) continue;
            register(material);

        }
    }



    public static void register(Material material) {

        Collection<FluidStack> f = new ArrayList<>();
        boolean containsCarbon = false;
        int outputMultiplier = 0;

        for (MaterialStack ms : material.getMaterialComponents()) {
            if (!ms.material.hasProperty(PropertyKey.FLUID)) continue;
            if (ms.material.getFluid().isGaseous()) continue; // Will make special recipes for those
            outputMultiplier += ms.amount;
            if (ms.material == Carbon) {
                containsCarbon = true;
            } else {
                f.add(ms.material.getFluid((int) ms.amount * L));
            }
        }

        if (f.size() < 2) return; /To remove element materials
        if (f.size() != material.getMaterialComponents().size() && !containsCarbon) return; //Means that some materials were removed

        if (!containsCarbon) {
            TKCYARecipeMaps.ALLOYING_CRUCIBLE_RECIPES.recipeBuilder()
                    .fluidInputs(f)
                    .fluidOutputs(material.getFluid(L * outputMultiplier))
                    .duration((int) material.getMass())
                    .buildAndRegister();
        }
        /*
        else {
            TKCYARecipeMaps.ALLOYING_CRUCIBLE_RECIPES.recipeBuilder()
                    .fluidInputs(f)
                    .input(dust, Carbon)
                    .fluidOutputs(material.getFluid(L * outputMultiplier))
                    .duration((int) material.getMass())
                    .buildAndRegister();
        }

         */

    }
}
