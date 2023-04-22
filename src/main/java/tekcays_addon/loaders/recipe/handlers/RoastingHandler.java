package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.unification.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;
import tekcays_addon.gtapi.utils.roasting.RoastableMaterial;
import tekcays_addon.common.items.TKCYAMetaItems;

import java.util.ArrayList;
import java.util.List;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static tekcays_addon.gtapi.utils.roasting.RoastableMaterial.*;
import static tekcays_addon.gtapi.utils.roasting.RoastingRecipeHandlerMethods.*;

public class RoastingHandler {

    public static final List<RoastableMaterial> ROASTABLE_MATERIALS = new ArrayList<RoastableMaterial>(){{
       add(TETRAHEDRITE);
       add(KESTERITE);
       add(STANNITE);
       add(COBALTITE);
       add(CHALCOPYRITE);
       add(ARSENOPYRITE);
       add(PENTLANDITE);
       add(STIBNITE);
       add(PYRITE);
       add(REALGAR);
       add(BORNITE);
    }};

    public static void init() {

        // 2 Cu2S + 2 O2 -> 2 Cu2O + 2 SO2
        specialOutputRecipes(CHALCOCITE, 2, 2, 2);

        // 2 MoS2 + 7 O2 -> 2 MoO3 + 4 SO2
        specialOutputRecipes(MOLYBDENITE, 2, 2, 7);


        for (RoastableMaterial rm : ROASTABLE_MATERIALS) {
            Material material = rm.getMaterial();
            ItemStack outputStack = new ItemStack(getDustMixtureStackWithNBT(material).getItem(), 9);
            int fluidAmount = 1000 * getAmountSulfur(material);
            FluidStack sulfurDioxideStack = SulfurDioxide.getFluid(fluidAmount);
            FluidStack oxygenStack = Oxygen.getFluid(fluidAmount * 2);
            FluidStack airStack = Air.getFluid(fluidAmount * 10);


            TKCYARecipeMaps.ROASTING.recipeBuilder()
                    .input(dust, material, getComponentsNumber(material))
                    .outputs(outputStack)
                    .fluidInputs(oxygenStack)
                    .minPressure(rm.getRoastingPressure())
                    .minTemperature(rm.getRoastingTemperature())
                    .duration(100)
                    .EUt(50)
                    .buildAndRegister();

            TKCYARecipeMaps.ROASTING.recipeBuilder()
                    .input(dust, material, getComponentsNumber(material))
                    .outputs(outputStack)
                    .fluidInputs(airStack)
                    .minPressure(rm.getRoastingPressure())
                    .minTemperature(rm.getRoastingTemperature())
                    .duration(100)
                    .EUt(50)
                    .buildAndRegister();

            //With the Air collector
            TKCYARecipeMaps.ROASTING.recipeBuilder()
                    .input(dust, material, getComponentsNumber(material))
                    .notConsumable(TKCYAMetaItems.GAS_COLLECTOR.getStackForm())
                    .outputs(outputStack)
                    .fluidInputs(oxygenStack)
                    .fluidOutputs(sulfurDioxideStack)
                    .minPressure(rm.getRoastingPressure())
                    .minTemperature(rm.getRoastingTemperature())
                    .duration(100)
                    .EUt(50)
                    .buildAndRegister();

            TKCYARecipeMaps.ROASTING.recipeBuilder()
                    .input(dust, material, getComponentsNumber(material))
                    .notConsumable(TKCYAMetaItems.GAS_COLLECTOR.getStackForm())
                    .outputs(outputStack)
                    .fluidInputs(airStack)
                    .fluidOutputs(sulfurDioxideStack)
                    .minPressure(rm.getRoastingPressure())
                    .minTemperature(rm.getRoastingTemperature())
                    .duration(100)
                    .EUt(50)
                    .buildAndRegister();
        }
    }

