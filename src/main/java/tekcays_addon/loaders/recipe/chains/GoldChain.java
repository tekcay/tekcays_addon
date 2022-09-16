package tekcays_addon.loaders.recipe.chains;

import gregtech.api.recipes.ingredients.GTRecipeItemInput;
import gregtech.api.recipes.ingredients.nbtmatch.NBTCondition;
import gregtech.api.recipes.ingredients.nbtmatch.NBTMatcher;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.api.unification.TKCYAMaterials;
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

        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("Composition", "material.copper_leach,4,material.chloroauric_acid,1000");

        CHEMICAL_RECIPES.recipeBuilder().duration(80)
                .EUt(30)
                .input(dust, GoldLeach, 4)
                .fluidInputs(HydrochloricAcid.getFluid(1000))
                .fluidOutputs(new FluidStack(MixtureToFilter.getFluid(), 1000, nbt))
                .buildAndRegister();

        // STEP 4
        // HAuCl(OH) -> Au + H2O + Cl

        nbt.removeTag("Composition");
        nbt.setString("Composition", "material.gold,4,material.hydrochloric_acid,4000");
        FluidStack output = new FluidStack(MixtureToFilter.getFluid(), 1000, nbt);
        MIXTURE_TO_FILTER.add(output);

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
    }
}
