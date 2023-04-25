package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import tekcays_addon.gtapi.consts.TKCYAValues;

import static gregtech.api.GTValues.LV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.CHEMICAL_RECIPES;
import static gregtech.api.recipes.RecipeMaps.LARGE_CHEMICAL_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.gtapi.unification.TKCYAMaterials.HighDensityPolyethylene;
import static tekcays_addon.gtapi.unification.TKCYAMaterials.Polypropylene;

public class PolymerHandler {
    
    public static void init() {

        //Polypropylene
        
        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(1))
                .fluidInputs(Air.getFluid(1000))
                .fluidInputs(Propene.getFluid(144))
                .fluidOutputs(Polypropylene.getFluid(144))
                .duration(160).EUt(VA[LV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(1))
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidInputs(Propene.getFluid(144))
                .fluidOutputs(Polypropylene.getFluid(216))
                .duration(160).EUt(VA[LV]).buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(2))
                .fluidInputs(Air.getFluid(7500))
                .fluidInputs(Propene.getFluid(2160))
                .fluidInputs(TitaniumTetrachloride.getFluid(100))
                .fluidOutputs(Polypropylene.getFluid(3240))
                .duration(800).EUt(VA[LV]).buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(2))
                .fluidInputs(Oxygen.getFluid(7500))
                .fluidInputs(Propene.getFluid(2160))
                .fluidInputs(TitaniumTetrachloride.getFluid(100))
                .fluidOutputs(Polypropylene.getFluid(4320))
                .duration(800).EUt(VA[LV]).buildAndRegister();

        //Cleanroom

        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(1))
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidInputs(Propene.getFluid(144))
                .fluidOutputs(Polypropylene.getFluid((int) (215 * TKCYAValues.CLEANROOM_MULTIPLIER)))
                .cleanroom(CleanroomType.CLEANROOM)
                .duration(160).EUt(VA[LV]).buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(2))
                .fluidInputs(Air.getFluid(7500))
                .fluidInputs(Propene.getFluid(2160))
                .fluidInputs(TitaniumTetrachloride.getFluid(100))
                .fluidOutputs(Polypropylene.getFluid((int) (3240 * TKCYAValues.CLEANROOM_MULTIPLIER)))
                .cleanroom(CleanroomType.CLEANROOM)
                .duration(800).EUt(VA[LV]).buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(2))
                .fluidInputs(Oxygen.getFluid(7500))
                .fluidInputs(Propene.getFluid(2160))
                .fluidInputs(TitaniumTetrachloride.getFluid(100))
                .fluidOutputs(Polypropylene.getFluid((int) (4320 * TKCYAValues.CLEANROOM_MULTIPLIER)))
                .cleanroom(CleanroomType.CLEANROOM)
                .duration(800).EUt(VA[LV]).buildAndRegister();

        //HDPE

        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(11))
                .fluidInputs(Air.getFluid(1000))
                .fluidInputs(Ethylene.getFluid(144))
                .fluidOutputs(HighDensityPolyethylene.getFluid(144))
                .duration(160).EUt(VA[LV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(11))
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidInputs(Ethylene.getFluid(144 * 4))
                .fluidOutputs(HighDensityPolyethylene.getFluid(216))
                .duration(160).EUt(VA[LV]).buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(12))
                .fluidInputs(Air.getFluid(7500))
                .fluidInputs(Ethylene.getFluid(2160 * 4))
                .fluidInputs(TitaniumTetrachloride.getFluid(100))
                .fluidOutputs(HighDensityPolyethylene.getFluid(3240))
                .duration(800).EUt(VA[LV]).buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(12))
                .fluidInputs(Oxygen.getFluid(7500))
                .fluidInputs(Ethylene.getFluid(2160 * 4))
                .fluidInputs(TitaniumTetrachloride.getFluid(100))
                .fluidOutputs(HighDensityPolyethylene.getFluid(4320))
                .duration(800).EUt(VA[LV]).buildAndRegister();

        //Cleanroom

        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(11))
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidInputs(Ethylene.getFluid(144 * 4))
                .fluidOutputs(HighDensityPolyethylene.getFluid((int) (215 * TKCYAValues.CLEANROOM_MULTIPLIER)))
                .cleanroom(CleanroomType.CLEANROOM)
                .duration(160).EUt(VA[LV]).buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(12))
                .fluidInputs(Air.getFluid(7500))
                .fluidInputs(Ethylene.getFluid(2160 * 4))
                .fluidInputs(TitaniumTetrachloride.getFluid(100))
                .fluidOutputs(HighDensityPolyethylene.getFluid((int) (3240 * TKCYAValues.CLEANROOM_MULTIPLIER)))
                .cleanroom(CleanroomType.CLEANROOM)
                .duration(800).EUt(VA[LV]).buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(12))
                .fluidInputs(Oxygen.getFluid(7500))
                .fluidInputs(Ethylene.getFluid(2160 * 4))
                .fluidInputs(TitaniumTetrachloride.getFluid(100))
                .fluidOutputs(HighDensityPolyethylene.getFluid((int) (4320 * TKCYAValues.CLEANROOM_MULTIPLIER)))
                .cleanroom(CleanroomType.CLEANROOM)
                .duration(800).EUt(VA[LV]).buildAndRegister();
        
        
    }
    
}
