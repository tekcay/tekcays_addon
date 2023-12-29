package tekcays_addon.common.metatileentities.multi;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapPrimitiveMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import tekcays_addon.common.items.behaviors.FilterBehavior;
import tekcays_addon.gtapi.recipes.TKCYARecipeMaps;
import tekcays_addon.gtapi.render.TKCYATextures;

public class MetaTileEntityFilter extends RecipeMapPrimitiveMultiblockController {

    public MetaTileEntityFilter(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYARecipeMaps.FILTRATION);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityFilter(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("Y")
                .where('Y', selfPredicate())
                .build();
    }

    @Override
    public void updateFormedValid() {
        super.updateFormedValid();

        ItemStack filterStack = importItems.getStackInSlot(0);
        if (filterStack.getDisplayName().equals("Air")) {
            this.recipeMapWorkable.setWorkingEnabled(false);
            // this.recipeMapWorkable.invalidateOutputs();
            return;
        } else this.recipeMapWorkable.setWorkingEnabled(true);

        FilterBehavior behavior = FilterBehavior.getInstanceFor(filterStack);
        if (behavior == null) return;
        if (!this.recipeMapWorkable.isActive()) return;
        if (getOffsetTimer() % 20 == 0) {
            behavior.applyFilterDamage(filterStack, 20);
        }
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return TKCYATextures.STEEL_GT;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(),
                recipeMapWorkable.isActive(), recipeMapWorkable.isWorkingEnabled());
    }

    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        return ModularUI.builder(GuiTextures.BACKGROUND, 176, 166)
                .shouldColor(false)
                .widget(new LabelWidget(5, 5, getMetaFullName()))

                .widget(new SlotWidget(importItems, 0, 50, 10, true, true)
                        .setBackgroundTexture(GuiTextures.SLOT))

                .widget(new TankWidget(importFluids.getTankAt(0), 80, 10, 18, 18)
                        .setBackgroundTexture(GuiTextures.FLUID_SLOT)
                        .setAlwaysShowFull(true)
                        .setContainerClicking(true, true))

                .widget(new RecipeProgressWidget(recipeMapWorkable::getProgressPercent, 65, 35, 18, 18,
                        GuiTextures.PROGRESS_BAR_SIFT, ProgressWidget.MoveType.VERTICAL_DOWNWARDS,
                        TKCYARecipeMaps.FILTRATION))

                .widget(new SlotWidget(exportItems, 0, 50, 60, true, false)
                        .setBackgroundTexture(GuiTextures.SLOT))

                .widget(new TankWidget(exportFluids.getTankAt(0), 80, 60, 18, 18)
                        .setBackgroundTexture(GuiTextures.FLUID_SLOT)
                        .setAlwaysShowFull(true)
                        .setContainerClicking(true, false))

                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 0);
    }

    @NotNull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.SIFTER_OVERLAY;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }
}
