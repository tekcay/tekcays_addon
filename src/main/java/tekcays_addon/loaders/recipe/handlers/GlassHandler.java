package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.GTValues;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;
import tekcays_addon.gtapi.unification.TKCYAMaterials;
import tekcays_addon.gtapi.utils.Glass;
import tekcays_addon.common.items.TKCYAMetaItems;

import static gregtech.api.unification.material.Materials.Glass;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static tekcays_addon.gtapi.utils.Glass.*;
import static tekcays_addon.gtapi.consts.TKCYAValues.MOLD_MATERIALS;

public class GlassHandler {

     public static void init() {

         GLASS_STUFF.add(BLOCK_GLASS);

        for (Glass glass : GLASS_STUFF) {

            for (Material m : MOLD_MATERIALS) {

                if (m.getFluid().getTemperature() > Glass.getFluid().getTemperature()) { //Compares temperatures of the mold Glass w/ and the fluid Glass

                    TKCYARecipeMaps.CASTING_TABLE_RECIPES.recipeBuilder()
                            .fluidInputs(Glass.getFluid(glass.fluidAmount))
                            .notConsumable(glass.mold, m)
                            .outputs(glass.output)
                            .duration(glass.fluidAmount* Glass.getFluid().getTemperature() / GTValues.L)
                            .buildAndRegister();

                    //Air Cooled

                    TKCYARecipeMaps.CASTING_TABLE_RECIPES.recipeBuilder()
                            .fluidInputs(Glass.getFluid(glass.fluidAmount), Materials.Air.getFluid(Glass.getFluid().getTemperature()))
                            .notConsumable(glass.mold, m)
                            .outputs(glass.output)
                            .duration((int) (0.8 * glass.fluidAmount* Glass.getFluid().getTemperature() / GTValues.L))
                            .buildAndRegister();

                    TKCYARecipeMaps.ELECTRIC_CASTING_RECIPES.recipeBuilder()
                            .fluidInputs(Glass.getFluid(glass.fluidAmount), Materials.Air.getFluid(Glass.getFluid().getTemperature()))
                            .notConsumable(glass.mold, m)
                            .outputs(glass.output)
                            .duration((int) (0.6 * glass.fluidAmount* Glass.getFluid().getTemperature() / GTValues.L))
                            .EUt(30)
                            .buildAndRegister();


                    //When using a gas collector, it outputs Hot Air

                    TKCYARecipeMaps.ELECTRIC_CASTING_RECIPES.recipeBuilder()
                            .fluidInputs(Glass.getFluid(glass.fluidAmount), Materials.Air.getFluid(Glass.getFluid().getTemperature()))
                            .notConsumable(glass.mold, m)
                            .notConsumable(TKCYAMetaItems.GAS_COLLECTOR)
                            .outputs(glass.output)
                            .fluidOutputs(TKCYAMaterials.HotAir.getFluid(Glass.getFluid().getTemperature()))
                            .duration((int) (0.6 * glass.fluidAmount* Glass.getFluid().getTemperature() / GTValues.L))
                            .EUt(30)
                            .buildAndRegister();

                } else { //Otherwise, it burns the mold

                    TKCYARecipeMaps.CASTING_TABLE_RECIPES.recipeBuilder()
                            .fluidInputs(Glass.getFluid(glass.fluidAmount))
                            .notConsumable(glass.mold, m)
                            .output(dust, Materials.Ash)
                            .duration(20)
                            .hidden()
                            .buildAndRegister();

                    TKCYARecipeMaps.ELECTRIC_CASTING_RECIPES.recipeBuilder()
                            .fluidInputs(Glass.getFluid(glass.fluidAmount))
                            .notConsumable(glass.mold, m)
                            .output(dust, Materials.Ash)
                            .duration(20)
                            .EUt(30)
                            .hidden()
                            .buildAndRegister();
                }
            }
        }
    }
}
