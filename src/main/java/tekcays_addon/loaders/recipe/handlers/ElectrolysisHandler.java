package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.item.ItemStack;
import tekcays_addon.common.items.TKCYAMetaItems;
import tekcays_addon.common.items.behaviors.ElectrodeBehavior;

import static gregtech.api.unification.material.Materials.Platinum;
import static tekcays_addon.api.recipes.TKCYARecipeMaps.ELECTROLYSIS;

public class ElectrolysisHandler {

    public static void init() {

        //H2O -> H2 + O2

        ItemStack electrodePlatinum = TKCYAMetaItems.ELECTRODE.getStackForm();

        ElectrodeBehavior.getInstanceFor(electrodePlatinum).setPartMaterial(electrodePlatinum, Platinum);

        ELECTROLYSIS.recipeBuilder()
                .notConsumable(electrodePlatinum)
                .notConsumable(Materials.SulfuricAcid.getFluid(), 1000)
                .fluidInputs(Materials.DistilledWater.getFluid(1000))
                .fluidOutputs(Materials.Hydrogen.getFluid(2000), Materials.Oxygen.getFluid(1000))
                .duration(100)
                .EUt(120)
                .buildAndRegister();

        ELECTROLYSIS.recipeBuilder()
                .notConsumable(electrodePlatinum)
                .notConsumable(OrePrefix.dust, Materials.Potash, 1)
                .fluidInputs(Materials.DistilledWater.getFluid(1000))
                .fluidOutputs(Materials.Hydrogen.getFluid(2000), Materials.Oxygen.getFluid(1000))
                .duration(100)
                .EUt(120)
                .buildAndRegister();

        ELECTROLYSIS.recipeBuilder()
                .notConsumable(electrodePlatinum)
                .notConsumable(OrePrefix.dust, Materials.SodiumHydroxide, 1)
                .fluidInputs(Materials.DistilledWater.getFluid(1000))
                .fluidOutputs(Materials.Hydrogen.getFluid(2000), Materials.Oxygen.getFluid(1000))
                .duration(100)
                .EUt(120)
                .buildAndRegister();

    }

}
