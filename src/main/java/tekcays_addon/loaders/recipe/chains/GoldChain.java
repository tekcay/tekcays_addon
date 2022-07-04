package tekcays_addon.loaders.recipe.chains;

import static gregtech.api.recipes.RecipeMaps.CHEMICAL_RECIPES;
import static gregtech.api.unification.ore.OrePrefix.ingot;

public class GoldChain {

    public static void init() {

        // STEP 2
        // Cu3Au? + HNO3 -> Cu3Au?(OH) + NO2
        CHEMICAL_RECIPES.recipeBuilder().duration(80)
                .input(ingot, GoldAlloy, 4)
                .fluidInputs(NitricAcid.getFluid(1000))
                .outputs(GoldLeach.getItemStack(4))
                .fluidOutputs(NitrogenDioxide.getFluid(1000))
                .buildAndRegister();

        // STEP 3
        // Cu3Au?(OH) + HCl -> HAuCl(OH) + Cu3?
        CHEMICAL_RECIPES.recipeBuilder().duration(80)
                .inputs(GoldLeach.getItemStack(4))
                .fluidInputs(HydrochloricAcid.getFluid(1000))
                .outputs(CopperLeach.getItemStack(4))
                .fluidOutputs(ChloroauricAcid.getFluid(1000))
                .buildAndRegister();

        // STEP 4
        // HAuCl(OH) -> Au + H2O + Cl
        CHEMICAL_RECIPES.recipeBuilder().duration(100)
                .fluidInputs(ChloroauricAcid.getFluid(1000))
                .notConsumable(dust, PotassiumMetabisulfite)
                .output(dust, Gold, 2)
                .fluidOutputs(Water.getFluid(1000))
                .fluidOutputs(Chlorine.getFluid(1000))
                .buildAndRegister();

        // SIDE INGREDIENTS ============================================================================================

        // NOT CONSUMED INGREDIENT
        MIXER_RECIPES.recipeBuilder().duration(100).EUt(30)
                .notConsumable(new IntCircuitIngredient(1))
                .input(dust, Potassium, 2)
                .input(dust, Sulfur, 2)
                .fluidInputs(Oxygen.getFluid(5000))
                .output(dust, PotassiumMetabisulfite, 9)
                .buildAndRegister();


    }
}
