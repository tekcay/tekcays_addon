package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.unification.material.Material;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.api.utils.roasting.RoastableMaterial;
import tekcays_addon.common.items.TKCYAMetaItems;

import java.util.ArrayList;
import java.util.List;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static tekcays_addon.api.utils.roasting.RoastableMaterial.*;
import static tekcays_addon.api.utils.roasting.RoastingRecipeHandlerMethods.*;

public class RoastingHandler {

    public static final List<RoastableMaterial> ROASTABLE_MATERIALS = new ArrayList<RoastableMaterial>(){{
       add(TETRAHEDRITE);
       add(KESTERITE);
       add(STANNITE);
       add(COBALTITE);
       add(CHALCOPYRITE);
       add(ARSENOPYRITE);
       add(MOLYBDNITE);
       add(PENTLANDITE);
       add(STIBNITE);
       add(PYRITE);
    }};

    public static void init() {

        for (RoastableMaterial rm : ROASTABLE_MATERIALS) {
            Material material = rm.getMaterial();

            int fluidAmount = 1000 * getAmountSulfur(material);

            TKCYARecipeMaps.ROASTING.recipeBuilder()
                    .input(dust, material, getComponentsNumber(material))
                    .outputs(getOutputStack(material, dust))
                    .fluidInputs(Oxygen.getFluid(fluidAmount * 4))
                    .pressure(rm.getRoastingTemperature())
                    .temperature(rm.getRoastingTemperature())
                    .duration(100)
                    .EUt(50)
                    .buildAndRegister();

            TKCYARecipeMaps.ROASTING.recipeBuilder()
                    .input(dust, material, getComponentsNumber(material))
                    .outputs(getOutputStack(material, dust))
                    .fluidInputs(Air.getFluid(fluidAmount * 10))
                    .pressure(rm.getRoastingTemperature())
                    .temperature(rm.getRoastingTemperature())
                    .duration(100)
                    .EUt(50)
                    .buildAndRegister();

            //With the Air collector
            TKCYARecipeMaps.ROASTING.recipeBuilder()
                    .input(dust, material, getComponentsNumber(material))
                    .notConsumable(TKCYAMetaItems.GAS_COLLECTOR.getStackForm())
                    .outputs(getOutputStack(material, dust))
                    .fluidInputs(Oxygen.getFluid(fluidAmount * 4))
                    .fluidOutputs(SulfurDioxide.getFluid(fluidAmount))
                    .pressure(rm.getRoastingTemperature())
                    .temperature(rm.getRoastingTemperature())
                    .duration(100)
                    .EUt(50)
                    .buildAndRegister();

            TKCYARecipeMaps.ROASTING.recipeBuilder()
                    .input(dust, material, getComponentsNumber(material))
                    .notConsumable(TKCYAMetaItems.GAS_COLLECTOR.getStackForm())
                    .outputs(getOutputStack(material, dust))
                    .fluidInputs(Air.getFluid(fluidAmount * 10))
                    .fluidOutputs(SulfurDioxide.getFluid(fluidAmount))
                    .pressure(rm.getRoastingTemperature())
                    .temperature(rm.getRoastingTemperature())
                    .duration(100)
                    .EUt(50)
                    .buildAndRegister();
        }
    }
}
