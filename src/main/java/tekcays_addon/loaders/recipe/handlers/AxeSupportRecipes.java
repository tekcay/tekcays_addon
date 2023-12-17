package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.items.ToolItems;
import gregtech.loaders.WoodTypeEntry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import tekcays_addon.api.recipe.RecipeRemovalHelper;
import tekcays_addon.common.metatileentities.TKCYAMetaTileEntities;
import tekcays_addon.gtapi.consts.TKCYAValues;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;
import tekcays_addon.gtapi.unification.material.info.WoodTypeEntryWrapper;

import static gregtech.api.unification.material.Materials.TreatedWood;
import static gregtech.api.unification.material.Materials.Wood;

public class AxeSupportRecipes {

    public static void init() {

        ModHandler.addShapedRecipe(TKCYAValues.TKCYA_ID, TKCYAMetaTileEntities.AXE_SUPPORT.getStackForm(), " U ", "   ", "   ", 'U', new UnificationEntry(OrePrefix.log, Wood));

        for (WoodTypeEntryWrapper entry : WoodTypeEntryWrapper.WOOD_TYPE_ENTRIES_RAW) {

            TKCYARecipeMaps.LOG_CUTING.recipeBuilder()
                    .notConsumable(TKCYAMetaTileEntities.AXE_SUPPORT.getStackForm())
                    .input(ToolItems.AXE.get())
                    .inputs(entry.getWoodTypeEntry().log.copy())
                    .outputs(entry.getCutWood().copy())
                    .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Wood))
                    .buildAndRegister();

            TKCYARecipeMaps.LOG_CUTING.recipeBuilder()
                    .notConsumable(TKCYAMetaTileEntities.AXE_SUPPORT.getStackForm())
                    .input(ToolItems.SAW.get())
                    .inputs(entry.getCutWood().copy())
                    .outputs(entry.getWoodTypeEntry().planks.copy())
                    .buildAndRegister();

            TKCYARecipeMaps.LOG_CUTING.recipeBuilder()
                    .notConsumable(TKCYAMetaTileEntities.AXE_SUPPORT.getStackForm())
                    .input(ToolItems.SAW.get())
                    .inputs(entry.getWoodTypeEntry().planks.copy())
                    .outputs(GTUtility.copyAmount(2, entry.getWoodTypeEntry().slab.copy()))
                    .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Wood))
                    .buildAndRegister();

            recipeRemoval(entry.getWoodTypeEntry());
        }

        TKCYARecipeMaps.LOG_CUTING.recipeBuilder()
                .notConsumable(TKCYAMetaTileEntities.AXE_SUPPORT.getStackForm())
                .input(ToolItems.AXE.get())
                .input(OrePrefix.plank, TreatedWood)
                .output(OrePrefix.slab, TreatedWood, 2)
                .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Wood))
                .buildAndRegister();

        TKCYARecipeMaps.LOG_CUTING.recipeBuilder()
                .notConsumable(TKCYAMetaTileEntities.AXE_SUPPORT.getStackForm())
                .input(ToolItems.SAW.get())
                .input(OrePrefix.slab, TreatedWood)
                .output(OrePrefix.stick, TreatedWood, 4)
                .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Wood))
                .buildAndRegister();

        TKCYARecipeMaps.LOG_CUTING.recipeBuilder()
                .notConsumable(TKCYAMetaTileEntities.AXE_SUPPORT.getStackForm())
                .input(ToolItems.SAW.get())
                .input(OrePrefix.slab, Wood)
                .output(OrePrefix.stick, Wood, 4)
                .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Wood))
                .buildAndRegister();

        ModHandler.removeRecipeByOutput(new ItemStack(Items.STICK, 4));
        ModHandler.removeRecipeByOutput(OreDictUnifier.get(OrePrefix.stick, TreatedWood, 4));

    }

    private static void recipeRemoval(WoodTypeEntry entry) {

        RecipeRemovalHelper.removeMcRecipe(RecipeRemovalHelper.MC_PLANK, entry.woodName);
        RecipeRemovalHelper.removeMcRecipe(RecipeRemovalHelper.MC_SLAB, entry.woodName);

        RecipeRemovalHelper.removeGtRecipeTool(RecipeRemovalHelper.SLAB_SAW, entry.woodName);
        RecipeRemovalHelper.removeGtRecipeTool(RecipeRemovalHelper.PLANK_SAW, entry.woodName);
    }

}
