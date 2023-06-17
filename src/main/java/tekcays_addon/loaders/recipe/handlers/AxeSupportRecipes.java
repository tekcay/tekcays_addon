package tekcays_addon.loaders.recipe.handlers;

import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTUtility;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;
import tekcays_addon.gtapi.unification.material.info.WoodTypeEntryWrapper;

import static gregtech.api.unification.material.Materials.TreatedWood;
import static gregtech.api.unification.material.Materials.Wood;

public class AxeSupportRecipes {

    public static void init() {

        for (WoodTypeEntryWrapper entry : WoodTypeEntryWrapper.WOOD_TYPE_ENTRIES_RAW) {

            TKCYARecipeMaps.LOG_CUTING.recipeBuilder()
                    //.notConsumable(new ItemStack(ToolItems.AXE.get()))
                    .inputs(entry.getWoodTypeEntry().log.copy())
                    .outputs(entry.getCutWood().copy())
                    .duration(10)
                    .buildAndRegister();

            TKCYARecipeMaps.LOG_CUTING.recipeBuilder()
                    //.notConsumable(new ItemStack(ToolItems.AXE.get()))
                    .inputs(entry.getCutWood().copy())
                    .outputs(entry.getWoodTypeEntry().planks.copy())
                    .duration(10)
                    .buildAndRegister();

            TKCYARecipeMaps.LOG_CUTING.recipeBuilder()
                    //.notConsumable(new ItemStack(ToolItems.AXE.get()))
                    .inputs(entry.getWoodTypeEntry().planks.copy())
                    .outputs(GTUtility.copyAmount(2, entry.getWoodTypeEntry().slab.copy()))
                    .duration(10)
                    .buildAndRegister();

        }

        TKCYARecipeMaps.LOG_CUTING.recipeBuilder()
                //.notConsumable(new ItemStack(ToolItems.SAW.get()))
                .input(OrePrefix.plank, TreatedWood)
                .output(OrePrefix.slab, TreatedWood, 2)
                .duration(10)
                .buildAndRegister();

        TKCYARecipeMaps.LOG_CUTING.recipeBuilder()
                //.notConsumable(new ItemStack(ToolItems.SAW.get()))
                .input(OrePrefix.slab, TreatedWood)
                .output(OrePrefix.stick, TreatedWood, 4)
                .duration(10)
                .buildAndRegister();

        TKCYARecipeMaps.LOG_CUTING.recipeBuilder()
                //.notConsumable(new ItemStack(ToolItems.SAW.get()))
                .input(OrePrefix.slab, Wood)
                .output(OrePrefix.stick, Wood, 4)
                .duration(10)
                .buildAndRegister();

    }


}
