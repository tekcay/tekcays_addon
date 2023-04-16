package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.GTValues;
import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.GTRecipeItemInput;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.recipes.ingredients.nbtmatch.NBTCondition;
import gregtech.api.recipes.ingredients.nbtmatch.NBTMatcher;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;
import tekcays_addon.gtapi.unification.TKCYAMaterials;
import tekcays_addon.gtapi.utils.TKCYAValues;

import static gregtech.api.GTValues.LV;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.plate;
import static tekcays_addon.gtapi.recipes.TKCYARecipeMaps.ELECTROLYSIS;
import static tekcays_addon.gtapi.unification.TKCYAMaterials.GalvanizedSteel;
import static tekcays_addon.loaders.DamageableItemsLoader.electrodeZinc;

public class GalvanizedSteel {

    public static void galvanizingSteelBath(){

        for (OrePrefix orePrefix : TKCYAValues.STEEL_TO_GALVANIZED_OREPREFIXES) {

            RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder()
                    .input(orePrefix, Steel)
                    .fluidInputs(Zinc.getFluid((int) (orePrefix.getMaterialAmount(Steel) * GTValues.L / (GTValues.M * 9)) * 2))
                    .duration((int) (10 + Steel.getMass() * orePrefix.getMaterialAmount(Steel) / GTValues.M))
                    .output(orePrefix, GalvanizedSteel)
                    .EUt((int) GTValues.V[LV])
                    .buildAndRegister();

            TKCYARecipeMaps.PRIMITIVE_BATH.recipeBuilder()
                    .input(orePrefix, Steel)
                    .fluidInputs(Zinc.getFluid((int) (orePrefix.getMaterialAmount(Steel) * GTValues.L / (GTValues.M * 9)) * 2))
                    .output(orePrefix, GalvanizedSteel)
                    .duration((int) (10 + 4 * Steel.getMass() * orePrefix.getMaterialAmount(Steel) / GTValues.M))
                    .buildAndRegister();

        }
    }

    public static void galvanizingSteelElectrolysis() {
        for (OrePrefix orePrefix : TKCYAValues.STEEL_TO_GALVANIZED_OREPREFIXES) {

            ELECTROLYSIS.recipeBuilder()
                    .input(orePrefix, Steel)
                    .inputNBT(GTRecipeItemInput.getOrCreate(electrodeZinc).setNonConsumable(), NBTMatcher.ANY, NBTCondition.ANY)
                    .fluidInputs(Zinc.getFluid((int) (orePrefix.getMaterialAmount(Steel) * GTValues.L / (GTValues.M * 9))))
                    .duration((int) (10 + Steel.getMass() * orePrefix.getMaterialAmount(Steel) / GTValues.M))
                    .output(orePrefix, GalvanizedSteel)
                    .EUt(120)
                    .buildAndRegister();
        }
    }

