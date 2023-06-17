package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.items.toolitem.ToolOreDict;
import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTUtility;
import gregtech.loaders.WoodTypeEntry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.ResourceLocation;
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

            recipeRemoval(entry.getWoodTypeEntry(), Items.STICK);

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

        recipeRemoval(WoodTypeEntryWrapper.TREATED_WOOD_ENTRY, OreDictUnifier.get(OrePrefix.stick, TreatedWood).getItem());

        ModHandler.removeRecipeByName(new ResourceLocation("minecraft:planks"));
        ModHandler.removeRecipeByName(new ResourceLocation("minecraft:wooden_slab"));

    }

    private static void recipeRemoval(WoodTypeEntry entry, Item item) {
        ModHandler.removeRecipeByOutput(entry.planks);
        ModHandler.removeRecipeByOutput(entry.slab);
        ModHandler.removeRecipeByOutput(new ItemStack(item));
    }


}
