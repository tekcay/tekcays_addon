package tekcays_addon.common.metatileentities.multi;

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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import tekcays_addon.api.recipes.TKCYARecipeMaps;
import tekcays_addon.api.render.TKCYATextures;

import javax.annotation.Nonnull;

public class MetaTileEntityFilter extends RecipeMapPrimitiveMultiblockController {

    private boolean boostAllowed;

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
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return TKCYATextures.STEEL_GT;
    }


    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), recipeMapWorkable.isActive(), recipeMapWorkable.isWorkingEnabled());
    }

    @Override
    public int getLightValueForPart(IMultiblockPart sourcePart) {
        return sourcePart == null && recipeMapWorkable.isActive() ? 15 : 0;
    }

    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        return ModularUI.builder(GuiTextures.BACKGROUND, 176, 166)
                .shouldColor(false)
                .widget(new LabelWidget(5, 5, getMetaFullName()))

                .widget(new SlotWidget(importItems, 0, 52, 38, true, true)
                        .setBackgroundTexture(GuiTextures.SLOT))

                .widget(new TankWidget(importFluids.getTankAt(0), 77, 50, 18, 18)
                        .setBackgroundTexture(GuiTextures.FLUID_SLOT)
                        .setAlwaysShowFull(true)
                        .setContainerClicking(true, true))

                .widget(new RecipeProgressWidget(recipeMapWorkable::getProgressPercent, 59, 39, 20, 15,
                        GuiTextures.PROGRESS_BAR_BATH, ProgressWidget.MoveType.VERTICAL_DOWNWARDS, TKCYARecipeMaps.FILTRATION))

                .widget(new SlotWidget(exportItems, 0, 52, 20, true, false)
                        .setBackgroundTexture(GuiTextures.SLOT))

                .widget(new TankWidget(exportFluids.getTankAt(1), 77, 20, 18, 18)
                        .setBackgroundTexture(GuiTextures.FLUID_SLOT)
                        .setAlwaysShowFull(true)
                        .setContainerClicking(true, true))

                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 0);
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.PRIMITIVE_BLAST_FURNACE_OVERLAY;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }


}
