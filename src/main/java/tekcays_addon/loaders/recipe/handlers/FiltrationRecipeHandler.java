package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.recipes.ingredients.GTRecipeItemInput;
import gregtech.api.recipes.ingredients.nbtmatch.NBTCondition;
import gregtech.api.recipes.ingredients.nbtmatch.NBTMatcher;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;
import tekcays_addon.common.items.TKCYAMetaItems;
import tekcays_addon.common.items.behaviors.FilterBehavior;

import static gregtech.api.recipes.RecipeMaps.LASER_ENGRAVER_RECIPES;
import static gregtech.api.unification.ore.OrePrefix.*;
import static tekcays_addon.gtapi.utils.MiscMethods.*;
import static tekcays_addon.gtapi.utils.TKCYAValues.FILTER_MATERIALS;
import static tekcays_addon.gtapi.utils.TKCYAValues.MIXTURE_TO_FILTER;
import static tekcays_addon.loaders.DamageableItemsLoader.filterStainlessSteel;

public class FiltrationRecipeHandler {

    public static void init() {

        //Filtration recipes
        for (FluidStack fs : MIXTURE_TO_FILTER) {

            TKCYARecipeMaps.FILTRATION.recipeBuilder()
                    .inputNBT(GTRecipeItemInput.getOrCreate(filterStainlessSteel).setNonConsumable(), NBTMatcher.ANY, NBTCondition.ANY)
                    .fluidInputs(fs)
                    .fluidOutputs(getOutputFluidStackFromNBT(fs.tag))
                    .outputs(getOutputItemStackFromNBT(dust, fs.tag))
                    .duration(100)
                    .buildAndRegister();
        }




        //Filter crafting
        for (Material m : FILTER_MATERIALS) {

            ItemStack filterStack = TKCYAMetaItems.FILTER.getStackForm();

            FilterBehavior.getInstanceFor(filterStack).setPartMaterial(filterStack, m);

            LASER_ENGRAVER_RECIPES.recipeBuilder()
                    .input(plate, m)
                    .notConsumable(lens, Materials.Glass)
                    .outputs(filterStack)
                    .duration((int) m.getMass() * 30)
                    .EUt(24)
                    .buildAndRegister();
        }
    }

}
