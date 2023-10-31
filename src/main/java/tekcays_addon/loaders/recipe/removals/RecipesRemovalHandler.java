package tekcays_addon.loaders.recipe.removals;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.MetaItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import tekcays_addon.loaders.recipe.handlers.HarderRotorsHandler;
import tekcays_addon.loaders.recipe.handlers.TKCYAPartsRecipeHandler;

import static gregtech.api.unification.material.Materials.*;
import static tekcays_addon.common.TKCYAConfigHolder.harderStuff;
import static tekcays_addon.common.TKCYAConfigHolder.meltingOverhaul;

public class RecipesRemovalHandler {

    public static void init() {
        if(harderStuff.disableTinCircuitRecipes) TinCircuitRemoval.init();
        if (harderStuff.disableComponentsShapesRecipes) ShapedComponentsRemoval.init();
        if (harderStuff.enableHarderRotors) HarderRotorsHandler.init();
        if (meltingOverhaul.enableMeltingOverhaul) TKCYAPartsRecipeHandler.removeExtractor();
        if (harderStuff.disableVanillaFurnaceRecipes) FurnacesRemoval.init();
    }

    public static void removeShapedTreatedWoodRecipe(){
        ModHandler.removeRecipeByOutput(OreDictUnifier.get(OrePrefix.plank, TreatedWood));
    }

    public static void removeMoldsAndUsage() {

        for (MetaItem<?>.MetaValueItem mvi : MetaItems.SHAPE_MOLDS) {
            if (mvi.isItemEqual(MetaItems.SHAPE_EMPTY.getStackForm())) continue;
            ModHandler.removeRecipeByOutput(mvi.getStackForm());
        }

        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES,
                new ItemStack[] {
                        OreDictUnifier.get(OrePrefix.dust, Wood),
                        MetaItems.SHAPE_MOLD_PLATE.getStackForm()},
                new FluidStack[] {Glue.getFluid(50)});

        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.FORMING_PRESS_RECIPES,
                new ItemStack[] {
                        OreDictUnifier.get(OrePrefix.dust, Glass),
                        MetaItems.SHAPE_MOLD_BALL.getStackForm()},
                new FluidStack[] {});

        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.FORMING_PRESS_RECIPES,
                new ItemStack[] {
                        OreDictUnifier.get(OrePrefix.dust, Glass),
                        MetaItems.SHAPE_MOLD_BLOCK.getStackForm()},
                new FluidStack[] {});
    }

}
