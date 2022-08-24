package tekcays_addon.loaders.recipe.chains;

import static gregtech.api.recipes.RecipeMaps.CHEMICAL_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static tekcays_addon.api.recipes.TKCYARecipeMaps.FILTRATION;
import static tekcays_addon.api.unification.TKCYAMaterials.*;
import static tekcays_addon.loaders.DamageableItemsLoader.filterStainlessSteel;

public class ZincChain {

    public static void init() {

        // ZnO + 2H2SO4 = ZnSO4 + ZincLeachingResidue [Contains: (H2O)(H2SO4)]
        CHEMICAL_RECIPES.recipeBuilder().duration(40).EUt(480)
                .input(dust, Zincite)
                .fluidInputs(SulfuricAcid.getFluid(2000))
                .fluidOutputs(ZincLeachingSolution.getFluid(1000))
                .buildAndRegister();

        // ZnO + 2H2SO4 = ZnSO4 + ZincLeachingResidue [Contains: (H2O)(H2SO4)]
        FILTRATION.recipeBuilder().duration(200)
                .fluidInputs(ZincLeachingSolution.getFluid(1000))
                .notConsumable(filterStainlessSteel)
                .output(dustSmall, Zinc)
                .fluidOutputs(ZincLeachingResidue.getFluid(1000))
                .buildAndRegister();



    }

}
