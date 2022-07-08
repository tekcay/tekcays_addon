package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.unification.material.Materials;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.common.items.TKCYAMetaItems;
import tekcays_addon.common.items.behaviors.ElectrodeBehavior;

import static gregtech.api.unification.material.Materials.Gold;
import static gregtech.api.unification.material.Materials.Platinum;
import static tekcays_addon.api.recipes.TKCYARecipeMaps.ELECTROLYSIS;

public class ElectrolysisHandler {

    public static void init() {

        //H2O -> H2 + O2

        ItemStack electrodeStack = TKCYAMetaItems.ELECTRODE.getStackForm();

        ElectrodeBehavior.getInstanceFor(electrodeStack).setPartMaterial(electrodeStack, Platinum);

        ELECTROLYSIS.recipeBuilder()
                .notConsumable(electrodeStack)
                .notConsumable(Materials.Water.getFluid(), 1000)
                .fluidInputs(Materials.Water.getFluid(1000))
                .fluidOutputs(Materials.Hydrogen.getFluid(2000), Materials.Oxygen.getFluid(1000))
                .duration(100)
                .EUt(120)
                .buildAndRegister();

    }

}
