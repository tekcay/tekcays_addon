package tekcays_addon.loaders.recipe.chains;

import net.minecraft.item.ItemStack;
import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockLargeMultiblockCasing;
import tekcays_addon.common.items.TKCYAMetaItems;
import tekcays_addon.common.items.behaviors.ElectrodeBehavior;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.CHEMICAL_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Water;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.api.unification.ore.OrePrefix.dustTiny;
import static tekcays_addon.api.recipes.TKCYARecipeMaps.ELECTROLYSIS;
import static tekcays_addon.api.unification.TKCYAMaterials.*;

public class FluorineChain {

    public static void init() {

        // CaF2 + H2SO4 -> CaSO4 + 2 HF
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Fluorite, 1)
                .fluidInputs(SulfuricAcid.getFluid(1000))
                .output(dust, CalciumSulfonate, 1)
                .fluidOutputs(HydrofluoricAcid.getFluid(1000))
                .duration(80)
                .EUt(VA[LV])
                .buildAndRegister();

        // LiOH + HF -> LiF + H20
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, LithiumHydroxide, 1)
                .fluidInputs(HydrofluoricAcid.getFluid(1000))
                .output(dust, LithiumFluoride, 1)
                .fluidOutputs(Water.getFluid(1000))
                .duration(80)
                .EUt(VA[LV])
                .buildAndRegister();

        // NaOH + HF -> NaF + H20
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, SodaAsh, 1)
                .fluidInputs(HydrofluoricAcid.getFluid(1000))
                .output(dust, SodiumFluoride, 1)
                .fluidOutputs(Water.getFluid(1000))
                .duration(80)
                .EUt(VA[LV])
                .buildAndRegister();

        // KOH + HF -> KF + H20
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Potash, 1)
                .fluidInputs(HydrofluoricAcid.getFluid(1000))
                .output(dust, PotassiumFluoride, 1)
                .fluidOutputs(Water.getFluid(1000))
                .duration(80)
                .EUt(VA[LV])
                .buildAndRegister();



        // KF + HF -> KF.HF
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Potash, 1)
                .notConsumable(HydrofluoricAcid.getFluid(1000))
                .fluidInputs(HydrofluoricAcid.getFluid(1000))
                .output(dust, PotassiumBifluoride, 1)
                .duration(80)
                .EUt(VA[LV])
                .buildAndRegister();


        // KF.HF(s) -> KF.HF(l)
        CHEMICAL_RECIPES.recipeBuilder() //TODO Melter or liquid extractor
                .input(dust, PotassiumBifluoride, 1)
                .fluidOutputs(PotassiumBifluoride.getFluid(1000))
                .duration(80)
                .EUt(VA[LV])
                .buildAndRegister();

        ItemStack electrodeCarbon = TKCYAMetaItems.ELECTRODE.getStackForm();

        ElectrodeBehavior.getInstanceFor(electrodeCarbon).setPartMaterial(electrodeCarbon, Carbon);

        ItemStack electrodeSteel = TKCYAMetaItems.ELECTRODE.getStackForm();

        ElectrodeBehavior.getInstanceFor(electrodeSteel).setPartMaterial(electrodeSteel, Steel);


        // KF.HF(l) + electrode + LiF -> H2 + F2  //TODO 70-130 °C
        ELECTROLYSIS.recipeBuilder()
                .input(dustTiny, LithiumFluoride, 1)
                .notConsumable(electrodeCarbon)
                .notConsumable(electrodeSteel)
                .notConsumable(TKCYAMetaBlocks.LARGE_MULTIBLOCK_CASING.getItemVariant(BlockLargeMultiblockCasing.CasingType.MONEL_CASING))
                .fluidInputs(PotassiumBifluoride.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(1000), Fluorine.getFluid(1000))
                .duration(80)
                .EUt(VA[HV])
                .buildAndRegister();
    }

}
