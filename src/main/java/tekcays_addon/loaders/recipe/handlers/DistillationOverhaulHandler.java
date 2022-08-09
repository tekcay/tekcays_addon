package tekcays_addon.loaders.recipe.handlers;

import tekcays_addon.common.blocks.TKCYAMetaBlocks;
import tekcays_addon.common.blocks.blocks.BlockPump;

import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.api.recipes.TKCYARecipeMaps.DISTILLATION;
import static tekcays_addon.api.utils.FluidWithProperties.*;

public class DistillationOverhaulHandler {

    public static void init() {
        distillationRecipes();
        fluidWithPropertiesInit();
        bubbleCapsRecipes();
        distillationTraysRecipes();
        distillationTraysBlockRecipes();
    }

    public static void distillationRecipes() {

        DISTILLATION.recipeBuilder()
                .fluidInputs(WoodVinegar.getFluid(1000))
                .fluidOutputs(AceticAcid.getFluid(100))
                .fluidOutputs(Water.getFluid(500))
                .fluidOutputs(Ethanol.getFluid(10))
                .fluidOutputs(Methanol.getFluid(300))
                .fluidOutputs(Acetone.getFluid(50))
                .fluidOutputs(MethylAcetate.getFluid(10))
                .minSeparationFactor(10)
                .duration(40)
                .buildAndRegister();

        DISTILLATION.recipeBuilder()
                .fluidInputs(Oil.getFluid(50))
                .notConsumable(TKCYAMetaBlocks.PUMP_MACHINE.getItemVariant(BlockPump.PumpType.PUMP_MACHINE_HV))
                .fluidOutputs(SulfuricHeavyFuel.getFluid(15))
                .fluidOutputs(SulfuricLightFuel.getFluid(50))
                .fluidOutputs(SulfuricNaphtha.getFluid(20))
                .fluidOutputs(SulfuricGas.getFluid(60))
                .minSeparationFactor(5)
                .duration(20).buildAndRegister();

        DISTILLATION.recipeBuilder()
                .fluidInputs(OilLight.getFluid(150))
                .notConsumable(TKCYAMetaBlocks.PUMP_MACHINE.getItemVariant(BlockPump.PumpType.PUMP_MACHINE_HV))
                .fluidOutputs(SulfuricHeavyFuel.getFluid(10))
                .fluidOutputs(SulfuricLightFuel.getFluid(20))
                .fluidOutputs(SulfuricNaphtha.getFluid(30))
                .fluidOutputs(SulfuricGas.getFluid(240))
                .minSeparationFactor(5)
                .duration(20).buildAndRegister();

        DISTILLATION.recipeBuilder()
                .fluidInputs(OilHeavy.getFluid(100))
                .notConsumable(TKCYAMetaBlocks.PUMP_MACHINE.getItemVariant(BlockPump.PumpType.PUMP_MACHINE_HV))
                .fluidOutputs(SulfuricHeavyFuel.getFluid(250))
                .fluidOutputs(SulfuricLightFuel.getFluid(45))
                .fluidOutputs(SulfuricNaphtha.getFluid(15))
                .fluidOutputs(SulfuricGas.getFluid(60))
                .minSeparationFactor(5)
                .duration(20).EUt(288).buildAndRegister();

        DISTILLATION.recipeBuilder()
                .fluidInputs(RawOil.getFluid(100))
                .notConsumable(TKCYAMetaBlocks.PUMP_MACHINE.getItemVariant(BlockPump.PumpType.PUMP_MACHINE_HV))
                .fluidOutputs(SulfuricHeavyFuel.getFluid(15))
                .fluidOutputs(SulfuricLightFuel.getFluid(50))
                .fluidOutputs(SulfuricNaphtha.getFluid(20))
                .fluidOutputs(SulfuricGas.getFluid(60))
                .minSeparationFactor(5)
                .duration(20).buildAndRegister();

    }

    public static void fluidWithPropertiesInit() {
        FLUID_WITH_PROPERTIES.add(ACETIC_ACID);
        FLUID_WITH_PROPERTIES.add(ACETONE);
        FLUID_WITH_PROPERTIES.add(ETHANOL);
        FLUID_WITH_PROPERTIES.add(METHANOL);
        FLUID_WITH_PROPERTIES.add(METHYL_ACETATE);
        FLUID_WITH_PROPERTIES.add(WATER);
        FLUID_WITH_PROPERTIES.add(OIL);
        FLUID_WITH_PROPERTIES.add(OIL_HEAVY);
        FLUID_WITH_PROPERTIES.add(OIL_LIGHT);
        FLUID_WITH_PROPERTIES.add(RAW_OIL);
        FLUID_WITH_PROPERTIES.add(SULFURIC_GAS);
        FLUID_WITH_PROPERTIES.add(SULFURIC_NAPHTHA);
        FLUID_WITH_PROPERTIES.add(SULFURIC_LIGHT_FUEL);
        FLUID_WITH_PROPERTIES.add(SULFURIC_HEAVY_FUEL);
    }

    public static void bubbleCapsRecipes() {

    }

    public static void distillationTraysRecipes() {

    }

    public static void distillationTraysBlockRecipes() {

    }


}