    public static void specialOutputRecipes(RoastableMaterial roastableMaterial, int countInput, int countOutput, int oxygen) {

        Material input = roastableMaterial.getMaterial();
        Material output = roastableMaterial.getOutput();
        int fluidAmount = 1000 * getAmountSulfur(input);
        int pressure = roastableMaterial.getRoastingPressure();
        int temp = roastableMaterial.getRoastingTemperature();

        if (roastableMaterial.getFluidOutput() == null) {

            TKCYARecipeMaps.ROASTING.recipeBuilder()
                    .input(dust, input, countInput * getComponentsNumber(input))
                    .output(dust, output, countOutput * getComponentsNumber(output))
                    .fluidInputs(Air.getFluid(10000 * oxygen))
                    .minPressure(pressure)
                    .minTemperature(temp)
                    .duration(100)
                    .EUt(50)
                    .buildAndRegister();

            TKCYARecipeMaps.ROASTING.recipeBuilder()
                    .input(dust, input, countInput * getComponentsNumber(input))
                    .output(dust, output, countOutput * getComponentsNumber(output))
                    .fluidInputs(Oxygen.getFluid(4000 * oxygen))
                    .minPressure(pressure)
                    .minTemperature(temp)
                    .duration(100)
                    .EUt(50)
                    .buildAndRegister();

            //With the Air collector
            TKCYARecipeMaps.ROASTING.recipeBuilder()
                    .input(dust, input, countInput * getComponentsNumber(input))
                    .output(dust, output, countOutput * getComponentsNumber(output))
                    .fluidInputs(Air.getFluid(10000 * oxygen))
                    .fluidOutputs(SulfurDioxide.getFluid(fluidAmount))
                    .minPressure(pressure)
                    .minTemperature(temp)
                    .duration(100)
                    .EUt(50)
                    .buildAndRegister();

            TKCYARecipeMaps.ROASTING.recipeBuilder()
                    .input(dust, input, countInput * getComponentsNumber(input))
                    .output(dust, output, countOutput * getComponentsNumber(output))
                    .fluidInputs(Oxygen.getFluid(4000 * oxygen))
                    .fluidOutputs(SulfurDioxide.getFluid(fluidAmount))
                    .minPressure(pressure)
                    .minTemperature(temp)
                    .duration(100)
                    .EUt(50)
                    .buildAndRegister();

        } else {

            TKCYARecipeMaps.ROASTING.recipeBuilder()
                    .input(dust, input, countInput * getComponentsNumber(input))
                    .output(dust, output, countOutput * getComponentsNumber(output))
                    .fluidInputs(Air.getFluid(10000 * oxygen))
                    .fluidOutputs(roastableMaterial.getFluidOutput().getFluid(1000))
                    .minPressure(pressure)
                    .minTemperature(temp)
                    .duration(100)
                    .EUt(50)
                    .buildAndRegister();

            TKCYARecipeMaps.ROASTING.recipeBuilder()
                    .input(dust, input, countInput * getComponentsNumber(input))
                    .output(dust, output, countOutput * getComponentsNumber(output))
                    .fluidInputs(Oxygen.getFluid(4000 * oxygen))
                    .fluidOutputs(roastableMaterial.getFluidOutput().getFluid(1000))
                    .minPressure(pressure)
                    .minTemperature(temp)
                    .duration(100)
                    .EUt(50)
                    .buildAndRegister();

            //With the Air collector
            TKCYARecipeMaps.ROASTING.recipeBuilder()
                    .input(dust, input, countInput * getComponentsNumber(input))
                    .output(dust, output, countOutput * getComponentsNumber(output))
                    .fluidInputs(Air.getFluid(10000 * oxygen))
                    .fluidOutputs(SulfurDioxide.getFluid(fluidAmount))
                    .fluidOutputs(roastableMaterial.getFluidOutput().getFluid(1000))
                    .minPressure(pressure)
                    .minTemperature(temp)
                    .duration(100)
                    .EUt(50)
                    .buildAndRegister();

            TKCYARecipeMaps.ROASTING.recipeBuilder()
                    .input(dust, input, countInput * getComponentsNumber(input))
                    .output(dust, output, countOutput * getComponentsNumber(output))
                    .fluidInputs(Oxygen.getFluid(4000 * oxygen))
                    .fluidOutputs(SulfurDioxide.getFluid(fluidAmount))
                    .fluidOutputs(roastableMaterial.getFluidOutput().getFluid(1000))
                    .minPressure(pressure)
                    .minTemperature(temp)
                    .duration(100)
                    .EUt(50)
                    .buildAndRegister();
        }
    }
}
