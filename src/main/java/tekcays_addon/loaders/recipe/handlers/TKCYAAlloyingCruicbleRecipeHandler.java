package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.stack.MaterialStack;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.recipes.TKCYARecipeMaps;

import static gregtech.api.GTValues.L;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TKCYAAlloyingCruicbleRecipeHandler {

    public static void init() {

        for (Material material : GregTechAPI.MATERIAL_REGISTRY) {
            if (material.getMaterialComponents().size() > 1) {
                if (material.hasProperty(PropertyKey.DUST)) {
                    if (material.hasProperty(PropertyKey.FLUID)) {
                        if (material.getFluid().getTemperature() > 300) {
                            registerAlloyingCrucibleRecipes(material);
                        }
                     }
                }
            }
        }
    }



    public static void registerAlloyingCrucibleRecipes(Material material) {

        Collection<FluidStack> f = new ArrayList<>();

        for (MaterialStack ms : material.getMaterialComponents()) {
            if (!ms.material.hasProperty(PropertyKey.FLUID)) return;
                f.add(ms.material.getFluid((int) ms.amount * L));
        }

        TKCYARecipeMaps.ALLOYING_CRUCIBLE_RECIPES.recipeBuilder()
                .fluidInputs(f)
                .fluidOutputs(material.getFluid(L))
                .duration((int) material.getMass())
                .buildAndRegister();

    }


}