    public static void shapedRecipes() {
        ModHandler.addShapedRecipe("mv_machine_casing", MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.LV),
                "PPP", "PwP", "PPP", 'P', new UnificationEntry(plate, TKCYAMaterials.GalvanizedSteel));
    }

    public static void componentsAssemblerRecipes() {

        //LV Machine Casing
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, GalvanizedSteel, 8)
                .circuitMeta(8)
                .outputs(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.LV))
                .duration(50)
                .EUt(16)
                .buildAndRegister();

        //ULV Machine Casing
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, Materials.Potin, 8)
                .circuitMeta(8)
                .outputs(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.ULV))
                .duration(25)
                .EUt(16)
                .buildAndRegister();

        // LV Motor
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.stick, GalvanizedSteel, 2)
                .input(OrePrefix.stick, Materials.SteelMagnetic)
                .input(OrePrefix.cableGtSingle, Materials.Tin, 2)
                .input(OrePrefix.wireGtSingle, Materials.Copper, 4)
                .output(MetaItems.ELECTRIC_MOTOR_LV)
                .duration(100)
                .EUt(30)
                .buildAndRegister();

        //LV Piston
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, GalvanizedSteel, 3)
                .input(OrePrefix.stick, GalvanizedSteel, 2)
                .input(OrePrefix.cableGtSingle, Materials.Tin, 2)
                .input(OrePrefix.gearSmall, GalvanizedSteel)
                .input(MetaItems.ELECTRIC_MOTOR_LV)
                .output(MetaItems.ELECTRIC_PISTON_LV)
                .duration(100)
                .EUt(30)
                .buildAndRegister();

        //LV Robot Arm
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.stick, GalvanizedSteel, 2)
                .input(OrePrefix.cableGtSingle, Materials.Tin, 3)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.LV)
                .input(MetaItems.ELECTRIC_PISTON_LV)
                .output(MetaItems.ROBOT_ARM_LV)
                .duration(100)
                .EUt(30)
                .buildAndRegister();

        /*
        //LV Power Unit //TO DO
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.stick, GalvanizedSteel, 2)
                .input(OrePrefix.cableGtSingle, Materials.Tin, 3)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.LV)
                .input(MetaItems.ELECTRIC_PISTON_LV)
                .output(MetaItems.POWER_UNI)
                .duration(100) //TODO
                .EUt(30)
                .buildAndRegister();

         */
    }

    public static void componentsGTCEuRecipesRemoval() {
        // Assembler recipes

        // LV Machine Casing
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES,
                new ItemStack[] {
                        OreDictUnifier.get(OrePrefix.plate, Steel, 8),
                        IntCircuitIngredient.getIntegratedCircuit(8)},
                new FluidStack[] {});


        //LV Motor
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES,
                new ItemStack[] {
                        OreDictUnifier.get(OrePrefix.stick, Steel, 2),
                        OreDictUnifier.get(OrePrefix.stick, Materials.SteelMagnetic),
                        OreDictUnifier.get(OrePrefix.wireGtSingle, Materials.Copper, 4),
                        OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Tin, 2)},
                new FluidStack[] {});

        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES,
                new ItemStack[] {
                        OreDictUnifier.get(OrePrefix.stick, Iron, 2),
                        OreDictUnifier.get(OrePrefix.stick, Materials.IronMagnetic),
                        OreDictUnifier.get(OrePrefix.wireGtSingle, Materials.Copper, 4),
                        OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Tin, 2)},
                new FluidStack[] {});

        //LV Piston
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES,
                new ItemStack[] {
                        OreDictUnifier.get(OrePrefix.stick, Steel, 2),
                        OreDictUnifier.get(OrePrefix.plate, Steel, 3),
                        OreDictUnifier.get(OrePrefix.gearSmall, Steel),
                        OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Tin, 2),
                        MetaItems.ELECTRIC_MOTOR_LV.getStackForm()},
                new FluidStack[] {});

        //LV Robot Arm
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES,
                new ItemStack[] {
                        OreDictUnifier.get(OrePrefix.stick, Steel, 2),
                        OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.LV),
                        MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        MetaItems.ELECTRIC_MOTOR_LV.getStackForm(),
                        MetaItems.ELECTRIC_PISTON_LV.getStackForm(),
                        OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.Tin, 3)},
                new FluidStack[] {});



        //Shaped recipes

        //LV Machine Casing
        ModHandler.removeRecipeByOutput(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.LV));

        //ULV Machine Casing
        ModHandler.removeRecipeByOutput(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.ULV));

        //ULV Machine Hull
        ModHandler.removeRecipeByOutput(MetaTileEntities.HULL[0].getStackForm());


        //LV Motor
        ModHandler.removeRecipeByOutput(MetaItems.ELECTRIC_MOTOR_LV.getStackForm());
        //LV Piston
        ModHandler.removeRecipeByOutput(MetaItems.ELECTRIC_PISTON_LV.getStackForm());
        //LV Robot Arm
        ModHandler.removeRecipeByOutput(MetaItems.ROBOT_ARM_LV.getStackForm());
    }
}
