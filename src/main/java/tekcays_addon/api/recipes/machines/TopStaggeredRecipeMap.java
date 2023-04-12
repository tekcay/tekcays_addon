package tekcays_addon.api.recipes.machines;

import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import net.minecraftforge.items.IItemHandlerModifiable;

import static tekcays_addon.api.utils.TKCYAValues.SLOT_INTERVAL;

public class TopStaggeredRecipeMap<R extends RecipeBuilder<R>> extends RecipeMap<R> {

    private static final int maxSlots = 4;

    public TopStaggeredRecipeMap(String unlocalizedName, int maxInputs, int maxOutputs, int maxFluidInputs, int maxFluidOutputs,
                                 R defaultRecipe, boolean isHidden) {
        super(unlocalizedName, maxInputs, maxOutputs, maxFluidInputs, maxFluidOutputs, defaultRecipe, isHidden);
    }

    @Override
    public ModularUI.Builder createJeiUITemplate(IItemHandlerModifiable importItems, IItemHandlerModifiable exportItems, FluidTankList importFluids, FluidTankList exportFluids, int yOffset) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 150)
                .widget(new ProgressWidget(200, 77, 1, 18, 18, GuiTextures.PROGRESS_BAR_ARROW, ProgressWidget.MoveType.HORIZONTAL));

        /*
        addSlot(builder, 30, 5, 0, importItems, null, false, false);
        addSlot(builder, 30, 27, 1, importItems, null, false, false);
        addSlot(builder, 52, 5, 0, null, importFluids, true, false);
        addSlot(builder, 52, 27, 1, null, importFluids, true, false);
        addSlot(builder, 74, 5, 2, null, importFluids, true, false);
        addSlot(builder, 74, 27, 3, null, importFluids, true, false);
        addSlot(builder, 118, 5, 0, null, exportFluids, true, true);
        addSlot(builder, 118, 27, 1, null, exportFluids, true, true);

         */

        int inputOffSet = 1;
        int outputOffset = 77 + SLOT_INTERVAL;


        if (getMaxInputs() != 0) {
            for (int i = 0; i < getMaxInputs(); i++) {
                addSlot(builder, getX(inputOffSet, i), getY(i), i, importItems, null, false, false);
            }
        }

        if (getMaxFluidInputs() != 0) {
            for (int i = getMaxInputs(); i < getMaxInputs() + getMaxFluidInputs(); i++) {
                addSlot(builder, getX(inputOffSet, i), getY(i), i - getMaxInputs(), null, importFluids, true, false);
            }
        }

        if (getMaxOutputs() != 0) {
            for (int i = 0; i < getMaxOutputs(); i++) {
                addSlot(builder, getX(outputOffset, i), getY(i), i, exportItems, null, false, true);
            }
        }

        if (getMaxFluidOutputs() != 0) {
            for (int i = getMaxOutputs(); i < getMaxOutputs() + getMaxFluidOutputs(); i++) {
                addSlot(builder, getX(outputOffset, i), getY(i), i - getMaxOutputs(), null, exportFluids, true, true);
            }
        }

        return builder;
    }

    private int getX(int offset, int i) {
        return offset + (i < maxSlots ? i * SLOT_INTERVAL : (i - maxSlots) * SLOT_INTERVAL);
    }

    private int getY(int i) {
        return i < maxSlots ? 1 : 1 + SLOT_INTERVAL;
    }

}

