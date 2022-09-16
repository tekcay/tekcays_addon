package tekcays_addon.loaders.recipe.chains;

import gregicality.science.api.recipes.GCYSRecipeMaps;
import gregtech.api.recipes.ingredients.GTRecipeItemInput;
import gregtech.api.recipes.ingredients.nbtmatch.NBTCondition;
import gregtech.api.recipes.ingredients.nbtmatch.NBTMatcher;
import gregtech.api.unification.stack.MaterialStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.api.unification.TKCYAMaterials;
import tekcays_addon.api.utils.MiscMethods;
import tekcays_addon.common.items.TKCYAMetaItems;

import static gregtech.api.recipes.RecipeMaps.CHEMICAL_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static tekcays_addon.api.unification.TKCYAMaterials.*;
import static tekcays_addon.api.utils.TKCYAValues.MIXTURE_TO_FILTER;
import static tekcays_addon.loaders.DamageableItemsLoader.electrodeSilver;


public class GoldChain {

    public static void init() {

        ////FROM GCYL

        // STEP 2
        // Cu3Au? + HNO3 -> Cu3Au?(OH) + NO2
        CHEMICAL_RECIPES.recipeBuilder().duration(80)
                .EUt(30)
                .input(ingot, GoldAlloy, 4)
                .fluidInputs(NitricAcid.getFluid(1000))
                .output(dust, GoldLeach)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder().duration(80)
                .EUt(30)
                .notConsumable(TKCYAMetaItems.GAS_COLLECTOR)
                .input(ingot, GoldAlloy, 4)
                .fluidInputs(NitricAcid.getFluid(1000))
                .output(dust, GoldLeach)
                .fluidOutputs(NitrogenDioxide.getFluid(1000))
                .buildAndRegister();

        // STEP 3
        // Cu3Au?(OH) + HCl -> HAuCl(OH) + Cu3?

        CHEMICAL_RECIPES.recipeBuilder().duration(80)
                .EUt(30)
                .input(dust, GoldLeach, 4)
                .fluidInputs(HydrochloricAcid.getFluid(1000))
                .fluidOutputs(MiscMethods.getMixtureToFilterStack(new MaterialStack(CopperLeach, 4), new MaterialStack(ChloroauricAcid, 1000)))
                .buildAndRegister();

        // STEP 4
        // HAuCl(OH) -> Au + H2O + Cl

        FluidStack output = MiscMethods.getMixtureToFilterStack(new MaterialStack(Gold, 4), new MaterialStack(HydrochloricAcid, 4000));

        TKCYARecipeMaps.ELECTROLYSIS.recipeBuilder().duration(100)
                .EUt(100)
                .inputNBT(GTRecipeItemInput.getOrCreate(electrodeSilver).setNonConsumable(), NBTMatcher.ANY, NBTCondition.ANY)
                .fluidInputs(ChloroauricAcid.getFluid(1000), DistilledWater.getFluid(1000))
                .input(dustTiny, TKCYAMaterials.PotassiumMetaBisulfite)
                .fluidOutputs(output)
                .fluidOutputs(Oxygen.getFluid(1500))
                .buildAndRegister();

        TKCYARecipeMaps.ELECTROLYSIS.recipeBuilder().duration(100)
                .EUt(120)
                .inputNBT(GTRecipeItemInput.getOrCreate(electrodeSilver).setNonConsumable(), NBTMatcher.ANY, NBTCondition.ANY)
                .fluidInputs(ChloroauricAcid.getFluid(1000), DistilledWater.getFluid(1000))
                .notConsumable(TKCYAMetaItems.GAS_COLLECTOR)
                .input(dustTiny, TKCYAMaterials.PotassiumMetaBisulfite)
                .fluidOutputs(output)
                .fluidOutputs(Oxygen.getFluid(1500))
                .buildAndRegister();

        /* //TODO gets a DUST_MIXTURE
        // Step 3 recovery
        // This step does not directly process Chloroauric Acid, and instead is processing
        // other byproducts from the chain, which are compacted from the older versions of the chain.
        // Cu3? -> 3Cu + Fe + Ni + Ag + Pb
        GCYSRecipeMaps.DRYER_RECIPES.recipeBuilder().EUt(30).duration(80)
                .input(dust, CopperLeach, 4)
                .output(dust, Copper, 3)
                .chancedOutput(dust, Lead, 1500, 500)
                .chancedOutput(dust, Iron, 1200, 400)
                .chancedOutput(dust, Nickel, 1000, 300)
                .chancedOutput(dust, Silver, 800, 200)
                .buildAndRegister();

         */
    }
}
