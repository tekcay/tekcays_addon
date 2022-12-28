package tekcays_addon.api.recipes.machines;

import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import net.minecraftforge.items.IItemHandlerModifiable;

public class TopStaggeredRecipeMap<R extends RecipeBuilder<R>> extends RecipeMap<R> {

    public TopStaggeredRecipeMap(String unlocalizedName,
                                 int minInputs, int maxInputs, int minOutputs, int maxOutputs,
                                 int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs,
                                 R defaultRecipe, boolean isHidden) {
        super(unlocalizedName, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, defaultRecipe, isHidden);
    }

    @Override
    public ModularUI.Builder createJeiUITemplate(IItemHandlerModifiable importItems, IItemHandlerModifiable exportItems, FluidTankList importFluids, FluidTankList exportFluids, int yOffset) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 150)
                .widget(new ProgressWidget(200, 96, 5, 20, 18, GuiTextures.PROGRESS_BAR_ARROW, ProgressWidget.MoveType.HORIZONTAL));
        addSlot(builder, 30, 5, 0, importItems, null, false, false);
        addSlot(builder, 52, 5, 1, importItems, null, false, false);
        addSlot(builder, 74, 5, 0, null, importFluids, true, false);
        addSlot(builder, 118, 5, 0, null, exportFluids, true, true);
        return builder;
    }
}

