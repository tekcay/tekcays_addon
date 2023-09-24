package tekcays_addon.gtapi.recipes.machines;

import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import net.minecraftforge.items.IItemHandlerModifiable;

public class TopStaggeredFluidsRecipeMap<R extends RecipeBuilder<R>> extends RecipeMap<R> {

    public TopStaggeredFluidsRecipeMap(String unlocalizedName, int maxInputs, int maxOutputs, int maxFluidInputs, int maxFluidOutputs,
                                       R defaultRecipe, boolean isHidden) {
        super(unlocalizedName, maxInputs, maxOutputs, maxFluidInputs, maxFluidOutputs, defaultRecipe, isHidden);
    }

    @Override
    public ModularUI.Builder createJeiUITemplate(IItemHandlerModifiable importItems, IItemHandlerModifiable exportItems, FluidTankList importFluids, FluidTankList exportFluids, int yOffset) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 350)
                .widget(new ProgressWidget(200, 37, 1, 18, 18, GuiTextures.PROGRESS_BAR_ARROW, ProgressWidget.MoveType.HORIZONTAL))
                .widget(new ProgressWidget(2000, 81, 1, 18, 18, GuiTextures.PROGRESS_BAR_ARROW, ProgressWidget.MoveType.HORIZONTAL))
                .widget(new ProgressWidget(4000, 81, 23, 18, 18, GuiTextures.PROGRESS_BAR_ARROW, ProgressWidget.MoveType.HORIZONTAL))
                .widget(new ProgressWidget(6000, 81, 45, 18, 18, GuiTextures.PROGRESS_BAR_ARROW, ProgressWidget.MoveType.HORIZONTAL))
                .widget(new ProgressWidget(8000, 81, 67, 18, 18, GuiTextures.PROGRESS_BAR_ARROW, ProgressWidget.MoveType.HORIZONTAL))
                .widget(new ProgressWidget(10000, 81, 89, 18, 18, GuiTextures.PROGRESS_BAR_ARROW, ProgressWidget.MoveType.HORIZONTAL));

        addSlot(builder, 15, 1, 0, null, importFluids, true, false);
        addSlot(builder, 15, 23, 0, importItems, null, false, false);
        addSlot(builder, 59, 1, 0, null, exportFluids, true, true);
        addSlot(builder, 59, 23, 1, null, exportFluids, true, true);
        addSlot(builder, 59, 45, 2, null, exportFluids, true, true);
        addSlot(builder, 59, 67, 3, null, exportFluids, true, true);
        addSlot(builder, 59, 89, 4, null, exportFluids, true, true);

        return builder;
    }

}

