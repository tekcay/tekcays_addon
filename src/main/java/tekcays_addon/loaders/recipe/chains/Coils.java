package tekcays_addon.loaders.recipe.chains;

import gregtech.api.GTValues;
import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;
import tekcays_addon.gtapi.unification.TKCYAMaterials;
import tekcays_addon.common.items.TKCYAMetaItems;

import static gregtech.api.GTValues.*;

public class Coils {

    public static void init(){

        //Mica based pulp

        RecipeMaps.MIXER_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Materials.Mica, 3)
                .input(OrePrefix.dust, Materials.RawRubber, 2)
                .output(OrePrefix.dust, TKCYAMaterials.MicaPulp, 5)
                .EUt(8)
                .duration(400)
                .buildAndRegister();

        RecipeMaps.MIXER_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Materials.Mica, 3)
                .input(MetaItems.STICKY_RESIN)
                .output(OrePrefix.dust, TKCYAMaterials.MicaPulp, 4)
                .EUt(8)
                .duration(400)
                .buildAndRegister();

        //Mica based sheet

        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, TKCYAMaterials.MicaPulp, 4)
                .input(OrePrefix.dust, Materials.Asbestos, 2)
                .output(TKCYAMetaItems.MICA_SHEET, 5)
                .EUt(28)
                .duration(400)
                .buildAndRegister();

        //Mica insulator sheet

        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder() //TODO : use another machine ?
                .input(TKCYAMetaItems.MICA_SHEET, 5)
                .input(OrePrefix.dust, Materials.SiliconDioxide, 3)
                .output(TKCYAMetaItems.MICA_INSULATOR_SHEET, 5)
                .EUt(30)
                .duration(400)
                .buildAndRegister();

        //Mica insulator foil

        TKCYARecipeMaps.CLUSTER_MILL_RECIPES.recipeBuilder()
                .input(TKCYAMetaItems.MICA_INSULATOR_SHEET)
                .output(TKCYAMetaItems.MICA_INSULATOR_FOIL, 4)
                .EUt(30)
                .duration(100)
                .buildAndRegister();

        //Coils

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(VA[LV]).input(OrePrefix.wireGtDouble, Materials.Cupronickel, 8).input(OrePrefix.foil, Materials.Bronze, 8).input(TKCYAMetaItems.MICA_INSULATOR_FOIL, 8).fluidInputs(Materials.TinAlloy.getFluid(GTValues.L)).outputs(MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.CUPRONICKEL)).duration(200).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(VA[MV]).input(OrePrefix.wireGtDouble, Materials.Kanthal, 8).input(OrePrefix.foil, Materials.Aluminium, 8).input(TKCYAMetaItems.MICA_INSULATOR_FOIL, 8).fluidInputs(Materials.Copper.getFluid(GTValues.L)).outputs(MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.KANTHAL)).duration(300).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(VA[HV]).input(OrePrefix.wireGtDouble, Materials.Nichrome, 8).input(OrePrefix.foil, Materials.StainlessSteel, 8).input(TKCYAMetaItems.MICA_INSULATOR_FOIL, 8).fluidInputs(Materials.Aluminium.getFluid(GTValues.L)).outputs(MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.NICHROME)).duration(400).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(VA[EV]).input(OrePrefix.wireGtDouble, Materials.RTMAlloy, 8).input(OrePrefix.foil, Materials.VanadiumSteel, 8).input(TKCYAMetaItems.MICA_INSULATOR_FOIL, 8).fluidInputs(Materials.Nichrome.getFluid(GTValues.L)).outputs(MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.RTM_ALLOY)).duration(500).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(VA[IV]).input(OrePrefix.wireGtDouble, Materials.HSSG, 8).input(OrePrefix.foil, Materials.TungstenCarbide, 8).input(TKCYAMetaItems.MICA_INSULATOR_FOIL, 8).fluidInputs(Materials.Tungsten.getFluid(GTValues.L)).outputs(MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.HSS_G)).duration(600).buildAndRegister();


        //RecipeRemovals

        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES,
                new ItemStack[] {OreDictUnifier.get(OrePrefix.wireGtDouble, Materials.Cupronickel, 8),
                OreDictUnifier.get(OrePrefix.foil, Materials.Bronze, 8)},
                new FluidStack[] {(Materials.TinAlloy.getFluid(L))});

        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES,
                new ItemStack[] {OreDictUnifier.get(OrePrefix.wireGtDouble, Materials.Kanthal, 8),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Aluminium, 8)},
                new FluidStack[] {(Materials.Copper.getFluid(L))});

        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES,
                new ItemStack[] {OreDictUnifier.get(OrePrefix.wireGtDouble, Materials.Nichrome, 8),
                        OreDictUnifier.get(OrePrefix.foil, Materials.StainlessSteel, 8)},
                new FluidStack[] {(Materials.Aluminium.getFluid(L))});

        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES,
                new ItemStack[] {OreDictUnifier.get(OrePrefix.wireGtDouble, Materials.TungstenSteel, 8),
                        OreDictUnifier.get(OrePrefix.foil, Materials.VanadiumSteel, 8)},
                new FluidStack[] {(Materials.Nichrome.getFluid(L))});

        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES,
                new ItemStack[] {OreDictUnifier.get(OrePrefix.wireGtDouble, Materials.HSSG, 8),
                        OreDictUnifier.get(OrePrefix.foil, Materials.TungstenCarbide, 8)},
                new FluidStack[] {(Materials.Tungsten.getFluid(L))});


    }

}
