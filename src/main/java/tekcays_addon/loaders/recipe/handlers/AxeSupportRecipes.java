package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.items.toolitem.ToolOreDict;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTUtility;
import gregtech.loaders.WoodTypeEntry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;
import tekcays_addon.gtapi.unification.material.info.WoodTypeEntryWrapper;

import static gregtech.api.unification.material.Materials.TreatedWood;
import static gregtech.api.unification.material.Materials.Wood;

public class AxeSupportRecipes {

    public static void init() {

        for (WoodTypeEntryWrapper entry : WoodTypeEntryWrapper.WOOD_TYPE_ENTRIES_RAW) {

            TKCYARecipeMaps.LOG_CUTING.recipeBuilder()
                    .toolOreDict(ToolOreDict.toolAxe)
                    .inputs(entry.getWoodTypeEntry().log.copy())
                    .outputs(entry.getCutWood().copy())
                    .buildAndRegister();

            TKCYARecipeMaps.LOG_CUTING.recipeBuilder()
                    .toolOreDict(ToolOreDict.toolSaw)
                    .inputs(entry.getCutWood().copy())
                    .outputs(entry.getWoodTypeEntry().planks.copy())
                    .buildAndRegister();

            TKCYARecipeMaps.LOG_CUTING.recipeBuilder()
                    .toolOreDict(ToolOreDict.toolSaw)
                    .inputs(entry.getWoodTypeEntry().planks.copy())
                    .outputs(GTUtility.copyAmount(2, entry.getWoodTypeEntry().slab.copy()))
                    .buildAndRegister();

            recipeRemoval(entry.getWoodTypeEntry());
        }

        TKCYARecipeMaps.LOG_CUTING.recipeBuilder()
                .input(OrePrefix.plank, TreatedWood)
                .output(OrePrefix.slab, TreatedWood, 2)
                .buildAndRegister();

        TKCYARecipeMaps.LOG_CUTING.recipeBuilder()
                .input(OrePrefix.slab, TreatedWood)
                .output(OrePrefix.stick, TreatedWood, 4)
                .buildAndRegister();

        TKCYARecipeMaps.LOG_CUTING.recipeBuilder()
                .input(OrePrefix.slab, Wood)
                .output(OrePrefix.stick, Wood, 4)
                .buildAndRegister();

        ModHandler.removeRecipeByOutput(new ItemStack(Items.STICK, 4));
        ModHandler.removeRecipeByOutput(OreDictUnifier.get(OrePrefix.stick, TreatedWood, 4));

    }

    private static void recipeRemoval(WoodTypeEntry entry) {
        ModHandler.removeRecipeByName("minecraft:" + entry.woodName + "_planks");
        ModHandler.removeRecipeByName("minecraft:" + entry.woodName + "_wooden_slab");
    }

}
