package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import tekcays_addon.gtapi.consts.TKCYAValues;

import static gregtech.api.GTValues.LV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.CHEMICAL_RECIPES;
import static gregtech.api.recipes.RecipeMaps.LARGE_CHEMICAL_RECIPES;
import static gregtech.api.unification.material.Materials.*;

public class GTCEuRequireCleanRoomHandler {

    public static void init() {

        //Polyethylene

        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(1))
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidInputs(Ethylene.getFluid(144))
                .fluidOutputs(Polyethylene.getFluid((int) (216 * TKCYAValues.CLEANROOM_MULTIPLIER)))
                .cleanroom(CleanroomType.CLEANROOM)
                .duration(160).EUt(VA[LV]).buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(3))
                .fluidInputs(Air.getFluid(7500))
                .fluidInputs(Ethylene.getFluid(2160))
                .fluidInputs(TitaniumTetrachloride.getFluid(100))
                .fluidOutputs(Polyethylene.getFluid((int) (3240 * TKCYAValues.CLEANROOM_MULTIPLIER)))
                .duration(800).EUt(VA[LV]).buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(3))
                .fluidInputs(Oxygen.getFluid(7500))
                .fluidInputs(Ethylene.getFluid(2160))
                .fluidInputs(TitaniumTetrachloride.getFluid(100))
                .fluidOutputs(Polyethylene.getFluid((int) (4320 * TKCYAValues.CLEANROOM_MULTIPLIER)))
                .cleanroom(CleanroomType.CLEANROOM)
                .duration(800).EUt(VA[LV]).buildAndRegister();
        
        //Polyvinylchloride

        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(1))
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidInputs(VinylChloride.getFluid(144))
                .fluidOutputs(PolyvinylChloride.getFluid((int) (216 * TKCYAValues.CLEANROOM_MULTIPLIER)))
                .cleanroom(CleanroomType.CLEANROOM)
                .duration(160).EUt(VA[LV]).buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(2))
                .fluidInputs(Air.getFluid(7500))
                .fluidInputs(VinylChloride.getFluid(2160))
                .fluidInputs(TitaniumTetrachloride.getFluid(100))
                .fluidOutputs(PolyvinylChloride.getFluid((int) (3240 * TKCYAValues.CLEANROOM_MULTIPLIER)))
                .cleanroom(CleanroomType.CLEANROOM)
                .duration(800).EUt(VA[LV]).buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(2))
                .fluidInputs(Oxygen.getFluid(7500))
                .fluidInputs(VinylChloride.getFluid(2160))
                .fluidInputs(TitaniumTetrachloride.getFluid(100))
                .fluidOutputs(PolyvinylChloride.getFluid((int) (4320 * TKCYAValues.CLEANROOM_MULTIPLIER)))
                .cleanroom(CleanroomType.CLEANROOM)
                .duration(800).EUt(VA[LV]).buildAndRegister();
        
        //PTFE

        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(1))
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidInputs(Tetrafluoroethylene.getFluid(144))
                .fluidOutputs(Polytetrafluoroethylene.getFluid((int) (216 * TKCYAValues.CLEANROOM_MULTIPLIER)))
                .cleanroom(CleanroomType.CLEANROOM)
                .duration(160).EUt(VA[LV]).buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(2))
                .fluidInputs(Air.getFluid(7500))
                .fluidInputs(Tetrafluoroethylene.getFluid(2160))
                .fluidInputs(TitaniumTetrachloride.getFluid(100))
                .fluidOutputs(Polytetrafluoroethylene.getFluid((int) (3240 * TKCYAValues.CLEANROOM_MULTIPLIER)))
                .cleanroom(CleanroomType.CLEANROOM)
                .duration(800).EUt(VA[LV]).buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(2))
                .fluidInputs(Oxygen.getFluid(7500))
                .fluidInputs(Tetrafluoroethylene.getFluid(2160))
                .fluidInputs(TitaniumTetrachloride.getFluid(100))
                .fluidOutputs(Polytetrafluoroethylene.getFluid((int) (4320 * TKCYAValues.CLEANROOM_MULTIPLIER)))
                .cleanroom(CleanroomType.CLEANROOM)
                .duration(800).EUt(VA[LV]).buildAndRegister();

    }

}
